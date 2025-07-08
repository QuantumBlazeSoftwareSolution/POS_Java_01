package com.qb.app.controllers.developer;

import com.qb.app.App;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.InterfaceMortion;
import com.qb.app.model.SVGIconGroup;
import com.qb.app.model.getLogger;
import com.qb.app.session.ApplicationSession;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class DeveloperVerificationController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Rectangle quantumBlazeIcon;
    @FXML
    private Group iconDeveloper;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button btnVerify;
    @FXML
    private Button btnExit;
    @FXML
    private Group iconExit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setIcons();
        setMouseEvent();
        setQBImage();
        Platform.runLater(() -> {
            tfPassword.requestFocus();
        });
    }

    @FXML
    private void handleSystemVerification(ActionEvent event) {
        if (event.getSource() == btnVerify) {
            verifyDeveloper();
        } else if (event.getSource() == btnExit) {
            closeTheSystem();
        }
    }

    private void setIcons() {
        iconDeveloper.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/users-solid.svg"));
        iconExit.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/exit-solid.svg"));
    }

    private void verifyDeveloper() {
        if (!tfPassword.getText().isEmpty()) {
            if (checkPinNumberIsCorrect()) {
                navigateDeveloperPanel();
            } else {
                CustomAlert.showStyledAlert(root, "Invalid PIN number.", "Invalid PIN", Alert.AlertType.WARNING);
            }
        } else {
            CustomAlert.showStyledAlert(root, "PIN number cannot be empty.", "Validation Error", Alert.AlertType.WARNING);
        }
    }

    private void closeTheSystem() {
        try {
            App.setRoot("sytemLogin");
        } catch (IOException e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    private void setQBImage() {
        Image image = new Image(getClass().getResource("/com/qb/app/assets/images/QB_LOGO.png").toExternalForm());
        quantumBlazeIcon.setFill(new ImagePattern(image));
    }

    private void setMouseEvent() {
        InterfaceMortion interfaceMortion = new InterfaceMortion();
        interfaceMortion.enableDrag(root);
    }

    private boolean checkPinNumberIsCorrect() {
        return ApplicationSession.getEmployee().getPin().equals(tfPassword.getText());
    }

    private void navigateDeveloperPanel() {
        try {
            App.setRoot("developer/panelDeveloper");
        } catch (IOException e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }
}
