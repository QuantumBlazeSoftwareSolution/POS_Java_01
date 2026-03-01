package com.qb.app.controllers.developer;

import com.qb.app.model.CustomAlert;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.InterfaceMortion;
import com.qb.app.model.PasswordEncryption;
import com.qb.app.model.SVGIconGroup;
import com.qb.app.session.ApplicationSession;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class actionVerificationController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Group closeIcon;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private PasswordField tfPin;
    @FXML
    private Button btnAction;

    private boolean actionConfirmed = false;

    public boolean isActionConfirmed() {
        return actionConfirmed;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        InterfaceMortion interfaceMortion = new InterfaceMortion();
        interfaceMortion.enableDrag(root);
        closeIcon.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/close-icon.svg"));
    }

    @FXML
    private void closeWindow(MouseEvent event) {
        InterfaceAction.closeWindow(root);
    }

    @FXML
    private void handlePinVerification(ActionEvent event) {
        if (event.getSource() == btnAction) {
            verifyDeveloper();
        }
    }

    private void verifyDeveloper() {
        if (entriesValid()) {
            if (isDeveloperExists()) {
                actionConfirmed = true;
                InterfaceAction.closeWindow(root);
            } else {
                CustomAlert.showStyledAlert(
                        root,
                        "The username, password, or PIN you entered is incorrect. Please verify and try again.",
                        "Credential mismatched",
                        Alert.AlertType.ERROR
                );
            }
        }
    }

    private boolean entriesValid() {
        if (tfUsername.getText().isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "Please enter your username to continue.",
                    "Missing Username",
                    Alert.AlertType.WARNING
            );
            tfUsername.requestFocus();
            return false;
        }

        if (tfPassword.getText().isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "Please enter your password for security verification.",
                    "Missing Password",
                    Alert.AlertType.WARNING
            );
            
            tfPassword.requestFocus();
            return false;
        }

        if (tfPin.getText().isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "A PIN is required for additional security. Please enter your PIN.",
                    "PIN Required",
                    Alert.AlertType.WARNING
            );
            tfPin.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isDeveloperExists() {
        return ApplicationSession.getEmployee().getUsername().equals(tfUsername.getText())
                && PasswordEncryption.verifyPassword(ApplicationSession.getEmployee().getPassword(), tfPassword.getText())
                && ApplicationSession.getEmployee().getPin().equals(tfPin.getText());
    }

}
