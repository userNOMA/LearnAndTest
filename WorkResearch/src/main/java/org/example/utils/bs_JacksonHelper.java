package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class bs_JacksonHelper implements bs_JsonHelper.JsonHelper {
    private static final Logger log = LoggerFactory.getLogger(bs_JacksonHelper.class);
    private ObjectMapper objectMapper = null;

    public bs_JacksonHelper() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String toJsonString(Object value) {
        String result = "";
        if (value == null) {
            return result;
        } else {
            try {
                result = value instanceof String ? (String)value : this.objectMapper.writeValueAsString(value);
            } catch (JsonProcessingException var4) {
                log.error("parse error, {}", var4);
            }

            return result;
        }
    }

    public <T> T fromJson(String jsonStr, Class<T> valueType) {
        T t = null;
        if (jsonStr != null && jsonStr.length() != 0 && valueType != null) {
            try {
                t = valueType.equals(String.class) ? (T)jsonStr : this.objectMapper.readValue(jsonStr, valueType);
            } catch (IOException var5) {
                log.error("parse error, {}", var5);
            }

            return t;
        } else {
            return t;
        }
    }
}