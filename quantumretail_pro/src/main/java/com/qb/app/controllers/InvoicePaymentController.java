package com.qb.app.controllers;

import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.InterfaceAction;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class InvoicePaymentController implements Initializable {

    @FXML
    private StackPane closeIcon;
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

    private CashierInvoiceController controller;
    private boolean isPaymentActive;
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfCashAmount.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfCardAmount.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfCreditAmount.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        ChangeListener<String> paymentFieldListener = (obs, oldVal, newVal) -> {
            isPaymentActive = false;
            btnAction.setText("Calculate Invoice");
        };
        tfCashAmount.textProperty().addListener(paymentFieldListener);
        tfCardAmount.textProperty().addListener(paymentFieldListener);
        tfCreditAmount.textProperty().addListener(paymentFieldListener);
    }

    @FXML
    private void closeWindow(MouseEvent event) {
        InterfaceAction.closeWindow(closeIcon);
    }

    public void saveProductRegistrationController(CashierInvoiceController controller) {
        this.controller = controller;
    }

    public void setItems(List<InvoiceItemController> invoiceItemList) {
        double itemCount = 0;
        double subTotal = 0;
        double discount = 0;
        for (InvoiceItemController invoiceItemController : invoiceItemList) {
            itemCount += invoiceItemController.getProductQty();
            subTotal += invoiceItemController.getProduct().getSalePrice() * invoiceItemController.getProductQty();
            discount += invoiceItemController.getProduct().getDiscount() * invoiceItemController.getProductQty();
        }
        invoiceItemCount.setText(String.valueOf(invoiceItemList.size()));
        invoiceItemCount.setText(String.valueOf(itemCount));
        invoiceSubTotal.setText(String.format("Rs. %, .2f", subTotal));
        invoiceDiscount.setText(String.format("Rs. %, .2f", discount));
        btnInvoiceAmount.setText(String.format("Rs. %, .2f", (subTotal - discount)));
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnAction) {
            calculateInvoice();
            isPaymentActive = true;
            btnAction.setText("Complete Invoice");
        }
    }

    private void calculateInvoice() {
        if (validateTextFields()) {
        }
    }

    private boolean validateTextFields() {
        if (tfCashAmount.getText().isEmpty() && tfCardAmount.getText().isEmpty() && tfCreditAmount.getText().isEmpty()) {
            CustomAlert.showStyledAlert(root, "Please enter payment amount in at least one payment method (Cash, Card, or Credit).", "Payment Required", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

}
