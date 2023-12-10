package com.qamission.assignment;

import org.openqa.selenium.WebDriver;
import com.qamission.config.Config;
import com.qamission.driver.ChromeDriverFactory;
import com.qamission.model.Product;
import com.qamission.pages.LoblawsPage;
import com.qamission.pages.SearchResultsPage;
import org.testng.annotations.*;

import java.util.List;

public class BaseTest {
    protected static WebDriver driver;
    protected static List<Product> productList;
    protected static String query;
    protected static LoblawsPage loblawsPage;
    protected static SearchResultsPage resultsPage;
    @Parameters({ "query" })
    @BeforeMethod
    public void setUpSuite(@Optional("apples") String searchString) {
        Config.getConfig();
        query = searchString;
        driver = ChromeDriverFactory.createChromeDriver();
        driver.get("https://www.loblaws.ca/");
        loblawsPage = new LoblawsPage(driver);

    }

    @Test
    public void firtstTest() {
        System.out.println("Hello World");
    }

    @AfterMethod
    public void tearDownSuite() {
        driver.quit();
    }
}


