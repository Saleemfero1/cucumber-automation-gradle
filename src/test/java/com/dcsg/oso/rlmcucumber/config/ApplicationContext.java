package com.dcsg.oso.rlmcucumber.config;

import static io.restassured.RestAssured.given;
import static java.lang.ThreadLocal.withInitial;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Singleton to manage objects and share their state between step definitions. */
@Component
public class ApplicationContext {

  private static final String PAYLOAD = "PAYLOAD";
  private static final String REQUEST = "REQUEST";
  private static final String RESPONSE = "RESPONSE";
  private static final String AUTHORIZATION = "AUTHORIZATION";
  private static final String HEADER = "HEADER";
  private final ThreadLocal<Map<String, Object>> threadLocal = withInitial(HashMap::new);
  @Autowired private ObjectMapper mapper;
  private Scenario scenario;

  public Scenario getScenario() {
    return scenario;
  }

  public void setScenario(Scenario scenario) {
    this.scenario = scenario;
  }

  private Map<String, Object> testContextMap() {
    return threadLocal.get();
  }

  public void set(String key, Object value) {
    testContextMap().put(key, value);
  }

  public Object get(String key) {
    return testContextMap().get(key);
  }

  public Object remove(String key) {
    return testContextMap().remove(key);
  }

  public Boolean contain(String key) {
    return testContextMap().containsKey(key);
  }

  public <T> T get(String key, Class<T> clazz) {
    //        return clazz.cast(testContextMap().get(key));
    return mapper.convertValue(testContextMap().get(key), clazz);
  }

  public Object getAuthorization() {
    return testContextMap().get(AUTHORIZATION);
  }

  public void setAuthorization(Object value) {
    set(AUTHORIZATION, value);
  }

  public Object getHeader() {
    return testContextMap().get(HEADER);
  }

  public void setHeader(Object value) {
    set(HEADER, value);
  }

  public Object getPayload() {
    return testContextMap().get(PAYLOAD);
  }

  public void setPayload(Object value) {
    set(PAYLOAD, value);
  }

  public <T> T getPayload(Class<T> clazz) {
    return get(PAYLOAD, clazz);
  }

  public RequestSpecification getRequest() {
    RequestSpecification req = get(REQUEST, RequestSpecification.class);
    return (null == req) ? given() : req;
  }

  public Response getResponse() {
    return get(RESPONSE, Response.class);
  }

  public void setResponse(Response response) {
    set(RESPONSE, response);
  }

  public Boolean contains(String key) {
    return testContextMap().containsKey(key);
  }

  public void reset() {
    testContextMap().clear();
  }
}
