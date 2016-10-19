package com.nnk.upstream.util;

import com.nnk.interfacetemplate.common.MD5Util;
import com.nnk.interfacetemplate.common.StringUtil;
import org.apache.log4j.Logger;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/25
 * Time: 12:43
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SignSecurityUtlis {
    public static final Logger LOGGER = Logger.getLogger(SignSecurityUtlis.class);
    //验签加密
    public static synchronized String sign(String str, String key){
        String sign = str + key;
        //MD5Util 非线程安全的需要加锁实现同步
        String encrySign = MD5Util.getMD5String(sign.getBytes(Charset.forName("utf-8")));
        LOGGER.debug("\n=================签名开始====================" +
                    "\nstr:" + str +
                    "\nkey:" + key +
                    "\nsign:" + encrySign +
                    "\n=================签名结束====================");


        return encrySign;
    }
    //验签
    public static synchronized boolean checkSign(String str,String key,String srcSign){
        String src = str + key;
        String desSign = MD5Util.getMD5String(src.getBytes(Charset.forName("utf-8")));
        LOGGER.debug("\n=================验签开始====================" +
                    "\nsrcString:" + str +
                    "\nkey:" + key +
                    "\ndesSign:" + desSign +
                    "\nsrcSign:" + srcSign +
                    "\n=================验签结束====================");
        if(StringUtil.isEmpty(srcSign)){
            return false;
        }
        if(srcSign.equals(desSign)){
            return true;
        }else {
            LOGGER.info("sign 为" + srcSign +"的流水[验签失败！]" );
            return false;
        }

    }


}
