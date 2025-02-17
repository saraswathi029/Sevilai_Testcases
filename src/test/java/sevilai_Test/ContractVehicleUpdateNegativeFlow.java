package sevilai_Test;

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

public class ContractVehicleUpdateNegativeFlow extends Login_Test {
    

    @DataProvider(name = "VehicleTestData")
    public Object[][] getTestData() {
        return new Object[][] {
            {"CVN_TC_01", "50", "RAH123", "Vehicle number is already exists"},
            {"CVN_TC_04", "", "", "Vehicle Number is required, Vehicle Capacity is required"},
            {"CVN_TC_02", "50", "", "Vehicle Number is required"},
            {"CVN_TC_03", "", "ABC123", "Vehicle Capacity is required"}
        };
    }

    // Reusable method to clear data
    public void clearFieldData(WebElement element) {
        try {
            element.sendKeys(Keys.CONTROL + "a"); // Select all text
            element.sendKeys(Keys.BACK_SPACE);   // Clear selected text
        } catch (Exception e) {
            Reporter.log("Failed to clear field data: " + e.getMessage(), true);
            throw e;
        }
    }

    @Test(priority = 1, dependsOnMethods = "testLogin", dataProvider = "VehicleTestData")
    public void testVehicleNegativeScenarios(String testCaseID, String vehicleCapacity, String vehicleNumber, String expectedError) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Step 1: Navigate to Vehicle page
        if (testCaseID.equals("CVN_TC_01")) {
            WebElement vehicleButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
            vehicleButton.click();
            WebElement vehiclePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
            Assert.assertTrue(vehiclePage.isDisplayed(), "Vehicle page is not displayed.");
            Reporter.log("PASSED: Vehicle page is successfully displayed.", true);

            WebElement Contractvehicle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Contract Vehicle')]")));
            Contractvehicle.click();
            Assert.assertTrue(Contractvehicle.isDisplayed(), "Contract Vehicle page is not displayed.");
            Reporter.log("PASSED: Contract Vehicle page is successfully displayed.", true);
        }

        // Step 2: Search functionality for test case CVN_TC_01
        if (testCaseID.equals("CVN_TC_01")) {
            WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Search...']")));
            searchElement.sendKeys("test1-AWS66T");
        }

        // Step 3: Locate and click the Edit button
        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Edit']")));
        editButton.click();
        Reporter.log("Edit button clicked successfully for vehicle: " + vehicleNumber, true);

        // Clear and Input Vehicle Capacity dynamically
        WebElement vehicleCapacityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Capacity']")));
        clearFieldData(vehicleCapacityField); // Clear old data
        if (!vehicleCapacity.isEmpty()) {
            vehicleCapacityField.sendKeys(vehicleCapacity); // Input new data
        }

        // Clear and Input Vehicle Number dynamically
        WebElement vehicleNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Number']")));
        clearFieldData(vehicleNumberField); // Clear old data
        if (!vehicleNumber.isEmpty()) {
            vehicleNumberField.sendKeys(vehicleNumber); // Input new data
        }

        // Step 6: Click 'Update' button
        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Update']")));
        updateButton.click();

        // Verify error messages
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

        // Return to Home after test case
        WebElement crossButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@role='button']")));
        crossButton.click();
    }

    // Separate Test Method for Remove Access functionality
    @Test(priority = 2)
    public void testRemoveAccessWithEmptyField() {
        String testCaseID = "SVR_TC_02";

		/*
		 * // Navigate to remove access for the vehicle WebElement vehicleButton =
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.
		 * xpath("//span[contains(text(),'Tab 2 of 5')]"))); vehicleButton.click();
		 * WebElement Contractvehicle =
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.
		 * xpath("//span[contains(text(), 'Contract Vehicle')]")));
		 * Contractvehicle.click(); WebElement VehicleElement =
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
		 * "//span[text()='Vehicles']")));
		 * Assert.assertTrue(VehicleElement.isDisplayed(),
		 * "Vehicle page is not displayed.");
		 * Reporter.log("PASSED: Vehicle page is successfully displayed.", true);
		 * 
		 * // Search and select Remove Access WebElement searchElement =
		 * wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(
		 * "[aria-label='Search...']"))); searchElement.sendKeys("AWS76T");
		 */

        WebElement removeAccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()='Remove Access']")));
        removeAccess.click();

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']")));
        deleteButton.click();

        // Validate success message for remove access
        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label*='Vehicle removed successfully']")));
        // Validate the message
        // Validate the message
        String messageText = toastMessage.getAttribute("aria-label");
        Reporter.log("Toast Message: " + messageText, true);

        // Assert and log results
        
            Assert.assertTrue(messageText.contains("Vehicle removed successfully"),
                    "Toast message validation failed for Test Case: " + testCaseID);
            Reporter.log("Test Case " + testCaseID + " Passed: Vehicle removed successfully", true);
        
    }
    // Separate Test Method for Remove Access for Scheduled Vehicle
    @Test(priority = 3, dependsOnMethods = "testRemoveAccessWithEmptyField")
    public void testRemoveAccessForScheduledVehicle() {
        String testCaseID = "SVR_TC_03";

        // Search and attempt to delete a scheduled vehicle
        WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Search...']")));
        searchElement.sendKeys("Tn-02-bh");

        WebElement removeAccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()='Remove Access']")));
        removeAccess.click();

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']")));
        deleteButton.click();

        // Validate error message for scheduled vehicle
        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label*='Vehicle assigned for Schedule']")));
        String messageText = toastMessage.getAttribute("aria-label");
        Reporter.log("Toast Message: " + messageText, true);

        // Assert and log results
        try {
            Assert.assertTrue(messageText.contains("Vehicle assigned for Schedule"),
                    "Toast message validation failed for Test Case: " + testCaseID);
            Reporter.log("Test Case " + testCaseID + " Passed: Vehicle assigned for Schedule", true);
        } catch (AssertionError e) {
            Reporter.log("Test Case " + testCaseID + " Failed.", true);
            throw e; // Ensures failure is recorded in the TestNG report


   }
    }
}
