/**
 * <b>包名:</b>demo</br>
 */
package com.nnk.template.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * 
 * <b>类名称：</b>UnicomLogin<br/>
 * <b>类描述：</b>TODO<br/>
 * <b>创建人：</b>y<br/>
 * <b>修改人：</b>y<br/>
 * <b>修改时间：</b>2016-5-9 下午02:17:33<br/>
 * <b>修改备注：</b><br/>
 * <b>版本@version:</b></br/>
 */
public class UnicomLogin {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		
		//声明一个火狐浏览器driver对象
		WebDriver driver= new FirefoxDriver();
		//设置窗口最大化
		driver.manage().window().maximize();
        int pageLoadTime = 10;
        driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
		//打开360搜索
		driver.get("https://uac.10010.com/portal/mallLogin.jsp?redirectURL=http://www.10010.com");
		//找到搜索框元素
		WebElement searchInput= driver.findElement(By.id("userName"));
		//向搜索框输入“selenium”
		searchInput.sendKeys("xxydliuy@163.com");
		
		WebElement searchInput2= driver.findElement(By.name("userPwd"));
		//向搜索框输入“selenium”
		searchInput2.sendKeys("ss5470");
		//找到搜索按钮
		WebElement searchButton= driver.findElement(By.id("login1"));
		//点击搜索按钮
		searchButton.click();
		
		
		try{
			//这里我们暂时用sleep方式等待页面条状，后续会讲到如何智能等待
			Thread.sleep(2000);
			} catch(InterruptedException e) {
			e.printStackTrace();
			}
			//跳转之后的页面关键字输入框元素
//			WebElement keywordInput= driver.findElement(By.id("keyword"));
			//验证输入框的内容是不是selenium
//			Assert.assertEquals(keywordInput.getAttribute("value"), "selenium");
			//关闭浏览器
//			driver.quit();
		}

}
