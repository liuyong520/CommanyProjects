package com.nnk.upstream.entity.parterner;


import com.nnk.interfacetemplate.common.StringUtil;
import com.nnk.upstream.util.PostFormUtils;
import com.nnk.utils.http.interfaces.HttpData;
import org.apache.http.HttpEntity;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/13
 * Time: 16:44
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class CallbackRequest extends HttpData implements Request{
    private String merchantNo;

    private String sendorderId;

    private String oemorderId;

    private String respCode;

    private String respMsg;

    private String orderStatus;

    private String realValue;

    private String sign;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getSendorderId() {
        return sendorderId;
    }

    public void setSendorderId(String sendorderId) {
        this.sendorderId = sendorderId;
    }

    public String getOemorderId() {
        return oemorderId;
    }

    public void setOemorderId(String oemorderId) {
        this.oemorderId = oemorderId;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRealValue() {
        return realValue;
    }

    public void setRealValue(String realValue) {
        this.realValue = realValue;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "CallbackRequest{" +
                "merchantNo='" + merchantNo + '\'' +
                ", sendorderId='" + sendorderId + '\'' +
                ", oemorderId='" + oemorderId + '\'' +
                ", respCode='" + respCode + '\'' +
                ", respMsg='" + respMsg + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", realValue='" + realValue + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    @Override
    public HttpEntity getPostdata() {
        return PostFormUtils.buildEntity(this, "", false);
    }

    @Override
    public String getGetdata() {
        return null;
    }

    public boolean vilidateData(){
        if(StringUtil.isEmpty(this.sendorderId)||StringUtil.isEmpty(this.merchantNo)
                ||StringUtil.isEmpty(this.respCode)||StringUtil.isEmpty(this.realValue)
                ||StringUtil.isEmpty(this.respMsg)||StringUtil.isEmpty(this.orderStatus)
                ||StringUtil.isEmpty(this.sign)){
            return false;
        }else return true;
    }
}

