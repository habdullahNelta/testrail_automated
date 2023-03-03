package com.idera.testrail.tests;

import org.junit.jupiter.api.DisplayName;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


class MultiplicationTest extends Base{

    Base testb=new Base();




/*
    @AfterSuite
    @Parameters({"suiteXmlFile"})
    void MultiplyTwoNumbers(String suiteXmlFile) throws Exception {
        Assert.assertEquals(4, 2*2, "Should equal 4");
        testb.TestrailUpdateCSVtoYml(suiteXmlFile);

    }*/

    @Test

    void MultiplyTwoNumbers1() {
        Assert.assertEquals(12, 6*2, "Should equal 12");
    }
    @Test
    void MultiplyTwoNumbers2() {
        Assert.assertEquals(12, 6*2, "Should equal 12");
    }
}