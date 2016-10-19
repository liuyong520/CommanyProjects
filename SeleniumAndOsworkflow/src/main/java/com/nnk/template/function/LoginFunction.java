package com.nnk.template.function;

import com.nnk.template.Appliaction;
import com.nnk.template.entity.ObtainImgCodeRequest;
import com.nnk.template.entity.SMSCodeIndex;
import com.nnk.template.entity.SMSCodeRequest;
import com.nnk.template.selenium.SeleniumUtils;
import com.nnk.template.util.DateUtils;
import com.nnk.utils.http.utils.StringUtil;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import nnk.msgsrv.server.MsgSrvShortConnector;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/10
 * Time: 18:43
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class LoginFunction extends FunctionProviderAdaptor{
    public static final Logger log = Logger.getLogger(LoginFunction.class);

    //userName userPwd SMSYZMLocation IMGYZMLocation SMSYZMInput IMGYZMInput login

    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String userName = (String) propertySet.getString("userName");
        String userPwd = (String) propertySet.getString("userPwd");
        String cookiesfileName = propertySet.getString("cookiesFileName");

        log.info("userName:" + userName);
        log.info("usePwd:" + userPwd);
        log.info("cookiesfileName:" + cookiesfileName);
        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        if(!StringUtils.isEmpty(cookiesfileName)) {
            try {
                SeleniumUtils.addCookiesFromFile(webDriver, cookiesfileName);
                propertySet.setObject(Contstant.WEBDRIVER,webDriver);
                propertySet.setString("loginResult","success");
            }catch (Exception e){
                e.printStackTrace();
                login(args, propertySet, userName, userPwd, webDriver);
            }

        }else {
            if (webDriver == null) {
                log.warn("webDriver is not set");
            }
            login(args, propertySet, userName, userPwd, webDriver);
        }
    }

    private void login(Map args, PropertySet propertySet, String userName, String userPwd, WebDriver webDriver) throws WorkflowException{
        String elementUserName = (String) args.get("userName");
        String elementUserPwd = (String) args.get("userPwd");
        String elememtSMSYZMLocation = (String) args.get("SMSYZMLocation");
        String elementIMGYZMLocation = (String) args.get("IMGYZMLocation");
        String elememtSMSYZMInput = (String) args.get("SMSYZMInput");
        String elementIMGYZMInput = (String) args.get("IMGYZMInput");
        log.debug("elememtSMSYZMLocation:" + elememtSMSYZMLocation);
        log.debug("elementIMGYZMLocation:" + elementIMGYZMLocation);
        log.debug("elememtSMSYZMInput:" + elememtSMSYZMInput);
        log.debug("elementIMGYZMInput:" + elementIMGYZMInput);
        String elementLogin = (String) args.get("login");
        SeleniumUtils.setInput(SeleniumUtils.findElement(webDriver, elementUserName), userName);
        SeleniumUtils.setInput(SeleniumUtils.findElement(webDriver, elementUserPwd), userPwd);
        boolean isNeedSMSYZM = false;
        boolean isNeedIMGYZM = false;
        if (!StringUtils.isEmpty(elememtSMSYZMLocation) && !StringUtils.isEmpty(elememtSMSYZMInput)) {
            isNeedSMSYZM = true;
            log.info("Need SMS Code !");
        }
        if (!StringUtils.isEmpty(elementIMGYZMLocation) && !StringUtils.isEmpty(elementIMGYZMInput)) {
            isNeedIMGYZM = true;
            log.info("Need IMG　Code !");
        }
        if (isNeedIMGYZM) {
            //获取图片验证码
            WebElement IMGYZMLocation = SeleniumUtils.findElement(webDriver, elementIMGYZMLocation);
            try {
                String ImgStr = SeleniumUtils.getScreenShotByElementAsString(webDriver, IMGYZMLocation);
                log.info("Imgstr:" + ImgStr);
                MsgSrvShortConnector connector = new MsgSrvShortConnector("config/msgsrv-CodeTran.xml");
                connector.setWaitTime(120000);
                while (true) {
                    String sn = System.currentTimeMillis() + "";
                    String imageMerid = (String) args.get("imageMerid");
                    String imageType = (String) args.get("imageType");
                    String imageRemark = (String) args.get("imageRemark");
                    if(StringUtils.isEmpty(imageMerid))throw new WorkflowException("args imageMerid is not set");
                    if(StringUtils.isEmpty(imageType))throw new WorkflowException("args imageType is not set");
                    if(StringUtils.isEmpty(imageRemark))throw new WorkflowException("args imageRemark is not set");
                    ObtainImgCodeRequest request = new ObtainImgCodeRequest(sn, "1", imageMerid, DateUtils.parseDate("yyyyMMddHHmmss", new Date()), "60000", imageRemark, imageType, ImgStr);
                    String msg = StringUtil.getAppendStrByValue(request, "", " ");
                    String retmsg = connector.send("CodeTran CodeRecognition " + msg);
                    if (StringUtils.isEmpty(retmsg) || retmsg.contains("Error Application")) {
                        log.warn("No response from CodeTran");
                    }
                    String[] retcontents = retmsg.split(" +");
                    String retcode = retcontents[4];
                    String result = retcontents[5];
                    log.info("retcode:" + retcode + ",result:" + result);
                    if (Contstant.IMGSUCCESS.equals(retcode)) {
                        log.info("recieved code result:" + result);
                        WebElement ImgInput = SeleniumUtils.findElement(webDriver, elementIMGYZMInput);
                        SeleniumUtils.setInput(ImgInput, result);
                        break;
                    } else if (Contstant.IMGFAIL.equals(retcode)) {
                        log.info("recognise code result failed:" + result);
                    }
                    Thread.sleep(5000);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (isNeedSMSYZM) {
            //获取短信验证码
            WebElement SMSYZMLocation = SeleniumUtils.findElement(webDriver, elememtSMSYZMLocation);
            SeleniumUtils.click(SMSYZMLocation);
            log.info("SMSSend success");
            String timeOut = (String) args.get(Contstant.TIMEOUT);
            String myphone = (String) args.get("myphone");
            String regex = (String) args.get("regex");
            if(StringUtils.isEmpty(timeOut)||StringUtils.isEmpty(myphone)||StringUtils.isEmpty(regex)) throw new WorkflowException("sms args timeout,myphone,index must be set");
            Long time = Long.parseLong(timeOut);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<SMSCodeRequest> messageList = Appliaction.SMSCODECACHE.get(myphone);
            try {
                String Smscode = getSMSCodeMatchedByRegex(messageList,regex);
                if(StringUtils.isNotEmpty(Smscode)) {
                    log.info("recieved sms code result:" + Smscode);
                    WebElement ImgInput = SeleniumUtils.findElement(webDriver, elememtSMSYZMInput);
                    SeleniumUtils.setInput(ImgInput, Smscode);
                }else{
                    log.info("recognise sms code result failed:" + Smscode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SeleniumUtils.click(SeleniumUtils.findElement(webDriver, elementLogin));
        SeleniumUtils.saveCookiesFile(webDriver, "cookies.txt");
        propertySet.setString("cookiesFileName", "cookies.txt");
        propertySet.setString("loginResult","success");
    }
    private String getSMSCodeMatchedByRegex(List<SMSCodeRequest> messageList, String regex) {
        Pattern pattern = Pattern.compile(regex);
        for(SMSCodeRequest message:messageList){
            String fullMessage = message.getFullShortMsg();
            if(StringUtils.isNotEmpty(fullMessage)) {
                Matcher matcher = pattern.matcher(fullMessage);
                if(matcher.find()&&matcher.groupCount()>=1) {
                    String content = matcher.group(1);
                    if(Appliaction.SMSTASKCACHE.size()==0) {
                        //全部清除
                        Appliaction.SMSCODECACHE.remove(message.getMyPhone());
                    }else {
                        Appliaction.SMSCODECACHE.get(message.getMyPhone()).remove(message);
                        Integer taskid = Appliaction.SMSTASKCACHE.get(message.getMyPhone());
                        taskid --;
                        Appliaction.SMSTASKCACHE.put(message.getMyPhone(),taskid);
                    }
                    return content;
                }
            }
        }
        return null;
    }
}
