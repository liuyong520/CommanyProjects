package com.nnk.upstream.service.imp;

import com.nnk.interfacetemplate.common.StringUtil;
import com.nnk.interfacetemplate.common.XmlUtil;
import com.nnk.upstream.convert.imp.CustomizeProtoclConverter;
import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.entity.mapping.Mapdata;
import com.nnk.upstream.entity.mapping.XmlModel;
import com.nnk.upstream.entity.parterner.Request;
import com.nnk.upstream.entity.proxy.ProtoclWrapper;
import com.nnk.upstream.entity.self.SlowIntProctol;
import com.nnk.upstream.entity.self.UnSlowIntProtocl;
import com.nnk.upstream.entity.self.UpstreamSession;
import com.nnk.upstream.service.IHandlerService;
import com.nnk.upstream.service.RedisCacheManager;
import com.nnk.upstream.util.*;
import com.nnk.upstream.vo.InterfaceConfig;
import com.nnk.upstream.vo.InterfaceRule;
import com.nnk.utils.http.exception.NetWorkException;
import com.nnk.utils.http.utils.HttpClientUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/5/30
 * Time: 13:18
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//�Զ���Э�鴦����
@Component
public class HandlerServiceCustomize implements IHandlerService {
    private static final String POST ="POST" ;
    private static final String GET ="GET" ;
    public static final Logger LOGGER = Logger.getLogger(HandlerServiceCustomize.class);
    @Autowired
    private HttpClientUtils httpClientUtils;
    @Autowired
    private ConfigContextManager configContextManager;
    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    public boolean notifyCallback(Request callbackRequest) {

        return false;
    }
    public static String buildCallbackResponse(String ruleReponseXml,InterfaceConfig config) throws JAXBException, IOException {
        if(StringUtil.isEmpty(ruleReponseXml)){
            return "success";
        }else {
            XmlModel xmlModel = XmlUtil.parseXml(XmlModel.class,ruleReponseXml);
            List<Mapdata> mapdataList = xmlModel.getMapdata();
            //xml�ַ����в��ܺ���&�ַ����߻��д���
            String protocl = xmlModel.getProtocl().replaceAll("@","&");//Э�鴮
            if(StringUtil.isEmpty(protocl)){
                return "success";
            }
            //�Ƿ���Ҫ��ǩ��
            Map valueMap = new HashMap();
            //ǩ���ֶ�
            String sign = null;
            String key = null;
            String password = null;
            if (xmlModel.getMapdata()==null||xmlModel.getMapdata().isEmpty()){
                LOGGER.info("�Զ���Э�鷵�أ�" + protocl);
                return protocl;
            }
            for (Mapdata mapdata:xmlModel.getMapdata()){//��ȡ��Ӧӳ���ϵ
                //����ǳ���
                if(CustomizeProtoclConverter.CONSTANT.equals(mapdata.getType())){
                    String value = mapdata.getDefaultvalue();
                    if(StringUtil.isNotEmpty(value)){
                        if("NA".equals(value)){
                            protocl =protocl.replace("$"+mapdata.getName(),"");
                            valueMap.put(mapdata.getName(),"");
                        }else {
                            protocl = protocl.replace("$" + mapdata.getName(), value);
                            valueMap.put(mapdata.getName(), value);
                        }
                    }else{
                        String nnkname = mapdata.getNnkname();
                        String methodname = "get" + org.apache.commons.lang.StringUtils.capitalize(nnkname);
                        String value1 = (String) ReflectUtils.invokeMethodName(config,methodname,null,null);
                        protocl =protocl.replace("$"+mapdata.getName(),value1);
                        valueMap.put(mapdata.getName(),value1);
                    }
                    // ������ַ�������
                }else if(CustomizeProtoclConverter.SIGN.equals(mapdata.getType())){
                    sign = mapdata.getName();
                    String nnkname = mapdata.getNnkname();
                    String methodname = "get" +  org.apache.commons.lang.StringUtils.capitalize(nnkname);
                    LOGGER.debug("����Method:"+methodname +"��InterfaceConfig�л�ȡ��Կ...");
                    key = (String) ReflectUtils.invokeMethodName(config,methodname,null,null);
                    LOGGER.debug("key:"+key);
                    //���ܵ�keyҲ��Ҫ�Ž�ȥ
                    valueMap.put(mapdata.getNnkname(),key);
                }else if(CustomizeProtoclConverter.PASSWORD.equals(mapdata.getType())){
                    password = mapdata.getDefaultvalue();
                }
            }
            if(xmlModel.isEncryOrCheckSign()) {
                //��ȡ�����㷨����
                String encryptionType = xmlModel.getEncryptionType();
                //��ȡ����˳��
                String encrySqueques = xmlModel.getSignSquence();
                String[] encrySquequeArray = encrySqueques.split(",");
                //��ȡ���ܸ�ʽ��
                String encryFormat = xmlModel.getSignFormart().replaceAll("@", "&");
                String charset = xmlModel.getCharset();
                for (String encryname : encrySquequeArray) {
                    String value = (String) valueMap.get(encryname);
                    encryFormat = encryFormat.replace("$" + encryname, value);
                    LOGGER.debug("encryname��" + encryname + ",value��" + value);
                }

                String encryptionSign = EncrytionFactory.sign(encryFormat, key,password, encryptionType, charset);
                if (CustomizeProtoclConverter.UPPERCASE.equals(xmlModel.getEncryptionCase())) {
                    encryptionSign = encryptionSign.toUpperCase();
                } else if (CustomizeProtoclConverter.LOWCASE.equals(xmlModel.getEncryptionCase())) {
                    encryptionSign = encryptionSign.toLowerCase();
                } else if (CustomizeProtoclConverter.CAPITAL.equals(xmlModel.getEncryptionCase())) {
                    encryptionSign = org.apache.commons.lang.StringUtils.capitalize(encryptionSign);
                } else {
                    encryptionSign = encryptionSign.toLowerCase();
                }
                LOGGER.debug("ǩ��ԭ����" + encryFormat + ",ǩ����" + encryptionSign);
                protocl = protocl.replace("$" + sign, encryptionSign);
            }
            LOGGER.info("�Զ���Э�鷵�أ�" + protocl);

            return protocl;
        }
    }


