package utils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JSONReader {

    public static String get(String key) {

        String json = readJson("/Users/macbookpro/IdeaProjects/DistributedStream/src/data/connectionInformation.json");

        JSONObject obj = new JSONObject(json);
        return obj.getString(key);

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
