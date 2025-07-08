package com.qb.app.controllers.developer;

import com.jfoenix.controls.JFXToggleButton;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeveloperOwnerShipManagementController implements Initializable{

    @FXML
    private TableView<Employee> table;
    @FXML
    private TableColumn<Employee, Integer> colID;
    @FXML
    private TableColumn<Employee, String> colName;
    @FXML
    private TableColumn<Employee, String> colUsername;
    @FXML
    private TableColumn<Employee, String> colStatus;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfPin;
    @FXML
    private JFXToggleButton toggleStatus;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnUpdate;
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        defineTableColumns();
        loadOwners();
    }

    private void defineTableColumns() {
        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colStatus.setCellValueFactory(cellData -> {
            EmployeeStatus status = cellData.getValue().getEmployeeStatusId();
            if (status != null) {
                return new SimpleStringProperty(status.getStatus());
            } else {
                return new SimpleStringProperty("N/A");
            }
        });
    }

    private void loadOwners() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        getOwnersList(employees);
        table.setItems(employees);
    }

    private void getOwnersList(ObservableList<Employee> employees) {
        JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);

            Predicate employeeRolePredicate = cb.equal(root.get("employeeRoleId"), getEmployeeRole());
            cq.where(employeeRolePredicate);

            List<Employee> result = em.createQuery(cq).getResultList();
            employees.addAll(result);
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

    private EmployeePanel getPanel() {
        return JPATransaction.runInTransaction((em) -> {
            return em.createNamedQuery("EmployeePanel.findByType", EmployeePanel.class).
                    setParameter("type", "Admin").
                    getSingleResult();
        });
    }

    @FXML
    private void rowSelection(MouseEvent event) {
        if (event.getClickCount() > 1) {
            getSelectedEmployee();
        }
    }

    private Employee selectedEmployee = null;

    private void getSelectedEmployee() {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Employee employee = table.getSelectionModel().getSelectedItem();
            selectedEmployee = employee;
            loadTheSelectedEmployee();
        }
    }

    private void loadTheSelectedEmployee() {
        tfName.setText(selectedEmployee.getName());
        tfUsername.setText(selectedEmployee.getUsername());
        tfPin.setText(selectedEmployee.getPin());
        toggleStatus.setSelected(selectedEmployee.getEmployeeStatusId().getStatus().equals("Active"));
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnUpdate) {
            executeWithVerification(this::verifyAndUpdateOwner);
        } else if (event.getSource() == btnClear) {
            clearInterface();
        }
    }

    private void executeWithVerification(Runnable action) {
        try {
            if (checkTheDeveloperVerification()) {
                action.run();
            } else {
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

    private boolean isLoginCredentialsTaken() {
        return JPATransaction.runInTransaction((em) -> {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
            Root<Employee> root = cq.from(Employee.class);

            Predicate usernamePredicate = cb.equal(root.get("username"), tfUsername.getText());
            Predicate employeeRolePredicate = cb.equal(root.get("employeeRoleId"), getEmployeeRole());
            Predicate loginPredicate;

            if (!tfPassword.getText().isEmpty()) {
                Predicate passwordPredicate = cb.equal(root.get("password"), PasswordEncryption.hashPassword(tfPassword.getText()));
                loginPredicate = cb.or(usernamePredicate, passwordPredicate);
            } else {
                loginPredicate = usernamePredicate;
            }

            cq.where(
                    cb.and(
                            loginPredicate,
                            employeeRolePredicate,
                            cb.notEqual(root.get("id"), selectedEmployee.getId()))
            );

            List<Employee> result = em.createQuery(cq).getResultList();

            return !result.isEmpty();
        });
    }

    private void updateOwner() {
        JPATransaction.runInTransaction((em) -> {
            selectedEmployee.setName(tfName.getText());
            selectedEmployee.setUsername(tfUsername.getText());
            if (!tfPassword.getText().isEmpty()) {
                selectedEmployee.setPassword(PasswordEncryption.hashPassword(tfPassword.getText()));
            }
            selectedEmployee.setPin(tfPin.getText());
            em.merge(selectedEmployee);

            CustomAlert.showStyledAlert(
                    root,
                    "Owner successfuly updated.",
                    "Success",
                    Alert.AlertType.INFORMATION
            );
            clearInterface();
        });
        loadOwners();
    }

    private void clearInterface() {
        selectedEmployee = null;
        tfName.setText("");
        tfUsername.setText("");
        tfPassword.setText("");
        tfPin.setText("");
    }

    private void verifyAndUpdateOwner() {
        if (selectedEmployee != null) {
            if (isEntriesValid()) {
                if (!isLoginCredentialsTaken()) {
                    updateOwner();
                } else {
                    CustomAlert.showStyledAlert(
                            root,
                            "The username or password you entered is already in use. Please choose different credentials.",
                            "VALIDATION ERROR",
                            Alert.AlertType.WARNING
                    );
                }
            }
        } else {
            CustomAlert.showStyledAlert(root, "Select a owner before you update the owner details", "No selected Employee", Alert.AlertType.WARNING);
        }
    }
}