    public String notifyCallback(String resp,InterfaceRule rule,InterfaceConfig config,String merid,Map respmap) {

        String ruleRequestXml = rule.getCallbackrequest();
        LOGGER.debug("ruleRequestXml:"+ruleRequestXml);

        if(StringUtil.isEmpty(ruleRequestXml)){
            return "fail";
        }
        String sessionid = UUID.randomUUID().toString().replace("-","");
        if(StringUtil.isEmpty(merid)){
            merid = config.getMerchantno();
        }
        try {
            XmlModel requestXmlModel = XmlUtil.parseXml(XmlModel.class,ruleRequestXml);
            String regexSuccess = requestXmlModel.getRegexSuccess();
            String regexFail = requestXmlModel.getRegexFail();
            Map map = null;
            //���respmap ��Ϊ��
            if(respmap != null&&!respmap.isEmpty()){
                map = respmap;
            }else {
                if (StringUtil.isNotEmpty(regexSuccess) && Pattern.matches(regexSuccess, resp)) {
                    //����Э�����ͻ�ȡmap
                    String protoclType = requestXmlModel.getProtoclType();//
                    map = MapUtils.getResultMap(resp, protoclType);
                    //ʧ�ܷ������ݸ�ʽƥ��
                } else if (StringUtil.isNotEmpty(regexFail) && Pattern.matches(regexFail, resp)) {
                    String protoclType = requestXmlModel.getExceptionProtoclType();//
                    map = MapUtils.getResultMap(resp, protoclType);
                }
            }
                String squence = requestXmlModel.getSignSquence();
                String[] encrySquequeArray = squence.split(",");
                String encryptionType = requestXmlModel.getEncryptionType();
                //��ȡǩ��ԭ��
                String signFormart = requestXmlModel.getSignFormart();
                signFormart = signFormart.replace("@","&");
                for (String encryname : encrySquequeArray){
                    String value = null;
                    if(encryname.equals("encrykey")){
                        String metodname = "getEncrykey";
                        value= (String) ReflectUtils.invokeMethodName(config,metodname,null,null);
                        LOGGER.debug("��ȡ����key��"+value);
                    }else {
                        Object obj = MapUtils.getObject(map,encryname);
                        if (obj != null) {
                            if (obj instanceof String[]) {
                                String[] contents = (String[]) obj;
                                value = contents[0];
                            } else {
                                value = (String) obj;
                            }
                        }
                    }
                    String key = "$" + encryname;
                    if (signFormart.contains(key)) {
                        //�����key�����滻Ϊ�գ������ظ���key
                        if(encryname.equals("encrykey")){
                            signFormart = signFormart.replace(key, "");
                        }else {
                            signFormart = signFormart.replace(key, value);
                        }
                    } else {
                        LOGGER.warn("encryption key name :" + key + " is not exsit");
                    }

                }
                List<Mapdata> mapdataList = requestXmlModel.getMapdata();
                if(mapdataList.isEmpty()){
                    return "";
                }
                String sign = null;
                String result = null;
                String realvalue = null;
                String sendorderid = null;
                String oemorderid = null;
                String responseCode = null;
                String password = null;
                for (Mapdata mapdata:mapdataList){
                    //key.key1.key2
                    String name = mapdata.getName();
                    String nnkname = mapdata.getNnkname();
                    if(CustomizeProtoclConverter.PASSWORD.equals(mapdata.getType())){
                        password = mapdata.getDefaultvalue();
                    }
                    if("sign".equals(nnkname)){
                        sign = (String)  MapUtils.getObject(map,name);
                    }else if("result".equals(nnkname)){
                        result = (String)  MapUtils.getObject(map,name);
                    }else if("realvalue".equals(nnkname)){
                        realvalue = (String)  MapUtils.getObject(map,name);
                    }else if("sendorderid".equals(nnkname)){
                        sendorderid = (String)  MapUtils.getObject(map,name);
                    }else if("orderid".equals(nnkname)){
                        oemorderid = (String)  MapUtils.getObject(map,name);
                    }else if("responseCode".equals(nnkname)){
                        responseCode = (String)  MapUtils.getObject(map,name);
                    }
                }
            boolean checksignflag = false;
            //�����Ҫ��ǩ
            if(requestXmlModel.isEncryOrCheckSign()){

                if(EncrytionFactory.checkSign(signFormart,config.getEncrykey(),password,encryptionType,requestXmlModel.getCharset(),sign,requestXmlModel.getEncryptionCase())){
                    checksignflag = true;
                }else {
                    LOGGER.info(String.format(CustomizeFormart,merid,sessionid,sendorderid)+"�ص�֪ͨ��ǩʧ�ܣ����账��");
                }
            }else {
                LOGGER.info(String.format(CustomizeFormart,merid,sessionid,sendorderid)+"û��ǩ����������ǩ");
                checksignflag = true;
            }
            if(StringUtil.isNotEmpty(sendorderid)&&redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP,sendorderid)){
                redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP,sendorderid);
            }
            List resonseSuccessCodeSetList = StringUtils.splitToList(requestXmlModel.getResponseSuccessCodeSet(), "\\|");
            List responseFailCodeSetList = StringUtils.splitToList(requestXmlModel.getResponseFailCodeSet(), "\\|");
            List successCodeSetList = StringUtils.splitToList(requestXmlModel.getSuccessCodeSet(),"\\|");
            List failCodeSetList = StringUtils.splitToList(requestXmlModel.getFailCodeSet(),"\\|");



