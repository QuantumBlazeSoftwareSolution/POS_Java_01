/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.exports;

import com.qb.app.model.entity.Stock;
import java.util.List;

/**
 *
 * @author Vihanga
 */
public class StockProductExport {

    private List<Stock> stocks;

    public StockProductExport() {
    }

    public StockProductExport(List<Stock> stock) {
        this.stocks = stock;
    }

    public List<Stock> getStock() {
        return this.stocks;
    }
}
