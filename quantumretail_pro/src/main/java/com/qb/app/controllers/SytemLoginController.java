package com.qb.app.controllers;

import com.qb.app.App;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.InterfaceMortion;
import com.qb.app.model.JpaUtil;
import com.qb.app.model.PasswordEncryption;
import com.qb.app.model.SVGIconGroup;
import com.qb.app.model.entity.Employee;
import com.qb.app.session.ApplicationSession;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.IOException;
import java.net.URL;
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

public class SytemLoginController implements Initializable {

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
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = JpaUtil.getEntityManager(); // Your utility method for getting EntityManager
            transaction = em.getTransaction();
            transaction.begin();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> employeeRoot = cq.from(Employee.class);

            // Filter by username
            Predicate usernamePredicate = cb.equal(employeeRoot.get("username"), tfUsername.getText());
            cq.where(usernamePredicate);

            // Execute query
            TypedQuery<Employee> query = em.createQuery(cq);
            Employee emp = null;
            try {
                emp = query.getSingleResult();
                ApplicationSession.setEmployee(emp);
            } catch (NoResultException e) {
                // No user found
                emp = null;
            }

            if (emp == null) {
                System.out.println("No user found with this username");
                return;
            }

            String enteredPassword = tfPassword.getText();

            if (PasswordEncryption.verifyPassword(emp.getPassword(), enteredPassword)) {
                String role = emp.getEmployeeRoleId().getRole().toLowerCase(); // Assuming employeeRoleId is the FK
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

            transaction.commit();
        } catch (Exception e) {
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
