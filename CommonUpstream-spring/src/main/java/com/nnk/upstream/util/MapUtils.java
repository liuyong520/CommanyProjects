package com.nnk.upstream.util;

import com.alibaba.fastjson.JSONObject;
import com.nnk.upstream.entity.proxy.ProctolTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/2
 * Time: 11:42
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class MapUtils {

    public static Object getObject(Map map,String keyname){
        String[] keynames = keyname.split("\\.");
        if(keynames.length==1){
            return map.get(keynames[0]);
        }
        Object object = map.get(keynames[0]);
        JSONObject jsonObject = null;
        if(object instanceof JSONObject) {
            jsonObject = (JSONObject) object;
            for(int i = 1;i <=keynames.length;i++){
                object = jsonObject.get(keynames[i]);
                if(object instanceof JSONObject){
                    jsonObject = (JSONObject) object;
                }else {
                    return object;
                }
            }
            return jsonObject;
        }else if(object instanceof Map){
            Map map1 = (Map) object;
            Object object1 = null;
            for(int i = 1;i <keynames.length;i++){
                object1 = map1.get(keynames[i]);
                if(object1 instanceof Map){
                    Map result = (Map) object1;
                    map1 = result;
                }
            }
            return object1;
        }
        return null;
   }

    /**
     * <p>parse the response's string to a map</p>
     * @param resp response's string
     * @param protoclType response's protoclType
     * @return a map of result
     */
    public static Map getResultMap(String resp, String protoclType) {
        Map map = null;
        if(ProctolTypeEnum.JSON.getName().equals(protoclType)){
            map = JsonUtils.phareToMap(resp);
        }else if(ProctolTypeEnum.FORM.getName().equals(protoclType)){
            map = new HashMap();
            String[] contents = resp.split("[=&|~!@#$%^&*:,\"]+");
            if(resp.contains("=")&&resp.contains("&")&&!resp.matches(".*[~!@#$%^&*:,\"]+.*")){
                int i=0;
                for (;i<contents.length;i=i+2){
                    String key = contents[i];
                    String value =contents[i+1];
                    map.put(key,value);
                }
            }else {
                int i = 0;
                for (String content:contents){

                    String key = "Str-index"+i;
                    map.put(key,content);
                    i++;
                }
            }
        }else if(ProctolTypeEnum.XML.getName().equals(protoclType)){
            XmlMapPharser mapPharser = new XmlMapPharser();
            map = mapPharser.parse(resp);
        }
        return map;
    }
}
