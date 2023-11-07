package org.example.utils;

import java.io.Serializable;

public class bs_JsonShadow implements Serializable, Cloneable {
    private bs_JsonShadow() {
        if (JsonHolder.INSTANCE != null) {
            throw new RuntimeException("不允许私自实例化对象！");
        }
    }

    private bs_JsonHelper.JsonHelper readResolve() {
        return JsonHolder.INSTANCE;
    }

    protected Object clone() {
        return JsonHolder.INSTANCE;
    }

    public static bs_JsonHelper.JsonHelper getInstance() {
        return JsonHolder.INSTANCE;
    }

    private static class JsonHolder {
        private static final bs_JsonHelper.JsonHelper INSTANCE = new bs_JacksonHelper();

        private JsonHolder() {
        }
    }
}