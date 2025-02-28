package com.qb.app.model;

import javafx.scene.control.Button;
import javafx.stage.Stage;

public class InderfaceAction {

    public static void closeWindow(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
