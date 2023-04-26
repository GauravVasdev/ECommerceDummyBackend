package http.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorizationFormRequest {

    @JsonProperty("token")
    private String token;

    @JsonProperty("uri")
    private String uri;

}
