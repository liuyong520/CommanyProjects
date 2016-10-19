package com.nnk.template.function;

import com.alibaba.fastjson.JSONObject;
import com.nnk.template.util.MapUtils;
import com.nnk.utils.http.exception.NetWorkException;
import com.nnk.utils.http.interfaces.HttpData;
import com.nnk.utils.http.utils.HttpClientUtils;
import com.nnk.utils.http.utils.StringUtil;
import com.nnk.utils.js.JsoupUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/18
 * Time: 15:52
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//用httpclient的方式去提交http请求
public class HttpClientRequestFunction extends FunctionProviderAdaptor{
    private Logger logger = Logger.getLogger(HttpClientRequestFunction.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        //上级请求下来的数据
        Object object = transientVars.get(Contstant.PARAMENTS);
        if(object==null){
            logger.info("paraments is not set");
        }
        logger.info("paraments:" + object);
        //协议格式
        String request_protocl = (String) args.get("request_protocl");
        //协议类型
        String request_Type = (String) args.get("request_type");
        if(StringUtils.isEmpty(request_protocl)) throw new WorkflowException("request_protocl is not init");
        if(StringUtils.isEmpty(request_Type)) throw new WorkflowException("request_Type is not init");

        String fieldsMapString = (String) args.get("request_fieldsMap");
        Map<String,String> fieldsMap = null;
        if(StringUtil.isNotEmpty(fieldsMapString)){
            fieldsMap = JSONObject.parseObject(fieldsMapString, Map.class);
        }else{
            logger.info("fieldsMap is empty");
        }
        //提交的数据map
        HashMap<String,String> dataMap = new HashMap<String, String>();
        HashMap<String,String> respfieldMap = new HashMap<String, String>();
        //提交的常量
        for(Object key:args.keySet()){
            String keyname = (String) key;
            if(keyname.startsWith("request_const_")){
                String value = (String) args.get(key);
                keyname = keyname.substring("request_const_".length(),keyname.length());
                dataMap.put(keyname,value);
            }else if(keyname.startsWith("resp_html_")){
                String value = (String) args.get(key);
                keyname = keyname.substring("resp_html_".length(),keyname.length());
                respfieldMap.put(keyname, value);
            }
        }
        if(fieldsMap!=null) {
            for (String fieldName : fieldsMap.keySet()) {
                String objectFieldName = fieldsMap.get(fieldName);
                String methodName = "get" + StringUtils.capitalize(objectFieldName);
                try {
                    String value = (String) MethodUtils.invokeExactMethod(object, methodName, null);
                    dataMap.put(fieldName, value);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        String method = (String) args.get("httpMethod");
        String url = (String) args.get(Contstant.URL);
        String heads =  (String) args.get("heads");
        if(StringUtils.isEmpty(method)) throw new WorkflowException("args method is not set");
        if(StringUtils.isEmpty(url)) throw new WorkflowException("args url is not set");
        Map<String, String> headMap = new HashMap<String, String>();
        if(!StringUtils.isEmpty(heads)) {
            headMap = JSONObject.parseObject(heads, Map.class);
        }
        Set<Cookie> cookies = webDriver.manage().getCookies();
        HashMap<String,String> cookiesMap = new HashMap<String, String>();
        for(Cookie cookie :cookies){
             cookiesMap.put(cookie.getName(),cookie.getValue());
        }
        logger.info("cookiesMap:" + cookiesMap);
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        try {
            String resp = null;
            if(method.toLowerCase().equals("post")){
                if(request_Type.equals("json")||request_Type.equals("xml")){
                    for(String key:dataMap.keySet()) {
                        String key1 = "$" + key;
                        if (request_protocl.contains(key1)) {
                            request_protocl = request_protocl.replace(key1, dataMap.get(key));
                        }
                    }
                    resp = httpClientUtils.doPost(url,new Data(request_protocl),cookiesMap,headMap);
                }else{
                    resp = httpClientUtils.doPost(url,dataMap,cookiesMap,headMap);
                }
            }else if(method.toLowerCase().equals("get")){
                 String fullUrl = url + "?" + MapUtils.MaptoHttpData(dataMap);
                 resp = httpClientUtils.doGet(fullUrl,cookiesMap,headMap);
            }
            String respType = (String) args.get("respType");
            if(StringUtils.isEmpty(respType)) throw new WorkflowException("args respType is empty");
            Map resultMap = MapUtils.getResultMap(resp,respType);
            if(resultMap!=null&&!resultMap.isEmpty()){
                logger.info("response parse success resultMap:" + resultMap);
                transientVars.putAll(resultMap);
            }else if("html".equals(respType)){
                org.jsoup.nodes.Document document = JsoupUtils.parseBodyFragment(resp);
//                for()
            }
        } catch (NetWorkException e) {
                e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class Data extends HttpData{
        String data;
        private Data(String data) {
            this.data = data;
        }

        @Override
        public HttpEntity getPostdata() {
            try {
                return new StringEntity(data);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public String getGetdata() {
            return data;
        }
    }
}
