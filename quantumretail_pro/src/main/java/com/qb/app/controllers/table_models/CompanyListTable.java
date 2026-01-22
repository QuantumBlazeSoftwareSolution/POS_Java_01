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
    private String colAddress ;
    private String colMobile ;
    private String colMobile02 ;
    private String  colStatus ;

    public CompanyListTable(Integer colId, String colCompanyName, String colAddress, String colMobile, String colMobile02, String colStatus) {
        this.colId = colId;
        this.colCompanyName = colCompanyName;
        this.colAddress = colAddress;
        this.colMobile = colMobile;
        this.colMobile02 = colMobile02;
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
     * @return the colAddress
     */
    public String getColAddress() {
        return colAddress;
    }

    /**
     * @param colAddress the colAddress to set
     */
    public void setColAddress(String colAddress) {
        this.colAddress = colAddress;
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

    /**
     * @return the colMobile02
     */
    public String getColMobile02() {
        return colMobile02;
    }

    /**
     * @param colMobile02 the colMobile02 to set
     */
    public void setColMobile02(String colMobile02) {
        this.colMobile02 = colMobile02;
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


