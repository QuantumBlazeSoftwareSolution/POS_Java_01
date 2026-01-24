package com.qb.app.database_crud;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductHasProductType;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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

            Predicate predicateProduct = cb.equal(stockTable.get("productId"), product);
            Predicate predicateReferenceProduct = cb.equal(stockTable.get("productId"), referenceProduct.getReferenceId());
//            Predicate predicateQty = cb.greaterThan(stockTable.get("qty"), 0);

//            cq.where(cb.and(predicateProduct, predicateQty));
            cq.where(cb.or(predicateProduct, predicateReferenceProduct));

            List<Stock> stockList = em.createQuery(cq).getResultList();

            return stockList;
        });
    }

    public static Stock createSingleTemporaryStock(Product product, double salePrice) throws Exception {

        return JPATransaction.runInTransaction((em) -> {
            Stock stock = new Stock();

            stock.setQty(0);
            stock.setCostPrice(0);
            stock.setSalePrice(salePrice);
            stock.setDiscount(0);
            stock.setReceivedDate(new Date());
            stock.setExpireDate(new Date());
            stock.setProductId(product);

            em.persist(stock);
            em.flush();

            return stock;
        });

    }

}
