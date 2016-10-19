package com.nnk.upstream.entity.mapping;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/23
 * Time: 18:58
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement(name = "xsdl")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlModel {
    //请求方式，httpPost 和httpGet
    private String method;
    //协议类型，正常返回协议类型
    private String protoclType;
    //异常返回协议，只有返回时才会存在此字段
    private String exceptionProtoclType;
    //协议，请求时才会用到
    private String protocl;
    //加密算法
    private String encryptionType;
    //签名大小写
    private String encryptionCase;
    //加密签名顺序：
    private String signSquence;
    //加密签名的格式：
    private String signFormart;
    //字符编码：
    private String charset;
    //是否需要验签加密
    private boolean encryOrCheckSign;
    //成功返回的正则表达式
    private String regexSuccess;
    //失败或者异常返回的正则表达式
    private String regexFail;
    //响应状态码
    private String responseSuccessCodeSet;
    //响应成功状态码
    private String responseFailCodeSet;
    //订单状态码
    private String successCodeSet;
    private String failCodeSet;
    private String uncertainCodeSet;

    public String getUncertainCodeSet() {
        return uncertainCodeSet;
    }

    public void setUncertainCodeSet(String uncertainCodeSet) {
        this.uncertainCodeSet = uncertainCodeSet;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    @XmlElementWrapper(name = "mappings")
    @XmlElement(name = "mapping")
    private List<Mapdata> mapdata;

    public List<Mapdata> getMapdata() {
        return mapdata;
    }

    public void setMapdata(List<Mapdata> mapdata) {
        this.mapdata = mapdata;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getProtocl() {
        return protocl;
    }

    public void setProtocl(String protocl) {
        this.protocl = protocl;
    }

    public String getProtoclType() {
        return protoclType;
    }

    public void setProtoclType(String protoclType) {
        this.protoclType = protoclType;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getSignSquence() {
        return signSquence;
    }

    public void setSignSquence(String signSquence) {
        this.signSquence = signSquence;
    }

    public String getSignFormart() {
        return signFormart;
    }

    public void setSignFormart(String signFormart) {
        this.signFormart = signFormart;
    }

    public boolean isEncryOrCheckSign() {
        return encryOrCheckSign;
    }

    public void setEncryOrCheckSign(boolean isEncryOrCheckSign) {
        this.encryOrCheckSign = isEncryOrCheckSign;
    }

    public String getRegexSuccess() {
        return regexSuccess;
    }

    public void setRegexSuccess(String regexSuccess) {
        this.regexSuccess = regexSuccess;
    }

    public String getRegexFail() {
        return regexFail;
    }

    public void setRegexFail(String regexFail) {
        this.regexFail = regexFail;
    }

    public String getResponseSuccessCodeSet() {
        return responseSuccessCodeSet;
    }

    public void setResponseSuccessCodeSet(String responseSuccessCodeSet) {
        this.responseSuccessCodeSet = responseSuccessCodeSet;
    }

    public String getResponseFailCodeSet() {
        return responseFailCodeSet;
    }

    public void setResponseFailCodeSet(String responseFailCodeSet) {
        this.responseFailCodeSet = responseFailCodeSet;
    }

    public String getSuccessCodeSet() {
        return successCodeSet;
    }

    public void setSuccessCodeSet(String successCodeSet) {
        this.successCodeSet = successCodeSet;
    }

    public String getFailCodeSet() {
        return failCodeSet;
    }

    public void setFailCodeSet(String failCodeSet) {
        this.failCodeSet = failCodeSet;
    }

    public String getExceptionProtoclType() {
        return exceptionProtoclType;
    }

    public void setExceptionProtoclType(String exceptionProtoclType) {
        this.exceptionProtoclType = exceptionProtoclType;
    }

    public String getEncryptionCase() {
        return encryptionCase;
    }

    public void setEncryptionCase(String encryptionCase) {
        this.encryptionCase = encryptionCase;
    }
}
