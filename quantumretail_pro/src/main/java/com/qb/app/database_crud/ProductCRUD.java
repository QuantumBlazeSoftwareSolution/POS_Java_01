package com.qb.app.database_crud;

import com.qb.app.controllers.admin.product.tables.ProductRegistrationTable;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.OperationResult;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductHasProductType;
import com.qb.app.model.entity.ProductType;
import com.qb.app.model.getLogger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ProductCRUD {

    public static OperationResult bulkProductRegistration(List<ProductRegistrationTable> items) {

        return JPATransaction.runInTransaction((EntityManager em) -> {
            try {
//                Product parentProduct = null;

                for (ProductRegistrationTable row : items) {
                    if ("parent".equalsIgnoreCase(row.getType().getType())) {

                        Product parentProduct = row.getProduct();

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

//                if (parentProduct == null) {
//                    return new OperationResult(false, "Parent product is missing.");
//                }
                for (ProductRegistrationTable row : items) {
                    if ("child".equalsIgnoreCase(row.getType().getType())) {

                        Product child = row.getProduct();
                        Product parent = row.getParentProduct();
                        em.persist(child);
                        em.flush();

                        ProductHasProductType rel = new ProductHasProductType();
                        rel.setProductId(child);
                        rel.setReferenceId(parent);
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

    public static List<Product> searchProductList() {
        return JPATransaction.runInTransaction((em) -> {
            try {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Product> cq = cb.createQuery(Product.class);
                Root<Product> table = cq.from(Product.class);

                List<Product> productList = em.createQuery(cq).getResultList();
                return productList;
            } catch (Exception e) {
                getLogger.logger().warning(e.toString());
                return new ArrayList<Product>();
            }
        });
    }

    public static List<Product> getParentProducts(EntityManager em, String searchTerm) {

            try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Product> cq = cb.createQuery(Product.class);
            Root<ProductHasProductType> phpTable = cq.from(ProductHasProductType.class);

            // Join to get the reference (parent) product
            Join<ProductHasProductType, Product> parentProduct = phpTable.join("referenceId");

            // Join to get the product type
            Join<ProductHasProductType, ProductType> productType = phpTable.join("productTypeId");

            // Build where conditions
            if (searchTerm != null && !searchTerm.isBlank()) {
                String pattern = "%" + searchTerm.toLowerCase() + "%";
                // Filter by product type = "parent" AND product name like searchTerm
                cq.where(
                        cb.and(
                                cb.equal(productType.get("type"), "parent"),
                                cb.like(cb.lower(parentProduct.get("product")), pattern)));
            } else {
                // Filter only by product type = "parent"
                cq.where(cb.equal(productType.get("type"), "parent"));
            }

            // Select distinct parent products
            cq.select(parentProduct).distinct(true);

            List<Product> productList = em.createQuery(cq).getResultList();
            return productList;
        } catch (Exception e) {
            getLogger.logger().warning(e.toString());
            return new ArrayList<Product>();
        }
    }
}
