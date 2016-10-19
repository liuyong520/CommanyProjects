package com.nnk.template.condition;

import com.nnk.template.selenium.SeleniumUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/9/23
 * Time: 14:45
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class WaitElementAttributeCondition extends ConditionAdaptor {
    @Override
    public boolean passesConditionCathch(Map map, Map args, PropertySet propertySet) throws WorkflowException {
        String elementName = (String) args.get("elementName");
        if(StringUtils.isEmpty(elementName)){
            return false;
        }
        String attributeName = (String)args.get("attributeName");
        String targetAttributeValue= (String)args.get("targetAttributeValue");
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        if(webDriver==null) return false;
        WebElement webElement = SeleniumUtils.findElement(webDriver, elementName);
        String attributeValue = SeleniumUtils.getAttribute(webElement,attributeName);
        if(targetAttributeValue.equals(attributeValue)){
            return true;
        }else return false;
    }
}
