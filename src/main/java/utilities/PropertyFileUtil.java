package utilities;

import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtil {
    public Properties prop;
    public final Logger logger = Logger.getLogger(getClass().getSimpleName());
    private static Properties configFile;

    public PropertyFileUtil() {
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
