package com.qb.app.model;

import com.qb.app.model.entity.Category;
import com.qb.app.model.entity.Employee;
import com.qb.app.model.entity.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.HibernateException;
import org.json.JSONException;
import org.json.JSONObject;

public class UnitTestingVihanga {

    private static boolean isLicenseActive;
    private static int period;
    private static double charge;
    private static String status;
    private static String init_date;
    private static String expire_date;

    public static void main(String[] args) {
//        testJPA();
//        getSessionDetails();
//        loadComboBoxData();
//        testRun();
        passwordTest();
//        testDatabaseResults();
//        testJsonFileHandling();
//        workWithConfigFile();
//        testEagerLoading();
    }

    private static void testJPA() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Employee employee = entityManager.find(Employee.class, 1L);
            System.out.println("Employee Name: " + employee.getName());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private static boolean isSignIn;

    private static void getSessionDetails() {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = JpaUtil.getEntityManager(); // Your utility method for getting EntityManager
            transaction = em.getTransaction();
            transaction.begin();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Session> criteriaQuery = criteriaBuilder.createQuery(Session.class);
            Root<Session> sessionTable = criteriaQuery.from(Session.class);

            LocalDate today = LocalDate.now(); // For '2025-04-27', you can use LocalDate.of(2025, 4, 27);

            // Build Predicate (where DATE(day_in_time) = today)
            Predicate predicate = criteriaBuilder.equal(
                    criteriaBuilder.function("DATE", Date.class, sessionTable.get("dayInTime")),
                    java.sql.Date.valueOf(today)
            );

            criteriaQuery.select(sessionTable).where(predicate);

            try {
                Session sessionToday = em.createQuery(criteriaQuery).getSingleResult();

                if (sessionToday.getStatus().equals("OFF")) {
                    System.out.println("Day Completed.");
                } else {
                    System.out.println("Already Sign In for Today.");
                    System.out.println("Waiting for Sign OFF.");
                    isSignIn = true;
                }
            } catch (NoResultException e) {
                System.out.println("No session found for today.");
                System.out.println("Waiting for Sign In.");
                System.out.println("Sign OFF is not activated.");
            }

        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error during login: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    private static void loadComboBoxData() {
        try {
            JPATransaction.runInTransaction((em) -> {
                CriteriaBuilder cBuilder = em.getCriteriaBuilder();
                CriteriaQuery<Category> cQuery = cBuilder.createQuery(Category.class);
                Root<Category> brandTable = cQuery.from(Category.class);

                cQuery.orderBy(cBuilder.asc(brandTable.get("brand")));

                List<Category> brandList = em.createQuery(cQuery).getResultList();

                ObservableList<Category> observableList = FXCollections.observableArrayList(brandList);
                for (Category brand : observableList) {
                    System.out.println(brand.getCategory());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testRun() {
        double unitPrice = 0;
        double itemQty = 1;
        System.out.println(String.format("Rs. %.2f", unitPrice * itemQty));
    }

    private static void passwordTest() {
        System.out.println("Your password: " + PasswordEncryption.hashPassword("asd321"));
    }

    private static void testDatabaseResults() {
        try {
            JPATransaction.runInTransaction((em) -> {
                CriteriaBuilder cBuilder = em.getCriteriaBuilder();
                CriteriaQuery<Employee> cQuery = cBuilder.createQuery(Employee.class);
                Root<Employee> rootTable = cQuery.from(Employee.class);

                Predicate condition = cBuilder.equal(rootTable.get("username"), "Cashier");
                cQuery.where(condition);

                Employee emp = em.createQuery(cQuery).getSingleResult();
                System.out.println("Employee Name: " + emp.getName());
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testJsonFileHandling() {
        JSONObject system = new JSONObject();
        system.put("bill_discount", false);
        system.put("cash_withdrawal", false);
        system.put("credit_payment", false);
        system.put("employee_management", false);
        system.put("inventory_management", false);
        system.put("product_discount", false);
        system.put("refund", false);
        system.put("multi_stock", false);
        system.put("expire_tracking", false);

        JSONObject report = new JSONObject();
        report.put("bin", false);
        report.put("cash_withdrawal", false);
        report.put("close_sale", false);
        report.put("customer", false);
        report.put("damage", false);
        report.put("distribute", false);
        report.put("grn", false);
        report.put("location_return", false);
        report.put("product_list", false);
        report.put("profit", false);
        report.put("sale_detail", false);
        report.put("sale_summary", false);
        report.put("sale_product", false);
        report.put("session", false);
        report.put("stock_balance", false);
        report.put("supplyOrder", false);

        JSONObject bill_discount = new JSONObject();
        bill_discount.put("amount", 0.00);
        bill_discount.put("type", "cash"); // type='cash' or 'percentage'

        JSONObject license = new JSONObject();
        license.put("isLicenseActive", false); // -1 for lifetime
        license.put("period", 0); // -1 for lifetime
        license.put("status", ""); // 'trial' or 'life-time'
        license.put("charge", 0);
        license.put("init_date", "");
        license.put("expiry_date", "");

        JSONObject stock_adj = new JSONObject();
        stock_adj.put("adjustment_count", 1);
        stock_adj.put("tempory_chance", 0);

        JSONObject config = new JSONObject();
        config.put("system_name", "");
        config.put("telephone_01", "");
        config.put("telephone_02", "");
        config.put("address", "");
        config.put("isInitialized", false);
        config.put("system", system);
        config.put("report", report);
        config.put("bill_discount", bill_discount);
        config.put("license", license);
        config.put("stock_adjustment", stock_adj);

        try {
            Path path = Paths.get("config.json");

            Files.write(path, config.toString(2).getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            try {
                String json = new String(Files.readAllBytes(path));
                AESUtil.saveEncryptedFile("system configuration.enc", json);
                System.out.println("Config file encrypted and saved as config.enc");

                Files.deleteIfExists(path);
            } catch (Exception e) {
                System.err.println("Error encrypting config file: " + e.getMessage());
                e.printStackTrace();
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error handling config file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void workWithConfigFile() {
        try {
            Config system_config = ConfigManager.loadConfig();
            System.out.println("BIN report status: " + system_config.report.bin);

            system_config.report.bin = false;
            ConfigManager.saveConfig(system_config);
            System.out.println("BIN report status: " + system_config.report.bin);
            System.out.println(system_config.license.expire_date);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void testEagerLoading() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
