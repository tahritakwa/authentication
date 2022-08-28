package fr.sparkit.starkoauthmicroservice.controller;
import fr.sparkit.starkoauthmicroservice.dto.AuthEnvironmentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthBuildPropertiesController {

    @Value("${tomcat.server.url}")
    private String authServerUrl;

    private BuildProperties buildProperties;

    @Autowired
    public AuthBuildPropertiesController(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }


    @GetMapping("build-properties")
    public AuthEnvironmentDto getBuildProperties() {
        return AuthEnvironmentDto.builder().buildVersion(buildProperties.getVersion()).authServerUrl(authServerUrl).build();
    }
}