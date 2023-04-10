package constant;

public class UserServiceConstant {

    public static final String BASE_URL ="/users";
    public static final String CREATE_BASE_URL ="/create";
    public static final String CREATE_USER_URL = CREATE_BASE_URL + "-user";

    public static final String UPDATE_BASE_URL ="/update";
    public static final String UPDATE_USER_URL = UPDATE_BASE_URL + "-user";
    public static final String DELETE_BASE_URL ="/delete";
    public static final String DELETE_USER_URL = DELETE_BASE_URL + "-user/{uuid}";
    public static final String GET_BASE_URL ="/get";
    public static final String GET_USER_URL = GET_BASE_URL + "-all-user";
    public static final String LOGIN_URL = "/login";
}
