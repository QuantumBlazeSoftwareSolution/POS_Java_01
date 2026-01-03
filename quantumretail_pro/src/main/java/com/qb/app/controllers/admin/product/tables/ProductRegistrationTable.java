package com.qb.app.controllers.admin.product.tables;

import com.qb.app.model.entity.Brand;
import com.qb.app.model.entity.Category;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductType;

public class ProductRegistrationTable {

    private String itemName;
    private ProductType type;
    private String typeStr;
    private double costPrice;
    private double salePrice;
    private double discount;
    private double unitMeasure;
    private String barCode;
    private Product product;
    private Brand brand;
    private Category category;

    public ProductRegistrationTable(Product product) {
        this.product = product;
    }

    public ProductRegistrationTable(ProductType type, Product product, Brand brand, Category category) {
        this.itemName = product.getProduct();
        this.type = type;
        this.typeStr = type.getType();
        this.costPrice = product.getCostPrice();
        this.salePrice = product.getSalePrice();
        this.discount = product.getDiscount();
        if (!product.getBarCode().isEmpty() || product.getBarCode() != null) {
            this.barCode = product.getBarCode();
        } else {
            this.barCode = "";
        }
        this.unitMeasure = product.getMeasure();
        this.product = product;
        this.brand = brand;
        this.category = category;
    }

    public String getItemName() {
        return this.itemName;
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

    public void setItemName(String name) {
        this.itemName = name;
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

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