           if(checksignflag){

            //�����ǩ�ɹ�
                if(resonseSuccessCodeSetList!=null
                        && (resonseSuccessCodeSetList.contains("*")||resonseSuccessCodeSetList.contains(responseCode))){
                    if(successCodeSetList!=null
                            &&(successCodeSetList.contains("*")||successCodeSetList.contains(result))){
                        LOGGER.info(String.format(CustomizeFormart,merid,sessionid,sendorderid)+"״̬��:" + result +"��ֵ�ɹ�");
                        MsgSrvResponseUtils.responseCallbackSuccess(sendorderid,merid,realvalue,oemorderid,DateUtil.format(new Date()),"Success");
                    }else if(failCodeSetList!=null
                            &&(failCodeSetList.contains("*")||failCodeSetList.contains(result))){
                        LOGGER.info(String.format(CustomizeFormart,merid,sessionid,sendorderid)+"״̬��:" + result +"��ֵʧ��");
                        MsgSrvResponseUtils.responseCallbackFail(sendorderid,merid,realvalue,oemorderid,DateUtil.format(new Date()),"fail");
                    }else {
                        LOGGER.info(String.format(CustomizeFormart,merid,sessionid,sendorderid)+"״̬��:" + result +"δ֪״̬�룬��ֵ�����ȷ��");
                    }
                }else if(responseFailCodeSetList!=null
                        && (responseFailCodeSetList.contains("*")||responseFailCodeSetList.contains(responseCode))){
                    LOGGER.info(String.format(CustomizeFormart, merid, sessionid, sendorderid) + "״̬��:" + responseCode + "��Ӧʧ�ܣ�����ظ���");
                }else{
                    LOGGER.warn(String.format(CustomizeFormart, merid, sessionid, sendorderid) + "״̬��δ����ƥ�䣬����Э�����״̬������");
                }


            }
            String ruleResponseXml = rule.getCallbackresponse();
            String response = buildCallbackResponse(ruleResponseXml,config);
            return response;
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * <p>handler balance request</p>
     * @param upstreamSession nnk's session
     * @param request opp parterner's protocl
     * @throws RuntimeException
     * @throws NetWorkException
     */
    @Override
    public void queryBalance(UpstreamSession upstreamSession, Request request) throws NetWorkException {
        String[] contents = upstreamSession.getContents().split(" +");
        String merid = contents[1];
        InterfaceConfig config = configContextManager.searchConfigContext(merid);
        if(StringUtil.isEmpty(config.getBanlanceurl())){
            return;
        }
        String resp = httpRequest(request,config.getBanlanceurl());
        if(StringUtil.isEmpty(resp)){
            throw new NetWorkException("���ݷ����쳣");
        }
        InterfaceRule rule = configContextManager.searchRuleContext(merid);
        hanlderBalanceResponce(resp, rule, upstreamSession);

    }

