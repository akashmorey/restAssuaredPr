package A_Basic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class prctice {
    public static String url="http://localhost:9009";
    public static String cookieValue="";

   public static String issueId="";
    @Test

    public void loginToJira(){
            Response response = RestAssured.given().baseUri(url).body("{\n" +
                            "\"username\": \"akash\",\n" +
                            "\"password\": \"akash\" \n" +
                            "}").contentType(ContentType.JSON)
                    .when().post("/rest/auth/1/session")
                    .then().log().all().extract().response();
            System.out.println(response.asString());

            JSONObject js=new JSONObject(response.asString());
            cookieValue="JSESSIONID="+js.getJSONObject("session").get("value").toString();
    }
    @Test(priority = 1)
    public void addBug(){
        Response response=RestAssured.given().baseUri(url).body("{\n" +
                "\"fields\": {\n" +
                "\"project\": {\n" +
                "\"key\": \"AM\"\n" +
                "},\n" +
                "\"summary\": \"Add new bub with the help of Automation\",\n" +
                "\"issuetype\": {\n" +
                "\"name\": \"Bug\"\n" +
                "}\n" +
                "}\n" +
                "}").contentType(ContentType.JSON).header("Cookie",cookieValue)
                .when().post("/rest/api/2/issue")
                .then().log().body().extract().response();

        System.out.println(response.asString());

        JSONObject js=new JSONObject(response.asString());
        issueId= js.get("key").toString();
        System.out.println(issueId);
    }
    @Test(priority = 2)
    public void getBug(){
       Response response=RestAssured.given().baseUri(url).contentType(ContentType.JSON).header("Cookie",cookieValue)
               .when().get("/rest/api/2/issue/"+issueId)
               .then().log().body().extract().response();
        System.out.println(response.getStatusCode());
    }
}
