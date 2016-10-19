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
    //��ǩ����
    public static synchronized String sign(String str, String key){
        String sign = str + key;
        //MD5Util ���̰߳�ȫ����Ҫ����ʵ��ͬ��
        String encrySign = MD5Util.getMD5String(sign.getBytes(Charset.forName("utf-8")));
        LOGGER.debug("\n=================ǩ����ʼ====================" +
                    "\nstr:" + str +
                    "\nkey:" + key +
                    "\nsign:" + encrySign +
                    "\n=================ǩ������====================");


        return encrySign;
    }
    //��ǩ
    public static synchronized boolean checkSign(String str,String key,String srcSign){
        String src = str + key;
        String desSign = MD5Util.getMD5String(src.getBytes(Charset.forName("utf-8")));
        LOGGER.debug("\n=================��ǩ��ʼ====================" +
                    "\nsrcString:" + str +
                    "\nkey:" + key +
                    "\ndesSign:" + desSign +
                    "\nsrcSign:" + srcSign +
                    "\n=================��ǩ����====================");
        if(StringUtil.isEmpty(srcSign)){
            return false;
        }
        if(srcSign.equals(desSign)){
            return true;
        }else {
            LOGGER.info("sign Ϊ" + srcSign +"����ˮ[��ǩʧ�ܣ�]" );
            return false;
        }

    }


}