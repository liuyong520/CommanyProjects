package com.nnk.template.handler;

import com.nnk.template.Appliaction;
import com.nnk.template.entity.SlowIntRequest;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import nnk.msgsrv.server.Request;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/18
 * Time: 16:14
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//上游模拟充值请求
public class SlowIntHandler {
    public static final Logger log = Logger.getLogger(SlowIntHandler.class);
    public void slowInt(Request request) throws WorkflowException {
        SlowIntRequest slowIntRequest = new SlowIntRequest(request);
        BasicWorkflow basicWorkflow = Appliaction.getBasicWorkflowInstance();
        Long workid =  Appliaction.getWorkid(request.getCmdName());
        int[] availableActions = basicWorkflow.getAvailableActions(workid,null);
        log.info("availableActions:" + Arrays.toString(availableActions));
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("paraments",slowIntRequest);
        PropertySet propertySet = basicWorkflow.getPropertySet(workid);
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        try {
            Set<org.openqa.selenium.Cookie> cookie = webDriver.manage().getCookies();
        }catch (Exception e){
            log.info("浏览器已经关掉了");
            request.response(request.getContent());
            String name = Thread.currentThread().getClass().getResource("/wf_login.xml").toString();
            Appliaction.initWorkflow(name,100,null);
        }
        for(int i:availableActions){
            log.info("i:" + i);
            basicWorkflow.doAction(workid,i,map);
        }

//        basicWorkflow.getCurrentSteps(Appliaction.getWorkid());
//
//        basicWorkflow.doAction(Appliaction.getWorkid(),);
    }
}
