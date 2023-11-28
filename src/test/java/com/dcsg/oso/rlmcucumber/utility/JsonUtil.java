package com.dcsg.oso.rlmcucumber.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

    private static final Logger log = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static <T> T convertToObject(String jsonString, Class<T> classType) {
        T obj = null;

        try {
            obj = objectMapper.readValue(jsonString, classType);
        } catch (Exception var4) {
            log.error("Unable to convert json string to required class type {} ", jsonString, var4);
        }

        return obj;
    }

    public static String convert(Object object) {
        try {
            return object == null ? null : objectMapper.writeValueAsString(object);
        } catch (Exception var2) {
            log.error("Unable to convert object to json string {}", object, var2);
            return null;
        }
    }
}
