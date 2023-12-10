package com.qamission.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.qamission.model.Product;

import java.util.List;

public class SearchResultsPage extends Page {
    private static final String GET_TOTAL_REGEX ="(\\d*)\\sRESULTS\\s";
    private static final String FIRST_LAST_ITEM_INDEX_REGEX = "(\\d*)-(\\d*)";

    private String query;
    private int pageSize;

    private By root = By.cssSelector(".search-page__result");
    private By pageTitle = By.cssSelector("h1.page-title__title");
    private By pagination = By.cssSelector("span.pagination > span");
    private By loadMoreButton = By.cssSelector("div.load-more-button>button");
    private By productInfo = By.cssSelector(".product-tile-group__list__item");
    private By sortDescXpath = By.xpath("//button[@data-option-value='price-desc']");
    private By filters = By.xpath("//*[@class='filter-group__header']");
    private By priceReduction = By.cssSelector("[data-track-filter-type='promotions:Price-Reduction']");
    private By addButtonSelector = By.cssSelector("button.quantity-selector.quantity-selector--update.quantity-selector--horizontal.quantity-selector--product-tile.quantity-selector--add-to-cart.quantity-selector--add-to-list-button");
    private By startNewOrderSelector = By.cssSelector("div.fulfillment-mode-flyout.fulfillment-mode-flyout--active");
    private By pcExpressPickUpSelector = By.cssSelector("button.fulfillment-fork-option-tile.fulfillment-fork-option-tile--instacart");
    private By selectLocationLinkLocator = By.cssSelector("a.store-locator-link.fulfillment-mode-select-location__button");

    private int totalFound;

    public SearchResultsPage(WebDriver driver, String query) {
        super(driver);
        this.query = query;
        if (containsText("We were unable to find results for")) throw new RuntimeException("There is no result page for "+query);
        wait.until(ExpectedConditions.presenceOfElementLocated(root));
        wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle));
        wait.until(ExpectedConditions.presenceOfElementLocated(pagination));
        pageSize = getNumberOfItemsOnPages();
        totalFound = getTotalFound();
    }

    public int getTotalFound() {
        WebElement title = driver.findElement(pageTitle);
        List<String> found = getAllRegExpFrom(title.getText(),GET_TOTAL_REGEX);
        return Integer.parseInt(found.get(1));
    }

    public int getNumberOfItemsOnPages() {
        return getLastItemIndexOnPage() - getFirstItemIndexOnPage() + 1;
    }

    public int getFirstItemIndexOnPage() {
        WebElement paging = getPagingElement();
        String firstIndex = getAllRegExpFrom(paging.getText(),FIRST_LAST_ITEM_INDEX_REGEX).get(1);
        return Integer.parseInt(firstIndex);
    }

    public int getLastItemIndexOnPage() {
        WebElement paging = getPagingElement();
        String lastIndex = getAllRegExpFrom(paging.getText(),FIRST_LAST_ITEM_INDEX_REGEX).get(2);
        return Integer.parseInt(lastIndex);
    }



    public void loadMore() {
        wait.until(ExpectedConditions.presenceOfElementLocated(loadMoreButton));
        WebElement button = driver.findElement(loadMoreButton);
        Point p = button.getLocation();
        scrollToPoint(p);
        scrollByPixels(-250);
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(pagination));
        wait.until(ExpectedConditions.visibilityOfElementLocated(pagination));
    }



    public void loadMore(int pages) {
        for (int i=0; i < pages; i++) loadMore();
    }

    public void loadAllPages() {
        int pages = (totalFound-pageSize)/pageSize + (totalFound%pageSize > 0 ? 1 : 0);
        loadMore(pages);
        scrollToTheTop();
    }

    public void sortDesc() {
        scrollToTheTop();
        WebElement button = driver.findElement(sortDescXpath);
        button.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(pagination));
        wait.until(ExpectedConditions.visibilityOfElementLocated(pagination));
    }

    public SearchResultsPage filterByPriceReduction() {
        wait.until(ExpectedConditions.elementToBeClickable(filters));
        if (isAisleExpended()) scrollByPixels(200);
        WebElement el = driver.findElements(filters).get(1);
        el.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(priceReduction));
        wait.until(ExpectedConditions.elementToBeClickable(priceReduction));
        driver.findElement(priceReduction).click();
        scrollToTheTop();
        wait.until(ExpectedConditions.presenceOfElementLocated(pagination));
        wait.until(ExpectedConditions.visibilityOfElementLocated(pagination));
        return new SearchResultsPage(driver,query);
    }

    public List<WebElement> getAllFoundProductElements() {
        // TODO need to make custom explicit condition
        long start = System.currentTimeMillis();
        while(!isAllLoaded()) {
            pause(1000);
            if ((System.currentTimeMillis() - start)/1000 > 5) throw new TimeoutException("Unable to read all products from the search result page within 5 sec.");
        }
        return driver.findElements(productInfo);
    }

    public List<Product> getAllFoundProducts() {
        List<WebElement> els = getAllFoundProductElements();
        ProductInfoParser parser = new ProductInfoParser(driver);
        return parser.getProducts(els);
    }

    public boolean isAllLoaded() {
        return driver.findElements(productInfo).size() >= totalFound;
    }

    public String getQuery() {
        return query;
    }

    public int getPageSize() {
        return pageSize;
    }

    public WebElement getProductById(String productId) {
        String xpathStr = "//div[@data-track-product-id='" + productId + "']/..";
        By xpath = By.xpath(xpathStr);
        List<WebElement> result = driver.findElements(xpath);
        if (result.isEmpty()) throw new RuntimeException("Product with id "+productId+" is not found.");
        if (result.size()>1) throw new RuntimeException("More than one product found on the page");
        return result.get(0);
    }

    public Product getProdcutInfoByIndex(int productNumberOnPage) {
        if (productNumberOnPage < 1 ) throw new RuntimeException("Product number on page should be more than 0");
        String xpathStr = "//div[@data-track-product-index='"+productNumberOnPage+"']/../..";
        By xpath = By.xpath(xpathStr);
        List<WebElement> els = driver.findElements(xpath);
        WebElement el = els.get(0);
        ProductInfoParser parser = new ProductInfoParser(driver);
        return parser.parseSingleProductElement(el);
    }

    public ShoppingCart addItemToShoppingCart(int productNumberOnPage) {
        if (productNumberOnPage <1 ) throw new RuntimeException("Product number should be more than 0");
        wait.until(ExpectedConditions.presenceOfElementLocated(addButtonSelector));
        wait.until(ExpectedConditions.elementToBeClickable(addButtonSelector));
        driver.findElements(addButtonSelector).get(productNumberOnPage-1).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(startNewOrderSelector));
        wait.until(ExpectedConditions.elementToBeClickable(pcExpressPickUpSelector));
        driver.findElement(pcExpressPickUpSelector).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(selectLocationLinkLocator));
        wait.until(ExpectedConditions.elementToBeClickable(selectLocationLinkLocator));
        driver.findElement(selectLocationLinkLocator).click();
        LocationSelector locationSelector = new LocationSelector(driver);
        locationSelector.searchLocation("dufferin");
        locationSelector.pickUpLocation(2);
        return new ShoppingCart(driver);
    }

    private boolean isAisleExpended() {
        WebElement aisle = driver.findElements(filters).get(0);
        String exp = aisle.findElement(By.xpath("./button")).getAttribute("aria-expanded");
        return exp.contains("true");
    }






    private WebElement getPagingElement() {
        return driver.findElement(pagination);
    }









}
