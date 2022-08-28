package fr.sparkit.starkoauthmicroservice;

import fr.sparkit.starkoauthmicroservice.auditing.RequestInterceptor;
import fr.sparkit.starkoauthmicroservice.util.UserInterceptorService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestInterceptor requestInterceptor;
    private final UserInterceptorService userInterceptorService;

    public WebConfig(RequestInterceptor requestInterceptor, UserInterceptorService userInterceptorService) {
        this.requestInterceptor = requestInterceptor;
        this.userInterceptorService = userInterceptorService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestInterceptor).addPathPatterns("/api/**");
        registry.addInterceptor(userInterceptorService).addPathPatterns("/api/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
