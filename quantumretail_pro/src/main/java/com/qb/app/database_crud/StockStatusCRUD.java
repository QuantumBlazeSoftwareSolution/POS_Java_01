package com.qb.app.database_crud;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.StockStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author Vihanga
 */
public class StockStatusCRUD {

    public static StockStatus getStockStatus(String state) {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<StockStatus> cq = cb.createQuery(StockStatus.class);
            Root<StockStatus> stockStatusTable = cq.from(StockStatus.class);

            cq.where(cb.equal(stockStatusTable.get("status"), state));

            List<StockStatus> list = em.createQuery(cq).getResultList();

            if (!list.isEmpty()) {
                return list.get(0);
            } else {
                return createAndReturnStatus(state, em);
            }
        });
    }

    private static StockStatus createAndReturnStatus(String state, EntityManager em) {
        List<String> statusList = TableInitialValues.stockStatusList;

        StockStatus response = null;

        for (String value : statusList) {

            StockStatus status = new StockStatus();
            status.setStatus(value);

            em.persist(status);
            em.flush();

            if (status.getStatus() == state) {
                response = status;
            }
        }

        return response;
    }
}
