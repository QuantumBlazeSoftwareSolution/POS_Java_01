/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.employee;

import com.qb.app.model.ComboBoxUtils;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.PasswordEncryption;
import com.qb.app.model.SinhalaInputNormalizer;
import com.qb.app.model.entity.Employee;
import com.qb.app.model.entity.EmployeePanel;
import com.qb.app.model.entity.EmployeeRole;
import com.qb.app.model.entity.EmployeeStatus;
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Employee_registrationController implements Initializable {

    @FXML
    private Group IconEmployeeRegistrationTopic;
    @FXML
    private TextField tfEmployeeName;
    @FXML
    private ComboBox<EmployeePanel> cbEmployeePanel;
    @FXML
    private TextField tfEmployeeUsername;
    @FXML
    private TextField tfEmployeePassword;
    @FXML
    private TextField tfEmployeePin;
    @FXML
    private Button btnRecruit;
    @FXML
    private Button btnClear;
    @FXML
    private ComboBox<EmployeeRole> cbEmployeeRole;
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        tfEmployeePin.setDisable(true);

        cbEmployeeRole.valueProperty().addListener((obs, oldRole, newRole) -> {

            if (newRole == null) {
                tfEmployeePin.clear();
                tfEmployeePin.setDisable(true);
                return;
            }

            if ("Admin".equalsIgnoreCase(newRole.getRole())) {
                tfEmployeePin.setDisable(false);
                tfEmployeePin.requestFocus();
            } else {
                tfEmployeePin.clear();
                tfEmployeePin.setDisable(true);
            }
        });
        SinhalaInputNormalizer.applySinhalaFixRecursively(root);
        backgroundThread();
    }

    private void backgroundThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                initializeBackgroundProcess();
            }
        };

        Thread bgThread = new Thread(runnable);
        bgThread.start();
    }

    private void initializeBackgroundProcess() {
        loadComboBoxes();
    }

    private void loadComboBoxes() {
        ComboBoxUtils.loadComboBoxValues(cbEmployeeRole, EmployeeRole.class, "role", EmployeeRole::getRole);
        ComboBoxUtils.loadComboBoxValues(cbEmployeePanel, EmployeePanel.class, "type", EmployeePanel::getType);
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnRecruit) {
            registerEmployee();
        } else if (event.getSource() == btnClear) {
            clearFields();
        }
    }

    private void registerEmployee() {

        String name = tfEmployeeName.getText().trim();
        String username = tfEmployeeUsername.getText().trim();
        String password = tfEmployeePassword.getText().trim();
        EmployeeRole role = cbEmployeeRole.getValue();
        EmployeePanel panel = cbEmployeePanel.getValue();

        if (IsEmployeeValid()) {
            if (!isEmployeeExist()) {

                JPATransaction.runInTransaction(em -> {

                    EmployeeStatus activeStatus = em.createQuery(
                            "SELECT s FROM EmployeeStatus s WHERE LOWER(s.status) = :status",
                            EmployeeStatus.class
                    )
                            .setParameter("status", "active")
                            .getResultStream()
                            .findFirst()
                            .orElseThrow(()
                                    -> new RuntimeException("Default employee status 'Active' not found!")
                            );

                    Employee employee = new Employee();
                    employee.setName(name);
                    employee.setEmployeeRoleId(role);
                    employee.setUsername(username);

                    employee.setPassword(PasswordEncryption.hashPassword(password));

                    employee.setEmployeeStatusId(activeStatus);

                    if ("Admin".equalsIgnoreCase(cbEmployeeRole.getValue().getRole())) {
                        employee.setPin(tfEmployeePin.getText().trim());
                    }

                    em.persist(employee);
                    
                    clearFields();

                    CustomAlert.showStyledAlert(
                            root,
                            "Employee added successfully",
                            Alert.AlertType.CONFIRMATION
                    );
                });

            }
        }

    }

    private boolean IsEmployeeValid() {
        if (tfEmployeeName.getText().trim().isEmpty()) {
            showWarning("Employee name is required.");
            tfEmployeeName.requestFocus();
            return false;
        }

        if (tfEmployeeUsername.getText().trim().isEmpty()) {
            showWarning("Username is required.");
            tfEmployeeUsername.requestFocus();
            return false;
        }

        if (tfEmployeePassword.getText().trim().isEmpty()) {
            showWarning("Password is required.");
            tfEmployeePassword.requestFocus();
            return false;
        }

        if (cbEmployeeRole.getValue() == null) {
            showWarning("Employee role is required.");
            cbEmployeeRole.requestFocus();
            return false;
        }

        if (cbEmployeePanel.getValue() == null) {
            showWarning("Employee panel is required.");
            cbEmployeePanel.requestFocus();
            return false;
        }

        if ("Admin".equalsIgnoreCase(cbEmployeeRole.getValue().getRole())) {

            if (tfEmployeePin.getText().trim().isEmpty()) {
                showWarning("PIN is required for Admin role.");
                tfEmployeePin.requestFocus();
                return false;
            }

            if (!tfEmployeePin.getText().matches("\\d{4}")) {
                showWarning("PIN must be exactly 4 digits.");
                tfEmployeePin.requestFocus();
                return false;
            }
        }

        return true;
    }

    private boolean isEmployeeExist() {
        return JPATransaction.runInTransaction(em -> {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);

            Predicate usernamePredicate = cb.equal(
                    cb.lower(root.get("username")),
                    tfEmployeeUsername.getText().toLowerCase()
            );

            cq.where(usernamePredicate);

            return !em.createQuery(cq).getResultList().isEmpty();
        });
    }

    private void showWarning(String message) {
        CustomAlert.showStyledAlert(
                root,
                message,
                "Validation Error",
                Alert.AlertType.WARNING
        );
    }

    private void clearFields() {
        tfEmployeeName.clear();
        tfEmployeeUsername.clear();
        tfEmployeePassword.clear();
        tfEmployeePin.clear();
        cbEmployeeRole.setValue(null);
        cbEmployeePanel.setValue(null);
        tfEmployeePin.setDisable(true);
    }

    @FXML
    private void handleDetailsbyRole(ActionEvent event) {

    }

}
