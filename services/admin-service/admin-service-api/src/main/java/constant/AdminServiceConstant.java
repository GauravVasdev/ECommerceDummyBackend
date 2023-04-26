package constant;

public class AdminServiceConstant {
    public static final String BASE_URL = "/admin";


    public static final String CREATE_BASE_URL = "/create";

    public static final String GET_BASE_URL = "/get";

    public static final String UPDATE_BASE_URL = "/update";

    public static final String DELETE_BASE_URL = "/delete";

    public static final String CREATE_CARD_URL = CREATE_BASE_URL+"/create-card";

    public static final String GET_CARD_URL = GET_BASE_URL+"/get-card/{cardUuid}";

    public static final String UPDATE_CARD_URL = UPDATE_BASE_URL+"/update-card";

    public static final String DELETE_CARD_URL = DELETE_BASE_URL+"/delete-card/{cardUuid}";
    public static final String GET_ALL_CARD_URL = GET_BASE_URL + "/getAllCard";
}
