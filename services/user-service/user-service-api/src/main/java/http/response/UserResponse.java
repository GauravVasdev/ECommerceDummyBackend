package http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("user_name")
    private String email;

    @JsonProperty("is_active")
    private boolean isActive;
}
