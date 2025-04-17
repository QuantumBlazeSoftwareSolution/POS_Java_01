package com.qb.app.controllers;

import com.qb.app.App;
import com.qb.app.model.HibernateUtil;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.InterfaceMortion;
import com.qb.app.model.PasswordEncryption;
import com.qb.app.model.SVGIconGroup;
import com.qb.app.model.entity.Employee;
import com.qb.app.model.entity.EmployeeRole;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.hibernate.Session;

public class SytemLoginController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    //    <editor-fold desc="FXML init component" defaultstate="collapsed">
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;
    @FXML
    private Group iconExit;
    @FXML
    private AnchorPane root;
    @FXML
    private Circle quantumBlazeIcon;
    @FXML
    private Group iconUser;
    //    </editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setMouseEvent();
        setInitialState();
        setQBImage();
    }

    @FXML
    private void handleSystemLogin(ActionEvent event) {
        if (event.getSource() == btnLogin) {
            systemLogin();
        } else if (event.getSource() == btnExit) {
            InterfaceAction.closeWindow(btnExit);
        }
    }

    private void systemLogin() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("storageUnit"); // replace with your persistence unit name
        try (EntityManager em = emf.createEntityManager()) {
            // Prepare CriteriaBuilder and Query
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> employeeRoot = cq.from(Employee.class);

            // Join with employeeRole (assumes proper mapping exists in Employee entity)
            Join<Employee, EmployeeRole> roleJoin = employeeRoot.join("employeeRole");

            // Filter by username
            Predicate usernamePredicate = cb.equal(employeeRoot.get("username"), tfUsername.getText());

            cq.select(employeeRoot).where(cb.and(usernamePredicate));

            List<Employee> employees = em.createQuery(cq).getResultList();

            if (employees.isEmpty()) {
                System.out.println("No user found with this username");
                return;
            }

            Employee emp = employees.get(0);
            String enteredPassword = tfPassword.getText();

            if (PasswordEncryption.verifyPassword(emp.getPassword(), enteredPassword)) {
                String role = emp.getEmployeeRoleId().getRole().toLowerCase();
                System.out.println("Login successful. Welcome " + role + ": " + emp.getName());

                try {
                    switch (role) {
                        case "admin" ->
                            App.setRoot("panelAdmin");
                        case "cashier" ->
                            App.setRoot("panelCashier");
                        case "developer" ->
                            App.setRoot("panelDeveloper");
                        default ->
                            System.out.println("Unknown role: " + role);
                    }
                } catch (IOException e) {
                    System.out.println("Navigation error: " + e.getMessage());
                }
            } else {
                System.out.println("Incorrect password");
            }
        } finally {
            emf.close();
        }
    }

    private void setInitialState() {
        setIcons();
        Rectangle clip = new Rectangle(root.getPrefWidth(), root.getPrefHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        root.setClip(clip);
    }

    private void setIcons() {
        iconUser.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/users-solid.svg"));
        iconExit.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/exit-solid.svg"));
    }

    private void setMouseEvent() {
        InterfaceMortion interfaceMortion = new InterfaceMortion();
        interfaceMortion.enableDrag(root);
    }

    private void setQBImage() {
        Image image = new Image(getClass().getResource("/com/qb/app/assets/images/QB_LOGO.png").toExternalForm());
        quantumBlazeIcon.setFill(new ImagePattern(image));
    }
}
