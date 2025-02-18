package sevilaitest;





	import java.time.Duration;
	import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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

	public class TripAdminEditNegativeTest {
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
	    @DataProvider(name = "negativeTestTripadminEdit")
	    public Object[][] provideTestData() {
	        return new Object[][] {
	            {"", "admin@gmail.com", "12345600", "TAE_TC_01", "Name is required"}, // Empty name
	            {"Test1", "admin@gmail.com", "12345600", "TAE_TC_02", "Enter a valid name"}, // Invalid name
	            {"@#$@", "admin@gmail.com", "12345600", "TAE_TC_03", "Enter a valid name"}, // Empty name
	            {"Tripadmin", "abc", "12345600", "TAE_TC_04", "Enter a valid email"}, // Invalid email
	            {"Tripadmin", "saraswathi.m@kaditinnovations.com", "12345600", "TAE_TC_05", "Admin email is already exists"}, // Existing email
	            {"Tripadmin", "admin@gmail.com", "", "TAE_TC_06", "Phone Number is required"}, // Missing phone number
	            {"Tripadmin", "admin@gmail.com", "1234", "TAE_TC_07", "Enter a valid phone number"}, // Invalid phone number
	            {"Tripadmin", "admin@gmail.com", "1234567891", "TAE_TC_08", "Enter a valid phone number"}, // Invalid phone number
	            {"Tripadmin", "admin@gmail.com", "12345678", "TAE_TC_09", "Admin Phone number is already exists"}, // Duplicate phone number
	            {"", "", "","", "TAE_TC_10", "Name is required, Email is required, Phone Number is required,Admin Type is required"} // All fields empty
	        };
	    }
	    public void clearFieldData(WebElement element) {
	        try {
	            element.sendKeys(Keys.CONTROL + "a"); // Select all text
	            element.sendKeys(Keys.BACK_SPACE);   // Clear selected text
	            //Reporter.log("Field data cleared successfully.", true);
	        } catch (Exception e) {
	            Reporter.log("Failed to clear field data: " + e.getMessage(), true);
	            throw e;
	        }
	    }

	    @Test(dataProvider = "negativeTestTripadminEdit", description = "Validate error messages for negative scenarios")
	    public void validateNegativeTestCases(String name, String email, String phoneNumber, String testCaseID, String expectedErrorMessage) throws InterruptedException {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	        Actions actions = new Actions(driver);

	        // Navigate to the Superadmin tab
	        WebElement superadminTabButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Tab 5 of 5')]")));
	        actions.moveToElement(superadminTabButton).click().perform();
	        WebElement TripAdmin = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Tab 2 of 2')]")));
			TripAdmin.click();
			
	        WebElement
			  refresh=wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector
			  ("[aria-label='Refresh']"))); refresh.click();
			 
			WebElement searchElement = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[aria-label='Search...']")));
			searchElement.click();
			searchElement.sendKeys("test@");
			WebElement Editbutton = wait
					.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label='Edit']")));
			Editbutton.click();

	      
	        // Fill in the form
	        WebElement Adminname=driver.findElement(By.cssSelector("[aria-label='Admin Name']"));
	        Adminname.sendKeys(name);
	        clearFieldData(Adminname); // Clear old data
	        WebElement Adminemail=driver.findElement(By.cssSelector("[aria-label='Admin Email']"));
	        Adminemail.sendKeys(email);
	        clearFieldData(Adminemail);
	        WebElement Adminphonenumber=driver.findElement(By.cssSelector("[aria-label='Phone Number']"));
	        Adminphonenumber.sendKeys(phoneNumber);
	        clearFieldData(Adminphonenumber);
	        if (!testCaseID.equals("SAE_TC_10")) {
	        WebElement dropdownField =wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Admin Type']"))); // Use the appropriate locator
	        dropdownField.click(); // Click to open the dropdown
	        Thread.sleep(1000); // Replace with WebDriverWait for better handling
	        WebElement dropdownOption = driver.findElement(By.xpath("//flt-semantics[text()='TRIP_ADMIN']")); // Replace 'OptionText' with the actual text of the option
	        dropdownOption.click(); 
	        }

	        // Click Save or Add button
	        driver.findElement(By.xpath("//flt-semantics[text()='Update']")).click();

	        // Validate error messages
	        if (testCaseID.equals("SAE_TC_05") || testCaseID.equals("SAE_TC_09")) {
	            // Handle toast messages for duplicate entries
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

	        WebElement superadminButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Tab 5 of 5')]")));
	        actions.moveToElement(superadminButton).click().perform();
	        Assert.assertTrue(superadminButton.isDisplayed(), "Tripadmin page is not displayed.");
	        Reporter.log("- PASSED: Tripadmin page is successfully displayed.", true);
	    }
	}









