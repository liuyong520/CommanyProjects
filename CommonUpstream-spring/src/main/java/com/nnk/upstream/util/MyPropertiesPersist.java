package com.nnk.upstream.util;
import org.springframework.util.DefaultPropertiesPersister;

import java.io.*;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/3/17
 * Time: 15:34
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class MyPropertiesPersist extends DefaultPropertiesPersister {
    public void load(Properties props, InputStream is) throws IOException {

        Properties properties = new Properties();
        properties.load(is);

        if ( (properties.get("jdbc.password") != null) ){
        /*这里通过解密算法，得到你的真实密码，然后写入到properties中*/
            System.out.println(properties.get("jdbc.password"));
            String password = YhbfShell.decoder(properties.getProperty("jdbc.password"));
            properties.setProperty("jdbc.password" , password);
        }
        OutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            properties.store(outputStream, "");
            is = outStream2InputStream(outputStream);
            super.load(props, is);
        }catch(IOException e) {
            throw e;
        }finally {
            outputStream.close();
        }
    }


    private InputStream outStream2InputStream(OutputStream out){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos = (ByteArrayOutputStream) out ;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(bos.toByteArray());
        return swapStream;
    }
}
