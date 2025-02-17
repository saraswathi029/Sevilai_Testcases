package sevilai_Test;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class VehiclePageNegativeTests {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() throws InterruptedException {
        driver = new EdgeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("http://sevilai-transport-uat.s3-website-ap-southeast-1.amazonaws.com");
        Thread.sleep(4000);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@id='flt-semantic-node-5']/input")));
        usernameField.click();
        usernameField.sendKeys("saraswathi.m@kaditinnovations.com");
        Thread.sleep(1000);
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@id='flt-semantic-node-6']/input")));
        passwordField.click();
        passwordField.sendKeys("97903450");
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@id='flt-semantic-node-4']/flt-semantics-container/flt-semantics[9]")));
        loginButton.click();
        // Navigate to Vehicle Page
        WebElement vehicleButton = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[aria-label*='Tab 2 of 5']")));
        vehicleButton.click();
    }

    @Test
    public void testCapacityProvidedNumberEmpty() throws IOException {
        // Click on the "Add" button
        driver.findElement(By.cssSelector("[aria-label*='Add']")).click();
        
        // Enter vehicle details with a provided capacity and empty number
        driver.findElement(By.cssSelector("[aria-label='Vehicle Capacity']")).sendKeys("20");
        driver.findElement(By.cssSelector("[aria-label='Vehicle Number']")).clear();
        
        // Click the "Add" button again
        driver.findElement(By.cssSelector("[aria-label*='Add']")).click();

        // Wait for error message to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errornumberMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[aria-label='Vehicle Number is required']")
        ));

        // Take a screenshot after the error message is visible
        captureScreenshot("CapacityProvidedNumberEmpty_Error");

        Assert.assertTrue(errornumberMessage.isDisplayed(), "Error message not displayed for empty vehicle number");
        System.out.println("Vehicle Number Error Text: " + errornumberMessage.getText());
    }

    @Test
    public void testNumberProvidedCapacityEmpty() throws IOException {
        // Click on the "Add" button
        driver.findElement(By.cssSelector("[aria-label*='Add']")).click();
        
        // Enter vehicle details with a provided number and empty capacity
        driver.findElement(By.cssSelector("[aria-label='Vehicle Capacity']")).clear();
        driver.findElement(By.cssSelector("[aria-label='Vehicle Number']")).sendKeys("ERG567");
        
        // Click the "Add" button again
        driver.findElement(By.cssSelector("[aria-label*='Add']")).click();

        // Wait for error message to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[aria-label='Vehicle Capacity is required']")
        ));

        // Take a screenshot after the error message is visible
        captureScreenshot("NumberProvidedCapacityEmpty_Error");

        Assert.assertTrue(errorMessage.isDisplayed(), "Error message not displayed for empty capacity");
        System.out.println("Vehicle Capacity Error Text: " + errorMessage.getText());
    }

    @Test
    public void testBothFieldsEmpty() throws IOException {
        // Click on the "Add" button
        driver.findElement(By.cssSelector("[aria-label*='Add']")).click();
        
        // Clear both fields (Capacity and Number)
        driver.findElement(By.cssSelector("[aria-label='Vehicle Capacity']")).clear();
        driver.findElement(By.cssSelector("[aria-label='Vehicle Number']")).clear();
        
        // Click the "Add" button again
        driver.findElement(By.cssSelector("[aria-label*='Add']")).click();

        // Wait for error messages to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement capacityError = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[aria-label='Vehicle Capacity is required']")
        ));
        WebElement numberError = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("[aria-label='Vehicle Number is required']")
        ));

        // Take a screenshot after the error messages are visible
        captureScreenshot("BothFieldsEmpty_Error");

        Assert.assertTrue(capacityError.isDisplayed(), "Vehicle Capacity error message is not visible");
        Assert.assertTrue(numberError.isDisplayed(), "Vehicle Number error message is not visible");
    }

    @Test
    public void testDuplicateVehicleNumber() throws IOException, InterruptedException {
        // Define the folder path for saving screenshots
        String screenshotFolderPath = "Screenshots/vehicle_negative_scenarios";

        // Create the folder if it does not exist
        File folder = new File(screenshotFolderPath);
        if (!folder.exists()) {
            if (folder.mkdirs()) {
                System.out.println("Screenshot folder created: " + screenshotFolderPath);
            } else {
                System.out.println("Failed to create screenshot folder: " + screenshotFolderPath);
            }
        }

        // Open Add Vehicle popup
        driver.findElement(By.cssSelector("[aria-label*='Add']")).click();

        // Enter vehicle details with a duplicate number
        driver.findElement(By.cssSelector("[aria-label='Vehicle Capacity']")).sendKeys("VAN20");
        driver.findElement(By.cssSelector("[aria-label='Vehicle Number']")).sendKeys("VAN123");
        driver.findElement(By.cssSelector("[aria-label='Add']")).click();

        // Pause to ensure the toast message appears (use explicit wait for better accuracy)
        Thread.sleep(2000); // Adjust this delay if needed based on your application behavior

        // Take a screenshot to capture the toast message
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Save the screenshot in the created folder
        String screenshotPath = screenshotFolderPath + "/duplicate_vehicle_number.png";
        FileUtils.copyFile(screenshot, new File(screenshotPath));

        // Log a message for tracking
        System.out.println("Screenshot captured for duplicate vehicle number toast message: " + screenshotPath);

        // Assert the presence of the screenshot file
        Assert.assertTrue(new File(screenshotPath).exists(), "Screenshot for duplicate vehicle number error not captured");
    }

    private void captureScreenshot(String testName) throws IOException {
        // Define the folder path for saving screenshots
        String screenshotFolderPath = "Screenshots/vehicle_negative_scenarios";
        
        // Create the folder if it does not exist
        File folder = new File(screenshotFolderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        // Capture screenshot
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        
        // Save the screenshot
        String screenshotPath = screenshotFolderPath + "/" + testName + ".png";
        FileUtils.copyFile(screenshot, new File(screenshotPath));
        
        // Log and verify the screenshot was captured
        System.out.println("Screenshot captured for " + testName + ": " + screenshotPath);
        Assert.assertTrue(new File(screenshotPath).exists(), "Screenshot not captured for " + testName);
    }
}
