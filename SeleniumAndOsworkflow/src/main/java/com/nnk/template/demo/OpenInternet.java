package com.nnk.template.demo;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.FunctionProvider;
import com.opensymphony.workflow.WorkflowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: y
 * Date: 2016/6/23
 * Time: 11:51
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class OpenInternet implements FunctionProvider {
    @Override
    public void execute(Map map, Map map2, PropertySet propertySet) throws WorkflowException {
        propertySet.setString("url","www.007ka.cn");
        System.setProperty("webdriver.firefox.bin", "c:/Program Files (x86)/Mozilla Firefox/firefox.exe");
        //声明一个火狐浏览器driver对象
        WebDriver driver= new FirefoxDriver();
        //设置窗口最大化
        driver.manage().window().maximize();
        map.put("url1","www.007ka.com");
        //打开360搜索
        driver.get("http://www.007ka.cn/007kaWeb/");
        propertySet.setObject("driver",driver);
    }
}
