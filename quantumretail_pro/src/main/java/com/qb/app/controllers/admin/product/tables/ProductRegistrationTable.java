package com.qb.app.controllers.admin.product.tables;

import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductType;

public class ProductRegistrationTable {

    private ProductType type;
    private String typeStr;
    private double costPrice;
    private double salePrice;
    private double discount;
    private double unitMeasure;
    private String barCode;
    private final Product product;

    public ProductRegistrationTable(Product product) {
        this.product = product;
    }

    public ProductRegistrationTable(ProductType type, Product product) {
        this.type = type;
        this.typeStr = type.getType();
        this.costPrice = product.getCostPrice();
        this.salePrice = product.getSalePrice();
        this.discount = product.getDiscount();
        this.barCode = product.getBarCode();
        this.unitMeasure = product.getMeasure();
        this.product = product;
    }

    public String getTypeStr() {
        return this.typeStr;
    }

    public Product getProduct() {
        return this.product;
    }

    public ProductType getType() {
        return type;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public double getUnitMeasure() {
        return unitMeasure;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public void setUnitMeasure(double unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
