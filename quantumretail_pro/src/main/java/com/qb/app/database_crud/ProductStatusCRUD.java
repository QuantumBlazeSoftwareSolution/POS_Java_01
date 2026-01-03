package com.qb.app.database_crud;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.ProductStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

public class ProductStatusCRUD {

    public static ProductStatus getProductStatusByStatus(String status) {

        return JPATransaction.runInTransaction((em) -> {
            try {
                return findStatusFromStatus(status);
            } catch (Exception e) {
                if (getProductStatus().isEmpty()) {
                    createProductStatus();
                    return findStatusFromStatus(status);
                }
                return null;
            }
        });
    }

    private static ProductStatus findStatusFromStatus(String status) {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductStatus> cq = cb.createQuery(ProductStatus.class);
            Root<ProductStatus> table = cq.from(ProductStatus.class);

            cq.where(cb.equal(table.get("status"), status));

            return em.createQuery(cq).getSingleResult();
        });
    }

    public static void createProductStatus() {
        JPATransaction.runInTransaction((em) -> {
            List<String> productStatus = List.of("active", "inactive");
            for (String productStatu : productStatus) {
                ProductStatus status = new ProductStatus();
                status.setStatus(productStatu);
                em.persist(status);
                em.flush();
            }
        });
    }

    public static List<ProductStatus> getProductStatus() {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProductStatus> cq = cb.createQuery(ProductStatus.class);
            Root<ProductStatus> table = cq.from(ProductStatus.class);

            return em.createQuery(cq).getResultList();
        });
    }

}
