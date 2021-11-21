package apiUtility;

import gherkin.deps.com.google.gson.*;
import io.restassured.response.Response;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class apiEngine {

    public Response callAPI(String ApiType, HashMap<String, String> headers, String body, String uri) throws URISyntaxException {

        Response res=null;

        switch(ApiType.toLowerCase()){
            case "get":
                res = given().headers(headers).when().get(new URI(uri)).then().extract().response();
                break;

            case "post":
                res = given().when().headers(headers).body(body).post(uri);
                break;

            case "put":
                res = given().headers(headers).when().headers(headers).body(body).put(uri);
                break;
            case "patch":
                res = given().headers(headers).when().headers(headers).body(body).patch(uri);
                break;
            case "delete":
                res = given().headers(headers).when().headers(headers).body(body).delete(uri);
                break;
        }

        return res;
    }
    public String searchJsonStringByJsonPath(String jsonString ,String jsonPath) throws JsonSyntaxException, IOException {
        DocumentContext parsedDataContext =  JsonPath.parse(jsonString);
        parsedDataContext.read(jsonPath);
        return (parsedDataContext.read(jsonPath).toString());
    }

    public JsonArray jsonStringtoJsonArray(String jsonFullString ,String jsonPathOfArrayNode) throws JsonSyntaxException, IOException {
        jsonFullString = searchJsonStringByJsonPath(jsonFullString, jsonPathOfArrayNode);
        JsonParser parser = new JsonParser();
        JsonElement elements = parser.parse(jsonFullString);
        JsonArray jsonArray = elements.getAsJsonArray();
        return jsonArray;
    }
    public String jsonFileToString(String jsonFilePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(jsonFilePath));
        String str = "";
        String st;
        while (true) {
            st = br.readLine();
            if(st==null) {
                break;
            }else {
                str = str + st;
            }
        }
        br.close();
        return str;
    }
}
