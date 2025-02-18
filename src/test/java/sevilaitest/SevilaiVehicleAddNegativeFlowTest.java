package sevilaitest;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class SevilaiVehicleAddNegativeFlowTest extends LoginTest {
    WebDriverWait wait;
    ExtentReports extent;
    ExtentTest test;

    @BeforeSuite
    public void setupExtentReports() {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("VehicleNegativeScenarioReport.html");
        sparkReporter.config().setDocumentTitle("Vehicle Negative Test Report");
        sparkReporter.config().setReportName("Vehicle Negative Test Scenarios");
        sparkReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "Staging");
        extent.setSystemInfo("Tester", "Your Name");
    }

    @DataProvider(name = "VehicleTestData")
    public Object[][] getTestData() {
        return new Object[][] {
            {"VN_TC_01", "50", "SPR123", "Vehicle number is already exists"},
            {"VN_TC_02", "", "", "Vehicle Number is required, Vehicle Capacity is required"},
            {"VN_TC_03", "50", "", "Vehicle Number is required"},
            {"VN_TC_04", "", "ABC123", "Vehicle Capacity is required"}
        };
    }

    @Test(dataProvider = "VehicleTestData")
    public void testVehicleNegativeScenarios(String testCaseID, String vehicleCapacity, String vehicleNumber, String expectedError) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        test = extent.createTest("Test Case: " + testCaseID, "Testing negative scenarios for vehicle module.");

        try {
            // Navigate to Vehicle tab
           
            Actions actions = new Actions(driver);

            // Wait for and click the driver tab button
            WebElement vehicleTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
            actions.moveToElement(vehicleTabButton).click().perform();
            test.pass("Navigated to Vehicle tab.");

            WebElement vehiclePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
            Assert.assertTrue(vehiclePage.isDisplayed(), "Vehicle page is not displayed.");
            test.pass("Vehicle page is successfully displayed.");

            // Click 'Add' button
            driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();
            test.pass("Clicked on 'Add' button.");

            // Input Vehicle Capacity
            if (!vehicleCapacity.isEmpty()) {
                WebElement vehicleCapacityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Capacity']")));
                vehicleCapacityField.sendKeys(vehicleCapacity);
                test.info("Entered Vehicle Capacity: " + vehicleCapacity);
            }

            // Input Vehicle Number
            if (!vehicleNumber.isEmpty()) {
                WebElement vehicleNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Number']")));
                vehicleNumberField.sendKeys(vehicleNumber);
                test.info("Entered Vehicle Number: " + vehicleNumber);
            }

            // Click 'Add' button
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Add']")));
            addButton.click();
            test.pass("Clicked on 'Add' button to submit.");

            // Verify error messages
            if (expectedError.contains("Vehicle number is already exists")) {
                WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label*='Vehicle number is already exists']")));
                String messageText = toastMessage.getAttribute("aria-label");
                Assert.assertTrue(messageText.contains("Vehicle number is already exists"), "Toast message validation failed.");
                test.pass("Verified error message: Vehicle number is already exists.");
            } else {
                if (expectedError.contains("Vehicle Number is required") && vehicleNumber.isEmpty()) {
                    WebElement vehicleNumberError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Vehicle Number is required']")));
                    Assert.assertTrue(vehicleNumberError.isDisplayed(), "Vehicle number error message not displayed.");
                    test.pass("Verified error message: Vehicle Number is required.");
                }

                if (expectedError.contains("Vehicle Capacity is required") && vehicleCapacity.isEmpty()) {
                    WebElement vehicleCapacityError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Vehicle Capacity is required']")));
                    Assert.assertTrue(vehicleCapacityError.isDisplayed(), "Vehicle capacity error message not displayed.");
                    test.pass("Verified error message: Vehicle Capacity is required.");
                }
            }
        

        
        // After Test Case 1, navigate back to the homepage
        WebElement CrossButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@role='button']")));
        CrossButton.click();
WebElement VehicleButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
        
        actions.moveToElement(VehicleButton).click().perform();
        Assert.assertTrue(VehicleButton.isDisplayed(), "Driver page is not displayed.");
        Reporter.log("- PASSED: Vehicle page is successfully displayed.", true);
        }
        catch (Exception e) {
            test.fail("Test Case " + testCaseID + " Failed: " + e.getMessage());
            throw e;
        } finally {
            extent.flush();
        }
    }
}

