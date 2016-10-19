package com.nnk.template.util;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/19
 * Time: 14:51
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class MapUtils {
    private static final String JSON = "json";
    private static final String FORM = "str";
    private static final String XML = "xml";
    public static String MaptoHttpData(Map<String,String> map){
       StringBuilder sb = new StringBuilder();
       for(String key:map.keySet()){
            sb.append(key).append("=").append(map.get(key)).append("&");
       }
       return sb.substring(0,sb.length()-1);

   }

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
        if(JSON.equals(protoclType.toLowerCase())){
            map = JsonUtils.phareToMap(resp);
        }else if(FORM.equals(protoclType.toLowerCase())){
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
        }else if(XML.equals(protoclType.toLowerCase())){
            XmlMapPharser mapPharser = new XmlMapPharser();
            map = mapPharser.parse(resp);
        }
        return map;
    }
}
