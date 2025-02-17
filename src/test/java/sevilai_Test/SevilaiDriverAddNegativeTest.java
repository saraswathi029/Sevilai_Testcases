package sevilai_Test;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SevilaiDriverAddNegativeTest extends Login_Test {

    // DataProvider for negative test cases
    @DataProvider(name = "negativeTestData")
    public Object[][] provideTestData() {
        return new Object[][] {
            {"", "testemail@gmail.com", "12345678", "SDA_TC_01", "Name is required"}, // Empty name
            {"Test Driver", "testemail@gmail.com", "1234", "SDA_TC_02", "Enter a valid phone number"}, // Invalid phone number
            {"Test Driver", "testemail@gmail.com", "12312312", "SDA_TC_03", "Driver phone number is already exists"}, // Existing phone number
            {"Test Driver", "testemail@gmail.com", "", "SDA_TC_04", "Phone Number is required"}, // Missing phone number
            {"Test1", "testemail@gmail.com", "12345678", "SDA_TC_05", "Enter a valid name"},
            {"Test Driver", "testemail@gmail.com", "abc", "SDA_TC_06", "Enter a valid phone number"},
            {"Test Driver", "testemail", "12345678", "SDA_TC_07", "Enter a valid email"},
            {"", "", "", "SDA_TC_08", "Name is required,Phone Number is required,Driver Type is required,Vehicle Capacity is required,Vehicle Number is required"}, // All fields empty
            {"Test Driver", "testemail@gmail.com", "12345678", "SDA_TC_09", "Vehicle Capacity is required"}, // Vehicle Capacity empty
            {"Test Driver", "testemail@gmail.com", "12345678", "SDA_TC_10", "Vehicle Number is required"}, // Vehicle Number empty
            {"Test Driver", "testemail@gmail.com", "12345678", "SDA_TC_11", "Driver Type is required"} // Driver Type empty
        };
    }

    @Test(dataProvider = "negativeTestData", description = "Validate error messages for negative scenarios")
    public void validateNegativeTestCases(String driverName, String driverEmail, String phoneNumber, String testCaseID, String expectedErrorMessage) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        Actions actions = new Actions(driver);

        // Navigate to the Driver tab
        WebElement DriverTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 3 of 5')]")));
        actions.moveToElement(DriverTabButton).click().perform();

        driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();

        // Fill in the form
        driver.findElement(By.cssSelector("[aria-label='Driver Name']")).sendKeys(driverName);
        driver.findElement(By.cssSelector("[aria-label*='Driver Email']")).sendKeys(driverEmail);
        driver.findElement(By.cssSelector("[aria-label='Phone Number']")).sendKeys(phoneNumber);

        // Handle dropdown selections based on test case
        if (!testCaseID.equals("SDA_TC_11")) {//&& !testCaseID.equals("SDA_TC_11")) {
            WebElement dropdownField = driver.findElement(By.xpath("//flt-semantics[text()='Driver Type']"));
            dropdownField.click();
            Thread.sleep(1000);

            WebElement dropdownOption = driver.findElement(By.xpath("//flt-semantics[text()='Sevilai']"));
            dropdownOption.click();
        }

        if (!testCaseID.equals("SDA_TC_09") && !testCaseID.equals("SDA_TC_11")){//&& !testCaseID.equals("SDA_TC_11")) {
            WebElement vehicleCapacityDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Vehicle Capacity']")));
            vehicleCapacityDropdown.click();
            Thread.sleep(1000);
            WebElement vehicleCapacityOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='20']")));
            vehicleCapacityOption.click();
        }

        if (!testCaseID.equals("SDA_TC_09") && !testCaseID.equals("SDA_TC_10") && !testCaseID.equals("SDA_TC_11")){// && !testCaseID.equals("SDA_TC_11")) {
            WebElement vehicleNumberDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Vehicle Number']")));
            vehicleNumberDropdown.click();
            Thread.sleep(1000);
            WebElement vehicleNumberOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='ERG567']")));
            vehicleNumberOption.click();
        }

        // Click Add button
        driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();
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
