package com.nnk.template.function;

import com.nnk.template.selenium.SeleniumUtils;
import com.nnk.utils.js.JsExecutor;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/29
 * Time: 19:43
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class JavaScriptActionById extends FunctionProviderAdaptor {
    private Logger logger = Logger.getLogger(JavaScriptActionById.class);
    @Override
    public void executeCatched(Map map, Map args, PropertySet propertySet) throws WorkflowException {
        String elementId = (String) args.get(Contstant.ELEMENTNAME);
        if(StringUtils.isEmpty(elementId)) throw new WorkflowException("elementName is not set");
        elementId = elementId.substring("id=".length(),elementId.length());
        String keys = (String) args.get(Contstant.KEYS);
        if(StringUtils.isEmpty(keys)) throw new WorkflowException("value is not set");
        WebDriver webDriver = (WebDriver) propertySet.getObject(Contstant.WEBDRIVER);
        logger.info("set keys:" + keys);
        String javascript = "document.getElementById('"+ elementId +"').value ='"+keys+"';";
        SeleniumUtils.excuteJs(javascript,webDriver);
    }
}
