/**
 * <b>����:</b>demo</br>
 */
package com.nnk.template.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

/**
 * 
 * <b>�����ƣ�</b>UnicomLogin<br/>
 * <b>��������</b>TODO<br/>
 * <b>�����ˣ�</b>y<br/>
 * <b>�޸��ˣ�</b>y<br/>
 * <b>�޸�ʱ�䣺</b>2016-5-9 ����02:17:33<br/>
 * <b>�޸ı�ע��</b><br/>
 * <b>�汾@version:</b></br/>
 */
public class UnicomLogin {
	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe");
		
		//����һ����������driver����
		WebDriver driver= new FirefoxDriver();
		//���ô������
		driver.manage().window().maximize();
        int pageLoadTime = 10;
        driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
		//��360����
		driver.get("https://uac.10010.com/portal/mallLogin.jsp?redirectURL=http://www.10010.com");
		//�ҵ�������Ԫ��
		WebElement searchInput= driver.findElement(By.id("userName"));
		//�����������롰selenium��
		searchInput.sendKeys("xxydliuy@163.com");
		
		WebElement searchInput2= driver.findElement(By.name("userPwd"));
		//�����������롰selenium��
		searchInput2.sendKeys("ss5470");
		//�ҵ�������ť
		WebElement searchButton= driver.findElement(By.id("login1"));
		//���������ť
		searchButton.click();
		
		
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
