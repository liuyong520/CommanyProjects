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
    public static final String Formart = "[ͨ�����νӿ�][������]:[%s],007��������:[%s],";
    public static final String CustomizeFormart = "[�Զ������νӿ�][�ӿڱ��]:[%s],[��ˮ]:[%s],[007ka������]:[%s]";
    public static final String CustomizeBalanceFormart = "[�Զ������νӿ�][�ӿڱ��]:[%s],[��ˮ]:[%s]";
    public boolean notifyCallback(Request callbackRequest) throws NetWorkException,RuntimeException;

    public void queryBalance(UpstreamSession session, Request balanceRequest) throws NetWorkException,RuntimeException;

    public void unRecharge(UpstreamSession session,Request request) throws NetWorkException,RuntimeException;

    public void recharge(UpstreamSession upstreamSession,Request request) throws NetWorkException,RuntimeException;

    public void query(UpstreamSession upstreamSession,Request request) throws NetWorkException,RuntimeException;
}
