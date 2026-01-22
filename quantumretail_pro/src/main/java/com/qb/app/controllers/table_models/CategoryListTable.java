/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.table_models;

/**
 *
 * @author user
 */
public class CategoryListTable {
    
    private Integer colId ; 
    private String colCategoryName ;
    private String colstatus ;

    public CategoryListTable(Integer colId, String colCategoryName, String colstatus) {
        this.colId = colId;
        this.colCategoryName = colCategoryName;
        this.colstatus = colstatus;
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
     * @return the colCategoryName
     */
    public String getColCategoryName() {
        return colCategoryName;
    }

    /**
     * @param colCategoryName the colCategoryName to set
     */
    public void setColCategoryName(String colCategoryName) {
        this.colCategoryName = colCategoryName;
    }

    /**
     * @return the colstatus
     */
    public String getColstatus() {
        return colstatus;
    }

    /**
     * @param colstatus the colstatus to set
     */
    public void setColstatus(String colstatus) {
        this.colstatus = colstatus;
    }
    
    
}
