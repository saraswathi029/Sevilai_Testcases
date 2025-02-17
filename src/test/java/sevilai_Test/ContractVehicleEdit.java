package sevilai_Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ContractVehicleEdit extends Login_Test {
	
	@BeforeClass
    public void checkLogin() throws Throwable {
        if (driver == null) { // If driver is not initialized, set it up
            setup();
        }
    }
	 String testCaseID = "CVE_TC_01";
	

    @Test(dependsOnMethods = "contractdriveradd")
    public void editcontractvehicle() throws Throwable {
    	
    	 Actions actions = new Actions(driver);

         // Navigate to the Driver tab
         WebElement VehicleTabButton = wait
                 .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
         actions.moveToElement(VehicleTabButton).click().perform();

		WebElement Contractvehicle = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Contract Vehicle')]")));
		Contractvehicle.click();
		
		WebElement VehicleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
    	
        Assert.assertTrue(VehicleElement.isDisplayed(), "Vehicle page is not displayed.");
        Reporter.log("PASSED: Vehicle page is successfully displayed.", true);
        WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector("[aria-label='Search...']")));
	      searchElement.sendKeys("Dumm2");
	      WebElement Editbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Edit']")));
	      Editbutton.click();
	  Reporter.log("Edit button clicked successfully!", true);
	  WebElement updatevehicle=wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//input[@aria-label='Vehicle Capacity']"))));
	  updatevehicle.click();
	// Select all text and delete
	  updatevehicle.sendKeys(Keys.CONTROL + "a");
	  updatevehicle.sendKeys(Keys.BACK_SPACE);
	  updatevehicle.sendKeys("20");
	  WebElement updateButton= wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//flt-semantics[text()='Update']"))));
    updateButton.click();
    WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("[aria-label*='Vehicle updated successfully']")
        ));

        // Validate the message
        String messageText = toastMessage.getAttribute("aria-label");
        Reporter.log("Toast Message: " + messageText, true);

        // Assert and log results
        try {
            Assert.assertTrue(messageText.contains("Vehicle updated successfully"),
                    "Toast message validation failed for Test Case: " + testCaseID);
            Reporter.log("Test Case " + testCaseID + " Passed: Vehicle updated successfully.", true);
        } catch (AssertionError e) {
            Reporter.log("Test Case " + testCaseID + " Failed.", true);
            throw e; // Ensures failure is recorded in the TestNG report


   }
    }
        @Test(dependsOnMethods = "contractdriveradd")
        public void contractvehicleRemoveAccess() throws Throwable {
            String testCaseID = "CVRA_TC_01";
            Actions actions = new Actions(driver);

    //Remove Access Functionality
     WebElement vehicle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='DumTe2']")));
     
     WebElement RemoveButton= wait.until(ExpectedConditions.elementToBeClickable(vehicle.findElement(By.xpath("//span[text()='DumTe2']/following::flt-semantics[text()='Remove Access'][1]"))));
  // Use the Actions class to move to the Edit button and click it
    

	   actions.moveToElement(RemoveButton).click().perform();
	  Reporter.log("Remove button clicked successfully!", true);
	  WebElement remark=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Remarks']")));
	  remark.click();
	  remark.sendKeys("not in use");
	  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']"))).click();
	  Reporter.log("Vehicle removed successfully", true);
	  
	  WebElement toastMessage1 = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.cssSelector("[aria-label*='Vehicle removed successfully']")
	        ));

	        // Validate the message
	        String messageText1 = toastMessage1.getAttribute("aria-label");
	        Reporter.log("Toast Message: " + messageText1, true);

	  try {
        Assert.assertTrue(messageText1.contains(""),
                "Toast message validation failed for Test Case: " + testCaseID);
        Reporter.log("Test Case " + testCaseID + " Passed: Vehicle removed successfully.", true);
    } catch (AssertionError e) {
        Reporter.log("Test Case " + testCaseID + " Failed.", true);
        throw e; // Ensures failure is recorded in the TestNG report


}
	  
	  

}
}



	    


