package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONDecoder {

    public static String extractFrom(String rawJSON, String key, String target) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(rawJSON, Map.class);
            LinkedHashMap body = (LinkedHashMap) map.get(key);
            return ((String) body.get(target)).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
