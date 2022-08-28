package fr.sparkit.starkoauthmicroservice.util;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import fr.sparkit.starkoauthmicroservice.util.errors.ApiErrorCodes;
import fr.sparkit.starkoauthmicroservice.util.http.HttpCustomException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import static fr.sparkit.starkoauthmicroservice.constants.SharedConstants.*;

@Component
public class JwtUtils {


    private JWTClaimsSet parseToken(String token) {
        if(token.startsWith(BEARER)){
            token = token.replace(BEARER, "");
        }
        try {
            JWT jwt = JWTParser.parse(token);
            return jwt.getJWTClaimsSet();
        } catch (ParseException e) {
            throw new HttpCustomException(ApiErrorCodes.INVALID_TOKEN);
        }
    }

    public String getEmailFromToken(String token) {
        JWTClaimsSet jwtClaimsSet = parseToken(token);
        Map<String, Object> userTokenMap = (Map<String, Object>) jwtClaimsSet.getClaims().get(USER);
        return (String) userTokenMap.get(TOKEN_EMAIL_KEY);
    }

    public String getLastConnectedCompanyFromToken(String token) {
        JWTClaimsSet jwtClaimsSet = parseToken(token);
        Map<String, Object> userTokenMap = (Map<String, Object>) jwtClaimsSet.getClaims().get(USER);
        return (String) userTokenMap.get(TOKEN_LAST_CONNECTED_COMPANY_KEY);
    }

    public String getLastConnectedCompanyCodeFromToken(String token) {
        JWTClaimsSet jwtClaimsSet = parseToken(token);
        Map<String, Object> userTokenMap = (Map<String, Object>) jwtClaimsSet.getClaims().get(USER);
        return (String) userTokenMap.get(LAST_CONNECTED_COMPANY_CODE);

    }

    public Date getTokenExpirationDate(String token) {
        JWTClaimsSet jwtClaimsSet = parseToken(token);
        return (Date) jwtClaimsSet.getClaims().get(TOKEN_EXPIRATION_DATE_KEY);
    }

    public boolean isTierUser(String token) {
        JWTClaimsSet jwtClaimsSet = parseToken(token);
        return (boolean) jwtClaimsSet.getClaims().get(TIERS_USER);
    }


    public String getUsernameFromToken(String token){
        JWTClaimsSet jwtClaimsSet = parseToken(token);
        return (String)jwtClaimsSet.getClaim("user_name");
    }




}
