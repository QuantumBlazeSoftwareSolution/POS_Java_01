package com.qb.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class Admin_top_panelController implements Initializable {

    @FXML
    private StackPane iconMenu;
    private PanelAdminController panelAdminController;
    @FXML
    private Label employeeName;
    @FXML
    private Label employeeType;
    @FXML
    private Circle employeeImage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void toggleMenu(MouseEvent event) {
        if (panelAdminController != null) {
            panelAdminController.toggleMenu(); // Call the toggleMenu method in PanelCashierController
        }
    }

    
    public void setPanelAdminController(PanelAdminController panelAdminController) {
        this.panelAdminController = panelAdminController;
    }    
}
