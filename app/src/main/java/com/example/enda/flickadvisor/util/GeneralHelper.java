package com.example.enda.flickadvisor.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by enda on 22/03/16.
 */
public class GeneralHelper {

    public static String convertObjectToJson(Object object, Type type) {
        Gson gson = new Gson();
        return gson.toJson(object, type);
    }
}
