package com.nnk.template.function;

import com.nnk.template.selenium.SeleniumUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang.reflect.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/24
 * Time: 18:48
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
//从临时变量输入数据
public class FindElementAndSetInputFromTransientVars extends FunctionProviderAdaptor {
    public static final Logger log = Logger.getLogger(FindElementAndClick.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String element = (String) args.get(Contstant.ELEMENTNAME);
        if(StringUtils.isEmpty(element)) throw  new  WorkflowException("args elementName is not set");
        String methodName = (String)args.get(Contstant.METHODNAME);
        if(StringUtils.isEmpty(methodName)) throw  new  WorkflowException("args methodName is not set");
        Object object = transientVars.get(Contstant.PARAMENTS);
        if(object==null) throw new WorkflowException("paraments is not set in transientVars");

        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        if(webDriver==null) throw  new WorkflowException("webDriver is not init in propertySet");
        WebElement webElement = SeleniumUtils.findElement(webDriver, element);
        String keys = null;
        try {
            keys = (String) MethodUtils.invokeExactMethod(object, methodName, null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        SeleniumUtils.setInput(webElement, keys);
        log.info("setInput:" + keys);
    }
}
