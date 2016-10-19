package com.nnk.upstream.service;

import com.nnk.upstream.entity.parterner.*;
import com.nnk.upstream.entity.self.UpstreamSession;
import com.nnk.utils.http.exception.NetWorkException;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/22
 * Time: 14:15
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public interface IHandlerService {
    public static final String Formart = "[通用上游接口][代理商]:[%s],007卡订单号:[%s],";
    public static final String CustomizeFormart = "[自定义上游接口][接口编号]:[%s],[流水]:[%s],[007ka订单号]:[%s]";
    public static final String CustomizeBalanceFormart = "[自定义上游接口][接口编号]:[%s],[流水]:[%s]";
    public boolean notifyCallback(Request callbackRequest) throws NetWorkException,RuntimeException;

    public void queryBalance(UpstreamSession session, Request balanceRequest) throws NetWorkException,RuntimeException;

    public void unRecharge(UpstreamSession session,Request request) throws NetWorkException,RuntimeException;

    public void recharge(UpstreamSession upstreamSession,Request request) throws NetWorkException,RuntimeException;

    public void query(UpstreamSession upstreamSession,Request request) throws NetWorkException,RuntimeException;
}
