package com.nnk.upstream.convert.imp;

import com.alibaba.fastjson.JSONObject;
import com.nnk.interfacetemplate.common.StringUtil;
import com.nnk.interfacetemplate.common.XmlUtil;
import com.nnk.upstream.entity.mapping.Mapdata;
import com.nnk.upstream.entity.mapping.XmlModel;
import com.nnk.upstream.entity.proxy.ProtoclWrapper;
import com.nnk.upstream.util.DateUtil;
import com.nnk.upstream.util.EncrytionFactory;
import com.nnk.upstream.util.ReflectUtils;
import com.nnk.upstream.vo.InterfaceConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/30
 * Time: 14:02
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CustomizeProtoclConverter {
    public static final String CONSTANT = "Constant";
    public static final String STRING = "String";
    public static final String DATE = "Date";
    public static final String MAP = "Map";
    public static final String SIGN = "Sign";
    public static final String PASSWORD = "Password";
    public static final String UPPERCASE = "uppercase";
    public static final String LOWCASE = "lowcase";
    public static final String CAPITAL = "capital";

    public static final Logger LOGGER = Logger.getLogger(CustomizeProtoclConverter.class);
    public ProtoclWrapper buildPotoclCustomize(Object targetObject, InterfaceConfig interfaceConfig, String rulexml) {
        if (StringUtil.isEmpty(rulexml)) {
            return null;
        }
        try {
            XmlModel xmlModel = XmlUtil.parseXml(XmlModel.class,rulexml);
            String protocl = xmlModel.getProtocl().replaceAll("@","&");
            Map valueMap = new HashMap();
            //签名字段
            String sign = null;
            String replacesign = null;
            String key = null;
            String password = null;
            for (Mapdata mapdata:xmlModel.getMapdata()){//获取对应映射关系
                String name = mapdata.getName();
                String relacename = "$" + name;
                //如果是常量
                if(CONSTANT.equals(mapdata.getType())){
                    String value = mapdata.getDefaultvalue();
                    if(StringUtil.isNotEmpty(value)){
                        if("NA".equals(value)){
                            protocl =protocl.replace(relacename,"");
                            valueMap.put(mapdata.getName(),"");
                        }else {
                            protocl = protocl.replace(relacename, value);
                            valueMap.put(mapdata.getName(), value);
                        }
                    }else{
                        String nnkname = mapdata.getNnkname();
                        if(StringUtil.isEmpty(nnkname)){
                            LOGGER.error("nnkname is empty check the protocl config ");
                        }
                        String methodname = "get" + StringUtils.capitalize(nnkname);
                        String value1 = (String) ReflectUtils.invokeMethodName(interfaceConfig,methodname,null,null);
                        protocl =protocl.replace(relacename,value1);
                        valueMap.put(mapdata.getName(),value1);
                    }
                    // 如果是字符串类型

                }else if(STRING.equals(mapdata.getType()) && StringUtil.isNotEmpty(mapdata.getNnkname())){
                    String nnkname = mapdata.getNnkname();
                    Double unit = null;
                    String methodname = "get" + StringUtils.capitalize(nnkname);
                    String value =null;
                    if("value".equalsIgnoreCase(nnkname)){
                        if(StringUtil.isEmpty(mapdata.getDefaultvalue())){
                            unit = 1d;
                        }else{
                            unit = Double.parseDouble(mapdata.getDefaultvalue());
                        }
                        value = (String) ReflectUtils.invokeMethodName(targetObject,methodname,null,null);
                        Integer integer = Integer.parseInt(value);
                        Number number = unit*integer;
                        value = number.intValue() + "";
                        LOGGER.info("unit:" + unit +",007ka value:" + integer + ",partner‘s value：" + value);
                    }else if("ordertime".equalsIgnoreCase(nnkname)){
                        String format = "yyyyMMddHHmmss";
                        if(StringUtil.isEmpty(mapdata.getDefaultvalue())){
                            format = "yyyyMMddHHmmss";;
                        }else{
                            format = mapdata.getDefaultvalue();
                        }
                        Date date  = (Date) ReflectUtils.invokeMethodName(targetObject,methodname,null,null);
                        value = DateUtil.format(date,format);
                    }else {
                        value =  (String) ReflectUtils.invokeMethodName(targetObject,methodname,null,null);
                    }

                    protocl = protocl.replace(relacename,value);
                    valueMap.put(mapdata.getName(),value);
                }else if(DATE.equals(mapdata.getType())){
                    String nnkname = mapdata.getNnkname();
                    String methodname = "get" +  StringUtils.capitalize(nnkname);
                    String formate = mapdata.getDefaultvalue();
                    String value = null;
                    if(StringUtil.isEmpty(formate)){
                        value = DateUtil.format(new Date());

                    }else {
                        value = DateUtil.format(new Date(),formate);
                    }
                    protocl =protocl.replace(relacename,value);
                    valueMap.put(mapdata.getName(),value);
                }else if(MAP.equals(mapdata.getType()) && StringUtil.isNotEmpty(mapdata.getNnkname())){
                    String nnkname = mapdata.getNnkname();
                    String methodname = "get" +  StringUtils.capitalize(nnkname);
                    String value = (String) ReflectUtils.invokeMethodName(targetObject,methodname,null,null);
                    Map map = null;
                    if(StringUtil.isNotEmpty(mapdata.getDefaultvalue())){
                        try {
                            String baseDecorderString = new String(Base64Utils.decodeFromString(mapdata.getDefaultvalue()));
                            map = JSONObject.parseObject(baseDecorderString, Map.class);
                        }catch (Exception e){
                            LOGGER.error("Map DefaultValue base64 decoded error!");
                            e.printStackTrace();
                        }
                    }

                    //根据nnkmap 获取到值
                    String value1 = (String) map.get(value);
                    LOGGER.debug("Map:" + map +",getMapValue{" + value + "}=" + value1);
                    if(value1==null){
                        LOGGER.error("获取Map映射值为空，构建数据异常");
                        return null;
                    }
                    protocl = protocl.replace(relacename,value1);
                    valueMap.put(mapdata.getName(),value1);
                }else if(SIGN.equals(mapdata.getType())){
                    sign = mapdata.getName();
                    replacesign = "$" + sign;
                    String nnkname = mapdata.getNnkname();
                    String methodname = "get" +  StringUtils.capitalize(nnkname);
                    LOGGER.debug("根据Method:"+methodname +"从InterfaceConfig中获取密钥...");
                    key = (String) ReflectUtils.invokeMethodName(interfaceConfig,methodname,null,null);
                    LOGGER.debug("key:"+key);
                    //加密的key也需要放进去
                    valueMap.put(mapdata.getNnkname(),key);
                }else if(PASSWORD.equals(mapdata.getType())){
                    password = mapdata.getDefaultvalue();
                    LOGGER.debug("password:"+password);
                    valueMap.put(mapdata.getName(),password);
                }
            }
            boolean IsSign =  xmlModel.isEncryOrCheckSign();
            if(IsSign) {
                //获取加密算法类型
                String encryptionType = xmlModel.getEncryptionType();
                //获取加密顺序
                String encrySqueques = xmlModel.getSignSquence();
                String[] encrySquequeArray = encrySqueques.split(",");
                //获取加密格式：
                String encryFormat = xmlModel.getSignFormart().replaceAll("@", "&");
                String charset = xmlModel.getCharset();
                for (String encryname : encrySquequeArray) {
                    String value = (String) valueMap.get(encryname);
                    if (value == null) {
                        LOGGER.error("签名获取值" + encryname + "为" + value);
                        return null;
                    }
                    String replacename = "$" + encryname;

                    if (encryname.equals("encrykey")) {
                        encryFormat = encryFormat.replace(replacename, "");
                    } else {
                        encryFormat = encryFormat.replace(replacename, value);
                    }
                    LOGGER.debug("encryname：" + encryname + ",value：" + value);
                }

                String encryptionSign = EncrytionFactory.sign(encryFormat, key, password, encryptionType, charset);
                if (StringUtil.isNotEmpty(encryptionSign)) {
                    if (UPPERCASE.equals(xmlModel.getEncryptionCase())) {
                        encryptionSign = encryptionSign.toUpperCase();
                    } else if (LOWCASE.equals(xmlModel.getEncryptionCase())) {
                        encryptionSign = encryptionSign.toLowerCase();
                    } else if (CAPITAL.equals(xmlModel.getEncryptionCase())) {
                        encryptionSign = StringUtils.capitalize(encryptionSign);
                    } else {
                        encryptionSign = encryptionSign.toLowerCase();
                    }
                    LOGGER.debug("签名原串：" + encryFormat + ",签名：" + encryptionSign);
                    protocl = protocl.replace(replacesign, encryptionSign);
                    LOGGER.info("自定义协议充值：" + protocl);
                    ProtoclWrapper protoclWrapper = new ProtoclWrapper(protocl, xmlModel.getMethod(), xmlModel.getCharset(), xmlModel.getProtoclType());
                    return protoclWrapper;
                } else {
                    LOGGER.error("生成签名出错，发生异常");
                    return null;
                }
            }else{
                LOGGER.info("没有签名,自定义协议充值：" + protocl);
                ProtoclWrapper protoclWrapper = new ProtoclWrapper(protocl, xmlModel.getMethod(), xmlModel.getCharset(), xmlModel.getProtoclType());
                return protoclWrapper;
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
