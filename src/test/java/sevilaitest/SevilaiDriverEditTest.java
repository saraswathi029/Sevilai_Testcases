package sevilaitest;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SevilaiDriverEditTest extends LoginTest{
	
	@BeforeClass
    public void checkLogin() throws Throwable {
        if (driver == null) { // If driver is not initialized, set it up
            setup();
        }
    }
	
	@Test(description = "SDA_TC_02: Validate driver is added successfully", dependsOnMethods = "sevilaidriveradd")
	public void sevilaidriveredit() throws InterruptedException {
		String testCaseID = "SDA_TC_02"; 
		 Actions actions = new Actions(driver);

	        // Navigate to the Driver tab
	        WebElement DriverTabButton = wait
	                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 3 of 5')]")));
	        actions.moveToElement(DriverTabButton).click().perform();

	        WebElement DriverElement = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Drivers']")));

	        Assert.assertTrue(DriverElement.isDisplayed(), "Driver page is not displayed.");
	        Reporter.log("PASSED: Driver page is successfully displayed.", true);

		
		
		

		
      WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector("[aria-label='Search...']")));
	  searchElement.sendKeys("vehi5");
	  WebElement Editbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Edit']")));
	  Editbutton.click();
	  WebElement Drivername=driver.findElement(By.cssSelector("[aria-label='Driver Name']"));
	  Thread.sleep(1000);
		Drivername.click();
		Drivername.sendKeys(Keys.CONTROL + "a");
		Drivername.sendKeys(Keys.BACK_SPACE);
		Drivername.sendKeys("Drive");
		WebElement Driveremail=driver.findElement(By.cssSelector("[aria-label='Email Address']"));
		Driveremail.click();
		Driveremail.sendKeys(Keys.CONTROL + "a");
		Driveremail.sendKeys(Keys.BACK_SPACE);
		Driveremail.sendKeys("Drive@gmail.com");
		
        // Select an option from the dropdown (adjust the locator for the options)
        WebElement vehiclecapacity =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Driver Phone Number']/following::flt-semantics[@role='button'][1]"))); // Replace 'OptionText' with the actual text of the option
        vehiclecapacity.click(); // Click the desired option
        WebElement vcdropdown =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='40']"))); // Replace 'OptionText' with the actual text of the option
        vcdropdown.click(); // Click the desired option
        
        
        Thread.sleep(1000);
        WebElement vehiclenumberdropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@role='button'][2]"))); // Use the appropriate locator
        
        vehiclenumberdropdown.click();
        Thread.sleep(1000); // Replace with WebDriver Wait for better handling

        // Select an option from the dropdown (adjust the locator for the options)
        WebElement vehiclenumberOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='vehi4']"))); // Replace 'OptionText' with the actual text of the option
        vehiclenumberOption.click();
        WebElement update = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Update']")));
        update.click();
     // Wait for the toast popup to appear
        WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector("[aria-label*='Driver updated successfully']")
        ));

        // Validate the message
        String messageText = toastMessage.getAttribute("aria-label");
        Reporter.log("Toast Message: " + messageText, true);

        // Assert and log results
        try {
            Assert.assertTrue(messageText.contains("Driver updated successfully"),
                    "Toast message validation failed for Test Case: " + testCaseID);
            Reporter.log("Test Case " + testCaseID + " Passed: Driver updated successfully.", true);
            
         // Search for the driver again to refresh the details
            searchElement.sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE); // Clear search
            searchElement.sendKeys("vehi5"); // Search the same driver again
            Thread.sleep(2000); // Wait for data to reload (Use explicit wait if possible)

            // Locate the email field again and fetch its value
            WebElement updatedEmailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Email Address']")));
            String updatedEmailValue = updatedEmailField.getAttribute("value"); // Get email field value

            // Assert if email is removed (empty)
            Assert.assertTrue(updatedEmailValue.isEmpty(), "Email address is not removed!");
            Reporter.log("Validation Passed: Email address is successfully removed from the driver details.", true);
        } catch (AssertionError e) {
            Reporter.log("Test Case " + testCaseID + " Failed.", true);
            throw e; // Ensures failure is recorded in the TestNG report
            
        
      	  


   }
	}
        @Test(dependsOnMethods = "sevilaidriveradd")
        public void sevilaivehicleRemoveAccess() throws Throwable {
            String testCaseID = "SDRA_TC_03";
            Actions actions = new Actions(driver);

            // Locate the vehicle
            WebElement vehicle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='vehi3']")));

       
        WebElement RemoveButton= wait.until(ExpectedConditions.elementToBeClickable(vehicle.findElement(By.xpath("//span[text()='Vehi3']/following::flt-semantics[text()='Remove Access'][1]"))));
     // Use the Actions class to move to the Edit button and click it
        

		   actions.moveToElement(RemoveButton).click().perform();
		  Reporter.log("Remove button clicked successfully!", true);
		  WebElement remark=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Remarks']")));
		  remark.click();
		  remark.sendKeys("not in use");
		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']"))).click();
		  //Reporter.log("Driver removed successfully", true);
		  
		  WebElement toastMessage1 = wait.until(ExpectedConditions.presenceOfElementLocated(
		            By.cssSelector("[aria-label*='Driver removed successfully']")
		        ));

		        // Validate the message
		        String messageText1 = toastMessage1.getAttribute("aria-label");
		        Reporter.log("Toast Message: " + messageText1, true);

		  try {
           Assert.assertTrue(messageText1.contains("Driver removed successfully"),
                   "Toast message validation failed for Test Case: " + testCaseID);
           Reporter.log("Test Case " + testCaseID + " Passed: Driver removed successfully.", true);
       } catch (AssertionError e) {
           Reporter.log("Test Case " + testCaseID + " Failed.", true);
           throw e; // Ensures failure is recorded in the TestNG report


  }



}
}
