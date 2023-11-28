# RLM-cucumber

###  Automation suite for RLM BackEnd
Automation suite to validate various Scenarios.

To learn Cucumber, check out the [Cucumber documentation](https://cucumber.io/).

## Steps to add New Scenario's
```
1. Add scenario's - */src/test/resources/features
create feature file with .feature extension

** Scenario step with parameterized data **

Given Publish FR with following attribute
| frId       | orderNumber   | 
| {ds1.frId} | {ds1.orderId} |
```
```
2. Add respecctive test data in json format - */src/test/resources/data.
** Test Data **

     {
      "ds1": {
      "scenarioName": "Single line Full Shipment Flow - DSCO",
      "frId": "FR0000000102320",
      "orderId": "35300937166"
         }
      }
```
```
3. Add corresponding step in stepDefinition with annotation @Given
- */java/com/dcsg/oso/rlmcucumber/stepdef

@Given("Publish FR with following attribute")
  public void userDetails() {
    
  }
```
4.Make sure the test data key should match with the placeholder in the scenario

```
To Generate the Report scenarios should be executed from TestRunner Class
- */java/com/dcsg/oso/rlmcucumber/RlmCucumberApplicationTests.java

@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.dcsg.oso.rlmcucumber"},
    plugin = {"pretty", "json:target/cucumber-report.json", "html:target/cucumber-report.html"},
    monochrome = true,
    tags = "@test",
    stepNotifications = true)
```
```
## Dependencies

- JDK 17 used in this Gradle project
## Steps to Execute
```

#### CommandLine
      gradlew cucumber -P tags="@IdentifyFlowType" -P v="2"
- tags `execute based on the given tags` -- `excluded @NotExecuted and @CleanUp tagged scenario's by default`
- v `execution version number`
### Report will be generated -  */target/Reports





