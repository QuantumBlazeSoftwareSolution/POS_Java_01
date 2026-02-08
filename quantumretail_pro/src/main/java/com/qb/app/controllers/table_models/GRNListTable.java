
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.table_models;

import com.qb.app.model.entity.Product;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author ravis
 */
public class GRNListTable {

    // Create Variables 
    private Product product;
    private String barcode;
    private double qty;
    private LocalDate expireDate;
    private double costPrice;
    private double salePrice;
    private double discount;
    private double amount;
    private double customerDiscount;

    // Create Constructor
    public GRNListTable(
            Product productData, String barcode, double qtyData, LocalDate expireDateData, double costPriceData, double salePriceData, double discountData, double amountData, double customerDiscount) {

        //Assign Vlaues 
        this.product = productData;
        this.qty = qtyData;
        this.barcode = barcode;
        this.expireDate = expireDateData;
        this.costPrice = costPriceData;
        this.salePrice = salePriceData;
        this.discount = discountData;
        this.amount = amountData;
        this.customerDiscount = customerDiscount;
    }

    public GRNListTable() {
    }

    public void recalculateAmount() {
        this.amount = (this.qty * this.costPrice) - (this.discount * this.qty);
    }

    public double getCustomerDiscount() {
        return this.customerDiscount;
    }

    public void setCustomerDiscount(double customerDiscount) {
        this.customerDiscount = customerDiscount;
    }

    //Create Getter and Setters for  Accesss the data
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getDiscount() {
        return this.discount * this.qty;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

}
