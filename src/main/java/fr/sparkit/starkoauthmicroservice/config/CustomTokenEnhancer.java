package fr.sparkit.starkoauthmicroservice.config;

import fr.sparkit.starkoauthmicroservice.convertor.UserConverter;
import fr.sparkit.starkoauthmicroservice.dao.CompanyLicenceRepository;
import fr.sparkit.starkoauthmicroservice.dao.CompanyRepository;
import fr.sparkit.starkoauthmicroservice.dao.UserCompanyRepository;
import fr.sparkit.starkoauthmicroservice.dao.UserRepository;
import fr.sparkit.starkoauthmicroservice.model.Company;
import fr.sparkit.starkoauthmicroservice.model.CompanyLicence;
import fr.sparkit.starkoauthmicroservice.model.User;
import fr.sparkit.starkoauthmicroservice.model.UserCompany;
import fr.sparkit.starkoauthmicroservice.util.errors.ApiErrorCodes;
import fr.sparkit.starkoauthmicroservice.util.errors.ErrorsResponse;
import fr.sparkit.starkoauthmicroservice.util.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomTokenEnhancer extends JwtAccessTokenConverter {
    private static final String DEFAULT_LANGUAGE = "fr";
    private static final String LANGUAGE = "language";
    private static final String USER = "user";
    private static final String TOKEN_CREATE_DATE = "token_create_date";
    private static final String IS_TIER_USER = "isTiersUser";

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private UserCompanyRepository userCompanyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyLicenceRepository companyLicenceRepository;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Map<String, Object> info = new LinkedHashMap<>(accessToken.getAdditionalInformation());
        List<UserCompany> userCompanies = userCompanyRepository.findByUser_IdAndIsDeletedFalse(user.getId());
        if (user.getLastConnectedCompany() != null && !userCompanies.isEmpty()) {
            Company lastConnectedCompany = companyRepository.findByCodeAndIsDeletedFalse(user.getLastConnectedCompany())
                    .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.NO_COMPANIES_FOR_USER));

            Optional<UserCompany> lastUserCompany = userCompanies.stream()
                    .filter(userCompany -> userCompany.getCompany().getCode().equals(lastConnectedCompany.getCode()))
                    .findAny();
            Optional<CompanyLicence> lastCompanyLicence = companyLicenceRepository
                    .findByCompanyIdAndIsDeletedFalse(lastConnectedCompany.getId());

            if (lastUserCompany.isPresent() && lastUserCompany.get().isActif() && lastCompanyLicence.isPresent()
                    && (lastCompanyLicence.get().getExpirationDate() == null
                    || lastCompanyLicence.get().getExpirationDate().isAfter(LocalDateTime.now()))) {

                info.put(USER, UserConverter.modelToDto(user, lastConnectedCompany.getId()));

            } else {
                getDefaultCompany(userCompanies, user, info);
            }
        } else if (!userCompanies.isEmpty()) {
            getDefaultCompany(userCompanies, user, info);
        } else {
            throw new HttpCustomException(ApiErrorCodes.NO_COMPANIES_FOR_USER);
        }

        Company lastConnectedCompany = companyRepository.findByCodeAndIsDeletedFalse(user.getLastConnectedCompany())
                .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.NO_COMPANIES_FOR_USER));
        info.put(USER, UserConverter.modelToDto(user, lastConnectedCompany.getId()));
        String language = user.getLanguage() != null ? user.getLanguage() : DEFAULT_LANGUAGE;
        info.put(LANGUAGE, language);
        info.put(TOKEN_CREATE_DATE, new Date(System.currentTimeMillis()));
        info.put(IS_TIER_USER, user.isBToB());
        log.info(info.toString());
        DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
        customAccessToken.setAdditionalInformation(info);
        return super.enhance(customAccessToken, authentication);
    }

    private void getDefaultCompany(List<UserCompany> userCompanies, User user, Map<String, Object> info) {
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
            if (companyToConnect.isPresent()) {
                user.setLastConnectedCompany(companyToConnect.get().getCode());
                userRepository.saveAndFlush(user);
                info.put(USER, UserConverter.modelToDto(user, companyToConnect.get().getId()));

            } else {
                throw new HttpCustomException(ApiErrorCodes.NO_COMPANIES_FOR_USER,
                        new ErrorsResponse().error(ApiErrorCodes.NO_COMPANIES_FOR_USER));
            }
        }

    }
}
