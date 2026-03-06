/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;

import com.qb.app.controllers.table_models.InvoiceItemList;
import com.qb.app.controllers.table_models.InvoiceList;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Invoice;
import com.qb.app.model.entity.InvoiceItem;
import com.qb.app.model.getLogger;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import javafx.scene.control.Alert;
import com.qb.app.controllers.table_models.CashierInvoiceTable;
import com.qb.app.database_crud.ProductHasProductTypeCRUD;
import com.qb.app.model.Config;
import com.qb.app.model.ConfigManager;
import com.qb.app.model.ControllerClose;
import com.qb.app.model.entity.ProductHasProductType;
import com.qb.app.session.ApplicationSession;
import java.util.HashMap;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class CashierRePrintController implements Initializable, ControllerClose {

    private static JasperReport INVOICE_REPORT;
    private static Config systemConfig;

    static {
        try {
            INVOICE_REPORT = (JasperReport) JRLoader.loadObject(
                    CashierRePrintController.class
                            .getResourceAsStream("/com/qb/app/reports/customerInvoice_sin_new.jasper"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ScrollBar rePrintInvoiceScroller;
    @FXML
    private Label itemsCount;
    @FXML
    private Label subTotal;
    @FXML
    private Label discount;
    @FXML
    private Label totalCost;
    @FXML
    private Button btnSearchInvoice;
    @FXML
    private TableView<InvoiceList> invoiceTable;
    @FXML
    private TableColumn<InvoiceList, String> colDateTime;
    @FXML
    private TableColumn<InvoiceList, Integer> colInvoiceNumber;
    @FXML
    private TableView<InvoiceItemList> itemTable;
    @FXML
    private TableColumn<InvoiceItemList, Integer> colItemCode;
    @FXML
    private TableColumn<InvoiceItemList, String> colItemName;
    @FXML
    private TableColumn<InvoiceItemList, Double> colUnitPrice;
    @FXML
    private TableColumn<InvoiceItemList, Integer> colQty;
    @FXML
    private TableColumn<InvoiceItemList, Double> colAmount;
    @FXML
    private Button btnPrintAgain;
    @FXML
    private AnchorPane root;
    @FXML
    private Label paidAmount;
    @FXML
    private Label balanceAmount;
    @FXML
    private DatePicker searchDate;
    @FXML
    private TextField searchInvoiceNumber;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadSystemConfig();
        configureInvoiceTable();
        configureItemTable();
        loadInvoices();
        setupInvoiceTableListener();
    }

    private void loadSystemConfig() {
        try {
            systemConfig = ConfigManager.loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    private void configureInvoiceTable() {
        colDateTime.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getInvoice().getDateTime().toString()));
        colInvoiceNumber.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getInvoice().getId()).asObject());

    }

    private void loadInvoices() {

        Task<List<InvoiceList>> task = new Task<>() {
            @Override
            protected List<InvoiceList> call() {

                return JPATransaction.runInTransaction(em -> {

                    List<Invoice> invoices = em
                            .createQuery("SELECT i FROM Invoice i", Invoice.class)
                            .getResultList();

                    return invoices.stream()
                            .map(i -> new InvoiceList(
                                    i.getDateTime().toString(),
                                    i.getId(),
                                    i))
                            .toList();
                });
            }
        };

        task.setOnSucceeded(e -> {
            invoiceTable.getItems().setAll(task.getValue());
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }

    private void configureItemTable() {
        colItemCode.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getColItemCode()).asObject());
        colItemName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getColItemName()));
        colUnitPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getUnitPrice()).asObject());
        colQty.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getQty()).asObject());
        colAmount.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getAmount()).asObject());
    }

    @FXML
    private void swipetable(SwipeEvent event) {
    }

    @FXML
    private void handleTableDoubleClick(MouseEvent event) {

    }

    private void setupInvoiceTableListener() {
        invoiceTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) { // single click
                InvoiceList selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
                if (selectedInvoice != null) {
                    loadInvoiceItems(selectedInvoice.getInvoice());
                }
            }
        });

    }

    @FXML
    private void searchInvoiceAction(ActionEvent event) {
        LocalDate date = searchDate.getValue();
        String idText = searchInvoiceNumber.getText().trim();

        if (date == null && idText.isEmpty()) {
            loadInvoices(); // Load all if nothing is entered
            return;
        }

        Task<List<InvoiceList>> task = new Task<>() {
            @Override
            protected List<InvoiceList> call() {
                return JPATransaction.runInTransaction(em -> {
                    String jpql = "SELECT i FROM Invoice i WHERE 1=1";
                    if (date != null) {
                        jpql += " AND i.dateTime >= :startDate AND i.dateTime <= :endDate";
                    }
                    if (!idText.isEmpty()) {
                        jpql += " AND i.id = :id";
                    }

                    var query = em.createQuery(jpql, Invoice.class);

                    if (date != null) {
                        java.time.LocalDateTime startOfDay = date.atStartOfDay();
                        java.time.LocalDateTime endOfDay = date.atTime(23, 59, 59);
                        query.setParameter("startDate", java.sql.Timestamp.valueOf(startOfDay));
                        query.setParameter("endDate", java.sql.Timestamp.valueOf(endOfDay));
                    }
                    if (!idText.isEmpty()) {
                        try {
                            query.setParameter("id", Integer.parseInt(idText));
                        } catch (NumberFormatException e) {
                            return List.of(); // Return empty if invalid ID
                        }
                    }

                    List<Invoice> invoices = query.getResultList();
                    return invoices.stream()
                            .map(i -> new InvoiceList(
                                    i.getDateTime().toString(),
                                    i.getId(),
                                    i))
                            .toList();
                });
            }
        };

        task.setOnSucceeded(e -> {
            invoiceTable.getItems().setAll(task.getValue());
            if (task.getValue().isEmpty()) {
                CustomAlert.showStyledAlert(root, "No invoices found matching your criteria.", "No Results",
                        Alert.AlertType.INFORMATION);
            }
        });

        task.setOnFailed(e -> task.getException().printStackTrace());

        new Thread(task).start();
    }

    private static class InvoiceSummaryData {
        private final List<InvoiceItemList> items;
        private final double subTotal;
        private final double discount;

        public InvoiceSummaryData(List<InvoiceItemList> items, double subTotal, double discount) {
            this.items = items;
            this.subTotal = subTotal;
            this.discount = discount;
        }

        public List<InvoiceItemList> getItems() {
            return items;
        }

        public double getSubTotal() {
            return subTotal;
        }

        public double getDiscount() {
            return discount;
        }
    }

    private void loadInvoiceItems(Invoice invoice) {
        Task<InvoiceSummaryData> task = new Task<>() {
            @Override
            protected InvoiceSummaryData call() {
                return JPATransaction.runInTransaction(em -> {
                    List<InvoiceItem> items = em.createQuery(
                            "SELECT ii FROM InvoiceItem ii WHERE ii.invoiceId = :invoice",
                            InvoiceItem.class).setParameter("invoice", invoice)
                            .getResultList();

                    double calcSubTotal = 0;
                    double calcDiscount = 0;

                    for (InvoiceItem item : items) {
                        ProductHasProductType productType = ProductHasProductTypeCRUD
                                .getProductHasProductTypeByProduct(item.getProductId());

                        double itemPrice = 0;
                        double itemDiscount = 0;

                        if (systemConfig.system.multi_stock) {
                            if (productType.getProductTypeId().getType().toLowerCase().equals("parent")) {
                                itemPrice = item.getStockBatchId().getSalePrice();
                                itemDiscount = item.getStockBatchId().getDiscount();
                            } else {
                                itemPrice = item.getProductId().getSalePrice();
                                itemDiscount = item.getProductId().getDiscount();
                            }
                        } else {
                            itemPrice = item.getProductId().getSalePrice();
                            itemDiscount = item.getProductId().getDiscount();
                        }

                        calcSubTotal += itemPrice * item.getQty();
                        calcDiscount += itemDiscount * item.getQty();
                    }

                    List<InvoiceItemList> viewItems = items.stream()
                            .map(ii -> new InvoiceItemList(
                                    ii.getProductId().getId(),
                                    ii.getProductId().getProduct(),
                                    ii.getSalePrice(),
                                    (int) ii.getQty(),
                                    ii.getSalePrice() * ii.getQty(),
                                    ii))
                            .toList();

                    return new InvoiceSummaryData(viewItems, calcSubTotal, calcDiscount);
                });
            }
        };

        task.setOnSucceeded(e -> {
            InvoiceSummaryData data = task.getValue();
            itemTable.getItems().setAll(data.getItems());

            // Update labels
            itemsCount.setText(String.valueOf(data.getItems().size()));
            subTotal.setText(String.format("Rs. %, .2f", data.getSubTotal()));
            discount.setText(String.format("Rs. %, .2f", data.getDiscount()));
            totalCost.setText(String.format("Rs. %, .2f", invoice.getBillAmount()));
            paidAmount.setText(String.format("Rs. %, .2f", invoice.getPaidAmount()));

            double bal = (invoice.getPaidAmount() + invoice.getCreditAmount()) - invoice.getBillAmount();
            balanceAmount.setText(String.format("Rs. %, .2f", bal));
        });

        task.setOnFailed(e -> task.getException().printStackTrace());

        new Thread(task).start();
    }

    @FXML
    private void printInvoiceAction(ActionEvent event) {

        // 1️⃣ Get selected invoice
        InvoiceList selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();
        if (selectedInvoice == null) {
            CustomAlert.showStyledAlert(null, "Please select an invoice to print.", "No Selection",
                    Alert.AlertType.WARNING);
            return;
        }

        Invoice invoice = selectedInvoice.getInvoice();

        // 2️⃣ Load invoice items
        Task<List<InvoiceItem>> task = new Task<>() {
            @Override
            protected List<InvoiceItem> call() {
                return JPATransaction.runInTransaction(em -> {
                    return em.createQuery(
                            "SELECT ii FROM InvoiceItem ii WHERE ii.invoiceId = :invoice",
                            InvoiceItem.class).setParameter("invoice", invoice)
                            .getResultList();
                });
            }
        };

        // 3️⃣ When items are loaded, call printInvoice
        task.setOnSucceeded(e -> {
            List<InvoiceItem> invoiceItems = task.getValue();
            if (invoiceItems.isEmpty()) {
                CustomAlert.showStyledAlert(null, "No items found for this invoice.", "Empty Invoice",
                        Alert.AlertType.WARNING);
            } else {
                printInvoice(invoice, invoiceItems);
            }
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            CustomAlert.showStyledAlert(null, "Failed to load invoice items: " + task.getException().getMessage(),
                    "Error", Alert.AlertType.ERROR);
        });

        new Thread(task).start();

    }

    private void printInvoice(Invoice invoice, List<InvoiceItem> invoiceItems) {
        Map<String, Object> params = getJRParams(invoice, invoiceItems);
        Vector<CashierInvoiceTable> collection = getBeanCollection(invoiceItems);

        try {
            JasperReportsContext jasperReportsContext = DefaultJasperReportsContext.getInstance();
            JRPropertiesUtil.getInstance(jasperReportsContext).setProperty(
                    "net.sf.jasperreports.awt.ignore.missing.font", "true");

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(collection);

            JasperPrint report = JasperFillManager.fillReport(
                    INVOICE_REPORT,
                    params,
                    dataSource);
            JasperPrintManager.printReport(report, false);
        } catch (JRException e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
            CustomAlert.showStyledAlert(root, "Report generation failed: " + e.getMessage(), "Reporting Error",
                    Alert.AlertType.ERROR);
        }
    }

    private Map<String, Object> getJRParams(Invoice invoice, List<InvoiceItem> invoiceItems) {
        double subTotal = 0;
        double discount = 0;
        double paidAmount = invoice.getPaidAmount();
        double creditAmount = invoice.getCreditAmount();
        double total = invoice.getBillAmount();

        for (InvoiceItem invoiceItemController : invoiceItems) {

            ProductHasProductType productType = ProductHasProductTypeCRUD
                    .getProductHasProductTypeByProduct(invoiceItemController.getProductId());
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
        params.put("Cashier", (invoice.getSessionId().getEmployeeId().getName()).split(" ")[0]);
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

    private Vector<CashierInvoiceTable> getBeanCollection(List<InvoiceItem> invoiceItems) {
        Vector<CashierInvoiceTable> collection = new Vector<>();
        for (InvoiceItem item : invoiceItems) {
            CashierInvoiceTable bean = new CashierInvoiceTable();

            bean.setItemName(item.getProductId().getProduct());
            double ourPrice = 0;
            if (systemConfig.system.multi_stock) {

                ProductHasProductType productType = ProductHasProductTypeCRUD
                        .getProductHasProductTypeByProduct(item.getProductId());

                if (productType.getProductTypeId().getType().toLowerCase().equals("parent")) {
                    bean.setUnitPrice(
                            String.format(
                                    DefaultAPI.currencyFloatFormat, item.getStockBatchId().getSalePrice()));

                    ourPrice = item.getStockBatchId().getSalePrice() - item.getStockBatchId().getDiscount();

                    bean.setDiscount(String.format(DefaultAPI.currencyFloatFormat, ourPrice));
                } else {
                    bean.setUnitPrice(
                            String.format(
                                    DefaultAPI.currencyFloatFormat, item.getProductId().getSalePrice()));
                    ourPrice = item.getProductId().getSalePrice() - item.getProductId().getDiscount();
                    bean.setDiscount(String.format(DefaultAPI.currencyFloatFormat, ourPrice));
                }
            } else {
                bean.setUnitPrice(
                        String.format(
                                DefaultAPI.currencyFloatFormat, item.getProductId().getSalePrice()));
                ourPrice = item.getProductId().getSalePrice() - item.getProductId().getDiscount();
                bean.setDiscount(String.format(DefaultAPI.currencyFloatFormat, ourPrice));
            }

            bean.setQty(item.getQty());
            bean.setAmount(String.format(
                    DefaultAPI.currencyFloatFormat, item.getQty() * ourPrice));

            collection.add(bean);
        }
        return collection;
    }

    @Override
    public void close() {
        // No cleanup required at this time.
    }

}
