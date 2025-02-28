package com.qb.app.controllers;

import com.qb.app.model.InderfaceAction;
import com.qb.app.model.SVGIconGroup;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class Cashier_top_panelController implements Initializable {

    @FXML
    private Group iconMenu;
    @FXML
    private Label panelTitle;
    @FXML
    private Group iconMinimize;
    @FXML
    private Group iconClose;

    private PanelCashierController panelCashierController; // Reference to PanelCashierController

    public void setPanelCashierController(PanelCashierController panelCashierController) {
        this.panelCashierController = panelCashierController;
    }

    @FXML
    private void toggleMenu() {
        if (panelCashierController != null) {
            panelCashierController.toggleMenu(); // Call the toggleMenu method in PanelCashierController
        }
    }

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

    @FXML
    private void titleBarActionHandler(MouseEvent event) {
        if (event.getSource() == iconClose) {
            InderfaceAction.closeWindow(iconClose);
        } else if (event.getSource() == iconMinimize) {
            InderfaceAction.minimizeWindow(iconMinimize);
        }
    }

}
