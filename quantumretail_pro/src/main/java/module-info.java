module com.qb.app {
    
    // ✅ JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    
    // ✅ Hibernate & JPA
    requires org.hibernate.orm.core; // Hibernate ORM Dependency
    requires org.hibernate.validator; // Hibernate ORM Dependency
    requires java.sql; // Hibernate ORM Dependency
    requires jakarta.persistence; // Hibernate ORM Dependency
    
    // ✅ Encryption & utilities
    requires de.mkammerer.argon2; // Password encryption dependancy
    requires de.mkammerer.argon2.nolibs; // Password encryption dependancy
    requires org.apache.commons.lang3; // random number generator
    requires com.sun.jna;
    
    requires java.naming;
    requires java.base;    
    requires java.desktop;
    requires com.github.weisj.jsvg;
    requires com.jfoenix;
    
    requires jasperreports;
    requires jasperreports.fonts;
    requires commons.beanutils;
    requires barbecue;
    
    requires org.json;
    requires com.google.gson;

    opens com.qb.app to javafx.fxml;
    opens com.qb.app.model to javafx.fxml, javafx.base;
    opens com.qb.app.controllers to javafx.fxml;
    opens com.qb.app.controllers.admin to javafx.fxml;
    opens com.qb.app.controllers.cashier to javafx.fxml;
//    opens com.qb.app.controllers.report.beans to javafx.fxml, jasperreports, commons.beanutils;
    opens com.qb.app.controllers.admin.employee to javafx.fxml;
    opens com.qb.app.controllers.admin.inventory to javafx.fxml;
    opens com.qb.app.controllers.admin.product to javafx.fxml;
    opens com.qb.app.controllers.admin.supply to javafx.fxml;
    opens com.qb.app.controllers.developer to javafx.fxml;
    opens com.qb.app.controllers.report to javafx.fxml;
    opens com.qb.app.model.entity to javafx.base, org.hibernate.orm.core;
    exports com.qb.app;
    exports com.qb.app.model;
//    exports com.qb.app.controllers.report.beans;
}
