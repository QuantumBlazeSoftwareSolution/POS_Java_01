/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;


import com.qb.app.model.DefaultAPI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author user
 */
public class CashierCloseSaleController implements Initializable {


    @FXML
    private AnchorPane root;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfCashier;
    @FXML
    private TextField tfDayIn;
    @FXML
    private TextField tfDayOff;
    @FXML
    private TextField tf5000Qty;
    @FXML
    private TextField tf5000Value;
    @FXML
    private TextField tf1000Qty;
    @FXML
    private TextField tf1000Value;
    @FXML
    private TextField tf500Qty;
    @FXML
    private TextField tf500Value;
    @FXML
    private TextField tf100Qty;
    @FXML
    private TextField tf100Value;
    @FXML
    private TextField tf50Qty;
    @FXML
    private TextField tf50Value;
    @FXML
    private TextField tf20Qty;
    @FXML
    private TextField tf20Value;
    @FXML
    private TextField tf10Qty;
    @FXML
    private TextField tf10Value;
    @FXML
    private TextField tf5Qty;
    @FXML
    private TextField tf5Value;
    @FXML
    private TextField tfCollection;
    @FXML
    private Button btnAction;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        initializeTextFields();
    }
    
    
        private void initializeTextFields() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

        // Allow numbers only
        tf5000Qty.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tf1000Qty.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tf500Qty.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tf100Qty.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tf50Qty.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tf20Qty.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tf10Qty.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tf5Qty.setTextFormatter(DefaultAPI.createNumericTextFormatter());

        // Example placeholders (replace with session data)
        tfCashier.setText("Cashier Name");
        tfDayIn.setText(df.format(new java.util.Date()));
        tfDayOff.setText(df.format(new java.util.Date()));

        tfCollection.setText("Rs. 0.00");
    }


    
    
    @FXML
    private void handleKeyEvent(KeyEvent event) {
    }

    @FXML
    private void ActionEvent(ActionEvent event) {
    }


}
