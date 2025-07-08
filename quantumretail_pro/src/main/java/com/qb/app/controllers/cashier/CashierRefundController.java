/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class CashierRefundController implements Initializable {


    @FXML
    private Separator previewSeparator;
    @FXML
    private Label previewMessage;
    @FXML
    private Separator salePriceSeparator;
    @FXML
    private Label labelItemNewPrice;
    @FXML
    private VBox refundItemContainer;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
