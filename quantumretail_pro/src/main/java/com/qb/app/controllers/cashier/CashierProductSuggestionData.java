/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.cashier;

import com.qb.app.database_crud.ProductCRUD;
import com.qb.app.model.entity.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.concurrent.Task;

/**
 *
 * @author Vihanga
 */
public class CashierProductSuggestionData {

    private static final Map<String, Product> idMap = new HashMap<>();
    private static final Map<String, Product> nameMap = new HashMap<>();
    private static boolean loaded = false;

    private static synchronized void loadProductFields() {
        if (loaded) {
            return;
        }

        List<Product> products = ProductCRUD.searchProductList(); // sync DB call
        for (Product product : products) {
            idMap.put(String.valueOf(product.getId()), product);
            nameMap.put(product.getProduct(), product);
        }

        loaded = true;
        System.out.println("Products loaded: " + products.size());
    }

    public static List<String> searchProductCodes(String query) {
        loadProductFields();
        String q = query.toLowerCase();
        return idMap.keySet().stream()
                .filter(id -> id.toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public static List<String> searchProductNames(String query) {
        loadProductFields();
        String q = query.toLowerCase();
        return nameMap.keySet().stream()
                .filter(name -> name.toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public static Product getProductById(String id) {
        loadProductFields();
        return idMap.get(id);
    }

    public static Product getProductByName(String name) {
        loadProductFields();
        return nameMap.get(name);
    }
}
