package com.idera.testrail.tests;

//import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Properties;
//import static main.Configuration.*;







    @SuppressWarnings("unused")
    public class TestRail {
/*
        Properties properties;

        APIClient client;
        String runname = "";



        public void connectTestRail() throws InterruptedException {
            client.setUser(TestRail_username);
            client.setPassword(TestRail_pwd);
        }

        public String createTestRun(String projid, String suiteid, String refnum, String runname) throws Exception {
            connectTestRail();
            String projectId = projid;
            String testsuiteId = suiteid;
            String refNum = refnum;
            String tesrunid="";
//		runname = runTestName + "_" + ((new SimpleDateFormat("dd/MM/yyyy-hh:mm:ss")).format(new Date()));

            String jsoncontent = "{\r\n" + "  \"suite_id\": " + testsuiteId + ",\r\n" + "  \"name\": \"" + runname
                    + "\",\r\n" + "  \"refs\": \"" + refnum + "\",\r\n" + "  \"include_all\": true\r\n" + "}";

            System.out.println(jsoncontent.trim());
            JSONParser parser = new JSONParser();
            JSONObject json;
            try {
                json = (JSONObject) parser.parse(jsoncontent.trim());
                JSONObject c = (JSONObject) client.sendPost("add_run/" + projectId, json);
                System.out.println("TestRun ID ->> " + c.get("id"));
                tesrunid = c.get("id").toString();
//			createTestCaseDetails(tesrunid);
            } catch (ParseException | IOException | APIException e) {
                e.printStackTrace();
            }
            return tesrunid;
        }

        public void createTestCaseDetails(String runid) throws InterruptedException {
            String runId = runid;
            connectTestRail();
            try {
                JSONArray c = (JSONArray) client.sendGet("get_tests/" + runId);
                try {
                    File existing = new File(
                            System.getProperty("user.dir").concat("/test_data/TestRail/SampleTestRailTCs.json"));
                    Files.delete(existing.toPath());
                } catch (Exception e) {

                }
                try {
                    FileWriter fw = new FileWriter(
                            System.getProperty("user.dir").concat("/test_data/TestRail/SampleTestRailTCs.json"));
                    fw.write(c.toString());
                    fw.close();
                } catch (Exception e) {
                    System.out.println("File Write Issue" + e);
                }

            } catch (IOException | APIException e) {
                e.printStackTrace();
            }

        }

        public String getTCDetails(String testTitle) throws InterruptedException {
            String caseid = "";
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(
                        new FileReader(System.getProperty("user.dir").concat("/test_data/TestRail/SampleTestRailTCs.json")));
                JSONArray jobj = (JSONArray) obj;
                for (int i = 0; i < jobj.size(); i++) {
                    JSONObject msg = (JSONObject) jobj.get(i);

                    String id = msg.get("title").toString();
                    if (msg.get("title").toString().equals(testTitle)) {
                        caseid = msg.get("id").toString();
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return caseid;

        }

        public void updateStatusTestRail(String tcname, String tcstatus) throws Exception {
            connectTestRail();

            String tccaseid = getTCDetails(tcname);
            int tcstatusindicator = 0;

            switch (tcstatus) {
                case "Passed":
                    tcstatusindicator = 1;
                    break;

                case "Failed":
                    tcstatusindicator = 5;
                    break;

                case "Blocked":
                    tcstatusindicator = 2;
                    break;

                case "Retest":
                    tcstatusindicator = 4;
                    break;

                default:
                    tcstatusindicator = 4;
                    break;
            }
            String updatestatuspost = "{\r\n" + "	\"status_id\":" + tcstatusindicator + "\r\n" + "}";
//		 System.out.println(updatestatuspost.trim());
            JSONParser parser = new JSONParser();
            JSONObject json;
            try {
                json = (JSONObject) parser.parse(updatestatuspost.trim());
                JSONObject c = (JSONObject) client.sendPost("add_result/" + tccaseid, json);
//			System.out.println(c);
            } catch (ParseException | IOException | APIException e) {
                e.printStackTrace();
            }
        }

        public String getLatestTestRunID() throws Exception {
            connectTestRail();
            JSONArray runs = (JSONArray) client.sendGet("get_runs/16&suite_id=329&is_completed=0");
            JSONObject run = (JSONObject) runs.get(0);
            String runID = run.get("id").toString();
            System.out.println("Latest Test Run ID ->> " + runID);
            return runID;
        }

        public void shareReportEmail() throws InterruptedException {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (USE_HEADLESS_BROWSER != null && USE_HEADLESS_BROWSER.equalsIgnoreCase("true"))
                options.addArguments("--headless");
            WebDriver driver = new ChromeDriver(options);
            driver.manage().window().maximize();
            JSONParser parser = new JSONParser();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(240));
            try {
                String runid = getLatestTestRunID();
                driver.get("https://intradodigitalmedia.testrail.io/index.php?/runs/view/" + runid.trim());
                driver.findElement(By.id("name")).sendKeys(TestRail_username);
                driver.findElement(By.id("password")).sendKeys(TestRail_pwd);
                driver.findElement(By.id("button_primary")).click();
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Reports']"))).click();
                wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath("//div[text()='Run']/following::a[contains(text(),'Summary')][1]")))
                        .click();
                wait.until(ExpectedConditions
                                .elementToBeClickable(By.xpath("//label[contains(text(),'Email the report as PDF attachment')]")))
                        .click();
                JavascriptExecutor js = ((JavascriptExecutor) driver);
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                Utils.delaySec(1);
                wait.until(
                                ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='notify_attachment_recipients']")))
                        .clear();
                wait.until(
                                ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='notify_attachment_recipients']")))
                        .sendKeys(TestRail_MailRecipients.replace(";", "\r\n"));
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Add Report')]")))
                        .click();
                driver.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }*/

    }

