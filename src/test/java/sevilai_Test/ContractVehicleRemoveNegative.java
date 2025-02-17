package sevilai_Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class ContractVehicleRemoveNegative extends Login_Test{
	
	
	

   @Test(description = "SVR_TC_01: Verify vehicle removal with empty delete field.", dependsOnMethods = "testLogin")
    public void RemovalWithEmptyField() {
	    String testCaseID = "SVR_TC_01";
        // Step 1: Navigate to Vehicles page
        WebElement vehicleButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
        vehicleButton.click();
        WebElement vehiclePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
        Assert.assertTrue(vehiclePage.isDisplayed(), "Vehicle page is not displayed.");
        Reporter.log("PASSED: Vehicle page is successfully displayed.", true);
        WebElement Contractvehicle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Contract Vehicle')]")));
        Contractvehicle.click();
        Assert.assertTrue(Contractvehicle.isDisplayed(), "Vehicle page is not displayed.");
        Reporter.log("PASSED: Contract Vehicle page is successfully displayed.", true);


		/*
		 * // Step 2: Select rows per page WebElement pageRow20 =
		 * wait.until(ExpectedConditions.elementToBeClickable(By.
		 * xpath("//flt-semantics[contains(text(),'20 rows')]"))); pageRow20.click();
		 * WebElement pageRow50 = wait.until(ExpectedConditions.elementToBeClickable(By.
		 * xpath("//flt-semantics[text()='50 rows per page']"))); pageRow50.click();
		 */
        // Step 3: Search for vehicle
        WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Search...']")));
        searchElement.sendKeys("Tn-03-bh");

        // Step 4: Click on Remove Access and Delete without entering remarks
        WebElement removeAccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()='Remove Access']")));
        removeAccess.click();
        WebElement deleteElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Delete Vehicle']")));
        Assert.assertTrue(deleteElement.isDisplayed(), "Delete header is not displayed.");

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']")));
        deleteButton.click();

        // Step 5: Verify success message
        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label*='Vehicle removed successfully']")));
        // Validate the message
        String messageText = toastMessage.getAttribute("aria-label");
        Reporter.log("Toast Message: " + messageText, true);

        // Assert and log results
        try {
            Assert.assertTrue(messageText.contains("Vehicle removed successfully"),
                    "Toast message validation failed for Test Case: " + testCaseID);
            Reporter.log("Test Case " + testCaseID + " Passed: Vehicle removed successfully", true);
        } catch (AssertionError e) {
            Reporter.log("Test Case " + testCaseID + " Failed.", true);
            throw e; // Ensures failure is recorded in the TestNG report
        }
   }


    // Test Case 2: Verify error message when trying to delete a scheduled vehicle
    @Test(description = "SVR_TC_02: Verify error when deleting a scheduled vehicle.", dependsOnMethods = "RemovalWithEmptyField")
    public void removedScheduledVehicle() {
    	String testCaseID="SVR_TC_02";
        // Step 1: Navigate to Vehicles page (optional if both tests run independently)
        WebElement vehicleButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
        vehicleButton.click();
        WebElement vehiclePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
        Assert.assertTrue(vehiclePage.isDisplayed(), "Vehicle page is not displayed.");
        Reporter.log("PASSED: Vehicle page is successfully displayed.", true);

        // Step 2: Search for another vehicle
        WebElement searchElement = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label*='Search...']")));
        searchElement.click();
        searchElement.sendKeys(Keys.CONTROL + "a");
	    searchElement.sendKeys(Keys.BACK_SPACE);
        searchElement.sendKeys("Tn-02-bh");

        // Step 3: Click on Remove Access and try to delete the scheduled vehicle
        WebElement removeAccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()='Remove Access']")));
        removeAccess.click();
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']")));
        deleteButton.click();

        // Step 4: Verify error message
        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label*='Vehicle assigned for Schedule']")));
        // Validate the message
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
