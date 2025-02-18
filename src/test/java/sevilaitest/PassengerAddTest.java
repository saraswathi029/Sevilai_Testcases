package sevilaitest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class PassengerAddTest extends LoginTest {

    @Test
    public void passengeradd() {
        String testCaseID = "CM_TC_01"; // Assign test case ID
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(200));
        //JavascriptExecutor js = (JavascriptExecutor) driver;
       // Actions actions = new Actions(driver);

        // Navigate to Company Tab
        WebElement companyTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 4 of 5')]")));
        companyTab.click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("company"), "Current URL does not indicate the Company page.");
        Reporter.log("PASSED: Company page is successfully loaded with URL: " + currentURL, true);
        //String routeId = "ABC000"; // Replace with the specific Route ID
        WebElement companyRow = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[text()='ABC000']/preceding::flt-semantics[text()='ABCD']")));
       // Step 3: Scroll horizontally to make the "Edit" button visible
        
       // js.executeScript("arguments[0].scrollIntoView({block: 'nearest', inline: 'center'});", companyRow);
        

        // Step 3: Hover over the company row to make the "Edit" button visible
        Actions actions = new Actions(driver);
        actions.moveToElement(companyRow).perform();
        companyRow.click();

		/*
		 * // Handle Rows per Page Dropdown WebElement rowsPerPageDropdown =
		 * wait.until(ExpectedConditions.elementToBeClickable(By.
		 * xpath("//flt-semantics[text()='20 rows per page']")));
		 * rowsPerPageDropdown.click(); // Open the dropdown
		 * 
		 * // Scroll to ensure visibility of the 50 rows option
		 * js.executeScript("arguments[0].scrollIntoView(true);", rowsPerPageDropdown);
		 * 
		 * // Hover and click on "50 rows per page" WebElement rows50Option =
		 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.
		 * xpath("//flt-semantics[text()='50 rows per page']")));
		 * actions.moveToElement(rows50Option).perform(); // Hover on the element
		 * rows50Option.click(); // Select 50 rows per page
		 * Reporter.log("PASSED: Successfully selected 50 rows per page.", true);
		 */
       // WebElement searchcompany = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label*='Company Search...']")));
        //searchcompany.click();
        //searchcompany.sendKeys("ABC000");

        // Locate the target company using the unique Route ID
       // String targetRouteID = "ABC000";
        //WebElement targetCompany = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//flt-semantics[contains(text(),'" + targetRouteID + "')]")));

        // Scroll to the company element
       // js.executeScript("arguments[0].scrollIntoView(true);", targetCompany);

        // Click on the company
       
       // Reporter.log("PASSED: Successfully clicked on the company with Route ID: " + targetRouteID, true);

        // Navigate to Passenger Tab
        
        WebElement passengerTab = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Tab 2 of 3')]")));
        passengerTab.click();

        // Click Add button
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Add']")));
        addButton.click();

        // Fill Passenger Details
        WebElement name = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Passenger Name']")));
        name.sendKeys("SARA");
        WebElement email = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Passenger Email']")));
        email.sendKeys("SARA@yahoo.com");
        WebElement number = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Phone Number']")));
        number.sendKeys("23232323");
        WebElement location = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Passenger Location']")));
        location.sendKeys("Kadayanallur");

        // Handle Pickup Dropdown
        WebElement pickup = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(),'Pickup')]")));
        pickup.click();
        WebElement pickupDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='ABC001']")));
        pickupDropdown.click();

        // Handle Drop Dropdown
        WebElement drop = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(),'Drop')]")));
        drop.click();
        WebElement dropDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='ABC001']")));
        dropDropdown.click();

        // Click Add Button to Save
        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Add']")));
        saveButton.click();

        Reporter.log("PASSED: Passenger successfully added.", true);
        // Wait for the toast popup to appear
        WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label*='Passenger added successfully']")));

        // Validate the message
        String messageText = toastMessage.getAttribute("aria-label");
        Reporter.log("Toast Message: " + messageText, true);

        // Assert and log results
        try {
            Assert.assertTrue(messageText.contains("Passenger added successfully"),
                    "Toast message validation failed for Test Case: " + testCaseID);
            Reporter.log("Test Case " + testCaseID + " Passed: Passenger added successfully.", true);
        } catch (AssertionError e) {
            Reporter.log("Test Case " + testCaseID + " Failed.", true);
            throw e; // Ensures failure is recorded in the TestNG report
        }
    }
}
