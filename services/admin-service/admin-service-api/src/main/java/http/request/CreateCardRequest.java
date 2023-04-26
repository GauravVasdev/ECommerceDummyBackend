package http.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateCardRequest {

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private String price;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("image")
    private String image;
}
