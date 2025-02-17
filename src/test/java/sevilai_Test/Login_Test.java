package sevilai_Test;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Login_Test {
    protected static WebDriver driver; // Static driver to persist across all tests
    protected static WebDriverWait wait;

    @BeforeSuite
    public void setup() throws Throwable {
        if (driver == null) { // Prevent multiple browser instances
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.get("https://staging.sevilaitech.app/");
            wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            WebElement homeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Sevilai Transport')]")));
            Assert.assertTrue(homeElement.isDisplayed(), "Login page is not displayed.");

            performLogin();
        }
    }

    public void performLogin() throws Throwable {
        wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Login process
        WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Username']")));
        usernameField.sendKeys("saraswathi.m@kaditinnovations.com");

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Password']")));
        passwordField.sendKeys("97903450");

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[text()='Login']")));
        loginButton.click();

        WebElement homeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Home')]")));
        Assert.assertTrue(homeElement.isDisplayed(), "Home page is not displayed.");
    }

    @AfterSuite
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
