package com.nnk.upstream.entity.proxy;

import com.nnk.upstream.entity.parterner.Request;
import com.nnk.upstream.util.PostFormUtils;
import com.nnk.utils.http.interfaces.HttpData;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/23
 * Time: 10:28
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class ProtoclWrapper extends HttpData implements Request{
    //协议
    private String protocl;
    //请求方法类型
    private String methodType;
    //请求编码类型
    private String charset;
    //协议传输类型
    private String protoclType;

    public ProtoclWrapper(String protocl, String methodType, String charset, String protoclType) {
        this.protocl = protocl;
        this.methodType = methodType;
        this.charset = charset;
        this.protoclType = protoclType;
    }

    @Override
    public HttpEntity getPostdata() {
        if(ProctolTypeEnum.FORM.getName().equals(protoclType)){
            HashMap<String,String> map = new HashMap<String,String>();
            String[] keypairs = protocl.split("&");

            for(String keyvalue:keypairs){

                String[] content = keyvalue.split("=",2);
                if(content.length<=1){
                    map.put(content[0],"");
                }else {
                    map.put(content[0], content[1]);
                }
            }
            return PostFormUtils.buildEntity(map,false,charset);
        }else if(ProctolTypeEnum.JSON.getName().equals(protoclType)){
            return new StringEntity(protocl, Charset.forName(charset));
        }else if(ProctolTypeEnum.XML.getName().equals(protoclType)){
            StringEntity entity = new StringEntity(protocl, Charset.forName(charset));
            return entity;
        }
        return null;
    }

    @Override
    public String getGetdata() {
        try {
            return EntityUtils.toString(getPostdata());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getProtocl() {
        return protocl;
    }

    public void setProtocl(String protocl) {
        this.protocl = protocl;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getProtoclType() {
        return protoclType;
    }

    public void setProtoclType(String protoclType) {
        this.protoclType = protoclType;
    }

    @Override
    public String toString() {
        return "ProtoclWrapper{" +
                "protocl='" + protocl + '\'' +
                ", methodType='" + methodType + '\'' +
                ", charset='" + charset + '\'' +
                ", protoclType='" + protoclType + '\'' +
                '}';
    }
}
