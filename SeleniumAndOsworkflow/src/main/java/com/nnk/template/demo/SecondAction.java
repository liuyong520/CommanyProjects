package com.nnk.template.demo;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/23
 * Time: 13:49
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class SecondAction implements FunctionProvider {
    @Override
    public void execute(Map map, Map map2, PropertySet propertySet) throws WorkflowException {
        WebDriver driver = (WebDriver) propertySet.getObject("driver");
        //ÕÒµ½ËÑË÷¿òÔªËØ
        WebElement searchInput= driver.findElement(By.id("mobno"));
        //ÏòËÑË÷¿òÊäÈë¡°selenium¡±
        searchInput.sendKeys("13267191379");
        String url1 = (String) map.get("url1");
        System.out.println("url1"+url1);
    }
}
