package utils;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class ReadInputs {
    public static String readJsonInputs(String path) throws IOException, ParseException {
        FileReader fr=new FileReader(path);
        JSONParser jp=new JSONParser();
       String reqBody=jp.parse(fr).toString();
       return reqBody;
    }
}
