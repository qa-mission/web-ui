package com.qamission.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Catalogue {
    List<Product> products;
    HashMap<String,Product> container;

    public Catalogue(List<Product> products) {
        this.products = products;
        container = fillContainer(this.products);
    }

    private HashMap<String, Product> fillContainer(List<Product> products) {
        HashMap<String,Product> hashMap =  new HashMap<>();
        for (Product p : products) {
            hashMap.put(p.getId(),p);
        }
        return hashMap;
    }

    public List<Product> sortProductsDesc() {
        Collections.sort(products);
        Collections.reverse(products);
        return products;
    }

    public Product getProductById(String id) {
        if (container.containsKey(id)) return container.get(id);
        throw new RuntimeException("Catalogue does not have product with "+id);
    }
}
