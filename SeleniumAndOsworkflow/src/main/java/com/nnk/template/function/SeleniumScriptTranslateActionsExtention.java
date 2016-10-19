package com.nnk.template.function;

import com.nnk.template.Appliaction;
import com.nnk.template.entity.ObtainImgCodeRequest;
import com.nnk.template.entity.OperationDescprition;
import com.nnk.template.entity.SMSCodeRequest;
import com.nnk.template.selenium.SeleniumUtils;
import com.nnk.template.util.DateUtils;
import com.nnk.utils.http.utils.StringUtil;
import com.nnk.utils.js.JsoupUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;
import nnk.msgsrv.server.MsgSrvShortConnector;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/10/17
 * Time: 14:34
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SeleniumScriptTranslateActionsExtention extends FunctionProviderAdaptor {
    public static final Logger log = Logger.getLogger(SeleniumScriptTranslateActionsExtention.class);
    @Override
    public void executeCatched(Map transvalues, Map args, PropertySet propertySet) throws WorkflowException {
        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        propertySet.setObject("optList",new LinkedList<OperationDescprition>());
        //获取文件地址
        String filename = (String) args.get("scriptFileName");
        try {
            pharseHtml(filename,propertySet);
            if(webDriver==null) {
                webDriver = creatWebDriver(propertySet);
            }
            List<OperationDescprition> operationDescpritions = (List<OperationDescprition>) propertySet.getObject("optList");
            Iterator it = operationDescpritions.iterator();
            while (it.hasNext()){
                OperationDescprition descprition = (OperationDescprition) it.next();
                //如果是条件语句
                if(descprition.isCondition()){
                    boolean ret = handler(descprition,propertySet,args,webDriver);
                    if(ret){
                        OperationDescprition right = descprition.getRight();
                        handler(right,propertySet,args,webDriver);
                        propertySet.setString("condition_result","true");
                    }else {
                        OperationDescprition left = descprition.getLeft();
                        handler(left,propertySet,args,webDriver);
                        propertySet.setString("condition_result","false");
                    }
                }else {
                    boolean iscanse = false;
                    do {

                        handler(descprition, propertySet,args,webDriver);
                        iscanse = descprition.isCascade();
                        descprition = descprition.getNext();
                    }while (iscanse && descprition!=null);
                }
            }
        } catch (Exception e){
            throw new WorkflowException(e);
        }
    }
    private boolean handler(OperationDescprition descprition,PropertySet context,Map args,WebDriver webDriver) throws WorkflowException, InterruptedException {
        log.info(descprition.toString());
        try {
            if (descprition.getOptName().equals("open")) {
                String baseUrl = context.getString("baseUrl");
                String url = baseUrl + descprition.getOptElement();
                webDriver.get(url);
                //普通输入
            } else if (descprition.getOptName().equals("type")) {
                WebElement webElement = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
                SeleniumUtils.clear(webElement);
                String value = descprition.getOptElementValue();
                //先正则匹配下是不是变量如果是变量从propertieset 中取值。
                value = getMatchedByRegex(context, value);
                SeleniumUtils.setInput(webElement, value);
                //点击操作
            } else if (descprition.getOptName().equals("click")) {
                WebElement webElement = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
                webElement = SeleniumUtils.changeElementAttributeByjs(webDriver, webElement);
                SeleniumUtils.click(webElement);
                //点击之后等待
            } else if (descprition.getOptName().equals("clickAndWait")) {
                WebElement webElement = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
                webElement = SeleniumUtils.changeElementAttributeByjs(webDriver, webElement);
                SeleniumUtils.click(webElement);
                Integer time = StringUtils.isEmpty(descprition.getOptElementValue()) ? 5000 : Integer.parseInt(descprition.getOptElementValue());
                SeleniumUtils.waitImplict(webDriver, time);
                Thread.sleep(1000);
                //图片验证码操作
            } else if (descprition.getOptName().equals("imglocation")) {
                //获取图片验证码
                WebElement IMGYZMLocation = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
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
                        if (StringUtils.isEmpty(imageMerid)) throw new WorkflowException("args imageMerid is not set");
                        if (StringUtils.isEmpty(imageType)) throw new WorkflowException("args imageType is not set");
                        if (StringUtils.isEmpty(imageRemark))
                            throw new WorkflowException("args imageRemark is not set");
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
                            setElementValue(context, descprition.getOptElementValue(), result);
                            break;
                        } else if (Contstant.IMGFAIL.equals(retcode)) {
                            log.info("recognise code result failed:" + result);
                        }
                        Thread.sleep(5000);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                //切换flame窗口
            } else if (descprition.getOptName().equals("selectFrame") || descprition.getOptName().equals("selectWindow")) {

                WebElement webElement = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
                SeleniumUtils.switchWindowToFrame(webDriver, webElement);
                //模拟鼠标点击
            } else if (descprition.getOptName().equals("actionClick")) {
                WebElement webElement = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
                SeleniumUtils.ActionMoveToElementAndClick(webDriver, webElement);
                //多选下拉框
            } else if (descprition.getOptName().equals("select")) {
                SelectElementByselct selectElementByselct = new SelectElementByselct();
                args.put(Contstant.ELEMENTNAME, descprition.getOptElement());
                args.put("elementValue", descprition.getOptElementValue());
                selectElementByselct.execute(null, args, context);
            } else if (descprition.getOptName().equals("dateformart")) {
                String element = descprition.getOptElement();
                String value = descprition.getOptElementValue();

                element = getMatchedByRegex(context, element);
                String dateformart = descprition.getOptElementValue2();
                String tranferDate = DateUtils.tranferDateFormat("yyyyMMdd", dateformart, element);
                setElementValue(context, value, tranferDate);
                //用js 输入
            } else if (descprition.getOptName().equals("jsType")) {
                String value = descprition.getOptElementValue();
                //正则表达式匹配${date}这种格式的数据
                value = getMatchedByRegex(context, value);
                WebElement webElement = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
                SeleniumUtils.setElementValueByJs(webDriver, webElement, value);
                //时间等待
            } else if (descprition.getOptName().equals("waitByTime")) {
                Integer time = StringUtils.isEmpty(descprition.getOptElementValue()) ? 5000 : Integer.parseInt(descprition.getOptElementValue());
                Thread.sleep(time);
                //点击短信验证码时需要提前执行这个操作
            } else if (descprition.getOptName().equals("smsClickBefore")) {
                String myphone = descprition.getOptElementValue();
                if (Appliaction.SMSTASKCACHE.containsKey(myphone)) {
                    int taskId = Appliaction.SMSTASKCACHE.get(myphone);
                    taskId++;
                    Appliaction.SMSTASKCACHE.put(myphone, taskId);
                } else {
                    Appliaction.SMSTASKCACHE.put(myphone, 1);
                }
                //获取短信验证码
            } else if (descprition.getOptName().equals("smsInput")) {
                //获取短信验证码
                String timeOut = (String) descprition.getOptType();
                String myphone = descprition.getOptElementValue();
                String regex = (String) descprition.getOptElementValue2();
                if (StringUtils.isEmpty(timeOut) || StringUtils.isEmpty(myphone) || StringUtils.isEmpty(regex))
                    throw new WorkflowException("sms args timeout,myphone,index must be set");
                Long time = Long.parseLong(timeOut);
                try {
                    Thread.sleep(time * 1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                List<SMSCodeRequest> messageList = Appliaction.SMSCODECACHE.get(myphone);
                try {
                    String SmsCode = getSMSCodeMatchedByRegex(messageList, regex);
                    if (StringUtils.isNotEmpty(SmsCode)) {
                        log.info("recieved sms code result:" + SmsCode);
                        WebElement ImgInput = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
                        SeleniumUtils.setInput(ImgInput, SmsCode);
                    } else {
                        log.info("recognise sms code result failed:" + SmsCode);
                        throw new WorkflowException("SmsCode is get null");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new WorkflowException("SmsCode is get null");
                }
                //跳转url
            } else if (descprition.getOptName().equals("getUrl")) {
                log.info("cookies:" + webDriver.manage().getCookies());
                webDriver.get(descprition.getOptElement());
                String url = webDriver.getCurrentUrl();
                log.info("currentUrl:" + url);
                //弹出对话话框
            } else if (descprition.getOptName().equals("assertAlert")) {
                WebElement webElement = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
                SeleniumUtils.clickAlert(webDriver, webElement);
                //获取某个元素的文本信息
            } else if (descprition.getOptName().equals("getText")) {
                WebElement webElement = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
                String value = SeleniumUtils.getText(webElement);
                String text = descprition.getOptElementValue();
                setElementValue(context,text,value);
                //获取某个元素的属性
            } else if (descprition.getOptName().equals("getAttribute")) {
                WebElement webElement = SeleniumUtils.findElement(webDriver, descprition.getOptElement());
                String value = SeleniumUtils.getAttribute(webElement, descprition.getOptElementValue());
                String text = descprition.getOptElementValue2();
                setElementValue(context,text,value);
            //切换回原来的窗口
            } else if (descprition.getOptName().equals("selectWindowsMain")) {
                webDriver.switchTo().defaultContent();
            }else if(descprition.getOptName().equals("Equal")){
                String text = descprition.getOptElement();
                String value = descprition.getOptElementValue();
                text = getMatchedByRegex(context,text);
                value = getMatchedByRegex(context,value);
                if(text.equals(value)){
                    return true;
                }else {
                    return false;
                }
                //给保存某个值到上下文中
            }else if(descprition.getOptElement().equals("propertySet")){
                String text = descprition.getOptElement();
                String value = descprition.getOptElementValue();
                setElementValue(context,text,value);
            }

        }catch (Exception e){
            log.error(e.getMessage(),e);
            return false;
        }
        return true;

    }

    public void pharseHtml(String file,PropertySet propertySet) throws IOException {
        String htmlContent = FileUtils.readFileToString(new File(file));
        Document document = JsoupUtils.parseHtmlByString(htmlContent);
                           
        Elements element = JsoupUtils.getElementsByTagName(document,"link");
        Element element1 = element.first();
        //获取访问浏览器的基础地址
        String baseUrl = element1.attr("href");
        propertySet.setString("baseUrl", baseUrl);
        log.info("baseUrl:" + baseUrl);
        log.info("begin to open the browser");
        log.info("open the browser and get baseUrl:" + baseUrl);
        Elements elements = JsoupUtils.getElementsByTagName(document, "tbody");

        Elements trs = JsoupUtils.getElementsByTagName(elements.first(),"tr");
        String t = JsoupUtils.getSingElementHtml(trs);
        int i = 0;
        for (Element tr :trs){
//            if(++i<=10)continue;
            parseHtmltrTag(tr,propertySet);
        }
       log.info(propertySet);
    }
    public static void parseHtmltrTag(Element tr,PropertySet context){
        int first = 0;
        boolean condition = false;
        boolean loop = false;
        boolean common = false;
        Elements elements = tr.children();
        Element firstNode = elements.first();
        String attr = firstNode.attr("type");
        if(StringUtil.isEmpty(attr)||"common".equals(attr)){
            common = true;
        }else if("condition".equals(attr)){
            condition=true;
        }else if("loop".equals(attr)){
            loop = true;
        }
        if(condition){
            parseTagtrCondtion(elements,context);
        }else if(loop){
            parseTagtrLoop(elements,context);
        }else if(common){
            parseTagtrCommon(elements,context);
        }
    }

    private static void parseTagtrCommon(Elements elements, PropertySet context) {
        //获取最后一次操作
        OperationDescprition lastest = getlastest(context);
        String[] parent = new String[4];
        OperationDescprition parentDescrition = null;
        int size = elements.size();
        System.out.println("size:" + size);
        int flag = 0;
        int k = 0;
        Stack<OperationDescprition> stack = new Stack<OperationDescprition>();
        for(Element element:elements){
            Elements ext = element.getElementsByTag("ext");
            if(ext==null||ext.size()==0){
                parent[k] = element.text();
                k++;
            }else {
                flag = 1;
                stack.clear();
                //如果有级联附属操作，且级联操作只能有一步
                Iterator it = ext.iterator();
                String[] tmp = new String[4];
                int i = 0;
                while(it.hasNext()){
                    Element extOpt = (Element) it.next();
                    tmp[i] = extOpt.text();
                    if(i>=3){
                        OperationDescprition extdecription = new OperationDescprition(tmp[0],"common",tmp[1],tmp[2],tmp[3]);
                        extdecription.setCascade(true);
                        //clear tmp;
                        tmp = new String[4];
                        i = 0;
                        //如果上级操作不为空
                        if(stack.isEmpty()){
                            stack.push(extdecription);
                        }else {
                            OperationDescprition extstart = stack.pop();
                            while (extstart.getNext()!=null){
                                 extstart = extstart.getNext();
                            }
                            extstart.setNext(extdecription);
                            extdecription.setLast(extstart);
                        }
                    }
                    i++;
                }
            }
            if(k == size - flag){
                parentDescrition = new OperationDescprition(parent[0],"common",parent[1],parent[2],parent[3]);
                parent = new String [4];
                k = 0;
            }
        }
        if(!stack.isEmpty()){
            OperationDescprition extstart = stack.pop();
            parentDescrition.setNext(extstart);
            parentDescrition.setCascade(true);
            extstart.setLast(parentDescrition);
        }
//        if(lastest!=null){
//            lastest.setNext(parentDescrition);
//            parentDescrition.setLast(lastest);
//        }
        List<OperationDescprition> list = (List<OperationDescprition>) context.getObject("optList");
        list.add(parentDescrition);
        context.setObject("optList",list);

    }
    private WebDriver creatWebDriver(PropertySet propertySet) {
        String filefox_path = propertySet.getString("fireFox.path");
        System.setProperty("webdriver.firefox.bin",filefox_path);
        WebDriver webDriver = new FirefoxDriver();
        propertySet.setObject(Contstant.WEBDRIVER,webDriver);
        webDriver.manage().window().maximize();
        log.info("create browser windows");
        return webDriver;
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

    private String getMatchedByRegex(PropertySet propertySet, String value) {
        String regex = "\\$\\{(.*?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if(matcher.find()&&matcher.groupCount()>=1) {
            String content = matcher.group(1);

            value = propertySet.getString(content);
            log.info("key:" + content +",value:" + value);
        }
        return value;
    }
    private void setElementValue(PropertySet propertySet, String text,String value) {
        String regex = "#\\{(.*?)\\}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if(matcher.find()&&matcher.groupCount()>=1) {
            String content = matcher.group(1);
            propertySet.setString(content,value);
            log.info("key:" + content +",value:" + text);
        }
    }


    private static void parseTagtrLoop(Elements elements, PropertySet context) {

    }
    private static OperationDescprition getlastest(PropertySet context){
        List<OperationDescprition> list = (List) context.getObject("optList");
        OperationDescprition lastest = null;
        if(list!=null && !list.isEmpty()){
            lastest = list.get(list.size()-1);
        }
        return lastest;
    }
    private static void parseTagtrCondtion(Elements elements, PropertySet context) {
        OperationDescprition lastest = getlastest(context);
        Iterator it = elements.iterator();
        System.out.println(elements.html());
        String textAll = elements.text();
        String[] temp = new String[4];
        Stack<OperationDescprition> stack = new Stack<OperationDescprition>();
        System.out.println(elements.size());
        int i = 0;
        while (it.hasNext()){
            Element element = (Element) it.next();
            String text = element.text();
            if(StringUtil.isNotEmpty(text)){
                temp[i++] = text;
            }else {
                int m = i;
                while(4-m > 0){
                    temp[m]="";
                    m++;
                }
                //如果是条件语句的条件声明操作则跳过。
                if ("IFTestStart".equals(temp[0]) && "Equal".equals(temp[1])) {
                    temp = new String[4];
                    i = 0;
                    continue;
                }else if("IFTestEnd".equals(temp[0])){
                    break;
                } else {
                    OperationDescprition conditiondescription = null;
                    conditiondescription = new OperationDescprition(temp[0], "condition", temp[1], temp[2],temp[3]);
                    conditiondescription.setCondition(true);
                    if(stack.isEmpty()){
                        stack.push(conditiondescription);
                    }else {
                        OperationDescprition olddescprition = stack.pop();
                        if(olddescprition.getRight()!=null && olddescprition.getLeft()!=null){
                            throw new IllegalArgumentException("the config file configuration condition is error");
                        }else if(olddescprition.getRight()==null){
                            olddescprition.setRight(conditiondescription);
                        }else if(olddescprition.getLeft()==null){
                            olddescprition.setLeft(conditiondescription);
                        }
                        stack.push(olddescprition);
                    }
                }
                temp = new String[4];
                i = 0;
            }
        }
        OperationDescprition olddescprition = stack.pop();

//        if(lastest!=null){
//            lastest.setNext(olddescprition);
//            olddescprition.setLast(lastest);
//        }
        List<OperationDescprition> list = (List<OperationDescprition>) context.getObject("optList");
        list.add(olddescprition);
        context.setObject("optList",list);

    }

}
