module com.qb.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires org.hibernate.orm.core;
    requires org.hibernate.validator;
    requires java.sql;
    requires jakarta.persistence;
    requires java.naming;
    requires java.base;    
    requires fontawesomefx;
    requires java.desktop;
    requires com.github.weisj.jsvg;

    opens com.qb.app to javafx.fxml;
    opens com.qb.app.controllers to javafx.fxml;
    opens com.qb.app.model to javafx.fxml, javafx.base; // Add javafx.base
    opens com.qb.app.model.entity to org.hibernate.orm.core;
    exports com.qb.app;
    exports com.qb.app.model;
}
