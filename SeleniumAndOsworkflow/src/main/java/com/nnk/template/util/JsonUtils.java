package com.nnk.template.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/30
 * Time: 10:41
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class JsonUtils {
    /**
     *
     * @param json source json string
     * @param keyname key.key1.key2
     * @return the jsonObject
     */
    public static Object getObject(String json,String keyname){
        Map map = JSONObject.parseObject(json, Map.class);
        return getObject(map,keyname);
    }

    /**
     *
     * @param json source json string
     * @return map
     */
    public static Map phareToMap(String json){
        Map map = JSONObject.parseObject(json, Map.class);
        return map;
    }

    /**
     *
     * @param jsonMap map for json
     * @param keyname key.key1.key2
     * @return
     */
    public static Object getObject(Map jsonMap,String keyname){
        String[] keynames = keyname.split(".");
        if(keynames.length==1){
            return jsonMap.get(keynames[0]);
        }
        Object object = jsonMap.get(keynames[0]);
        JSONObject jsonObject = null;
        if(object instanceof JSONObject){
            jsonObject = (JSONObject) object;
        }
        for(int i = 1;i <=keynames.length;i++){
            jsonObject = (JSONObject) jsonObject.get(keynames[i]);
        }
        return jsonObject;
    }
}
