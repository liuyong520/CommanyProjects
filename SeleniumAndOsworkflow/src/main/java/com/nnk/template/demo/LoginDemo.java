package com.nnk.template.demo;

import com.nnk.template.selenium.SeleniumUtils;
import com.nnk.template.util.ShellRunner;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import javax.print.attribute.standard.SheetCollate;
import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: xxydl
 * Date: 2016/8/10
 * Time: 11:10
 * email: xxydliuy@163.com
 * To change this template use File | Settings | File Templates.
 */
public class LoginDemo {
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
        //声明一个火狐浏览器driver对象
        WebDriver driver = new FirefoxDriver();
        WebElement webElement = SeleniumUtils.findElement(driver, "http://gd.10086.cn/group/home/index.jspx;JSESSIONID=3d37c848-8536-4ee2-8e7e-bd87a88b8abf", "@id:login-btn");
        SeleniumUtils.click(webElement);
        WebElement webElement1 = SeleniumUtils.findElement(driver, "@xpath:.//*[@id='login-capture']");
        String pathandName = System.getProperty("user.dir") + "\\data\\code.png";
        String pathandName1 = System.getProperty("user.dir") + "\\data\\code";
        System.out.println("pathname:"+pathandName);
        System.out.println("pathname1:"+pathandName1);
        SeleniumUtils.getScreenShotByElement(driver, pathandName, webElement1);
        String cmd = "cmd /C start " +  System.getProperty("user.dir")+"/data/yzm.bat";
        System.out.println(cmd);
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(pathandName1+".txt");
        if(file.exists()) {
            FileHandler fh = new FileHandler();
            String s = fh.readAsString(file).trim();
            System.out.println(s);
        } else {
            System.out.print("yzm.txt不存在");
        }
        SeleniumUtils.quit(driver);
    }
}
