package tk.teamfield3.jTTD.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigUtil {

    public static String getProperty(String file, String property) {
        Properties properties = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream(file);
            properties.load(input);

            return properties.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setProperty(String file, String property, String value) {
        Properties properties = new Properties();
        OutputStream output = null;

        properties.setProperty(property, value);

        try {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
