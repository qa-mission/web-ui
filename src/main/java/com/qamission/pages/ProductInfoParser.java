package com.qamission.pages;

import com.qamission.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.qamission.model.Price;
import com.qamission.model.Unit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProductInfoParser extends Page {
    private static Logger log = LoggerFactory.getLogger(ProductInfoParser.class);

    private List<WebElement> products = new ArrayList<WebElement>();;

    private String productId = "./div[@data-track-product-id]";
    private String productIndex = "//div[@data-track-product-index]";
    private String salesText = "//div[contains(@class,'product-badge__icon__text product-badge__icon__text--sale')]";
    private String salesExpire = "//div[contains(@class,'product-badge__icon__expiry product-badge__icon__expiry--sale')]";
    private String productEyeBrow = "//div[contains(@class,'product-tile__eyebrow')]";
    private String productBrand = "//span[contains(@class,'product-name__item product-name__item--brand')]";
    private String productName = "//span[contains(@class,'product-name__item product-name__item--name')]";
    private String productSize = "//span[contains(@class,'product-name__item product-name__item--package-size')]";
    private String productText = "//div[contains(@class,'product-badge__text product-badge__text--product-tile')]";

    private String sellingPriceValue = "//span[contains(@class,'price__value selling-price-list__item__price selling-price-list__item__price--now-price__value')]";
    private String sellingPriceType = "//span[contains(@class,'price__type selling-price-list__item__price selling-price-list__item__price--now-price__type')]/span";

    private String sellingPriceWasValue = "//span[contains(@class,'price__value selling-price-list__item__price selling-price-list__item__price--was-price__value')]";
    private String sellingPriceWasType = "//span[contains(@class,'price__type selling-price-list__item__price selling-price-list__item__price--was-price__type')]/span";

    private Unit sellingPriceUnit = Unit.EA;

    private String comparisonPriceValue = "//span[contains(@class,'price__value comparison-price-list__item__price__value')]";
    private String comparisonPriceUnit = "//span[contains(@class,'price__unit comparison-price-list__item__price__unit')]";

    public ProductInfoParser(WebDriver driver) {
        super(driver);
    }

    public List<Product> getProducts(List<WebElement> prodInfo) {
        products.addAll(prodInfo);
        return parse(products);
    }

    public List<Product> getProducts(WebElement prodInfo) {
        products.add(prodInfo);
        return parse(products);
    }

    public List<Product> parse(List<WebElement> products) {
        ArrayList<Product> result = new ArrayList<>();
        for (WebElement productRoot : products) result.add(parseSingleProductElement(productRoot));
        return result;
    }

    public Product parseProductById (String productId) {
        String root  = "//div[@data-track-product-id='" + productId + "']/..";
        String prodId = productId;
        String prodIndex = driver.findElement(By.xpath(root+productIndex)).getAttribute("data-track-product-index");
        int index = Integer.parseInt(prodIndex);
        log.info("its index is: "+index);
        String sales = getValue(root+salesText);
        String salesExpires = getValue(root+ salesExpire);
        //String prodEyeBrow = getValue(root+productEyeBrow);
        String prodBrand = getValue(root+productBrand);
        String prodName = getValue(root+productName);
        String prodSize = getValue(root+productSize);
        String prodText = getProductText(root + productText);
        Price salesPriceNow = parseSalesPrice(root,sellingPriceValue,sellingPriceType);
        Price salesPriceWas = parseSalesPrice(root,sellingPriceWasValue,sellingPriceWasType);
        List<Price> comparisonPrices = parseComparisonPrices(root,comparisonPriceValue,comparisonPriceUnit);
        //return new Product(prodId,index,sales,salesExpires,prodEyeBrow,prodBrand,prodName,prodSize,prodText,salesPriceNow,salesPriceWas,comparisonPrices);
        //return new Product(prodId,index,prodBrand,prodName,prodSize,salesPriceNow,comparisonPrices);
        return new Product(prodId,index, sales, salesExpires,prodBrand,prodName,prodSize,prodText,salesPriceNow,salesPriceWas, comparisonPrices);
    }

    public Product parseSingleProductElement(WebElement root) {
        String prodId = root.findElement(By.xpath(productId)).getAttribute("data-track-product-id");
        log.info("Parsing product: "+prodId);
        return parseProductById(prodId);
    }

    private String getValue(String xpath) {
        return getValue(xpath,0);
    }

    private String getValue(String xpath, int index) {
        List<WebElement> r = getElementsByXpath(xpath);
        if (r.isEmpty()) return "";
        if (index > r.size()-1) throw new RuntimeException(index + " is out of boundaries from results found by " + xpath);
        return r.get(index).getText();
    }

    private Price parseSalesPrice(String rootXpath, String valueXpath, String typeXpath) {
        String value = getValue(rootXpath+valueXpath);
        if (value.isEmpty()) return null;// TODO need to have kind of empty price object.
        value = getDollarValue(value.replace(",",""));
        String type = getValue(rootXpath+typeXpath);
        double dollars = Double.parseDouble(value);
        return new Price(dollars,type, Unit.EA);
    }

    private List<Price> parseComparisonPrices (String rootXpath, String valueXpath, String unitXpath) {
        List<WebElement> values = getElementsByXpath(rootXpath+valueXpath);
        if (values.isEmpty()) throw new RuntimeException("Unable to find comparison price for "+(rootXpath+valueXpath));
        List<WebElement> units = getElementsByXpath(rootXpath+unitXpath);
        if (units.isEmpty()) throw new RuntimeException("Unable to find comparison units for "+(rootXpath+valueXpath));
        if (values.size() != units.size()) throw new RuntimeException("Extracted number of comp. prices and types is not equal");
        ArrayList<Price> comparionPrices = new ArrayList<>();
        int howMany = values.size();
        for (int i=0; i < howMany; i++) {
            String v = getDollarValue(values.get(i).getText()).replace(",","");
            Unit u = getUnit(units.get(i).getText());
            double dollars = Double.parseDouble(v);
            Price p = new Price(dollars,"",u);
            comparionPrices.add(p);
        }
        return comparionPrices;
    }

    private List<WebElement> getElementsByXpath(String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    private Unit getUnit(String strValue) {
        if (strValue.contains("kg")) return Unit.KG;
        else if (strValue.contains("lb")) return Unit.LB;
        else if (strValue.contains("100g")) return Unit.HNDGR;
        else if (strValue.contains("ea")) return Unit.EA;
        else if (strValue.contains("100ml")) return Unit.HNDML;
        else if (strValue.contains("1ml")) return Unit.ONEML;
        else if (strValue.contains("0ml")) return Unit.ZEROML;
        else if (strValue.contains("1g")) return Unit.ONEGR;
        else if (strValue.contains("0g")) return Unit.ZEROGR;
        else if (strValue.contains("1pk")) return Unit.PKG;
        else throw new RuntimeException("Parsed unit value "+strValue+" is undefined.");
    }

    private String getDollarValue(String value) {
        if (value.startsWith("$")) return value.substring(1);
        return value;
    }

    private String getProductText(String xpath) {
        List<WebElement> r = getElementsByXpath(xpath);
        if (r.isEmpty()) return "";
        if (r.size()>1) return r.get(r.size()-1).getText();
        return r.get(0).getText();
    }




}
