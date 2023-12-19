package utilities;

import common.CommonClass;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtil extends CommonClass {
    public String getDataFromJson(String scenarioName, String key) {
        String path = System.getProperty("user.dir") + "/src/test/resources/data/json/input.json";
        try {
            String text = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
            JSONObject obj = new JSONObject(text);
            JSONObject profileData = obj.getJSONObject(scenarioName);
            return profileData.getString(key);
        } catch (Exception ex) {
            logger.info("An Exception has occurred " + ex);
            return null;
        }
    }
}
