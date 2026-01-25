/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.database_crud;

import java.util.List;

/**
 *
 * @author Vihanga
 */
public class TableInitialValues {

    // Stock Status Table Data
    public static List<String> stockStatusList = List.of("active", "inactive", "temporary");

    public static class StockStatusList {

        public static String active = "active";
        public static String inactive = "inactive";
        public static String temporary = "temporary";
    }

    public static List<String> invoiceItemTypeList = List.of("selling", "returning");

    public static class InvoiceItemType {

        public static String selling = "selling";
        public static String returning = "returning";
    }
}
