package com.qamission.assignment;

import com.qamission.model.Catalogue;
import com.qamission.model.Product;
import com.qamission.pages.ProductInfoParser;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Task1 extends BaseTest{
    ProductInfoParser parser;

    @BeforeMethod
    public void setUpTestMethod() {
        parser = new ProductInfoParser(driver);
    }

    @Test
    public void testProductsSorted() {
        resultsPage = loblawsPage.search(query);
        resultsPage.loadAllPages();
        productList = resultsPage.getAllFoundProducts();
        resultsPage.sortDesc();
        Catalogue catalogue = new Catalogue(productList);
        catalogue.sortProductsDesc();

        resultsPage.sortDesc();
        resultsPage.loadAllPages();

        Product mostExpenciveProduct = productList.get(0);
        Product mostExpenciveProductFromPage = parser.parseProductById(mostExpenciveProduct.getId());
        Assert.assertEquals(mostExpenciveProductFromPage.getProductIndex(),1,"Most expensive product on the page has index 1");

        Product chepeastProduct = productList.get(resultsPage.getLastItemIndexOnPage()-1);
        Product chepeastProductFromPage = parser.parseProductById(chepeastProduct.getId());
        Assert.assertEquals(chepeastProductFromPage.getProductIndex(),
                resultsPage.getLastItemIndexOnPage(),"Cheapest product on the page has 'last' index");
    }

}
