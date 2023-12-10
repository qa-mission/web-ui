package com.qamission.model;

import org.openqa.selenium.WebElement;
import com.qamission.BaseUnitTest;
import com.qamission.pages.LoblawsPage;
import com.qamission.pages.ProductInfoParser;
import com.qamission.pages.SearchResultsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ProductInfoParserTests extends BaseUnitTest {
    private LoblawsPage loblawsPage;

    @BeforeMethod
    public void setUpTestMethod() {
        loblawsPage = new LoblawsPage(driver);
    }

    @Test
    public void findSingleProduct1() {
        String prodId = "20083526001_KG";
        SearchResultsPage resultsPage = loblawsPage.search("apples");
        WebElement prod = resultsPage.getProductById(prodId);
        Assert.assertTrue(prod != null);
    }

    @Test
    public void findSingleProduct2() {
        String prodId = "20725795001_EA";
        SearchResultsPage resultsPage = loblawsPage.search("apples");
        WebElement prod = resultsPage.getProductById(prodId);
        Assert.assertTrue(prod != null);
    }

    @Test
    public void parseProductId() {
        String prodId = "20725795001_EA";
        SearchResultsPage resultsPage = loblawsPage.search("apples");
        ProductInfoParser parser = new ProductInfoParser(driver);
        Product pr = parser.parseProductById(prodId);
        Assert.assertEquals(pr.getId(),prodId);

    }

    @Test
    public void parseProductSales1() {
        String prodId = "20606349001_EA";
        SearchResultsPage resultsPage = loblawsPage.search("apples");
        WebElement prods = resultsPage.getProductById(prodId);
        Assert.assertTrue(prods != null);
        ProductInfoParser parser = new ProductInfoParser(driver);
        Product pr = parser.getProducts(prods).get(0);
        Assert.assertEquals(pr.getId(),prodId);
    }

    @Test
    public void parseProductSales2() {
        String prodId = "20632238001_EA";
        SearchResultsPage resultsPage = loblawsPage.search("apples");
        WebElement prods = resultsPage.getProductById(prodId);
        ProductInfoParser parser = new ProductInfoParser(driver);
        Product pr = parser.getProducts(prods).get(0);
        Assert.assertEquals(pr.getId(),prodId);
    }

    @Test
    public void parseProductSales3() {
        String prodId = "20613154001_EA";
        SearchResultsPage resultsPage = loblawsPage.search("apples");
        WebElement prods = resultsPage.getProductById(prodId);
        ProductInfoParser parser = new ProductInfoParser(driver);
        List<Product> list = parser.getProducts(prods);
        Assert.assertFalse(list.isEmpty());
        Product p = list.get(0);
        Assert.assertEquals(p.getProductBrand(),"PC Organics");
        Assert.assertEquals(p.getId(),prodId);
        Assert.assertEquals(p.getComparisonPrices().get(0).getValue(),5.90);
    }

    @Test
    public void parseShortListOfProducts() {
        SearchResultsPage resultsPage = loblawsPage.search("asdf");
        List<WebElement> foundElements = resultsPage.getAllFoundProductElements();
        ProductInfoParser parser = new ProductInfoParser(driver);
        List<Product> prods = parser.parse(foundElements);
        Assert.assertEquals(prods.size(),resultsPage.getLastItemIndexOnPage());
    }

    @Test
    public void parseOnePageListOfProducts() {
        SearchResultsPage resultsPage = loblawsPage.search("milk");
        List<WebElement> foundElements = resultsPage.getAllFoundProductElements();
        ProductInfoParser parser = new ProductInfoParser(driver);
        List<Product> prods = parser.parse(foundElements);
        Assert.assertEquals(prods.size(),resultsPage.getLastItemIndexOnPage());
    }

    @Test
    public void parsAllApples() {
        SearchResultsPage resultsPage = loblawsPage.search("apples");
        resultsPage.loadAllPages();
        List<WebElement> foundElements = resultsPage.getAllFoundProductElements();
        ProductInfoParser parser = new ProductInfoParser(driver);
        List<Product> prods = parser.parse(foundElements);
        Assert.assertEquals(prods.size(),resultsPage.getLastItemIndexOnPage());
    }


}
