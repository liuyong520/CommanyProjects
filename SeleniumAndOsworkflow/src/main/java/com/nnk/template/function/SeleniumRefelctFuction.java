package com.nnk.template.function;

import com.nnk.template.selenium.SeleniumUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang.reflect.MethodUtils;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/24
 * Time: 10:38
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SeleniumRefelctFuction extends FunctionProviderAdaptor{
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String element = (String) args.get("element");
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        String method = (String) args.get("method");
    }
}
