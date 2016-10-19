package com.nnk.template.function;

import com.nnk.template.Appliaction;
import com.nnk.template.entity.ObtainImgCodeRequest;
import com.nnk.template.entity.SMSCodeIndex;
import com.nnk.template.entity.SMSCodeRequest;
import com.nnk.template.selenium.SeleniumUtils;
import com.nnk.template.util.DateUtils;
import com.nnk.utils.http.utils.StringUtil;
import com.nnk.utils.js.JsoupUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import nnk.msgsrv.server.MsgSrvShortConnector;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import sun.org.mozilla.javascript.internal.ast.ObjectProperty;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/1
 * Time: 8:40
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */

/**
 * html 解析操作
 */
public class SeleniumScriptTranslateActions extends FunctionProviderAdaptor {
    private Logger log = Logger.getLogger(SeleniumScriptTranslateActions.class);
    @Override
    public void executeCatched(Map map, Map args, PropertySet propertySet) throws WorkflowException {
        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        //获取文件地址
        String filename = (String) args.get("scriptFileName");
        File file = new File(filename);
        try {
            String htmlContent = FileUtils.readFileToString(file);
            Document document = JsoupUtils.parseHtmlByString(htmlContent);
            Elements element = JsoupUtils.getElementsByTagName(document,"link");
            Element element1 = element.first();
            //获取访问浏览器的基础地址
            String baseUrl = element1.attr("href");
            log.info("baseUrl:" + baseUrl);
                //创建浏览器
            if(webDriver==null) {
                webDriver = creatWebDriver(propertySet);
            }
            log.info("begin to open the browser");
            webDriver.get(baseUrl);
            log.info("open the browser and get baseUrl:" + baseUrl);
            Elements elements = JsoupUtils.getElementsByTagName(document, "tbody");
            Elements trs = JsoupUtils.getElementsByTagName(elements.first(),"tr");

            for(Element e:trs){
                Elements tagName = e.children();
//              Elements tagName =  e.getElementsByTag("td");
                Operator operator = new Operator(tagName);
                log.info(operator.toString());
                //如果为打开浏览器操作
                if(operator.getOptName().equals("open")){
                    String url = baseUrl + operator.getElement();
                    webDriver.get(url);
                //普通输入
                }else if(operator.getOptName().equals("type")){
                    WebElement webElement = SeleniumUtils.findElement(webDriver, operator.getElement());
                    SeleniumUtils.clear(webElement);
                    String value = operator.getValue();
                    //先正则匹配下是不是变量如果是变量从propertieset 中取值。
                    value = getMatchedByRegex(propertySet, value);
                    SeleniumUtils.setInput(webElement,value);
                    //点击操作
                }else if(operator.getOptName().equals("click")){
                    WebElement webElement = SeleniumUtils.findElement(webDriver, operator.getElement());
                    webElement = SeleniumUtils.changeElementAttributeByjs(webDriver,webElement);
                    SeleniumUtils.click(webElement);
                //点击之后等待
                }else if(operator.getOptName().equals("clickAndWait")){
                    WebElement webElement = SeleniumUtils.findElement(webDriver, operator.getElement());
                    webElement = SeleniumUtils.changeElementAttributeByjs(webDriver,webElement);
                    SeleniumUtils.click(webElement);
                    Integer time = StringUtils.isEmpty(operator.getValue())?5000:Integer.parseInt(operator.getValue());
                    SeleniumUtils.waitImplict(webDriver,time);
                    Thread.sleep(1000);
                //图片验证码操作
                }else if (operator.getOptName().equals("imglocation")){
                    //获取图片验证码
                    WebElement IMGYZMLocation = SeleniumUtils.findElement(webDriver, operator.getElement());
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
                                WebElement ImgInput = SeleniumUtils.findElement(webDriver, operator.getExtElement());
                                SeleniumUtils.setInput(ImgInput, result);
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
                }else if(operator.getOptName().equals("selectFrame")||operator.getOptName().equals("selectWindow")){

                    WebElement webElement = SeleniumUtils.findElement(webDriver,operator.getElement());
                    SeleniumUtils.switchWindowToFrame(webDriver,webElement);
                 //模拟鼠标点击
                }else if(operator.getOptName().equals("actionClick")){
                    WebElement webElement = SeleniumUtils.findElement(webDriver,operator.getElement());
                    SeleniumUtils.ActionMoveToElementAndClick(webDriver,webElement);
                //多选下拉框
                }else if(operator.getOptName().equals("select")){
                    SelectElementByselct selectElementByselct = new SelectElementByselct();
                    args.put(Contstant.ELEMENTNAME,operator.getElement());
                    args.put("elementValue",operator.getValue());
                    selectElementByselct.execute(map, args, propertySet);
                    //用js 输入
                }else if(operator.getOptName().equals("jsType")){
                    String value = operator.getValue();
                    //正则表达式匹配${date}这种格式的数据
                    value = getMatchedByRegex(propertySet, value);
                    String optType = operator.getOptType();
                    if(StringUtils.isNotEmpty(optType)&&optType.equals("dateformart")){
                        String dateformart = operator.getExtValue();
                        value = DateUtils.tranferDateFormat("yyyyMMdd",dateformart,value);
                    }
                    WebElement webElement =SeleniumUtils.findElement(webDriver,operator.getElement());

                    SeleniumUtils.setElementValueByJs(webDriver,webElement,value);
                //时间等待
                }else if(operator.getOptName().equals("waitByTime")) {
                    Integer time = StringUtils.isEmpty(operator.getValue()) ? 5000 : Integer.parseInt(operator.getValue());
                    Thread.sleep(time);
                //点击短信验证码时需要提前执行这个操作
                }else if(operator.getOptName().equals("smsClickBefore")){
                    String myphone = operator.getValue();
                    if(Appliaction.SMSTASKCACHE.containsKey(myphone)){
                        int taskId = Appliaction.SMSTASKCACHE.get(myphone);
                        taskId ++;
                        Appliaction.SMSTASKCACHE.put(myphone,taskId);
                    }else {
                        Appliaction.SMSTASKCACHE.put(myphone,1);
                    }
                //获取短信验证码
                }else if(operator.getOptName().equals("smsInput")){
                    //获取短信验证码
                    String timeOut = (String) operator.getOptType();
                    String myphone = operator.getValue();
                    String regex = (String) operator.getExtValue();
                    if(StringUtils.isEmpty(timeOut)||StringUtils.isEmpty(myphone)||StringUtils.isEmpty(regex)) throw new WorkflowException("sms args timeout,myphone,index must be set");
                    Long time = Long.parseLong(timeOut);
                    try {
                        Thread.sleep(time*1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    List<SMSCodeRequest> messageList = Appliaction.SMSCODECACHE.get(myphone);
                    try {
                        String SmsCode = getSMSCodeMatchedByRegex(messageList, regex);
                        if(StringUtils.isNotEmpty(SmsCode)) {
                            log.info("recieved sms code result:" + SmsCode);
                            WebElement ImgInput = SeleniumUtils.findElement(webDriver, operator.getElement());
                            SeleniumUtils.setInput(ImgInput, SmsCode);
                        }else{
                            log.info("recognise sms code result failed:" + SmsCode);
                            throw new WorkflowException("SmsCode is get null");
                        }
                    }catch (Exception ex){
                        ex.printStackTrace();
                        throw new WorkflowException("SmsCode is get null");
                    }
                //跳转url
                }else if(operator.getOptName().equals("getUrl")){
                    log.info("cookies:" + webDriver.manage().getCookies());
                    webDriver.get(operator.getElement());
                    String url = webDriver.getCurrentUrl();
                    log.info("currentUrl:" + url);
                //弹出对话话框
                }else if(operator.getOptName().equals("assertAlert")){
                    WebElement webElement = SeleniumUtils.findElement(webDriver,operator.getElement());
                    SeleniumUtils.clickAlert(webDriver,webElement);
                //获取某个元素的文本信息
                }else if(operator.getOptName().equals("getText")){
                    WebElement webElement = SeleniumUtils.findElement(webDriver,operator.getElement());
                    SeleniumUtils.getText(webElement);
                //获取某个元素的属性
                }else if(operator.getOptName().equals("getAttribute")){
                    WebElement webElement = SeleniumUtils.findElement(webDriver,operator.getElement());
                    SeleniumUtils.getAttribute(webElement,operator.getValue());
                }else if(operator.getOptName().equals("selectWindowsMain")){
                    webDriver.switchTo().defaultContent();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

    class Operator{
        private String optName;
        private String element;
        private String value;
        private String optType;
        private String extOptName;
        private String extElement;
        private String extValue;
        private String extOptType;
        public Operator(Elements elements) {

            Element[] array = new Element[elements.size()];
            elements.toArray(array);
            this.optName = array[0].text();
            //元素格式化处理
            this.element = array[1].text();
            if(array.length >= 3) this.value = array[2].text();
            if(array.length >= 4) this.optType = array[3].text();
            if(array.length >= 5) {
                Element extElement = array[4];
                Elements extElements = extElement.getElementsByTag("ext");
                Element[] extArray = new Element[extElements.size()];
                extElements.toArray(extArray);
                this.extOptName = extArray[0].text();
                this.extElement = extArray[1].text();
                if(extArray.length >= 3) this.extValue = extArray[2].text();
                if(extArray.length >= 4) this.extOptType = extArray[3].text();
            }
        }

        public Operator(String optName, String element, String value, String optType) {
            this.optName = optName;
            this.element = element;
            this.value = value;
            this.optType = optType;
        }

        public Operator(String optName, String element, String value) {
           this(optName,element,value,"");
        }

        public Operator(String optName, String element) {
            this(optName,element,"","");
        }

        public String getOptName() {
            return optName;
        }

        public void setOptName(String optName) {
            this.optName = optName;
        }

        public String getElement() {
            return element;
        }

        public void setElement(String element) {
            this.element = element;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getOptType() {
            return optType;
        }

        public void setOptType(String optType) {
            this.optType = optType;
        }

        @Override
        public String toString() {
            return "Operator{" +
                    "optName='" + optName + '\'' +
                    ", element='" + element + '\'' +
                    ", value='" + value + '\'' +
                    ", optType='" + optType + '\'' +
                    '}';
        }

        public String getExtOptName() {
            return extOptName;
        }

        public void setExtOptName(String extOptName) {
            this.extOptName = extOptName;
        }

        public String getExtElement() {
            return extElement;
        }

        public void setExtElement(String extElement) {
            this.extElement = extElement;
        }

        public String getExtValue() {
            return extValue;
        }

        public void setExtValue(String extValue) {
            this.extValue = extValue;
        }

        public String getExtOptType() {
            return extOptType;
        }

        public void setExtOptType(String extOptType) {
            this.extOptType = extOptType;
        }
    }
}
