package com.qb.app.controllers;

import com.qb.app.model.SVGIconGroup;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;

public class Cashier_top_panelController implements Initializable {

    @FXML
    private Group iconMenu;
    @FXML
    private Label panelTitle;
    @FXML
    private Group iconMinimize;
    @FXML
    private Group iconClose;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setIcons();
        setInitialState();
    }

    private void setIcons() {
        iconMenu.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/menu-icon.svg"));
        iconClose.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/close-icon.svg"));
        iconMinimize.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/minimize-icon.svg"));
    }

    private void setInitialState() {

    }

    public void setTitle(String title) {
        panelTitle.setText(title);
    }

}
