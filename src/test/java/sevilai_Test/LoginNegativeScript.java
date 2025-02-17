package sevilai_Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginNegativeScript {
    WebDriver driver;
    String screenshotFolderPath;
    ExtentReports extent;

    @BeforeSuite
    public void setupReport() {
        // Set up ExtentReports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("ExtentReports/LoginNegativeTestsReport.html");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setDocumentTitle("Login Negative Tests");
        sparkReporter.config().setReportName("Negative Test Case Report");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Browser", "Edge");
        extent.setSystemInfo("Tester", "Automation Engineer");
    }

    @BeforeMethod
    public void setupBrowser() throws InterruptedException {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.get("http://staging.sevilaitech.app/");
        Thread.sleep(4000);

        // Create folder for screenshots
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        screenshotFolderPath = "Screenshots/LoginNegativeTests_" + timeStamp;
        File folder = new File(screenshotFolderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    @DataProvider(name = "negativeLoginData")
    public Object[][] negativeLoginDataProvider() {
        return new Object[][] {
            {"karan@gmail.com", "66666666", "User does not exist.", "LN_TC_01 - Both are Wrong"},
            {"saraswathi.m@kaditinnovations.com", "66666666", "Incorrect password", "LN_TC_02 - Incorrect password"},
            {"abc@gmail.com", "97903450", "User does not exist.", "LN_TC_03 - Incorrect email"},
            {"", "97903450", "Every field must be filled in", "LN_TC_04 - Email Field as Empty"},
            {"saraswathi.m@kaditinnovations.com", "", "Every field must be filled in", "LN_TC_05 - Password Field as Empty"},
            {"", "", "Every field must be filled in", "LN_TC_06 - Both fields empty"},
            {"admin' OR '1'='1", "password", "Please Enter the Valid Email", "LN_TC_07 - SQL injection"}
        };
    }

    @Test(dataProvider = "negativeLoginData")
    public void testNegativeLogin(String username, String password, String expectedErrorMessage, String testCaseID) throws Exception {
        // Create a new test in the report for each data set
        ExtentTest test = extent.createTest(testCaseID + " - " + expectedErrorMessage);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Enter username
            WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Username']")));
            usernameField.click();
            usernameField.clear();
            usernameField.sendKeys(username);

            // Enter password
            WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Password']")));
            passwordField.click();
            passwordField.sendKeys(password);

            // Click login button
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Login']")));
            js.executeScript("arguments[0].scrollIntoView(true);", loginButton);
            loginButton.click();

            // Capture toast message
            String dynamicErrorXPath = "//flt-semantics[contains(@aria-label, 'Login Failed')]";
            FluentWait<WebDriver> fluentWait = new FluentWait<>(driver)
                    .withTimeout(Duration.ofSeconds(40))
                    .pollingEvery(Duration.ofMillis(500))
                    .ignoring(Exception.class);

            WebElement errorMessageElement = fluentWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(dynamicErrorXPath)));
            js.executeScript("arguments[0].scrollIntoView(true);", errorMessageElement);

            // Extract and validate error message
            String actualErrorMessage = errorMessageElement.getAttribute("aria-label").trim();
            String cleanedActualErrorMessage = cleanErrorMessage(actualErrorMessage);

            // Log actual vs expected message
            test.info("Username: " + username);
            test.info("Password: " + password);
            test.info("Actual Error Message (Cleaned): " + cleanedActualErrorMessage);
            test.info("Expected Error Message: " + expectedErrorMessage);

            Assert.assertTrue(cleanedActualErrorMessage.equalsIgnoreCase(expectedErrorMessage),
                    "Expected error message to be: '" + expectedErrorMessage + "', but got: '" + cleanedActualErrorMessage + "'");
            test.pass("Test passed: Expected error message matches actual error message.");

            // Capture screenshot for validation
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String fileName = "toast_message_" + username + "_" + password + ".png";
            File screenshotPath = new File(screenshotFolderPath + "/" + fileName);
            FileUtils.copyFile(screenshot, screenshotPath);
            test.addScreenCaptureFromPath(screenshotPath.getAbsolutePath());
        } catch (Exception e) {
            test.fail("Test failed due to exception: " + e.getMessage());
            throw e;
        }
    }

    // Helper method to clean dynamic parts from the error message
    private String cleanErrorMessage(String message) {
        return message.replace("Login Failed!", "").trim().replaceAll("[0-9%]+", "").trim();
    }

    @AfterMethod
    public void tearDownBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite
    public void tearDownReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
