/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

/**
 *
 * @author Vihanga
 */
public class JpaUtil {

    private static final EntityManager em = buildEntityManagerFactory();

    private static EntityManager buildEntityManagerFactory() {
        try {
            return Persistence.createEntityManagerFactory("databaseUnit").createEntityManager();
        } catch (Exception ex) {
            throw new ExceptionInInitializerError("Initial EntityManagerFactory creation failed: " + ex);
        }
    }

    public static EntityManager getEntityManagerFactory() {
        return em;
    }

    public static void shutdown() {
        if (em != null) {
            em.close();
        }
    }
}
