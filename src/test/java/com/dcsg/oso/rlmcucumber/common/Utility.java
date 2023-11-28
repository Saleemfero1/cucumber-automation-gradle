package com.dcsg.oso.rlmcucumber.common;

import com.dcsg.oso.rlmcucumber.config.ApplicationContext;
import com.dcsg.oso.rlmcucumber.config.ConfigFileReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import java.util.*;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Utility {

  @Autowired private ApplicationContext context;

  @Autowired private ObjectMapper mapper;

  @Autowired private ConfigFileReader configFileReader;

  /** Capture the log on console */
  public void logResponse(Response response) {
    response.then().log().all();
  }

  /** Capture the log on Cucumber Json */
  public void logOnReport(Object payload, Response response) {

    Scenario scenario = context.getScenario();

    try {
      if (payload != null)
        scenario.attach(
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload).getBytes(),
            "application/json",
            "Request");
      else scenario.attach("no payload".getBytes(), "application/json", "Request");
      if (response != null)
        scenario.attach(
            response.getBody().prettyPrint().getBytes(), "application/json", "Response");
      else scenario.attach("no Response".getBytes(), "application/json", "Response");
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  /** Capture the log on Cucumber Json */
  public void logOnReport(Object expected, Object actual) {

    Scenario scenario = context.getScenario();

    try {
      if (expected != null)
        scenario.attach(
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expected).getBytes(),
            "application/json",
            "Expected");
      else scenario.attach("no payload".getBytes(), "application/json", "Expected");
      if (actual != null)
        scenario.attach(
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(actual).getBytes(),
            "application/json",
            "Actual");
      else scenario.attach("no Response".getBytes(), "application/json", "Actual");
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  /** Capture the log on Cucumber Json */
  public void logOnReport(Object payload, String placeHolder) {
    Scenario scenario = context.getScenario();

    try {
      if (payload != null)
        scenario.attach(
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload).getBytes(),
            "application/json",
            placeHolder);
      else scenario.attach("no payload".getBytes(), "application/json", placeHolder);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  public List<Map<String, String>> convertDataTableValues(List<Map<String, String>> dataTables, String path) {
    List<Map<String, String>> convertedDataTables = new ArrayList<>();
    for (Map<String, String> dataTable : dataTables) {
      Map<String, String> convertedDataTable = new LinkedHashMap<>();
      for (String key : dataTable.keySet()) {
        Object value = null;
        value = loadCustomValue(dataTable.get(key) != null ? dataTable.get(key) : "", path);
        convertedDataTable.put(key, value != null ? value.toString() : null);
      }
      convertedDataTables.add(convertedDataTable);
    }
    return convertedDataTables;
  }

  /** Set value to the specific key return the map */
  public Map<String, Object> setMapValue(Map<String, Object> map, String key, Object value, String path) {
    if (key == null) return map;
    if (map == null) map = new LinkedHashMap<>();
    List<String> keys = new ArrayList<String>(Arrays.asList(key.split(Pattern.quote("."))));
    if (keys.size() > 1) {
      String k = keys.get(0);
      Map<String, Object> child;
      if (map.containsKey(k)) {
        child = mapper.convertValue(map.get(k), Map.class);
      } else {
        child = new LinkedHashMap<>();
      }
      keys.remove(k);
      map.put(k, setMapValue(child, String.join(".", keys), value, path));
      return map;
    } else if (map.containsKey(keys.get(0))) {
      return setCustomMapValue(map, value, keys, path);
    } else {
      return setCustomMapValue(map, value, keys, path);
    }
  }

  private Map<String, Object> setCustomMapValue(
      Map<String, Object> map, Object value, List<String> keys, String path) {
    JsonNode json = mapper.convertValue(map, JsonNode.class);
    value = value.toString().isEmpty() ? null : value;
    Object changedValue = value;
    if (value != null && (value.toString().contains("{"))) {
      changedValue = loadCustomValue(value.toString(), path);
    }
    if (changedValue != null && json.has(keys.get(0)) && json.get(keys.get(0)).isArray()) {
      List<String> arrayValue =
          new ArrayList<>(Arrays.asList(changedValue.toString().split(Pattern.quote(","))));
      map.put(keys.get(0), arrayValue);
    } else {
      map.put(keys.get(0), changedValue);
    }
    return map;
  }

  /** Parse the value return custom String value */
  public Object loadCustomValue(String customValueKey, String path) {
    String changedValue = null;
    JsonNode customValue;
    List<String> customValueKeys =
        new ArrayList<String>(Arrays.asList(customValueKey.split(Pattern.quote("+"))));
    for (String valueKey : customValueKeys) {
      if (valueKey.contains("{")) {
        String key = valueKey.replaceAll(("[\\{\\}]"), "");
        List<String> keys = new ArrayList<String>(Arrays.asList(key.split(Pattern.quote("."))));
        customValue = configFileReader.getCustomValue(path);
        //                keys.remove(0);

        if (keys.size() > 1) {
          Object obj = customValue;
          for (String k : keys) {
            JsonNode json = mapper.convertValue(obj, JsonNode.class);
            if (json.has(k)) obj = json.get(k);
            else return null;
          }
          String val = mapper.convertValue(obj, JsonNode.class).toString().replaceAll("\"", "");
          changedValue = changedValue == null ? val : changedValue.concat(val);
        } else if (customValue.has(keys.get(0))) {
          changedValue =
              changedValue == null
                  ? customValue.get(keys.get(0)).toString().replaceAll("\"", "")
                  : changedValue.concat(
                      customValue.get(keys.get(0)).toString().replaceAll("\"", ""));
        }
      } else {
        changedValue = changedValue == null ? valueKey : changedValue.concat(valueKey);
      }
    }
    return changedValue;
  }

  /** return value from json */
  public String getMapValue(JsonNode json, String key) {
    if (json == null) return null;
    List<String> keys = new ArrayList<String>(Arrays.asList(key.split(Pattern.quote("."))));
    if (keys.size() > 1) {
      Object obj = json;
      for (String k : keys) {
        JsonNode jsonNode = mapper.convertValue(obj, JsonNode.class);
        if (jsonNode.has(k)) obj = jsonNode.path(k);
        else return null;
      }
      return obj == null ? null : obj.toString().replaceAll("\"", "");
    } else {
      Object obj = json.get(key);
      return obj == null ? null : obj.toString().replaceAll("\"", "");
    }
  }

//  public JsonNode getCustomValue() {
//    return configFileReader.getCustomValue();
//  }

  public void waitForMinutes(int delay) {
    try {
      Thread.sleep(delay * 60000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void waitForSeconds(int delay) {
    try {
      Thread.sleep(delay * 10000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
