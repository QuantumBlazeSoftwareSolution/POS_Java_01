package com.qb.app.database_crud;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.ProductType;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ProductTypeCRUD {

    public static ProductType getProductType(String type) {
        return JPATransaction.runInTransaction((em) -> {
            try {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<ProductType> cq = cb.createQuery(ProductType.class);
                Root<ProductType> root = cq.from(ProductType.class);

                cq.where(cb.equal(root.get("type"), type));
                
                return em.createQuery(cq).getSingleResult();

            } catch (NoResultException e) {
                return null;
            }
        });
    }
}
