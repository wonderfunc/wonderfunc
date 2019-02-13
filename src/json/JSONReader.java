package json;

import org.json.JSONObject;

public class JSONReader {

    public static String read(String key) {

        JSONObject obj = new JSONObject("{\"sourceUnitIP\":\"localhost\", \"sourUnitPort\":\"8081\"}");
        return obj.getString(key);

    }

}
