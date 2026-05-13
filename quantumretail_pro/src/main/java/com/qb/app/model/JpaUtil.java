package com.qb.app.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class JpaUtil {

    private static final String PERSISTENCE_UNIT_NAME = "quantumRetailDBUnit";
    private static EntityManagerFactory factory;

    static {
        try {

            String dbUrl = System.getenv("DB_URL");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");

            System.out.println("DB URL:" + dbUrl);
            System.out.println("DB User:" + dbUser);
            System.out.println("DB Password:" + dbPassword);

            if (dbUrl == null || dbUrl.isBlank()) {
                throw new RuntimeException("Missing DB_URL");
            }

            if (dbUser == null || dbUser.isBlank()) {
                throw new RuntimeException("Missing DB_USER");
            }

            if (dbPassword == null || dbPassword.isBlank()) {
                throw new RuntimeException("Missing DB_PASSWORD");
            }

            Map<String, Object> properties = new HashMap<>();

            properties.put(
                    "jakarta.persistence.jdbc.url",
                    dbUrl + "?zeroDateTimeBehavior=CONVERT_TO_NULL"
            );
            properties.put("jakarta.persistence.jdbc.user", dbUser);
            properties.put("jakarta.persistence.jdbc.password", dbPassword);
            properties.put("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");

            System.out.println("Database URL: " + dbUrl);

            factory = Persistence.createEntityManagerFactory(
                    PERSISTENCE_UNIT_NAME,
                    properties
            );

        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
            throw new RuntimeException("Failed to create EntityManagerFactory", e);
        }
    }

    public static EntityManager getEntityManager() {
        return factory.createEntityManager();
    }

    public static void close() {
        if (factory != null) {
            factory.close();
        }
    }
}
