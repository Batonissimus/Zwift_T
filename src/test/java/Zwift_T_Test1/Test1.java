package Zwift_T_Test1;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import java.time.Duration;


public class Test1 {

    private WebDriver driver;


    @BeforeSuite
    public void SetUp() {

        WebDriverManager.chromedriver().browserVersion("76.0.3809.126").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");

        driver = new ChromeDriver(options);
        driver.get("https://www.zwift.com/");


    }

    @Test
    public void CookiesAcceptPopUp() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("truste-consent-button"))).click();

        var cw = driver.findElements(By.id("truste-consent-buttons")).size() >0;

        Assert.assertEquals(cw,false);

    }

    @Test
    public void EventsPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.findElement(By.xpath("//*[@aria-label='Open side navigation']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Events")));
        driver.findElement(By.linkText("Events")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='map-sport-type']")));
        String sportType = driver.findElement(By.xpath("//*[@class='map-sport-type']")).getText();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='filter-toggle']")));
        driver.findElement(By.xpath("//*[@class='filter-toggle']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='filter-body']")));

        if (sportType.equals("CYCLING"))
        {
            driver.findElement(By.xpath("//*[@class='buttons' and text()='Running']")).click();
        }
        else
        {
            driver.findElement(By.xpath("//*[@class='buttons default' and text()='Cycling']")).click();
        }

        driver.findElement(By.xpath("//*[@class='buttons' and text()='C']")).click();
        driver.findElement(By.xpath("//*[@class='buttons' and text()='Night']")).click();
        driver.findElement(By.xpath("//*[@class='apply-button']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='events-column']")));

        if (driver.findElement(By.xpath("//*[@class='header-title']")).getText().equals("No results."))
        {
            Assert.assertEquals(driver.findElement(By.xpath("//*[@class='header-title']")).getText(),"No results.",
                    "No results expected");
        }
        else
        if (sportType.equals("CYCLING"))
        {
            Assert.assertEquals(driver.findElement(By.xpath("//*[@class='map-sport-type']")).getText(),"RUNNING",
                    "Expected sport type is wrong");
        }
        else
        {
            Assert.assertEquals(driver.findElement(By.xpath("//*[@class='map-sport-type']")).getText(),"CYCLING",
                    "Expected sport type is wrong");
        }
    }

    @AfterSuite
    void quite()
    {
        driver.quit();
    }
}
