package com.nnk.redis;

import com.alibaba.fastjson.JSONObject;
import com.nnk.utils.http.exception.NetWorkException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/2
 * Time: 8:39
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class JsonTest {
    @Test
    public void pharseJson(){
        String jsonstr = "{\"code\":\"621\",\"message\":\"商户余额不足,未充值!\"}";
        String jsonstr1= "{\n" +
                "    \"code\": \"2\",\n" +
                "    \"data\": {\n" +
                "        \"up_order_no\": \"20160224103952765560633\"\n" +
                "    },\n" +
                "    \"message\": \"操作成功\"\n" +
                "}" ;
        String jsonstr2 = "{\n" +
                "    \"code\": \"2\",\n" +
                "    \"data\": {\n" +
                "        \"client_order_no\": \"2015122415075700000001\",\n" +
                "        \"deduction_amount\": \"267.0000\",\n" +
                "        \"phone_no\": \"18559622286\",\n" +
                "        \"product_type\": \"4\",\n" +
                "        \"recharge_status\": \"6\",\n" +
                "        \"up_order_no\": \"20151224150841723471453\"\n" +
                "    },\n" +
                "    \"message\": \"操作成功\"\n" +
                "}";


        Map map = JSONObject.parseObject(jsonstr,Map.class);
        Map map1 = JSONObject.parseObject(jsonstr1,Map.class);
        Map map2 = JSONObject.parseObject(jsonstr2,Map.class);
        System.out.println(map);
        System.out.println(map1);
        System.out.println(map2);
        Object data = map2.get("data");
        if (data instanceof JSONObject){
            JSONObject jsonObjectData = (JSONObject) data;
            Object object = jsonObjectData.get("phone_no1");
            System.out.println(object);
        }
        System.out.println(data);
    }



    @Test
    public void testCallbackSuccess() throws NetWorkException, IOException {
//        HttpClientUtils httpClientUtils = new HttpClientUtils();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://211.139.145.140:9080/eucp/charge/ListCharge.do");
        httpPost.addHeader("Cookie","JSESSIONID=65180F842DDCD00672BBA13571519E6E");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("action:ListCharge","查询"));
        nvps.add(new BasicNameValuePair("chargeDetail.beChargedNumber","13751004674"));
        nvps.add(new BasicNameValuePair("chargeDetail.calledState", ""));
        nvps.add(new BasicNameValuePair("desc", "0"));
        nvps.add(new BasicNameValuePair("endTime", "2016-06-03 14:53:36"));
        nvps.add(new BasicNameValuePair("goValue", "1"));
        nvps.add(new BasicNameValuePair("orderby", ""));
        nvps.add(new BasicNameValuePair("pageNo", "1"));
        nvps.add(new BasicNameValuePair("pageSize", "10"));
        nvps.add(new BasicNameValuePair("rowCount", "1"));
        nvps.add(new BasicNameValuePair("showPageSize", "10"));
        nvps.add(new BasicNameValuePair("startTime", "2016-05-27 00:00:00"));
        HttpEntity entity = new UrlEncodedFormEntity(nvps, Charset.forName("utf-8"));
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        HttpEntity httpcontent = response.getEntity();
        System.out.println(EntityUtils.toString(httpcontent));
    }

}
