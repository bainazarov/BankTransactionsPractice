package Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Consumer {

    @JsonProperty("ConsumerData")
    private ConsumerData consumerData;

}
