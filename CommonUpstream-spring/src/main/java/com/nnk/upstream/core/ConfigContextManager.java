package com.nnk.upstream.core;

import com.nnk.upstream.exception.NNKSQLException;
import com.nnk.upstream.service.InterfaceManager;
import com.nnk.upstream.vo.InterfaceConfig;
import com.nnk.upstream.vo.InterfaceRule;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/14
 * Time: 10:55
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */

public class ConfigContextManager{

    private static final Logger LOGGER = Logger.getLogger(ConfigContextManager.class);
    @Autowired
    private InterfaceManager interfaceManager;


    /**
     * ��������������
     * @param Merchantno
     * @return
     */
    public void updateConfigContext(String Merchantno){

        try {

            List<InterfaceConfig> list = interfaceManager.find(Merchantno,null);//�������ݿ��Ӧ��������
            for(InterfaceConfig config:list){
                update(config);
            }
        } catch (NNKSQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * ��������������
     * @param Merchantno
     * @return
     */
    public void updateRuleContext(String Merchantno){

        try {

            List<InterfaceRule> list = interfaceManager.findRule(Merchantno);//�������ݿ��Ӧ��������
            for(InterfaceRule rule:list){
                updateRule(rule);
            }
        } catch (NNKSQLException e) {
            e.printStackTrace();
        }
    }

    private void updateRule(InterfaceRule config) {
        if(!RuleContext.containsKey(config.getMerchantno())){
            LOGGER.info("����" + config);
            RuleContext.put(config.getMerchantno(), config);
        }else {
            InterfaceRule contextConfig = RuleContext.get(config.getMerchantno());
            if(contextConfig == null){
                LOGGER.warn("interfaceRule contextConfig is null");
            }
            //����и��������
            if(!contextConfig.getRechargerequest().equals(config.getRechargerequest())
                    ||!contextConfig.getRechargeresponse().equals(config.getRechargeresponse())
                    ||!contextConfig.getQueryrequest().equals(config.getQueryrequest())
                    ||!contextConfig.getQueryresponse().equals(config.getQueryresponse())
                    ||!contextConfig.getBalanceresponse().equals(config.getBalanceresponse())
                    ||!contextConfig.getBanlancerequest().equals(config.getBanlancerequest())
                    ||!contextConfig.getCallbackrequest().equals(config.getCallbackrequest())
                    ||!contextConfig.getCallbackresponse().equals(config.getCallbackresponse())
                    ||!contextConfig.getUnrechargerequest().equals(config.getUnrechargerequest())
                    ||!contextConfig.getUnrechargeresponse().equals(config.getUnrechargeresponse())){

                RuleContext.put(config.getMerchantno(),config);
                LOGGER.info("����Э�����" + config);
            }
        }
    }

    public List<InterfaceConfig> listAll(){
        try {
            return interfaceManager.findAll();
        } catch (NNKSQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * ���������в��Ҷ�Ӧ��������
     * @param Merchantno
     * @return
     */
    public InterfaceConfig searchConfigContext(String Merchantno){
        try {
            if(ConfigContext.containsKey(Merchantno)){
                return ConfigContext.get(Merchantno);
            }else {
                List<InterfaceConfig> list = interfaceManager.find(Merchantno,null);
                for(InterfaceConfig config:list){
                    if(Merchantno.equals(config.getMerchantno())){
                        ConfigContext.put(config.getMerchantno(),config);
                        LOGGER.info("cconfig is saved in ConfigContext");
                        return config;
                    }
                }
            }
        } catch (NNKSQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ���������в���Э��������
     * @param Merchantno
     * @return
     */
    public InterfaceRule searchRuleContext(String Merchantno){
        try {
            if(RuleContext.containsKey(Merchantno)){
                return RuleContext.get(Merchantno);
            }else {
                List<InterfaceRule> list = interfaceManager.findRule(Merchantno);
                for(InterfaceRule config:list){
                    if(Merchantno.equals(config.getMerchantno())){
                        RuleContext.put(config.getMerchantno(),config);
                        LOGGER.info("config is saved in RuleContext");
                        return config;
                    }
                }
            }
        } catch (NNKSQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * �߳�������
     */
    public void start(){
        LOGGER.info("�����������߳̿���");
        new ConfigContextThread().start();
    }
    private class ConfigContextThread implements Runnable {
        private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

        @Override
        public void run() {
            try {
                List<InterfaceConfig> list = interfaceManager.findAll();

                for (InterfaceConfig config : list) {
                    update(config);
                }
                List<InterfaceRule> rulelist = interfaceManager.findAllRule();
                for (InterfaceRule config : rulelist) {
                    updateRule(config);
                }
            } catch (NNKSQLException e) {
                e.printStackTrace();
            }
        }
        public void start(){
            executorService.scheduleAtFixedRate(this,0,5, TimeUnit.MINUTES);
        }
    }

    private void update(InterfaceConfig config) {
        if(!ConfigContext.containsKey(config.getMerchantno())){
            LOGGER.info("����" + config);
            ConfigContext.put(config.getMerchantno(), config);
        }else {
            InterfaceConfig contextConfig = ConfigContext.get(config.getMerchantno());
            if(contextConfig == null){
                LOGGER.warn("contextConfig is null");
            }
            //����и��������
            if(!contextConfig.getEncrykey().equals(config.getEncrykey())
                    ||!contextConfig.getRechargeurl().equals(config.getRechargeurl())
                    ||!contextConfig.getBanlanceurl().equals(config.getBanlanceurl())
                    ||!contextConfig.getRechargetype().equals(config.getRechargetype())
                    ||!contextConfig.getBackurl().equals(config.getBackurl())
                    ||!contextConfig.getQueryurl().equals(config.getQueryurl())
                    ||!contextConfig.getUnrechargeurl().equals(config.getUnrechargeurl())
                    ||(contextConfig.getIsexpire()!=config.getIsexpire())
                    ||!contextConfig.getExpiretime().equals(config.getExpiretime())
                    ||(contextConfig.getInterfacetype()!=config.getInterfacetype())){

                ConfigContext.put(config.getMerchantno(),config);
                LOGGER.info("����" + config);
            }
        }
    }
}