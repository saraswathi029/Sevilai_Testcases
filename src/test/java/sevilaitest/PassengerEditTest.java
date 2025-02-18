package sevilaitest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class PassengerEditTest extends LoginTest {
	
	 @Test
	    public void passengeredit() {
	        String testCaseID = "CM_TC_01"; // Assign test case ID
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	       // Actions actions = new Actions(driver);

	        // Navigate to Company Tab
	        WebElement companyTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 4 of 5')]")));
	        companyTab.click();

	        String currentURL = driver.getCurrentUrl();
	        Assert.assertTrue(currentURL.contains("company"), "Current URL does not indicate the Company page.");
	        Reporter.log("PASSED: Company page is successfully loaded with URL: " + currentURL, true);
	        //String routeId = "ABC000"; // Replace with the specific Route ID
	        WebElement companyRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//span[text()='ABC000']/preceding::flt-semantics[text()='ABCD']")));
	      
	        Actions actions = new Actions(driver);
	        actions.moveToElement(companyRow).perform();
	        companyRow.click();
	        WebElement passengerTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Tab 2 of 3')]")));
	        passengerTab.click();
	        WebElement uqnos= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='23232323']")));
	       
	        actions.moveToElement(uqnos).perform();
	        
	       // Step 5: Locate the "Edit" button within the same row
	        WebElement editButton = uqnos.findElement(By.xpath("//span[text()='23232323']/following::flt-semantics[@aria-label='Edit'][1]"));
	        js.executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'center'});", editButton);

	       // Step 6: Click the "Edit" button
	        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();

	        Reporter.log("PASSED: Edit button clicked for the company with Mobile number", true);

	        // Fill Passenger Details
	        WebElement name = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Passenger Name']")));
	        name.sendKeys(Keys.CONTROL + "a");
			name.sendKeys(Keys.BACK_SPACE);
	        name.sendKeys("SARA");
	        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Passenger Email']")));
	        email.sendKeys(Keys.CONTROL + "a");
			email.sendKeys(Keys.BACK_SPACE);
	        email.sendKeys("SARA@yahoo.com");
	        WebElement number = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Passenger Phone']")));
	        number.sendKeys(Keys.CONTROL + "a");
			number.sendKeys(Keys.BACK_SPACE);
	        number.sendKeys("23232323");
	        WebElement location = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Passenger Location']")));
	        location.sendKeys(Keys.CONTROL + "a");
			location.sendKeys(Keys.BACK_SPACE);
	        location.sendKeys("Kadayanallur");

	        // Handle Pickup Dropdown
	        WebElement pickup = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(),'Pickup')]")));
	        pickup.click();
	        WebElement pickupDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='ABC001']")));
	        pickupDropdown.click();

	        // Handle Drop Dropdown
	        WebElement drop = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(),'Drop')]")));
	        drop.click();
	        WebElement dropDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='ABC001']")));
	        dropDropdown.click();

	        // Click Add Button to Save
	        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Update']")));
	        saveButton.click();
	        
	        WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
		            By.cssSelector("[aria-label*='Passenger updated successfully']")
		        ));

		        // Validate the message
		        String messageText = toastMessage.getAttribute("aria-label");
		        Reporter.log("Toast Message: " + messageText, true);

		        // Assert and log results
		        try {
		            Assert.assertTrue(messageText.contains("Passenger updated successfully"),
		                    "Toast message validation failed for Test Case: " + testCaseID);
		            Reporter.log("Test Case " + testCaseID + " Passed: Passenger updated successfully.", true);
		        } catch (AssertionError e) {
		            Reporter.log("Test Case " + testCaseID + " Failed.", true);
		            throw e; // Ensures failure is recorded in the TestNG report
		            }
		      //Remove Access Functionality.
		         WebElement Company = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='23232323']")));
		         
		         WebElement RemoveButton= wait.until(ExpectedConditions.elementToBeClickable(Company.findElement(By.xpath("//span[text()='23232323']/following::flt-semantics[@role='button'][2]"))));
		      // Use the Actions class to move to the Edit button and click it
		         

		 		   actions.moveToElement(RemoveButton).click().perform();
		 		  Reporter.log("Remove button clicked successfully!", true);
		 		  WebElement remark=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Remarks']")));
		 		  remark.click();
		 		  remark.sendKeys("not in use");
		 		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']"))).click();
		 		  //Reporter.log("Vehicle removed successfully", true);
		 		  
		 		  WebElement toastMessage1 = wait.until(ExpectedConditions.presenceOfElementLocated(
		 		            By.cssSelector("[aria-label*='Passenger removed successfully']")
		 		        ));

		 		        // Validate the message
		 		        String messageText1 = toastMessage1.getAttribute("aria-label");
		 		        Reporter.log("Toast Message: " + messageText1, true);

		 		  try {
		            Assert.assertTrue(messageText1.contains("Passenger removed successfully"),
		                    "Toast message validation failed for Test Case: " + testCaseID);
		            Reporter.log("Test Case " + testCaseID + " Passed: Passenger removed successfully.", true);
		        } catch (AssertionError e) {
		            Reporter.log("Test Case " + testCaseID + " Failed.", true);
		            throw e; // Ensures failure is recorded in the TestNG report


		   }




}
}
