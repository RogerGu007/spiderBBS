package com.school.utils;

import com.google.gson.Gson;

public class GsonUtils {

    public static String toGsonString(Object gson) {
        return new Gson().toJson(gson);
    }

    public static <T> T fromGsonString(String str, Class<T> classOfT) {
        return new Gson().fromJson(str, classOfT);
    }
}
