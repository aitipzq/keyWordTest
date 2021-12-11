package cn.gloryroad.configuration;

public class Constants {
    //测试数据相关常量设定
    public static final String Path_ExcelFile = "src/main/java/cn/gloryroad/data/关键字驱动测试用例.xlsx";
    public static final String Path_ConfigurationFile = System.getProperty("user.dir") + "\\objectMap.properties";
    //第一列用0表示，测试用例序号列
    public static final int Col_TestCaseID = 0;
    //第四列用3表示，为关键字列
    public static final int Col_KeyWordAction = 3;
    //第五列用4表示，为操作元素的定位表达式
    public static final int Col_LocatorExpression = 4;
    //第五列用4表示，为操作值
    public static final int Col_ActionValue = 5;
    //第三列用2表示，为测试步骤描述列
    public static final int Col_RunFlag = 2;
    //第七列用6表示，为操作结果列
    public static final int Col_TestStepTestResult = 6;
    //“测试用例集合”sheet中测试结果列号常量设定
    public static final int Col_TestSuiteTestResult = 3;
    //sheet名称常量设定
    public static final String Sheet_TestSteps = "发送邮件";
    //“测试用例集合”sheet常量设定
    public static final String Sheet_TestSuite = "测试用例集合";

}
