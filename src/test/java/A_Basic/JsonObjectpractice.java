package API_practice;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class JsonObjectpractice {
    public static void main(String[] args) throws IOException, ParseException {
        FileReader fr=new FileReader("src/test/java/input_Json/reqBodyForPrac.json");
        JSONParser jp=new JSONParser();
       String reqBody=jp.parse(fr).toString();

        System.out.println(reqBody);

        JSONObject js=new JSONObject(reqBody);

       String id= js.getJSONArray("students").getJSONObject(0).get("id").toString();
        System.out.println(id);

       String id2= js.getJSONArray("students").getJSONObject(1).get("id").toString();
        System.out.println(id2);

        String id3=js.getJSONArray("students").getJSONObject(2).get("id").toString();
        System.out.println(id3);

       String prc= js.getJSONArray("students").getJSONObject(2).getJSONObject("city").get("previous").toString();
        System.out.println(prc);

        String in=js.getJSONArray("students").getJSONObject(2).get("IN").toString();
        System.out.println(in);

        String marks=js.getJSONArray("students").getJSONObject(2).getJSONArray("marks").getJSONObject(0).get("firstMock").toString();
        System.out.println(marks);
    }
}
