package com.dcsg.oso.rlmcucumber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.dcsg.oso")
public class RlmCucumberApplication {

  public static void main(String[] args) {
    SpringApplication.run(RlmCucumberApplication.class, args);
  }
}
