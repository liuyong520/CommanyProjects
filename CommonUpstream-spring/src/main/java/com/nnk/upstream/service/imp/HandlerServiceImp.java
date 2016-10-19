package com.nnk.upstream.service.imp;


import com.nnk.interfacetemplate.entity.ApplicationParameters;
import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.entity.parterner.*;
import com.nnk.upstream.entity.self.SlowIntProctol;
import com.nnk.upstream.entity.self.UnSlowIntProtocl;
import com.nnk.upstream.entity.self.UpstreamSession;
import com.nnk.upstream.service.IHandlerService;
import com.nnk.upstream.service.RedisCacheManager;
import com.nnk.upstream.util.*;
import com.nnk.upstream.vo.InterfaceConfig;
import com.nnk.utils.http.exception.NetWorkException;
import com.nnk.utils.http.utils.HttpClientUtils;
import com.nnk.utils.http.utils.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/13
 * Time: 18:09
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@Component(value = "HandlerServiceImp")
public class HandlerServiceImp implements IHandlerService {

    private static final Logger LOGGER = Logger.getLogger(HandlerServiceImp.class);
    private static final String NOTEXIST = "ORDERNOTEXIST" ;
    //�ӿ����ù�����
    @Autowired
    private ConfigContextManager configContextManager;
    //����������
    @Autowired
    private ApplicationParameters applicationParameters;
    //http ����
    @Autowired
    private HttpClientUtils httpClientUtils;
    //�������
    @Autowired
    RedisCacheManager redisCacheManager;
    /**
     * �ύ���β�ѯ����
     * @param upstreamSession
     * @param request
     * @throws com.nnk.utils.http.exception.NetWorkException
     * @throws RuntimeException
     */
    public void query(UpstreamSession upstreamSession,Request request) throws NetWorkException,RuntimeException{
        if(null == upstreamSession || null==request){
            throw new RuntimeException("��ѯ��������");
        }
        SlowIntProctol slowIntProctol = new SlowIntProctol(upstreamSession.getContents().split(" +"));
        try {

            InterfaceConfig config = configContextManager.searchConfigContext(slowIntProctol.getMerid());
            if(StringUtil.isEmpty(config.getQueryurl())){
                return ;
            }
            String resp = httpClientUtils.doPost(config.getQueryurl(), (QueryRequest) request);
            QueryResponse queryResponse = JsonUtil.buildObject(resp, QueryResponse.class);
            String src = ReflectUtils.getkeyValueString(queryResponse, "=", "&", "sign");
            if(SignSecurityUtlis.checkSign(src,config.getEncrykey(),queryResponse.getSign())) {
                //��Ӧ�ɹ�
                if (applicationParameters.getResponseSuccessSet().contains(queryResponse.getRespCode())) {
                    if (applicationParameters.getQuerySucceeSet().contains(queryResponse.getOrderStatus())) {
                        MsgSrvResponseUtils.responseCallbackSuccess(upstreamSession, queryResponse.getRealValue(), queryResponse.getOemorderId(), DateUtil.format(new Date()), "�ɹ�");
                        LOGGER.info(String.format(Formart,slowIntProctol.getMerid(),slowIntProctol.getSendorderid())+"��ֵ�ɹ�");
                        //�������
                        if (redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid())) {
                            redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid());
                        }
                    } else if (applicationParameters.getQueryFailSet().contains(queryResponse.getOrderStatus())) {
                        MsgSrvResponseUtils.responseCallbackFail(upstreamSession, "0", queryResponse.getOemorderId(), DateUtil.format(new Date()), "ʧ��");
                        LOGGER.info(String.format(Formart,slowIntProctol.getMerid(),slowIntProctol.getSendorderid())+"��ֵʧ��");
                        //��������쳣�ܲ鵽����ġ�
                        if (redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid())) {
                            redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid());
                        }
                    }else if(applicationParameters.getQueryEnsureSet().contains(queryResponse.getOrderStatus())){
                        if(NOTEXIST.equals(queryResponse.getOrderStatus())) {
                            if (config.getIsexpire()) {//�Ƿ����ȴ�����ʧ�ܴ���
                                int delay = config.getExpiretime() * 60;
                                Date chgtime = null;
                                if(SlowIntProctol.CALLBACK.equals(slowIntProctol.getSendedApp())){//����ǲ�ѯЭ��
                                    chgtime = DateUtil.parse(slowIntProctol.getChgTime(), "yyyyMMddHHmmss");
                                }else{//����ǳ�ֵЭ��
                                    chgtime = slowIntProctol.getOrdertime();
                                }
                                if (DateUtil.isTimeout(chgtime, delay)) {
                                    MsgSrvResponseUtils.responseCallbackFail(upstreamSession, "0", "NA", DateUtil.format(new Date()), "notExist");
                                    LOGGER.info(String.format(Formart, slowIntProctol.getMerid(), slowIntProctol.getSendorderid()) + "����������,�Ѿ��������ȴ�ʱ�䣬��ʧ�ܴ���");
                                    if (redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid())) {
                                        redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            configContextManager.updateConfigContext(slowIntProctol.getMerid());
            throw new RuntimeException("��ѯ���̷����쳣");
        }
    }

    /**
     * �ύ��ֵ��������
     * @param upstreamSession
     * @param request
     * @throws com.nnk.utils.http.exception.NetWorkException
     * @throws RuntimeException
     */
    public void recharge(UpstreamSession upstreamSession,Request request) throws NetWorkException,RuntimeException{
        if(null == upstreamSession || null==request){
            throw new RuntimeException("��ֵ��������");
        }
        SlowIntProctol slowIntProctol = new SlowIntProctol(upstreamSession.getContents().split(" +"));
        try {
            InterfaceConfig config = configContextManager.searchConfigContext(slowIntProctol.getMerid());
            if(StringUtil.isEmpty(config.getRechargeurl())){
                return ;
            }
            String resp = httpClientUtils.doPost(config.getRechargeurl(), (RechargeRequest)request);
            RechargeResponse rechargeResponse = JsonUtil.buildObject(resp, RechargeResponse.class);
            String src = ReflectUtils.getkeyValueString(rechargeResponse, "=", "&", "sign");
            if(SignSecurityUtlis.checkSign(src, config.getEncrykey(), rechargeResponse.getSign())) {

                if (applicationParameters.getResponseSuccessSet().contains(rechargeResponse.getRespCode())) {
                    if (applicationParameters.getRechargeSuccessSet().contains(rechargeResponse.getOrderStatus())) {//�µ�ֻ�гɹ�����ʧ��
                        MsgSrvResponseUtils.responseBrokeSuccess(upstreamSession, rechargeResponse.getOemorderId());
                        LOGGER.info(String.format(Formart,slowIntProctol.getMerid(),slowIntProctol.getSendorderid())+"�µ�����ɹ�");
                    } else if (applicationParameters.getRechargeFailSet().contains(rechargeResponse.getOrderStatus())) {
                        MsgSrvResponseUtils.responseBrokeFail(upstreamSession);
                        LOGGER.info(String.format(Formart,slowIntProctol.getMerid(),slowIntProctol.getSendorderid())+"�µ�����ʧ��");
                    }
                    //ֻҪ���������������Ƴ���
                    updateRedisPotoclStatus(slowIntProctol);//Ϊ�˱�������ȰѶ�����״̬��
                    if (redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid())) {//����յ��ظ��Ƴ���������
                        redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid());
                    }
                } else {
                    MsgSrvResponseUtils.responseBrokeFail(upstreamSession);
                    LOGGER.info(String.format(Formart,slowIntProctol.getMerid(),slowIntProctol.getSendorderid())+ "���η�����Ӧ�룺" + rechargeResponse.getRespCode() + "������:" + rechargeResponse.getRespMsg());
                    updateRedisPotoclStatus(slowIntProctol);//����Э��Э��״̬
                }
            }else {
                updateRedisPotoclStatus(slowIntProctol);
            }
        }catch (Exception e){
            configContextManager.updateConfigContext(slowIntProctol.getMerid());
            throw new RuntimeException("��ֵ�����з����쳣");
        }
    }

