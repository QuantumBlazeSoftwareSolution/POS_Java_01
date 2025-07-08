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

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class CashierInvoiceController implements Initializable {


    @FXML
    private AnchorPane root;
    @FXML
    private TextField tfBarCode;
    @FXML
    private TextField tfItemCode;
    @FXML
    private TextField tfItemCode1;
    @FXML
    private Button btnProductView;
    @FXML
    private ImageView itemImage;
    @FXML
    private Label labelItemName;
    @FXML
    private Separator previewSeparator;
    @FXML
    private Label previewMessage;
    @FXML
    private Label labelItemPrice;
    @FXML
    private Separator salePriceSeparator;
    @FXML
    private Label labelItemNewPrice;
    @FXML
    private Button btnDecreaseQty;
    @FXML
    private Button btnViewQty;
    @FXML
    private Button btnIncreaseQty;
    @FXML
    private Button itemPrice;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnAdd;
    @FXML
    private Label invoiceItemCount;
    @FXML
    private Label invoiceSubTotal;
    @FXML
    private Label invoiceDiscount;
    @FXML
    private Label invoiceTotal;
    @FXML
    private Button btnPayment;
    @FXML
    private ScrollPane invoiceScrollContainer;
    @FXML
    private VBox invoiceItemContainer;
    @FXML
    private ScrollBar invoiceScroller;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void itemCodePressed(KeyEvent event) {
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
    }

    @FXML
    private void handleQuantityAmount(ActionEvent event) {
    }

}
