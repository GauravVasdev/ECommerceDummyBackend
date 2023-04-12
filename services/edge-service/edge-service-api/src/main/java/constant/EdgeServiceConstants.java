package constant;

public class EdgeServiceConstants {

    public static final String LOGIN_URL = "/users/login";

    public static final String[] IGNORE_END_POINTS = {LOGIN_URL};

    public static final String GET_ORG_ID = "/organization/get/{orgId}";

    public static final String VALIDATE_TOKEN_URL = "/users/authenticate-token";

    public static final String ACCESS_DENIED = "ACCESS DENIED";
    public static final String VALIDATE_ROLE = "/users/authorize-role";

}
