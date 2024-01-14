package C_Advance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;
import utils.ReadInputs;

import java.io.FileReader;
import java.io.IOException;

public class E2E_User_Story {
    public static String cookievalue="";
    public static String issueId="";
    @Test
    public void loginToJira() throws IOException, ParseException {
        FileReader fr=new FileReader("src/test/java/input_Json/loginRequestBody.json");
        JSONParser jp=new JSONParser();
        String reqBody=jp.parse(fr).toString();

        Response response = RestAssured.given().baseUri(ReadProperties.readProperty("URL")).body(reqBody).contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().extract().response();
        System.out.println(response.getStatusCode());

        JSONObject js=new JSONObject(response.asString());
        cookievalue="JSESSIONID="+js.getJSONObject("session").get("value").toString();
        System.out.println(cookievalue);
    }
    @Test (priority = 1)
    public void add_user_Story() throws IOException, ParseException {
        String reqBody=ReadInputs.readJsonInputs("src/test/java/input_Json/CreateUserStoryReqBody.json");

        JSONObject jsb=new JSONObject(reqBody);
        jsb.getJSONObject("fields").getJSONObject("project").put("summary","add user Story by advanced way");

        Response resopnse= RestAssured.given().baseUri(ReadProperties.readProperty("URL")).body(jsb.toString()).contentType(ContentType.JSON).header("Cookie",cookievalue)
                .when().post("/rest/api/2/issue")
                .then().log().all().extract().response();
        System.out.println(resopnse.getStatusCode());

        JSONObject js=new JSONObject(resopnse.asString());
        issueId=js.get("key").toString();
    }
    @Test(priority = 2)
    public void getUpdatedUserStory() throws IOException {
        Response response=RestAssured.given().baseUri(ReadProperties.readProperty("URL")).contentType(ContentType.JSON).header("Cookie",cookievalue).queryParam("fields","summary")
                .when().get("/rest/api/2/issue/"+issueId)
                .then().log().body().extract().response();

        System.out.println(response.getStatusCode());
    }
}
