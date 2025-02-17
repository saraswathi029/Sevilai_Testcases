package sevilai_Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SevilaiVehAdd extends Login_Test {
	
	@BeforeClass
    public void checkLogin() throws Throwable {
        if (driver == null) { // If driver is not initialized, set it up
            setup();
        }
    }
@Test
public void sevilaiadd() throws Throwable {
    String testCaseID = "SVA_TC_01";
    Actions actions = new Actions(driver);

   // Navigate to the Driver tab
    WebElement DriverTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
    actions.moveToElement(DriverTabButton).click().perform();


	       

     WebElement VehicleElement = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));

	 Assert.assertTrue(VehicleElement.isDisplayed(), "Vehicle page is not displayed.");
	 Reporter.log("PASSED: Vehicle page is successfully displayed.", true);

	 // Array of vehicle details (capacity and number)
	  String[][] vehicleDetails = {
	            {"20", "vehi1"},
	            {"20", "vehi2"},
	            {"30", "vehi3"},
	            {"40", "vehi4"}
	            
};

	        for (String[] vehicle : vehicleDetails) {
	            // Click the Add button
	            driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();

	            // Enter vehicle details
	            driver.findElement(By.xpath("//input[@aria-label='Vehicle Capacity']")).sendKeys(vehicle[0]);
	         // Locate the vehicle number input field
	            WebElement vehicleNumberInput = driver.findElement(By.xpath("//input[@aria-label='Vehicle Number']"));

	           
	            vehicleNumberInput.clear(); // Clear field before entering text
	            vehicleNumberInput.sendKeys(vehicle[1]); // Enter vehicle number
	            Thread.sleep(500); // Small wait to let UI process the input



	            // Click the final Add button
	            driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();

	            // Validate the toast message
	            WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
	                By.cssSelector("[aria-label*='Vehicle added successfully']")));
	            String messageText = toastMessage.getAttribute("aria-label");
	            Reporter.log("Toast Message: " + messageText, true);

	            Assert.assertTrue(
	                messageText.contains("Vehicle added successfully"),
	                "Failed to add vehicle: " + vehicle[1]
	            );

	            Reporter.log("Vehicle with capacity " + vehicle[0] + " and number " + vehicle[1] + " added successfully.", true);

	            // Wait before adding the next vehicle to avoid timing issues
	            Thread.sleep(2000); // Adjust wait time as necessary
	        }

	        Reporter.log("Test Case " + testCaseID + " Passed: All vehicles added successfully.", true);
	    }
	}



