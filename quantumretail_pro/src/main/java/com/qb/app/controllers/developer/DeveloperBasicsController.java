package com.qb.app.controllers.developer;

import com.qb.app.model.Config;
import com.qb.app.model.ConfigManager;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.PopUp;
import com.qb.app.model.getLogger;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeveloperBasicsController implements Initializable {

    @FXML
    private TextField tfSystemName;
    @FXML
    private TextField tfTelephone_01;
    @FXML
    private TextField tfTelephone_02;
    @FXML
    private TextField tfAddress;
    @FXML
    private Button btnAction;
    @FXML
    private AnchorPane root;

    private Config config;
    private boolean btnIsPrimary = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadConfig();
    }

    private void loadConfig() {
        try {
            config = ConfigManager.loadConfig();
            loadTheContent();
        } catch (Exception e) {
            e.printStackTrace();
        getLogger.logger().warning(e.toString());
        }
    }

    private void checkIfSystemInitialized() {
        if (this.config.isInitialized) {
            btnAction.setText("Update Changes");
            btnAction.getStyleClass().add("button-primary-outline");
            this.btnIsPrimary = false;
        }
    }

    private void loadTheContent() {
        checkIfSystemInitialized();
        if (!this.config.system_name.isEmpty()) {
            tfSystemName.setText(this.config.system_name);
        }
        if (!this.config.telephone_01.isEmpty()) {
            tfTelephone_01.setText(this.config.telephone_01);
        }
        if (!this.config.telephone_02.isEmpty()) {
            tfTelephone_02.setText(this.config.telephone_02);
        }
        if (!this.config.address.isEmpty()) {
            tfAddress.setText(this.config.address);
        }
    }

    private void executeWithVerification(Runnable action) {
        try {
            if (checkTheDeveloperVerification()) {
                action.run();
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

    @FXML
    void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnAction) {
            if (this.config.license.isLicenseActive) {
                executeWithVerification(this::doTheAction);
            } else {
                CustomAlert.showStyledAlert(
                        root,
                        "License is not activated yet.",
                        "Incomplete License",
                        Alert.AlertType.WARNING);
            }
        }
    }

    private void doTheAction() {
        if (validateInputs()) {
            this.config.system_name = tfSystemName.getText();
            this.config.telephone_01 = tfTelephone_01.getText();
            if (!tfTelephone_02.getText().isEmpty()) {
                this.config.telephone_02 = tfTelephone_02.getText();
            }
            this.config.address = tfAddress.getText();
            if (btnIsPrimary) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate initDate = LocalDate.now();
                LocalDate expiryDate = initDate.plusMonths(this.config.license.period);
                this.config.license.init_date = initDate.format(formatter);
                this.config.license.expire_date = expiryDate.format(formatter);
                this.config.isInitialized = true;
            }
            try {
                ConfigManager.saveConfig(config);
                String message = btnIsPrimary ? "System Initialization Completed." : "System Update Completed.";
                CustomAlert.showStyledAlert(root, message, "Success", Alert.AlertType.INFORMATION);
                checkIfSystemInitialized();
            } catch (Exception e) {
                e.printStackTrace();
        getLogger.logger().warning(e.toString());
            }
        }
    }

    private boolean validateInputs() {
        if (tfSystemName.getText().isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "System name is a required field. Please enter a valid name.",
                    "Validation Error",
                    Alert.AlertType.WARNING);
            tfSystemName.requestFocus();
            return false;
        }

        if (tfTelephone_01.getText().isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "Primary telephone number is required. Please provide a contact number.",
                    "Validation Error",
                    Alert.AlertType.WARNING);
            tfTelephone_01.requestFocus();
            return false;
        }

        if (!DefaultAPI.isInteger(tfTelephone_01.getText())) {
            CustomAlert.showStyledAlert(
                    root,
                    "Invalid primary telephone number format. Please enter numeric values only.",
                    "Validation Error",
                    Alert.AlertType.WARNING);
            tfTelephone_01.requestFocus();
            return false;
        }

        if (!tfTelephone_02.getText().isEmpty() && !DefaultAPI.isInteger(tfTelephone_02.getText())) {
            CustomAlert.showStyledAlert(
                    root,
                    "Secondary telephone number contains invalid characters. Only digits are allowed.",
                    "Validation Error",
                    Alert.AlertType.WARNING);
            tfTelephone_02.requestFocus();
            return false;
        }

        if (tfAddress.getText().isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "Address field cannot be blank. Please provide a physical address.",
                    "Validation Error",
                    Alert.AlertType.WARNING);
            tfAddress.requestFocus();
            return false;
        }

        return true;
    }
}
