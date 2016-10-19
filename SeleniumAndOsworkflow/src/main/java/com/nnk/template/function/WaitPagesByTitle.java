package com.nnk.template.function;

import com.nnk.template.selenium.SeleniumUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/12
 * Time: 15:09
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class WaitPagesByTitle extends FunctionProviderAdaptor{
    public static final Logger log = Logger.getLogger(WaitPagesByTitle.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String titleName = (String) args.get("titleName");
        if(StringUtils.isEmpty(titleName)) throw new WorkflowException("titleName is not set");
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        boolean ret = SeleniumUtils.waitExplict(webDriver,2000,titleName);
        if(ret){
            log.info("wait windowsTitle:" + titleName +"true");
            propertySet.setString("titleShow","true");
        }else{
            propertySet.setString("titleShow","false");
        }
    }
}
