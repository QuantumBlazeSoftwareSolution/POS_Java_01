package com.qb.app.controllers.table_models;

import com.qb.app.model.entity.Stock;

public class StockAlertTable {

    private Stock stock;
    private String batchId;
    private String itemName;
    private String measure;
    private String originQty;
    private String totalQty;

    public StockAlertTable() {
    }

    public StockAlertTable(Stock stock) {
        this.stock = stock;
        this.batchId = String.valueOf(stock.getBatchId());
        this.itemName = stock.getProductId().getProduct();
        this.measure = String.valueOf(stock.getProductId().getMeasure());
        this.totalQty = String.valueOf(stock.getQty());

        double originQtyDouble = stock.getQty() / stock.getProductId().getMeasure();

        // formatted to 2 decimals or whole number if it has no fraction
        if (originQtyDouble == (long) originQtyDouble) {
            this.originQty = String.format("%d", (long) originQtyDouble);
        } else {
            this.originQty = String.format("%.2f", originQtyDouble);
        }
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

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getOriginQty() {
        return originQty;
    }

    public void setOriginQty(String originQty) {
        this.originQty = originQty;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }
}
