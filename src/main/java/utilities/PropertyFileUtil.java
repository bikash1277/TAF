package utilities;

import common.CommonClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtil extends CommonClass {
    public Properties prop;

    private static Properties configFile;

    static {
        try {
            FileInputStream fileInputStream = new FileInputStream("config.properties");
            configFile = new Properties();
            configFile.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            logger.info("An Exception has occurred " + e);
        }
    }

    public static String getProperty(String key) {
        return configFile.getProperty(key);
    }
}
