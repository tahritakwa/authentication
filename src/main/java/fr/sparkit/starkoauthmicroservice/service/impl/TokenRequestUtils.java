package fr.sparkit.starkoauthmicroservice.service.impl;

import fr.sparkit.starkoauthmicroservice.util.Credential;
import fr.sparkit.starkoauthmicroservice.util.Oauth2ClientCredential;
import fr.sparkit.starkoauthmicroservice.util.RefreshToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

import static fr.sparkit.starkoauthmicroservice.constants.SharedConstants.*;

@Service
public final class TokenRequestUtils {

    @Value("${access_token_grant_type}")
    private String accessTokenGrantType;

    @Value("${refresh_token_grant_type}")
    private String refreshTokenGrantType;

    @Value(("${config.oauth2.accessTokenUri}"))
    private String accessTokenUri;


    @Autowired
    private RestTemplate restTemplate;

    public Object accessTokenRequest(Credential credential, Oauth2ClientCredential clientCredential) {

        HttpHeaders headers = prepareHeaderToRetrieveAccessToken(clientCredential);
        String requestBody = USERNAME + "=" + credential.getEmail() + "&" + PASSWORD + "=" + credential.getPassword()
                + "&" + GRANT_TYPE + "=" + accessTokenGrantType;
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(accessTokenUri, httpEntity, Object.class);
    }

    public Object refreshTokenRequest(RefreshToken refreshToken, Oauth2ClientCredential clientCredential) {
        HttpHeaders headers = prepareHeaderToRetrieveAccessToken(clientCredential);
        String requestBody = GRANT_TYPE + "=" + refreshTokenGrantType + "&" + REFRESH_TOKEN + "="
                + refreshToken.getRefreshToken();
        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
        return restTemplate.postForEntity(accessTokenUri, httpEntity, Object.class);
    }

    private HttpHeaders prepareHeaderToRetrieveAccessToken(Oauth2ClientCredential clientCredential) {
        String encodedClientIdClientSecret = Base64.getEncoder()
                .encodeToString((clientCredential.getClientId() + ":" + clientCredential.getClientSecret()).getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set(AUTHORIZATION_HEADER_KEY, AUTHORIZATION_TYPE + encodedClientIdClientSecret);
        return headers;
    }

}
