package com.dcsg.oso.rlmcucumber.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigFileReader {
  private static JsonNode customValue;
  @Autowired private ObjectMapper mapper;

  /** Read from path and return inputStream */
  private InputStream getInputStreamData(String path) {
    return TypeReference.class.getResourceAsStream(path);
  }

  /** Return custom JsonNode */
  public JsonNode getCustomValue(String path) {
    if (customValue == null) {
      InputStream customValueStream = getInputStreamData(path);
      try {
        customValue = mapper.readTree(customValueStream);
        customValueStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return customValue;
  }
}
