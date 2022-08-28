package fr.sparkit.starkoauthmicroservice.util;

import fr.sparkit.starkoauthmicroservice.dao.CompanyRepository;
import fr.sparkit.starkoauthmicroservice.dao.UserCompanyRepository;
import fr.sparkit.starkoauthmicroservice.dao.UserRepository;
import fr.sparkit.starkoauthmicroservice.model.Company;
import fr.sparkit.starkoauthmicroservice.model.User;
import fr.sparkit.starkoauthmicroservice.model.UserCompany;
import fr.sparkit.starkoauthmicroservice.util.errors.ApiErrorCodes;
import fr.sparkit.starkoauthmicroservice.util.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class UserInterceptorService extends HandlerInterceptorAdapter {


    private UserRepository userRepository;
    private UserCompanyRepository userCompanyRepository;
    private CompanyRepository companyRepository;
    private JwtUtils jwtUtils;

    @Autowired
    public UserInterceptorService(UserRepository userRepository, UserCompanyRepository userCompanyRepository, CompanyRepository companyRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.userCompanyRepository = userCompanyRepository;
        this.companyRepository = companyRepository;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && !jwtUtils.isTierUser(token)) {
            String email = jwtUtils.getEmailFromToken(token);
            User user = userRepository.findByEmailAndIsDeletedFalse(email)
                    .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.USER_WITH_EMAIL_NOT_FOUND));
            String lastConnectedCompanyCode = request.getHeader("Company") != null ? request.getHeader("Company") : jwtUtils.getLastConnectedCompanyCodeFromToken(token);
            Company company = companyRepository.findByCodeAndIsDeletedFalse(lastConnectedCompanyCode)
                    .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.COMPANY_WITH_CODE_NOT_FOUND));
            UserCompany userCompany = userCompanyRepository.findByUser_IdAndCompany_IdAndIsDeletedFalse(user.getId(), company.getId())
                    .orElseThrow(() -> new HttpCustomException(ApiErrorCodes.USER_WITH_EMAIL_NOT_ACTIVE));
            if (userCompany.isActif()) {
                return super.preHandle(request, response, handler);
            } else {
                log.error("User not active {}", user);
                throw new HttpCustomException(ApiErrorCodes.USER_WITH_EMAIL_NOT_ACTIVE);
            }
        } else {
            return super.preHandle(request, response, handler);
        }

    }
}
