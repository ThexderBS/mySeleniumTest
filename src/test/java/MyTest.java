import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MyTest {
    WebDriver driver;
    ExtentReports extent;
    private static final String CODE1 = "{\n    \"theme\": \"standard\",\n    \"encoding\": \"utf-8\n}";
    private static final String CODE2 = "{\n    \"protocol\": \"HTTPS\",\n    \"timelineEnabled\": false\n}";

    @BeforeTest
    public void iniciar(){
        System.setProperty("webdriver.chrome.driver", "C:/WebDrivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8081/mitest/");

        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("target/Spark.html");
        extent.attachReporter(spark);

        extent.createTest("ScreenCapture")
                .addScreenCaptureFromPath("extent.png")
                .pass(MediaEntityBuilder.createScreenCaptureFromPath("extent.png").build());

        extent.createTest("LogLevels")
                .info("info")
                .pass("pass")
                .warning("warn")
                .skip("skip")
                .fail("fail");

        extent.createTest("CodeBlock").generateLog(
                Status.PASS,
                MarkupHelper.createCodeBlock(CODE1, CODE2));

        extent.createTest("ParentWithChild")
                .createNode("Child")
                .pass("This test is created as a toggle as part of a child test of 'ParentWithChild'");

        extent.createTest("Tags")
                .assignCategory("MyTag")
                .pass("The test 'Tags' was assigned by the tag <span class='badge badge-primary'>MyTag</span>");

        extent.createTest("Authors")
                .assignAuthor("TheAuthor")
                .pass("This test 'Authors' was assigned by a special kind of author tag.");

        extent.createTest("Devices")
                .assignDevice("TheDevice")
                .pass("This test 'Devices' was assigned by a special kind of devices tag.");

        extent.createTest("Exception! <i class='fa fa-frown-o'></i>")
                .fail(new RuntimeException("A runtime exception occurred!"));

        extent.flush();
        /*extent.attachReporter(spark);
        extent.createTest("MyFirstTest")
                .log(Status.PASS, "This is a logging event for MyFirstTest, and it passed!");
        extent.flush();

        extent.createTest("ScreenCapture")
                .addScreenCaptureFromPath("target/extent.png")
                .pass(MediaEntityBuilder.createScreenCaptureFromPath("target/extent.png").build());
        extent.flush();
        ExtentTest logTest = extent.createTest("Preuba");
        logTest.log(Status.INFO, "Este es info");
        logTest.log(Status.WARNING, "Alerta");
        extent.flush();*/
    }

    @Test
    public void test() throws IOException {
        screen(driver, "c://tmp/beforetest.png") ;
        readInputs();
        extent.createTest("Primer paso")
                .log(Status.PASS, "Leo inputs!");
        extent.flush();
        new MyTestComplement(driver);
        screen(driver, "c://tmp/test.png") ;
        readInputs();
        extent.createTest("Segundo paso")
                .log(Status.PASS, "Leo inputs alterados!");
        extent.flush();
    }

    public void readInputs(){
        By inputs = By.tagName("input");
        List<WebElement> allInputs = driver.findElements(inputs);
        for (WebElement input : allInputs){
            System.out.println(input.getAttribute("value"));
        }
    }

    public static void screen(WebDriver driver, String fileWithPath) throws IOException {
        TakesScreenshot scrShot =((TakesScreenshot) driver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile=new File(fileWithPath);
        FileUtils.copyFile(SrcFile, DestFile);
    }
}
