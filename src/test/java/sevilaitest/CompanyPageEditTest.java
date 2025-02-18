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

public class CompanyPageEditTest extends LoginTest {
	
	 @Test(description = "CP_TC_02: Validate Company Edit is added successfully", dependsOnMethods = "testLogin")
	    public void companyadd() throws Exception {
	    	String testCaseID = "CP_TC_02"; // Assign test case ID
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));

	        // Navigate to Company Tab
	        WebElement companyTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 4 of 5')]")));
	        companyTab.click();

	        String currentURL = driver.getCurrentUrl();
	        Assert.assertTrue(currentURL.contains("company"), "Current URL does not indicate the Company page.");
	        Reporter.log("PASSED: Company page is successfully loaded with URL: " + currentURL, true);
	        // Step 2: Locate the specific company by Route ID
	        String routeId = "ABC000"; // Replace with the specific Route ID
	        WebElement companyRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.xpath("//span[text()='ABC000']")));
	       // Step 3: Scroll horizontally to make the "Edit" button visible
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'center'});", companyRow);
	        

	        // Step 3: Hover over the company row to make the "Edit" button visible
	        Actions actions = new Actions(driver);
	        actions.moveToElement(companyRow).perform();
	        
	       // Step 5: Locate the "Edit" button within the same row
	        WebElement editButton = companyRow.findElement(By.xpath("//span[text()='ABC000']/following::flt-semantics[@aria-label='Edit'][1]"));
	        js.executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'center'});", editButton);

	       // Step 6: Click the "Edit" button
	        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();

	        Reporter.log("PASSED: Edit button clicked for the company with Route ID: " + routeId, true);


	      

	        // Fill out the form
	        WebElement name = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Company Name']")));
	        name.click();
	        name.sendKeys(Keys.CONTROL + "a");
			name.sendKeys(Keys.BACK_SPACE);
		    name.sendKeys("ABCD");
		    WebElement update = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Update']")));
		    update.click();

			/*
			 * WebElement number = wait.until(ExpectedConditions.elementToBeClickable(By.
			 * xpath("//input[@aria-label='Phone Number']"))); number.click();
			 * number.sendKeys("34343434");
			 * 
			 * WebElement id = wait.until(ExpectedConditions.elementToBeClickable(By.
			 * xpath("//input[@aria-label='Route ID']"))); id.click();
			 * id.sendKeys("ABC110");
			 * 
			 * // Wait for the date input field to be visible
			 * 
			 * WebElement dateInput = wait.until(ExpectedConditions.elementToBeClickable(By.
			 * xpath("//input[@aria-label='Start Date']")));
			 * 
			 * // Click the date input field to open the calendar dateInput.click();
			 * 
			 * 
			 * 
			 * // Locate and click on the 27th December 2024 WebElement dateCell =
			 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
			 * "//flt-semantics[contains(text(),'27')]"))); dateCell.click();
			 * 
			 * WebElement okbutton =
			 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
			 * "//flt-semantics[text()='OK']"))); okbutton.click();
			 * 
			 * // Wait for the date input field to be visible
			 * 
			 * WebElement dateEnd = wait.until(ExpectedConditions.elementToBeClickable(By.
			 * xpath("//input[@aria-label='End Date']")));
			 * 
			 * // Click the date input field to open the calendar dateEnd.click();
			 * 
			 * WebElement yeardrop = wait.until(ExpectedConditions.elementToBeClickable(By.
			 * xpath("//flt-semantics[text()='Select year']")));
			 * 
			 * // Click the date input field to open the calendar yeardrop.click();
			 * 
			 * WebElement year =
			 * wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
			 * "//flt-semantics[text()='2025']"))); year.click();
			 * 
			 * //WebElement previous =
			 * wait.until(ExpectedConditions.elementToBeClickable(By.
			 * xpath("//flt-semantics[text()='Previous month']"))); // previous.click();
			 * WebElement date = wait.until(ExpectedConditions.elementToBeClickable(By.
			 * xpath("//flt-semantics[contains(text(),'December 5')]"))); date.click();
			 * okbutton.click();
			 * 
			 * WebElement Add= wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
			 * "//flt-semantics[text()='Add']")));
			 * 
			 * Add.click();
			 */
	        
	        // Wait for the toast popup to appear
	        WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
	            By.cssSelector("[aria-label*='Company Edit successfully']")
	        ));

	        // Validate the message
	        String messageText = toastMessage.getAttribute("aria-label");
	        Reporter.log("Toast Message: " + messageText, true);

	        // Assert and log results
	        try {
	            Assert.assertTrue(messageText.contains("Company updated successfully"),
	                    "Toast message validation failed for Test Case: " + testCaseID);
	            Reporter.log("Test Case " + testCaseID + " Passed: Company updated successfully.", true);
	        } catch (AssertionError e) {
	            Reporter.log("Test Case " + testCaseID + " Failed.", true);
	            throw e; // Ensures failure is recorded in the TestNG report
	            }
	      //Remove Access Functionality.
	         WebElement Company = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[text()='ABC000']")));
	         
	         WebElement RemoveButton= wait.until(ExpectedConditions.elementToBeClickable(Company.findElement(By.xpath("//span[text()='ABC000']/following::flt-semantics[text()='Remove Access'][1]"))));
	      // Use the Actions class to move to the Edit button and click it
	         

	 		   actions.moveToElement(RemoveButton).click().perform();
	 		  Reporter.log("Remove button clicked successfully!", true);
	 		  WebElement remark=wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Remarks']")));
	 		  remark.click();
	 		  remark.sendKeys("not in use");
	 		  wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Delete']"))).click();
	 		  //Reporter.log("Vehicle removed successfully", true);
	 		  
	 		  WebElement toastMessage1 = wait.until(ExpectedConditions.presenceOfElementLocated(
	 		            By.cssSelector("[aria-label*='Company removed successfully']")
	 		        ));

	 		        // Validate the message
	 		        String messageText1 = toastMessage1.getAttribute("aria-label");
	 		        Reporter.log("Toast Message: " + messageText1, true);

	 		  try {
	            Assert.assertTrue(messageText1.contains("Company removed successfully"),
	                    "Toast message validation failed for Test Case: " + testCaseID);
	            Reporter.log("Test Case " + testCaseID + " Passed: Company removed successfully.", true);
	        } catch (AssertionError e) {
	            Reporter.log("Test Case " + testCaseID + " Failed.", true);
	            throw e; // Ensures failure is recorded in the TestNG report


	   }
	    }
	}


