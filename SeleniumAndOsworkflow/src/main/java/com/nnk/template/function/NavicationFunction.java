package com.nnk.template.function;

import com.nnk.template.selenium.SeleniumUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/16
 * Time: 16:26
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//浏览器是前进还是后退
public class NavicationFunction extends FunctionProviderAdaptor{

    public static final Logger log = Logger.getLogger(NavicationFunction.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String optType = (String) args.get("optType");
        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        if(webDriver==null) throw new WorkflowException("webdriver is not set");
        if(StringUtils.isEmpty(optType)) throw new WorkflowException("optType is not Set");
        if(Contstant.FORWARD.equals(optType)){
            SeleniumUtils.forward(webDriver);
        }else if(Contstant.BACK.equals(optType)){
            SeleniumUtils.back(webDriver);
        }else if (Contstant.TO.equals(optType)){
            String url = (String) args.get("optType");
            if(StringUtils.isEmpty(url))throw new WorkflowException("optType is to must set arg \"url\"");
            SeleniumUtils.to(webDriver,url);
        }else if(Contstant.REFRESH.equals(optType)){
            SeleniumUtils.refresh(webDriver);
        }
        log.debug("currenturl:" + webDriver.getCurrentUrl());
        propertySet.setObject("webdriver",webDriver);
    }
}
