package cn.gloryroad.testScript;

import cn.gloryroad.configuration.Constants;
import cn.gloryroad.configuration.KeyWordsAction;
import cn.gloryroad.util.ExcelUtil;
import cn.gloryroad.util.Log;
import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Method;


public class TestSuiteByExcel {
    public static KeyWordsAction keyWordsaction;
    public static Method method[];
    public static String keyword;
    public static String locatorExpression;
    public static String value;
    public static int testStep;
    public static int testLastStep;
    public static String testCaseID;
    public static String testCaseRunFlag;


    @Test
    public void testTestSuite() throws Exception {
        //声明一个关键工作类实例
        keyWordsaction = new KeyWordsAction();
        //使用java的反射机制获取到KeyWordsAction类的所有方法对象
        method = keyWordsaction.getClass().getMethods();
        //定义Excel文件路径
        String excelFilePath = Constants.Path_ExcelFile;
        //读取Excel文件中的“发送邮件”Sheet为操作目标
        ExcelUtil.setExcelFile(excelFilePath);
        //读取“测试用例集合”Sheet中的测试用例总数
        int testCasesCount = ExcelUtil.getRowCount(Constants.Sheet_TestSuite);
//        System.out.println(testCasesCount);
        //使用for循环执行所有标记为“y”的测试用例
        for (int testCaseNo = 1; testCaseNo <= testCasesCount; testCaseNo++) {
            //读取“测试用例集合”sheet中每行的测试用例序号
            testCaseID = ExcelUtil.getCellData(Constants.Sheet_TestSuite, testCaseNo, Constants.Col_TestCaseID);
//            System.out.println(testCaseID);
            //读取”测试用例集合“sheet中每行测试用例序号
            testCaseRunFlag = ExcelUtil.getCellData(Constants.Sheet_TestSuite, testCaseNo, Constants.Col_RunFlag);
//            System.out.println(testCaseRunFlag);

            //判断“是否运行”列中的值为y，y则执行测试用例中的所有步骤
            if (testCaseRunFlag.equalsIgnoreCase("y")) {
                Log.startTEstCase(testCaseID);
                ExcelUtil.testResult = true;
                //在“发送邮件”sheet中获取当前要执行的测试用例第一个步骤的行号
                testStep = ExcelUtil.getFirstRowContainsTestCaseID(Constants.Sheet_TestSteps, testCaseID, Constants.Col_TestCaseID);
                System.out.println(testStep);
                //在“发送邮件”sheet中获取当前要执行的测试用例的最后一个步骤的行号
                testLastStep = ExcelUtil.getTestCaseLastStepRow(Constants.Sheet_TestSteps, testCaseID, testStep);
                System.out.println(testLastStep);
                //遍历用例中的所有测试步骤
                for (; testStep < testLastStep; testStep++) {
                    //从“发送邮件”Sheet中读取关键字和操作值调用execute_Actions方法
                    keyword = ExcelUtil.getCellData(Constants.Sheet_TestSteps, testStep, Constants.Col_KeyWordAction);
                    Log.info("读取的关键字是：" + keyword);

                    locatorExpression = ExcelUtil.getCellData(Constants.Sheet_TestSteps, testStep, Constants.Col_LocatorExpression);
                    Log.info("读取的操作元素的定位表达式是：" + locatorExpression);

                    value = ExcelUtil.getCellData(Constants.Sheet_TestSteps, testStep, Constants.Col_ActionValue);
                    Log.info("读取的操作值是：" + value);

                    execute_Actions();
                    if (ExcelUtil.testResult == false) {
                        ExcelUtil.setCellData(Constants.Sheet_TestSuite, testCaseNo, Constants.Col_TestSuiteTestResult, "测试执行失败");
                        break;

                    }
                    if (ExcelUtil.testResult == true) {
                        ExcelUtil.setCellData(Constants.Sheet_TestSuite, testCaseNo, Constants.Col_TestSuiteTestResult, "执行成功");
                    }
                }
                Log.endTestCase(testCaseID);

            }
        }
    }

    private static void execute_Actions() {
        try {
            for (int i = 0; i < method.length; i++) {
                //使用反射的方法，找到关键字对应的测试方法并将value作为测试方法的函数值进行调用
                if (method[i].getName().equals(keyword)) {
                    method[i].invoke(keyWordsaction, locatorExpression, value);
                    if (ExcelUtil.testResult == true) {
                        ExcelUtil.setCellData(Constants.Sheet_TestSteps, testStep, Constants.Col_TestStepTestResult, "测试步骤执行成功");
                        break;
                    } else {
                        ExcelUtil.setCellData(Constants.Sheet_TestSteps, testStep, Constants.Col_TestStepTestResult, "测试步骤执行失败");
                        KeyWordsAction.close_browser("", "");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            Assert.fail("执行过程中出现异常，测试用例执行失败");
        }
    }

    @BeforeClass
    public void beforclass() {
        DOMConfigurator.configure("log4j.xml");
    }
}
