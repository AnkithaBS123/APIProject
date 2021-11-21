package stepDefinitions.API;

import GlobalVariables.Global;
import apiUtility.apiEngine;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import io.restassured.path.json.JsonPath;
import gherkin.deps.com.google.gson.JsonArray;


public class apiStepDefinitions {
    apiEngine ae = new apiEngine();

    @Given("^that param \"([^\"]*)\" is set to value \"([^\"]*)\"$")
    public void that_param_is_set_to_value(String dictionaryKey, String value) {

        String[] paramCategory = dictionaryKey.split("\\.");

        switch (paramCategory[0]) {
            case "headers":
                Global.headers.put(paramCategory[1], value);
                break;
            case "base_url":
                Global.globalVariables.put(paramCategory[1], value);
                break;
        }
    }
    @When("^I make a \"([^\"]*)\" REST Call with URI \"([^\"]*)\" and store the response with Dictionary Key \"([^\"]*)\"$")
    public void i_make_a_REST_Call_with_URI_and_store_the_response_with_Dictionary_Key(String requestType, String URI, String dictionaryKey) throws Throwable {
        Response rs = ae.callAPI(requestType, Global.headers, "", Global.globalVariables.get("url").toString() + URI);
        Global.globalVariables.put(dictionaryKey,rs);
    }

    @Then("^I verify that the response code is \"([^\"]*)\" for the response with Dictionary Key \"([^\"]*)\"$")
    public void i_verify_that_the_response_code_is_for_the_response_with_Dictionary_Key(String responseCode, String dictionaryKey) {
        Response rs = (Response) Global.globalVariables.get(dictionaryKey);
        System.out.println("response value" +rs.asString());
//        System.out.println("get id" +rs.jsonPath().getString("id"));
        Assert.assertEquals("Actual Response Code is " + rs.getStatusCode() + " vs Expected Response Code" + responseCode, String.valueOf(rs.getStatusCode()), responseCode);

    }

    @Then("^I verify that the json path \"([^\"]*)\" for node \"([^\"]*)\"  value is \"([^\"]*)\" for the response with Dictionary Key \"([^\"]*)\"$")
    public void i_verify_that_the_json_path_for_node_value_is_for_the_response_with_Dictionary_Key(String jsonPath, String valueOfNode, String dictionaryKey) throws Throwable {
        valueOfNode = valueOfNode.replace(";qt;", "\"");
        Response rs = (Response) Global.globalVariables.get(dictionaryKey);
        System.out.println(ae.searchJsonStringByJsonPath(rs.asString(), jsonPath));
        System.out.println(valueOfNode);
        Assert.assertEquals("Json Path node not found", ae.searchJsonStringByJsonPath(rs.asString(), jsonPath), valueOfNode);
    }


    @Then("^I verify that the json path \"([^\"]*)\" for node array \"([^\"]*)\" contains \"([^\"]*)\" number of nodes for the response with Dictionary Key \"([^\"]*)\"$")
    public void i_verify_that_the_json_path_for_node_array_contains_number_of_nodes_for_the_response_with_Dictionary_Key(String jsonPath, String numberOfNodes, String dictionaryKey) throws Throwable {
        Response rs = (Response) Global.globalVariables.get(dictionaryKey);
        JsonArray ja = ae.jsonStringtoJsonArray(rs.asString(), jsonPath);
        Assert.assertEquals("Number of of elements is " + ja.size() + " vs Expected Value of " + numberOfNodes, Integer.parseInt(numberOfNodes), ja.size());
    }

    @When("^I read the JSON from file \"([^\"]*)\" into Dictionary Key \"([^\"]*)\"$")
    public void i_read_the_JSON_from_file_into_Dictionary_Key(String jsonFilePath, String dictionaryKey) throws Throwable {
        String cwd = System.getProperty("user.dir");
        System.out.println("current directory "+cwd);
        String jsonPath=cwd+jsonFilePath;
        System.out.println("actual directory "+jsonPath);
        Global.globalVariables.put(dictionaryKey, ae.jsonFileToString(jsonPath));
    }

    @When("^I make a \"([^\"]*)\" REST Call with URL \"([^\"]*)\" and Body from Dictionary Key \"([^\"]*)\"$")
    public void i_make_a_REST_Call_with_URL_and_Body_from_Dictionary_Key(String callType, String URI, String dictionaryKey) throws Throwable {
        Response rs = ae.callAPI(callType, Global.headers, Global.globalVariables.get(dictionaryKey).toString(), Global.globalVariables.get("url").toString() + URI );
        Global.globalVariables.put(dictionaryKey, rs);
    }

    @Then("^I add the value at json path \"([^\"]*)\" from response with Dictionary Key \"([^\"]*)\" and store it in Dictionary Key \"([^\"]*)\"$")
    public void i_add_the_value_at_json_path_from_response_with_Dictionary_Key_and_store_it_in_Dictionary_Key(String jsonPath, String sourceDictionary, String destDictionary) throws Throwable {
        Response rs = (Response) Global.globalVariables.get(sourceDictionary);
        Global.globalVariables.put(destDictionary,ae.searchJsonStringByJsonPath(rs.asString(), jsonPath));

    }
    @When("^I make a \"([^\"]*)\" REST Call with URI \"([^\"]*)\" from Dictionary Key \"([^\"]*)\"$")
    public void i_make_a_REST_Call_with_URI_and_store_the_response_ofSessionID_with_Dictionary_Key(String requestType, String URI, String dictionaryKey) throws Throwable {
        Response rs = ae.callAPI("get", Global.headers, "", Global.globalVariables.get("url").toString() + URI);
        Global.globalVariables.put(dictionaryKey,rs);
    }


}
