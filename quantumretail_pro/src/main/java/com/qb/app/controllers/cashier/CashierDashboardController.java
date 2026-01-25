/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;

import com.qb.app.model.ControllerClose;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class CashierDashboardController implements Initializable, ControllerClose {

    @FXML
    private Label labelYear;
    @FXML
    private Label labelMonth;
    @FXML
    private Label labelDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void close() {

    }

}
