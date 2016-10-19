package com.nnk.template.exeception;

import com.nnk.template.Appliaction;
import com.nnk.template.function.Contstant;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.module.propertyset.PropertySetCloner;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.InvalidRoleException;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.spi.WorkflowEntry;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/22
 * Time: 9:48
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//通用异常处理
public class CommonExceptionHandler implements Exceptionhandler{
    public static final Logger log = Logger.getLogger(CommonExceptionHandler.class);
    @Override
    public void HandlerException(Map transvalues, Map args, PropertySet propertySet, Throwable throwable) throws WorkflowException {
        if(throwable instanceof UnreachableBrowserException || throwable instanceof SessionNotFoundException ||throwable instanceof WebDriverException){
            //重新创建浏览器
            WebDriver webDriver = recreatWebDriver(propertySet);
            propertySet.setObject(Contstant.WEBDRIVER, webDriver);
            WorkflowEntry entry = (WorkflowEntry) transvalues.get("entry");
            String workflowName = entry.getWorkflowName();
            Long workId = Appliaction.WORKFLOWINMAP.get(workflowName);
            BasicWorkflow basicWorkflow = Appliaction.getBasicWorkflowInstance();
            log.info("the browser is closed,reopen the browser");
            restartWorkflow(workflowName, basicWorkflow,workId);
        }else if(throwable instanceof Exception){
            Exception e = (Exception) throwable;
            e.printStackTrace();
            log.error(e.getMessage(),e);
            String restart = propertySet.getString("restart");
            if(StringUtils.isEmpty(restart)){
                restart = (String) Appliaction.properties.get("restart");
            }
            boolean isrestart = Boolean.parseBoolean(restart);
            WorkflowEntry entry = (WorkflowEntry) transvalues.get("entry");
            String workflowName = entry.getWorkflowName();
            Long workId = Appliaction.WORKFLOWINMAP.get(workflowName);
            BasicWorkflow basicWorkflow = Appliaction.getBasicWorkflowInstance();
            //从第一步开始重新执行
            if(isrestart){
                log.info("restart execute workflow");
               restartWorkflow(workflowName,basicWorkflow,workId);
            }else {//重新执行当前步骤
                Integer actionId = (Integer) transvalues.get("actionId");
                log.info("actionId:" + actionId);
                long id = entry.getId();
                log.info("id:" + id+",workName:"+workflowName);
                int exceptionTimes = propertySet.getInt("exceptionTimes");
                if(exceptionTimes <= 1){
                    exceptionTimes ++;
                    propertySet.setInt("exceptionTimes",exceptionTimes);
                    basicWorkflow.doAction(id, actionId, Collections.EMPTY_MAP);
                }else{
                    quitInternet(basicWorkflow, workId);
                }
            }
        }
    }
    private boolean quitInternet(BasicWorkflow workflow, Long workId) {
        WebDriver webdriver = (WebDriver) workflow.getPropertySet(workId).getObject(Contstant.WEBDRIVER);
        if (webdriver != null) {
            webdriver.close();
            log.info("quit webdriver");
            return true;
        }else return false;
    }
    private WebDriver recreatWebDriver(PropertySet propertySet) {
        String filefox_path = propertySet.getString("fireFox.path");
        System.setProperty("webdriver.firefox.bin",filefox_path);

        WebDriver webDriver = new FirefoxDriver();
//        WebDriver oldWebDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
//        for(Cookie cookie:oldWebDriver.manage().getCookies()){
//            webDriver.manage().addCookie(cookie);
//        }
        propertySet.setObject(Contstant.WEBDRIVER,webDriver);
        webDriver.manage().window().maximize();
        log.info("create browser windows");
        return webDriver;
    }
    private void restartWorkflow(String workflowName, BasicWorkflow basicWorkflow,Long workId) throws WorkflowException {
        PropertySet src = basicWorkflow.getPropertySet(workId);
        long id = basicWorkflow.initialize(workflowName, 1, Collections.EMPTY_MAP);
        PropertySet target = basicWorkflow.getPropertySet(id);
        PropertySetCopy(src,target);
        Appliaction.WORKFLOWINMAP.put(workflowName, id);
        Appliaction.WORKfLOWSTARTDATEMAP.put(workflowName,new Date());
        int[] availableActions = basicWorkflow.getAvailableActions(id, Collections.EMPTY_MAP);
        System.out.println("availableAction:" + Arrays.toString(availableActions));
        for (int i:availableActions) {
            basicWorkflow.doAction(id, i, Collections.EMPTY_MAP);
        }
    }
    private void PropertySetCopy(PropertySet src,PropertySet target) {
        log.info("collection:" + src.getKeys());
        PropertySetCloner cloner = new PropertySetCloner();
        cloner.setSource(src);
        cloner.setDestination(target);
        cloner.cloneProperties();
    }
}
