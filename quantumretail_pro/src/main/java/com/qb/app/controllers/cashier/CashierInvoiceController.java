package com.qb.app.controllers.cashier;

import com.qb.app.controllers.exports.StockProductExport;
import com.qb.app.controllers.table_models.CashierInvoiceTable;
import com.qb.app.database_crud.InvoiceItemTypeCRUD;
import com.qb.app.database_crud.ProductHasProductTypeCRUD;
import com.qb.app.database_crud.StockCRUD;
import com.qb.app.database_crud.TableInitialValues;
import com.qb.app.model.Config;
import com.qb.app.model.ConfigManager;
import com.qb.app.model.ControllerClose;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.PopUp;
import com.qb.app.model.SuggestionPopupService;
import com.qb.app.model.entity.Invoice;
import com.qb.app.model.entity.InvoiceItem;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductHasProductType;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.getLogger;
import com.qb.app.session.ApplicationSession;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class CashierInvoiceController implements Initializable, ControllerClose {

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
    @FXML
    private TextField tfCashAmount;
    @FXML
    private Button btnProcessPayments;
    @FXML
    private Label tfInvoiceBalance;
    @FXML
    private Label invoiceMessage;

    private Stock selectedStock;
    private SuggestionPopupService suggestionService;
    private Product selectedProduct;
    private static Config systemConfig;
    private double billSubTotal;
    private double billDiscount;
    private double billFinalAmount;
    private double billItemCount;
    private boolean canInvoicePaid;
    private double cashAmount;
    private boolean isParent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        suggestionService = new SuggestionPopupService();
        attachSuggestion();
        addEventListener();
        tableConfiguration();
        textFieldConfiguration();
        loadSystemConfig();
        interceptQuantityKeys();
        tfBarCode.requestFocus();
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnAdd) {
            addItemToTable();
            calculateInvoiceTotal();
        } else if (event.getSource() == btnProcessPayments) {
            processThePayment();
        } else if (event.getSource() == btnPayment) {
            completeThePayment();
        } else if (event.getSource() == btnIncreaseQty) {
            changeQuantity(true);
        } else if (event.getSource() == btnDecreaseQty) {
            changeQuantity(false);
        }
    }

    private static JasperReport INVOICE_REPORT;

    static {
        try {
            INVOICE_REPORT = (JasperReport) JRLoader.loadObject(
                    CashierInvoiceController.class
                            .getResourceAsStream("/com/qb/app/reports/customerInvoice_sin_new.jasper")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
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
//            else if (event.getCode() == KeyCode.PLUS) {
//                if (this.selectedProduct != null) {
//                    changeQuantity(true);
//                }
//            } else if (event.getCode() == KeyCode.MINUS) {
//                if (this.selectedProduct != null) {
//                    changeQuantity(false);
//                }
//            }
        });
    }

    private void interceptQuantityKeys() {
        EventHandler<KeyEvent> pressedHandler = event -> {
            if (event.getCode() == KeyCode.PLUS || event.getCode() == KeyCode.ADD) {
                changeQuantity(true);
                event.consume();
            } else if (event.getCode() == KeyCode.MINUS || event.getCode() == KeyCode.SUBTRACT) {
                changeQuantity(false);
                event.consume();
            } else if (event.getCode() == KeyCode.ENTER && this.selectedProduct != null) {
                addItemToTable();
                event.consume();
            } else if (event.getCode() == KeyCode.F1) {
                tfBarCode.requestFocus();
                event.consume();
            } else if (event.getCode() == KeyCode.F2) {
                tfItemCode.requestFocus();
                event.consume();
            } else if (event.getCode() == KeyCode.F3) {
                tfItemName.requestFocus();
                event.consume();
            } else if (event.getCode() == KeyCode.SHIFT) {
                tfCashAmount.requestFocus();
                event.consume();
            }
        };

        EventHandler<KeyEvent> typedHandler = event -> {
            if ("+".equals(event.getCharacter()) || "-".equals(event.getCharacter())) {
                event.consume();
            }
        };

        tfQty.addEventFilter(KeyEvent.KEY_PRESSED, pressedHandler);
        tfQty.addEventFilter(KeyEvent.KEY_TYPED, typedHandler);

        tfItemCode.addEventFilter(KeyEvent.KEY_PRESSED, pressedHandler);
        tfItemCode.addEventFilter(KeyEvent.KEY_TYPED, typedHandler);

        tfItemName.addEventFilter(KeyEvent.KEY_PRESSED, pressedHandler);
        tfItemName.addEventFilter(KeyEvent.KEY_TYPED, typedHandler);

        tfBarCode.addEventFilter(KeyEvent.KEY_PRESSED, pressedHandler);
        tfBarCode.addEventFilter(KeyEvent.KEY_TYPED, typedHandler);
    }

    private void clearPreviewArea() {
        // UI components
        labelItemName.setText("Product name");
        labelItemPrice.setText("Rs. 0.00");
        labelItemNewPrice.setText("");
        tfItemCode.setText("");
        tfBarCode.setText("");
        tfItemName.setText("");
        tfQty.setText("1");

        // Static & Instance variables
        this.selectedProduct = null;
        this.selectedStock = null;
        this.isParent = false;
        this.canInvoicePaid = false;
        this.cashAmount = 0;
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
        } catch (IOException e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    public void closeWithClear() {
        clearPreviewArea();
    }

    public void setSelectedStock(Stock stock, Product product) {

        this.selectedStock = stock;
        this.selectedProduct = product;

        ProductHasProductType productType = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(product);
        this.isParent = productType.getProductTypeId().getType().toLowerCase().equals("parent");

        if (Objects.equals(stock.getProductId().getId(), product.getId())) {
            System.out.println("Product have stocks.");
            setPreviewDetails(
                    product.getProduct(),
                    stock.getSalePrice(),
                    (stock.getSalePrice() - stock.getDiscount())
            );
        } else {
            System.out.println("Product doesn't have any stocks.");
            setPreviewDetails(
                    product.getProduct(),
                    product.getSalePrice(),
                    (product.getSalePrice() - product.getDiscount())
            );
        }
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
        if (salePrice != newPrice) {
            labelItemNewPrice.setText(String.valueOf(newPrice));
            labelItemNewPrice.setVisible(true);
        } else {
            labelItemNewPrice.setText("");
            labelItemNewPrice.setVisible(false);
        }

        calculatePreviewTotal();
    }

    private void clearSelectedProduct() {
        this.selectedProduct = null;
    }

    @FXML
    private void itemCodePressed(KeyEvent event) {

    }

    private void changeQuantity(boolean isPositive) {
        double qty = Double.parseDouble(tfQty.getText());
        if (isPositive) {
            tfQty.setText(String.valueOf(qty + 1));
        } else {
            tfQty.setText(String.valueOf(qty - 1));
        }

        calculatePreviewTotal();
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
            cashierInvoiceTable.setQty(Double.parseDouble(tfQty.getText()));
            cashierInvoiceTable.setAmount(String.format(DefaultAPI.currencyFloatFormat, calculatePreviewTotal()));
            cashierInvoiceTable.setProduct(selectedProduct);

            if (systemConfig.system.multi_stock) {
                cashierInvoiceTable.setStock(selectedStock);
                if (this.isParent) {
                    cashierInvoiceTable.setUnitPrice(String.valueOf(selectedStock.getSalePrice()));
                    cashierInvoiceTable.setDiscount(String.format(DefaultAPI.currencyFloatFormat, selectedStock.getDiscount()));
                } else {
                    cashierInvoiceTable.setUnitPrice(String.valueOf(selectedProduct.getSalePrice()));
                    cashierInvoiceTable.setDiscount(String.format(DefaultAPI.currencyFloatFormat, selectedProduct.getDiscount()));
                }
            } else {
                cashierInvoiceTable.setUnitPrice(String.valueOf(selectedProduct.getSalePrice()));
                cashierInvoiceTable.setDiscount(String.format(DefaultAPI.currencyFloatFormat, selectedProduct.getDiscount()));
            }

            tableInvoice.getItems().add(cashierInvoiceTable);
            tableInvoice.refresh();

            clearPreviewArea();
            tfBarCode.requestFocus();
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
        if (!tfQty.getText().isEmpty()) {

            double qty = Double.parseDouble(tfQty.getText());
            if (systemConfig.system.multi_stock && selectedStock != null) {
                if (this.isParent) { // Parent Item
                    itemAmount = selectedStock.getSalePrice() * qty;
                } else { // Child Item
                    itemAmount = selectedProduct.getSalePrice() * qty;
                }
            } else {
                itemAmount = selectedProduct.getSalePrice() * qty;
            }
            itemPrice.setText(String.format(DefaultAPI.currencyFloatFormat, itemAmount));

        }
        return itemAmount;
    }

    private void calculateInvoiceTotal() {
        double itemCount = tableInvoice.getItems().size();
        double subTotal = 0;
        double discount = 0;

        List<CashierInvoiceTable> invoiceItemList = tableInvoice.getItems();
        for (CashierInvoiceTable item : invoiceItemList) {
            if (systemConfig.system.multi_stock && item.getStock() != null) {
                ProductHasProductType productType = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(item.getProduct());
                if (productType.getProductTypeId().getType().toLowerCase().equals("parent")) {
                    subTotal += item.getStock().getSalePrice() * item.getQty();
                    discount += item.getStock().getDiscount() * item.getQty();
                } else {
                    subTotal += item.getProduct().getSalePrice() * item.getQty();
                    discount += item.getProduct().getDiscount() * item.getQty();
                }
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
        this.canInvoicePaid = false;

        tfCashAmount.setText("");
        tfInvoiceBalance.setText("Rs. 0.00");

        clearPreviewArea();
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getSource() == tfCashAmount && event.getCode() == KeyCode.ENTER) {
            if (canInvoicePaid) {
                completeThePayment();
            } else {
                processThePayment();
            }
        }
    }

    private void processThePayment() {
        calculateInvoiceTotal();

        if (!tfCashAmount.getText().isEmpty()) {
            this.cashAmount = Double.parseDouble(tfCashAmount.getText());

            if (cashAmount >= this.billFinalAmount) {
                double balance = cashAmount - this.billFinalAmount;
                tfInvoiceBalance.setText(String.format(DefaultAPI.currencyFloatFormat, balance));
                changeActionButtonText("Pay & Print", false);
                this.canInvoicePaid = true;
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
    }

    private void changeActionButtonText(String text, boolean state) {
        btnPayment.setText(text);
        btnPayment.setDisable(state);
    }

//    private void completeThePayment() {
//        if (this.canInvoicePaid) {
//
//            try {
//                List<CashierInvoiceTable> invoiceItemList = tableInvoice.getItems();
//
//                Invoice invoice = new Invoice();
//                List<InvoiceItem> invoiceItems = new ArrayList<>();
//
//                JPATransaction.runInTransaction((em) -> {
//                    // INSERT invoice
//                    invoice.setDateTime(new Date());
//                    invoice.setBillAmount(this.billFinalAmount);
//                    invoice.setPaidAmount(this.cashAmount);
//                    invoice.setSessionId(ApplicationSession.getSession());
//
//                    em.persist(invoice);
//                    em.flush();
//
//                    for (CashierInvoiceTable item : invoiceItemList) {
//                        // insert invoice_item
//
//                        InvoiceItem invoiceItem = new InvoiceItem();
//                        invoiceItem.setQty(item.getQty());
//                        if (systemConfig.system.multi_stock) {
//
//                            ProductHasProductType productType = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(item.getProduct());
//
//                            if (productType.getProductTypeId().getType().toLowerCase().equals("parent")) {
//                                invoiceItem.setSalePrice(item.getStock().getSalePrice());
//                                invoiceItem.setCostPrice(item.getStock().getCostPrice());
//                                invoiceItem.setDiscount(item.getStock().getDiscount());
//                            } else {
//                                invoiceItem.setSalePrice(item.getProduct().getSalePrice());
//                                invoiceItem.setCostPrice(item.getProduct().getCostPrice());
//                                invoiceItem.setDiscount(item.getProduct().getDiscount());
//                            }
//                        } else {
//                            invoiceItem.setSalePrice(item.getProduct().getSalePrice());
//                            invoiceItem.setCostPrice(item.getProduct().getCostPrice());
//                            invoiceItem.setDiscount(item.getProduct().getDiscount());
//                        }
//                        invoiceItem.setInvoiceId(invoice);
//                        invoiceItem.setInvoiceItemTypeId(
//                                InvoiceItemTypeCRUD.getInvoiceItemType(
//                                        TableInitialValues.InvoiceItemType.selling
//                                )
//                        );
//                        invoiceItem.setProductId(item.getProduct());
//                        invoiceItem.setStockBatchId(item.getStock());
//
//                        if (systemConfig.system.multi_stock) {
//                            invoiceItem.setStockBatchId(item.getStock());
//                        }
//
//                        em.persist(invoiceItem);
//                        em.flush();
//
//                        invoiceItems.add(invoiceItem);
//
//                        // deduct stocks
//                        Stock stock = item.getStock();
//                        stock.setQty(stock.getQty() - item.getQty());
//
//                        em.merge(stock);
//                        em.flush();
//                    }
//                });
//
//                printInvoice(invoice, invoiceItems);
//
//                // print the bill
//                DefaultAPI.showMessageAndHidden(invoiceMessage, "Payment Successful");
//                clearBillDetails();
//                clearSelectedProduct();
//
//                tfBarCode.requestFocus();
//            } catch (Exception e) {
//                e.printStackTrace();
//                getLogger.logger().warning(e.toString());
//                CustomAlert.showStyledAlert(
//                        root,
//                        "Unable to process the payment. Please try again or contact support.",
//                        "Payment Failed",
//                        Alert.AlertType.ERROR
//                );
//            }
//        } else {
//            CustomAlert.showStyledAlert(
//                    root,
//                    "This invoice is incomplete. Please add the required items before proceeding.",
//                    "Incomplete Invoice",
//                    Alert.AlertType.WARNING
//            );
//        }
//    }
    private void completeThePayment() {
        if (!canInvoicePaid) {
            CustomAlert.showStyledAlert(
                    root,
                    "This invoice is incomplete.",
                    "Incomplete Invoice",
                    Alert.AlertType.WARNING
            );
            return;
        }

        btnPayment.setDisable(true);

        Task<Void> paymentTask = new Task<>() {
            @Override
            protected Void call() {
                processPaymentInBackground();
                return null;
            }
        };

        paymentTask.setOnSucceeded(e -> {
            DefaultAPI.showMessageAndHidden(invoiceMessage, "Payment Successful");
            clearBillDetails();
            clearSelectedProduct();
            tfBarCode.requestFocus();
            btnPayment.setDisable(false);
        });

        paymentTask.setOnFailed(e -> {
            Throwable ex = paymentTask.getException();
            ex.printStackTrace();
            CustomAlert.showStyledAlert(
                    root,
                    "Payment failed. Please retry.",
                    "Error",
                    Alert.AlertType.ERROR
            );
            btnPayment.setDisable(false);
        });

        new Thread(paymentTask, "payment-thread").start();
    }

    private void processPaymentInBackground() {
        List<CashierInvoiceTable> invoiceItemList
                = new ArrayList<>(tableInvoice.getItems());

        Invoice invoice = new Invoice();
        List<InvoiceItem> invoiceItems = new ArrayList<>();

        JPATransaction.runInTransaction(em -> {

            invoice.setDateTime(new Date());
            invoice.setBillAmount(billFinalAmount);
            invoice.setPaidAmount(cashAmount);
            invoice.setSessionId(ApplicationSession.getSession());

            em.persist(invoice);

            for (CashierInvoiceTable item : invoiceItemList) {

                InvoiceItem invoiceItem = new InvoiceItem();
                invoiceItem.setQty(item.getQty());
                invoiceItem.setInvoiceId(invoice);
                invoiceItem.setInvoiceItemTypeId(
                        InvoiceItemTypeCRUD.getInvoiceItemType(
                                TableInitialValues.InvoiceItemType.selling
                        )
                );
                invoiceItem.setProductId(item.getProduct());
                invoiceItem.setStockBatchId(item.getStock());

                if (systemConfig.system.multi_stock && item.getStock() != null) {
                    ProductHasProductType pt
                            = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(item.getProduct());

                    if ("parent".equalsIgnoreCase(pt.getProductTypeId().getType())) {
                        invoiceItem.setSalePrice(item.getStock().getSalePrice());
                        invoiceItem.setCostPrice(item.getStock().getCostPrice());
                        invoiceItem.setDiscount(item.getStock().getDiscount());
                    } else {
                        invoiceItem.setSalePrice(item.getProduct().getSalePrice());
                        invoiceItem.setCostPrice(item.getProduct().getCostPrice());
                        invoiceItem.setDiscount(item.getProduct().getDiscount());
                    }
                } else {
                    invoiceItem.setSalePrice(item.getProduct().getSalePrice());
                    invoiceItem.setCostPrice(item.getProduct().getCostPrice());
                    invoiceItem.setDiscount(item.getProduct().getDiscount());
                }

                em.persist(invoiceItem);
                invoiceItems.add(invoiceItem);

                // Stock update
                Stock stock = item.getStock();
                if (stock != null) {
                    stock.setQty(stock.getQty() - item.getQty());
                    em.merge(stock);
                }
            }

            // ✅ SINGLE flush
            em.flush();
        });

        // Printing AFTER commit
        printInvoice(invoice, invoiceItems);
    }

    @Override
    public void close() {
    }

    private void printInvoice(Invoice invoice, List<InvoiceItem> invoiceItems) {
        Map<String, Object> params = getJRParams(invoice, invoiceItems);
        Vector<CashierInvoiceTable> collection = getBeanCollection();

        try {
            JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();
            JRPropertiesUtil.getInstance(jasperReportsContext).setProperty(
                    "net.sf.jasperreports.awt.ignore.missing.font", "true"
            );
//            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(
//                    getClass().getResourceAsStream("/com/qb/app/reports/customerInvoice_sin.jasper"));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(collection);

            JasperPrint report = JasperFillManager.fillReport(
                    INVOICE_REPORT,
                    params,
                    dataSource
            );
            JasperPrintManager.printReport(report, false);
//            JasperViewer.viewReport(report, false);
        } catch (JRException e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
            CustomAlert.showStyledAlert(root, "Report generation failed: " + e.getMessage(), "Reporting Error", Alert.AlertType.ERROR);
        }
    }

    private Map<String, Object> getJRParams(Invoice invoice, List<InvoiceItem> invoiceItems) {
        double subTotal = 0;
        double discount = 0;
        double paidAmount = 0;
        double creditAmount = 0;
        double total = 0;

        if (!tfCashAmount.getText().isEmpty()) {
            paidAmount += Double.parseDouble(tfCashAmount.getText());
        }

        for (InvoiceItem invoiceItemController : invoiceItems) {

            ProductHasProductType productType
                    = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(invoiceItemController.getProductId());
            if (systemConfig.system.multi_stock) {

                if (productType.getProductTypeId().getType().toLowerCase().equals("parent")) {
                    subTotal += invoiceItemController.getStockBatchId().getSalePrice() * invoiceItemController.getQty();
                    discount += invoiceItemController.getStockBatchId().getDiscount() * invoiceItemController.getQty();
                } else {
                    subTotal += invoiceItemController.getProductId().getSalePrice() * invoiceItemController.getQty();
                    discount += invoiceItemController.getProductId().getDiscount() * invoiceItemController.getQty();
                }
            } else {
                subTotal += invoiceItemController.getProductId().getSalePrice() * invoiceItemController.getQty();
                discount += invoiceItemController.getProductId().getDiscount() * invoiceItemController.getQty();
            }
        }

        total += subTotal - discount;

        Map<String, Object> params = new HashMap<>();

        try {
            URL imageUrl = getClass().getResource("/com/qb/app/assets/images/pos_logo.png");
            params.put("Logo", imageUrl);
        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }

        params.put("ID", String.format("INV-%06d", invoice.getId()));
        params.put("ItemCount", String.valueOf(invoiceItems.size()));
        params.put("CompanyName", systemConfig.system_name);
        params.put("Cashier", (ApplicationSession.getEmployee().getName()).split(" ")[0]);
        params.put("SubTotal", String.format("Rs. %, .2f", subTotal));
        params.put("Discount", String.format("Rs. %, .2f", discount));
        params.put("BillDiscount", ("Rs. 0.00"));
        params.put("TotalAmount", String.format("Rs. %, .2f", total));
        params.put("PaidAmount", String.format("Rs. %, .2f", paidAmount));
        params.put("CreditAmount", String.format("Rs. %, .2f", creditAmount));
        params.put("Balance", String.format("Rs. %, .2f", ((paidAmount + creditAmount) - (total))));
        params.put("Address", systemConfig.address);
        if (systemConfig.telephone_02 != null && !systemConfig.telephone_02.isEmpty()) {
            params.put("Contact", systemConfig.telephone_01 + " / " + systemConfig.telephone_02);
        } else {
            params.put("Contact", systemConfig.telephone_01);
        }
        return params;
    }

    private Vector<CashierInvoiceTable> getBeanCollection() {
        Vector<CashierInvoiceTable> collection = new Vector<>();
        for (CashierInvoiceTable item : tableInvoice.getItems()) {
            CashierInvoiceTable bean = new CashierInvoiceTable();

            bean.setItemName(item.getItemName());
            double ourPrice = 0;
            if (systemConfig.system.multi_stock) {

                ProductHasProductType productType = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(item.getProduct());

                if (productType.getProductTypeId().getType().toLowerCase().equals("parent")) {
//                    bean.setUnitPrice(
//                            String.format(
//                                    DefaultAPI.currencyFloatFormat, item.getStock().getSalePrice() - item.getStock().getDiscount()
//                            )
//                    );
                    bean.setUnitPrice(
                            String.format(
                                    DefaultAPI.currencyFloatFormat, item.getStock().getSalePrice()
                            )
                    );

                    ourPrice = item.getStock().getSalePrice() - item.getStock().getDiscount();

                    bean.setDiscount(String.format(DefaultAPI.currencyFloatFormat, ourPrice));
//                    bean.setDiscount(String.format(DefaultAPI.currencyFloatFormat, item.getStock().getDiscount()));
                } else {
//                    bean.setUnitPrice(
//                            String.format(
//                                    DefaultAPI.currencyFloatFormat, item.getProduct().getSalePrice() - item.getProduct().getDiscount()
//                            )
//                    );
                    bean.setUnitPrice(
                            String.format(
                                    DefaultAPI.currencyFloatFormat, item.getProduct().getSalePrice()
                            )
                    );
                    ourPrice = item.getProduct().getSalePrice() - item.getProduct().getDiscount();
                    bean.setDiscount(String.format(DefaultAPI.currencyFloatFormat, ourPrice));
//                    bean.setDiscount(String.format(DefaultAPI.currencyFloatFormat, item.getProduct().getDiscount()));
                }
            } else {
//                bean.setUnitPrice(
//                        String.format(
//                                DefaultAPI.currencyFloatFormat, item.getProduct().getSalePrice() - item.getProduct().getDiscount()
//                        )
//                );
                bean.setUnitPrice(
                        String.format(
                                DefaultAPI.currencyFloatFormat, item.getProduct().getSalePrice()
                        )
                );
                ourPrice = item.getProduct().getSalePrice() - item.getProduct().getDiscount();
                bean.setDiscount(String.format(DefaultAPI.currencyFloatFormat, ourPrice));
//                bean.setDiscount(String.format(DefaultAPI.currencyFloatFormat, item.getProduct().getDiscount()));
            }

            bean.setQty(item.getQty());
//            bean.setAmount(item.getAmount());
            bean.setAmount(String.format(
                    DefaultAPI.currencyFloatFormat, item.getQty() * ourPrice
            ));

            collection.add(bean);
        }
        return collection;
    }

    @FXML
    private void handleOnKeyPressed(KeyEvent event) {
        if (event.getSource() == tfBarCode && event.getCode() == KeyCode.ENTER) {
            searchItemByBarcode(tfBarCode.getText());
        }
    }

//    private void searchItemByBarcode(String barcode) {
//        StockProductExport stockProduct = StockCRUD.getStockItemsByBarcode(barcode);
//
//        if (stockProduct.getStock() != null && stockProduct.getStock().size() == 1) {
//            Stock stock = stockProduct.getStock().get(0);
//            Product product = stock.getProductId();
//
//            setSelectedStock(stock, product);
//            this.selectedProduct = product;
//            this.selectedStock = stock;
//
//            ProductHasProductType productType = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(selectedProduct);
//            this.isParent = productType.getProductTypeId().getType().toLowerCase().equals("parent");
//        } else {
//            openStockPopup(stockProduct.getStock());
//        }
//    }
    private void searchItemByBarcode(String barcode) {
        StockProductExport stockProduct = StockCRUD.getStockItemsByBarcode(barcode);

        List<Stock> stocks = stockProduct.getStock();

        if (stocks == null || stocks.isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "No stock found for this barcode.",
                    "Stock Not Found",
                    Alert.AlertType.WARNING
            );
            tfBarCode.requestFocus();
            tfBarCode.selectAll();
            return;
        }

        if (stocks.size() == 1) {
            Stock stock = stocks.get(0);
            Product product = stock.getProductId();

            setSelectedStock(stock, product);

            this.selectedProduct = product;
            this.selectedStock = stock;

            ProductHasProductType productType
                    = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(product);

            this.isParent
                    = productType.getProductTypeId()
                            .getType()
                            .equalsIgnoreCase("parent");

            return;
        }

        // multiple stocks only
        openStockPopup(stocks);
    }

    private void openStockPopup(List<Stock> stocks) {
        try {
            PopUp.showPopupAndWait(
                    "cashier/stock_popup_barcode.fxml",
                    root,
                    this.root.getScene(),
                    PopUp.PopupType.CENTERED_80_WIDTH,
                    (Stock_popup_barcodeController controller) -> {
                        controller.saveController(this);
                        controller.setStocks(stocks);
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }
}
