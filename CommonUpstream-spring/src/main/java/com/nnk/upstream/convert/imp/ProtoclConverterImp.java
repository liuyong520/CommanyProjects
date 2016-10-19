package com.nnk.upstream.convert.imp;


import com.nnk.upstream.convert.ProtoclConverter;
import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.entity.parterner.QueryRequest;
import com.nnk.upstream.entity.self.*;
import com.nnk.upstream.service.IHandlerService;
import com.nnk.upstream.vo.InterfaceConfig;
import com.nnk.upstream.vo.InterfaceRule;
import com.nnk.utils.http.interfaces.HttpData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/8
 * Time: 12:30
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ProtoclConverterImp implements ProtoclConverter {
    public static final Logger LOGGER = Logger.getLogger(ProtoclConverterImp.class);

    //ͨ��Э��ת����
    @Autowired
    private CommonProtoclConverter commonConverter;
    //�Զ���Э��ת����
    @Autowired
    private CustomizeProtoclConverter customizeConverter;
    @Autowired
    private ConfigContextManager configContextManager;

    @Override
    public HttpData toRequest(UpstreamSession session) {
        if (ProtoclType.RECHARGE == session.getProtoclType()) {
            LOGGER.info("[��ˮ]"+ session.getSessionId()+"========������ֵ����=======");
            return toRechargeRequest(session);
        } else if (ProtoclType.UNRECHARGE == session.getProtoclType()) {
            LOGGER.info("[��ˮ]"+ session.getSessionId()+"========������������=======");
            return toUnRechargeRequest(session);
        } else if (ProtoclType.BALANCE == session.getProtoclType()) {
            LOGGER.info("[��ˮ]"+ session.getSessionId()+"========��������ѯ����=======");
            return toBanlanceRequest(session);
        } else if (ProtoclType.QUERY == session.getProtoclType()) {
            LOGGER.info("[��ˮ]"+ session.getSessionId()+"========����������ѯ����=======");
            return toQueryRequest(session);
        }
        return null;
    }

    /**
     * ���ɲ�ѯ����
     * @param session
     * @return
     */
    private HttpData toQueryRequest(UpstreamSession session) {
        SlowIntProctol slowIntProctol = new SlowIntProctol(session.getContents().split(" +"));
        InterfaceConfig interfaceConfig = configContextManager.searchConfigContext(slowIntProctol.getMerid());
        RechargeType rechargeType = getRechargeType(interfaceConfig);
        boolean common = interfaceConfig.getInterfacetype();
        if (common) {
            QueryRequest queryRequest = commonConverter.getQueryRequestCommon(slowIntProctol, interfaceConfig, rechargeType);
            return queryRequest;
        } else {
            LOGGER.info(String.format(IHandlerService.CustomizeFormart,slowIntProctol.getMerid(),session.getSessionId(),slowIntProctol.getSendorderid())+"����ʼ����....");
            InterfaceRule rule = configContextManager.searchRuleContext(slowIntProctol.getMerid());
            String rulexml = rule.getQueryrequest();
            return customizeConverter.buildPotoclCustomize(slowIntProctol,interfaceConfig,rulexml);
        }
    }




    /**
     * ѡ���ֵ����
     * @param interfaceConfig
     * @return
     */
    private RechargeType getRechargeType(InterfaceConfig interfaceConfig) {
        RechargeType rechargeType = RechargeType.CHARG_MOBLIE;
        if (interfaceConfig.getRechargetype().intValue() == RechargeType.CHARG_MOBLIE.getIndex()) {
            rechargeType = RechargeType.CHARG_MOBLIE;
        } else if (interfaceConfig.getRechargetype().intValue() == RechargeType.CHARGE_FLOW.getIndex()) {
            rechargeType = RechargeType.CHARGE_FLOW;
        } else if (interfaceConfig.getRechargetype().intValue() == RechargeType.CHARGE_OILE.getIndex()) {
            rechargeType = RechargeType.CHARGE_OILE;
        }
        return rechargeType;
    }

    /**
     * ��������ѯЭ��
     * @param session
     * @return
     */
    private HttpData toBanlanceRequest(UpstreamSession session) {
        String[] cotents = session.getContents().split(" +");
        String merid = cotents[1];
        InterfaceConfig interfaceConfig = configContextManager.searchConfigContext(merid);
        boolean common = interfaceConfig.getInterfacetype();
        if(common){
            return commonConverter.getBalanceRequestCommon(merid, interfaceConfig);
        }else{
            LOGGER.info(String.format(IHandlerService.CustomizeBalanceFormart,merid,session.getSessionId())+"����ʼ����....");
            InterfaceRule rule = configContextManager.searchRuleContext(merid);
            String rulexml = rule.getBanlancerequest();
            return customizeConverter.buildPotoclCustomize(null,interfaceConfig,rulexml);
        }

    }


    /**
     * ���ɳ�������
     * @param session
     * @return
     */
    private HttpData toUnRechargeRequest(UpstreamSession session) {
        UnSlowIntProtocl unSlowIntProtocl = new UnSlowIntProtocl(session);
        InterfaceConfig interfaceConfig = configContextManager.searchConfigContext(unSlowIntProtocl.getMerid());
        RechargeType rechargeType = getRechargeType(interfaceConfig);
        boolean common = interfaceConfig.getInterfacetype();
        if(common){
            return commonConverter.getUnRechargeRequestCommon(unSlowIntProtocl, interfaceConfig, rechargeType);
        }else{
            LOGGER.info(String.format(IHandlerService.CustomizeFormart,unSlowIntProtocl.getMerid(),session.getSessionId(),unSlowIntProtocl.getSendorderid())+"����ʼ����....");
            InterfaceRule rule = configContextManager.searchRuleContext(unSlowIntProtocl.getMerid());
            String rulexml = rule.getUnrechargerequest();
            return customizeConverter.buildPotoclCustomize(unSlowIntProtocl,interfaceConfig,rulexml);
        }
    }

    /**
     * ���ɳ�ֵ����
     * @param session
     * @return
     */
    private HttpData toRechargeRequest(UpstreamSession session) {
        SlowIntProctol slowIntProctol = new SlowIntProctol(session.getContents().split(" +"));
        InterfaceConfig interfaceConfig = configContextManager.searchConfigContext(slowIntProctol.getMerid());
        RechargeType rechargeType = getRechargeType(interfaceConfig);
        boolean common = interfaceConfig.getInterfacetype();
        if(common){
            return commonConverter.getRechargeRequestCommon(slowIntProctol, interfaceConfig, rechargeType);
        }else {
            LOGGER.info(String.format(IHandlerService.CustomizeFormart,slowIntProctol.getMerid(),session.getSessionId(),slowIntProctol.getSendorderid())+"����ʼ����....");
            InterfaceRule rule = configContextManager.searchRuleContext(slowIntProctol.getMerid());
            String rulexml = rule.getRechargerequest();
            return customizeConverter.buildPotoclCustomize(slowIntProctol,interfaceConfig,rulexml);
        }
    }
}
