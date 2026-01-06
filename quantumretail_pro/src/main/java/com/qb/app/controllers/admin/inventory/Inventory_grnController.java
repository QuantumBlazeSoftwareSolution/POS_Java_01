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
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
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
            System.out.println("Selected Company: " + value.getName());
            List<Supplier> supplierList = loadSuppliersList(value);

            // Convert List to ObservableList
            ObservableList<Supplier> observableSuppliers
                    = FXCollections.observableArrayList(supplierList);

            // Load suppliers into ComboBox
            SupplierComboBox.setItems(observableSuppliers);

            // Optional: clear previous selection
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

            cq.where(
                    cb.equal(
                            table.get("companyId").get("id"),
                            company.getId()
                    )
            );

            supplierList.addAll(
                    em.createQuery(cq).getResultList()
            );
        });

        return supplierList;
    }

}
