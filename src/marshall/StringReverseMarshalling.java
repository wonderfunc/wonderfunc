package marshall;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StringReverseMarshalling implements Marshalling<String, String> {

    @Override
    public String marshall(String messageData) {
        return "{" +
                "\"string\": \" " + messageData +" \"" +
                "}";
    }

    @Override
    public String unmarshall(String output) {
        String reversedString = "";
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(output, Map.class);
            LinkedHashMap body = (LinkedHashMap) map.get("body");
            reversedString = ((String) body.get("reversed_str")).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reversedString;
    }
}
