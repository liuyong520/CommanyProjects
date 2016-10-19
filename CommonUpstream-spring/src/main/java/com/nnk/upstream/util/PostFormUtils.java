package com.nnk.upstream.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PostFormUtils {
    public static HttpEntity buildEntity(Object obj,String stopString,boolean urlencorde){
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field f:fields){
            try {
                f.setAccessible(true);
                String name = f.getName();
                if(name.equals(stopString)){
                    break;
                }
                String value = (String)f.get(obj);
                if(urlencorde){
                    value = URLEncoder.encode(value, "utf-8");
                }
                formparams.add(new BasicNameValuePair(name,value));

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Charset.forName("utf-8"));
        return entity;
    }
    public static HttpEntity buildEntity(Map<String,String> map,boolean urlencorde,String charset){
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for(Map.Entry<String,String> entry :map.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            if(urlencorde){
                try {
                    value = URLEncoder.encode(value, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            formparams.add(new BasicNameValuePair(key,value));
        }

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Charset.forName(charset));
        return entity;
    }
}
