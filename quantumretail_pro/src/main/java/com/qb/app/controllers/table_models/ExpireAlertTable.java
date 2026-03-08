/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.table_models;

import com.qb.app.model.DefaultAPI;
import com.qb.app.model.entity.Stock;

/**
 *
 * @author Vihanga
 */
public class ExpireAlertTable {

    private Stock stock;
    private String batchId;
    private String itemName;
    private String salePrice;
    private String qty;
    private String expireDate;

    public ExpireAlertTable() {
    }

    public ExpireAlertTable(Stock stock) {
        this.stock = stock;
        this.batchId = String.valueOf(stock.getBatchId());
        this.itemName = stock.getProductId().getProduct();
        this.salePrice = String.format(DefaultAPI.currencyFloatFormat, stock.getSalePrice());
        this.qty = String.valueOf(stock.getQty());
        this.expireDate = DefaultAPI.formatDateObject(stock.getExpireDate(), "dd MMM YYYY");
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

}
