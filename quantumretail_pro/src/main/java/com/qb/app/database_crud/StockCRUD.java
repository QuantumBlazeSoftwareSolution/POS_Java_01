package com.qb.app.database_crud;

import com.qb.app.controllers.exports.StockProductExport;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductHasProductType;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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

            ProductHasProductType referenceProduct = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(product);

//            Predicate predicateProduct = cb.equal(stockTable.get("productId"), product);
            Predicate predicateReferenceProduct = cb.equal(stockTable.get("productId"), referenceProduct.getReferenceId());
//            Predicate predicateQty = cb.greaterThan(stockTable.get("qty"), 0);

//            cq.where(cb.and(predicateProduct, predicateQty));
            cq.where(predicateReferenceProduct);

            List<Stock> stockList = em.createQuery(cq).getResultList();

            System.out.println("Stock items count: " + stockList.size());

            return stockList;
        });
    }

    public static Stock getStockByBatchId(String batchId) {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Stock> cq = cb.createQuery(Stock.class);

            Root<Stock> stockTable = cq.from(Stock.class);

            cq.where(cb.equal(stockTable.get("batchId"), batchId));

            Stock stock = em.createQuery(cq).getResultList().get(0);

            return stock;
        });
    }

    public static Stock createSingleTemporaryStock(Product product, String salePrice, String costPrice, LocalDate expireDate, String barcode, double discount) {

        Stock stock = new Stock();

        stock.setQty(0);
        if (!costPrice.isEmpty()) {
            stock.setCostPrice(Double.parseDouble(costPrice));
        } else {
            stock.setCostPrice(0);
        }
        stock.setSalePrice(Double.parseDouble(salePrice));
        stock.setDiscount(0);
        stock.setReceivedDate(new Date());
        if (expireDate != null) {
            stock.setExpireDate(java.sql.Date.valueOf(expireDate));
        } else {
            stock.setExpireDate(new Date());
        }
        stock.setProductId(product);
        stock.setBarcode(barcode);
        stock.setDiscount(discount);
        stock.setStockStatusId(
                StockStatusCRUD.getStockStatus(
                        TableInitialValues.StockStatusList.temporary
                )
        );

        return JPATransaction.runInTransaction((em) -> {

            em.persist(stock);
            em.flush();

            return stock;
        });
    }

    public static StockProductExport getStockItemsByBarcode(String barcode) {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Stock> cq = cb.createQuery(Stock.class);
            Root<Stock> stockTable = cq.from(Stock.class);

            Predicate predicateBarcode = cb.equal(stockTable.get("barcode"), barcode);

            cq.where(predicateBarcode);

            try {
                Stock stock = em.createQuery(cq).getSingleResult();
                return new StockProductExport(stock, stock.getProductId());
            } catch (Exception e) {
                return new StockProductExport(null, null);
            }
        });
    }

    public static Stock updateStock(Stock stock) {
        return JPATransaction.runInTransaction((em) -> {
            try {
                em.merge(stock);
                em.flush();
                return stock;
            } catch (Exception e) {
                e.printStackTrace();
                getLogger.logger().warning(e.toString());
                return null;
            }

        });
    }

    public static List<Stock> getStocks() {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Stock> cq = cb.createQuery(Stock.class);
            Root<Stock> stockTable = cq.from(Stock.class);

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.notEqual(stockTable.get("qty"), 0));

            predicates.add(cb.notEqual(
                    stockTable.get("stockStatusId"),
                    StockStatusCRUD.getStockStatus("inactive")
            ));

            cq.where(cb.and(predicates.toArray(Predicate[]::new)));

            List<Stock> result = em.createQuery(cq).getResultList();

            System.out.println("Stock founds: " + result.size());

            return result;
        });
    }
}
