/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.table_models;

import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.Stock;

/**
 *
 * @author Vihanga
 */
public class CashierInvoiceTable {

    private Product product;
    private String itemId;
    private String itemName;
    private double qty;
    private String unitPrice;
    private String amount;
    private Stock stock;

    public CashierInvoiceTable() {
    }
    
    public CashierInvoiceTable(Product product, String itemId, String itemName, double qty, String unitPrice, String amount) {
        this.product = product;
        this.itemId = itemId;
        this.itemName = itemName;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

}
