/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;

import com.qb.app.controllers.popup.PopUpProductListController;
import com.qb.app.controllers.table_models.CashierInvoiceTable;
import com.qb.app.database_crud.ProductStatusCRUD;
import com.qb.app.model.Config;
import com.qb.app.model.ConfigManager;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.PopUp;
import com.qb.app.model.SuggestionPopupService;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.getLogger;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

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
    @FXML
    private TableView<CashierInvoiceTable> tableInvoice;
    @FXML
    private TableColumn<CashierInvoiceTable, String> colItemCode;
    @FXML
    private TableColumn<CashierInvoiceTable, String> colImage;
    @FXML
    private TableColumn<CashierInvoiceTable, String> colItemName;
    @FXML
    private TableColumn<CashierInvoiceTable, String> colUnitPrice;
    @FXML
    private TableColumn<CashierInvoiceTable, Double> colQty;
    @FXML
    private TableColumn<CashierInvoiceTable, String> colAmount;
    @FXML
    private TableColumn<CashierInvoiceTable, String> colAction;
    @FXML
    private TextField tfQty;

    private Stock selectedStock;
    private SuggestionPopupService suggestionService;
    private Product selectedProduct;
    private static Config systemConfig;
    private double billSubTotal;
    private double billDiscount;
    private double billFinalAmount;
    private double billItemCount;
    @FXML
    private TextField tfCashAmount;
    @FXML
    private Button btnProcessPayments;
    @FXML
    private Label tfInvoiceBalance;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        suggestionService = new SuggestionPopupService();
        attachSuggestion();
        addEventListener();
        tableConfiguration();
        textFieldConfiguration();
        loadSystemConfig();
    }

    private void loadSystemConfig() {
        try {
            systemConfig = ConfigManager.loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    private void textFieldConfiguration() {
        tfQty.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfCashAmount.setTextFormatter(DefaultAPI.createNumericTextFormatter());
    }

    private void tableConfiguration() {
        // Other columns
        colItemCode.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemId()));
        colItemName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemName()));
        colQty.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getQty()).asObject());
        colUnitPrice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUnitPrice()));
        colAmount.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAmount()));
        colAction.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/cashier/billingActionCell.fxml"));
                        AnchorPane actionBox = loader.load();
                        BillingActionCellController billAction = loader.getController();

                        CashierInvoiceTable rowItem = getTableView().getItems().get(getIndex());
                        billAction.initData(rowItem, getTableView(), () -> updateSubtotal());

                        setGraphic(actionBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                        getLogger.logger().warning(e.toString());
                        setGraphic(null);
                    }
                }
            }
        });

        tableInvoice.getItems().addListener((javafx.collections.ListChangeListener<CashierInvoiceTable>) change -> updateSubtotal());
    }

    private void addEventListener() {
        root.addEventFilter(KeyEvent.KEY_PRESSED, (event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                clearSelectedProduct();
                clearPreviewArea();
            }
        });
    }

    private void clearPreviewArea() {
        // UI components
        labelItemName.setText("Product name");
        labelItemPrice.setText("Rs. 0.00");
        labelItemNewPrice.setText("");
        tfItemCode.setText("");
        tfBarCode.setText("");
        tfItemName.setText("");
        tfQty.setText("0");

        // Static & Instance variables
        selectedProduct = null;
        selectedStock = null;
    }

    private void attachSuggestion() {
        suggestionService.attach(
                tfItemCode,
                CashierProductSuggestionData::searchProductCodes,
                code -> {
                    Product product = CashierProductSuggestionData.getProductById(code);
                    setSelectedProduct(product);
                }
        );

        suggestionService.attach(
                tfItemName,
                CashierProductSuggestionData::searchProductNames,
                name -> {
                    Product product = CashierProductSuggestionData.getProductByName(name);
                    setSelectedProduct(product);
                }
        );
    }

    private void openStockPopup(Product product) {
        try {
            PopUp.showPopupAndWait(
                    "cashier/stock_popup.fxml",
                    root,
                    this.root.getScene(),
                    PopUp.PopupType.CENTERED_80_WIDTH,
                    (Stock_popupController controller) -> {
                        controller.saveController(this);
                        controller.setProduct(product);
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    public void setSelectedStock(Stock stock) {
        this.selectedStock = stock;
        setPreviewDetails(
                stock.getProductId().getProduct(),
                stock.getSalePrice(),
                (stock.getSalePrice() - stock.getDiscount())
        );
    }

    private void setSelectedProduct(Product product) {
        if (product == null) {
            clearSelectedProduct();
            return;
        }

        this.selectedProduct = product;

        if (systemConfig.system.multi_stock) { // Multi stock system
            openStockPopup(product);
        } else { // Single Stock system
            setPreviewDetails(
                    product.getProduct(),
                    product.getSalePrice(),
                    (product.getSalePrice() - product.getDiscount())
            );
        }

    }

    private void setPreviewDetails(String itemName, double salePrice, double newPrice) {
        labelItemName.setText(itemName);
        labelItemPrice.setText(String.valueOf(salePrice));
        labelItemNewPrice.setText(String.valueOf(newPrice));

        calculatePreviewTotal();
    }

    private void clearSelectedProduct() {
        this.selectedProduct = null;
    }

    @FXML
    private void itemCodePressed(KeyEvent event) {

    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnAdd) {
            addItemToTable();
            calculateInvoiceTotal();
        } else if (event.getSource() == btnProcessPayments) {
            processThePayment();
        }
    }

    @FXML
    private void handleQuantityAmount(ActionEvent event) {
    }

    private void addItemToTable() {
        if (selectedProduct == null) {
            CustomAlert.showStyledAlert(
                    root,
                    "Please select a product before adding it to the invoice.",
                    "Product Selection Required",
                    Alert.AlertType.WARNING
            );
            return;
        }

        if (isEntriesValid()) {
            CashierInvoiceTable cashierInvoiceTable = new CashierInvoiceTable();
            cashierInvoiceTable.setItemId(String.valueOf(selectedProduct.getId()));
            cashierInvoiceTable.setItemName(String.valueOf(selectedProduct.getProduct()));
            cashierInvoiceTable.setQty(Double.valueOf(tfQty.getText()));
            cashierInvoiceTable.setAmount(String.format(DefaultAPI.currencyFloatFormat, calculatePreviewTotal()));

            if (systemConfig.system.multi_stock) {
                cashierInvoiceTable.setStock(selectedStock);
                cashierInvoiceTable.setUnitPrice(String.valueOf(selectedStock.getSalePrice()));
            } else {
                cashierInvoiceTable.setUnitPrice(String.valueOf(selectedProduct.getSalePrice()));
            }

            tableInvoice.getItems().add(cashierInvoiceTable);
            tableInvoice.refresh();

            clearPreviewArea();
        }
    }

    private void updateSubtotal() {
        calculateInvoiceTotal();
    }

    private boolean isEntriesValid() {
        if (tfQty.getText().isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "Please enter the quantity before adding the item to the invoice.",
                    "Quantity Required",
                    Alert.AlertType.WARNING
            );
            tfQty.requestFocus();
            tfQty.selectAll();
            return false;
        }

        if (Double.parseDouble(tfQty.getText()) <= 0) {
            CustomAlert.showStyledAlert(
                    root,
                    "Quantity must be greater than zero. Please enter a valid quantity.",
                    "Invalid Quantity",
                    Alert.AlertType.WARNING
            );
            tfQty.requestFocus();
            tfQty.selectAll();
            return false;
        }

        return true;
    }

    @FXML
    private void handleKeyReleaseEvent(KeyEvent event) {
        if (event.getSource() == tfQty) {
            if (Double.parseDouble(tfQty.getText()) <= 0 || tfQty.getText().isEmpty()) {
                tfQty.setText("1");
            }

            calculatePreviewTotal();
        }
    }

    private double calculatePreviewTotal() {
        double itemAmount = 0;
        double qty = Double.parseDouble(tfQty.getText());
        if (systemConfig.system.multi_stock && selectedStock != null) {
            itemAmount = selectedStock.getSalePrice() * qty;
        } else {
            itemAmount = selectedProduct.getSalePrice() * qty;
        }
        itemPrice.setText(String.format(DefaultAPI.currencyFloatFormat, itemAmount));

        return itemAmount;
    }

    private void calculateInvoiceTotal() {
        double itemCount = tableInvoice.getItems().size();
        double subTotal = 0;
        double discount = 0;

        List<CashierInvoiceTable> invoiceItemList = tableInvoice.getItems();
        for (CashierInvoiceTable item : invoiceItemList) {
            if (systemConfig.system.multi_stock && item.getStock() != null) {
                subTotal += item.getStock().getSalePrice() * item.getQty();
                discount += item.getStock().getDiscount() * item.getQty();
            } else {
                subTotal += item.getProduct().getSalePrice() * item.getQty();
                discount += item.getProduct().getDiscount() * item.getQty();
            }
        }

        this.billItemCount = itemCount;
        this.billSubTotal = subTotal;
        this.billDiscount = discount;
        this.billFinalAmount = subTotal - discount;

        invoiceItemCount.setText(String.valueOf(itemCount)); // Item count
        invoiceSubTotal.setText(String.format(DefaultAPI.currencyFloatFormat, subTotal)); // Sub total (without any discount)
        invoiceDiscount.setText(String.format(DefaultAPI.currencyFloatFormat, discount)); // Total discount
        invoiceTotal.setText(String.format(DefaultAPI.currencyFloatFormat, (subTotal - discount))); // Final amount
    }

    private void clearBillDetails() {
        // bill details
        this.billItemCount = 0;
        this.billSubTotal = 0;
        this.billDiscount = 0;
        this.billFinalAmount = 0;

        // invoice table
        tableInvoice.getItems().clear();
        tableInvoice.refresh();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
    }

    private void processThePayment() {

        calculateInvoiceTotal();

        double cashAmount = Double.parseDouble(tfCashAmount.getText());

        if (cashAmount >= this.billFinalAmount) {
            double balance = cashAmount - this.billFinalAmount;
            tfInvoiceBalance.setText(String.format(DefaultAPI.currencyFloatFormat, balance));
            changeActionButtonText("Pay & Print", false);
        } else {
            CustomAlert.showStyledAlert(
                    root,
                    "The entered cash amount is less than the total bill amount.\nPlease enter a valid amount to proceed with the payment.",
                    "Insufficient Cash",
                    Alert.AlertType.WARNING
            );

            changeActionButtonText("Insufficient Cash", true);
        }
    }

    private void changeActionButtonText(String text, boolean state) {
        btnPayment.setText(text);
        btnPayment.setDisable(state);
    }

}
