package sevilaitest;

import java.io.File;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import utility.CSVUtils;

public class SevilaiVehicleAddFTPTest extends LoginTest {

    @Test
    public void sevilaivehicleaddftp() throws Throwable {
        String testCaseID = "SVAFTP_TC_01";
        String resourcePath = "test data/Vehicle_valid.csv"; // Path to the CSV in resources

        // Step 1: Read the CSV file
        List<String[]> vehicleData = CSVUtils.readCSV(resourcePath);

        // Step 2: Login and navigate to the Vehicle tab
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Actions actions = new Actions(driver);
        WebElement vehicleTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 2 of 5')]")));
        actions.moveToElement(vehicleTabButton).click().perform();

        // Step 3: Verify the Vehicle page
        WebElement vehiclePageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Vehicles']")));
        Assert.assertTrue(vehiclePageElement.isDisplayed(), "Vehicle page is not displayed.");
        Reporter.log("PASSED: Vehicle page is successfully displayed.", true);

        // Step 4: Click the Add button
        driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();
        Reporter.log("PASSED: Clicked the Add button.", true);

        // Step 5: Download the template
        WebElement downloadTemplateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Download Template']")));
        downloadTemplateButton.click();
        Reporter.log("PASSED: Template download initiated.", true);

        // Step 6: Upload the file
        WebElement uploadElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//flt-semantics[text()='Drop Your Files Here']")));
        Reporter.log("PASSED: Located the 'Drop Your Files Here' element.", true);

        // Absolute path to the file in resources
        String absolutePath = System.getProperty("user.dir") + "/src/test/resources/" + resourcePath;
        File file = new File(absolutePath);

        // Simulate drag-and-drop using JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
            "const dataTransfer = new DataTransfer();" +
            "const file = new File([''], arguments[0]);" +
            "dataTransfer.items.add(file);" +
            "const dropEvent = new DragEvent('drop', { dataTransfer });" +
            "arguments[1].dispatchEvent(dropEvent);",
            file.getAbsolutePath(), uploadElement
        );

        Reporter.log("PASSED: Vehicle sheet uploaded successfully via drag-and-drop.", true);

        // Step 7: Wait for and validate the toast message
        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label*='Vehicle added successfully']")));
        Assert.assertTrue(toastMessage.isDisplayed(), "Toast message is not displayed.");
        String messageText = toastMessage.getAttribute("aria-label");
        Reporter.log("Toast Message: " + messageText, true);

        Reporter.log("Test Case " + testCaseID + " Passed: Vehicle added successfully.", true);
    }
}
