/**
 * <b>包名:</b>demo</br>
 */
package com.nnk.template.demo;


import com.nnk.template.win.WindowsOperators;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;


/**
 * 
 * <b>类名称：</b>NNKRecharge<br/>
 * <b>类描述：</b>TODO<br/>
 * <b>创建人：</b>y<br/>
 * <b>修改人：</b>y<br/>
 * <b>修改时间：</b>2016-5-9 下午03:28:05<br/>
 * <b>修改备注：</b><br/>
 * <b>版本@version:</b></br/>
 */
public class NNKRecharge {
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
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.firefox.bin", "c:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		
		//声明一个火狐浏览器driver对象
		WebDriver driver= new FirefoxDriver();
		//设置窗口最大化
		driver.manage().window().maximize();

		//打开360搜索
		driver.get("http://www.007ka.cn/007kaWeb/");
		//找到搜索框元素
		WebElement searchInput= driver.findElement(By.id("mobno"));
		//向搜索框输入“selenium”
		searchInput.sendKeys("13267191379");
		
		WebElement searchInput2= driver.findElement(By.name("czval"));
//		Select value = new Select(searchInput2);
//		value.selectByVisibleText("200元");
		//向搜索框输入“selenium”
		searchInput2.sendKeys("400");
		//找到搜索按钮
		WebElement searchButton= driver.findElement(By.id("commit"));
		//点击搜索按钮
		searchButton.click();
		waitForElementToLoad(driver,2000, By.name("mob"));
		driver.findElement(By.name("mob")).sendKeys("13267191379");
		
		WebElement submit = driver.findElement(By.name("Submit"));
		
		submit.click();
		driver.findElement(By.className("button")).click();
		List<WebElement> interface1 = driver.findElements(By.name("card_type"));
		for(WebElement e:interface1){
			String value = e.getAttribute("value");
			if(value.equals("5")){
				e.click(); break;
			}
		}
		driver.findElement(By.className("button")).click();
		boolean ret = waitForElementToLoad(driver,2000, By.xpath(".//*[@id='divWP']/div[2]/div[2]/div/div/span[2]"));
//		Assert.assertEquals(ret, true);
		driver.findElement(By.xpath(".//*[@id='divWP']/div[2]/div[2]/div/div/span[2]")).click();
		
		WindowsOperators.setInput("MozillaWindowClass", "招商银行网上支付[SZG] - Mozilla Firefox", "", "42342234253434323433");
		
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
