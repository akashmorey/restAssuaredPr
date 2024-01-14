package B_Standerd;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;


public class E2E_User_Story {
    public static String url="http://localhost:9009";
    public static String cookieValue="";
    public static String issueId="";
    @Test
    public void loginToJira() throws IOException, ParseException {

        FileReader fr=new FileReader("src/test/java/input_Json/loginRequestBody.json");
        JSONParser jp=new JSONParser();
        String reqbody=jp.parse(fr).toString();

        Response response= RestAssured.given().baseUri(url).body(reqbody).contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response();
        System.out.println(response.asString());

        JSONObject js= new JSONObject(response.asString());
        cookieValue="JSESSIONID="+js.getJSONObject("session").get("value").toString();

        System.out.println(cookieValue);
    }

    @Test (priority = 1)
    public void createUserStory() throws IOException, ParseException {
        FileReader fr=new FileReader("src/test/java/input_Json/CreateUserStoryReqBody.json");
        JSONParser jp=new JSONParser();
        String reqBody=jp.parse(fr).toString();

        Response response=RestAssured.given().baseUri(url).body(reqBody).contentType(ContentType.JSON).header("Cookie",cookieValue)
                .when().post("/rest/api/2/issue")
                .then().log().all().extract().response();
        System.out.println(response.asString());

        JSONObject js=new JSONObject(response.asString());
        issueId=js.get("key").toString();

    }
    @Test(priority = 3)
    public void updateUserStoryWithStanderdReqBody() throws IOException, ParseException {
        FileReader fr=new FileReader("src/test/java/input_Json/UpdateUserStoryReqBody.json");
        JSONParser jp=new JSONParser();
       String reqBdy= jp.parse(fr).toString();
        System.out.println(reqBdy);


       Response response=RestAssured.given().baseUri(url).body(reqBdy).contentType(ContentType.JSON).header("Cookie",cookieValue)
               .when().put("/rest/api/2/issue/"+issueId)
               .then().log().all().extract().response();
        System.out.println(response.asString());
    }
    @Test(priority = 4)
    public void get_updated_User_Story(){
        Response response=RestAssured.given().baseUri(url).contentType(ContentType.JSON).header("Cookie",cookieValue)
                .queryParam("Fields","summary").queryParam("fields","status")
                .when().get("/rest/api/2/issue/"+issueId)
                .then().log().all().extract().response();
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());
    }
    }
