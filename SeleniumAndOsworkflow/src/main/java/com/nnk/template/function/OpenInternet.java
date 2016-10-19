package com.nnk.template.function;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowContext;
import com.opensymphony.workflow.WorkflowException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/10
 * Time: 15:12
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class OpenInternet extends FunctionProviderAdaptor{
    private Logger logger = Logger.getLogger(OpenInternet.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        System.out.println(transientVars);
        if(transientVars.containsKey("context")) {
            final WorkflowContext wfContext = (WorkflowContext) transientVars.get("context");
            logger.info("wfContext" + wfContext);
        }else{
            logger.info("context is not exist!");
        }
        String url = null;
        if(args.containsKey(Contstant.URL)){
            url = (String) args.get(Contstant.URL);
            logger.info("url:" + url);
        }else{
            logger.info("url is not exist!");
        }
        if(args.containsKey("webdriver.firefox.bin")) {
            String webdriver_string = (String) args.get("webdriver.firefox.bin");
            System.setProperty("webdriver.firefox.bin",webdriver_string);
            WebDriver webDriver = new FirefoxDriver();
            webDriver.get(url);
            webDriver.manage().window().maximize();
            propertySet.setObject(Contstant.WEBDRIVER,webDriver);
        }else{
            logger.info("webdriver.firefox.bin is not exist!");
        }
    }
}
