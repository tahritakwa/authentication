package fr.sparkit.starkoauthmicroservice.service.impl;

import fr.sparkit.starkoauthmicroservice.convertor.CompanyConverter;
import fr.sparkit.starkoauthmicroservice.convertor.UserConverter;
import fr.sparkit.starkoauthmicroservice.dao.*;
import fr.sparkit.starkoauthmicroservice.dto.CompanyDto;
import fr.sparkit.starkoauthmicroservice.dto.UserDto;
import fr.sparkit.starkoauthmicroservice.dto.UserRequestDto;
import fr.sparkit.starkoauthmicroservice.model.*;
import fr.sparkit.starkoauthmicroservice.service.IUserService;
import fr.sparkit.starkoauthmicroservice.util.*;
import fr.sparkit.starkoauthmicroservice.util.errors.ApiErrorCodes;
import fr.sparkit.starkoauthmicroservice.util.errors.ErrorsResponse;
import fr.sparkit.starkoauthmicroservice.util.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements IUserService {


    @Value("${projectName}")
    private String projectName;

    @Value("${clientId}")
    private String clientId;

    @Value("${clientSecret}")
    private String clientSecret;

    @Value("${PrivateKey}")
    private String privateKey ;

    @Value("${isWIthLicence}")
    private boolean isWIthLicence ;

    @Value("${pathLicense}")
    private String pathLicense;


    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private JdbcTokenStoreUtils jdbcTokenStoreUtils;




    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CompanyRepository companyRepository;
    private CustomUserDetailsService customUserDetailsService;
    private RoleUserRepository roleUserRepository;
    private RolePermissionRepository rolePermissionRepository;
    private UserCompanyRepository userCompanyRepository;

    @Autowired
    private CompanyLicenceRepository companyLicenceRepository;

    @Autowired
    private TokenRequestUtils tokenRequestUtils;

    private final CacheManager cacheManager;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       CompanyRepository companyRepository, CustomUserDetailsService customUserDetailsService,
                       RoleUserRepository roleUserRepository, RolePermissionRepository rolePermissionRepository,
                       UserCompanyRepository userCompanyRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.companyRepository = companyRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.roleUserRepository = roleUserRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.userCompanyRepository = userCompanyRepository;

        this.cacheManager = cacheManager;
    }

    @Override
    public Object retrieveToken(Credential credential, String projectName) {
        boolean isWithLicenceAndFileNotFound = false ;
        User user = userRepository.findByEmailAndIsDeletedFalse(credential.getEmail())
                .orElseThrow(() -> new HttpCustomException(500, new ErrorsResponse().error("Bad Credentials")));;
        List<UserCompany> userCompanies = userCompanyRepository.findByUser_IdAndIsDeletedFalse(user.getId());

        if(isWIthLicence)
        {
            isWithLicenceAndFileNotFound = true ;
            String name = "";
            for(UserCompany company : userCompanies) {
                name = company.getCompany().getName();
                File f = new File(pathLicense+ name + ".keystore");
                if(f.exists())
                {
                    isWithLicenceAndFileNotFound = false ;
                    try {
                        if (checkCertificate(name, privateKey)) {
                            checkUserHasAuthorities(credential.getEmail());
                            if (projectName.equals(this.projectName)) {
                                checkIfBtoBUser(credential.getEmail());
                            } else {
                                checkIfStarkUser(credential.getEmail());
                                checkCompany(user);
                            }
                        }
                    } catch (Exception e) {
                        throw new HttpCustomException(ApiErrorCodes.INVALID_LICENSE);
                    }
                    break;
                }
            }
        }

        if (isWithLicenceAndFileNotFound)
        {
            throw new HttpCustomException(ApiErrorCodes.INVALID_LICENSE);
        }
        else if(!isWIthLicence) {
            checkUserHasAuthorities(credential.getEmail());
            if (projectName.equals(this.projectName)) {
                checkIfBtoBUser(credential.getEmail());
            } else {
                checkIfStarkUser(credential.getEmail());
                checkCompany(user);
            }
        }
        //checkUserAlreadyLoggedIn(credential.getEmail());
        return tokenRequestUtils.accessTokenRequest(credential, new Oauth2ClientCredential(clientId, clientSecret));

    }

    @Override
    public Object retrieveTokenUsingRefreshToken(RefreshToken refreshToken) {
        return tokenRequestUtils.refreshTokenRequest(refreshToken, new Oauth2ClientCredential(clientId, clientSecret));
    }

    @Override
    public Object retrieveTokenUsingEmail(UserRequestDto userRequestDto) {
        Date refreshTokenExpirationDate;
        OAuth2RefreshToken refreshToken;
        try{
            refreshToken = jdbcTokenStoreUtils.selectRefreshTokenByUsername(userRequestDto.getEmail());
            refreshTokenExpirationDate = jwtUtils.getTokenExpirationDate(refreshToken.getValue());
        }catch (EmptyResultDataAccessException e){
            throw new HttpCustomException(ApiErrorCodes.INVALID_TOKEN,new ErrorsResponse().error(userRequestDto.getEmail()));
        }
        if(refreshTokenExpirationDate!=null && refreshTokenExpirationDate.after(Date.from(Instant.now()))){
            RefreshToken refreshTokenObject = new RefreshToken(refreshToken.getValue());
            return tokenRequestUtils.refreshTokenRequest(refreshTokenObject, new Oauth2ClientCredential(clientId, clientSecret));
        }else{
            revokeToken(new UserRequestDto(userRequestDto.getEmail()));
            throw new HttpCustomException(ApiErrorCodes.INVALID_TOKEN,new ErrorsResponse().error(userRequestDto.getEmail()));
        }
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "UserAuthoritiesCache", key = "'UserAuthoritiesCache_'+#email")})
    public List<Role> addRolesToUser(String email, List<Integer> rolesIds) {
        List<Role> roles = roleRepository.findByIdIn(rolesIds);
        User user = getUserByEmail(email);
        List<RoleUser> roleUsers = roleUserRepository.findByUser_Id(user.getId());
        List<RoleUser> roleUsersForCurrentCompany = roleUsers.stream()
                .filter(roleUser -> roleUser.getUser().getLastConnectedCompany().equals(user.getLastConnectedCompany()))
                .collect(Collectors.toList());
        roleUsersForCurrentCompany.forEach(roleUser -> roleUserRepository.deleteById(roleUser.getId()));
        roles.forEach(role -> roleUserRepository.save(new RoleUser(user, role)));
        return roles;
    }

    @Override
    public List<Role> findRolesByUserAndCompany(String email, Integer companyId) {
        User user = getUserByEmail(email);
        List<Role> allRoles = roleUserRepository.findByUser_Id(user.getId()).stream().map(RoleUser::getRole)
                .collect(Collectors.toList());
        List<Role> rolesForCompany = allRoles.stream().filter(role -> role.getCompany().getId().equals(companyId))
                .collect(Collectors.toList());
        rolesForCompany.forEach(role -> {
            role.setPermissions(new ArrayList<>());
            role.getCompany().setDbSettings(new DbSettings());
        });
        return rolesForCompany;
    }

    @Override
    public boolean checkUserHasRoles(String token, List<String> permissions) {
        log.info("verifying if user with token {} hasRoles : {}", token, permissions);
        return !Collections.disjoint(getUserAuthorities(token), permissions);
    }

    @Override
    public List<String> getUserAuthorities(String token) {
        String email = jwtUtils.getEmailFromToken(token);
        return customUserDetailsService.getAuthorities(email);
    }

    @Override
    public boolean changeUsersCompanyUsingToken(String companyCode, String token) {
        Company company = companyRepository.findByCodeAndIsDeletedFalse(companyCode)
                .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.COMPANY_WITH_CODE_NOT_FOUND));
        String email = jwtUtils.getEmailFromToken(token);
        customUserDetailsService.changeUsersCompany(company, email);
        return true;
    }

    @Override
    public Collection<String> getUsersByRoles(List<String> permissions, String companyCode) {
        List<User> users = userRepository.findAllByIsDeletedFalse();
        Set<String> usersWithPermissions = new HashSet<>();
        for (User user : users) {
            List<Role> roles = roleUserRepository.findByUser_Id(user.getId()).stream().map(RoleUser::getRole)
                    .filter(role -> role.getCompany().getCode().equals(companyCode)).collect(Collectors.toList());
            Set<String> authorities = new HashSet<>();
            for (Role role : roles) {
                authorities.add(role.getCode());
                List<String> userPermissions = rolePermissionRepository.findByRole_Id(role.getId()).stream()
                        .map(rolePermission -> rolePermission.getPermission().getCode()).collect(Collectors.toList());
                authorities.addAll(userPermissions);
            }
            if (!Collections.disjoint(authorities, permissions)) {
                usersWithPermissions.add(user.getEmail());
            }
        }
        return usersWithPermissions;
    }

    @Override
    public List<CompanyDto> getUserWithAssociatedCompanies(String email) {
        return userCompanyRepository.findByUser_EmailAndIsDeletedFalse(email).stream()
                .map(userCompany -> CompanyConverter.modelToDto(userCompany.getCompany())).collect(Collectors.toList());
    }

    @Override
    public boolean revokeToken(UserRequestDto userRequestDto) {
        return jdbcTokenStoreUtils.revokeToken(userRequestDto);
    }

    @Override
    public boolean changeUserLanguage(UserRequestDto userRequestDto) {
        return userRepository.changeUserLanguage(userRequestDto.getEmail(), userRequestDto.getLanguage()) > 0;
    }


    private void checkUserAlreadyLoggedIn(String email) {
        String queryResult = null;
        Date dateExp = null;
        try {
            queryResult = jdbcTokenStoreUtils.selectUsernameQuery(email);
            OAuth2RefreshToken refreshToken = jdbcTokenStoreUtils.selectRefreshTokenByUsername(queryResult);
            assert refreshToken != null;
            dateExp = jwtUtils.getTokenExpirationDate(refreshToken.getValue());
        } catch (EmptyResultDataAccessException e) {
            log.info("No user logged in with this email : {}", email);
        }
        if (dateExp != null && dateExp.before(Date.from(Instant.now()))) {
            revokeToken(new UserRequestDto(email));
        } else if (queryResult != null) {
            throw new HttpCustomException(ApiErrorCodes.USER_ALREADY_LOGGED_IN, new ErrorsResponse().error(email));
        }
    }


    @Override
    public void checkIfBtoBUser(String email) {
        User user = getUserByEmail(email);
        if (!user.isBToB()) {
            throw new HttpCustomException(ApiErrorCodes.IS_NOT_B2B_USER, new ErrorsResponse().error(email));
        }
    }

    @Override
    public UserDto getUserInfo(String email) {
        User user = getUserByEmail(email);
        Company company = companyRepository.findByCodeAndIsDeletedFalse(user.getLastConnectedCompany())
                .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.NO_COMPANIES_FOR_USER));
        return UserConverter.modelToDto(user,company.getId());
    }

    private void checkIfStarkUser(String email) {
        User user = getUserByEmail(email);
        if (user.isBToB()) {
            throw new HttpCustomException(ApiErrorCodes.IS_NOT_STARK_USER, new ErrorsResponse().error(email));
        }
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.USER_WITH_EMAIL_NOT_FOUND,
                        new ErrorsResponse().error(email)));
    }

    private void checkCompany(User user) {
        List<UserCompany> userCompanies = userCompanyRepository.findByUser_IdAndIsDeletedFalse(user.getId());
        if (user.getLastConnectedCompany() != null && !userCompanies.isEmpty()) {
            Company lastConnectedCompany = companyRepository.findByCodeAndIsDeletedFalse(user.getLastConnectedCompany())
                    .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.NO_COMPANIES_FOR_USER));

            Optional<UserCompany> lastUserCompany = userCompanies.stream()
                    .filter(userCompany -> userCompany.getCompany().getCode().equals(lastConnectedCompany.getCode()))
                    .findAny();
            Optional<CompanyLicence> lastCompanyLicence = companyLicenceRepository
                    .findByCompanyIdAndIsDeletedFalse(lastConnectedCompany.getId());

            if (!(lastUserCompany.isPresent() && lastUserCompany.get().isActif() && lastCompanyLicence.isPresent()
                    && (lastCompanyLicence.get().getExpirationDate() == null
                    || lastCompanyLicence.get().getExpirationDate().isAfter(LocalDateTime.now())))) {
                getDefaultCompany(userCompanies);
            }
        } else if (!userCompanies.isEmpty()) {
            getDefaultCompany(userCompanies);
        } else {
            throw new HttpCustomException(ApiErrorCodes.NO_COMPANIES_FOR_USER);
        }
    }

    private void getDefaultCompany(List<UserCompany> userCompanies) {
        List<Company> companies = userCompanies.stream().filter(UserCompany::isActif).map(UserCompany::getCompany)
                .collect(Collectors.toList());
        if (companies.isEmpty()) {
            throw new HttpCustomException(ApiErrorCodes.NO_COMPANIES_FOR_USER);
        } else {
            Optional<Company> companyToConnect = companies.stream().filter(company -> {
                Optional<CompanyLicence> companyLicence = companyLicenceRepository
                        .findByCompanyIdAndIsDeletedFalse(company.getId());
                return companyLicence.isPresent() && (companyLicence.get().getExpirationDate() == null
                        || companyLicence.get().getExpirationDate().isAfter(LocalDateTime.now()));
            }).findFirst();
            if (!companyToConnect.isPresent()) {
                throw new HttpCustomException(ApiErrorCodes.NO_COMPANIES_FOR_USER,
                        new ErrorsResponse().error(ApiErrorCodes.NO_COMPANIES_FOR_USER));
            }
        }

    }

    private void checkUserHasAuthorities(String email) {
        User user = getUserByEmail(email);
        Set<String> authorities = new HashSet<>();
        List<Role> roles = roleUserRepository.findByUser_Id(user.getId()).stream().map(RoleUser::getRole).collect(Collectors.toList());
        roles.forEach(role -> {
            List<Permission> permissions = rolePermissionRepository.findByRole_Id(role.getId()).stream().map(RolePermission::getPermission).collect(Collectors.toList());
            permissions.forEach(permission -> authorities.add(permission.getCode()));
        });
        if (authorities.isEmpty()) {
            throw new HttpCustomException(ApiErrorCodes.CURRENT_USER_DO_NOT_HAVE_ANY_PERMISSIONS,
                    new ErrorsResponse().error(ApiErrorCodes.CURRENT_USER_DO_NOT_HAVE_ANY_PERMISSIONS));
        }
    }

    public Boolean checkCertificate(String societeName , String private_key) throws Exception {
        Boolean ret = false;
        Date now = new Date();
        KeyStore p12 = KeyStore.getInstance("JKS");
        p12.load(new FileInputStream(pathLicense+ societeName + ".keystore"), private_key.toCharArray());
        Enumeration<String> e = p12.aliases();
        while (e.hasMoreElements()) {
            String alias = e.nextElement();
            X509Certificate c = (X509Certificate) p12.getCertificate(alias);
            if (now.before(c.getNotAfter()))
                ret = true;
        }
        return ret;
    }

}
