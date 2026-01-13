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

/**
 *
 * @author Vihanga
 */
public class CashierProductSuggestionData {

    private static final List<Map<String, Product>> idList = new ArrayList<>();
    private static final List<Map<String, Product>> nameList = new ArrayList<>();

    private static void loadProductFields() {
        List<Product> productList = ProductCRUD.searchProductList();

        idList.clear();
        nameList.clear();

        for (Product product : productList) {

            Map<String, Product> idMap = new HashMap<>();
            idMap.put(String.valueOf(product.getId()), product);
            idList.add(idMap);

            Map<String, Product> nameMap = new HashMap<>();
            nameMap.put(product.getProduct(), product);
            nameList.add(nameMap);
        }
    }

    public static List<String> searchProductCodes(String query) {

        if (idList.isEmpty()) {
            loadProductFields();
        }

        String q = query.toLowerCase();

        return idList.stream()
                .flatMap(map -> map.keySet().stream())
                .filter(id -> id.toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public static List<String> searchProductNames(String query) {

        if (nameList.isEmpty()) {
            loadProductFields();
        }

        String q = query.toLowerCase();

        return nameList.stream()
                .flatMap(map -> map.keySet().stream())
                .filter(name -> name.toLowerCase().contains(q))
                .collect(Collectors.toList());
    }

    public static Product getProductById(String id) {

        if (idList.isEmpty()) {
            loadProductFields();
        }

        for (Map<String, Product> map : idList) {
            if (map.containsKey(id)) {
                return map.get(id);
            }
        }
        return null;
    }

    public static Product getProductByName(String name) {

        if (nameList.isEmpty()) {
            loadProductFields();
        }

        for (Map<String, Product> map : nameList) {
            if (map.containsKey(name)) {
                return map.get(name);
            }
        }
        return null;
    }

}