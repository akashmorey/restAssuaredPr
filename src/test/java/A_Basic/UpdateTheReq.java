package A_Basic;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class UpdateTheReq {
    public static void main(String[] args) throws IOException, ParseException {
        FileReader fr=new FileReader("src/test/java/input_Json/reqBodyForPrac.json");
        JSONParser jp=new JSONParser();
        String reqBody=jp.parse(fr).toString();

        System.out.println(reqBody);

        JSONObject js=new JSONObject(reqBody);
       js.getJSONArray("students").getJSONObject(1).getJSONArray("marks").getJSONObject(2).put("thirdMock","11").toString();
        System.out.println(js);
    }
}
