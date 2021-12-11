package cn.gloryroad.configuration;


import cn.gloryroad.util.ExcelUtil;
import cn.gloryroad.util.KeyBoardUtil;
import cn.gloryroad.util.Log;
import cn.gloryroad.util.ObjectMap;
import cn.gloryroad.util.WaitUitl;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import java.util.List;

import static cn.gloryroad.util.WaitUitl.waitWebElement;


public class KeyWordsAction {
    //声明静态webdriver对象
    public static WebDriver driver;
    //声明存储定位表达配置文件的objectMap对象
    private static ObjectMap objectMap = new ObjectMap(Constants.Path_ConfigurationFile);

    static {
        //指定Log4j配置文件为log4j.xml
        DOMConfigurator.configure("log4j.xml");
    }

    //判断此方法名称对应Excel文件关键字列中的open_browser关键字，操作值列中的内容用于指定使用何种浏览器运行测试用例
    public static void open_browser(String string, String browserName) {
        if (browserName.equals("chrome")) {
            System.setProperty("webdriver.chrome.diver", "C:\\Python39\\chromedriver.exe");
            driver = new ChromeDriver();
            Log.info("chrome浏览器实例已经声明");
        } else if (browserName.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "D:\\WebDriver\\geckodriver.exe");
            driver = new FirefoxDriver();
            Log.info("firefox浏览器实例已经声明");
        } else {
            System.setProperty("webdriver.edge.driver", "D:\\WebDriver\\msedgedriver.exe");
            driver = new EdgeDriver();
            Log.info("edge浏览器实例已经声明");
        }
    }

    //此方法的名称对应Excel文件“关键字”列中的navigate关键字
    public static void navigate(String string, String url) {
        driver.get(url);
        Log.info("浏览器访问网址：" + url);
    }

    public static void switch_frame(String locatorExpression, String string) {
        try {
            driver.switchTo().frame(driver.findElement(objectMap.getLocator(locatorExpression)));
            Log.info("进入frame" + locatorExpression + "成功");
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("进入frame" + locatorExpression + "失败，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }

    //此方法的名称对应Excel文件关键字列中的input_userName关键字
    public static void input(String locatorExpression, String inputString) {
        System.out.println("收到参数值：" + inputString);

        try {
            driver.findElement(objectMap.getLocator(locatorExpression)).clear();
            Log.info("清除" + locatorExpression + "输入框中所有内容");
            driver.findElement(objectMap.getLocator(locatorExpression)).sendKeys(inputString);
            Log.info("在" + locatorExpression + "输入框输入" + inputString);
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("在" + locatorExpression + "输入框中输入" + inputString + "时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }


    //此方法的名称对应Excel文件关键字列中的click_login
    public static void click(String locatorExpression, String string) {
        try {
            driver.findElement(objectMap.getLocator(locatorExpression)).click();
            Log.info("单击" + locatorExpression + "页面元素成功");
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("单击" + locatorExpression + "页面元素时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }

    //此方法的名称对应Excel文件关键字列中的default_frame
    public static void default_frame(String string1, String string2) {
        try {
            driver.switchTo().defaultContent();
            Log.info("退出iframe");
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("退出首页iframe时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }

    //此方法的名称对应Excel文件关键字列中的WaitFor_Element关键字
    public static void WaitFor_Element(String xpathExpreesion, String string) {
        try {
            waitWebElement(driver, objectMap.getLocator(xpathExpreesion));
            Log.info("显示等待元素出现成功，元素是：" + xpathExpreesion);
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("显示等待元素时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }

    }

    //此方法的名称对应Excel文件关键字列中的close_browser关键字
    public static void close_browser(String string1, String string2) {
        try {
            System.out.println("浏览器关闭函数被调用");
            Log.info("关闭浏览器窗口");
            driver.quit();
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("关闭浏览器出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }

    //此方法的名称对应Excel文件关键字列中的press_Tab关键字
    public static void press_Tab(String string1, String string2) {
        try {
            Thread.sleep(2000);
            KeyBoardUtil.pressTabKey();
            Log.info("按Tab键成功");
        } catch (InterruptedException e) {
            ExcelUtil.testResult = false;
            Log.info("按Tab键时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }

    }

    //此方法的名称对应Excel文件关键字列中的paste_mailContent关键字
    public static void paste_mailContent(String string, String pasteContent) {
        try {
            KeyBoardUtil.setAndctrlVClipboardData(pasteContent);
            Log.info("成功粘贴邮件正文：" + pasteContent);
        } catch (Exception e) {
            ExcelUtil.testResult = false;

            Log.info("在输入框中粘贴内容是出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }

    }

    //此方法的名称对应Excel文件关键字列中的Assert_String关键字
    public static void Assert_String(String string, String assertString) {
        try {
            Assert.assertTrue(driver.getPageSource().contains(assertString));
            Log.info("成功断言关键字");
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("出现断言失败，具体断言失败信息：" + e.getMessage());
            e.printStackTrace();
        }
    }

    //此方法的名称对应Excel文件关键字列中的press_enter关键字
    public static void press_enter(String string1, String string2) {
        try {
            KeyBoardUtil.pressEnterKey();
            Log.info("按Enter键成功");
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("按Enter键时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }

    //此方法的名称对应Excel文件关键字列中的sleep关键字
    public static void sleep(String string, String sleepTime) {
        try {
            WaitUitl.sleep(Integer.parseInt(sleepTime));
            Log.info("休眠" + Integer.parseInt(sleepTime) / 1000 + "秒成功");
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("线程休眠时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }

    }

    //此方法的名称对应Excel文件关键字列中的paste_uploadFileName关键字
    public static void paste_uploadFileName(String string, String uploadFileName) {
        try {
            KeyBoardUtil.setAndctrlVClipboardData(uploadFileName);
            Log.info("成功粘贴上传文件名：" + uploadFileName);
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("在文件名输入框中粘贴上传文件名称时常出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }

    }

/*    //此方法的名称对应Excel文件关键字列中的click_writeLetterLink关键字
    public static void click_writeLetterLink(String string) {
        try {
            driver.findElement(objectMap.getLocator("homepage.writeLetterLink")).click();
            Log.info("单击写信链接成功");
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("单击写信链接时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }*/

   /* //此方法的名称对应Excel文件关键字列中的input_recipients关键字
    public static void input_recipients(String recipients) {
        try {
            driver.findElement(objectMap.getLocator("writemailpage.recipients")).sendKeys(recipients);
            Log.info("成功粘贴邮件正文：" + recipients);
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("在输入框中粘贴内容时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }*/

 /*   //此方法的名称对应Excel文件关键字列中的input_mailSubject关键字
    public static void input_mailSubject(String mailSubject) {
        try {
            driver.findElement(objectMap.getLocator("writemailpage.mailsubject")).sendKeys(mailSubject);
            Log.info("成功输入邮件主题：" + mailSubject);
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("在邮件主题输入框输入邮件主题时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }*/



   /* //此方法的名称对应Excel文件关键字列中的click_addAttachment关键字
    public static void click_addAttachment(String string1,String string2) {
        try {
            driver.findElement(objectMap.getLocator("writemailpage.addattachmentlink")).click();
            Log.info("单击添加附件按钮成功");
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("单击添加附件按钮时出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }*/


    //此方法的名称对应Excel文件关键字列中的click_sendMailButton关键字
    public static void click_sendMailButton(String loccatorExpreesion, String string) {
        try {
            List<WebElement> buttons = driver.findElements(objectMap.getLocator(loccatorExpreesion));
            buttons.get(0).click();
            Log.info("单击发送邮件按钮成功");
            System.out.println("发送按钮被成功点击");
        } catch (Exception e) {
            ExcelUtil.testResult = false;
            Log.info("单击发送邮件按钮出现异常，具体异常信息：" + e.getMessage());
            e.printStackTrace();
        }
    }




     /* //此方法的名称对应Excel文件关键字列中的input_password
     public static void input_passWord(String password) {
         try {
             driver.findElement(objectMap.getLocator("login.password")).clear();
             Log.info("密码输入框的原有内容");
             driver.findElement(objectMap.getLocator("login.password")).sendKeys(password);
             Log.info("在密码输入框中输入密码：" + password);
         } catch (Exception e) {
             ExcelUtil.testResult = false;
             Log.info("在密码输入框中输入密码出现异常，具体异常信息：" + e.getMessage());
             e.printStackTrace();
         }
     }
 */

}
