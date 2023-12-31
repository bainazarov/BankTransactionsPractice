package data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CredLimitHistories {
    @JsonProperty("CreditLimitChange")
    private String credditLimitChange;
    @JsonProperty("CreditLimitDate")
    private CreditLimitDate creditLimitDate;
}
