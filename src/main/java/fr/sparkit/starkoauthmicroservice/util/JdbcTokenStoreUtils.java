package fr.sparkit.starkoauthmicroservice.util;

import fr.sparkit.starkoauthmicroservice.dto.UserRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JdbcTokenStoreUtils {

    private String selectUserNameQuery = "select user_name from [dbo].[oauth_access_token] where user_name = ?";
    private String selectRefreshTokenQuery = "select token_id, token from oauth_refresh_token where token_id in "
            + "(SELECT  refresh_token FROM oauth_access_token where user_name = ?) ";
    private String selectAccessTokenQuery = "select token_id , token from oauth_access_token where user_name = ?";
    private String selectAuthenticationQuery = "select token_id, authentication from oauth_refresh_token where token_id in "
            + "(SELECT  refresh_token FROM oauth_access_token where user_name = ?) ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DefaultTokenServices defaultTokenServices;


    public boolean revokeToken(UserRequestDto userRequestDto) {
        try {
            if(userRequestDto.getAccessToken()!=null){
                return defaultTokenServices.revokeToken(userRequestDto.getAccessToken());
            }else{
                OAuth2AccessToken queryResult = selectAccessTokenByUsername(userRequestDto.getEmail());
                assert queryResult != null;
                return defaultTokenServices.revokeToken(queryResult.getValue());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public String selectUsernameQuery(String email) {
        return jdbcTemplate.queryForObject(selectUserNameQuery, new Object[]{email},
                (rs, rowNum) -> rs.getString("user_name"));
    }

    public OAuth2RefreshToken selectRefreshTokenByUsername(String username) {
        return jdbcTemplate.queryForObject(selectRefreshTokenQuery,
                new Object[]{username}, (rs, rowNum) -> deserializeRefreshToken(rs.getBytes(2)));
    }

    public OAuth2Authentication selectAuthenticationForRefreshTokenByUsername(String username){
        return this.jdbcTemplate.queryForObject(this.selectAuthenticationQuery,new Object[]{username}, (rs , rowNum) -> deserializeAuthentication(rs.getBytes(2)));
    }


    public OAuth2AccessToken selectAccessTokenByUsername(String username){
        return jdbcTemplate.queryForObject(selectAccessTokenQuery,new Object[]{username},(rs, rowNum)->deserializeAccessToken(rs.getBytes(2)));
    }


    private OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
        return (OAuth2RefreshToken)SerializationUtils.deserialize(token);
    }

    private OAuth2AccessToken deserializeAccessToken(byte [] token) {
        return (OAuth2AccessToken)SerializationUtils.deserialize(token);
    }

    private OAuth2Authentication deserializeAuthentication(byte[] authentication) {
        return (OAuth2Authentication)SerializationUtils.deserialize(authentication);
    }
}
