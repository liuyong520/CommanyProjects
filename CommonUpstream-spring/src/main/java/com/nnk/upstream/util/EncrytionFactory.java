package com.nnk.upstream.util;

import com.nnk.interfacetemplate.common.DESUtil;
import com.nnk.interfacetemplate.common.MD5Util;
import com.nnk.interfacetemplate.common.SHA1Util;
import com.nnk.interfacetemplate.common.StringUtil;
import com.nnk.upstream.convert.imp.CustomizeProtoclConverter;
import org.apache.log4j.Logger;
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/31
 * Time: 9:56
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class EncrytionFactory {
    private static Logger LOGGER = Logger.getLogger(EncrytionFactory.class);
    private static final String DES = "DES";
    private static final String MD5 = "MD5";
    private static final String SHA1 = "SHA1";
    private static final String BASE64 = "BASE64";
    private static final String AES = "AES";
    public static synchronized String sign(String srcStr,
                                           String key,
                                           String password,
                                           String encrytionType,
                                           String charset){
        if(StringUtil.isEmpty(encrytionType)){
            throw new IllegalArgumentException("加密算法为空");
        }
        if(DES.equals(encrytionType)){
            LOGGER.debug("DES");
            return DESUtil.encryptToDES(srcStr,key);
        }else if(MD5.equals(encrytionType)){
            LOGGER.debug("MD5");
            String encrySrcStr = srcStr + key;
            return MD5Util.getMD5String(encrySrcStr.getBytes(Charset.forName(charset)));
        }else if(SHA1.equals(encrytionType)){
            LOGGER.debug("SHA1");
            String encrySrcStr = srcStr + key;
            return SHA1Util.getSHA1String(encrySrcStr.getBytes(Charset.forName(charset)));
        }else if(BASE64.equals(encrytionType)){
            LOGGER.debug("BASE64");
            String encrySrcStr = srcStr + key;
            return Base64Utils.encodeToString(encrySrcStr.getBytes(Charset.forName(charset)));
        }else if(AES.equals(encrytionType)){
            LOGGER.debug("AES");
            return AESUtils.Encrypt(srcStr,key,password);
        }else{
            throw  new IllegalArgumentException("签名算法配置错误");
        }

    }
    public static synchronized boolean checkSign(String srcStr,
                                                 String key,
                                                 String password,
                                                 String encrytionType,
                                                 String charset,
                                                 String signstr,
                                                 String signCase){
        String sign = sign(srcStr,key,password,encrytionType,charset);

        if (CustomizeProtoclConverter.UPPERCASE.equals(signCase)) {
            sign = sign.toUpperCase();
        } else if (CustomizeProtoclConverter.LOWCASE.equals(signCase)) {
            sign = sign.toLowerCase();
        } else if (CustomizeProtoclConverter.CAPITAL.equals(signCase)) {
            sign = org.apache.commons.lang.StringUtils.capitalize(sign);
        } else {
            sign = sign.toLowerCase();
        }
        LOGGER.debug("签名原串："+srcStr +"，生成签名："+sign + ",原签名："+signstr);
        if(sign.equals(signstr)){
            return true;
        }else return false;

    }

    public static synchronized String descryption(String signstr,
                                                  String key,
                                                  String encrytionType,
                                                  String charset){
        if(StringUtil.isEmpty(encrytionType)){
            throw new IllegalArgumentException("加密算法为空");
        }
        if(DES.equals(encrytionType)){
            return DESUtil.decrypt(signstr,key);
        }else if(MD5.equals(encrytionType)){
            throw new IllegalArgumentException("MD5 为不可逆的加密算法");
        }else if(SHA1.equals(encrytionType)){
            throw new IllegalArgumentException("SHA1 为不可逆的加密算法");
        }else if(BASE64.equals(encrytionType)){
            return new String(Base64Utils.decode(signstr.getBytes(Charset.forName(charset))));
        }else if(AES.contains(encrytionType)){
            throw new IllegalArgumentException("AES 为不可逆的加密算法");
        }
        return null;
    }
}
