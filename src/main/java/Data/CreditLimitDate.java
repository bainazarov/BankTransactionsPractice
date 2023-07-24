package Data;

import Deserializers.DeserializerMM;
import Deserializers.DeserializerYY;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

public class CreditLimitDate {
    @JsonProperty("CreditLimitDateMM")
    @JsonDeserialize(using = DeserializerMM.class)
    private LocalDate creditLimitDateMM;
    @JsonProperty("CreditLimitDateYY")
    @JsonDeserialize(using = DeserializerYY.class)
    private LocalDate creditLimitDateYY;
}
