package sevilai_Test;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ContractDriverAddNegativeTest {
	public WebDriver driver;
    public WebDriverWait wait;
    
    @BeforeClass
    

    // Setup method to initialize the driver and perform login
    public void setup() throws Throwable {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("http://staging.sevilaitech.app/");
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Login process
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Username']")));
        usernameField.click();
        usernameField.sendKeys("saraswathi.m@kaditinnovations.com");

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Password']")));
        passwordField.click();
        passwordField.sendKeys("97903450");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Login']")));
        loginButton.click();

        // Validate successful login
        WebElement homeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Home')]")));
        Assert.assertTrue(homeElement.isDisplayed(), "Login was not successful.");
    }


    // DataProvider for negative test cases
    @DataProvider(name = "negativeTestContractDriverAdd")
    public Object[][] provideTestData() {
        return new Object[][] {
            {"", "testemail@gmail.com", "12345678", "20", "TES123", "CDA_TC_01", "Name is required"}, // Empty name
            {"Test Driver", "testemail@gmail.com", "1234", "20", "TES123", "CDA_TC_02", "Enter a valid phone number"}, // Invalid phone number
            {"Test Driver", "testemail@gmail.com", "12312312", "20", "TES123", "CDA_TC_03", "Driver phone number is already exists"}, // Existing phone number
            {"Test Driver", "testemail@gmail.com", "", "20", "TES123", "CDA_TC_04", "Phone Number is required"}, // Missing phone number
            {"Test1", "testemail@gmail.com", "12345678", "20", "TES123", "CDA_TC_05", "Enter a valid name"},
            {"Test Driver", "testemail@gmail.com", "abc", "20", "TES123", "CDA_TC_06", "Enter a valid phone number"},
            {"Test Driver", "testemail", "12345678", "20", "TES123", "CDA_TC_07", "Enter a valid email"},
            {"Test Driver", "testemail@gmail.com", "12345678", "", "TES123", "CDA_TC_08", "Vehicle Capacity is required"},
            {"", "", "", "", "", "CDA_TC_09", "Name is required,Phone Number is required,Driver Type is required,Vehicle Capacity is required,Vehicle Number is required"}, // All fields empty
            {"Test Driver", "testemail@gmail.com", "12345678", "CDA_TC_10", "Vehicle Capacity is required"}, // Vehicle Capacity empty
            {"Test Driver", "testemail@gmail.com", "12345678", "", "TES123", "CDA_TC_11", "Vehicle Number is required"}, // Vehicle Number empty
            {"Test Driver", "testemail@gmail.com", "12345678", "", "", "CDA_TC_12", "Vehicle Number is required, Vehicle Capacity is required"},
            {"Test Driver", "testemail@gmail.com", "12345678", "CDA_TC_13", "Driver Type is required"}, // Driver Type empty
            {"Test Driver", "testemail@gmail.com", "12345678", "20", "SPR878", "CDA_TC_14", "Vehicle Number is already exists"} // Duplicate Vehicle Number
        };
    }

    @Test(dataProvider = "negativeTestContractDriverAdd", description = "Validate error messages for negative scenarios")
    public void validateNegativeTestCases(String driverName, String driverEmail, String phoneNumber, String vehicleCapacity, String vehicleNumber, String testCaseID, String expectedErrorMessage) throws InterruptedException {
    	 WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    	 WebElement homeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Home')]")));
    	 Assert.assertTrue(homeElement.isDisplayed(), "Login was not successful.");
       
        Actions actions = new Actions(driver);

        // Navigate to the Driver tab
        WebElement driverTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 3 of 5')]")));
        actions.moveToElement(driverTabButton).click().perform();

        // Click Add button to open the form
        driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();

        // Fill in the form
        driver.findElement(By.cssSelector("[aria-label='Driver Name']")).sendKeys(driverName);
        driver.findElement(By.cssSelector("[aria-label*='Driver Email']")).sendKeys(driverEmail);
        driver.findElement(By.cssSelector("[aria-label='Phone Number']")).sendKeys(phoneNumber);

        // Handle dropdown selections based on test case
        if (!testCaseID.equals("SDA_TC_13") && !testCaseID.equals("SDA_TC_09")) {
            WebElement dropdownField = driver.findElement(By.xpath("//flt-semantics[text()='Driver Type']"));
            dropdownField.click();
            Thread.sleep(1000);

            WebElement dropdownOption = driver.findElement(By.xpath("//flt-semantics[text()='Contract']"));
            dropdownOption.click();
        }

        if (!testCaseID.equals("SDA_TC_08") && !testCaseID.equals("SDA_TC_12") && !testCaseID.equals("SDA_TC_09")) {
            WebElement vehicleCapacityField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Capacity']")));
            vehicleCapacityField.sendKeys(vehicleCapacity);
        }

        if (!testCaseID.equals("SDA_TC_09") && !testCaseID.equals("SDA_TC_11") && !testCaseID.equals("SDA_TC_12")) {
            WebElement vehicleNumberField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Vehicle Number']")));
            vehicleNumberField.sendKeys(vehicleNumber);
        }

        // Click Add button
        driver.findElement(By.xpath("//flt-semantics[text()='Add']")).click();

        // Validate error messages
        if (testCaseID.equals("SDA_TC_03") || testCaseID.equals("SDA_TC_14")) {
            // Handle toast messages
            WebElement toastMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[aria-label*='" + expectedErrorMessage + "']")));
            String toastMessage = toastMessageElement.getAttribute("aria-label");

            Reporter.log("Expected Toast Message: " + expectedErrorMessage, true);
            Reporter.log("Actual Toast Message: " + toastMessage, true);

            Assert.assertTrue(toastMessage.contains(expectedErrorMessage), "Toast message validation failed for Test Case: " + testCaseID);
        } else {
            // Handle generic field validation errors
            WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'" + expectedErrorMessage + "')]")));
            String actualErrorMessage = errorMessageElement.getText();

            Reporter.log("Expected Error Message: " + expectedErrorMessage, true);
            Reporter.log("Actual Error Message: " + actualErrorMessage, true);

            Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message validation failed for Test Case: " + testCaseID);
        }

        // Navigate back to the homepage
        WebElement crossButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@role='button']")));
        crossButton.click();

        WebElement driverButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Tab 3 of 5')]")));
        actions.moveToElement(driverButton).click().perform();
        Assert.assertTrue(driverButton.isDisplayed(), "Driver page is not displayed.");
        Reporter.log("- PASSED: Driver page is successfully displayed.", true);
    }
}
