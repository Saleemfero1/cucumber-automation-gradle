package com.dcsg.oso.rlmcucumber;

import static com.dcsg.oso.rlmcucumber.utility.VersionGenerate.*;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.sorting.SortingMethod;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"com.dcsg.oso.rlmcucumber"},
    plugin = {"pretty", "json:target/cucumber-report.json", "html:target/cucumber-report.html"},
    monochrome = true,
    tags = "@test",
    stepNotifications = true)
class RlmCucumberApplicationTests {

  private static final String PROJECT_NAME = "RLM";
  private static String buildNumber = null;
  private static final String BRANCH_NAME = "master";
  protected static final String INITIAL_FOLDER_PATH = "target/Reports/";
  protected static final String FILE_NAME = "Version";
  private static String finalFolderPath;

  @AfterClass
  public static void teardown() throws IOException {
    buildNumber = "0.0." + generateBuildNumber();
    finalFolderPath = folderCreation(INITIAL_FOLDER_PATH, FILE_NAME);
    File reportOutputDirectory = new File(finalFolderPath);
    System.out.println("teardown method");
    List<String> jsonFiles = new ArrayList<>();
    jsonFiles.add("target/cucumber-report.json");

    // set values from respective build tool
    Configuration configuration = new Configuration(reportOutputDirectory, PROJECT_NAME);

    configuration.setBuildNumber(buildNumber);
    configuration.addClassifications("Branch Name", BRANCH_NAME);
    configuration.addClassifications("Platform", "Windows");
    configuration.setSortingMethod(SortingMethod.NATURAL);
    configuration.setTrendsStatsFile(new File("target/demo-trends.json"));
    ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
    reportBuilder.generateReports();
  }
}
