package com.qamission.pages;

import com.qamission.BaseUnitTest;
import com.qamission.model.Product;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ShoppingCartTests extends BaseUnitTest {
    @Test
    public void addItemToShoppingCart() {
        LoblawsPage loblawsPage = new LoblawsPage(driver);
        SearchResultsPage resultsPage = loblawsPage.search("apples");
        Product productInCart = resultsPage.getProdcutInfoByIndex(1);
        ShoppingCart cart = resultsPage.addItemToShoppingCart(1);
        String inCartProdName = cart.getProductNameAt(1);
        double inCartProdPrice = cart.getProductPriceAt(1);
        Assert.assertEquals(inCartProdName, productInCart.getProductName(),"Names should match");
        Assert.assertEquals(inCartProdPrice,productInCart.getSallingPriceNow().getValue(),0.001);

    }
}
