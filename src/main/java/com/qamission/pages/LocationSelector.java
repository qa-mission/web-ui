package com.qamission.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LocationSelector extends Page {
    private By storeLocatorContentLocator = By.cssSelector("div.store-locator-content");
    private By locationSearchField = By.cssSelector("input.location-search__search__input");
    private By storeLocatorContentList = By.cssSelector("ul.location-list");
    //private By pickupLocationButtonSelector = By.cssSelector("button.location-set-store__button.location-set-store__button--is-not-current-location.location-set-store__button--is-shoppable.location-set-store__button--is-store.location-set-store__button--is-not-this-banner");
    private By pickupLocationButtonSelector = By.cssSelector("div.location-set-store.location-set-store--location-list-item-actions");
    private By continueButton = By.cssSelector("a.fulfillment-location-confirmation__actions__button");
    private By openLocationButtonSelector = By.cssSelector("button.fulfillment-mode-button");
    private By locationDetailsLinkSelector = By.cssSelector("a.pickup-location-details__link");
    private By storeFlyerSelector = By.cssSelector("a.location-details-contact__flyer__link");
    private By addressLocationSelector = By.cssSelector("address.location-address.location-address--location-details-summary-info");

    public LocationSelector(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.presenceOfElementLocated(storeLocatorContentLocator));
    }

    public void searchLocation(String locationTofind) {
        WebElement field = driver.findElement(locationSearchField);
        field.sendKeys(locationTofind);
        field.sendKeys(Keys.ARROW_DOWN);
        field.sendKeys(Keys.ENTER);pause(500);
        field.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.presenceOfElementLocated(pickupLocationButtonSelector));
        wait.until(ExpectedConditions.elementToBeClickable(pickupLocationButtonSelector));
        wait.until(ExpectedConditions.presenceOfElementLocated(storeLocatorContentList));

    }

    public void pickUpLocation(int locationNumber) {
        if (locationNumber < 1 ) throw new RuntimeException("Location number must be more than 0");
        wait.until(ExpectedConditions.presenceOfElementLocated(pickupLocationButtonSelector));
        wait.until(ExpectedConditions.elementToBeClickable(pickupLocationButtonSelector));
        WebElement el = driver.findElements(pickupLocationButtonSelector).get(locationNumber-1);
        el.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(continueButton));
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        driver.findElement(continueButton).click();
    }

    public void openLocationDetails() {
        wait.until(ExpectedConditions.presenceOfElementLocated(openLocationButtonSelector));
        wait.until(ExpectedConditions.elementToBeClickable(openLocationButtonSelector));
        driver.findElement(openLocationButtonSelector).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(locationDetailsLinkSelector));
        wait.until(ExpectedConditions.elementToBeClickable(locationDetailsLinkSelector));
        driver.findElements(locationDetailsLinkSelector).get(1).click();
    }

    public StoreFlyer openStoreFlyer() {
        wait.until(ExpectedConditions.presenceOfElementLocated(storeFlyerSelector));
        driver.findElement(storeFlyerSelector).click();
        return new StoreFlyer(driver);
    }

    public String getAddress() {
        wait.until(ExpectedConditions.presenceOfElementLocated(addressLocationSelector));
        return driver.findElement(addressLocationSelector).getText();
    }

}
