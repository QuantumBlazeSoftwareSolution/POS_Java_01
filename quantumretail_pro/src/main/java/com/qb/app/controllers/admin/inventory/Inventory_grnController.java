/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.inventory;

import com.qb.app.model.ComboBoxUtils;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Company;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.Supplier;
import com.qb.app.model.entity.SupplyStatus;
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Inventory_grnController implements Initializable {

    @FXML
    private Group GrnIcon;
    @FXML
    private ScrollPane grnTableScrollContainer;
    @FXML
    private VBox grnTableBody;
    @FXML
    private ScrollBar grnTableScroller;
    @FXML
    private ComboBox<Company> CompanyComboBox;
    @FXML
    private ComboBox<Supplier> SupplierComboBox;
    @FXML
    private TextField GRNID_TF;
    @FXML
    private TextField ID_TF;
    @FXML
    private TextField Cost_TF;
    @FXML
    private TextField Sale_TF;
    @FXML
    private TextField Qty_TF;
    @FXML
    private DatePicker ExpireDatePicker;
    @FXML
    private TextField ProductName_TF;
    @FXML
    private TextField Amount_TF;
    @FXML
    private Button Add_Btn;
    @FXML
    private TextField Discount_TF;
    @FXML
    private TextField Total_TF;
    @FXML
    private Button Apply_Btn;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField PDiscount_TF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCompanyComboBox();
        loadSupplierComboBox();

        // TODO
    }

    private void loadCompanyComboBox() {
        ComboBoxUtils.loadComboBoxValues(CompanyComboBox, Company.class, "name", Company::getName);
    }

    private void loadSupplierComboBox() {
        ComboBoxUtils.loadComboBoxValues(SupplierComboBox, Supplier.class, "name", Supplier::getName);
    }

    @FXML
    private void CompanySelectAction(ActionEvent event) {
        Company value = CompanyComboBox.getValue();

        if (value != null) {
//            System.out.println("Selected Company: " + value.getName());
            List<Supplier> supplierList = loadSuppliersList(value);
            ObservableList<Supplier> observableSuppliers
                    = FXCollections.observableArrayList(supplierList);
            SupplierComboBox.setItems(observableSuppliers);
            SupplierComboBox.setValue(null);
        }

    }

    @FXML
    private void SupplierSelectAction(ActionEvent event) {
        Supplier value = SupplierComboBox.getValue();

        if (value != null) {
            System.out.println("Selected Supplier: " + value.getName());
        }
    }

    @FXML
    private void AddBtnAction(ActionEvent event) {
        if (validateTextFields()) {
            System.out.println("Succes values !");
        }
    }

    private boolean validateTextFields() {

        String id = ID_TF.getText() == null ? "" : ID_TF.getText().trim();
        String productName = ProductName_TF.getText() == null ? "" : ProductName_TF.getText().trim();
        String costPriceText = Cost_TF.getText() == null ? "" : Cost_TF.getText().trim();
        String salePriceText = Sale_TF.getText() == null ? "" : Sale_TF.getText().trim();
        String qtyText = Qty_TF.getText() == null ? "" : Qty_TF.getText().trim();
        String amountText = Amount_TF.getText() == null ? "" : Amount_TF.getText().trim();
        String ProductDiscount = PDiscount_TF.getText() == null ? "" : PDiscount_TF.getText().trim();

        if (id.isEmpty()) {
            showError("Product ID is required");
            return false;
        }

        if (productName.isEmpty()) {
            showError("Product name is required");
            return false;
        }

        if (costPriceText.isEmpty() || salePriceText.isEmpty() || qtyText.isEmpty() || amountText.isEmpty()) {
            showError("All numeric fields are required");
            return false;
        }

       
        if (ExpireDatePicker.getValue() == null) {
            showError("Expire date must be selected");
            return false;
        }

        if (ExpireDatePicker.getValue().isBefore(java.time.LocalDate.now())) {
            showError("Expire date cannot be in the past");
            return false;
        }

        double costPrice, salePrice, amount;
        int qty;

        try {
            costPrice = Double.parseDouble(costPriceText);
            salePrice = Double.parseDouble(salePriceText);
            amount = Double.parseDouble(amountText);
            qty = Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            showError("Invalid number format detected");
            return false;
        }

     
        if (costPrice <= 0 || salePrice <= 0 || amount <= 0) {
            showError("Prices and amount must be greater than zero");
            return false;
        }

        if (qty <= 0) {
            showError("Quantity must be greater than zero");
            return false;
        }

        if (salePrice < costPrice) {
            showError("Sale price cannot be less than cost price");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        System.out.println("Validation Error: " + message);
    
    }

    @FXML
    private void ApplyBtnAction(ActionEvent event) {
    }

    private List<Supplier> loadSuppliersList(Company company) {

        List<Supplier> supplierList = new ArrayList<>();

        JPATransaction.runInTransaction(em -> {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Supplier> cq = cb.createQuery(Supplier.class);
            Root<Supplier> table = cq.from(Supplier.class);
            Join<Supplier, SupplyStatus> statusJoin
                    = table.join("supplierStatusId");
            cq.where(
                    cb.equal(
                            table.get("companyId").get("id"),
                            company.getId()
                    ),
                    cb.equal(
                            statusJoin.get("status"),
                            "Active"
                    )
            );
            supplierList.addAll(
                    em.createQuery(cq).getResultList()
            );
        });
        return supplierList;
    }
}