    private void hanlderBalanceResponce(String resp, InterfaceRule rule, UpstreamSession upstreamSession) {
        String rulexml = rule.getBalanceresponse();
        String methodName = "handlerBalanceResponseEx";
        handlerResponceByMethodName(resp, upstreamSession, rulexml, methodName);
    }
    /**
     * <p>handler the banlance response,by HandlerServiceCustomize#handlerResponceByMethodName invoked
     * @see HandlerServiceCustomize#handlerResponceByMethodName </p>
     * @param upstreamSession nnk's session
     * @param xmlModel xml's rule
     * @param map response map
     */
    private void handlerBalanceResponseEx(UpstreamSession upstreamSession, XmlModel xmlModel, Map map) {
        String[] contents = upstreamSession.getContents().split(" +");
        String merid = contents[1];
        String sessionid = upstreamSession.getSessionId();
        List<Mapdata> mapdatas = xmlModel.getMapdata();
        //��Ӧ��
        String responseCode=null;
        //��ֵ״̬
        String rechargeCode = null;
        String balance = null;
        //��������
        String changeFactor = null;
        for(Mapdata mapdata:mapdatas){
            if(mapdata.getNnkname().equals("responseCode")){
                //key.key1.key2.key3
                String keyname = mapdata.getName();
                responseCode = (String) MapUtils.getObject(map, keyname);
            }else if(mapdata.getNnkname().equals("result")){
                String keyname = mapdata.getName();
                rechargeCode = (String) MapUtils.getObject(map,keyname);
            }else if(mapdata.getNnkname().equals("balance")){
                String keyname = mapdata.getName();
                balance = (String) MapUtils.getObject(map,keyname);
                changeFactor = mapdata.getDefaultvalue();
            }
        }
        balance = StringUtil.isEmpty(balance)?"0":balance;

        Integer chageFactorNumber = StringUtil.isEmpty(changeFactor)?1:Integer.parseInt(changeFactor);
        LOGGER.info(String.format(CustomizeBalanceFormart,merid,sessionid)+"���Ϊ��" + balance +"ת�����ӣ�" + chageFactorNumber);

        Double balanceNumber = Double.parseDouble(balance);
        balanceNumber = balanceNumber*chageFactorNumber;
        Integer balanceExtra = balanceNumber.intValue();

        LOGGER.debug("ת��֮���Ϊ��" + balanceExtra);
        //��Ӧ�����
        String resonseSuccessCodeSet = xmlModel.getResponseSuccessCodeSet();
        List resonseSuccessCodeSetList = StringUtils.splitToList(resonseSuccessCodeSet, "\\|");
        List responseFailCodeSetList = StringUtils.splitToList(xmlModel.getResponseFailCodeSet(), "\\|");
        List successCodeSetList = StringUtils.splitToList(xmlModel.getSuccessCodeSet(),"\\|");
        List failCodeSetList = StringUtils.splitToList(xmlModel.getFailCodeSet(),"\\|");
        if(resonseSuccessCodeSetList!=null
                && (resonseSuccessCodeSetList.contains("*")||resonseSuccessCodeSetList.contains(responseCode))){
            if(successCodeSetList!=null
                    &&(successCodeSetList.contains("*")||successCodeSetList.contains(rechargeCode))){
                LOGGER.info(String.format(CustomizeBalanceFormart,merid,sessionid)+"[����ѯ�ɹ�],���Ϊ��" + balance );

                MsgSrvResponseUtils.responseBalanceSuccess(upstreamSession,balanceExtra+"");
            }else if(failCodeSetList!=null
                    &&(failCodeSetList.contains("*")||failCodeSetList.contains(rechargeCode))){
                LOGGER.info(String.format(CustomizeBalanceFormart,merid,sessionid)+"[����ѯʧ��]��״̬�룺" + rechargeCode );
                MsgSrvResponseUtils.responseBalanceFail(upstreamSession);
            }
        }else if(responseFailCodeSetList!=null
                && (responseFailCodeSetList.contains("*")||responseFailCodeSetList.contains(responseCode))){
            LOGGER.info(String.format(CustomizeBalanceFormart,merid,sessionid)+"[����ѯʧ��]��״̬�룺" + rechargeCode );
            MsgSrvResponseUtils.responseBalanceFail(upstreamSession);
        }
    }

