package com.qamission.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StoreFlyer extends Page {
    private By flyerLocationInfoSelector = By.cssSelector("div.flyers-header__location-info__location-label>span");
    private By flyerHeaderSelector = By.cssSelector("h1.flyers-header__header>span");
    public StoreFlyer(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(flyerHeaderSelector));
    }
    public String getFlyerForLocation() {
        wait.until(ExpectedConditions.presenceOfElementLocated(flyerLocationInfoSelector));
        return driver.findElement(flyerLocationInfoSelector).getText();
    }


}
