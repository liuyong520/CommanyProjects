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
 * Date: 2016/8/24
 * Time: 14:31
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class FindElementAndSetInputFromArgs extends FunctionProviderAdaptor {
    public static final Logger log = Logger.getLogger(FindElementAndClick.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String element = (String) args.get(Contstant.ELEMENTNAME);
        if(StringUtils.isEmpty(element)) throw  new  WorkflowException("args elementName is not set");
        String keys = (String)args.get(Contstant.KEYS);
        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        if(webDriver==null) throw  new WorkflowException("webDriver is not init");
        WebElement webElement = SeleniumUtils.findElement(webDriver, element);
        SeleniumUtils.clear(webElement);
        SeleniumUtils.setInput(webElement, keys);
        log.info("setInput:" + keys);
    }
}
