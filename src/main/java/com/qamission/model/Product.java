package com.qamission.model;

import com.qamission.utils.MathUtils;

import java.util.List;

public class Product implements Comparable<Product> {
    private String id;
    private int productIndex;
    private String salesText;
    private String salesEnds;
    //private String productEyeBrow;
    private String productBrand;
    private String productName;
    private String productSize;
    private String productText;
    private Price sellingPriceNow; // unit always Unit.EA
    private Price sellingPiceWas; // unit always Unit.EA
    private List<Price> comparisonPrices;

    private double saveValue;

    private boolean onSale=false;
    /*
    public Product(String id, int productIndex, String salesText, String salesEnds, String productEyeBrow, String productBrand, String productName, String productSize, String productText, Price sellingPriceNow, Price sellingPiceWas, List<Price> comparisonPrices) {
        this.id = id;
        this.productIndex = productIndex;
        this.salesText = salesText;
        this.salesEnds = salesEnds;
        this.productEyeBrow = productEyeBrow;
        this.productBrand = productBrand;
        this.productName = productName;
        this.productSize = productSize;
        this.productText = productText;
        this.sellingPriceNow = sellingPriceNow;
        this.sellingPiceWas = sellingPiceWas;
        this.comparisonPrices = comparisonPrices;
    }
     */

    public Product(String id, int productIndex, String salesText, String salesEnds, String productBrand, String productName, String productSize, String productText, Price sellingPriceNow, Price sellingPiceWas, List<Price> comparisonPrices) {
        this.id = id;
        this.productIndex = productIndex;
        this.salesText = salesText;
        if (salesText.toLowerCase().contains("sale")) onSale = true;
        this.salesEnds = salesEnds;
        this.productBrand = productBrand;
        this.productName = productName;
        this.productSize = productSize;
        this.productText = productText;
        this.sellingPriceNow = sellingPriceNow;
        this.sellingPiceWas = sellingPiceWas;
        this.comparisonPrices = comparisonPrices;
        if (onSale) {
            if (productText.contains("$")) saveValue = MathUtils.roundDouble(Double.parseDouble(productText.split("\\$")[1]),2);
            else saveValue = 0;
        }
    }

    @Override
    public int compareTo(Product p) {
        return this.sellingPriceNow.compareTo(p.getSallingPriceNow());
    }

    public String getId() {
        return id;
    }

    public int getProductIndex() {
        return productIndex;
    }

    public String getSalesText() {
        return salesText;
    }

    public String getSalesEnds() {
        return salesEnds;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductSize() {
        return productSize;
    }

    public String getProductText() {
        return productText;
    }

    public Price getSallingPriceNow() {
        return sellingPriceNow;
    }

    public Price getSallingPiceWas() {
        return sellingPiceWas;
    }

    public List<Price> getComparisonPrices() {
        return comparisonPrices;
    }

    public boolean isOnSale() {
        return onSale;
    }

    public double getSaveValue() {
        return saveValue;
    }
}
