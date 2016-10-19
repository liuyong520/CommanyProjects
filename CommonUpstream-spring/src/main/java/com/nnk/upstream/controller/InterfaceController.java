package com.nnk.upstream.controller;


import com.alibaba.fastjson.JSONObject;
import com.nnk.interfacetemplate.common.StringUtil;
import com.nnk.upstream.core.ConfigContextManager;
import com.nnk.upstream.core.MsgSrvService;
import com.nnk.upstream.entity.parterner.*;
import com.nnk.upstream.service.IHandlerService;
import com.nnk.upstream.service.imp.HandlerServiceCustomize;
import com.nnk.upstream.service.imp.HandlerServiceImp;
import com.nnk.upstream.util.DateUtil;
import com.nnk.upstream.util.JsonUtil;
import com.nnk.upstream.util.ReflectUtils;
import com.nnk.upstream.util.SignSecurityUtlis;
import com.nnk.upstream.vo.InterfaceConfig;
import com.nnk.upstream.vo.InterfaceRule;
import com.nnk.utils.encry.MD5Util;
import nnk.msgsrv.server.MsgSrvLongConnector;
import nnk.msgsrv.server.MsgSrvShortConnector;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/4/13
 * Time: 16:11
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class InterfaceController {


    public static final Logger LOGGER = Logger.getLogger(InterfaceController.class);
    @Autowired
    private HandlerServiceImp handlerService;
    @Autowired
    private HandlerServiceCustomize handlerServiceCustomize;
    @Autowired
    private ConfigContextManager configContextManager;
    private boolean allflag = false;
    private boolean merichanoflag = false;
    private boolean onlyflag = false;
    private Test testall;
    private Test testOnly;
    private Test testMerchNo;
    //
    @RequestMapping("/Upstream/recharge")
    public String rechargesuccess(HttpServletRequest request, RechargeRequest rechargeRequest, HttpServletResponse response) throws IOException {
        LOGGER.info(String.format(IHandlerService.Formart, rechargeRequest.getMerchantNo(), rechargeRequest.getSendorderId())
                + "接收充值请求：\n" + rechargeRequest);
        String src = ReflectUtils.getkeyValueString(rechargeRequest, "=", "&", "sign");
        InterfaceConfig config = configContextManager.searchConfigContext(rechargeRequest.getMerchantNo());
        RechargeResponse rechargeResponse = null;
        int rand = new Random().nextInt(11);
        if(SignSecurityUtlis.checkSign(src, config.getEncrykey(), rechargeRequest.getSign())) {

            switch (rand){
                case 0: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","0000","成功","SUCCESS");break;
                case 1: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","0000","成功","FAIL");break;
                case 2: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","0001","签名错误","FAIL");break;
                case 3: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","0002","请求参数错误","FAIL");break;
                case 4: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","0004","IP受限","FAIL");break;
                case 5: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","0005","订单号重复","FAIL");break;
                case 6: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","0006","余额不足","FAIL");break;
                case 7: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","0007","运营商升级","FAIL");break;
                case 8: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","0008","号码问题","FAIL");
                case 9: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","9999","系统异常","FAIL");break;
                case 10: rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                        rechargeRequest.getSendorderId(),"222222222","9999","系统异常","SUCCESS");break;
            }


        }else {
            rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                    rechargeRequest.getSendorderId(),"222222222","0001","失败","FAIL");
        }
        String srcStr = ReflectUtils.getkeyValueString(rechargeResponse, "=", "&", "sign");
        String sign = SignSecurityUtlis.sign(srcStr,config.getEncrykey());
        rechargeResponse.setSign(sign);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JsonUtil.buildJson(rechargeResponse));
        return null;
    }
    @RequestMapping("/Upstream/Fail")
    public String rechargefail(HttpServletRequest request, RechargeRequest rechargeRequest, HttpServletResponse response) throws IOException {
        LOGGER.info(String.format(IHandlerService.Formart, rechargeRequest.getMerchantNo(), rechargeRequest.getSendorderId())
                + "接收充值请求：\n" + rechargeRequest);
        String src = ReflectUtils.getkeyValueString(rechargeRequest, "=", "&", "sign");
        InterfaceConfig config = configContextManager.searchConfigContext(rechargeRequest.getMerchantNo());
        RechargeResponse rechargeResponse = null;
        if(SignSecurityUtlis.checkSign(src, config.getEncrykey(), rechargeRequest.getSign())) {
            rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                    rechargeRequest.getSendorderId(),"222222222","0002","失败","FAIL");

        }else {
            rechargeResponse = new RechargeResponse(rechargeRequest.getMerchantNo(),
                    rechargeRequest.getSendorderId(),"222222222","0001","失败","FAIL");
        }
        String srcStr = ReflectUtils.getkeyValueString(rechargeResponse, "=", "&", "sign");
        String sign = SignSecurityUtlis.sign(srcStr,config.getEncrykey());
        rechargeResponse.setSign(sign);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JsonUtil.buildJson(rechargeResponse));
        return null;
    }

    @RequestMapping("/Upstream/query")
    public String query(HttpServletRequest request, QueryRequest queryRequest, HttpServletResponse response) throws IOException {
        LOGGER.info(String.format(IHandlerService.Formart, queryRequest.getMerchantNo(), queryRequest.getSendorderId())
                + "接收查询请求：\n" + queryRequest);
        String src = ReflectUtils.getkeyValueString(queryRequest, "=", "&", "sign");
        InterfaceConfig config = configContextManager.searchConfigContext(queryRequest.getMerchantNo());
        QueryResponse queryResponse = null;

        if(SignSecurityUtlis.checkSign(src, config.getEncrykey(), queryRequest.getSign())) {

            queryResponse = new QueryResponse(queryRequest.getMerchantNo(),
                    queryRequest.getSendorderId(),"222222222","0000","成功","ORDERNOTEXIST",queryRequest.getRechargeValue());

        }else {
            queryResponse = new QueryResponse(queryRequest.getMerchantNo(),
                    queryRequest.getSendorderId(),"222222222","0001","失败","FAIL","0");
        }
        String srcStr = ReflectUtils.getkeyValueString(queryResponse, "=", "&", "sign");
        String sign = SignSecurityUtlis.sign(srcStr,config.getEncrykey());
        queryResponse.setSign(sign);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JsonUtil.buildJson(queryResponse));
        return null;
    }

    @RequestMapping("/Upstream/banlance")
    public String balance(HttpServletResponse response, BalanceRequest balanceRequest) throws IOException {
        BalanceResponse rechargeResponse = new BalanceResponse();
        InterfaceConfig config = configContextManager.searchConfigContext(balanceRequest.getMerchantNo());
        String src = ReflectUtils.getkeyValueString(balanceRequest,"=","&","sign");
        if(SignSecurityUtlis.checkSign(src, config.getEncrykey(), balanceRequest.getSign())) {
            rechargeResponse.setMerchantNo(balanceRequest.getMerchantNo());
            rechargeResponse.setRespCode("0000");
            rechargeResponse.setRespMsg("成功");
            rechargeResponse.setBalance("1000000");
        }else{
            rechargeResponse.setMerchantNo(balanceRequest.getMerchantNo());
            rechargeResponse.setRespCode("0001");
            rechargeResponse.setRespMsg("失败");
            rechargeResponse.setBalance("0");
        }
        response.setCharacterEncoding("utf-8");
        String srcStr = ReflectUtils.getkeyValueString(rechargeResponse, "=", "&", "sign");
        String sign = SignSecurityUtlis.sign(srcStr,config.getEncrykey());
        rechargeResponse.setSign(sign);
        response.getWriter().write(JsonUtil.buildJson(rechargeResponse));
        return null;
    }

    @RequestMapping("/Upstream/unrecharge")
    @ResponseBody
    public String unrecharge(HttpServletResponse response, UnRechargeRequest unRechargeRequest) throws IOException {
        LOGGER.info(String.format(IHandlerService.Formart, unRechargeRequest.getMerchantNo(), unRechargeRequest.getSendorderId())
                + "接收冲正：\n" + unRechargeRequest);
        String src = ReflectUtils.getkeyValueString(unRechargeRequest, "=", "&", "sign");
        InterfaceConfig config = configContextManager.searchConfigContext(unRechargeRequest.getMerchantNo());
        UnRechargeResponse unRechargeResponse = null;
        if(SignSecurityUtlis.checkSign(src, config.getEncrykey(), unRechargeRequest.getSign())) {
            unRechargeResponse = new UnRechargeResponse(unRechargeRequest.getMerchantNo(),
                    unRechargeRequest.getUnslowIntOrderId(),unRechargeRequest.getSendorderId(),"222222222","0000","成功","SUCCESS");

        }else {
            unRechargeResponse = new UnRechargeResponse(unRechargeRequest.getMerchantNo(),
                    unRechargeRequest.getUnslowIntOrderId(),unRechargeRequest.getSendorderId(),"222222222","0001","失败","FAIL");
        }
        String srcStr = ReflectUtils.getkeyValueString(unRechargeResponse, "=", "&", "sign");
        String sign = SignSecurityUtlis.sign(srcStr,config.getEncrykey());
        unRechargeResponse.setSign(sign);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JsonUtil.buildJson(unRechargeResponse));
        return null;
    }

    /**
     * <p>common protocl callback entry </p>
     * @param response
     * @param callbackRequest
     * @return
     * @throws IOException
     */
    @RequestMapping("/Upstream/Callback")
    @ResponseBody
    public String Callback(HttpServletResponse response, CallbackRequest callbackRequest) throws IOException {
        CallbackResponse callbackResponse = new CallbackResponse();
        if(!callbackRequest.vilidateData()){
            callbackResponse.setRespCode("0003");
            callbackResponse.setRespMsg("请求数据错误");
        }else {
            LOGGER.info(String.format(IHandlerService.Formart, callbackRequest.getMerchantNo(), callbackRequest.getSendorderId())
                    + "接收回调请求：\n" + callbackRequest);
            InterfaceConfig interfaceConfig = configContextManager.searchConfigContext(callbackRequest.getMerchantNo());
            String key = interfaceConfig.getEncrykey();
            if (handlerService.notifyCallback(callbackRequest)) {
                callbackResponse.setRespCode("0000");
                callbackResponse.setRespMsg("成功");
            } else {
                callbackResponse.setRespCode("0001");
                callbackResponse.setRespMsg("签名错误");
            }
            String srcStr = ReflectUtils.getkeyValueString(callbackResponse, "=", "&", "sign");
            String sign = SignSecurityUtlis.sign(srcStr,interfaceConfig.getEncrykey());
            callbackResponse.setSign(sign);
        }
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(JsonUtil.buildJson(callbackResponse));
        return null;
    }

    /***
     * Get request query string, form method : post
     *
     * @param request
     * @return byte[]
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        InputStream in = request.getInputStream();
        BufferedInputStream bufin = new BufferedInputStream(in);
        int buffSize = 1024;
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);

        byte[] temp = new byte[1024];
        int size = 0;
        while ((size = bufin.read(temp)) != -1) {
            out.write(temp, 0, size);
        }
        bufin.close();

        byte[] content = out.toByteArray();
        return content;
    }

    /***
     * Get request query string, form method : post
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }
    /**
     * Compatible with GET and POST
     *
     * @param request
     * @return : <code>String</code>
     * @throws IOException
     */
    public static String getRequestQueryStr(HttpServletRequest request,
                                            String charEncoding) throws IOException {
        String submitMehtod = request.getMethod();
        if (submitMehtod.equalsIgnoreCase("post")) {
            byte[] bytes = getRequestPostBytes(request);
            String charEncoding2 = request.getCharacterEncoding();// charset
            if(StringUtils.isEmpty(charEncoding)){
                charEncoding=charEncoding2;
            }
            LOGGER.debug("recieve Bytes[]:"+bytes);
            return new String(bytes, charEncoding);
        } else {// form method :Get
            return request.getQueryString();
        }
    }
    @RequestMapping(value = "/Upstream/coustomizeCallbackUtf",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String Callbackutf(HttpServletResponse response,HttpServletRequest request) throws IOException{
       return Callback(response,request);
    }
    /**
     * <p>coustomize protocl callback entry </p>
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/Upstream/coustomizeCallback")
    @ResponseBody
    public String Callback(HttpServletResponse response,HttpServletRequest request) throws IOException {
        try {
            InputStream is = request.getInputStream();
            String contentType = request.getContentType();
            //contentType:application/x-www-form-urlencoded; charset=UTF-8
            boolean form = true;
            String resp = null;
            Map<String, String[]> requestMap = null;
            Map<String, String> map = new HashMap<String, String>();

            if (request.getMethod().equalsIgnoreCase("get") ||
                    StringUtil.isNotEmpty(contentType) && (contentType.contains("form") || contentType.contains("text/html"))) {
                requestMap = request.getParameterMap();
                for (Map.Entry<String, String[]> entry : requestMap.entrySet()) {
                    String[] contents = entry.getValue();
                    //如果有?号需要再分割一次
                    String content = contents[0];
                    if (content.contains("?")) {
                        String value[] = content.split("\\?", 2);
                        map.put(entry.getKey(), value[0]);
                        String subvalue = value[1];
                        if (subvalue.contains("=")) {
                            String[] keyvalue = subvalue.split("=", 2);
                            if (keyvalue.length == 2) {
                                map.put(keyvalue[0], keyvalue[1]);
                            } else {
                                map.put(keyvalue[0], "");
                            }
                        }
                    } else {
                        map.put(entry.getKey(), content);
                    }
                }
                LOGGER.info("\n回调通知,requestMap:" + map + "\ncontentType:" + contentType);

            } else {
                resp = StreamUtils.copyToString(is, Charset.defaultCharset());
                LOGGER.info("\n回调通知,resp:" + resp + "\ncontentType:" + contentType);
                form = false;
            }
            String merid = request.getParameter("merid");
            if (merid.contains("?")) {
                LOGGER.debug("merid 格式不规范，格式规范化...");
                merid = merid.substring(0, merid.indexOf("?"));
            }
            LOGGER.info("回调通知,merid:" + merid);
            if (map != null && map.containsKey(merid)) {
                map.remove(merid);
            }
            InterfaceRule rule = configContextManager.searchRuleContext(merid);
            InterfaceConfig config = configContextManager.searchConfigContext(merid);
            if (!form && StringUtil.isEmpty(resp)) {
                return "";
            }
            if (form && requestMap == null) {
                return "";
            }
            return handlerServiceCustomize.notifyCallback(resp, rule, config, merid, map);
        }catch (Exception e){
            e.printStackTrace();
            return "OK";
        }
    }


    @RequestMapping("/upstream/delay")
    @ResponseBody
    public String testDelay(HttpServletResponse response, RechargeRequest rechargeRequest) throws IOException, InterruptedException {

        CallbackResponse callbackResponse = new CallbackResponse();
        InterfaceConfig interfaceConfig = configContextManager.searchConfigContext(rechargeRequest.getMerchantNo());
        String key = interfaceConfig.getEncrykey();
        Thread.sleep(1200000);
        String srcStr = ReflectUtils.getkeyValueString(callbackResponse, "=", "&", "sign");
        String encryStr = MD5Util.getMD5String(srcStr + key);
        callbackResponse.setSign(encryStr);
        response.getWriter().write(JsonUtil.buildJson(callbackResponse));
        return null;
    }

    @RequestMapping("/RechargeTestAll")
    @ResponseBody
    public String RechargeTestAll(HttpServletResponse response) throws IOException, InterruptedException {

        final String date = DateUtil.format(new Date());
       synchronized (this) {
           if(!allflag) {
               List<InterfaceConfig> list = configContextManager.listAll();
               final String sendorderid = DateUtil.format(new Date())+new Random().nextInt(1000);
               testall = new Test(new ScheduledThreadPoolExecutor(100), list,null,"13267191379","5000");
               testall.scheduleAtFixedRate();
               allflag = true;
           }
       }
        response.setCharacterEncoding("GBK");
        response.getWriter().print("测试已经开始");
        return null;
    }
    @RequestMapping("/RechargeTest")
    @ResponseBody
    public String RechargeTest(final HttpServletResponse response, @RequestParam(value = "merchantno") final String merchantno) throws IOException, InterruptedException {

        final String date = DateUtil.format(new Date());

        synchronized (this) {
            if (!merichanoflag) {
                InterfaceConfig  cofig = new InterfaceConfig();
                cofig.setMerchantno(merchantno);
                final String sendorderid = DateUtil.format(new Date())+new Random().nextInt(1000);
                testMerchNo = new Test(new ScheduledThreadPoolExecutor(100), Arrays.asList(cofig),null,"13267191379","5000");
                testMerchNo.scheduleAtFixedRate();

                merichanoflag = true;
            }
        }
        response.setCharacterEncoding("GBK");
        response.getWriter().print("测试已经开始");
        return null;
    }

    @RequestMapping("/RechargeTestOnly")
    @ResponseBody
    public String RechargeTestOnly(HttpServletResponse response,
                                  @RequestParam(value = "merchantno1") final String merchantno1,
                                  @RequestParam(value = "sendorderId") final String sendorderId,
                                  @RequestParam(value = "mob") final String mob,
                                  @RequestParam(value = "value") final String value) throws IOException, InterruptedException {


        synchronized (this) {
//           if(!onlyflag) {
               InterfaceConfig  cofig = new InterfaceConfig();
               cofig.setMerchantno(merchantno1);
               testOnly = new Test(new ScheduledThreadPoolExecutor(100), Arrays.asList(cofig),sendorderId,mob,value);
               testOnly.start();
//               onlyflag = true;
//           }
        }
        response.setCharacterEncoding("GBK");
        response.getWriter().print("测试已经开始");
        return null;
    }
    @RequestMapping("/cancerTest")
    @ResponseBody
    public String cancerTest(HttpServletResponse response) throws IOException, InterruptedException {
        LOGGER.info("cancrTest 测试");
        synchronized (this){
            allflag = false;
            onlyflag = false;
            merichanoflag = false;
            if(testall!=null){
                LOGGER.info("停止所有测试");
                testall.stop();
            }
            if(testOnly!=null){
                LOGGER.info("停止单笔测试");
                testOnly.stop();
            }
            if(testMerchNo!=null){
                LOGGER.info("停止代理商测试");
                testMerchNo.stop();
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", 1);
            jsonObject.put("content", "success");
            return jsonObject.toString();
        }
    }


    private static class Test implements Runnable{
        private boolean start =true;
        private ScheduledThreadPoolExecutor excuteSerivce;
        List<InterfaceConfig> list;
        MsgSrvLongConnector msgSrvLongConnector;
        String sendorderid;
        String mobile;
        String value;
        private Test( ScheduledThreadPoolExecutor excuteSerivce, List<InterfaceConfig> list,String sendorderid,String mobile,String value) {
            this.start = true;
            this.excuteSerivce = excuteSerivce;
            this.list = list;
            msgSrvLongConnector = MsgSrvService.get("start");
            if(sendorderid == null){
                this.sendorderid = DateUtil.format(new Date())+new Random().nextInt(1000);
            }else {
                this.sendorderid = sendorderid;
            }
            this.mobile = mobile;
            this.value = value;
        }

        @Override
        public void run() {
            try {
                final String date = DateUtil.format(new Date());
                for (InterfaceConfig config : list) {
                    MsgSrvShortConnector connector = new MsgSrvShortConnector("config/msgsrv-short.xml");
                    connector.send(msgSrvLongConnector.getConnector().getMyAppName() + " SlowInt 0 " + sendorderid + " "
                                    + config.getMerchantno() + " " + "200000000000013 2010052016304839 1 " + value + " 105 广东 22 NA NA " + mobile + " " + date + " NA NA"
                    );
                }
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (InterfaceConfig config : list) {
                    MsgSrvShortConnector connector = new MsgSrvShortConnector("config/msgsrv-short.xml");
                    connector.send(msgSrvLongConnector.getConnector().getMyAppName() + " SlowInt 0 " + sendorderid + " "
                                    + config.getMerchantno() + " " + "200000000000013 2010052016304839 1 " + value + " 105 广东 11 NA NA " + mobile + " " + date + " NA NA"
                    );
                }
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (InterfaceConfig config : list) {
                    MsgSrvShortConnector connector = new MsgSrvShortConnector("config/msgsrv-short.xml");
                    String sendorderid1 = "UN" + sendorderid;
                    connector.send(msgSrvLongConnector.getConnector().getMyAppName() + " UnSlowInt 0 " + sendorderid1 + " "
                                    + config.getMerchantno() + " " + "200000000000013 2010052016304839 1 " + value + " 105 广东 22 NA NA " + mobile + " " + date + " NA NA " + sendorderid
                    );
                    connector.send(msgSrvLongConnector.getConnector().getMyAppName() + " GetAccount 20130711 " + config.getMerchantno() + " 20130711112522");
                }
            }catch (Exception e){
                e.printStackTrace();
                stop();
            }
        }
        public void start(){
            Logger.getLogger(this.getClass()).info("测试线程启动");
            excuteSerivce.submit(this);
        }
        public void scheduleAtFixedRate(){
            Logger.getLogger(this.getClass()).info("测试线程启动");
            excuteSerivce.scheduleAtFixedRate(this, 0, 60, TimeUnit.SECONDS);
        }
        public void stop(){

           if(!excuteSerivce.isShutdown()){
               Logger.getLogger(this.getClass()).info("停止线程");
               excuteSerivce.shutdownNow();

           }
            try {
                Thread.sleep(5000);
                Logger.getLogger(this.getClass()).info("线程状态：" + excuteSerivce.isShutdown());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        public void setstop(boolean stop){
            start = stop;
            if(!start){
                stop();
            }
        }
    }



}
