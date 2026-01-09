/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;

import com.qb.app.model.SuggestionPopupService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
    private TextField tfItemName;

    private SuggestionPopupService suggestionService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        suggestionService = new SuggestionPopupService();

        // Fake realistic product codes
        suggestionService.attach(tfItemCode, query
                -> FakeProductData.searchProductCodes(query)
        );

        // Fake realistic product names
        suggestionService.attach(tfItemName, query
                -> FakeProductData.searchProductNames(query)
        );

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

class FakeProductData {

    private static final List<String> PRODUCT_CODES = List.of(
            "PRD-1001",
            "PRD-1002",
            "PRD-1003",
            "PRD-2001",
            "PRD-2002",
            "PRD-3001"
    );

    private static final List<String> PRODUCT_NAMES = List.of(
            "Paracetamol 500mg",
            "Panadol Extra",
            "Amoxicillin 250mg",
            "Vitamin C Tablets",
            "Cough Syrup",
            "Antiseptic Solution"
    );

    public static List<String> searchProductCodes(String query) {
        return PRODUCT_CODES.stream()
                .filter(code -> code.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public static List<String> searchProductNames(String query) {
        return PRODUCT_NAMES.stream()
                .filter(name -> name.toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
}
