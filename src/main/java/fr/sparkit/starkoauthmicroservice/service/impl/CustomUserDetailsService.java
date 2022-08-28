package fr.sparkit.starkoauthmicroservice.service.impl;

import fr.sparkit.starkoauthmicroservice.constants.SharedConstants;
import fr.sparkit.starkoauthmicroservice.dao.*;
import fr.sparkit.starkoauthmicroservice.model.*;
import fr.sparkit.starkoauthmicroservice.util.errors.ApiErrorCodes;
import fr.sparkit.starkoauthmicroservice.util.errors.ErrorsResponse;
import fr.sparkit.starkoauthmicroservice.util.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "userDetailsService")
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    private ModuleRepository moduleRepository;
    private SubModuleRepository subModuleRepository;
    private RoleUserRepository roleUserRepository;
    private RolePermissionRepository rolePermissionRepository;

    private final CacheManager cacheManager;


    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, ModuleRepository moduleRepository,
                                    SubModuleRepository subModuleRepository, RoleUserRepository roleUserRepository, RolePermissionRepository rolePermissionRepository, CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
        this.subModuleRepository = subModuleRepository;
        this.roleUserRepository = roleUserRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.cacheManager = cacheManager;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmailAndIsDeletedFalse(username)
                .orElseThrow(() -> new HttpCustomException(500, new ErrorsResponse().error("Bad Credentials")));
        new AccountStatusUserDetailsChecker().check(user);
        if (user.isBToB()) {
            user.setAuthorities(Collections.singletonList(new SimpleGrantedAuthority(SharedConstants.B2B_AUTHORITY)));
        }
        return user;
    }

    @Cacheable(value = "UserAuthoritiesCache", key = "'UserAuthoritiesCache_'+#email")
    public List<String> getAuthorities(String email) {
        log.info("getting authorities for user with email {}", email);
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.USER_WITH_EMAIL_NOT_FOUND));
        return getAuthorities(user).stream().sorted().collect(Collectors.toList());
    }

    @Caching(evict = {@CacheEvict(value = "UserAuthoritiesCache", key = "'UserAuthoritiesCache_'+#email")})
    public void changeUsersCompany(Company company, String email) {
        log.info("changing company  for user with email {} to {}", email, company.getCode());
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.USER_WITH_EMAIL_NOT_FOUND,
                        new ErrorsResponse().error(email)));
        user.setLastConnectedCompany(company.getCode());
        userRepository.saveAndFlush(user);
    }

    private Collection<String> getAuthorities(User user) {
        List<Role> rolesForLastConnectedCompany = roleUserRepository.findByUser_Id(user.getId()).stream().map(RoleUser::getRole)
                .filter((Role role) -> role.getCompany().getCode().equals(user.getLastConnectedCompany()))
                .collect(Collectors.toList());
        return getGrantedAuthorities(rolesForLastConnectedCompany);
    }

    private Collection<String> getGrantedAuthorities(List<Role> roles) {
        Set<String> authorities = new HashSet<>();
        roles.forEach((Role role) -> {
            authorities.add(role.getCode());
            List<Permission> permissions = rolePermissionRepository.findByRole_Id(role.getId()).stream().map(RolePermission::getPermission).collect(Collectors.toList());
            permissions.forEach((Permission permission) -> {
                SubModule subModuleOfPermission = subModuleRepository.findByPermissions_Id(permission.getId());
                Module moduleOfPermission = moduleRepository.findBySubModules_Id(subModuleOfPermission.getId());
                authorities.add(subModuleOfPermission.getCode());
                authorities.add(moduleOfPermission.getCode());
                authorities.add(permission.getCode());
            });
        });
        return authorities;
    }
}
