package fr.sparkit.starkoauthmicroservice.auditing;

import fr.sparkit.starkoauthmicroservice.service.IUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;

import static fr.sparkit.starkoauthmicroservice.constants.SharedConstants.AUTHORIZATION_HEADER_KEY;

@Aspect
@Configuration
public class AuthAspect {
    private IUserService userService;

    @Autowired
    public AuthAspect(IUserService userService) {
        this.userService = userService;
    }

    @Before("@annotation(fr.sparkit.starkoauthmicroservice.auditing.HasRoles)")
    public void before(JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = CurrentRequestContextHolder.getCurrentHttpServletRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget().getClass().getMethod(signature.getMethod().getName(),
                signature.getMethod().getParameterTypes());
        HasRoles hasRoles = method.getAnnotation(HasRoles.class);
        String token = request.getHeader(AUTHORIZATION_HEADER_KEY);
        CurrentRequestContextHolder.removeCurrentHttpServletRequest();
        if (!userService.checkUserHasRoles(token, Arrays.asList(hasRoles.permissions()))) {
            throw new AccessDeniedException("User doesn't have the proper permissions to access this API");
        }
    }
}
