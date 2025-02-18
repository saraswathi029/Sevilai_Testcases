package sevilaitest;

import java.time.Duration;
import java.time.LocalDate;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class ComCalenderAddTest extends LoginTest {

    @Test(description = "CP_TC_01: Validate Company is added successfully", dependsOnMethods = "testLogin")
    public void companyadd() throws Exception {
        String testCaseID = "CM_TC_01"; // Assign test case ID
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

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
        name.sendKeys("test");

        WebElement number = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Phone Number']")));
        number.click();
        number.sendKeys("34343434");

        WebElement id = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Route ID']")));
        id.click();
        id.sendKeys("Tes100");

        // Start Date Selection - Dynamically select today's date
        LocalDate today = LocalDate.now();
        WebElement startDateField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Start Date']")));
        startDateField.click();

        WebElement todayDate = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(), '" + today.getDayOfMonth() + "')]")));
        todayDate.click();

        WebElement startDateOkButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='OK']")));
        startDateOkButton.click();

     // End Date Selection - Dynamically select tomorrow's date
        LocalDate tomorrow = today.plusDays(1);
        String expectedMonth = tomorrow.getMonth().name().substring(0, 1).toUpperCase() + tomorrow.getMonth().name().substring(1).toLowerCase(); // Format to match UI (e.g., January)
        int expectedYear = tomorrow.getYear();

        WebElement endDateField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='End Date']")));
        endDateField.click();

        // Debugging: Log the expected month and year
        Reporter.log("Expected Month: " + expectedMonth + ", Expected Year: " + expectedYear, true);

        // Loop to navigate through the calendar until the correct month and year are displayed
        while (true) {
            // Get the displayed month and year from the calendar header
            WebElement calendarHeader = driver.findElement(By.xpath("//flt-semantics[contains(@aria-label, 'Calendar header')]")); // Update this XPath to match your application
            String displayedMonthYear = calendarHeader.getText();
            Reporter.log("Displayed Month and Year: " + displayedMonthYear, true); // Debugging log

            // Extract displayed month and year from the header
            String displayedMonth = displayedMonthYear.split(" ")[0]; // Assuming "January 2024"
            int displayedYear = Integer.parseInt(displayedMonthYear.split(" ")[1]);

            if (displayedMonth.equalsIgnoreCase(expectedMonth) && displayedYear == expectedYear) {
                break; // Correct month and year displayed
            }

            // Click the "Next Month" button if needed
            WebElement nextMonthButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Next month']")));
            nextMonthButton.click();
        }

        // Select tomorrow's date in the calendar
        WebElement nextDay = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[contains(text(), '" + tomorrow.getDayOfMonth() + "')]")));
        nextDay.click();

        // Click the "OK" button
        WebElement endDateOkButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='OK']")));
        endDateOkButton.click();


        // Submit the form
        WebElement addButtonFinal = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Add']")));
        addButtonFinal.click();

        // Wait for the toast popup to appear
        WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label*='Company added successfully']")));

        // Validate the message
        String messageText = toastMessage.getAttribute("aria-label");
        Reporter.log("Toast Message: " + messageText, true);

        // Assert and log results
        try {
            Assert.assertTrue(messageText.contains("Company added successfully"),
                    "Toast message validation failed for Test Case: " + testCaseID);
            Reporter.log("Test Case " + testCaseID + " Passed: Company added successfully.", true);
        } catch (AssertionError e) {
            Reporter.log("Test Case " + testCaseID + " Failed.", true);
            throw e; // Ensures failure is recorded in the TestNG report
        }
    }
}
