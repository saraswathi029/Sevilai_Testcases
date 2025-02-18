package sevilaitest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class SevilaiVehicleRemoveAccessNegativeTest extends LoginTest {
	

   
    @Test(description = "SVR_TC_01: Verify vehicle removal with empty delete field.", dependsOnMethods = "testLogin")
    public void RemovalWithEmptyField() {
        // Step 1: Navigate to Vehicles page
        WebElement vehicleButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
        vehicleButton.click();
        WebElement vehiclePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
        Assert.assertTrue(vehiclePage.isDisplayed(), "Vehicle page is not displayed.");
        Reporter.log("PASSED: Vehicle page is successfully displayed.", true);

        // Step 2: Select rows per page
        WebElement pageRow20 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(),'20 rows')]")));
        pageRow20.click();
        WebElement pageRow50 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='50 rows per page']")));
        pageRow50.click();

        // Step 3: Search for vehicle
        WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Search...']")));
        searchElement.sendKeys("ERG567");

        // Step 4: Click on Remove Access and Delete without entering remarks
        WebElement removeAccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()='Remove Access']")));
        removeAccess.click();
        WebElement deleteElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Delete Vehicle']")));
        Assert.assertTrue(deleteElement.isDisplayed(), "Delete header is not displayed.");

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']")));
        deleteButton.click();

        // Step 5: Verify success message
        WebElement successPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label*='Vehicle removed successfully']")));
        String successMessage = successPopup.getText();
        Assert.assertTrue(successMessage.contains("Vehicle removed successfully"), "Unexpected success message.");
        Reporter.log("PASSED: Vehicle was removed successfully even when the delete field was empty.", true);
    }

    // Test Case 2: Verify error message when trying to delete a scheduled vehicle
    @Test(description = "SVR_TC_02: Verify error when deleting a scheduled vehicle.")
    public void removedScheduledVehicle() {
        // Step 1: Navigate to Vehicles page (optional if both tests run independently)
        WebElement vehicleButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
        vehicleButton.click();
        WebElement vehiclePage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
        Assert.assertTrue(vehiclePage.isDisplayed(), "Vehicle page is not displayed.");
        Reporter.log("PASSED: Vehicle page is successfully displayed.", true);

        // Step 2: Search for another vehicle
        WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Search...']")));
        searchElement.sendKeys("ERG567");

        // Step 3: Click on Remove Access and try to delete the scheduled vehicle
        WebElement removeAccess = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()='Remove Access']")));
        removeAccess.click();
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']")));
        deleteButton.click();

        // Step 4: Verify error message
        WebElement errorPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label*='Vehicle assigned for Schedule']")));
        String errorMessage = errorPopup.getText();
        Assert.assertTrue(errorMessage.contains("Vehicle assigned for Schedule"), "Unexpected error message.");
        Reporter.log("PASSED: Error message displayed as expected for scheduled vehicle.", true);
    }
}
