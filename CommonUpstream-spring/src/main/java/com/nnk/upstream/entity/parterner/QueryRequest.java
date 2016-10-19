package com.nnk.upstream.entity.parterner;


import com.nnk.upstream.util.PostFormUtils;
import com.nnk.utils.http.interfaces.HttpData;
import org.apache.http.HttpEntity;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/13
 * Time: 16:35
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class QueryRequest extends HttpData implements Request{

    private String merchantNo;
    private String sendorderId;
    private String rechargeType;
    private String rechargeNum;
    private String rechargeValue;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public HttpEntity getPostdata() {
        return PostFormUtils.buildEntity(this, "", false);
    }

    @Override
    public String getGetdata() {
        return null;
    }

    @Override
    public String toString() {
        return "QueryRequest{" +
                "merchantNo='" + merchantNo + '\'' +
                ", sendorderId='" + sendorderId + '\'' +
                ", rechargeType='" + rechargeType + '\'' +
                ", rechargeNum='" + rechargeNum + '\'' +
                ", rechargeValue='" + rechargeValue + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public QueryRequest(String merchantNo, String sendorderId, String rechargeType, String rechargeNum, String rechargeValue) {
        this.merchantNo = merchantNo;
        this.sendorderId = sendorderId;
        this.rechargeType = rechargeType;
        this.rechargeNum = rechargeNum;
        this.rechargeValue = rechargeValue;
    }

    public QueryRequest() {
    }
}
