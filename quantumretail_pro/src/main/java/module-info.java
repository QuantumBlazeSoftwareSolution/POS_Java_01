module com.qb.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires org.hibernate.orm.core; // Hibernate ORM Dependency
    requires org.hibernate.validator; // Hibernate ORM Dependency
    requires java.sql; // Hibernate ORM Dependency
    requires jakarta.persistence; // Hibernate ORM Dependency
    
    requires java.naming;
    requires java.base;    
    requires java.desktop;
    requires com.github.weisj.jsvg;
    requires com.jfoenix;

    opens com.qb.app to javafx.fxml;
    opens com.qb.app.controllers to javafx.fxml;
    opens com.qb.app.model to javafx.fxml, javafx.base;
    opens com.qb.app.model.entity to org.hibernate.orm.core;
    exports com.qb.app;
    exports com.qb.app.model;
}