    /**
     * <p>handler unrecharge request</p>
     * @param upstreamSession nnk's session
     * @param request opp parterner's protocl
     * @throws RuntimeException
     * @throws NetWorkException
     */
    @Override
    public void unRecharge(UpstreamSession upstreamSession, Request request) throws RuntimeException, NetWorkException {
        UnSlowIntProtocl unSlowIntProtocl = new UnSlowIntProtocl(upstreamSession);
        InterfaceConfig config = configContextManager.searchConfigContext(unSlowIntProtocl.getMerid());
        if(StringUtil.isEmpty(config.getUnrechargeurl())){
            return;
        }
        String resp = httpRequest(request,config.getUnrechargeurl());
        if(StringUtil.isEmpty(resp)){
            throw new NetWorkException("���ݷ����쳣");
        }
        InterfaceRule rule = configContextManager.searchRuleContext(unSlowIntProtocl.getMerid());
        String rulexml = rule.getUnrechargeresponse();
        hanlderUnrechargeResponce(resp, rule, upstreamSession);
    }

    private void hanlderUnrechargeResponce(String resp, InterfaceRule rule, UpstreamSession upstreamSession) {
        String rulexml = rule.getUnrechargeresponse();
        String methodName = "handlerUnrechargeResponseEx";
        handlerResponceByMethodName(resp, upstreamSession, rulexml, methodName);
    }
    /**
     * <p>handler the unrecharge response,by HandlerServiceCustomize#handlerResponceByMethodName invoked
     * @see HandlerServiceCustomize#handlerResponceByMethodName </p>
     * @param upstreamSession nnk's session
     * @param xmlModel xml's rule
     * @param map response map
     */
    private void handlerUnrechargeResponseEx(UpstreamSession upstreamSession, XmlModel xmlModel, Map map) {
        String sessionid = upstreamSession.getSessionId();
        SlowIntProctol slowint = new SlowIntProctol(upstreamSession.getContents());
        List<Mapdata> mapdatas = xmlModel.getMapdata();
        //��Ӧ��
        String responseCode=null;
        //��ֵ״̬
        String rechargeCode = null;
        for(Mapdata mapdata:mapdatas){
            if(mapdata.getNnkname().equals("responseCode")){
                //key.key1.key2.key3
                String keyname = mapdata.getName();
                responseCode = (String) MapUtils.getObject(map, keyname);
            }else if(mapdata.getNnkname().equals("result")){
                String keyname = mapdata.getName();
                rechargeCode = (String) MapUtils.getObject(map,keyname);
            }
        }
        //��Ӧ�����
        String resonseSuccessCodeSet = xmlModel.getResponseSuccessCodeSet();
        List resonseSuccessCodeSetList= StringUtils.splitToList(resonseSuccessCodeSet, "\\|");
        List responseFailCodeSetList= StringUtils.splitToList(xmlModel.getResponseFailCodeSet(), "\\|");

        List successCodeSetList = StringUtils.splitToList(xmlModel.getSuccessCodeSet(),"\\|");
        List failCodeSetList = StringUtils.splitToList(xmlModel.getFailCodeSet(),"\\|");
        if(resonseSuccessCodeSetList!=null
                && (resonseSuccessCodeSetList.contains("*")||resonseSuccessCodeSetList.contains(responseCode))){
            if(successCodeSetList!=null
                    &&(successCodeSetList.contains("*")||successCodeSetList.contains(rechargeCode))){
                LOGGER.info(String.format(CustomizeFormart,slowint.getMerid(),sessionid,slowint.getSendorderid())+"[�����ɹ�]" );
                MsgSrvResponseUtils.responseUnslowIntSuccess(upstreamSession);
            }else if(failCodeSetList!=null
                    &&(failCodeSetList.contains("*")||failCodeSetList.contains(rechargeCode))){
                LOGGER.info(String.format(CustomizeFormart,slowint.getMerid(),sessionid,slowint.getSendorderid())+"[����ʧ��]��״̬�룺" + rechargeCode );
                MsgSrvResponseUtils.responseUnslowIntFail(upstreamSession);
            }
        }else if(responseFailCodeSetList!=null
                && (responseFailCodeSetList.contains("*")||responseFailCodeSetList.contains(responseCode))){
            LOGGER.info(String.format(CustomizeFormart,slowint.getMerid(),sessionid,slowint.getSendorderid())+"[����ʧ��]��״̬�룺" + responseCode );
            MsgSrvResponseUtils.responseUnslowIntFail(upstreamSession);
        }
    }
    /**
     * <p>handler recharge request</p>
     * @param upstreamSession nnk's session
     * @param request opp parterner's protocl
     * @throws RuntimeException
     * @throws NetWorkException
     */
    @Override
    public void recharge(UpstreamSession upstreamSession, Request request) throws NetWorkException, RuntimeException {
        SlowIntProctol slowIntProctol = new SlowIntProctol(upstreamSession.getContents());
        InterfaceConfig config = configContextManager.searchConfigContext(slowIntProctol.getMerid());
        if(StringUtil.isEmpty(config.getRechargeurl())){
            return;
        }
        String resp = httpRequest(request,config.getRechargeurl());
        if(StringUtil.isEmpty(resp)){
            throw new NetWorkException("���ݷ����쳣");
        }
        //����״̬
        updateRedisPotoclStatus(slowIntProctol);
        try {
            InterfaceRule rule = configContextManager.searchRuleContext(slowIntProctol.getMerid());
            hanlderRecharegResponce(resp, rule, upstreamSession);
        }catch (Exception e){
            throw new RuntimeException("�����ֵ���󷵻ؽ���쳣");
        }
    }
    /**
     * <p>handler query request</p>
     * @param upstreamSession nnk's session
     * @param request opp parterner's protocl
     * @throws RuntimeException
     * @throws NetWorkException
     */
    @Override
    public void query(UpstreamSession upstreamSession, Request request) throws NetWorkException, RuntimeException {
        SlowIntProctol slowIntProctol = new SlowIntProctol(upstreamSession.getContents());
        InterfaceConfig config = configContextManager.searchConfigContext(slowIntProctol.getMerid());
        if(StringUtil.isEmpty(config.getQueryurl())){
            return;
        }
        String resp = httpRequest(request,config.getQueryurl());
        if(StringUtil.isEmpty(resp)){
            throw new NetWorkException("���ݷ����쳣");
        }
        updateRedisPotoclStatus(slowIntProctol);
        try {
            InterfaceRule rule = configContextManager.searchRuleContext(slowIntProctol.getMerid());
            hanlderQueryResponce(resp, rule, upstreamSession);
        }catch (Exception e){
            throw new RuntimeException("�����ѯ���ؽ���쳣");
        }
    }

