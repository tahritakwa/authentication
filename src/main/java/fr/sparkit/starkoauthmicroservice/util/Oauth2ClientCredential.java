package fr.sparkit.starkoauthmicroservice.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oauth2ClientCredential {

    private String clientId;
    private String clientSecret;
}
