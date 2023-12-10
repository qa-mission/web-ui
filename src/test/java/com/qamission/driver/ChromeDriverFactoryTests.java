package com.qamission.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qamission.BaseUnitTest;
import com.qamission.config.Config;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class ChromeDriverFactoryTests extends BaseUnitTest {
    private WebDriver driver;

    @Test
    public void startChromeDriver() {
        driver = ChromeDriverFactory.createChromeDriver();
        Assert.assertNotNull(driver);
        driver.get("https://www.loblaws.ca/");
        driver.manage().window().maximize();
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofMillis(Config.getConfig().getPauseInTest()));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".logo")));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("site-content")));
        WebElement element = driver.findElement(By.cssSelector("div.booking-selector>a>span>span"));
        Assert.assertEquals(element.getText(), "Store Locator".toUpperCase());
    }
    @AfterMethod
    public void tearDownTestMethod() {
        driver.quit();
    }


}
