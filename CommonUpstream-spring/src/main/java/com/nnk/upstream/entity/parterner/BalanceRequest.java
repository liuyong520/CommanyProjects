package com.nnk.upstream.entity.parterner;

import com.nnk.upstream.util.PostFormUtils;
import com.nnk.utils.http.interfaces.HttpData;
import org.apache.http.HttpEntity;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/13
 * Time: 16:38
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class BalanceRequest extends HttpData implements Request{
    private String merchantNo;

    private String sign;

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public HttpEntity getPostdata() {
        return  PostFormUtils.buildEntity(this, "", false);
    }

    @Override
    public String getGetdata() {
        return null;
    }

    @Override
    public String toString() {
        return "BalanceRequest{" +
                "merchantNo='" + merchantNo + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public BalanceRequest(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public BalanceRequest() {
    }
}
