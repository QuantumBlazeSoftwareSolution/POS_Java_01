/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.database_crud;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.InvoiceItemType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;

/**
 *
 * @author Vihanga
 */
public class InvoiceItemTypeCRUD {

    public static InvoiceItemType getInvoiceItemType(String type) {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<InvoiceItemType> cq = cb.createQuery(InvoiceItemType.class);
            Root<InvoiceItemType> invoiceItemTable = cq.from(InvoiceItemType.class);

            cq.where(cb.equal(invoiceItemTable.get("type"), type));

            List<InvoiceItemType> list = em.createQuery(cq).getResultList();

            if (!list.isEmpty()) {
                return list.get(0);
            } else {
                return createAndReturnInvoiceItemType(type, em);
            }
        });
    }

    public static InvoiceItemType createAndReturnInvoiceItemType(String type, EntityManager em) {
        List<String> typeList = TableInitialValues.invoiceItemTypeList;

        InvoiceItemType response = null;

        for (String string : typeList) {

            InvoiceItemType itemType = new InvoiceItemType();
            itemType.setType(string);
            em.persist(itemType);
            em.flush();

            if (itemType.getType() == type) {
                response = itemType;
            }
        }

        return response;
    }

}
