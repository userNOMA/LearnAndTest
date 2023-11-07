package org.example.utils;

public class bs_JsonHelper {
    public interface JsonHelper {
        String toJsonString(Object value);

        <T> T fromJson(String jsonStr, Class<T> valueType);
    }
}

