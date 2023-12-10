package com.qamission;

import org.openqa.selenium.WebDriver;
import com.qamission.driver.ChromeDriverFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseUnitTest {
    protected static WebDriver driver = null;
    @BeforeSuite
    public void setUpSuite() {
        String env = System.getProperty("os.name");
        if (env.toLowerCase().contains("linux")) System.setProperty("env","linux");
        else System.setProperty("env","windows");
        driver = ChromeDriverFactory.createChromeDriver();
    }


    @AfterSuite
    public void tearDownSuite() {
        driver.quit();
    }

}
