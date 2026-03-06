package com.qb.app.controllers.table_models;

import com.qb.app.model.DefaultAPI;
import com.qb.app.model.entity.Stock;

public class StockManagementTable {

    private String batchId;
    private String itemName;
    private String costPrice;
    private Double salePrice;
    private String expireDate;

    public StockManagementTable() {
    }

    public StockManagementTable(Stock stock) {
        this.batchId = stock.getBatchId().toString();
        this.itemName = stock.getProductId().getProduct();
        this.costPrice = String.format(DefaultAPI.currencyFloatFormat, stock.getCostPrice());
        this.salePrice = stock.getSalePrice();
        this.expireDate = DefaultAPI.formatDateObject(stock.getExpireDate(), "dd MMM YYYY");
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

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

}
