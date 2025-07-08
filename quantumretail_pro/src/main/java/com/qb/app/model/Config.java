package com.qb.app.model;

public class Config {

    public License license;
    public String address;
    public System system;
    public BillDiscount bill_discount;
    public String telephone_01;
    public String telephone_02;
    public String system_name;
    public boolean isInitialized;
    public Report report;
    public StockAdjustment stock_adjustment;

        public static class License {

            public boolean isLicenseActive;
            public int period;
            public int previous_period;
            public double charge;
            public String status;
            public String init_date;
            public String expire_date;
            public String previous_expire_date;
            public LicenseType licenseType;

            public class LicenseType {

                public String TRIAL = "trial";
                public String LIFETIME = "life-time";
            }
        }

    public static class System {

        public boolean employee_management;
        public boolean bill_discount;
        public boolean inventory_management;
        public boolean product_discount;
        public boolean cash_withdrawal;
        public boolean credit_payment;
        public boolean refund;
        public boolean multi_stock;
        public boolean expire_tracking; 
    }

    public static class BillDiscount {


        public int amount;
        public String type;
        public Type valueType;
        
        public class Type {

            public String CASH = "cash";
            public String PERCENTAGE = "percentage";
        }
    }

    public static class Report {

        public boolean damage;
        public boolean grn;
        public boolean bin;
        public boolean cash_withdrawal;
        public boolean session;
        public boolean sale_summary;
        public boolean stock_balance;
        public boolean close_sale;
        public boolean sale_detail;
        public boolean location_return;
        public boolean sale_product;
        public boolean distribute;
        public boolean product_list;
        public boolean profit;
        public boolean customer;
        public boolean supplyOrder;
    }

    public static class StockAdjustment {

        public int adjustment_count;
        public int tempory_chance;
    }
}
