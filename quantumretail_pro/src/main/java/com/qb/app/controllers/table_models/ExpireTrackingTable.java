package com.qb.app.controllers.table_models;

import com.qb.app.model.entity.Stock;

public class ExpireTrackingTable {

    private String batchId;
    private String itemName;
    private String quantity;
    private String expireDate;
    private String receiveDate;
    private Stock stock;

    public ExpireTrackingTable() {
    }

    public ExpireTrackingTable(String batchId, String itemName, String quantity, String expireDate, String receiveDate, Stock stock) {
        this.batchId = batchId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.expireDate = expireDate;
        this.receiveDate = receiveDate;
        this.stock = stock;
    }

    public Stock getStoc() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

}
