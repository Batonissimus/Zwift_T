package Zwift_T_Test1;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Test1 {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeSuite
    public void SetUp() {

        System.setProperty("webdriver.chrome.driver", ".\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 40);

        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);


        driver.get("https://www.zwift.com/");

    }

    @Test
    public void CookiesAcceptPopUp() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("truste-consent-button"))).click();

        var cw = driver.findElements(By.id("truste-consent-buttons")).size() >0;

        Assert.assertEquals(cw,true);

    }

    @Test
    public void EventsPage() {

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
}
