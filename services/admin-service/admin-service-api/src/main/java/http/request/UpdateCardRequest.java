package http.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UpdateCardRequest {

    @JsonProperty("card_uuid")
    private String cardUuid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("price")
    private String price;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("image")
    private String image;

    @JsonProperty("id")
    private Long id;
}
