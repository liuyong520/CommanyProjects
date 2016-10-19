/**
 * <b>����:</b>demo</br>
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
 * <b>�����ƣ�</b>NNKRecharge<br/>
 * <b>��������</b>TODO<br/>
 * <b>�����ˣ�</b>y<br/>
 * <b>�޸��ˣ�</b>y<br/>
 * <b>�޸�ʱ�䣺</b>2016-5-9 ����03:28:05<br/>
 * <b>�޸ı�ע��</b><br/>
 * <b>�汾@version:</b></br/>
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
			Assert.fail("��ʱ!! " + timeOut + " ��֮��û�ҵ�Ԫ�� [" + by + "]", e);
		}
		return false;
	}
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.firefox.bin", "c:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		
		//����һ����������driver����
		WebDriver driver= new FirefoxDriver();
		//���ô������
		driver.manage().window().maximize();

		//��360����
		driver.get("http://www.007ka.cn/007kaWeb/");
		//�ҵ�������Ԫ��
		WebElement searchInput= driver.findElement(By.id("mobno"));
		//�����������롰selenium��
		searchInput.sendKeys("13267191379");
		
		WebElement searchInput2= driver.findElement(By.name("czval"));
//		Select value = new Select(searchInput2);
//		value.selectByVisibleText("200Ԫ");
		//�����������롰selenium��
		searchInput2.sendKeys("400");
		//�ҵ�������ť
		WebElement searchButton= driver.findElement(By.id("commit"));
		//���������ť
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
		
		WindowsOperators.setInput("MozillaWindowClass", "������������֧��[SZG] - Mozilla Firefox", "", "42342234253434323433");
		
		try{
			//����������ʱ��sleep��ʽ�ȴ�ҳ����״�������ὲ��������ܵȴ�
			Thread.sleep(2000);
			} catch(InterruptedException e) {
			e.printStackTrace();
			}
			//��ת֮���ҳ��ؼ��������Ԫ��
//			WebElement keywordInput= driver.findElement(By.id("keyword"));
			//��֤�����������ǲ���selenium
//			Assert.assertEquals(keywordInput.getAttribute("value"), "selenium");
			//�ر������
//			driver.quit();
		}
}
