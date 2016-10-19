package com.nnk.template.function;

import com.nnk.template.selenium.SeleniumUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/15
 * Time: 15:29
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class WindowsActionMoveToElement extends FunctionProviderAdaptor{
    public static final Logger log = Logger.getLogger(WindowsActionMoveToElement.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String elementName = (String) args.get("elementName");
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        WebElement webElement = SeleniumUtils.findElement(webDriver, elementName);
        SeleniumUtils.ActionMoveToElement(webDriver,webElement);
    }
}
