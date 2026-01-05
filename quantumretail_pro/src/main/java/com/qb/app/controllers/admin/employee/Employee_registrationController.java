/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.employee;

import com.qb.app.model.ComboBoxUtils;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.entity.EmployeePanel;
import com.qb.app.model.entity.EmployeeRole;
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

        }
    }

    private void registerEmployee() {

        String name = tfEmployeeName.getText().trim();
        String username = tfEmployeeUsername.getText().trim();
        String password = tfEmployeePassword.getText().trim();
        EmployeeRole role = cbEmployeeRole.getValue();

        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || role == null) {
            showWarning("Please fill all required fields.");
            return;
        }
    }
    
    
    private void showWarning(String message) {
        CustomAlert.showStyledAlert(
                root,
                message,
                "Validation Error",
                Alert.AlertType.WARNING
        );
    }

}
