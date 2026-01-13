/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.inventory;

import com.qb.app.controllers.popup.PopUpProductListController;
import com.qb.app.controllers.table_models.GRNListTable;
import com.qb.app.model.ComboBoxUtils;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.PopUp;
import com.qb.app.model.entity.Company;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.Supplier;
import com.qb.app.model.entity.SupplyStatus;
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private TableView<GRNListTable> table;
    @FXML
    private TableColumn<GRNListTable, String> colProduct;
    @FXML
    private TableColumn<GRNListTable, String> colCostPrice;
    @FXML
    private TableColumn<GRNListTable, String> colSalePrice;
    @FXML
    private TableColumn<GRNListTable, String> colQty;
    @FXML
    private TableColumn<GRNListTable, String> colExpireDate;
    @FXML
    private TableColumn<GRNListTable, String> colAmount;

    /**
     * Initializes the controller class.
     */
    private final ObservableList<GRNListTable> grnList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCompanyComboBox();
        loadSupplierComboBox();

        table.setItems(grnList);

        colProduct.setCellValueFactory(data
                -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getProduct().getProduct())
        );

        colCostPrice.setCellValueFactory(data
                -> new javafx.beans.property.SimpleStringProperty(
                        String.format("%.2f", data.getValue().getCostPrice())
                )
        );

        colSalePrice.setCellValueFactory(data
                -> new javafx.beans.property.SimpleStringProperty(
                        String.format("%.2f", data.getValue().getSalePrice())
                )
        );

        colQty.setCellValueFactory(data
                -> new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(data.getValue().getQty())
                )
        );

        colExpireDate.setCellValueFactory(data
                -> new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getExpireDate().toString()
                )
        );

        colAmount.setCellValueFactory(data
                -> new javafx.beans.property.SimpleStringProperty(
                        String.format("%.2f", data.getValue().getAmount())
                )
        );

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
    Product loadedProduct;
    private GRNListTable editingRow = null;

    @FXML
    private void AddBtnAction(ActionEvent event) {
//        if (validateTextFields()) {
//            System.out.println("Succes values !");
//            Product product details = getProduct();
//              GRNListTable tableProduct = new GRNListTable(productData, 0, LocalDate.EPOCH, 0, 0, 0, 0);
//                table.getItems().add(tableProduct);
//        }

        if (!validateTextFields()) {
            return;
        }

        Product selectedProduct = loadedProduct;
        double enteredQty = Double.parseDouble(Qty_TF.getText());
        double costPrice = Double.parseDouble(Cost_TF.getText());
        double salePrice = Double.parseDouble(Sale_TF.getText());
        double discountPrice = Double.parseDouble(PDiscount_TF.getText());
        double amount = Double.parseDouble(Amount_TF.getText());
        LocalDate expireDate = ExpireDatePicker.getValue();

        // 1️⃣ Check already exists
        GRNListTable existingRow = checkItemAlreadyExists(selectedProduct);

        if (existingRow != null && editingRow == null) {
            // 🔁 MERGE QTY (like DistributeBean)

            double oldQty = existingRow.getQty();
            double newQty = oldQty + enteredQty;

            existingRow.setQty(newQty);
            existingRow.setCostPrice(costPrice);
            existingRow.setSalePrice(salePrice);
            existingRow.setExpireDate(expireDate);
            existingRow.setDiscount(discountPrice);
            existingRow.setAmount(amount);

            existingRow.recalculateAmount();

            table.refresh();

        } else {
            if (editingRow != null) {
                // ✏️ UPDATE EXISTING (EDIT MODE)

                editingRow.setQty(enteredQty);
                editingRow.setCostPrice(costPrice);
                editingRow.setSalePrice(salePrice);
                editingRow.setExpireDate(expireDate);
                editingRow.setDiscount(discountPrice);
                editingRow.setAmount(amount);
                editingRow.recalculateAmount();

                table.refresh();
                editingRow = null;

            } else {
                // ➕ ADD NEW ROW

                GRNListTable newRow = new GRNListTable(
                        selectedProduct,
                        enteredQty,
                        expireDate,
                        costPrice,
                        salePrice,
                        discountPrice,
                        amount
                );

                newRow.recalculateAmount();
                grnList.add(newRow);

//                table.getItems().add(newRow);
            }
        }

        calculateTotal();
        clearInputs();
        table.refresh();
        // UX polish
        Qty_TF.requestFocus();
    }

    private void calculateTotal() {

        double total = 0.0;
        double discount = 0.0;

        for (GRNListTable row : table.getItems()) {
            total += row.getAmount();
            discount += row.getDiscount();
        }

        Total_TF.setText(String.format("%.2f", total));
        Discount_TF.setText(String.format("%.2f", discount));
    }

    private void clearInputs() {

//        loadedProduct.setValue(null);
        Qty_TF.clear();
        Cost_TF.clear();
        Sale_TF.clear();
        Amount_TF.clear();
        ProductName_TF.clear();
        ID_TF.clear();
        PDiscount_TF.clear();
        ExpireDatePicker.setValue(null);

        editingRow = null;

        ID_TF.requestFocus();
    }

    private GRNListTable checkItemAlreadyExists(Product product) {
        for (GRNListTable row : table.getItems()) {
            if (row.getProduct().getId() == product.getId()) {
                return row;
            }
        }
        return null;
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

    @FXML
    private void handleOnKeyPress(KeyEvent event) {
        if (event.getSource() == ID_TF && event.getCode() == KeyCode.ENTER) {
            handleItemIdPressed();
        }
    }

    private void handleItemIdPressed() {
        if (ID_TF.getText().isEmpty()) {
            //load the popUp window
            try {
                PopUp.showPopupAndWait(
                        "popup/popUpProductList.fxml",
                        root,
                        this.root.getScene(),
                        PopUp.PopupType.CENTERED_80_WIDTH,
                        (PopUpProductListController controller) -> {
                            controller.saveController(this);
                        }
                );
            } catch (IOException e) {
                getLogger.logger().warning(e.toString());
            }
        } else {

            Product product = getEnteredProduct();
            if (product != null) {
                loadedProduct = product;
                ProductName_TF.setText(product.getProduct());

                ExpireDatePicker.requestFocus();
            } else {

                showError("The specified item ID could not be found. Please verify the ID and try again.");
                loadedProduct = null;
            }
        }
    }

    private Product getEnteredProduct() {
        //load the product
        return JPATransaction.runInTransaction((em) -> {
            Integer itemID = Integer.valueOf(ID_TF.getText());
            Product product = em.find(Product.class, itemID);

            if (product != null) {
                return product;
            } else {
                return null;
            }
        });
    }

    @FXML
    private void handleOnKeyRelease(KeyEvent event) {
    }

    @FXML
    private void DiscountAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            
              Amount_TF.setText(String.format("%.2f", calculateProductAmount())); 
        }
    }

    private  double  calculateProductAmount() {
        System.out.println(" event work");
        double enteredQty = Double.parseDouble(Qty_TF.getText());
        double costPrice = Double.parseDouble(Cost_TF.getText());
        double discount = Double.parseDouble(PDiscount_TF.getText());
        
        double finalAmount = (enteredQty*costPrice)-discount;
       
          
         return finalAmount;
        

    }
}
