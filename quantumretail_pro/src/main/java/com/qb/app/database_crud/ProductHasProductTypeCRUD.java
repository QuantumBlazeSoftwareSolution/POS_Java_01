/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.database_crud;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductHasProductType;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author Vihanga
 */
public class ProductHasProductTypeCRUD {

    public static ProductHasProductType getProductHasProductTypeByProductProduct(Product product) {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductHasProductType> cq = cb.createQuery(ProductHasProductType.class);
            Root<ProductHasProductType> productHasProductTypeTable = cq.from(ProductHasProductType.class);

            cq.where(cb.equal(productHasProductTypeTable.get("productId"), product));

            List<ProductHasProductType> productHasProductTypeList = em.createQuery(cq).getResultList();

            if (!productHasProductTypeList.isEmpty()) {
                return productHasProductTypeList.get(0);
            } else {
                return null;
            }
        });
    }

}
