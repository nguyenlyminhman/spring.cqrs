package com.lab.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Common {
    public static Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").setPrettyPrinting().create();

    public static Object toModel(final String jsonString, final Class<?> clazz) {
        try {
            return gson.fromJson(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error");
        }
    }
}
