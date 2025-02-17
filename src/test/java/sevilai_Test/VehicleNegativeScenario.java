package sevilai_Test;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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

public class VehicleNegativeScenario extends Login_Test {
    WebDriverWait wait;
   @DataProvider(name = "VehicleTestData")
    public Object[][] getTestData() {
        return new Object[][] {
            // Test Case ID, Vehicle Capacity, Vehicle Number, Expected Error
            {"VN_TC_01", "50", "HFG523", "Vehicle number is already exists"},
            {"VN_TC_02", "", "", "Vehicle Number is required, Vehicle Capacity is required"},
            {"VN_TC_03", "50", "", "Vehicle Number is required"},
            {"VN_TC_04", "", "ABC123", "Vehicle Capacity is required"}
        };
    }

    @Test(priority = 1, dependsOnMethods = "testLogin", dataProvider = "VehicleTestData")
    public void testVehicleNegativeScenarios(String testCaseID, String vehicleCapacity, String vehicleNumber, String expectedError) {
    	
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Navigate to Vehicle tab
        WebElement vehicleButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
        vehicleButton.click();
        WebElement vehiclePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
        Assert.assertTrue(vehiclePage.isDisplayed(), "Vehicle page is not displayed.");
        Reporter.log("PASSED: Vehicle page is successfully displayed.", true);

        // Click 'Add' button
        driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();

        // Input Vehicle Capacity
        if (!vehicleCapacity.isEmpty()) {
            WebElement vehicleCapacityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Capacity']")));
            vehicleCapacityField.sendKeys(vehicleCapacity);
        }

        // Input Vehicle Number
        if (!vehicleNumber.isEmpty()) {
            WebElement vehicleNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Number']")));
            vehicleNumberField.sendKeys(vehicleNumber);
        }

        // Click 'Add' button
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Add']")));
        addButton.click();

        // Verify error messages
        try {
            // Check for specific error messages based on expected error
            if (expectedError.contains("Vehicle number is already exists")) {
                WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label*='Vehicle number is already exists']")));
                String messageText = toastMessage.getAttribute("aria-label");
                Assert.assertTrue(messageText.contains("Vehicle number is already exists"),
                        "Toast message validation failed for Test Case: " + testCaseID);
                Reporter.log("Test Case " + testCaseID + " Passed: Vehicle number is already exists", true);
            } else {
                // Check if "Vehicle Number is required" error appears
                if (expectedError.contains("Vehicle Number is required") && vehicleNumber.isEmpty()) {
                    WebElement vehicleNumberError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Vehicle Number is required']")));
                    Assert.assertTrue(vehicleNumberError.isDisplayed(), "Vehicle number error message not displayed.");
                    Reporter.log("Test Case " + testCaseID + " Passed: Vehicle Number is required", true);
                }

                // Check if "Vehicle Capacity is required" error appears
                if (expectedError.contains("Vehicle Capacity is required") && vehicleCapacity.isEmpty()) {
                    WebElement vehicleCapacityError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Vehicle Capacity is required']")));
                    Assert.assertTrue(vehicleCapacityError.isDisplayed(), "Vehicle capacity error message not displayed.");
                    Reporter.log("Test Case " + testCaseID + " Passed: Vehicle Capacity is required", true);
                }

                // Log the result for the case when both fields are empty
                if (expectedError.equals("Vehicle Number is required, Vehicle Capacity is required") && vehicleNumber.isEmpty() && vehicleCapacity.isEmpty()) {
                    WebElement vehicleNumberError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Vehicle Number is required']")));
                    WebElement vehicleCapacityError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Vehicle Capacity is required']")));
                    Assert.assertTrue(vehicleNumberError.isDisplayed(), "Vehicle number error message not displayed.");
                    Assert.assertTrue(vehicleCapacityError.isDisplayed(), "Vehicle capacity error message not displayed.");
                    Reporter.log("Test Case " + testCaseID + " Passed: Both 'Vehicle Number is required' and 'Vehicle Capacity is required' messages are displayed", true);
                }
            }
        } catch (AssertionError e) {
            Reporter.log("Test Case " + testCaseID + " Failed: " + e.getMessage(), true);
            throw e;
        }

        
        // After Test Case 1, navigate back to the homepage
        WebElement CrossButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@role='button']")));
        CrossButton.click();
        WebElement homeButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Home')]")));
        homeButton.click();
        Assert.assertTrue(homeButton.isDisplayed(), "Home page is not displayed.");
        Reporter.log("- PASSED: Home page is successfully displayed.", true);
    }
}
