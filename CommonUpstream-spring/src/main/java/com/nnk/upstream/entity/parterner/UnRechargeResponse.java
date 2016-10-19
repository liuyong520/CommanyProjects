package com.nnk.upstream.entity.parterner;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/13
 * Time: 16:42
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class UnRechargeResponse {
    private String merchantNo;

    private String unslowIntOrderId;

    private String sendorderId;

    private String oemorderId;

    private String respCode;

    private String respMsg;

    private String orderStatus;

    private String sign;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getUnslowIntOrderId() {
        return unslowIntOrderId;
    }

    public void setUnslowIntOrderId(String unslowIntOrderId) {
        this.unslowIntOrderId = unslowIntOrderId;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public UnRechargeResponse(String merchantNo, String unslowIntOrderId, String sendorderId, String oemorderId, String respCode, String respMsg, String orderStatus) {
        this.merchantNo = merchantNo;
        this.unslowIntOrderId = unslowIntOrderId;
        this.sendorderId = sendorderId;
        this.oemorderId = oemorderId;
        this.respCode = respCode;
        this.respMsg = respMsg;
        this.orderStatus = orderStatus;
    }
}
