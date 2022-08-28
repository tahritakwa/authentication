package fr.sparkit.starkoauthmicroservice.config;

import fr.sparkit.starkoauthmicroservice.util.JdbcTokenStoreUtils;
import fr.sparkit.starkoauthmicroservice.util.JwtUtils;
import fr.sparkit.starkoauthmicroservice.util.errors.ApiErrorCodes;
import fr.sparkit.starkoauthmicroservice.util.errors.ErrorsResponse;
import fr.sparkit.starkoauthmicroservice.util.http.HttpCustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;


@Component("customJdbcTokenStore")
@Slf4j
public class CustomJdbcTokenStore extends JdbcTokenStore {

    private String insertAccessTokenSql = "insert into oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";
    private String selectRefreshTokenSql = "select token_id, token from oauth_refresh_token where token_id = ?";
    private String selectRefreshTokenAuthenticationSql = "select token_id, authentication from oauth_refresh_token where token_id = ?";
    private String selectAccessTokenSql = "select token_id, token from oauth_access_token where token_id = ?";
    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    private JdbcTokenStoreUtils jdbcTokenStoreUtils;

    @Autowired
    private JwtUtils jwtUtils;

    CustomJdbcTokenStore(DataSource dataSource){
        super(dataSource);
        Assert.notNull(dataSource, "DataSource required");
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    @Override
    public synchronized void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        log.info("Store access token {}",token);
        String refreshToken = null;
        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }
        if (this.readAccessToken(token.getValue()) != null) {
            this.removeAccessToken(token.getValue());
        }

        try{
            insertAccessToken(token,authentication,refreshToken);
        }catch (Exception e){
            log.error("Error occurred while registering the token {}",token);
            this.removeAccessToken(token);
            throw new HttpCustomException(ApiErrorCodes.TOKEN_REGISTRATION_ERROR,new ErrorsResponse().error(authentication.getPrincipal()));
        }
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String token) {
        OAuth2RefreshToken refreshToken = null;
        try {
            refreshToken = (OAuth2RefreshToken)this.jdbcTemplate.queryForObject(this.selectRefreshTokenSql, new RowMapper<OAuth2RefreshToken>() {
                public OAuth2RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return CustomJdbcTokenStore.this.deserializeRefreshToken(rs.getBytes(2));
                }
            }, new Object[]{this.extractTokenKey(token)});
        } catch (EmptyResultDataAccessException var4) {
            try{
                String username = jwtUtils.getUsernameFromToken(token);
                refreshToken = jdbcTokenStoreUtils.selectRefreshTokenByUsername(username);
            }catch (EmptyResultDataAccessException var6){
                if (log.isInfoEnabled()) {
                    log.info("Failed to find refresh token for token " + token);
                }
            }catch (IllegalArgumentException var7){
                log.warn("Failed to deserialize refresh token for token " + token, var7);
                this.removeRefreshToken(token);
            }
            if (log.isInfoEnabled()) {
                log.info("Failed to find refresh token for token " + token);
            }
        } catch (IllegalArgumentException var5) {
            log.warn("Failed to deserialize refresh token for token " + token, var5);
            this.removeRefreshToken(token);
        }

        return refreshToken;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(String refreshTokenValue) {
        OAuth2Authentication authentication = null;

        try {
            authentication = (OAuth2Authentication)this.jdbcTemplate.queryForObject(this.selectRefreshTokenAuthenticationSql, new RowMapper<OAuth2Authentication>() {
                public OAuth2Authentication mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return CustomJdbcTokenStore.this.deserializeAuthentication(rs.getBytes(2));
                }
            }, new Object[]{this.extractTokenKey(refreshTokenValue)});
        } catch (EmptyResultDataAccessException var4) {
            try{
                String username = jwtUtils.getUsernameFromToken(refreshTokenValue);
                authentication = jdbcTokenStoreUtils.selectAuthenticationForRefreshTokenByUsername(username);
            }catch (EmptyResultDataAccessException var6){
                if (log.isInfoEnabled()) {
                    log.info("Failed to find access token for token " + refreshTokenValue);
                }
            }catch (IllegalArgumentException var7){
                log.warn("Failed to deserialize access token for " + refreshTokenValue, var7);
                this.removeRefreshToken(refreshTokenValue);
            }
            if (log.isInfoEnabled()) {
                log.info("Failed to find access token for token " + refreshTokenValue);
            }
        } catch (IllegalArgumentException var5) {
            log.warn("Failed to deserialize access token for " + refreshTokenValue, var5);
            this.removeRefreshToken(refreshTokenValue);
        }

        return authentication;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;

        try {
            accessToken = (OAuth2AccessToken)this.jdbcTemplate.queryForObject(this.selectAccessTokenSql, new RowMapper<OAuth2AccessToken>() {
                public OAuth2AccessToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                    return CustomJdbcTokenStore.this.deserializeAccessToken(rs.getBytes(2));
                }
            }, new Object[]{this.extractTokenKey(tokenValue)});
        } catch (EmptyResultDataAccessException var4) {
            try{
                String username = jwtUtils.getUsernameFromToken(tokenValue);
                accessToken = jdbcTokenStoreUtils.selectAccessTokenByUsername(username);
            }catch (EmptyResultDataAccessException var6){
                if (log.isInfoEnabled()) {
                    log.info("Failed to find access token for token " + tokenValue);
                }
            }catch (IllegalArgumentException var5){
                log.warn("Failed to deserialize access token for " + tokenValue, var5);
                this.removeAccessToken(tokenValue);
            }
            if (log.isInfoEnabled()) {
                log.info("Failed to find access token for token " + tokenValue);
            }
        } catch (IllegalArgumentException var5) {
            log.warn("Failed to deserialize access token for " + tokenValue, var5);
            this.removeAccessToken(tokenValue);
        }

        return accessToken;
    }

    @Override
    public synchronized void removeAccessToken(String tokenValue) {
        super.removeAccessToken(tokenValue);
    }

    synchronized void  insertAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication, String refreshToken){
        this.jdbcTemplate.update(this.insertAccessTokenSql, new Object[]{this.extractTokenKey(token.getValue()),
                new SqlLobValue(this.serializeAccessToken(token)), this.authenticationKeyGenerator.extractKey(authentication), authentication.isClientOnly() ? null : authentication.getName(),
                authentication.getOAuth2Request().getClientId(), new SqlLobValue(this.serializeAuthentication(authentication)), this.extractTokenKey(refreshToken)},
                new int[]{12, 2004, 12, 12, 12, 2004, 12});
    }
}
