/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.table_models;

/**
 *
 * @author user
 */
public class CompanyListTable {
    
    private Integer colId ;
    private String colCompanyName ;
    private String colMobile ;

    public CompanyListTable(Integer colId, String colCompanyName, String colMobile) {
        this.colId = colId;
        this.colCompanyName = colCompanyName;
        this.colMobile = colMobile;
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
     * @return the colMobile
     */
    public String getColMobile() {
        return colMobile;
    }

    /**
     * @param colMobile the colMobile to set
     */
    public void setColMobile(String colMobile) {
        this.colMobile = colMobile;
    }

    

  
    

    
    
}


