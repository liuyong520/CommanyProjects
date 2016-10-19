package com.nnk.upstream.handler;

import com.nnk.upstream.convert.ProtoclConverter;
import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.entity.parterner.Request;
import com.nnk.upstream.entity.self.ProtoclType;
import com.nnk.upstream.entity.self.RechargeType;
import com.nnk.upstream.entity.self.SlowIntProctol;
import com.nnk.upstream.entity.self.UpstreamSession;
import com.nnk.upstream.service.RedisCacheManager;
import com.nnk.upstream.service.imp.HandlerServiceCustomize;
import com.nnk.upstream.service.imp.HandlerServiceImp;
import com.nnk.upstream.util.DateUtil;
import com.nnk.upstream.util.MsgSrvResponseUtils;
import com.nnk.upstream.vo.InterfaceConfig;
import com.nnk.utils.http.exception.NetWorkException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/19
 * Time: 14:11
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */

public class MessageHandler implements Handler{
    public static final Logger LOGGER = Logger.getLogger(MessageHandler.class);
    @Autowired
    private HandlerServiceImp handlerService;
    @Autowired
    private HandlerServiceCustomize customizeHandlerService;
    @Autowired
    private ProtoclConverter converter;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Autowired
    private ConfigContextManager configContextManager;

    @Override
    public void handlerRequest(UpstreamSession session) {
        SlowIntProctol slowIntProctol = new SlowIntProctol(session.getContents().split(" +"));
        InterfaceConfig config = configContextManager.searchConfigContext(slowIntProctol.getMerid());
        if(session.getProtoclType().equals(ProtoclType.RECHARGE)){
            Request rechargeRequest = null;
            slowIntProctol.setRechargeType(RechargeType.getname(config.getRechargetype()));
            redisCacheManager.add2Map(RedisCacheManager.WAITTINGMAP,slowIntProctol.getSendorderid(),slowIntProctol);
            try {
                rechargeRequest = (Request) converter.toRequest(session);
                if (DateUtil.isTimeout(new Date(session.getTime()), 200)) {
                    LOGGER.info("从队列接收超时：" + session.getContents());
                    MsgSrvResponseUtils.responseBrokeFail(session);
                    return;
                }
                if (DateUtil.isTimeout(slowIntProctol.getOrdertime(), 300)) {
                    LOGGER.info("发送超时：" + session.getContents());
                    MsgSrvResponseUtils.responseBrokeFail(session);
                    return;
                }
            }catch (Exception e){
                e.printStackTrace();
                MsgSrvResponseUtils.responseBrokeFail(session);//请求未发送之前都可以转回系统
                return;
            }
            try {
                boolean common = config.getInterfacetype();
                if(common) {
                    handlerService.recharge(session, rechargeRequest);
                }else {
                    customizeHandlerService.recharge(session,rechargeRequest);
                }
            } catch (NetWorkException e) {
                slowIntProctol.setSendStatus(SlowIntProctol.SendStatus.SENDED);
                redisCacheManager.add2Map(RedisCacheManager.WAITTINGMAP,slowIntProctol.getSendorderid(),slowIntProctol);
                e.printStackTrace();
            }catch (RuntimeException e){
                slowIntProctol.setSendStatus(SlowIntProctol.SendStatus.SENDED);
                redisCacheManager.add2Map(RedisCacheManager.WAITTINGMAP,slowIntProctol.getSendorderid(),slowIntProctol);
                e.printStackTrace();
            }
        }else if(session.getProtoclType().equals(ProtoclType.QUERY) ){
            Request queryRequest = (Request) converter.toRequest(session);
            try {
                boolean common = config.getInterfacetype();
                if(common) {
                    handlerService.query(session, queryRequest);
                }else {
                    customizeHandlerService.query(session, queryRequest);
                }
            } catch (NetWorkException e) {
                e.printStackTrace();
            }
        }

    }
}
