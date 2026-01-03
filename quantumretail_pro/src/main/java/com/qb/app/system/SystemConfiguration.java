package com.qb.app.system;

import com.qb.app.model.AESUtil;
import com.qb.app.model.getLogger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import org.json.JSONException;
import org.json.JSONObject;

public class SystemConfiguration {

    public static void createConfigurationFile() {
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

        JSONObject valueType = new JSONObject();
        valueType.put("CASH", "cash");
        valueType.put("PERCENTAGE", "percentage");
        bill_discount.put("valueType", valueType);

        JSONObject license = new JSONObject();
        license.put("isLicenseActive", false); // -1 for lifetime
        license.put("period", 0); // -1 for lifetime
        license.put("previous_period", 0); // -1 for lifetime
        license.put("status", ""); // 'trial' or 'life-time'
        license.put("charge", 0);
        license.put("init_date", "");
        license.put("previous_expiry_date", "");
        license.put("expiry_date", "");

        JSONObject licenseType = new JSONObject();
        licenseType.put("TRIAL", "trial");
        licenseType.put("LIFETIME", "life-time");
        license.put("licenseType", licenseType);

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

//                Files.deleteIfExists(path);
            } catch (Exception e) {
                System.err.println("Error encrypting config file: " + e.getMessage());
                e.printStackTrace();
            getLogger.logger().warning(e.toString());
            }
        } catch (IOException | JSONException e) {
            System.err.println("Error handling config file: " + e.getMessage());
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }
}
