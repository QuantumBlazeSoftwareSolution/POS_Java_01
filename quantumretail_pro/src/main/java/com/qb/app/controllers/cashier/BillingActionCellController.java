/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;

import com.qb.app.controllers.table_models.CashierInvoiceTable;
import com.qb.app.model.SVGIconGroup;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class BillingActionCellController implements Initializable {

    @FXML
    private Button btnDecrease;
    @FXML
    private Group invoiceItemMinusIcon;
    @FXML
    private Button btnIncrease;
    @FXML
    private Group invoiceItemAddIcon;
    @FXML
    private Button btnDelete;
    @FXML
    private Group invoiceItemDeleteIcon;

    private CashierInvoiceTable currentItem;
    private TableView<CashierInvoiceTable> parentTable;
    private Runnable totalUpdater;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setIcons();
    }

    private void setIcons() {
        invoiceItemAddIcon.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/invoiceItemPlus.svg"));
        invoiceItemMinusIcon.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/invoiceItemMinus.svg"));
        invoiceItemDeleteIcon.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/invoiceItemDelete.svg"));
    }

    public void initData(CashierInvoiceTable item, TableView<CashierInvoiceTable> table, Runnable totalUpdater) {
        this.currentItem = item;
        this.parentTable = table;
        this.totalUpdater = totalUpdater;

        btnIncrease.setOnAction(e -> handleAdd());
        btnDecrease.setOnAction(e -> handleMinus());
        btnDelete.setOnAction(e -> handleDelete());
    }

    private void handleAdd() {
        double qty = Double.parseDouble(currentItem.getQty());
        qty++;
        currentItem.setQty(String.valueOf(qty));

        double unitPrice = Double.parseDouble(currentItem.getUnitPrice().replace("Rs.", "").trim());
        currentItem.setAmount(String.format("Rs. %.2f", qty * unitPrice));

        parentTable.refresh();
        totalUpdater.run();
    }

    private void handleMinus() {
        double qty = Double.parseDouble(currentItem.getQty());
        if (qty > 1) {
            qty--;
            currentItem.setQty(String.valueOf(qty));

            double unitPrice = Double.parseDouble(currentItem.getUnitPrice().replace("Rs.", "").trim());
            currentItem.setAmount(String.format("Rs. %.2f", qty * unitPrice));

            parentTable.refresh();
            totalUpdater.run();
        }
    }

    private void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete Item - Confirmation Required");
        alert.setHeaderText("Delete Confirmation");
        alert.setContentText(
                "You are about to delete the selected item from the table.\n"
                + "This action cannot be undone. Do you want to proceed? \n"
        );

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));

        ButtonType exitButton = new ButtonType("Delete Anyway", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType stayButton = new ButtonType("Cancel", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(stayButton, exitButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == exitButton) {
            parentTable.getItems().remove(currentItem);
            totalUpdater.run();
        }
    }

}
