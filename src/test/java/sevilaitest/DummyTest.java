package sevilaitest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public class DummyTest {

    @BeforeSuite
    public void setup() throws Throwable {
        System.out.println("Inside @BeforeSuite of Login_Test");
        
    }

    @Test
    public void verifySuiteExecution() {
        System.out.println("TestNG suite is running!");
    }
   
}
