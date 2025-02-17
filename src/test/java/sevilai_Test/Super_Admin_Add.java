package sevilai_Test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class Super_Admin_Add extends Login_Test {
    
    @Test(description = "SAA_TC_01: Validate Admin is added successfully")
    public void superadmin() throws Throwable {
        String testCaseID = "SAA_TC_01"; // Assign test case ID
        
        Actions actions = new Actions(driver);

        // Navigate to the Driver tab
         WebElement DriverTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 5 of 5')]")));
         actions.moveToElement(DriverTabButton).click().perform();

        
        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("admin"), "Current URL does not indicate the Admin page.");
        Reporter.log("PASSED: Admin page is successfully loaded with URL: " + currentURL, true);

        // Test data for super admins
        String[][] adminData = {
            {"Super", "super@gmail.com", "12345123", "SUPER_ADMIN"},
            {"sup", "sup2@gmail.com", "12345122", "SUPER_ADMIN"}
        };

        for (String[] data : adminData) {
            // Click the Add button to add a new admin
            driver.findElement(By.xpath(("//flt-semantics[text()='Add']"))).click();

            // Fill in the details for the admin
            WebElement Name = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Admin Name']")));
            Name.sendKeys(data[0]);
            WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Admin Email']")));
            email.sendKeys(data[1]);
            WebElement Phonenumber = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Phone Number']")));
            Phonenumber.sendKeys(data[2]);
            
            // Select Admin Type from the dropdown
            WebElement dropdownField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Admin Type']")));
            dropdownField.click();
            Thread.sleep(1000); // Replace with WebDriverWait for better handling
            WebElement dropdownOption = driver.findElement(By.xpath("//flt-semantics[text()='" + data[3] + "']"));
            dropdownOption.click();

            // Click the Add button to submit the form
            WebElement Add = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flt-semantics[text()='Add']")));
            Add.click();
            
            // Wait for the toast popup to appear
            WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[aria-label*='Admin added successfully']")
            ));

            // Validate the message
            String messageText = toastMessage.getAttribute("aria-label");
            Reporter.log("Toast Message: " + messageText, true);

            // Assert and log results
            try {
                Assert.assertTrue(messageText.contains("Admin added successfully"),
                        "Toast message validation failed for Test Case: " + testCaseID);
                Reporter.log("Test Case " + testCaseID + " Passed: Admin " + data[0] + " added successfully.", true);
            } catch (AssertionError e) {
                Reporter.log("Test Case " + testCaseID + " Failed for Admin " + data[0], true);
                throw e; // Ensures failure is recorded in the TestNG report
            }
        }
    }
}
