/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.table_models;

/**
 *
 * @author user
 */
public class SupplierListTable {
    
     private Integer colId ;
    private String colSupplierName ;
    private String colCompanyName ;
    private String colStatus ;

    public SupplierListTable(Integer colId, String colSupplierName, String colCompanyName, String colStatus) {
        this.colId = colId;
        this.colSupplierName = colSupplierName;
        this.colCompanyName = colCompanyName;
        this.colStatus = colStatus;
    }

    /**
     * @return the colId
     */
    public Integer getColId() {
        return colId;
    }

    /**
     * @param colId the colId to set
     */
    public void setColId(Integer colId) {
        this.colId = colId;
    }

    /**
     * @return the colSupplierName
     */
    public String getColSupplierName() {
        return colSupplierName;
    }

    /**
     * @param colSupplierName the colSupplierName to set
     */
    public void setColSupplierName(String colSupplierName) {
        this.colSupplierName = colSupplierName;
    }

    /**
     * @return the colCompanyName
     */
    public String getColCompanyName() {
        return colCompanyName;
    }

    /**
     * @param colCompanyName the colCompanyName to set
     */
    public void setColCompanyName(String colCompanyName) {
        this.colCompanyName = colCompanyName;
    }

    /**
     * @return the colStatus
     */
    public String getColStatus() {
        return colStatus;
    }

    /**
     * @param colStatus the colStatus to set
     */
    public void setColStatus(String colStatus) {
        this.colStatus = colStatus;
    }
    
    
    
}
