package fr.sparkit.starkoauthmicroservice;

import fr.sparkit.starkoauthmicroservice.config.RestTemplateResponseErrorHandler;
import fr.sparkit.starkoauthmicroservice.util.SHA512PasswordEncoder;
import lombok.extern.slf4j.Slf4j;
//import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
// @EnableDiscoveryClient
@EnableCaching
@Slf4j
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE , classes = JdbcTokenStore.class)
})
public class StarkOauthMicroserviceApplication extends SpringBootServletInitializer implements CommandLineRunner {


    public static void main(String[] args) {
       // log.info("Starting {} application...\", \"stark-auth-back-end-java\"");
        SpringApplication.run(StarkOauthMicroserviceApplication.class, args);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new SHA512PasswordEncoder();
    }

    @Override
    public void run(String... args) {

    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate;
    }
}
