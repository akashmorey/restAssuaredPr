package A_Basic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.Test;

public class LoginToJira {
    public static String cookievalue="";
    public static String issueId="";
    public static String url="http://localhost:9009";
    @Test
    public void loginToJira(){
        Response response = RestAssured.given().baseUri(url).body("{\n" +
                        "\"username\": \"akash\",\n" +
                        "\"password\": \"akash\" \n" +
                        "}").contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().extract().response();
        System.out.println(response.getStatusCode());

        JSONObject js=new JSONObject(response.asString());
        cookievalue="JSESSIONID="+js.getJSONObject("session").get("value").toString();
    }
    @Test (priority = 1)
    public void add_user_Story(){
      Response resopnse= RestAssured.given().baseUri(url).body("{\n" +
                "\"fields\": {\n" +
                "\"project\": {\n" +
                "\"key\": \"AM\"\n" +
                "},\n" +
                "\"summary\": \"Add user srory with the help of rest Asssuered\",\n" +
                "\"issuetype\": {\n" +
                "\"name\": \"Story\"\n" +
                "}\n" +
                "}\n" +
                "}").contentType(ContentType.JSON).header("Cookie",cookievalue)
              .when().post("/rest/api/2/issue")
              .then().log().all().extract().response();
        System.out.println(resopnse.getStatusCode());


            JSONObject js=new JSONObject(resopnse.asString());
            issueId=js.get("key").toString();
    }
    @Test (priority = 2)
    public void getValue(){
        System.out.println(cookievalue);
    }

    @Test (priority = 3)
    public void get_Added_User_Story(){
       Response response= RestAssured.given().baseUri(url).contentType(ContentType.JSON).header("Cookie",cookievalue)
               .queryParam("fields","summary").queryParam("fields","status").queryParam("fields","priority")
                .when().get("/rest/api/2/issue/"+issueId)
                .then().log().all().extract().response();

        System.out.println(response.asString());
    }
    @Test (priority = 4)
    public void update_user_Story(){
       Response response= RestAssured.given().baseUri(url).body("{\n" +
                "\"fields\": {\n" +
                "\"project\": {\n" +
                "\"key\": \"AM\"\n" +
                "},\n" +
                "\"summary\": \"Update Added user Story \",\n" +
                "\"issuetype\": {\n" +
                "\"name\": \"Story\"\n" +
                "}\n" +
                "}\n" +
                "}").contentType(ContentType.JSON).header("Cookie",cookievalue)
                .when().put("/rest/api/2/issue/"+issueId)
                .then().log().all().extract().response();

        System.out.println(response.asString());
        System.out.println(response.getStatusCode());
    }
    @Test(priority = 5)
    public void get_updated_User_Story(){
        Response response=RestAssured.given().baseUri(url).contentType(ContentType.JSON).header("Cookie",cookievalue)
                .queryParam("Fields","summary").queryParam("fields","status")
                .when().get("/rest/api/2/issue/"+issueId)
                .then().log().all().extract().response();
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());
    }

    @Test(priority = 6)
    public void delete_added_User_Story(){
        Response response=RestAssured.given().baseUri(url).contentType(ContentType.JSON).header("Cookie",cookievalue)
                .when().delete("/rest/api/2/issue/"+issueId)
                .then().extract().response();
        System.out.println(response.asString());
        System.out.println(response.getStatusCode());
    }

}
