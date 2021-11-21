Feature: Dummy API Test

  Background:
    Given that param "base_url.url" is set to value "http://dummy.restapiexample.com/api/v1"

  Scenario: Create/Select/Update/Delete the Employee Data
#***************** Create *****************
    Given that param "headers.Content-Type" is set to value "application/json"
    When I read the JSON from file "/src/test/resources/testData/post.json" into Dictionary Key "PostCall"
    When I make a "POST" REST Call with URL "/create" and Body from Dictionary Key "PostCall"
    Then I verify that the response code is "200" for the response with Dictionary Key "PostCall"
  #***************** Get *****************
    When I make a "GET" REST Call with URI "/employee/1" from Dictionary Key "GetCall"
    Then I verify that the response code is "200" for the response with Dictionary Key "GetCall"
    #***************** Put *****************
    When I read the JSON from file "/src/test/resources/testData/put.json" into Dictionary Key "PutCall"
    When I make a "PUT" REST Call with URL "/update/21" and Body from Dictionary Key "PutCall"
    Then I verify that the response code is "200" for the response with Dictionary Key "PutCall"
     #***************** Delete *****************
    When I read the JSON from file "/src/test/resources/testData/put.json" into Dictionary Key "DeleteCall"
    When I make a "DELETE" REST Call with URL "/delete/2" and Body from Dictionary Key "DeleteCall"
    Then I verify that the response code is "200" for the response with Dictionary Key "DeleteCall"