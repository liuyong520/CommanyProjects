package com.nnk.template.selenium;


import com.nnk.template.util.Base64Util;
import com.nnk.template.util.DateUtils;
import com.thoughtworks.selenium.webdriven.commands.SeleniumSelect;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/9
 * Time: 14:58
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SeleniumUtils {
    public static final Logger LOGGER = Logger.getLogger(SeleniumUtils.class);

    /**
     *
     * @param webDriver
     * @param url
     * @param name
     * @return
     */
    public static WebElement findElement(WebDriver webDriver,String url,String name){
        By by = choose(name);
        webDriver.get(url);
        WebElement search = webDriver.findElement(by);
        return search;
    }

    /**
     *
     * @param webDriver
     * @param name
     * @return
     */
    public static WebElement findElement(WebDriver webDriver,String name){
        By by = choose(name);
        return webDriver.findElement(by);
    }

    /**
     *
     * @param webElement
     */
    public static void click(WebElement webElement){
        if(webElement.isDisplayed()&&webElement.isEnabled()){
            webElement.click();
        }
    }
    public static WebElement changeElementAttributeByjs(WebDriver webDriver,WebElement webElement){
        JavascriptExecutor js = (JavascriptExecutor)webDriver;
        String jsvar = "if(arguments[0].getAttribute(\"arguments[1]\")!=null){arguments[0].attributes.removeNamedItem(\"arguments[1]\"); }";
        js.executeScript(jsvar,webElement,"disable");
        js.executeScript("arguments[0].style=arguments[1]",webElement,"display:block;");
        return webElement;
    }
    public static void setElementValueByJs(WebDriver webDriver,WebElement webElement,String input){
        JavascriptExecutor js = (JavascriptExecutor)webDriver;
        String jsvar = "arguments[0].value=arguments[1];";
        js.executeScript(jsvar,webElement,input);
    }
    /**
     *
     * @param webElement
     */
    public static void clear(WebElement webElement){
        boolean enabled = webElement.isEnabled();
        boolean disabled = webElement.isDisplayed();
        if(webElement.isDisplayed()&&webElement.isEnabled()){
            webElement.clear();
        }
    }

    /**
     * 调用js执行修改操作
     * @param javaScript
     * @param webDriver
     */
    public static void excuteJs(String javaScript,WebDriver webDriver){
        JavascriptExecutor jsExcutor = (JavascriptExecutor) webDriver;
        jsExcutor.executeScript(javaScript);
    }
    /**
     *
     * @param webElement
     * @param name
     * @return
     */
    public static String getAttribute(WebElement webElement,String name){
        if(webElement.isDisplayed()){
            return webElement.getAttribute(name);
        }
        return null;
    }

    /**
     *
     * @param webElement
     * @return
     */
    public static String getText(WebElement webElement){
        if(webElement.isDisplayed()){
            return webElement.getText();
        }
        return null;
    }

    /**
     *
     * @param webElement
     */
    public static void submit(WebElement webElement){
        if(webElement.isDisplayed()&&webElement.isEnabled()){
            webElement.submit();
        }
    }

    /**
     *
     * @param webDriver
     * @return
     */
    public static String getCurrentWindowTitle(WebDriver webDriver){
        return webDriver.getTitle();
    }

    /**
     *
     * @param webElement
     */
    public static void select(WebElement webElement){
        if(!webElement.isSelected()){
            webElement.click();
        }
    }

    /**
     * ���ֵ��ѡ�������б��ֵ
     * @param webDriver
     * @param element
     * @param value
     */
    public static void selectByValue(WebDriver webDriver,String element,String value){
        WebElement webElement = findElement(webDriver,element);
        Select select = new Select(webElement);
        select.selectByValue(value);
    }
    public static void selectByIndex(WebDriver webDriver,String element ,String index){
        WebElement webElement = findElement(webDriver,element);
        Select select = new Select(webElement);
        select.selectByIndex(Integer.parseInt(index));
    }

    public static void selectByVisableText(WebDriver webDriver,String element,String text){
        WebElement webElement = findElement(webDriver,element);
        Select select = new Select(webElement);
        select.selectByVisibleText(text);
    }
    /**
     *
     * @param webElement
     * @param keys
     * @return
     */
    public static boolean setInput(WebElement webElement , String keys){
        if (webElement.isEnabled()){
            webElement.sendKeys(keys);
            if(LOGGER.isDebugEnabled()){
                LOGGER.debug("set Inputs:" + keys +"to the Element:"+ webElement.getText());
            }
            return true;
        }else {
            LOGGER.info("the Element is not Enable to set Inputs");
            return false;
        }
    }

    /**
     * <p>���ҷ������Ĵ���</p>
     * @param webDriver
     * @param title
     * @return
     */
    public static boolean findWindow(WebDriver webDriver,String title){
        Set<String> allWindowsId = webDriver.getWindowHandles();
        for(String windowsid: allWindowsId){
            String windowsTitle = webDriver.switchTo().window(windowsid).getTitle();
            LOGGER.info("windowsId:" + windowsid +"windowsTitle:" + windowsTitle);
            if(windowsTitle.contains(title)){
                switchWindow(webDriver, windowsid);
                return true;
            }
        }
        return false;
    }

    /**
     * <p>�л�����</p>
     * @param webDriver
     * @param windowId
     */
    public static void switchWindow(WebDriver webDriver,String windowId){
        webDriver.switchTo().window(windowId);
    }

    /**
     *
     * @param webDriver
     * @param frame
     */
    public static void switchWindowToFrame(WebDriver webDriver,WebElement frame){
        webDriver.switchTo().frame(frame);
        LOGGER.debug("pagesource:"+webDriver.getPageSource());
    }
    public static boolean findContent(WebDriver webDriver,String content){
        String contents = webDriver.getPageSource();
        if (contents.contains(content)){
            return true;
        }else {
            return false;
        }
    }
    public static void clickAlert(WebDriver webDriver ,WebElement webElement){
        webElement.click();
        Alert javaAlert = webDriver.switchTo().alert();
        LOGGER.debug("Alert Message:"+ javaAlert.getText());
        javaAlert.accept();
    }

    /**
     * ����Alert����
     * @param webDriver
     * @param webElement
     * @param keys
     */
    public static void setInputAlert(WebDriver webDriver,WebElement webElement,String keys){
        webElement.click();
        Alert javaAlert = webDriver.switchTo().alert();

        javaAlert.sendKeys(keys);
        javaAlert.accept();
        LOGGER.debug("Alert Message:"+ javaAlert.getText());
    }

    public static By choose(String by){
        String bydes = null;
        if(by.startsWith("id=")){
            bydes = by.substring("id=".length(),by.length());
            return By.id(bydes);
        }else if (by.startsWith("link=")){
            bydes = by.substring("link=".length(),by.length());
            return By.linkText(bydes);
        }else if(by.startsWith("class=")){
            bydes = by.substring("class=".length(),by.length());
            return By.className(bydes);
        }else if(by.startsWith("css=")){
            bydes = by.substring("css=".length(),by.length());
            return By.cssSelector(bydes);
        }else if(by.startsWith("name=")){
            bydes = by.substring("name=".length(),by.length());
            return By.name(bydes);
        }else if(by.startsWith("xpath=")){
            bydes = by.substring("xpath=".length(),by.length());
            return By.xpath(bydes);
        }else if(by.startsWith("partial=")){
            bydes = by.substring("partial=".length(),by.length());
            return By.partialLinkText(bydes);
        }
        return null;
    }

    public static void saveCookiesFile(WebDriver webDriver,String filename ){
        File cookieFile = new File(filename);
        try {
            if(cookieFile.exists()){
                cookieFile.delete();
                cookieFile.createNewFile();
            }
            FileWriter fileWriter =  new FileWriter(cookieFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for(Cookie cookie:webDriver.manage().getCookies()){
                String cookies  = cookie.getName()+";"+cookie.getValue() + ";"
                        + cookie.getDomain() + ";"
                        + cookie.getPath() + ";"
                        + cookie.getExpiry() + ";"
                        + cookie.isSecure();
                LOGGER.info("cookies:" +  cookies);
                bufferedWriter.write(cookies);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addCookiesFromFile(WebDriver webDriver ,String filename){
        try{
            File cookieFile = new File(filename);
            FileReader  fileReader = new FileReader(cookieFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine() )!=null){
                StringTokenizer stringTokenizer = new StringTokenizer(line,";");
                while (stringTokenizer.hasMoreTokens()){
                    String name = stringTokenizer.nextToken();
                    String value = stringTokenizer.nextToken();
                    String domain = stringTokenizer.nextToken();
                    String path = stringTokenizer.nextToken();
                    Date expiry = null;
                    String dt ;
                    if(!(dt = stringTokenizer.nextToken()).equals("null")){
                        expiry = new Date(dt);
                    }
                    boolean isSecure = new Boolean(stringTokenizer.nextToken()).booleanValue();
                    Cookie cookie = new Cookie(name,value,domain,path,expiry,isSecure);
                    LOGGER.info("cookies:" + cookie.toString() );
                    webDriver.manage().addCookie(cookie);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void back(WebDriver webDriver){
        webDriver.navigate().back();
    }

    public static void forward(WebDriver webDriver){
        webDriver.navigate().forward();
    }

    public static void refresh(WebDriver webDriver){
        webDriver.navigate().refresh();
    }

    public static void to(WebDriver webDriver,String url){
        webDriver.navigate().to(url);
    }

    public static boolean waitExplict(WebDriver webDriver,Integer time,final String title){
        WebDriverWait webDriverWait = new WebDriverWait(webDriver,time);
        webDriverWait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                LOGGER.info("waitExpict exec start");
                return driver.getTitle().toLowerCase().contains(title);
            }
        });
        LOGGER.info("waitExpict exec finish");
        return false;

    }

    public static void waitImplict(WebDriver webDriver,Integer time){
        webDriver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }
    //��ȡ������?
    public static void getScreenShotAsFile(WebDriver webDriver,String filename) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;
        File srcFile = (File) takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File(filename));
    }
    public static void getScreenShotByElement(WebDriver webDriver,String filename,WebElement webElement) throws IOException {
        Point p = webElement.getLocation();
        int width = webElement.getSize().getWidth();
        int higth = webElement.getSize().getHeight();
        java.awt.Rectangle rectangle = new Rectangle(width,higth);
        TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;

        File srcFile = (File) takesScreenshot.getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(srcFile);
        BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, higth);
        ImageIO.write(dest, "png", srcFile);
        File fng = new File(filename);
        if(fng.exists()){
            fng.delete();
        }
        FileUtils.copyFile(srcFile, fng);
        org.openqa.selenium.Rectangle rectangle1 = new org.openqa.selenium.Rectangle(p.getX(), p.getY(), width, higth);
    }
    public static String getScreenShotByElementAsString(WebDriver webDriver,WebElement webElement) throws IOException {
        Point p = webElement.getLocation();
        int width = webElement.getSize().getWidth();
        int higth = webElement.getSize().getHeight();
        LOGGER.info("width:"+ width);
        LOGGER.info("hight:" +higth);
        java.awt.Rectangle rectangle = new Rectangle(width,higth);
        TakesScreenshot takesScreenshot = (TakesScreenshot) webDriver;

        File srcFile = (File) takesScreenshot.getScreenshotAs(OutputType.FILE);
        BufferedImage img = ImageIO.read(srcFile);
        BufferedImage dest = img.getSubimage(p.getX(), p.getY(), width, higth);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(dest, "png", byteArrayOutputStream);

//        ImageIO.write(dest, "png", srcFile);
//        File fng = new File(DateUtils.parseDate("yyyymmddHHmmssS",new Date())+".png");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
//        FileUtils.copyInputStreamToFile(byteArrayInputStream, fng);
        String imgsrc = Base64Util.encodeToString(byteArrayOutputStream.toByteArray());
        return imgsrc;
    }

    public static void ActionMoveToElementAndClick(WebDriver webDriver,WebElement webElement){
        Actions actions = ActionMoveToElement(webDriver,webElement);
        actions.click().perform();
    }
    public static Actions ActionMoveToElement(WebDriver webDriver,WebElement webElement){
        Actions actions = new Actions(webDriver);
        actions.moveToElement(webElement).perform();
        return actions;
    }

    public static Actions ActionMoveToElementAndSetInput(WebDriver webDriver,WebElement webElement,String keys){
        Actions actions = ActionMoveToElement(webDriver,webElement);
        actions.sendKeys(webElement,keys);
        return actions;
    }
    public static Actions keyUp(Actions actions,String keys,WebElement webElement){
        Keys keys1 = Keys.valueOf(keys);
        actions.keyUp(webElement,keys1);
        return actions;
    }

    public static Actions keyDown(Actions actions,String keys,WebElement webElement){
        Keys keys1 = Keys.valueOf(keys);
        actions.keyDown(webElement, keys1);

        return actions;
    }
    public static Actions moveToElement(Actions actions,String keys,WebElement webElement){
        Keys keys1 = Keys.valueOf(keys);
        actions.moveToElement(webElement);

        return actions;
    }
    public static Actions contentClick(Actions actions,WebElement webElement){
        actions.contextClick(webElement);
        return actions;
    }
    public static Actions doubleClick(Actions actions,WebElement webElement){
        actions.doubleClick(webElement);
        return actions;
    }
    public static Actions clickAndHold(Actions actions,WebElement webElement){
        actions.clickAndHold(webElement);
        return actions;
    }
    public static Actions dragAndDrop(Actions actions,WebElement src,WebElement desc){
        actions.dragAndDrop(src, desc);
        return actions;
    }
    public static Actions dragAndDropBy(Actions actions,WebElement src,int x,int y){
        actions.dragAndDropBy(src, x, y);
        return actions;
    }
    public static Actions release(Actions actions,WebElement src){
        actions.release(src);
        return actions;
    }
    public static Actions perform(Actions actions){
        actions.perform();
        return actions;
    }
    public static void quit(WebDriver webDriver){
        webDriver.quit();
    }

    public static void close(WebDriver webDriver){
        webDriver.close();
    }

    /**
     * ���ܵȴ�Ԫ�ر�����
     * @param driver
     * @param timeOut
     * @param webElement
     * @return
     */
    public static boolean waitForElementToLoad(WebDriver driver,int timeOut,final WebElement webElement){
        try {
            new WebDriverWait(driver, timeOut).until(new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(WebDriver driver) {
                    // TODO Auto-generated method stub
                   LOGGER.info("find zhe element to load");
                    return webElement.isDisplayed()&&webElement.isEnabled();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            LOGGER.error("can't find zhe  element,timeOut:" + timeOut + "webElementName:[" + webElement + "]", e);
        }
        return false;
    }

    /**
     * ���ܵȴ�Ԫ�ر�����
     * @param driver
     * @param timeOut
     * @param name
     */
    public static void waitForElementToLoad(WebDriver driver,int timeOut,final String name){

        new WebDriverWait(driver, timeOut).until(ExpectedConditions.presenceOfElementLocated(choose(name)));
    }
}
