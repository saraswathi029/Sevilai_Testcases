package sevilaitest;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SevilaiVehicleEditNegativeFlow extends LoginTest {
    WebDriverWait wait;

    @DataProvider(name = "VehicleTestData")
    public Object[][] getTestData() {
        return new Object[][] {
            {"VN_TC_01", "50", "SPR123", "Vehicle number is already exists"},
            {"VN_TC_02", "", "", "Vehicle Number is required, Vehicle Capacity is required"},
            {"VN_TC_03", "50", "", "Vehicle Number is required"},
            {"VN_TC_04", "", "ABC123", "Vehicle Capacity is required"}
        };
    }
 // Reusable method to clear data
    public void clearFieldData(WebElement element) {
        try {
            element.sendKeys(Keys.CONTROL + "a"); // Select all text
            element.sendKeys(Keys.BACK_SPACE);   // Clear selected text
            //Reporter.log("Field data cleared successfully.", true);
        } catch (Exception e) {
            Reporter.log("Failed to clear field data: " + e.getMessage(), true);
            throw e;
        }
    }
    

    @Test(priority = 1, dependsOnMethods = "testLogin", dataProvider = "VehicleTestData")
    public void testVehicleNegativeScenarios(String testCaseID, String vehicleCapacity, String vehicleNumber, String expectedError) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Step 1: Perform setup actions only for the first test case
        if (testCaseID.equals("VN_TC_01")) {
            // Navigate to Vehicle tab
            WebElement vehicleButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
            vehicleButton.click();
            WebElement vehiclePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
            Assert.assertTrue(vehiclePage.isDisplayed(), "Vehicle page is not displayed.");
            Reporter.log("PASSED: Vehicle page is successfully displayed.", true);

            // Set rows per page and toggle edit column
            WebElement pageRow20 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(),'20 rows')]")));
            pageRow20.click();
            WebElement pageRow50 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='50 rows per page']")));
            pageRow50.click();
        }

        // Step 2: Perform search functionality only for the first test case
        if (testCaseID.equals("VN_TC_01")) {
            WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Search...']")));
            searchElement.sendKeys("");
        }

        // Step 3: Locate and click the Edit button dynamically
        try {
            WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Edit']")));
            editButton.click();
            Reporter.log("Edit button clicked successfully for vehicle: " + vehicleNumber, true);
        } catch (Exception e) {
            Reporter.log("Vehicle not found or edit button not clickable for vehicle: " + vehicleNumber, true);
            throw e;
        }
        // Clear and Input Vehicle Capacity dynamically
        try {
            WebElement vehicleCapacityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Capacity']")));
            clearFieldData(vehicleCapacityField); // Clear old data
            if (!vehicleCapacity.isEmpty()) {
                vehicleCapacityField.sendKeys(vehicleCapacity); // Input new data
            }
        } catch (Exception e) {
            Reporter.log("Error interacting with Vehicle Capacity field for Test Case: " + testCaseID, true);
            throw e;
        }

        // Clear and Input Vehicle Number dynamically
        try {
            WebElement vehicleNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Number']")));
            clearFieldData(vehicleNumberField); // Clear old data
            if (!vehicleNumber.isEmpty()) {
                vehicleNumberField.sendKeys(vehicleNumber); // Input new data
            }
        } catch (Exception e) {
            Reporter.log("Error interacting with Vehicle Number field for Test Case: " + testCaseID, true);
            throw e;
        }


        

        // Step 6: Click 'Update' button
        try {
            WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Update']")));
            updateButton.click();
        } catch (Exception e) {
            Reporter.log("Error clicking the Update button for Test Case: " + testCaseID, true);
            throw e;
        }
        
        // Verify error messages
        try {
            if (expectedError.contains("Vehicle number is already exists")) {
                WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label*='Vehicle number is already exists']")));
                String messageText = toastMessage.getAttribute("aria-label");
                Assert.assertTrue(messageText.contains("Vehicle number is already exists"), "Toast message validation failed for Test Case: " + testCaseID);
                Reporter.log("Test Case " + testCaseID + " Passed: Vehicle number is already exists", true);
            } else {
                if (expectedError.contains("Vehicle Number is required") && vehicleNumber.isEmpty()) {
                    WebElement vehicleNumberError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Vehicle Number is required']")));
                    Assert.assertTrue(vehicleNumberError.isDisplayed(), "Vehicle number error message not displayed.");
                    Reporter.log("Test Case " + testCaseID + " Passed: Vehicle Number is required", true);
                }

                if (expectedError.contains("Vehicle Capacity is required") && vehicleCapacity.isEmpty()) {
                    WebElement vehicleCapacityError = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='Vehicle Capacity is required']")));
                    Assert.assertTrue(vehicleCapacityError.isDisplayed(), "Vehicle capacity error message not displayed.");
                    Reporter.log("Test Case " + testCaseID + " Passed: Vehicle Capacity is required", true);
                }
            }
        } catch (AssertionError e) {
            Reporter.log("Test Case " + testCaseID + " Failed: " + e.getMessage(), true);
            throw e;
        }

        // Return to Home after test case
        try {
            WebElement crossButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@role='button']")));
            crossButton.click();
        } catch (Exception e) {
            Reporter.log("Error closing the form for Test Case: " + testCaseID, true);
            throw e;
        }
    }
}

        
