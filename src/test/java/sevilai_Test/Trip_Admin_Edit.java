package sevilai_Test;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class Trip_Admin_Edit extends Login_Test {
	


	@Test(description = "TAP_TC_03: Validate Admin is added successfully", dependsOnMethods = "superadmin")
	public void edit() throws Throwable {
		String testCaseID = "TAP_TC_03"; // Assign test case ID
		
		WebElement AdminTab = wait.until(
				ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 5 of 5')]")));
		AdminTab.click();
		Thread.sleep(2000);
		String currentURL = driver.getCurrentUrl();
		Assert.assertTrue(currentURL.contains("admin"), "Current URL does not indicate the Admin page.");
		Reporter.log("PASSED: Admin page is successfully loaded with URL: " + currentURL, true);

		// WebElement
		// id=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='sara@gmail.com']")));
		WebElement TripAdmin = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Tab 2 of 2')]")));
		TripAdmin.click();
		
		  WebElement
		  refresh=wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
		  ("[aria-label='Refresh']"))); refresh.click();
		 
		WebElement searchElement = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Search...']")));
		searchElement.click();
		searchElement.sendKeys("test@");
		WebElement Editbutton = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Edit']")));
		Editbutton.click();

		WebElement Name = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Admin Name']")));
		Name.sendKeys(Keys.CONTROL + "a");
		Name.sendKeys(Keys.BACK_SPACE);
		Name.sendKeys("test");
		WebElement email = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Admin Email']")));
		email.sendKeys(Keys.CONTROL + "a");
		email.sendKeys(Keys.BACK_SPACE);
		email.sendKeys("test2@yahoo.com");
		WebElement Phonenumber = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Admin Phone']")));
		Phonenumber.sendKeys(Keys.CONTROL + "a");
		Phonenumber.sendKeys(Keys.BACK_SPACE);

		Phonenumber.sendKeys("90909011");
		WebElement dropdownField = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(), 'Admin Type')]"))); 
		dropdownField.click(); // Click to open the dropdown
		Thread.sleep(1000); // Replace with WebDriverWait for better handling
		WebElement dropdownOption = driver.findElement(By.xpath("//flt-semantics[text()='TRIP_ADMIN']")); 
		dropdownOption.click();
		WebElement Add = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()='Update']")));
		Add.click();
		// Wait for the toast popup to appear
		WebElement toastMessage = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.cssSelector("[aria-label*='Admin updated successfully']")));

		// Validate the message
		String messageText = toastMessage.getAttribute("aria-label");
		Reporter.log("Toast Message: " + messageText, true);

		// Assert and log results
		try {
			Assert.assertTrue(messageText.contains("Admin updated successfully"),
					"Toast message validation failed for Test Case: " + testCaseID);
			Reporter.log("Test Case " + testCaseID + " Passed: Admin updated successfully.", true);
		} catch (AssertionError e) {
			Reporter.log("Test Case " + testCaseID + " Failed.", true);
			throw e; // Ensures failure is recorded in the TestNG report

		}
	}
	 @Test(dependsOnMethods = "superadmin")
     public void TripadminRemoveAccess() throws Throwable {
         String testCaseID = "CDRA_TC_03";
         Actions actions = new Actions(driver);

		
	
		WebElement mailsearch = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='sara123@gmail.com']")));
        
        WebElement RemoveButton= wait.until(ExpectedConditions.elementToBeClickable(mailsearch.findElement(By.xpath("//span[text()='sara123@gmail.com']/following::flt-semantics[@role='button'][2]"))));
     

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

