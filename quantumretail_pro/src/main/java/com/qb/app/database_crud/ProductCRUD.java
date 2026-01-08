package com.qb.app.database_crud;

import com.qb.app.controllers.admin.product.tables.ProductRegistrationTable;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.OperationResult;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductHasProductType;
import com.qb.app.model.getLogger;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ProductCRUD {

    public static OperationResult bulkProductRegistration(List<ProductRegistrationTable> items) {

        return JPATransaction.runInTransaction((EntityManager em) -> {
            try {
                Product parentProduct = null;

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
                    return new OperationResult(false, "Parent product is missing.");
                }

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

                return new OperationResult(true, "Products registered successfully.");

            } catch (Exception e) {
                e.printStackTrace();
                return new OperationResult(
                        false,
                        "Database error occurred while saving products."
                );
            }
        });
    }

    public static Product updateProduct(Product product) {
        return JPATransaction.runInTransaction((em) -> {
            try {
                em.merge(product);
                em.flush();
                return product;
            } catch (Exception e) {
                getLogger.logger().warning(e.toString());
                e.printStackTrace();
                return null;
            }
        });
    }

    public static Product searchProductById(int id) {
        return JPATransaction.runInTransaction((em) -> {
            Product product = em.find(Product.class, id);
            return product;
        });
    }
    
}
