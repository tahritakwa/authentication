package fr.sparkit.starkoauthmicroservice.constants;

public final class SharedConstants {
    public static final String PROJECT_NAME = "ProjectName";
    public static final String AUTHORIZATION_TYPE = "Basic ";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String LAST_CONNECTED_COMPANY_CODE = "LastConnectedCompany";
    public static final String TOKEN_EMAIL_KEY = "Email";
    public static final String TOKEN_LAST_CONNECTED_COMPANY_KEY = "LastConnectedCompany";
    public static final String TOKEN_EXPIRATION_DATE_KEY = "exp";
    public static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String GRANT_TYPE = "grant_type";
    public static final String B2B_AUTHORITY = "B2B_ADMIN";
    public static final String TIERS_USER = "isTiersUser";
    public static final String USER = "user";
    public static final String BEARER = "Bearer ";

    private SharedConstants() {
        super();
    }
}
