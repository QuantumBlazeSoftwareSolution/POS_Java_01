package com.qb.app.controllers.developer;

import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.PasswordEncryption;
import com.qb.app.model.PopUp;
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
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeveloperOwnerShipTransferringController implements Initializable{

    @FXML
    private TextField tfName;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfPin;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnAction;
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnAction) {
            if (isEntriesValid()) {
                if (!isLoginCredentialsTaken()) {
                    executeWithVerification(this::createOwner);
                } else {
                    CustomAlert.showStyledAlert(
                            btnAction,
                            "The username or password you entered is already in use. Please choose different credentials.",
                            "VALIDATION ERROR",
                            Alert.AlertType.WARNING
                    );
                }
            }
        } else if (event.getSource() == btnClear) {
            clearInterface();
        }
    }

    private boolean isEntriesValid() {
        if (tfName.getText().isEmpty()) {
            CustomAlert.showStyledAlert(tfName, "Name cannot be empty. Please provide a valid name for admin", "VALIDATION ERROR", Alert.AlertType.WARNING);
            tfName.requestFocus();
            return false;
        }
        if (tfUsername.getText().isEmpty()) {
            CustomAlert.showStyledAlert(tfUsername, "Username cannot be empty. Please provide a valid username for admin", "VALIDATION ERROR", Alert.AlertType.WARNING);
            tfUsername.requestFocus();
            return false;
        }
        if (tfPassword.getText().isEmpty()) {
            CustomAlert.showStyledAlert(tfPassword, "Password cannot be empty. Please provide a valid password for admin", "VALIDATION ERROR", Alert.AlertType.WARNING);
            tfPassword.requestFocus();
            return false;
        }
        if (tfPin.getText().isEmpty()) {
            CustomAlert.showStyledAlert(tfPin, "PIN number cannot be empty. Please provide a valid PIN number for admin", "VALIDATION ERROR", Alert.AlertType.WARNING);
            tfPin.requestFocus();
            return false;
        }

        if (!tfPin.getText().isEmpty() && !DefaultAPI.isInteger(tfPin.getText())) {
            CustomAlert.showStyledAlert(tfPin, "Invalid PIN number, use digits", "VALIDATION ERROR", Alert.AlertType.WARNING);
            tfPin.requestFocus();
            return false;
        }

        return true;
    }

    private void createOwner() {
        Employee employee = new Employee();
        employee.setName(tfName.getText());
        employee.setUsername(tfUsername.getText());
        employee.setPassword(PasswordEncryption.hashPassword(tfPassword.getText()));
        employee.setPin(tfPin.getText());
        employee.setEmployeeRoleId(getEmployeeRole());
        employee.setEmployeeStatusId(getEmployeeStatus());
        JPATransaction.runInTransaction((em) -> {
            em.persist(employee);
            showTransferSuccessMessage();
            clearInterface();
        });
    }

    private EmployeeRole getEmployeeRole() {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<EmployeeRole> cq = cb.createQuery(EmployeeRole.class);
            Root<EmployeeRole> root = cq.from(EmployeeRole.class);

            Predicate panelPredicate = cb.equal(root.get("employeePanelId"), getPanel());
            Predicate rolePredicate = cb.equal(root.get("role"), "Admin");
            cq.where(cb.and(panelPredicate, rolePredicate));

            EmployeeRole employeeRole = em.createQuery(cq).getSingleResult();
            return employeeRole;
        });
    }

    private EmployeeStatus getEmployeeStatus() {
        return JPATransaction.runInTransaction((em) -> {
            return em.createNamedQuery("EmployeeStatus.findByStatus", EmployeeStatus.class).
                    setParameter("status", "Active").
                    getSingleResult();
        });
    }

    private EmployeePanel getPanel() {
        return JPATransaction.runInTransaction((em) -> {
            return em.createNamedQuery("EmployeePanel.findByType", EmployeePanel.class).
                    setParameter("type", "Admin").
                    getSingleResult();
        });
    }

    private void showTransferSuccessMessage() {
        Alert transferAlert = new Alert(Alert.AlertType.INFORMATION);
        Stage alertStage = (Stage) transferAlert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));
        transferAlert.setTitle("Administrator Successfully Assigned");
        transferAlert.setHeaderText("System Ownership Transferred");
        transferAlert.setContentText("New administrator privileges have been granted successfully.");
        transferAlert.showAndWait();
    }

    private void clearInterface() {
        tfName.setText("");
        tfUsername.setText("");
        tfPassword.setText("");
        tfPin.setText("");
    }

    private void executeWithVerification(Runnable action) {
        try {
            if (checkTheDeveloperVerification()) {
                action.run();
            }else{
                clearInterface();
            }
        } catch (Exception e) {
            e.printStackTrace();
        getLogger.logger().warning(e.toString());
        }
    }

    private boolean checkTheDeveloperVerification() throws Exception {
        actionVerificationController[] ref = new actionVerificationController[1];
        PopUp.showPopupAndWait(
                "developer/developerActionVerification.fxml",
                root,
                this.root.getScene(),
                PopUp.PopupType.CENTERED_50_WIDTH,
                (actionVerificationController controller) -> {
                    ref[0] = controller;
                }
        );

        if (ref[0] != null && ref[0].isActionConfirmed()) {
            return true;
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            Stage errorAlertStage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
            errorAlertStage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));
            errorAlert.setTitle("Access Denied");
            errorAlert.setHeaderText("Developer Verification Failed");
            errorAlert.setContentText("Fail to verify you as a developer.");
            errorAlert.showAndWait();
            return false;
        }
    }
    
    private boolean isLoginCredentialsTaken() {
        return JPATransaction.runInTransaction((em) -> {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);

            Predicate usernamePredicate = cb.equal(root.get("username"), tfUsername.getText());
            Predicate passwordPredicate = cb.equal(root.get("password"), PasswordEncryption.hashPassword(tfPassword.getText()));
            Predicate employeeRolePredicate = cb.equal(root.get("employeeRoleId"), getEmployeeRole());
            cq.where(
                    cb.and(
                            cb.or(
                                    usernamePredicate,
                                    passwordPredicate),
                            employeeRolePredicate)
            );

            List<Employee> result = em.createQuery(cq).getResultList();

            return !result.isEmpty();
        });
    }

}
