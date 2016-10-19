package com.nnk.template.function;

import com.nnk.template.selenium.SeleniumUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/16
 * Time: 18:30
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class QuitInternet extends FunctionProviderAdaptor{

    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        if (webDriver==null) throw new WorkflowException("Webdriver is not init");
        SeleniumUtils.close(webDriver);
    }
}
