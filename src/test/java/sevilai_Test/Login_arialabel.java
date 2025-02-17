package sevilai_Test;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Login_arialabel {
	WebDriver driver;

	@BeforeMethod
	    public void setup() throws Throwable {
			/*
			 * WebDriverManager.chromedriver().setup(); driver = new ChromeDriver();
			 */
	    	WebDriverManager.edgedriver().setup();  // Set up the Firefox WebDriver
	        driver = new ChromeDriver();
	        driver.get("https://sevilaitransport.pages.dev/#/login");
	        Thread.sleep(4000);
	        // Example test - check the page title
	        String title = driver.getTitle();
	        System.out.println("Page Title: " + title);
	    }


@Test
	    public void testLogin() throws Throwable {
	    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
	    	WebElement usernameField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@aria-label='Username']")));
	    	usernameField.click();
	    	
	    	usernameField.sendKeys("saranya@kaditinnovations.com");
	    	Thread.sleep(1000);
}
}
	    	/*WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@id='flt-semantic-node-6']/input")));
	    	passwordField.click();
	    	passwordField.sendKeys("12345623");
	    	WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//flt-semantics[@id='flt-semantic-node-4']/flt-semantics-container/flt-semantics[9]")));
	    	loginButton.click();
			
			 * JavascriptExecutor js = (JavascriptExecutor) driver;
			 * js.executeScript("arguments[0].click();", loginButton);
			 
	    	Thread.sleep(4000);
	    	 try {
	             WebElement profileElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//flutter-view")));
	             if (profileElement.isDisplayed()) {
	                 System.out.println("Login successful!");
	             } else {
	                 System.out.println("Login failed!");
	             }
	         } catch (Exception e) {
	             System.out.println("Login failed! The homepage element was not found.");
	         }
	     }
	       
	    
	    
	    // Closing the browser after each test
	    @AfterMethod
	    public void tearDown() {
	        if (driver != null) {
	            driver.quit();
	        }
	    }
	}


}
*/