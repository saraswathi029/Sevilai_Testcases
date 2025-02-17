package sevilai_Test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ContractDriverAdd extends Login_Test {
    
    @BeforeClass
    public void checkLogin() throws Throwable {
        if (driver == null) { // If driver is not initialized, set it up
            setup();
        }
    }

    @Test(description = "CDA_TC_02: Validate driver is added successfully")
    public void contractdriveradd() throws Throwable  {
        String testCaseID = "CDA_TC_02"; // Assign test case ID
        
        Actions actions = new Actions(driver);

        // Navigate to the Driver tab
        WebElement DriverTabButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 3 of 5')]")));
        actions.moveToElement(DriverTabButton).click().perform();

        WebElement DriverElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Drivers']")));

        Assert.assertTrue(DriverElement.isDisplayed(), "Driver page is not displayed.");
        Reporter.log("PASSED: Driver page is successfully displayed.", true);

        // Array of driver details (name, email, phone, vehicle capacity, and vehicle number)
        String[][] DriverDetails = {
                {"Dumm", "dum1@gmail.com", "12121200", "10", "Dumm2"},
                {"DummyTe", "DummyTe@gmail.com", "12312300", "20", "DumTe2"}
        };

        for (String[] Driver : DriverDetails) {
            driver.findElement(By.xpath(("//flt-semantics[text()='Add']"))).click();
            WebElement Drivername = driver.findElement(By.cssSelector("[aria-label='Driver Name']"));
            Drivername.click();
            Drivername.sendKeys(Driver[0]); // Name
            driver.findElement(By.cssSelector("[aria-label*='Driver Email']")).sendKeys(Driver[1]); // Email
            driver.findElement(By.cssSelector("[aria-label='Phone Number']")).sendKeys(Driver[2]); // Phone

            // Locate the dropdown field for Driver Type
            WebElement dropdownField = driver.findElement(By.xpath("//flt-semantics[text()='Driver Type']"));
            dropdownField.click(); // Click to open the dropdown
            Thread.sleep(1000); // Replace with WebDriverWait for better handling

            // Select 'Contract' option
            WebElement dropdownOption = driver.findElement(By.xpath("//flt-semantics[text()='Contract']")); 
            dropdownOption.click(); 

            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Enter vehicle capacity as a text field
            WebElement vehicleCapacityField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@aria-label='Vehicle Capacity']")));
            js.executeScript("arguments[0].scrollIntoView(true);", vehicleCapacityField);
            vehicleCapacityField.click();
            vehicleCapacityField.clear();
            vehicleCapacityField.sendKeys(Driver[3]); // Enter capacity

            // Enter vehicle number as a text field
            WebElement vehicleNumberField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//input[@aria-label='Vehicle Number']")));
            js.executeScript("arguments[0].scrollIntoView(true);", vehicleNumberField);
            vehicleNumberField.click();
            vehicleNumberField.clear();
            vehicleNumberField.sendKeys(Driver[4]); // Enter vehicle number

            driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();
           
            // Wait for the toast popup to appear
            WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[aria-label*='Driver added successfully']")
            ));

            // Validate the message
            String messageText = toastMessage.getAttribute("aria-label");
            Reporter.log("Toast Message: " + messageText, true);

            // Assert and log results
            try {
                Assert.assertTrue(messageText.contains("Driver added successfully"),
                        "Toast message validation failed for Test Case: " + testCaseID);
                Reporter.log("Test Case " + testCaseID + " Passed: Driver added successfully.", true);
            } catch (AssertionError e) {
                Reporter.log("Test Case " + testCaseID + " Failed.", true);
                throw e; // Ensures failure is recorded in the TestNG report
            }
        }
    }
}
