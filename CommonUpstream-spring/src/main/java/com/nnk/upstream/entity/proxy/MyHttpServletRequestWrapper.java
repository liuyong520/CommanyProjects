package com.nnk.upstream.entity.proxy;


import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/12
 * Time: 9:30
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {
    private byte[] body;
    private Map<String,String[]> paramenters = new HashMap<String, String[]>();
    public MyHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        body = StreamUtils.copyToByteArray(request.getInputStream());
        String contentType = request.getContentType();
//        System.out.println("contentType:" + contentType);
//        System.out.println("body length:" + body.length);
        Map map = request.getParameterMap();
        paramenters.putAll(map);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return paramenters;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
       final ByteArrayInputStream bais = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };

    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }
}
