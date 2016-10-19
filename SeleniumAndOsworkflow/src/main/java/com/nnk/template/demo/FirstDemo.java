/**
 * <b>����:</b>demo</br>
 */
package com.nnk.template.demo;

import com.nnk.template.selenium.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

/**
 * 
 * <b>�����ƣ�</b>FirstDemo<br/>
 * <b>��������</b>TODO<br/>
 * <b>�����ˣ�</b>y<br/>
 * <b>�޸��ˣ�</b>y<br/>
 * <b>�޸�ʱ�䣺</b>2016-5-9 ����10:56:27<br/>
 * <b>�޸ı�ע��</b><br/>
 * <b>�汾@version:</b></br/>
 */
public class FirstDemo {
	public static void main(String[] args) {
        System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");

        //����һ����������driver����
        WebDriver driver = new FirefoxDriver();
        WebElement webElement = SeleniumUtils.findElement(driver, "http://wwww.baidu.com", "@id:kw");
        webElement.sendKeys("007ka");
        SeleniumUtils.waitExplict(driver, 2000, "007ka_�ٶ�����");
        WebElement webElement1 = SeleniumUtils.findElement(driver, "@linkText:007ka������");
        SeleniumUtils.click(webElement1);
        SeleniumUtils.waitExplict(driver, 2000, "007ka");
        String tiltle = SeleniumUtils.getCurrentWindowTitle(driver);
        System.out.println("windows title:" + tiltle);
        SeleniumUtils.findWindow(driver,"007ka��");
        WebElement webElement2 = SeleniumUtils.findElement(driver,"@id:mob_recharge");
        webElement2.click();
        WebElement webElement3 = SeleniumUtils.findElement(driver,"@id:mobno");
        webElement3.sendKeys("13267191379");
        WebElement webElement4 = SeleniumUtils.findElement(driver,"@xpath:.//*[@id='czval']");
        SeleniumUtils.ActionMoveToElementAndClick(driver,webElement4);
        WebElement webElement5 = SeleniumUtils.findElement(driver,"@xpath:.//*[@id='a1']");
        SeleniumUtils.ActionMoveToElementAndClick(driver,webElement5);
        SeleniumUtils.waitImplict(driver,2000000);
        WebElement webElement6 = SeleniumUtils.findElement(driver,"@id:commit");
        SeleniumUtils.click(webElement6);
//        SeleniumUtils.quit(driver);
    }
}
