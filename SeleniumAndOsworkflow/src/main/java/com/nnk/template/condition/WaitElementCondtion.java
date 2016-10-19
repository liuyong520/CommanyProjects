package com.nnk.template.condition;

import com.nnk.template.selenium.SeleniumUtils;
import com.nnk.utils.http.utils.StringUtil;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.Condition;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/25
 * Time: 14:56
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class WaitElementCondtion extends ConditionAdaptor {
    @Override
    public boolean passesConditionCathch(Map transvarMap, Map args, PropertySet propertySet) throws WorkflowException {
        String elementName = (String) args.get("elementName");
        if(StringUtils.isEmpty(elementName)){

            return false;
        }
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        if(webDriver==null) return false;
        WebElement webElement = SeleniumUtils.findElement(webDriver, elementName);
        boolean ret = SeleniumUtils.waitForElementToLoad(webDriver,20000,webElement);
        return ret;
    }
}
