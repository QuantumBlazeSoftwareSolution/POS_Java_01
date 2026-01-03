package com.qb.app.database_crud;

import com.qb.app.controllers.admin.product.tables.ProductRegistrationTable;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductHasProductType;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProductCRUD {

    public static void bulkProductRegistration(List<ProductRegistrationTable> items) {

        JPATransaction.runInTransaction((EntityManager em) -> {

            Product parentProduct = null;

            // 1️ Insert parent first
            for (ProductRegistrationTable row : items) {

                if ("parent".equalsIgnoreCase(row.getType().getType())) {

                    parentProduct = row.getProduct();
                    em.persist(parentProduct);
                    em.flush();

                    ProductHasProductType rel = new ProductHasProductType();
                    rel.setProductId(parentProduct);
                    rel.setReferenceId(parentProduct);
                    rel.setProductTypeId(row.getType());

                    em.persist(rel);
                    break;
                }
            }

            if (parentProduct == null) {
                throw new RuntimeException("Parent product not found");
            }

            // 2️ Insert children
            for (ProductRegistrationTable row : items) {

                if ("child".equalsIgnoreCase(row.getType().getType())) {

                    Product child = row.getProduct();
                    em.persist(child);
                    em.flush();

                    ProductHasProductType rel = new ProductHasProductType();
                    rel.setProductId(child);
                    rel.setReferenceId(parentProduct);
                    rel.setProductTypeId(row.getType());

                    em.persist(rel);
                }
            }
        });
    }
}
