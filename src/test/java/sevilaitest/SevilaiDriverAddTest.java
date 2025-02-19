package sevilaitest;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SevilaiDriverAddTest extends LoginTest {
	
	@BeforeClass
    public void checkLogin() throws Throwable {
        if (driver == null) { // If driver is not initialized, set it up
            setup();
        }
    }

    @Test(description = "SDA_TC_01: Validate driver is added successfully", dependsOnMethods = "sevilaivehicleedit")
    public void sevilaidriveradd() throws InterruptedException {
        String testCaseID = "SDA_TC_01"; // Assign test case ID

       
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
                {"Driver", "driver1@gmail.com", "90909091", "20", "vehi5"},
                {"Drive", "driver2@gmail.com", "80808031", "30", "vehi3"}
        };

        for (String[] Driver : DriverDetails) {
            driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();

            // Fill out driver details
            WebElement Drivername = driver.findElement(By.cssSelector("[aria-label='Driver Name']"));
            Drivername.click();
            Drivername.sendKeys(Driver[0]); // Name
            driver.findElement(By.cssSelector("[aria-label*='Driver Email']")).sendKeys(Driver[1]); // Email
            driver.findElement(By.cssSelector("[aria-label='Phone Number']")).sendKeys(Driver[2]); // Phone

            // Select driver type
            WebElement dropdownField = driver.findElement(By.xpath("//flt-semantics[text()='Driver Type']"));
            dropdownField.click();
            Thread.sleep(1000);
            WebElement dropdownOption = driver.findElement(By.xpath("//flt-semantics[text()='Sevilai']"));
            dropdownOption.click();

            JavascriptExecutor js = (JavascriptExecutor) driver;

            // Select vehicle capacity
            WebElement vehicleCapacityDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//flt-semantics[text()='Vehicle Capacity']")));
            js.executeScript("arguments[0].scrollIntoView(true);", vehicleCapacityDropdown);
            vehicleCapacityDropdown.click();
            Thread.sleep(1000);
            WebElement vehicleCapacityOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//flt-semantics[text()='" + Driver[3] + "']"))); // Dynamic selection based on array
            vehicleCapacityOption.click();

            // Select vehicle number
            WebElement vehicleNumberDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//flt-semantics[text()='Vehicle Number']")));
            js.executeScript("arguments[0].scrollIntoView(true);", vehicleNumberDropdown);
            vehicleNumberDropdown.click();
            Thread.sleep(1000);
            WebElement vehicleNumberOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//flt-semantics[text()='" + Driver[4] + "']"))); // Dynamic selection based on array
            vehicleNumberOption.click();

            // Click Add button
            driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();

            // Validate success toast message
            WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("[aria-label*='Driver added successfully']")));
            String messageText = toastMessage.getAttribute("aria-label");
            Reporter.log("Toast Message: " + messageText, true);

            try {
                Assert.assertTrue(messageText.contains("Driver added successfully"),
                        "Toast message validation failed for Test Case: " + testCaseID);
                Reporter.log("Test Case " + testCaseID + " Passed: Driver added successfully.", true);
            } catch (AssertionError e) {
                Reporter.log("Test Case " + testCaseID + " Failed.", true);
                throw e;
            }
        }
    }
}