    private void updateRedisPotoclStatus(SlowIntProctol slowIntProctol) {
        if (redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid())) {//����յ��ظ��Ƴ���������
            slowIntProctol.setSendStatus(SlowIntProctol.SendStatus.SENDEDANDRECVED);
            redisCacheManager.add2Map(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid(), slowIntProctol);
        }
    }

    /**
     * �ύ���г�������
     * @param session
     * @param request
     */
    public void unRecharge(UpstreamSession session,Request request) {
        if(null == session || null==request){
            throw new RuntimeException("��ֵ��������");
        }
        UnSlowIntProtocl unSlowIntProtocl = new UnSlowIntProtocl(session);
        try {
            InterfaceConfig config = configContextManager.searchConfigContext(unSlowIntProtocl.getMerid());
            if(StringUtil.isEmpty(config.getUnrechargeurl())){
                return ;
            }

            String resp = httpClientUtils.doPost(config.getUnrechargeurl(),(UnRechargeRequest) request);
            UnRechargeResponse unRechargeResponse = JsonUtil.buildObject(resp, UnRechargeResponse.class);
            String src = ReflectUtils.getkeyValueString(unRechargeResponse, "=", "&", "sign");
            if(SignSecurityUtlis.checkSign(src, config.getEncrykey(), unRechargeResponse.getSign())) {
                if (applicationParameters.getResponseSuccessSet().contains(unRechargeResponse.getRespCode())) {
                    LOGGER.debug(String.format(Formart,unSlowIntProtocl.getMerid(),unSlowIntProtocl.getSendorderid())+"������ǩͨ��");
                    if (applicationParameters.getUnRechargeSuccessSet().contains(unRechargeResponse.getOrderStatus())) {
                        MsgSrvResponseUtils.responseUnslowIntSuccess(session);
                        LOGGER.info(String.format(Formart,unSlowIntProtocl.getMerid(),unSlowIntProtocl.getSendorderid())+"�����ɹ�");
                    } else if (applicationParameters.getUnREchargeFailSet().contains(unRechargeResponse.getOrderStatus())) {
                        MsgSrvResponseUtils.responseUnslowIntFail(session);
                        LOGGER.info(String.format(Formart,unSlowIntProtocl.getMerid(),unSlowIntProtocl.getSendorderid())+"����ʧ��");
                    }
                } else {
                    MsgSrvResponseUtils.responseUnslowIntFail(session);
                }
            }
        }catch (Exception e){
            configContextManager.updateConfigContext(unSlowIntProtocl.getMerid());
            LOGGER.info("���������쳣");
        }
    }

    /**
     * �ύ��������ѯ����
     * @param session
     * @param request
     */
    public void queryBalance(UpstreamSession session, Request request) {
        if(null == session || null==request){
            throw new RuntimeException("��ֵ��������");
        }
        String[] contents = session.getContents().split(" +");
        String merid = contents[1];
        try {
            InterfaceConfig config = configContextManager.searchConfigContext(merid);
            if(StringUtil.isEmpty(config.getBanlanceurl())){
                return ;
            }
            String resp = httpClientUtils.doPost(config.getBanlanceurl(),(BalanceRequest) request);
            BalanceResponse balanceResponse = JsonUtil.buildObject(resp, BalanceResponse.class);
            String src = ReflectUtils.getkeyValueString(balanceResponse,"=","&","sign");
            if(SignSecurityUtlis.checkSign(src, config.getEncrykey(), balanceResponse.getSign())) {
                if (applicationParameters.getBalanceSucceessSet().contains(balanceResponse.getRespCode())) {
                    MsgSrvResponseUtils.responseBalanceSuccess(session, balanceResponse.getBalance());
                    LOGGER.info("[������]"+merid+"���Ϊ��"+balanceResponse.getBalance());
                } else if (applicationParameters.getBalanceFailSet().contains(balanceResponse.getRespCode())||applicationParameters.getCallbackFailSet().contains("*")) {
                    MsgSrvResponseUtils.responseBalanceFail(session);
                }
            }

        }catch (Exception e){
            configContextManager.updateConfigContext(merid);
            //�Ե��쳣ʲô������
        }
    }


    public boolean notifyCallback(Request request1){
        if(request1 instanceof CallbackRequest) {
            CallbackRequest request = (CallbackRequest) request1;
            InterfaceConfig config = configContextManager.searchConfigContext(request.getMerchantNo());
            String src = ReflectUtils.getkeyValueString(request, "=", "&", "sign");
            if (SignSecurityUtlis.checkSign(src, config.getEncrykey(), request.getSign())) {
                if (applicationParameters.getResponseSuccessSet().contains(request.getRespCode())) {
                    if (applicationParameters.getCallbackSuccessSet().contains(request.getOrderStatus())) {
                        MsgSrvResponseUtils.responseCallbackSuccess(request.getSendorderId(), request.getMerchantNo(), request.getRealValue(),
                                request.getOemorderId(), DateUtil.format(new Date()), "success");
                        LOGGER.info(String.format(Formart, request.getMerchantNo(), request.getSendorderId()) + "�ص�����ֵ�ɹ�");
                    } else if (applicationParameters.getCallbackFailSet().contains(request.getOrderStatus())) {
                        MsgSrvResponseUtils.responseCallbackFail(request.getSendorderId(), request.getMerchantNo(), "0", request.getOemorderId(),
                                DateUtil.format(new Date()), "ʧ��");
                        LOGGER.info(String.format(Formart, request.getMerchantNo(), request.getSendorderId()) + "�ص�����ֵʧ��");
                    }
                } else {
                    LOGGER.info(String.format(Formart, request.getMerchantNo(), request.getSendorderId()) + "callback Respcode:" + request.getRespCode() + ",RespMsg:" + request.getRespMsg());
                }
                if (redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP, request.getSendorderId())) {//����յ��ظ��Ƴ���������
                    redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP, request.getSendorderId());
                }
                return true;
            } else {
                return false;
            }
        }

        return false;
    }
}
