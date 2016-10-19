package com.nnk.upstream.entity.parterner;


import com.nnk.upstream.util.PostFormUtils;
import com.nnk.utils.http.interfaces.HttpData;
import org.apache.http.HttpEntity;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/8
 * Time: 10:50
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class RechargeRequest extends HttpData implements Request{

    private String merchantNo;
    private String sendorderId;
    private String rechargeType;
    private String rechargeNum;
    private String rechargeValue;
    private String backUrl;
    private String orderTime;
    private String sign;

    public RechargeRequest() {
    }

    @Override
    public HttpEntity getPostdata() {
       return  PostFormUtils.buildEntity(this,"",false);
    }

    @Override
    public String getGetdata() {
        return null;
    }

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

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getRechargeNum() {
        return rechargeNum;
    }

    public void setRechargeNum(String rechargeNum) {
        this.rechargeNum = rechargeNum;
    }

    public String getRechargeValue() {
        return rechargeValue;
    }

    public void setRechargeValue(String rechargeValue) {
        this.rechargeValue = rechargeValue;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "RechargeRequest{" +
                "merchantNo='" + merchantNo + '\'' +
                ", sendorderId='" + sendorderId + '\'' +
                ", rechargeType='" + rechargeType + '\'' +
                ", rechargeNum='" + rechargeNum + '\'' +
                ", rechargeValue='" + rechargeValue + '\'' +
                ", backUrl='" + backUrl + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public RechargeRequest(String merchantNo, String sendorderId, String rechargeType, String rechargeNum, String rechargeValue, String backUrl, String orderTime) {
        this.merchantNo = merchantNo;
        this.sendorderId = sendorderId;
        this.rechargeType = rechargeType;
        this.rechargeNum = rechargeNum;
        this.rechargeValue = rechargeValue;
        this.backUrl = backUrl;
        this.orderTime = orderTime;
    }
}
