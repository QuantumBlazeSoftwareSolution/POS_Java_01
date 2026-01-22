/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.database_crud;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.Stock;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author Vihanga
 */
public class StockCRUD {

    public static List<Stock> getStockItemsByProduct(Product product) {
        return JPATransaction.runInTransaction((em) -> {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Stock> cq = cb.createQuery(Stock.class);
            Root<Stock> stockTable = cq.from(Stock.class);

            Predicate predicateProduct = cb.equal(stockTable.get("productId"), product);
            Predicate predicateQty = cb.greaterThan(stockTable.get("qty"), 0);

            cq.where(cb.and(predicateProduct, predicateQty));

            List<Stock> stockList = em.createQuery(cq).getResultList();

            return stockList;
        });
    }

}
