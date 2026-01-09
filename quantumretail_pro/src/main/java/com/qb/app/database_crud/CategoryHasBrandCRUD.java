package com.qb.app.database_crud;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Brand;
import com.qb.app.model.entity.Category;
import com.qb.app.model.entity.CategoryHasBrand;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author Vihanga
 */
public class CategoryHasBrandCRUD {

    public static CategoryHasBrand getCategoryHasBrand(Brand brand, Category category) {

        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<CategoryHasBrand> cq = cb.createQuery(CategoryHasBrand.class);
            Root<CategoryHasBrand> table = cq.from(CategoryHasBrand.class);

            Predicate predicateBrand = cb.equal(table.get("brandId"), brand);
            Predicate predicateCategory = cb.equal(table.get("categoryId"), category);

            cq.where(cb.and(predicateBrand, predicateCategory));

            List<CategoryHasBrand> list = em.createQuery(cq).getResultList();
            if (!list.isEmpty()) {
                return list.get(0);
            } else {
                CategoryHasBrand categoryHasBrand = new CategoryHasBrand();
                categoryHasBrand.setBrandId(brand);
                categoryHasBrand.setCategoryId(category);

                em.persist(categoryHasBrand);
                em.flush();
                return categoryHasBrand;
            }
        });
    }
    
}
