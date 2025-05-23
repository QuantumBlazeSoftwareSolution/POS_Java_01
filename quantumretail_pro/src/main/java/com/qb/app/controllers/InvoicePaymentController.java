package com.qb.app.controllers;

import com.qb.app.controllers.report.beans.InvoiceItemBean;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.SVGIconGroup;
import com.qb.app.model.entity.Invoice;
import com.qb.app.model.entity.InvoiceItem;
import com.qb.app.model.entity.InvoiceItemType;
import com.qb.app.session.ApplicationSession;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class InvoicePaymentController implements Initializable {

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

    private CashierInvoiceController controller;
    private boolean isPaymentActive;
    @FXML
    private AnchorPane root;
    private double invoiceAmount;
    private List<InvoiceItemController> invoiceItemList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTextFields();
        closeIcon.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/close-icon.svg"));
    }

    @FXML
    private void closeWindow(MouseEvent event) {
        InterfaceAction.closeWindow(root);
    }

    public void saveProductRegistrationController(CashierInvoiceController controller) {
        this.controller = controller;
    }

    public void setItems(List<InvoiceItemController> invoiceItemList) {
        this.invoiceItemList = invoiceItemList;
        double itemCount = 0;
        double subTotal = 0;
        double discount = 0;
        for (InvoiceItemController invoiceItemController : invoiceItemList) {
            itemCount += invoiceItemController.getProductQty();
            subTotal += invoiceItemController.getProduct().getSalePrice() * invoiceItemController.getProductQty();
            discount += invoiceItemController.getProduct().getDiscount() * invoiceItemController.getProductQty();
        }
        invoiceItemCount.setText(String.valueOf(itemCount));
        invoiceSubTotal.setText(String.format("Rs. %, .2f", subTotal));
        invoiceDiscount.setText(String.format("Rs. %, .2f", discount));
        btnInvoiceAmount.setText(String.format("Rs. %, .2f", (subTotal - discount)));
        this.invoiceAmount = subTotal - discount;
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnAction) {
            makeInvoice();
        }
    }

    private void makeInvoice() {
        if (isPaymentActive) {
            createInvoice();
        }
        calculateInvoice();
    }

    private void calculateInvoice() {
        if (validateTextFields()) {
            double cashAmount = 0;
            if (!tfCashAmount.getText().isEmpty()) {
                cashAmount += Double.parseDouble(tfCashAmount.getText());
            }
            if (!tfCardAmount.getText().isEmpty()) {
                cashAmount += Double.parseDouble(tfCardAmount.getText());
            }
            if (!tfCreditAmount.getText().isEmpty()) {
                cashAmount += Double.parseDouble(tfCreditAmount.getText());
            }

            if (cashAmount >= invoiceAmount) {
                if (!tfCreditAmount.getText().isEmpty()) {
                    invoiceCreditAmount.setText(String.format("Rs. %, .2f", Double.parseDouble(tfCreditAmount.getText())));
                }
                double paidAmount = 0;
                if (!tfCashAmount.getText().isEmpty()) {
                    paidAmount += Double.parseDouble(tfCashAmount.getText());
                }

                if (!tfCardAmount.getText().isEmpty()) {
                    paidAmount += Double.parseDouble(tfCardAmount.getText());
                }
                invoicePaidAmount.setText(String.format("Rs. %, .2f", paidAmount));
                invoiceBalance.setText(String.format("Rs. %, .2f", cashAmount - invoiceAmount));
                btnAction.setText("Print Invoice");
                isPaymentActive = true;
            } else {
                double shortAmount = invoiceAmount - cashAmount;
                String formattedShortAmount = String.format("Rs. %, .2f", shortAmount);
                String formattedTotalAmount = String.format("Rs. %, .2f", invoiceAmount);
                String formattedPaidAmount = String.format("Rs. %, .2f", cashAmount);

                CustomAlert.showStyledAlert(
                        root,
                        "Payment Insufficient, Total Amount: " + formattedTotalAmount + " | Amount Paid: " + formattedPaidAmount + " | Short by: " + formattedShortAmount,
                        "Payment Error",
                        Alert.AlertType.WARNING
                );
            }
        }
    }

    private boolean validateTextFields() {
        if (tfCashAmount.getText().isEmpty() && tfCardAmount.getText().isEmpty() && tfCreditAmount.getText().isEmpty()) {
            CustomAlert.showStyledAlert(root, "Please enter payment amount in at least one payment method (Cash, Card, or Credit).", "Payment Required", Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    private void setupTextFields() {
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

    private void createInvoice() {
        JPATransaction.runInTransaction((em) -> {

            double paidAmount = 0;
            double creditAmount = 0;
            if (!tfCashAmount.getText().isEmpty()) {
                paidAmount += Double.parseDouble(tfCashAmount.getText());
            }

            if (!tfCardAmount.getText().isEmpty()) {
                paidAmount += Double.parseDouble(tfCardAmount.getText());
            }

            if (!tfCreditAmount.getText().isEmpty()) {
                creditAmount += Double.parseDouble(tfCreditAmount.getText());
            }

            Invoice invoice = new Invoice();
            invoice.setDateTime(new Date());
            invoice.setBillAmount(invoiceAmount);
            invoice.setPaidAmount(paidAmount);
            invoice.setCreditAmount(creditAmount);
            invoice.setSessionId(ApplicationSession.getSession());
            em.persist(invoice);

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<InvoiceItemType> cq = cb.createQuery(InvoiceItemType.class);
            Root<InvoiceItemType> invoiceItemTypeRoot = cq.from(InvoiceItemType.class);

            cq.select(invoiceItemTypeRoot).where(cb.equal(invoiceItemTypeRoot.get("type"), "Sell"));

            InvoiceItemType sellItemType = em.createQuery(cq).getSingleResult();

            for (InvoiceItemController item : invoiceItemList) {
                InvoiceItem invoiceItem = new InvoiceItem();
                invoiceItem.setProductId(item.getProduct());
                invoiceItem.setQty(item.getProductQty());
                invoiceItem.setSalePrice(item.getProduct().getSalePrice());
                invoiceItem.setCostPrice(item.getProduct().getCostPrice());
                invoiceItem.setInvoiceId(invoice);
                invoiceItem.setInvoiceItemTypeId(sellItemType);
                em.persist(invoiceItem);
            }
            InterfaceAction.closeWindow(root);
        });

        Map<String, Object> params = getJRParams();
        Vector<InvoiceItemBean> collection = getBeanCollection();

        try {
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(
                    getClass().getResourceAsStream("/com/qb/app/reports/QBCashierInvoice.jasper"));

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(collection);

            JasperPrint report = JasperFillManager.fillReport(jasperReport, params, dataSource);
//            JasperPrintManager.printReport(report, false);
            JasperViewer.viewReport(report, false);
        } catch (JRException e) {
            e.printStackTrace();
            CustomAlert.showStyledAlert(root, "Report generation failed: " + e.getMessage(), "Reporting Error", Alert.AlertType.ERROR);
        }
        controller.removeAll();
    }

    private Map<String, Object> getJRParams() {
        double subTotal = 0;
        double discount = 0;
        double paidAmount = 0;
        double creditAmount = 0;
        double total = 0;

        if (!tfCashAmount.getText().isEmpty()) {
            paidAmount += Double.parseDouble(tfCashAmount.getText());
        }

        if (!tfCardAmount.getText().isEmpty()) {
            paidAmount += Double.parseDouble(tfCardAmount.getText());
        }

        if (!tfCreditAmount.getText().isEmpty()) {
            creditAmount += Double.parseDouble(tfCreditAmount.getText());
        }

        for (InvoiceItemController invoiceItemController : invoiceItemList) {
            subTotal += invoiceItemController.getProduct().getSalePrice() * invoiceItemController.getProductQty();
            discount += invoiceItemController.getProduct().getDiscount() * invoiceItemController.getProductQty();
        }

        total += subTotal - discount;

        Map<String, Object> params = new HashMap<>();
        params.put("ID", "2237");
        params.put("CompanyName", "Quantum Retail Pro");
        params.put("Cashier", "Vihanga Heshan");
        params.put("SubTotal", String.format("Rs. %, .2f", subTotal));
        params.put("Discount", String.format("Rs. %, .2f", discount));
        params.put("TotalAmount", String.format("Rs. %, .2f", total));
        params.put("PaidAmount", String.format("Rs. %, .2f", paidAmount));
        params.put("CreditAmount", String.format("Rs. %, .2f", creditAmount));
        params.put("Balance", String.format("Rs. %, .2f", ((paidAmount + creditAmount) - total)));
        params.put("Address", "No: 231/D, Deenapamunuwa, Urapola. 1st Street.");
        params.put("Contact", "Contact: 0719892932/0788056838, Email: vihangaheshan37@gmail.com");
        return params;
    }

    private Vector<InvoiceItemBean> getBeanCollection() {
        Vector<InvoiceItemBean> collection = new Vector<>();
        for (InvoiceItemController item : invoiceItemList) {
            InvoiceItemBean bean = new InvoiceItemBean(
                    item.getProduct().getProduct(),
                    String.format("Rs. %,.2f", item.getProduct().getSalePrice() - item.getProduct().getDiscount()),
                    String.valueOf(item.getProductQty()),
                    String.format("Rs. %,.2f", (item.getProduct().getSalePrice() * item.getProductQty()) - (item.getProductQty() * item.getProduct().getDiscount()))
            );
            collection.add(bean);
        }
        return collection;
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            makeInvoice();
        }
    }

}
