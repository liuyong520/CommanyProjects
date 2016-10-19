package com.nnk.upstream.entity.parterner;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/13
 * Time: 16:35
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class QueryResponse {

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

    public QueryResponse(String merchantNo, String sendorderId, String oemorderId, String respCode, String respMsg, String orderStatus, String realValue) {
        this.merchantNo = merchantNo;
        this.sendorderId = sendorderId;
        this.oemorderId = oemorderId;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.orderStatus = orderStatus;
        this.realValue = realValue;
    }


}
