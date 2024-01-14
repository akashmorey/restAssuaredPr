package advance;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties {
    public static String readProperty(String propertyKey) throws IOException {
        FileReader fr=new FileReader("src/test/java/utils/global.properties");
        Properties prop=new Properties();
        prop.load(fr);
        String URL= prop.getProperty(propertyKey);
        return URL;
    }
}
