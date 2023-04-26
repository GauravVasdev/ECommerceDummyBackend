package http.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class UpdateCardResponse {

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private String price;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("image")
    private String image;
}