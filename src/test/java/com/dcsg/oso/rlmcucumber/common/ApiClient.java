package com.dcsg.oso.rlmcucumber.common;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

import com.dcsg.oso.rlmcucumber.config.ApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class ApiClient {
  @Autowired private ApplicationContext context;

  @Autowired private ObjectMapper mapper;

  @Autowired private Utility utility;

  public void executePut(String entity, String url) {
    executePut(entity, url, null, null);
  }

  public void executePut(String entity, String url, Map<String, Object> queryParams) {
    executePut(entity, url, null, queryParams);
  }

  public void executePut(
      String entity, String url, Map<String, Object> pathParams, Map<String, Object> queryParams) {
    final RequestSpecification request = context.getRequest();
    final Object payload = context.get(entity);
    final Object header = context.getHeader();

    setHeaders(request, header);
    setQueryParams(request, queryParams);
    setPathParams(request, pathParams);
    setPayload(request, payload);

    Response response = request.accept(ContentType.JSON).log().all().put(url);
    context.set(entity, response);
  }

  public void executePostText(String entity, String url, Map<String, Object> queryParams) {
    executePostText(entity, url, null, queryParams);
  }

  public void executePostText(
      String entity, String url, Map<String, Object> pathParams, Map<String, Object> queryParams) {
    final RequestSpecification request = context.getRequest();
    final Object payload = context.get(entity);
    final Object header = context.getHeader();

    setHeaders(request, header);
    setQueryParams(request, queryParams);
    setPathParams(request, pathParams);
    request.body(payload);
    request.contentType(ContentType.TEXT);
    Response response = request.accept(ContentType.ANY).log().all().post(url);
    context.set(entity, response);
  }

  public void executePost(String entity, String url) {
    executePost(entity, url, null, null);
  }

  public void executePost(String entity, String url, Map<String, Object> queryParams) {
    executePost(entity, url, null, queryParams);
  }

  public void executePost(
      String entity, String url, Map<String, Object> pathParams, Map<String, Object> queryParams) {
    final RequestSpecification request = context.getRequest();
    final Object payload = context.get(entity);
    final Object header = context.getHeader();

    setHeaders(request, header);
    setQueryParams(request, queryParams);
    setPathParams(request, pathParams);
    setPayload(request, payload);

    Response response = request.accept(ContentType.JSON).log().all().post(url);
    context.set(entity, response);
  }

  public void executeUpload(String entity, String url, File file) {
    final RequestSpecification request = context.getRequest();
    final Object header = context.getHeader();

    setHeaders(request, header);

    Response response =
        request.accept(ContentType.JSON).log().all().multiPart("file-data", file).post(url);
    context.set(entity, response);
  }

  public void executeDelete(String entity, String url, Map<String, Object> pathParams) {
    executeDelete(entity, url, pathParams, null);
  }

  public void executeDelete(
      String entity, String url, Map<String, Object> pathParams, Map<String, Object> queryParams) {
    final RequestSpecification request = context.getRequest();
    final Object header = context.getHeader();
    setHeaders(request, header);
    setQueryParams(request, queryParams);
    setPathParams(request, pathParams);

    Response response = request.accept(ContentType.JSON).log().all().delete(url);
  }

  public void executeGet(String entity, String url, Map<String, Object> pathParams) {
    executeGet(entity, url, pathParams, null);
  }

  public void executeGet(
      String entity, String url, Map<String, Object> pathParams, Map<String, Object> queryParams) {
    final RequestSpecification request = context.getRequest();
    final Object header = context.getHeader();

    setHeaders(request, header);
    setQueryParams(request, queryParams);
    setPathParams(request, pathParams);

    Response response = request.accept(ContentType.JSON).log().all().get(url);
    context.set(entity, response);
  }

  /** Set the Headers in http request */
  private void setHeaders(RequestSpecification request, Object headers) {
    if (headers != null) {
      Map<String, Object> header = mapper.convertValue(headers, Map.class);
      request.headers(header);
    }
  }

  /** Set the queryParams in http request */
  private void setQueryParams(RequestSpecification request, Map<String, Object> queryParams) {
    if (queryParams != null) {
      request.queryParams(queryParams);
    }
  }

  /** Set the pathParams in http request */
  private void setPathParams(RequestSpecification request, Map<String, Object> pathParams) {
    if (pathParams != null) {
      request.pathParams(pathParams);
    }
  }

  /** Set the payload in http request */
  private void setPayload(RequestSpecification request, Object payload) {
    if (payload != null) {
      request.contentType(ContentType.JSON).body(payload);
    }
  }
}
