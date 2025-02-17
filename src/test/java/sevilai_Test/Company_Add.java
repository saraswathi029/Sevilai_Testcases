package sevilai_Test;

import java.time.Duration;
import java.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class Company_Add extends Login_Test {

    @Test(description = "CP_TC_01: Validate Company is added successfully", dependsOnMethods = "testLogin")
    public void companyadd() throws Exception {
        String testCaseID = "CM_TC_01"; // Assign test case ID
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(40));

        // Navigate to Company Tab
        WebElement companyTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 4 of 5')]")));
        companyTab.click();

        String currentURL = driver.getCurrentUrl();
        Assert.assertTrue(currentURL.contains("company"), "Current URL does not indicate the Company page.");
        Reporter.log("PASSED: Company page is successfully loaded with URL: " + currentURL, true);

        // Click Add button
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Add']")));
        addButton.click();

        // Locate the Add Company form's header
        WebElement formHeader = driver.findElement(By.xpath("//span[text()='Add Company']"));
        Assert.assertTrue(formHeader.isDisplayed(), "The Add Company form is not displayed.");
        Reporter.log("The 'Add Company' form is successfully opened.", true);

        WebElement name = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Company Name']")));
        name.click();
        name.sendKeys("Test");

        WebElement number = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Phone Number']")));
        number.click();
        number.sendKeys("34343434");

        WebElement id = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Route ID']")));
        id.click();
        id.sendKeys("Tes100");

        // Start Date Selection - Select today's date from calendar
        WebElement startDateField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Start Date']")));
        startDateField.click();
        WebElement todayDate = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(), '" + LocalDate.now().getDayOfMonth() + "')]")));
        todayDate.click();

        WebElement startDateOkButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='OK']")));
        startDateOkButton.click();

        // End Date Selection - Select the same date next year
        WebElement endDateField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='End Date']")));
        endDateField.click();
        
     // Open the "Select Year" dropdown (you need to click the dropdown to view the years)
       
        Actions actions = new Actions(driver);
        WebElement selectYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Select year']")));  // This is the "Select Year" element
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", selectYearDropdown);
        actions.moveToElement(selectYearDropdown).click().perform();

        // Open the calendar and find the year dropdown
        //WebElement calendarYearDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(),'2025')]"))); // The current year is typically displayed
        //calendarYearDropdown.click();

        // Select the next year (2026) from the dropdown
        WebElement nextYearOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(),'2026')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextYearOption);
        
        wait.until(ExpectedConditions.elementToBeClickable(nextYearOption));
        nextYearOption.click();

        // Select the same day (next year)
        LocalDate nextYearDate = LocalDate.now().plusYears(1);
        WebElement endDate = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(), '" + nextYearDate.getDayOfMonth() + "')]")));
        endDate.click();

        // Click the OK button to confirm the selection
        WebElement endDateOkButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='OK']")));
        endDateOkButton.click();

        // Submit the form
        WebElement addButtonFinal = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Add']")));
        addButtonFinal.click();

        // Wait for the toast popup to appear
        WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label*='Company updated successfully']")));

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
    }
}
