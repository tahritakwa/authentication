package fr.sparkit.starkoauthmicroservice.util.errors;

public final class ApiErrorCodes {
    public static final int PERMISSION_CODE_EXISTS = 40001;
    public static final int SUB_MODULE_CODE_EXISTS = 40002;
    public static final int PERMISSION_USED_IN_ROLE = 40003;
    public static final int NO_COMPANIES_FOR_USER = 40004;
    public static final int INVALID_TOKEN = 40005;
    public static final int USER_WITH_EMAIL_NOT_FOUND = 40006;
    public static final int COMPANY_WITH_ID_NOT_FOUND = 40007;
    public static final int COMPANY_WITH_CODE_NOT_FOUND = 40008;
    public static final int SUB_MODULE_WITH_ID_NOT_FOUND = 40009;
    public static final int NO_ROLE_WITH_ID = 40010;
    public static final int CURRENT_ROLE_IN_USE = 40011;
    public static final int IS_NOT_B2B_USER = 40012;
    public static final int USER_ALREADY_LOGGED_IN = 40013;
    public static final int USER_WITH_EMAIL_NOT_ACTIVE = 40014;
    public static final int IS_NOT_STARK_USER = 40015;
    public static final int CURRENT_USER_DO_NOT_HAVE_ANY_PERMISSIONS = 40016;
    public static final int TOKEN_REGISTRATION_ERROR = 40017;

    public static final int INVALID_LICENSE = 40018;

    private ApiErrorCodes() {
        super();
    }
}
