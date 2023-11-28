package com.dcsg.oso.rlmcucumber.common;

import com.dcsg.oso.rlmcucumber.RlmCucumberApplication;
import com.dcsg.oso.rlmcucumber.config.ApplicationContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(
    classes = RlmCucumberApplication.class,
    loader = SpringBootContextLoader.class)
@Slf4j
public class CucumberSpringConfiguration {

  @Autowired ApplicationContext context;

  @Before
  public void setUp(Scenario scenario) {
    //      setScenario
    context.setScenario(scenario);
    log.info("Before Scenario : " + scenario.getName());
  }

  @After
  public void tearUp(Scenario scenario) {
    log.info("After Scenario : " + scenario.getName());
    context.reset();
  }
}
