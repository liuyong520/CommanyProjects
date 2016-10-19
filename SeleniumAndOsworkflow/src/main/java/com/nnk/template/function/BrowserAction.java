package com.nnk.template.function;

import com.nnk.template.selenium.SeleniumUtils;
import com.nnk.template.util.ReflectUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/10
 * Time: 16:09
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class BrowserAction extends FunctionProviderAdaptor{
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String element = (String) args.get("Element");
        String OptName = (String) args.get("Opt");
        boolean isWebdriver = false;
        String WebdriverStr = (String) args.get("isWebdriver");
        if(StringUtils.isEmpty(WebdriverStr)){
            isWebdriver = Boolean.parseBoolean(WebdriverStr);
        }
        boolean isElement = false;
//        String isElement = (String) args.get("isElement");
        if(StringUtils.isEmpty(WebdriverStr)){
            isWebdriver = Boolean.parseBoolean(WebdriverStr);
        }
        WebDriver webDriver = (WebDriver) propertySet.getObject("webDriver");

        WebElement webElement = SeleniumUtils.findElement(webDriver, element);
//        ReflectUtils.invokeMethodNameStatic(SeleniumUtils.class,OptName,)
    }
}
