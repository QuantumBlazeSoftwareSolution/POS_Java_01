/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.exports;

import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.Stock;

/**
 *
 * @author Vihanga
 */
public class StockProductExport {

    private Stock stock;
    private Product product;

    public StockProductExport() {
    }

    public StockProductExport(Stock stock, Product product) {
        this.stock = stock;
        this.product = product;
    }

    public Stock getStock() {
        return this.stock;
    }

    public Product getProduct() {
        return this.product;
    }
}
