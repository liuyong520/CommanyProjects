/*
 * Copyright 1999-29 Nov 2015 Alibaba.com All right reserved. This software is the
 * confidential and proprietary information of Alibaba.com ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Alibaba.com.
 */
package com.nnk.template.osworkflow;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.apache.commons.logging.impl.Log4JCategoryLog;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.Map;


/**
 * 类TestFunction.java的实现描述：TODO 类实现描述 
 * @author liuy 29 Nov 2015 5:19:17 pm
 */
public class TestFunction implements FunctionProvider {
    private Logger logger = Logger.getLogger(TestFunction.class);
    @Override
    public void execute(Map arg0, Map arg1, PropertySet arg2) throws WorkflowException {
        // 改状态
        System.out.println("change status...");
        System.out.println(arg0);
        System.out.println(arg1);
        System.out.println(arg2);
        System.out.println(arg1.get("context"));

        System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
        Log4JLogger log = (Log4JLogger) arg0.get("log");
        log.info("hello");

    }
    public static boolean waitForElementToLoad(WebDriver driver,int timeOut,final By by){
        try {
            new WebDriverWait(driver, timeOut).until(new ExpectedCondition<Boolean>() {

                @Override
                public Boolean apply(WebDriver driver) {
                    // TODO Auto-generated method stub
                    WebElement element = driver.findElement(by);
                    System.out.println("find zhe element");
                    return element.isDisplayed();
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            Assert.fail("超时!! " + timeOut + " 秒之后还没找到元素 [" + by + "]", e);
        }
        return false;
    }
}