    private void hanlderQueryResponce(String resp, InterfaceRule rule, UpstreamSession upstreamSession) {
        String rulexml = rule.getQueryresponse();
        String methodName = "handlerQueryResponseEx";
        handlerResponceByMethodName(resp, upstreamSession, rulexml, methodName);
    }

    /**
     * <p>handler the query response,by HandlerServiceCustomize#handlerResponceByMethodName invoked
     * @see HandlerServiceCustomize#handlerResponceByMethodName </p>
     * @param upstreamSession nnk's session
     * @param xmlModel xml's rule
     * @param map response map
     */
    private void handlerQueryResponseEx(UpstreamSession upstreamSession, XmlModel xmlModel ,Map map) {
        String sessionid = upstreamSession.getSessionId();
        SlowIntProctol slowint = new SlowIntProctol(upstreamSession.getContents());
        String sendorderid = slowint.getSendorderid();
        List<Mapdata> mapdatas = xmlModel.getMapdata();
        //��Ӧ��
        String responseCode=null;
        //�����̶�����
        String oemOrderid = null;
        //��ֵ״̬
        String rechargeCode = null;
        //���˽��
        String realvalue = null;
        //��Ӧ��
        String responseAndResult = null;
        boolean responseCodeAsResult = false;
        for(Mapdata mapdata:mapdatas){
            //��Ӧ״̬
            if(mapdata.getNnkname().equals("responseCode")){
                //key.key1.key2.key3
                String keyname = mapdata.getName();
                responseCode = (String) MapUtils.getObject(map, keyname);
            }else if(mapdata.getNnkname().equals("orderid")){
                String keyname = mapdata.getName();
                oemOrderid = (String) MapUtils.getObject(map,keyname);
                //����״̬
            }else if(mapdata.getNnkname().equals("result")){
                String keyname = mapdata.getName();
                rechargeCode = (String) MapUtils.getObject(map,keyname);

            }else if(mapdata.getNnkname().equals("realvalue")){
                String keyname = mapdata.getName();
                realvalue = (String) MapUtils.getObject(map,keyname);
                //
            }else if(mapdata.getNnkname().equals("responseAndResult")){
                String condition = mapdata.getDefaultvalue();
                if(condition.equals("result")&&rechargeCode==null) {
                    LOGGER.info("������resultΪ��ֵʱ��responseCode �������ʹ������");
                    responseAndResult = responseCode;
                    responseCodeAsResult = true;
                }
            }
        }
        oemOrderid = StringUtil.isEmpty(oemOrderid)?"NA":oemOrderid;
        realvalue = StringUtil.isEmpty(realvalue)?slowint.getValue():realvalue;
        //��Ӧ�����
        String resonseSuccessCodeSet = xmlModel.getResponseSuccessCodeSet();
        List resonseSuccessCodeSetList = StringUtils.splitToList(resonseSuccessCodeSet, "\\|");
        List successCodeSetList = StringUtils.splitToList(xmlModel.getSuccessCodeSet(),"\\|");
        List failCodeSetList = StringUtils.splitToList(xmlModel.getFailCodeSet(),"\\|");
        List uncertainCodeSetList = StringUtils.splitToList(xmlModel.getUncertainCodeSet(),"\\|");

        if(resonseSuccessCodeSetList!=null
                && (resonseSuccessCodeSetList.contains("*")||resonseSuccessCodeSetList.contains(responseCode))){
            if(rechargeCode == null && responseCodeAsResult){
                rechargeCode = responseAndResult;
                LOGGER.info("Condition:result is null,result ��responseCode����ȡ��Ϊ��"+ rechargeCode);
            }
            if(successCodeSetList!=null
                    &&(successCodeSetList.contains("*")||successCodeSetList.contains(rechargeCode))){
                LOGGER.info(String.format(CustomizeFormart,slowint.getMerid(),sessionid,slowint.getSendorderid())+"[��ֵ�ɹ�]��״̬�룺" + rechargeCode );
                //�Ƴ�����
                if(redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP,sendorderid)){
                    redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP,sendorderid);
                }
                MsgSrvResponseUtils.responseCallbackSuccess(upstreamSession,realvalue,oemOrderid,DateUtil.format(new Date()),"SUCCESS");
            }else if(failCodeSetList!=null
                    &&(failCodeSetList.contains("*")||failCodeSetList.contains(rechargeCode))){
                LOGGER.info(String.format(CustomizeFormart,slowint.getMerid(),sessionid,slowint.getSendorderid())+"[��ֵʧ��]��״̬�룺" + rechargeCode );
                //�Ƴ�����
                if(redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP,sendorderid)){
                    redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP,sendorderid);
                }
                MsgSrvResponseUtils.responseCallbackFail(upstreamSession,"0",oemOrderid,DateUtil.format(new Date()),"FAIL");
            }else if(uncertainCodeSetList!=null
                    &&(uncertainCodeSetList.contains("*")||uncertainCodeSetList.contains(rechargeCode))){
                LOGGER.info(String.format(CustomizeFormart,slowint.getMerid(),sessionid,slowint.getSendorderid())+"[��ֵ��ȷ��]��״̬�룺" + rechargeCode );
            }
        }
    }

    /**
     * <p>communication Method,http communication,according the request#getMethodType to choose the method
     * support HTTPPOST and HTTPGET,
     * @see com.nnk.utils.http.utils.HttpClientUtils#doPost(String, com.nnk.utils.http.interfaces.HttpData) dan
     * @see com.nnk.utils.http.utils.HttpClientUtils#doGet(String) </p>
     * @param request
     * @param url
     * @return
     * @throws NetWorkException
     */
    private String httpRequest(Request request,String url) throws NetWorkException {

        String resp = null;
        if(request instanceof ProtoclWrapper){
            ProtoclWrapper wrapperRequest = (ProtoclWrapper) request;
            LOGGER.info("wrapperRequest:"+wrapperRequest);
            if(POST.equals(wrapperRequest.getMethodType())){
                resp = httpClientUtils.doPost(url,wrapperRequest);
            }else if(GET.equals(wrapperRequest.getMethodType())){
                String dogetUrl = url+"?"+wrapperRequest.getGetdata();
                resp =  httpClientUtils.doGet(dogetUrl);
            }
            LOGGER.info("Cunstomize protocl��������:" + resp);
        }
        return resp;
    }

    private void hanlderRecharegResponce(String resp,InterfaceRule rule,UpstreamSession upstreamSession){
        String rulexml = rule.getRechargeresponse();

        String methodName = "handlerRechargeResponseEx";
        handlerResponceByMethodName(resp, upstreamSession, rulexml, methodName);
    }

    private void handlerResponceByMethodName(String resp, UpstreamSession upstreamSession, String rulexml, String methodName) {
        try {
            XmlModel xmlModel = XmlUtil.parseXml(XmlModel.class, rulexml);
            String regexSuccess = xmlModel.getRegexSuccess();
            String regexFail = xmlModel.getRegexFail();
            String sessionid = upstreamSession.getSessionId();
            //�ɹ��ܹ�ƥ�����
            LOGGER.info("��ˮ��"+ sessionid + "������ʽ:" + regexSuccess + "���ؽ������ʼ....");
            resp = resp.replaceAll("\\n","");
            if (StringUtil.isNotEmpty(regexSuccess) && Pattern.matches(regexSuccess, resp)) {
                LOGGER.info("��ˮ��" + sessionid + "Э���������أ�[ƥ��]�ɹ�");
                //����Э�����ͻ�ȡmap
                String protoclType = xmlModel.getProtoclType();//
                Map map = MapUtils.getResultMap(resp, protoclType);
                ReflectUtils.invokeMethodName(this, methodName,
                        new Class[]{UpstreamSession.class, XmlModel.class, Map.class},
                        upstreamSession, xmlModel, map);
                //ʧ�ܷ������ݸ�ʽƥ��
            } else if (StringUtil.isNotEmpty(regexFail) && Pattern.matches(regexFail, resp)) {
                LOGGER.info("��ˮ��" + sessionid + "Э���쳣���أ�[ƥ��]�ɹ�");
                String protoclType = xmlModel.getExceptionProtoclType();//
                Map map = MapUtils.getResultMap(resp, protoclType);
                ReflectUtils.invokeMethodName(this,methodName,
                        new Class[]{UpstreamSession.class,XmlModel.class,Map.class},
                        upstreamSession,xmlModel,map);
            }else {
                LOGGER.info("��ˮ��" + sessionid + "Э�����κ�ƥ���߼��������쳣...");
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * <p>handler the recharge response,by HandlerServiceCustomize#handlerResponceByMethodName invoked
     * @see HandlerServiceCustomize#handlerResponceByMethodName </p>
     * @param upstreamSession nnk's session
     * @param xmlModel xml's rule
     * @param map response map
     */
    private void handlerRechargeResponseEx(UpstreamSession upstreamSession, XmlModel xmlModel, Map map) {
        SlowIntProctol slowint = new SlowIntProctol(upstreamSession.getContents());
        String sessionid = upstreamSession.getSessionId();
        String sendorderid = slowint.getSendorderid();
        List<Mapdata> mapdatas = xmlModel.getMapdata();
        //��Ӧ��
        String responseCode=null;
        //�����̶�����
        String oemOrderid = null;
        //��ֵ״̬
        String rechargeCode = null;
        for(Mapdata mapdata:mapdatas){
            if(mapdata.getNnkname().equals("responseCode")){
                //key.key1.key2.key3
                String keyname = mapdata.getName();
                responseCode = (String) MapUtils.getObject(map, keyname);
                LOGGER.debug("responseCode:"+responseCode);
            }else if(mapdata.getNnkname().equals("orderid")){
                String keyname = mapdata.getName();
                oemOrderid = (String) MapUtils.getObject(map,keyname);
                LOGGER.debug("oemOrderid:"+oemOrderid);
            }else if(mapdata.getNnkname().equals("result")){
                String keyname = mapdata.getName();
                rechargeCode = (String) MapUtils.getObject(map,keyname);
                LOGGER.debug("rechargeCode:"+rechargeCode);
            }
        }
        //��Ӧ�����
        String resonseSuccessCodeSet = xmlModel.getResponseSuccessCodeSet();
        List resonseSuccessCodeSetList = StringUtils.splitToList(resonseSuccessCodeSet, "\\|");
        List responseFailCodeSetList = StringUtils.splitToList(xmlModel.getResponseFailCodeSet(), "\\|");
        List successCodeSetList = StringUtils.splitToList(xmlModel.getSuccessCodeSet(),"\\|");
        List failCodeSetList = StringUtils.splitToList(xmlModel.getFailCodeSet(),"\\|");
        if(resonseSuccessCodeSetList!=null
                && (resonseSuccessCodeSetList.contains("*")||resonseSuccessCodeSetList.contains(responseCode))){
            LOGGER.debug(String.format(CustomizeFormart,slowint.getMerid(),sessionid,sendorderid)+"��Ӧ�ɹ�");
            if(successCodeSetList!=null
                    &&(successCodeSetList.contains("*")||successCodeSetList.contains(rechargeCode))){
                LOGGER.info(String.format(CustomizeFormart,slowint.getMerid(),sessionid,sendorderid)+"[�µ��ɹ�]��״̬�룺" + rechargeCode );
                MsgSrvResponseUtils.responseBrokeSuccess(upstreamSession, oemOrderid);
            }else if(failCodeSetList!=null
                    &&(failCodeSetList.contains("*")||failCodeSetList.contains(rechargeCode))){
                LOGGER.info(String.format(CustomizeFormart,slowint.getMerid(),sessionid,sendorderid)+"[�µ�ʧ��]��״̬�룺" + rechargeCode );
                MsgSrvResponseUtils.responseBrokeFail(upstreamSession);
            }

            if(redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP,sendorderid)){
                redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP,sendorderid);
            }
        }else if(responseFailCodeSetList!=null
                && (responseFailCodeSetList.contains("*")||responseFailCodeSetList.contains(responseCode))){
            LOGGER.info(String.format(CustomizeFormart,slowint.getMerid(),sessionid,sendorderid)+"[�µ�ʧ��]����Ӧ״̬�룺" + responseCode );
            MsgSrvResponseUtils.responseBrokeFail(upstreamSession);
            if(redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP,sendorderid)){
                redisCacheManager.removeKeys(RedisCacheManager.WAITTINGMAP,sendorderid);
            }
        }
    }

    private void updateRedisPotoclStatus(SlowIntProctol slowIntProctol) {
        if (redisCacheManager.existKey(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid())) {//����յ��ظ��Ƴ���������
            slowIntProctol.setSendStatus(SlowIntProctol.SendStatus.SENDEDANDRECVED);
            redisCacheManager.add2Map(RedisCacheManager.WAITTINGMAP, slowIntProctol.getSendorderid(), slowIntProctol);
        }
    }

}
