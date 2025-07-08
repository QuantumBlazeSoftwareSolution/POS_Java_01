package com.qb.app.model;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class CustomAlert {

    public static void showStyledAlert(Node node, String message, Alert.AlertType type) {
        ceateAlert(node, message, null, type);
    }

    public static void showStyledAlert(Node node, String message, String title, Alert.AlertType type) {
        ceateAlert(node, message, title, type);
    }

    private static void ceateAlert(Node node, String message, String title, Alert.AlertType type) {
        Window owner = node.getScene().getWindow();
        Alert alert = new Alert(type);
        alert.setTitle(title != null ? title : "System Notification");
        alert.setHeaderText(null);
        alert.setContentText(message);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(CustomAlert.class.getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));
        
        if (owner != null) {
            stage.initOwner(owner);
        }

        alert.show();
    }
}
