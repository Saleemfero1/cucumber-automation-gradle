package com.dcsg.oso.rlmcucumber.common;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

import com.dcsg.oso.rlmcucumber.config.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class StepDefUtility {

  @Autowired private ApiClient apiClient;
  @Autowired private ApplicationContext context;
  @Autowired private Properties properties;
  @Autowired private Utility utility;
}
