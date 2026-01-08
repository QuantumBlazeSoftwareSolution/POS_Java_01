/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.database_crud;

import com.qb.app.model.EntityManagerCallBack;
import static com.qb.app.model.JPATransaction.runInTransaction;
import com.qb.app.model.entity.Session;

/**
 *
 * @author Vihanga
 */
public class SessionCRUD {

    public static void saveNewSignInSession(Session signInSession) {
        runInTransaction((EntityManagerCallBack) em -> em.persist(signInSession));
    }

    public static void mergeSignOffSession(Session sessionToday) {
        runInTransaction((EntityManagerCallBack) em -> em.merge(sessionToday));
    }
}
