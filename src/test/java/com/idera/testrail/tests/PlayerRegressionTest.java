package com.idera.testrail.tests;

//import org.junit.jupiter.api.DisplayName;



import org.junit.jupiter.params.ParameterizedTest;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;

public class PlayerRegressionTest extends Base{
    protected static ThreadLocal<String> description = new ThreadLocal<>();

    @Test
    public  void  PlayerRegression(Method method , ITestContext iTestContext) {
        Test test = method.getAnnotation(Test.class);
        String testCaseName = method.getName();
        System.out.println(testCaseName);
      //  String   testSuiteName = iTestContext.getSuite().getName().replaceAll("[ (\\d)]", "");
       // String    testName = iTestContext.getCurrentXmlTest().getName();
       // System.out.println(testSuiteName+"  "+testName);
        Assert.assertEquals(4, 2*2, "Should equal 4");

    }

    @Test
    void PlayerRegression2() {
        Assert.assertEquals(4, 2*2, "Should equal 4");
    }
}
