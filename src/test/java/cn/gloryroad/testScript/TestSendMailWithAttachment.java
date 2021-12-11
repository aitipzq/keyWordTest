package cn.gloryroad.testScript;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static cn.gloryroad.util.WaitUitl.*;
import static cn.gloryroad.util.KeyBoardUtil.*;

public class TestSendMailWithAttachment {
    WebDriver driver;
    String baseUrl = "https://mail.163.com/";

    @Test
    public void testSendMailWithAttachment() {
        driver.get(baseUrl);
        WebElement iframe = driver.findElement(By.xpath("//iframe[contains(@id,'x-URS-iframe')]"));
        driver.switchTo().frame(iframe);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebElement userName = driver.findElement(By.xpath("//input[@data-placeholder='邮箱帐号或手机号码']"));
        WebElement passWord = driver.findElement(By.xpath("//input[@data-placeholder='输入密码']"));
        WebElement loginButton = driver.findElement(By.id("dologin"));
        userName.clear();
        userName.sendKeys("15609223996");
        passWord.clear();
        passWord.sendKeys("1q2w3e4r");
        loginButton.click();
        driver.switchTo().defaultContent();
        waitWebElement(driver, "//div[contains(.,'退出')]");
        sleep(1000);
        WebElement writeMailLink = driver.findElement(By.xpath("//*[contains(@id,'_mail_component_')]/span[contains(.,'写 信')]"));
        writeMailLink.click();
        waitWebElement(driver, "//a[contains(.,'收件人')]");
        WebElement recipients = driver.findElement(By.xpath("//input[@aria-label='收件人地址输入框，请输入邮件地址，多人时地址请以分号隔开']"));
        WebElement mailSybject = driver.findElement(By.xpath("//input[contains(@id,'subjectInput')]"));
        recipients.sendKeys("917719801@qq.ccom");
        mailSybject.sendKeys("这是一封测试邮件");
        pressTabKey();
        setAndctrlVClipboardData("这是一封自动化发送邮件的正文");
        driver.findElement(By.xpath("//*[contains(@id,'_attachBrowser')]")).click();
        sleep(500);

        setAndctrlVClipboardData("d:\\d.log");
        pressEnterKey();
        sleep(4000);
        List<WebElement> buttons = driver.findElements(By.xpath("//*[contains(@id,'_mail_button_')]/span[contains(.,'发送')]"));
        buttons.get(1).click();
        waitWebElement(driver, "//*[contains(@id,'_succInfo')]");
        Assert.assertTrue(driver.getPageSource().contains("发送成功"));
    }


    @BeforeMethod
    public void beforemethod() {
        System.setProperty("webdriver.gecko.driver", "D:\\WebDriver\\geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @AfterMethod

    public void aftermethod() {
        driver.quit();
    }
}
