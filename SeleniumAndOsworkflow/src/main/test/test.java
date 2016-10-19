import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class test {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://www.yolly.cn/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void test2112() throws Exception {
    driver.get(baseUrl + "/");
    driver.findElement(By.id("loginName")).clear();
    driver.findElement(By.id("loginName")).sendKeys("65548039");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("nnk88888007");
    driver.findElement(By.id("verifyCode")).clear();
    driver.findElement(By.id("verifyCode")).sendKeys("4290");
    driver.findElement(By.id("dologin")).click();
    driver.findElement(By.id("sysnotice")).click();
    driver.findElement(By.linkText("订单查询")).click();
    driver.findElement(By.cssSelector("div.hideshowmenus")).click();
    driver.findElement(By.cssSelector("#secondmenus > li.on")).click();
    // ERROR: Caught exception [ERROR: Unsupported command [selectFrame | subframe | ]]
    driver.findElement(By.linkText("最近7天")).click();
    driver.findElement(By.id("start_time")).click();
    driver.findElement(By.linkText("29")).click();
    driver.findElement(By.id("end_time")).click();
    driver.findElement(By.linkText("30")).click();
    driver.findElement(By.cssSelector("span.ui-button-text")).click();
    driver.findElement(By.cssSelector("i.refresh.icon_pos_4")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
