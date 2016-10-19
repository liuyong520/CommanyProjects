package com.nnk.upstream.convert.imp;

import com.nnk.upstream.entity.parterner.BalanceRequest;
import com.nnk.upstream.entity.parterner.QueryRequest;
import com.nnk.upstream.entity.parterner.RechargeRequest;
import com.nnk.upstream.entity.parterner.UnRechargeRequest;
import com.nnk.upstream.entity.self.RechargeType;
import com.nnk.upstream.entity.self.SlowIntProctol;
import com.nnk.upstream.entity.self.UnSlowIntProtocl;
import com.nnk.upstream.util.DateUtil;
import com.nnk.upstream.util.ReflectUtils;
import com.nnk.upstream.util.SignSecurityUtlis;
import com.nnk.upstream.vo.InterfaceConfig;
import com.nnk.utils.http.interfaces.HttpData;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/25
 * Time: 18:44
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@Component
public class CommonProtoclConverter {
    /**
     * 通用接口协议转换
     *
     * @param slowIntProctol
     * @param interfaceConfig
     * @param rechargeType
     * @return
     */
    public QueryRequest getQueryRequestCommon(SlowIntProctol slowIntProctol, InterfaceConfig interfaceConfig, RechargeType rechargeType) {
        String value = "";
        if (RechargeType.CHARGE_FLOW == rechargeType) {
            value = Integer.parseInt(slowIntProctol.getValue()) / 100 + "";
        } else {
            value = slowIntProctol.getValue() + "";
        }
        QueryRequest queryRequest = new QueryRequest(slowIntProctol.getMerid(), slowIntProctol.getSendorderid(),
                rechargeType.getName(), slowIntProctol.getMob(), value);

        String src = ReflectUtils.getkeyValueString(queryRequest, "=", "&", "sign");
        String key = interfaceConfig.getEncrykey();
        String sign = SignSecurityUtlis.sign(src, key);
        queryRequest.setSign(sign);
        return queryRequest;
    }


    public HttpData getUnRechargeRequestCommon(UnSlowIntProtocl unSlowIntProtocl, InterfaceConfig interfaceConfig, RechargeType rechargeType) {
        String value = "";
        if (RechargeType.CHARGE_FLOW == rechargeType) {
            value = Integer.parseInt(unSlowIntProtocl.getValue()) / 100 + "";
        } else {
            value = unSlowIntProtocl.getValue();
        }
        String UnslowOrderid = "UN" + unSlowIntProtocl.getUnsloworderid();
        UnRechargeRequest unRechargeRequest = new UnRechargeRequest(unSlowIntProtocl.getMerid(),
                UnslowOrderid, unSlowIntProtocl.getSendorderid(), rechargeType.getName(),
                unSlowIntProtocl.getMob(), value);

        String key = interfaceConfig.getEncrykey();
        String src = ReflectUtils.getkeyValueString(unRechargeRequest, "=", "&", "sign");
        String sign = SignSecurityUtlis.sign(src, key);
        unRechargeRequest.setSign(sign);
        return unRechargeRequest;
    }

    public HttpData getRechargeRequestCommon(SlowIntProctol slowIntProctol, InterfaceConfig interfaceConfig, RechargeType rechargeType) {
        String value = "";
        if (RechargeType.CHARGE_FLOW == rechargeType) {
            value = Integer.parseInt(slowIntProctol.getValue()) / 100 + "";
        } else {
            value = slowIntProctol.getValue() + "";
        }
        RechargeRequest rechargeRequest = new RechargeRequest(slowIntProctol.getMerid(),
                slowIntProctol.getSendorderid(), rechargeType.getName(), slowIntProctol.getMob(),
                value, interfaceConfig.getBackurl(), DateUtil.format(new Date()));
        String src = ReflectUtils.getkeyValueString(rechargeRequest, "=", "&", "sign");
        String key = interfaceConfig.getEncrykey();
        String sign = SignSecurityUtlis.sign(src, key);
        rechargeRequest.setSign(sign);
        return rechargeRequest;
    }

    public HttpData getBalanceRequestCommon(String merid, InterfaceConfig interfaceConfig) {
        String key = interfaceConfig.getEncrykey();
        BalanceRequest balanceRequest = new BalanceRequest(merid);
        String src = ReflectUtils.getkeyValueString(balanceRequest, "=", "&", "sign");
        String sign = SignSecurityUtlis.sign(src, key);
        balanceRequest.setSign(sign);
        return balanceRequest;
    }


}
