package sevilaitest;




import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
	import org.openqa.selenium.interactions.Actions;
	import org.openqa.selenium.support.ui.ExpectedConditions;
	import org.openqa.selenium.support.ui.WebDriverWait;
	import org.testng.Assert;
	import org.testng.Reporter;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;

	public class ContractDriverEditNegativeTest extends LoginTest {

	    // DataProvider for negative test cases
	    @DataProvider(name = "contractdrivereditnegativeTest")
	    public Object[][] provideTestData() {
	        return new Object[][] {
	        	 {"", "testemail@gmail.com", "12345678", "20", "TES123", "SDA_TC_01", "Name is required"}, // Empty name
	             {"Test Driver", "testemail@gmail.com", "1234", "20", "TES123", "SDA_TC_02", "Enter a valid phone number"}, // Invalid phone number
	             {"Test Driver", "testemail@gmail.com", "12312312", "20", "TES123", "SDA_TC_03", "Driver phone number is already exists"}, // Existing phone number
	             {"Test Driver", "testemail@gmail.com", "", "20", "TES123", "SDA_TC_04", "Phone Number is required"}, // Missing phone number
	             {"Test1", "testemail@gmail.com", "12345678", "20", "TES123", "SDA_TC_05", "Enter a valid name"},
	             {"Test Driver", "testemail@gmail.com", "abc", "20", "TES123", "SDA_TC_06", "Enter a valid phone number"},
	             {"Test Driver", "testemail", "12345678", "20", "TES123", "SDA_TC_07", "Enter a valid email"},
	             {"Test Driver", "testemail@gmail.com", "12345678", "", "TES123", "SDA_TC_08", "Vehicle Capacity is required"},
	             {"", "", "", "", "", "SDA_TC_09", "Name is required,Phone Number is required,Driver Type is required,Vehicle Capacity is required,Vehicle Number is required"}, // All fields empty
	             {"Test Driver", "testemail@gmail.com", "12345678", "SDA_TC_10", "Vehicle Capacity is required"}, // Vehicle Capacity empty
	             {"Test Driver", "testemail@gmail.com", "12345678", "", "TES123", "SDA_TC_11", "Vehicle Number is required"}, // Vehicle Number empty
	             {"Test Driver", "testemail@gmail.com", "12345678", "", "", "SDA_TC_12", "Vehicle Number is required, Vehicle Capacity is required"},
	             {"Test Driver", "testemail@gmail.com", "12345678", "SDA_TC_13", "Driver Type is required"}, // Driver Type empty
	             {"Test Driver", "testemail@gmail.com", "12345678", "20", "SPR878", "SDA_TC_14", "Vehicle Number is already exists"} // Duplicate Vehicle Number
	            
	        };
	    }
	    
	    public void clearFieldData(WebElement element) {
	        try {
	            element.sendKeys(Keys.CONTROL + "a"); // Select all text
	            element.sendKeys(Keys.BACK_SPACE);   // Clear selected text
	            //Reporter.log("Field data cleared successfully.", true);
	        } catch (Exception e) {
	            Reporter.log("Failed to clear field data: " + e.getMessage(), true);
	            throw e;
	        }
	    }

	    @Test(dataProvider = "contractdrivereditnegativeTest", description = "Validate error messages for negative scenarios")
	    public void validateNegativeTestCases(String driverName, String driverEmail, String phoneNumber, String vehicleCapacity, String vehicleNumber, String testCaseID, String expectedErrorMessage)throws InterruptedException {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	        Actions actions = new Actions(driver);

	        // Navigate to the Driver tab
	        WebElement DriverTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 3 of 5')]")));
	        actions.moveToElement(DriverTabButton).click().perform();
	        Thread.sleep(2000);
	        if (testCaseID.equals("SDA_TC_01")) {
	        WebElement DriverSearchButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@aria-label='Search...']")));
	        DriverSearchButton.click();
	        DriverSearchButton.sendKeys("21212121");
	        }
	        WebElement DriverEditButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()='Edit']")));
	        DriverEditButton.click();

	       
	     
	        // Fill in the form
	        WebElement name=driver.findElement(By.cssSelector("[aria-label='Driver Name']"));
	        clearFieldData(name); // Clear old data
	        name.sendKeys(driverName);
	        WebElement email=driver.findElement(By.cssSelector("[aria-label*='Email Address']"));
	        clearFieldData(email); // Clear old data
	        email.sendKeys(driverEmail);
	        WebElement number=driver.findElement(By.cssSelector("[aria-label='Driver Phone Number']"));
	        clearFieldData(number); // Clear old data
	        number.sendKeys(phoneNumber);
	        // Handle dropdown selections based on test case
	        if (!testCaseID.equals("SDA_TC_13") && !testCaseID.equals("SDA_TC_09")) {
	            WebElement dropdownField = driver.findElement(By.xpath("//flt-semantics[text()='Driver Type']"));
	            dropdownField.click();
	            Thread.sleep(1000);

	            WebElement dropdownOption = driver.findElement(By.xpath("//flt-semantics[text()='Contract']"));
	            dropdownOption.click();
	        }

	        if (!testCaseID.equals("SDA_TC_08") && !testCaseID.equals("SDA_TC_12") && !testCaseID.equals("SDA_TC_09")) {
	            WebElement vehicleCapacityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Capacity']")));
	            vehicleCapacityField.sendKeys(vehicleCapacity);
	        }

	        if (!testCaseID.equals("SDA_TC_09") && !testCaseID.equals("SDA_TC_11") && !testCaseID.equals("SDA_TC_12")) {
	            WebElement vehicleNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Number']")));
	            vehicleNumberField.sendKeys(vehicleNumber);
	        }

	       

	      
	           

	        // Click Add button
	        driver.findElement(By.xpath("//flt-semantics[text()='Update']")).click();
	        // Validate error message for popup validation in SDA_TC_03
	        if (testCaseID.equals("SDA_TC_03")) {
	            // Handle specific popup error for "Driver phone number is already exists"
	            WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
	                By.cssSelector("[aria-label*='Driver phone number is already exists']")
	            ));
	            String messageText = errorMessageElement.getAttribute("aria-label");
	            Reporter.log("Toast Message: " + messageText, true);

	            // Assert and log results
	            try {
	                Reporter.log("Expected Error: " + expectedErrorMessage, true);
	                Assert.assertTrue(messageText.contains("Driver phone number is already exists"),
	                    "Toast message validation failed for Test Case: " + testCaseID);
	                Reporter.log("Test Case " + testCaseID + " Passed: Driver phone number is already exists", true);
	            } catch (AssertionError e) {
	                Reporter.log("Test Case " + testCaseID + " Failed.", true);
	                throw e; // Ensures failure is recorded in the TestNG report
	            }
	        } else {

	        // Validate error message// Handle generic field validation errors
	        WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'" + expectedErrorMessage + "')]")));
	        String actualErrorMessage = errorMessageElement.getText();

	        Reporter.log("Expected Error Message: " + expectedErrorMessage, true);
	        Reporter.log("Actual Error Message: " + actualErrorMessage, true);

	        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message validation failed for Test Case: " + testCaseID);

	        Reporter.log("Test Case " + testCaseID + " Passed.", true);
	        }

	        // Navigate back to the homepage
	        WebElement CrossButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@role='button']")));
	        CrossButton.click();
	        WebElement DriverButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Tab 3 of 5')]")));
	        
	        actions.moveToElement(DriverButton).click().perform();
	        Assert.assertTrue(DriverButton.isDisplayed(), "Driver page is not displayed.");
	        Reporter.log("- PASSED: Driver page is successfully displayed.", true);
	    
	}
	}






