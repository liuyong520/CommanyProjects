package com.nnk.upstream.entity.parterner;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/13
 * Time: 16:45
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class CallbackResponse {
    private String respCode;

    private String respMsg;

    private String sign;

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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
