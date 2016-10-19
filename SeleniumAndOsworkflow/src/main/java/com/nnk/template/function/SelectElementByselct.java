package com.nnk.template.function;

import com.nnk.template.selenium.SeleniumUtils;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/25
 * Time: 14:24
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SelectElementByselct extends FunctionProviderAdaptor{
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        String ElementName= (String) args.get(Contstant.ELEMENTNAME);
        if(StringUtils.isEmpty(ElementName)) throw new WorkflowException("ElementName is not set");
        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        if(webDriver==null) throw new WorkflowException("webdriver is not init");
        String value = (String) args.get("elementValue");
        if(value.startsWith("value=")){
            value = value.substring("value=".length(),value.length());
            SeleniumUtils.selectByValue(webDriver,ElementName,value);
        }else if(value.startsWith("index=")){
            value = value.substring("index=".length(),value.length());
            SeleniumUtils.selectByIndex(webDriver,ElementName,value);
        }else if(value.startsWith("label=")){
            value = value.substring("label=".length(),value.length());
            SeleniumUtils.selectByVisableText(webDriver,ElementName,value);
        }
    }
}
