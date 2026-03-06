/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.table_models;

import com.qb.app.model.entity.Invoice;

/**
 *
 * @author user
 */
public class InvoiceList {
    private String colDateTime ; 
    private Integer colInvoiceNumber ;
    private Invoice invoice;

    public InvoiceList(String colDateTime, Integer colInvoiceNumber, Invoice invoice) {
        this.colDateTime = colDateTime;
        this.colInvoiceNumber = colInvoiceNumber;
        this.invoice = invoice;
    }

    /**
     * @return the colDateTime
     */
    public String getColDateTime() {
        return colDateTime;
    }

    /**
     * @param colDateTime the colDateTime to set
     */
    public void setColDateTime(String colDateTime) {
        this.colDateTime = colDateTime;
    }

    /**
     * @return the colInvoiceNumber
     */
    public Integer getColInvoiceNumber() {
        return colInvoiceNumber;
    }

    /**
     * @param colInvoiceNumber the colInvoiceNumber to set
     */
    public void setColInvoiceNumber(Integer colInvoiceNumber) {
        this.colInvoiceNumber = colInvoiceNumber;
    }

    /**
     * @return the invoice
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * @param invoice the invoice to set
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    

   
    
    
    
    
}


