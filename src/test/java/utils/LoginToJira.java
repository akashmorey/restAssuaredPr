package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.json.Json;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LoginToJira {
    @Test
    public void login() throws IOException, ParseException {
        FileReader fr=new FileReader("src/test/java/input_Json/loginRequestBody.json");
        JSONParser jp=new JSONParser();
       String reqBody=jp.parse(fr).toString();

       Response response= RestAssured.given().baseUri(ReadInputs.readJsonInputs("URL")).body(reqBody).contentType(ContentType.JSON)
                .when().post("/rest/auth/1/session")
                .then().log().body().extract().response();
        System.out.println(response.asString());
       // JSONObject js=new JSONObject("");

    }
}
