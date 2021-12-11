package cn.gloryroad.testScript;

import cn.gloryroad.configuration.Constants;
import cn.gloryroad.configuration.KeyWordsAction;
import cn.gloryroad.util.ExcelUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class TestSendMailWithAttachmentByExcel {
    public static Method method[];
    public static String keyword;
    public static String value;
    public static KeyWordsAction keyWordsaction;


    @Test
    public void testSendMailWithAttachment() throws Exception {
        //声明一个关键动作类的实例
        keyWordsaction = new KeyWordsAction();
        //使用java的反射机制获取KeyWordsAction类的所有方法对象
        method = keyWordsaction.getClass().getMethods();
        //定义Excel文件路径
        String excelFilePath = Constants.Path_ExcelFile;
        //去读Excel文件中“发送邮件”Sheet为操作目标
        ExcelUtil.setExcelFile(excelFilePath, Constants.Sheet_TestSteps);
        //从Excel文件“发送邮件”sheet中，将每一行的第四列读取出来作为关键字
        for (int iRow = 1; iRow <= ExcelUtil.getLastRowNum(); iRow++) {
            keyword = ExcelUtil.getCellData(Constants.Sheet_TestSteps, iRow, Constants.Col_KeyWordAction);
            value = ExcelUtil.getCellData(Constants.Sheet_TestSteps, iRow, Constants.Col_ActionValue);
            execute_Actions();
        }

    }


    private static void execute_Actions() {
        try {
            for (int i = 0; i < method.length; i++) {
                //通过遍历，判断关键字和Keywordsaction类的那个方法名一致
                if (method[i].getName().equals(keyword)) {
                    //找到KeyWordsAction类中的映射方法后，通过invoke方法完成函数调用
                    method[i].invoke(keyWordsaction, value);
                    break;
                }
            }
        } catch (Exception e) {
                Assert.fail("执行出现异常，测试用例执行失败");
        }
    }

}
