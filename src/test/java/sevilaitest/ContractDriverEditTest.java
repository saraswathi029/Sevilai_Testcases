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

public class ContractDriverEditTest extends LoginTest {
	
	@BeforeClass
    public void checkLogin() throws Throwable {
        if (driver == null) { // If driver is not initialized, set it up
            setup();
        }
    }
	
	@Test(description = "CDA_TC_02: Validate driver is added successfully", dependsOnMethods = "contractdriveradd")
	public void contractdriveredit() throws InterruptedException {
		String testCaseID = "CDA_TC_02"; 
		 Actions actions = new Actions(driver);

	        // Navigate to the Driver tab
	        WebElement DriverTabButton = wait
	                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 3 of 5')]")));
	        actions.moveToElement(DriverTabButton).click().perform();

			/*
			 * WebElement DriverElement = wait.until(
			 * ExpectedConditions.visibilityOfElementLocated(By.xpath(
			 * "//span[text()='Drivers']")));
			 * 
			 * Assert.assertTrue(DriverElement.isDisplayed(),
			 * "Driver page is not displayed.");
			 * Reporter.log("PASSED: Driver page is successfully displayed.", true);
			 */
	
		
		WebElement ContractDriver = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Contract Drivers')]")));
		 actions.moveToElement(ContractDriver).click().perform();
		
		
		WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector("[aria-label='Search...']")));
		searchElement.click();
		searchElement.sendKeys("Dumm2");
		WebElement Editbutton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Edit']")));
		Editbutton.click();
		WebElement Drivername=driver.findElement(By.cssSelector("[aria-label='Driver Name']"));
	
		Drivername.click();
		Thread.sleep(1000);
		Drivername.sendKeys(Keys.CONTROL + "a");
		Drivername.sendKeys(Keys.BACK_SPACE);
		Drivername.sendKeys("Dumm");
		WebElement Driveremail=driver.findElement(By.cssSelector("[aria-label='Email Address']"));
		Driveremail.click();
		Driveremail.sendKeys(Keys.CONTROL + "a");
		Driveremail.sendKeys(Keys.BACK_SPACE);
		//Driveremail.sendKeys("DummTe@gmail.com");
		WebElement Driverphonenumber=driver.findElement(By.cssSelector("[aria-label='Driver Phone Number']"));
		Driverphonenumber.click();
		Driverphonenumber.sendKeys(Keys.CONTROL + "a");
		Driverphonenumber.sendKeys(Keys.BACK_SPACE);
		Driverphonenumber.sendKeys("12121200");
		 // Locate the dropdown field (adjust the locator based on your application)
        
        
        
        WebElement vehiclecapacity = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Vehicle Capacity']")));
        

        
        vehiclecapacity.click();
        vehiclecapacity.sendKeys(Keys.CONTROL + "a");
		vehiclecapacity.sendKeys(Keys.BACK_SPACE);
        vehiclecapacity.sendKeys("10");
       
        WebElement vehiclenumber = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Number ']")));
        
        vehiclenumber.click();
        vehiclenumber.sendKeys(Keys.CONTROL + "a");
        vehiclenumber.sendKeys(Keys.BACK_SPACE);
       vehiclenumber.sendKeys("Dumm2");
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
     } catch (AssertionError e) {
         Reporter.log("Test Case " + testCaseID + " Failed.", true);
         throw e; // Ensures failure is recorded in the TestNG report
     }
 }
	
     
     @Test(dependsOnMethods = "contractdriveredit")
     public void contractDriverRemoveAccess() throws Throwable {
         String testCaseID = "CDRA_TC_03";
         Actions actions = new Actions(driver);
         
         WebElement DriverTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                 By.xpath("//span[contains(text(),'Tab 3 of 5')]")));
         actions.moveToElement(DriverTabButton).click().perform();
         
         WebElement ContractDriver = wait
 				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'Contract Drivers')]")));
 		 actions.moveToElement(ContractDriver).click().perform();
 		

         
         WebElement searchElement =
       		  wait.until(ExpectedConditions.presenceOfElementLocated(
       		  By.xpath("//input[@type='text']")));
       		  
       		  searchElement.sendKeys(Keys.CONTROL + "a");
       		  searchElement.sendKeys(Keys.BACK_SPACE); 
       		  searchElement.sendKeys("DumTe2");
       		  searchElement.sendKeys(Keys.ENTER);


         // Locate the vehicle
         WebElement vehicle = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='DumTe2']")));

       
         WebElement RemoveButton= wait.until(ExpectedConditions.elementToBeClickable(vehicle.findElement(By.xpath("//span[text()='DumTe2']/following::flt-semantics[text()='Remove Access'][1]"))));
      // Use the Actions class to move to the Edit button and click it
         

 		   actions.moveToElement(RemoveButton).click().perform();
 		  Reporter.log("Remove button clicked successfully!", true);
 		  WebElement remark=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Remarks']")));
 		  remark.click();
 		  remark.sendKeys("not in use");
 		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']"))).click();
 		 
 		  
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
            Reporter.log("Test Case " + testCaseID + " Failed: Driver added successfully", true);
            throw e; // Ensures failure is recorded in the TestNG report


   }


	}
}


   



	




