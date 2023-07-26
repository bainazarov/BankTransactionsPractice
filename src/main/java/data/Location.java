package data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    @JsonProperty("HouseNumber")
    private String houseNumber;
    @JsonProperty("Street")
    private String street;
    @JsonProperty("PostTown")
    private String postTown;
    @JsonProperty("Country")
    private String country;
    @JsonProperty("Postcode")
    private String postcode;
}
