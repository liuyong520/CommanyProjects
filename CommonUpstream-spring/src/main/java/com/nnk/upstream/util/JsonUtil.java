package com.nnk.upstream.util;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;

public class JsonUtil {

    public static Map<String, String> buildMap(String json) throws RuntimeException{
        try {
            GsonBuilder gb = new GsonBuilder();
            Gson g = gb.create();
            Map<String, String> map = g.fromJson(json, new TypeToken<Map<String, String>>() {
            }.getType());
            return map;
        }catch (Exception e){
            throw new RuntimeException("json ");
        }
    }

    public static String buildJson(Object object) throws RuntimeException{
        try {
            GsonBuilder gb = new GsonBuilder();
            Gson g = gb.create();
            return g.toJson(object);
        }catch (Exception e){
            throw new RuntimeException("json ");
        }
    }

    public static <T> T buildObject(String json, Class<T> clazz) throws RuntimeException{
        try{
            GsonBuilder gb = new GsonBuilder();
            Gson g = gb.create();
            return g.fromJson(json, clazz);}catch (Exception e){
            throw new RuntimeException("json ");
        }

    }

}
