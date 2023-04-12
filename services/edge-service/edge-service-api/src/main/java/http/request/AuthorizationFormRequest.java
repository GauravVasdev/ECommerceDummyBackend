package http.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class AuthorizationFormRequest {

    @JsonProperty("token")
    private String token;

    @JsonProperty("uri")
    private String uri;
}