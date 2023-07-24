package Data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalances {
    @JsonProperty("AccountBalances")
    private String accountBalances;

    @JsonProperty("Status")
    private String status;
}
