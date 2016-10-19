package com.nnk.template.condition;

import com.nnk.template.selenium.SeleniumUtils;
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
 * Date: 2016/9/5
 * Time: 11:19
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class WaitWindowsShowByTitle extends ConditionAdaptor {
    @Override
    public boolean passesConditionCathch(Map map, Map args, PropertySet propertySet) throws WorkflowException {
        String titleName = (String) args.get("titleName");
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        if(webDriver==null||StringUtils.isEmpty(webDriver.getTitle())){
            return false;
        }
        boolean ret = SeleniumUtils.findWindow(webDriver, titleName);
        return ret;
    }
}
