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
 * Date: 2016/8/29
 * Time: 19:11
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class WaitForElementToLoad extends FunctionProviderAdaptor {
    public static final Logger log = Logger.getLogger(WaitPagesByElement.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String elementName = (String) args.get("elementName");
        String timeOut =  (String) args.get("timeOut");
        if (StringUtils.isEmpty(elementName)) throw new WorkflowException("elementName is not set");
        if (StringUtils.isEmpty(timeOut)) throw new WorkflowException("timeOut is not set");
        int time = StringUtils.isEmpty(timeOut)?5000:Integer.parseInt(timeOut);
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        SeleniumUtils.waitForElementToLoad(webDriver,time,elementName);

    }
}
