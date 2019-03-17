package utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONReader {

    public static String get(String key) {

        String json = readJson("/Users/macbookpro/IdeaProjects/DistributedStream/src/data/connectionInformation.json");

        JSONObject obj;
        String result = "";
        try {
            obj = new JSONObject(json);
            result = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;

    }

    private static String readJson(String jsonPath) {

        String json = "";

        try {
            json = Files.readAllLines(Paths.get(jsonPath)).stream().reduce((a, b) -> a + b).orElse("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }

}
