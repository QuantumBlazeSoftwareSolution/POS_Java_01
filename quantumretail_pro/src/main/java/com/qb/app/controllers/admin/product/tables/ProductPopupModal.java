package com.qb.app.controllers.admin.product.tables;

import com.qb.app.model.entity.Product;

/**
 *
 * @author Vihanga
 */
public class ProductPopupModal {

    private Integer colId;
    private String colProduct;
    private Double colSalePrice;
    private Double colCostPrice;
    private String colMeasure;
    private Double colDiscount;
    private String colBarcode;
    private Product product;

    public ProductPopupModal(Integer colId, String colProduct, Double colSalePrice, Double colCostPrice, String colMeasure, Double colDiscount, String colBarcode, Product product) {
        this.colId = colId;
        this.colProduct = colProduct;
        this.colSalePrice = colSalePrice;
        this.colCostPrice = colCostPrice;
        this.colMeasure = colMeasure;
        this.colDiscount = colDiscount;
        this.colBarcode = colBarcode;
        this.product = product;
    }

    public Integer getColId() {
        return colId;
    }

    public void setColId(Integer colId) {
        this.colId = colId;
    }

    public String getColProduct() {
        return colProduct;
    }

    public void setColProduct(String colProduct) {
        this.colProduct = colProduct;
    }

    public Double getColSalePrice() {
        return colSalePrice;
    }

    public void setColSalePrice(Double colSalePrice) {
        this.colSalePrice = colSalePrice;
    }

    public Double getColCostPrice() {
        return colCostPrice;
    }

    public void setColCostPrice(Double colCostPrice) {
        this.colCostPrice = colCostPrice;
    }

    public String getColMeasure() {
        return colMeasure;
    }

    public void setColMeasure(String colMeasure) {
        this.colMeasure = colMeasure;
    }

    public Double getColDiscount() {
        return colDiscount;
    }

    public void setColDiscount(Double colDiscount) {
        this.colDiscount = colDiscount;
    }

    public String getColBarcode() {
        return colBarcode;
    }

    public void setColBarcode(String colBarcode) {
        this.colBarcode = colBarcode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
