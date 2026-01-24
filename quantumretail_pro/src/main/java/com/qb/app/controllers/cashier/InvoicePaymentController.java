/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class InvoicePaymentController implements Initializable {


    @FXML
    private AnchorPane root;
    @FXML
    private Group closeIcon;
    @FXML
    private Button btnInvoiceAmount;
    @FXML
    private Label invoiceItemCount;
    @FXML
    private Label invoiceSubTotal;
    @FXML
    private Label invoiceDiscount;
    @FXML
    private Label invoiceCreditAmount;
    @FXML
    private Label invoicePaidAmount;
    @FXML
    private Label invoiceBalance;
    @FXML
    private TextField tfCashAmount;
    @FXML
    private TextField tfCardAmount;
    @FXML
    private TextField tfCreditAmount;
    @FXML
    private Button btnAction;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void closeWindow(MouseEvent event) {
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
    }

}
