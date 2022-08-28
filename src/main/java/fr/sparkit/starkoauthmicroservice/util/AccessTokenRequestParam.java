package fr.sparkit.starkoauthmicroservice.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenRequestParam {

    private Credential credential;
    private Oauth2ClientCredential oauth2ClientCredential;
}
