package deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeserializerAmount extends JsonDeserializer<Double> {
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("£([\\d.]+)");

    @Override
    public Double deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String amount = jsonParser.getValueAsString();
        Matcher matcher = AMOUNT_PATTERN.matcher(amount);

        if(matcher.matches()) {
            String amountValue = matcher.group(1);
            return Double.parseDouble(amountValue);
        } return 0.0;
    }
}
