package sevilaitest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class SuperAdminEditTest extends LoginTest{
	
	@Test(description = "AP_TC_02: Validate Admin is added successfully")
	public void edit() throws Throwable {
		String testCaseID = "AP_TC_02"; // Assign test case ID

        Actions actions = new Actions(driver);

        // Navigate to the Driver tab
         WebElement AdminTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 5 of 5')]")));
         actions.moveToElement(AdminTabButton).click().perform();


		Thread.sleep(2000);
		 String currentURL = driver.getCurrentUrl();
		  Assert.assertTrue(currentURL.contains("admin"), "Current URL does not indicate the Admin page.");
		  Reporter.log("PASSED: Admin page is successfully loaded with URL: " + currentURL, true);

		//WebElement id=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='sara@gmail.com']")));
		  
		    WebElement searchElement = wait.until(ExpectedConditions.presenceOfElementLocated( By.cssSelector("[aria-label='Search...']")));
			searchElement.click();
			searchElement.sendKeys("super@gmail.com");
			/*
			 * WebElement
			 * refresh=wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
			 * ("[aria-label='Refresh']"))); refresh.click();
			 */
			
			 
			WebElement Editbutton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Edit']")));
			Editbutton.click();
		
		WebElement Name=wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Admin Name']")));
		Name.sendKeys(Keys.CONTROL + "a");
		Name.sendKeys(Keys.BACK_SPACE);
		Name.sendKeys("super");
		WebElement email=wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Admin Email']")));
		email.sendKeys(Keys.CONTROL + "a");
		email.sendKeys(Keys.BACK_SPACE);
		email.sendKeys("Super@gmail.com");
		WebElement Phonenumber=wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Admin Phone']")));
		Phonenumber.sendKeys(Keys.CONTROL + "a");
		Phonenumber.sendKeys(Keys.BACK_SPACE);
		
		Phonenumber.sendKeys("12345121");
		WebElement dropdownField =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(), 'Admin Type')]"))); // Use the appropriate locator
	    dropdownField.click(); // Click to open the dropdown
	    Thread.sleep(1000); // Replace with WebDriverWait for better handling
	    WebElement dropdownOption = driver.findElement(By.xpath("//flt-semantics[text()='SUPER_ADMIN']")); // Replace 'OptionText' with the actual text of the option
	    dropdownOption.click(); 
	    WebElement Add=  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()='Update']")));
	    Add.click();
	    // Wait for the toast popup to appear
	    WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label*='Admin updated successfully']")));

	   // Validate the message
	    String messageText = toastMessage.getAttribute("aria-label");
	     Reporter.log("Toast Message: " + messageText, true);

	     // Assert and log results
	      try {
	           Assert.assertTrue(messageText.contains("Admin updated successfully"),"Toast message validation failed for Test Case: " + testCaseID);
	           Reporter.log("Test Case " + testCaseID + " Passed: Admin updated successfully.", true);
	           } catch (AssertionError e) {
	            Reporter.log("Test Case " + testCaseID + " Failed.", true);
	            throw e; // Ensures failure is recorded in the TestNG report


	      }
	}

    @Test
    public void contractvehicleRemoveAccess() throws Throwable {
        String testCaseID = "SARA_TC_01";
        Actions actions = new Actions(driver);
	   //Remove Access Functionality
	      JavascriptExecutor js = (JavascriptExecutor) driver;
	      WebElement mailsearch = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='super@gmail.com']")));

	   // Scroll to make the email element visible
	   //JavascriptExecutor js = (JavascriptExecutor) driver;
	   js.executeScript("arguments[0].scrollIntoView(true);", mailsearch);

	        
	        WebElement RemoveButton= wait.until(ExpectedConditions.elementToBeClickable(mailsearch.findElement(By.xpath("//span[text()='sup2@gmail.com']/following::flt-semantics[@role='button'][2]"))));
	    

			   actions.moveToElement(RemoveButton).click().perform();
			  Reporter.log("Remove button clicked successfully!", true);
			  WebElement remark=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Remarks']")));
			  remark.click();
			  remark.sendKeys("not in use");
			  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']"))).click();
			  //Reporter.log("Vehicle removed successfully", true);
			  
			  WebElement toastMessage1 = wait.until(ExpectedConditions.presenceOfElementLocated(
			            By.cssSelector("[aria-label*='Admin removed successfully']")
			        ));

			        // Validate the message
			        String messageText1 = toastMessage1.getAttribute("aria-label");
			        Reporter.log("Toast Message: " + messageText1, true);

			  try {
	           Assert.assertTrue(messageText1.contains("Admin removed successfully"),
	                   "Toast message validation failed for Test Case: " + testCaseID);
	           Reporter.log("Test Case " + testCaseID + " Passed: Admin removed successfully.", true);
	       } catch (AssertionError e) {
	           Reporter.log("Test Case " + testCaseID + " Failed.", true);
	           throw e; // Ensures failure is recorded in the TestNG report


	  }


		
		
		
		
	}

}
