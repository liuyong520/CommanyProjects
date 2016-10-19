package com.nnk.template.function;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/15
 * Time: 16:13
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class GetUrl extends FunctionProviderAdaptor {
    private Logger logger = Logger.getLogger(GetUrl.class);
    @Override
    public void executeCatched(Map transientVars, Map args, PropertySet propertySet) throws WorkflowException {
        System.out.println(transientVars);
        String url = null;
        if(args.containsKey(Contstant.URL)){
            url = (String) args.get("url");
            logger.info("url:" + url);
        }else{
            logger.info("url is not exist!");
        }
        WebDriver webDriver = (WebDriver) propertySet.getObject("webdriver");
        logger.info("cookies:" + webDriver.manage().getCookies());
//        webDriver.navigate().to(url);
        webDriver.get(url);
        url = webDriver.getCurrentUrl();
        logger.info("currentUrl:" + url);

    }
}
