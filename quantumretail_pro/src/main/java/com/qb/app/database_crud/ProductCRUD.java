package com.qb.app.database_crud;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductType;
import jakarta.persistence.criteria.CriteriaBuilder;

public class ProductCRUD {
    
    public static Product createProduct(Product product, ProductType type){
        return JPATransaction.runInTransaction((em) -> {
            
            
            
            em.persist(em);            
            return null;
        });
    }
    
}
