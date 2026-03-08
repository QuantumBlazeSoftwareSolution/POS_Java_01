package com.qb.app.database_crud;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Invoice;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

/**
 * Provides database aggregation methods for the Admin Dashboard Charts.
 */
public class DashboardCRUD {

    public static Map<String, Double> getRevenueLast7Days() {
        return JPATransaction.runInTransaction(em -> {
            Map<String, Double> dailyRevenue = new LinkedHashMap<>();
            SimpleDateFormat sdfDay = new SimpleDateFormat("EEE");

            // Initialize last 7 days with 0.0
            Calendar cal = Calendar.getInstance();
            for (int i = 6; i >= 0; i--) {
                Calendar c = (Calendar) cal.clone();
                c.add(Calendar.DAY_OF_MONTH, -i);
                dailyRevenue.put(sdfDay.format(c.getTime()), 0.0);
            }

            Calendar startCal = Calendar.getInstance();
            startCal.add(Calendar.DAY_OF_MONTH, -7);
            startCal.set(Calendar.HOUR_OF_DAY, 0);
            startCal.set(Calendar.MINUTE, 0);
            startCal.set(Calendar.SECOND, 0);
            Date startDate = startCal.getTime();

            List<Invoice> invoices = em.createQuery(
                    "SELECT i FROM Invoice i WHERE i.dateTime >= :startDate", Invoice.class)
                    .setParameter("startDate", startDate)
                    .getResultList();

            for (Invoice inv : invoices) {
                String dayStr = sdfDay.format(inv.getDateTime());
                if (dailyRevenue.containsKey(dayStr)) {
                    dailyRevenue.put(dayStr, dailyRevenue.get(dayStr) + inv.getBillAmount());
                }
            }

            return dailyRevenue;
        });
    }

    public static Map<String, Double> getSalesByCategory() {
        return JPATransaction.runInTransaction(em -> {
            Map<String, Double> categorySales = new LinkedHashMap<>();
            List<Object[]> results = em.createQuery(
                    "SELECT ii.productId.categoryHasBrandId.categoryId.category, SUM(ii.qty) " +
                            "FROM InvoiceItem ii " +
                            "GROUP BY ii.productId.categoryHasBrandId.categoryId.category",
                    Object[].class)
                    .getResultList();

            for (Object[] row : results) {
                String cat = row[0] != null ? (String) row[0] : "Other";
                Double qty = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
                categorySales.put(cat, qty);
            }
            return categorySales;
        });
    }

    public static Map<String, Double> getTopSellingProducts() {
        return JPATransaction.runInTransaction(em -> {
            Map<String, Double> topProducts = new LinkedHashMap<>();
            List<Object[]> results = em.createQuery(
                    "SELECT ii.productId.product, SUM(ii.qty) " +
                            "FROM InvoiceItem ii " +
                            "GROUP BY ii.productId.product " +
                            "ORDER BY SUM(ii.qty) DESC",
                    Object[].class)
                    .setMaxResults(5)
                    .getResultList();

            for (Object[] row : results) {
                String prod = row[0] != null ? (String) row[0] : "Unknown";
                Double qty = row[1] != null ? ((Number) row[1]).doubleValue() : 0.0;
                topProducts.put(prod, qty);
            }
            return topProducts;
        });
    }

    public static Map<String, Double> getMonthlyRevenue(boolean currentMonth) {
        return JPATransaction.runInTransaction(em -> {
            Map<String, Double> weeklyRevenue = new LinkedHashMap<>();
            weeklyRevenue.put("Week 1", 0.0);
            weeklyRevenue.put("Week 2", 0.0);
            weeklyRevenue.put("Week 3", 0.0);
            weeklyRevenue.put("Week 4", 0.0);

            Calendar cal = Calendar.getInstance();
            if (!currentMonth) {
                cal.add(Calendar.MONTH, -1);
            }
            int targetMonth = cal.get(Calendar.MONTH);
            int targetYear = cal.get(Calendar.YEAR);

            Calendar startCal = Calendar.getInstance();
            startCal.set(Calendar.YEAR, targetYear);
            startCal.set(Calendar.MONTH, targetMonth);
            startCal.set(Calendar.DAY_OF_MONTH, 1);
            startCal.set(Calendar.HOUR_OF_DAY, 0);
            startCal.set(Calendar.MINUTE, 0);
            startCal.set(Calendar.SECOND, 0);
            Date startDate = startCal.getTime();

            Calendar endCal = Calendar.getInstance();
            endCal.set(Calendar.YEAR, targetYear);
            endCal.set(Calendar.MONTH, targetMonth);
            endCal.set(Calendar.DAY_OF_MONTH, endCal.getActualMaximum(Calendar.DAY_OF_MONTH));
            endCal.set(Calendar.HOUR_OF_DAY, 23);
            endCal.set(Calendar.MINUTE, 59);
            endCal.set(Calendar.SECOND, 59);
            Date endDate = endCal.getTime();

            List<Invoice> invoices = em.createQuery(
                    "SELECT i FROM Invoice i WHERE i.dateTime BETWEEN :startDate AND :endDate", Invoice.class)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getResultList();

            for (Invoice inv : invoices) {
                Calendar invoiceCal = Calendar.getInstance();
                invoiceCal.setTime(inv.getDateTime());
                int dayOfMonth = invoiceCal.get(Calendar.DAY_OF_MONTH);

                String weekKey;
                if (dayOfMonth <= 7)
                    weekKey = "Week 1";
                else if (dayOfMonth <= 14)
                    weekKey = "Week 2";
                else if (dayOfMonth <= 21)
                    weekKey = "Week 3";
                else
                    weekKey = "Week 4";

                weeklyRevenue.put(weekKey, weeklyRevenue.get(weekKey) + inv.getBillAmount());
            }
            return weeklyRevenue;
        });
    }
}
