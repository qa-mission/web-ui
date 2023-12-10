package com.qamission.pages;

import com.qamission.utils.MathUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ShoppingCart extends Page{

    private By miniCartButtonSelector = By.cssSelector("button.desktop-mini-cart-button");
    private By viewCartLinkSelector = By.cssSelector("a.cart-summary__view-cart__link");
    private By cartItem = By.cssSelector("li.cart-entry-list__item");
    private By cartConten = By.cssSelector("div.grocery-cart-products__content");
    private By itemName = By.cssSelector("div.cart-entry__content.cart-entry__content--product-name");
    private By itemPrice = By.cssSelector("div.cart-entry__content.cart-entry__content--product-price>span>span");

    public ShoppingCart(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(miniCartButtonSelector));
        driver.findElement(miniCartButtonSelector).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(viewCartLinkSelector));
        wait.until(ExpectedConditions.elementToBeClickable(viewCartLinkSelector));
        driver.findElement(viewCartLinkSelector).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(cartConten));
    }

    public int size() {
        wait.until(ExpectedConditions.presenceOfElementLocated(cartItem));
        return driver.findElements(cartItem).size();
    }

    public String getProductNameAt(int place) {
        if (place < 1 ) throw new RuntimeException("Item place for name in cart should be more than 0");
        WebElement item = driver.findElements(itemName).get(place-1);
        return item.getText();
    }

    public double getProductPriceAt(int place) {
        if (place < 1 ) throw new RuntimeException("Item place for prod price in cart should be more than 0");
        WebElement item = driver.findElements(itemPrice).get(place-1);
        String strPrice = item.getText().substring(1);
        double price = MathUtils.roundDouble(Double.parseDouble(strPrice),2);
        return price;
    }


}
