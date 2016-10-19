package com.nnk.template.function;

import com.nnk.template.selenium.SeleniumUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/24
 * Time: 9:54
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class WindowsActionContentClick extends FunctionProviderAdaptor {
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String elementName = (String) args.get("elementName");
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        WebElement webElement = SeleniumUtils.findElement(webDriver, elementName);
        Actions actions= SeleniumUtils.ActionMoveToElement(webDriver,webElement);
        SeleniumUtils.contentClick(actions,webElement);
    }
}
