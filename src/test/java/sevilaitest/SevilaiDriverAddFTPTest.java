package sevilaitest;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
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

public class SevilaiDriverAddFTPTest extends LoginTest {

    @Test
    public void sevilaidriveraddftp() throws Throwable {
        String testCaseID = "SDAFTP_TC_01";
        String resourcePath = "test data\\Driver_valid.csv"; // Path to the CSV in resources

        // Step 1: Read the CSV file
        List<String[]> driverData = CSVUtils.readCSV(resourcePath);

        // Step 2: Login and navigate to the Vehicle tab
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        Actions actions = new Actions(driver);

        // Wait for and click the driver tab button
        WebElement DriverTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 3 of 5')]")));
        actions.moveToElement(DriverTabButton).click().perform();

        // Step 3: Verify the Vehicle page
        WebElement driverPageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Drivers']")));
        Assert.assertTrue(driverPageElement.isDisplayed(), "Driver page is not displayed.");
        Reporter.log("PASSED: Driver page is successfully displayed.", true);

        // Step 4: Click the Add button
        driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();
        Reporter.log("PASSED: Clicked the Add button.", true);

        // Step 5: Download the template
        WebElement downloadTemplateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Download Template']")));
        downloadTemplateButton.click();
        Reporter.log("PASSED: Template download initiated.", true);

        // Step 6: Select dropdown option
        WebElement dropdownField = driver.findElement(By.xpath("//flt-semantics[text()='Driver Type']"));
        dropdownField.click();
        Thread.sleep(1000); // Replace with WebDriverWait for better handling

        WebElement dropdownOption = driver.findElement(By.xpath("//flt-semantics[text()='Sevilai']"));
        dropdownOption.click(); // Click the desired option

        // Step 7: File upload using Robot Class
       String absolutePath = "C:\\Users\\KADIT\\eclipse-workspace1\\Sevillai_Testing_Flow\\src\\test\\resources\\" + resourcePath;
       // String absolutePath = System.getProperty("user.dir") + "src/test/resources" + resourcePath;

        File file = new File(absolutePath);

        // Trigger the "Drop Your Files Here" button
        WebElement uploadElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//flt-semantics[text()='Drop Your Files Here']")));
        uploadElement.click(); // This opens the file explorer

        // Handle file explorer using Robot Class
        handleFileUploadWithRobot(file.getAbsolutePath());

        // Step 8: Validate the toast message
        WebElement toastMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label*='Driver added successfully']")));
        Assert.assertTrue(toastMessage.isDisplayed(), "Toast message is not displayed.");
        Reporter.log("PASSED: Driver sheet uploaded successfully.", true);
    }

    private void handleFileUploadWithRobot(String filePath) throws Exception {
        // Copy the file path to the clipboard
        StringSelection filePathSelection = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(filePathSelection, null);

        // Create a Robot instance
        Robot robot = new Robot();

        // Simulate CTRL + V (Paste)
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        // Simulate pressing ENTER
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // Add a small delay to allow the application to process the upload
        Thread.sleep(2000);
    }
}
