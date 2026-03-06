/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.table_models;

import com.qb.app.model.entity.InvoiceItem;

/**
 *
 * @author user
 */
public class InvoiceItemList {
    
    private Integer colItemCode ; 
    private String colItemName ;
    private Double unitPrice;
    private Integer qty;
    private Double Amount;
    private InvoiceItem invoiceItem;

    public InvoiceItemList(Integer colItemCode, String colItemName, Double unitPrice, Integer qty, Double Amount, InvoiceItem invoiceItem) {
        this.colItemCode = colItemCode;
        this.colItemName = colItemName;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.Amount = Amount;
        this.invoiceItem = invoiceItem;
    }

    /**
     * @return the colItemCode
     */
    public Integer getColItemCode() {
        return colItemCode;
    }

    /**
     * @param colItemCode the colItemCode to set
     */
    public void setColItemCode(Integer colItemCode) {
        this.colItemCode = colItemCode;
    }

    /**
     * @return the colItemName
     */
    public String getColItemName() {
        return colItemName;
    }

    /**
     * @param colItemName the colItemName to set
     */
    public void setColItemName(String colItemName) {
        this.colItemName = colItemName;
    }

    /**
     * @return the unitPrice
     */
    public Double getUnitPrice() {
        return unitPrice;
    }

    /**
     * @param unitPrice the unitPrice to set
     */
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     * @return the qty
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * @return the Amount
     */
    public Double getAmount() {
        return Amount;
    }

    /**
     * @param Amount the Amount to set
     */
    public void setAmount(Double Amount) {
        this.Amount = Amount;
    }

    /**
     * @return the invoiceItem
     */
    public InvoiceItem getInvoiceItem() {
        return invoiceItem;
    }

    /**
     * @param invoiceItem the invoiceItem to set
     */
    public void setInvoiceItem(InvoiceItem invoiceItem) {
        this.invoiceItem = invoiceItem;
    }
    
    
    
}
