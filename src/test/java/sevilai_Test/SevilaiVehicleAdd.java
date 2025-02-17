package sevilai_Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class SevilaiVehicleAdd extends Login_Test {
		

	@Test
	    public void sevilaivehicleadd() throws Throwable {
	    	String testCaseID = "SVA_TC_01";
            // Wait for and click the vehicle tab button
			// System.out.println("Waiting for vehicle tab button...");
			WebElement vehicleButton = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
			vehicleButton.click();
			
			WebElement VehicleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
	    	
	        Assert.assertTrue(VehicleElement.isDisplayed(), "Vehicle page is not displayed.");
	        Reporter.log("PASSED: Vehicle page is successfully displayed.", true);
	        
			
	       
			driver.findElement(By.xpath(("//flt-semantics[text()='Add']"))).click();
			driver.findElement(By.xpath("//input[@aria-label='Vehicle Capacity']")).sendKeys("20");
			driver.findElement(By.xpath("//input[@aria-label='Vehicle Number']")).sendKeys("ERG567");
			driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();
			WebElement VehicleAdd = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[@id='flt-semantic-node-97']")));
			VehicleAdd.click();
			
			Thread.sleep(2000); // Adjust wait time as necessary
			WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
		            By.cssSelector("[aria-label*='Vehicle added successfully']")
		        ));

		        // Validate the message
		        String messageText = toastMessage.getAttribute("aria-label");
		        Reporter.log("Toast Message: " + messageText, true);
		        Reporter.log("Test Case " + testCaseID + " Passed: Vehicle added successfully.", true);
}
	}



