package sevilaitest;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SevilaiVehicleEditTest extends LoginTest {
   
    @BeforeClass
    public void checkLogin() throws Throwable {
       if (driver == null) { // If driver is not initialized, set it up
            setup();
        }
   }

    @Test(dependsOnMethods = "sevilaiadd")
    public void sevilaivehicleedit() throws Throwable {
       String testCaseID = "SVE_TC_01";
      Actions actions = new Actions(driver);

       // Navigate to the Vehicle tab
      WebElement vehicleTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
               By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
       actions.moveToElement(vehicleTabButton).click().perform();

        WebElement vehicleElement = wait.until(
               ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
       Assert.assertTrue(vehicleElement.isDisplayed(), "Vehicle page is not displayed.");
        Reporter.log("PASSED: Vehicle page is successfully displayed.", true);

        // Search for the vehicle
        WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[aria-label='Search...']")));
        searchElement.click();
       searchElement.sendKeys("vehi1");
       searchElement.sendKeys(Keys.ENTER); // Ensure search execution

        // Click the "Edit" button
       WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//flt-semantics[contains(@aria-label, 'Edit')]")));
       editButton.click();
        Reporter.log("Edit button clicked successfully!", true);

        // Update vehicle capacity
        WebElement updateVehicle = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@aria-label='Vehicle Capacity']")));
        updateVehicle.click();
        updateVehicle.sendKeys(Keys.CONTROL + "a");
        updateVehicle.sendKeys(Keys.BACK_SPACE);
        updateVehicle.sendKeys("20");

       // Update vehicle number
       WebElement vehicleNumber = wait.until(ExpectedConditions.elementToBeClickable(
               By.xpath("//input[@aria-label='Vehicle Number']")));
        vehicleNumber.click();
       vehicleNumber.sendKeys(Keys.CONTROL + "a");
        vehicleNumber.sendKeys(Keys.BACK_SPACE);
        vehicleNumber.sendKeys("vehi5");

        // Click the "Update" button
        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//flt-semantics[text()='Update']")));
        updateButton.click();

        // Validate success toast message
        Thread.sleep(2000); // Allow time for toast message
        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[aria-label*='Vehicle updated successfully']")));
       String messageText = toastMessage.getAttribute("aria-label");
       Reporter.log("Toast Message: " + messageText, true);

        try {
            Assert.assertTrue(messageText.contains("Vehicle updated successfully"),
                    "Toast message validation failed for Test Case: " + testCaseID);
            Reporter.log("Test Case " + testCaseID + " Passed: Vehicle updated successfully.", true);
        } catch (AssertionError e) {
            Reporter.log("Test Case " + testCaseID + " Failed.", true);
            throw e;
        }
   }

    @Test(dependsOnMethods = "sevilaivehicleedit")
    public void sevilaivehicleRemoveAccess() throws Throwable {
        String testCaseID = "SVRA_TC_01";
        Actions actions = new Actions(driver);
        
        WebElement vehicleTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
        actions.moveToElement(vehicleTabButton).click().perform();


		
	  WebElement searchElement =
		  wait.until(ExpectedConditions.presenceOfElementLocated(
		  By.xpath("//input[@type='text']")));
		  
		  searchElement.sendKeys(Keys.CONTROL + "a");
		  searchElement.sendKeys(Keys.BACK_SPACE); 
		  searchElement.sendKeys("vehi2");
		  searchElement.sendKeys(Keys.ENTER); // Ensure search execution
		 
       // Handle case where vehicle does not exist
       WebElement vehicle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='vehi2']")));

        // Click the "Remove Access" button
       WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(vehicle.findElement(By.xpath("//span[text()='vehi2']/following::flt-semantics[text()='Remove Access'][1]"))));
        actions.moveToElement(removeButton).click().perform();
        Reporter.log("Remove button clicked successfully!", true);

       // Add remarks and delete
       WebElement remark = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Remarks']")));
        remark.click();
        remark.sendKeys("not in use");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']"))).click();
        Reporter.log("Vehicle removed successfully", true);

        // Validate success toast message
        WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label*='Vehicle removed successfully']")));
        String messageText = toastMessage.getAttribute("aria-label");
        Reporter.log("Toast Message: " + messageText, true);

       try {
          Assert.assertTrue(messageText.contains("Vehicle removed successfully"), "Toast message validation failed for Test Case: " + testCaseID);
           Reporter.log("Test Case " + testCaseID + " Passed: Vehicle removed successfully.", true);
        } catch (AssertionError e) {
            Reporter.log("Test Case " + testCaseID + " Failed: Vehicle added successfully", true);
            throw e;
        }
    }
}

