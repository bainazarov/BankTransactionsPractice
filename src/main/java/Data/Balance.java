package Data;

import Deserializers.DeserializerAmount;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
@Data
public class Balance {
    @JsonProperty("Amount")
    @JsonDeserialize(using = DeserializerAmount.class)
    private double amount;
    @JsonProperty("Narrative")
    private String narrative;
}
