/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;


import com.qb.app.model.DefaultAPI;
import com.qb.app.session.ApplicationSession;
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
        tfCashier.setText(ApplicationSession.getSession().getEmployeeId().getName());
        tfDayIn.setText(df.format(ApplicationSession.getSession().getDayInTime()));
        tfDayOff.setText(df.format(ApplicationSession.getSession().getDayOutTime()));

        tfCollection.setText("Rs. 0.00");
    }


    
    
    @FXML
    private void handleKeyEvent(KeyEvent event) {
        if (event.getSource() == tf5000Qty) {
            setCashValue(tf5000Qty, tf5000Value, 5000);
        }
        if (event.getSource() == tf1000Qty) {
            setCashValue(tf1000Qty, tf1000Value, 1000);
        }
        if (event.getSource() == tf500Qty) {
            setCashValue(tf500Qty, tf500Value, 500);
        }
        if (event.getSource() == tf100Qty) {
            setCashValue(tf100Qty, tf100Value, 100);
        }
        if (event.getSource() == tf50Qty) {
            setCashValue(tf50Qty, tf50Value, 50);
        }
        if (event.getSource() == tf20Qty) {
            setCashValue(tf20Qty, tf20Value, 20);
        }
        if (event.getSource() == tf10Qty) {
            setCashValue(tf10Qty, tf10Value, 10);
        }
        if (event.getSource() == tf5Qty) {
            setCashValue(tf5Qty, tf5Value, 5);
        }

        double total = calculateTotalCollection();
        tfCollection.setText(String.format("Rs. %,.2f", total));
    }
    
    
    private void setCashValue(TextField qtyField, TextField valueField, int denomination) {
        if (!qtyField.getText().isEmpty()) {
            int qty = Integer.parseInt(qtyField.getText());
            valueField.setText(
                String.format("Rs. %,d.00", qty * denomination)
            );
        } else {
            valueField.setText("");
        }
    }
    
    
    private double calculateTotalCollection() {
        double total = 0;

        total += getDenominationTotal(tf5000Qty, 5000);
        total += getDenominationTotal(tf1000Qty, 1000);
        total += getDenominationTotal(tf500Qty, 500);
        total += getDenominationTotal(tf100Qty, 100);
        total += getDenominationTotal(tf50Qty, 50);
        total += getDenominationTotal(tf20Qty, 20);
        total += getDenominationTotal(tf10Qty, 10);
        total += getDenominationTotal(tf5Qty, 5);

        return total;
    }
    
    
    private double getDenominationTotal(TextField qtyField, int denomination) {
        try {
            if (!qtyField.getText().isEmpty()) {
                return Integer.parseInt(qtyField.getText()) * denomination;
            }
        } catch (NumberFormatException ignored) {
        }
        return 0;
    }

    @FXML
    private void ActionEvent(ActionEvent event) {
    }


}
