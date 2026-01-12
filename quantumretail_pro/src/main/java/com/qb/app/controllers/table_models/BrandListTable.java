/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.table_models;

/**
 *
 * @author user
 */
public class BrandListTable {
    
    private Integer colId ; 
    private String colBrandName ;
    private String colstatus ;

    public BrandListTable(Integer colId, String colBrandName, String colstatus) {
        this.colId = colId;
        this.colBrandName = colBrandName;
        this.colstatus = colstatus;
    }

    
    public Integer getColId() {
        return colId;
    }

    
    public void setColId(Integer colId) {
        this.colId = colId;
    }

    
    public String getColBrandName() {
        return colBrandName;
    }

    
    public void setColBrandName(String colBrandName) {
        this.colBrandName = colBrandName;
    }

    
    public String getColstatus() {
        return colstatus;
    }

    
    public void setColstatus(String colstatus) {
        this.colstatus = colstatus;
    }
    
    
    
}
