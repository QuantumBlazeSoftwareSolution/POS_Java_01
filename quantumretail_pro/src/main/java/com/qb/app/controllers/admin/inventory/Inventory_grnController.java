/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.inventory;

import com.qb.app.controllers.popup.PopUpProductListController;
import com.qb.app.controllers.table_models.GRNListTable;
import com.qb.app.model.ComboBoxUtils;
import com.qb.app.model.Config;
import com.qb.app.model.ConfigManager;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.PopUp;
import com.qb.app.model.entity.Company;
import com.qb.app.model.entity.Grn;
import com.qb.app.model.entity.GrnItem;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.entity.StockStatus;
import com.qb.app.model.entity.Supplier;
import com.qb.app.model.entity.SupplyStatus;
import com.qb.app.model.getLogger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
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

    @FXML
    private TableColumn<GRNListTable, String> colDiscount;
    /**
     * Initializes the controller class.
     */
    private final ObservableList<GRNListTable> grnList = FXCollections.observableArrayList();
    @FXML
    private Button Refesh;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCompanyComboBox();
        loadSupplierComboBox();

        handleTableDoubleClick();

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

        colDiscount.setCellValueFactory(data
                -> new javafx.beans.property.SimpleStringProperty(
                        String.format("%.2f", data.getValue().getDiscount())
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

        if (!validateTextFields()) {
            return;
        }

        Product selectedProduct = loadedProduct;
        double enteredQty = Double.parseDouble(Qty_TF.getText());
        double costPrice = Double.parseDouble(Cost_TF.getText());
        double salePrice = Double.parseDouble(Sale_TF.getText());
        double discountPrice = Double.parseDouble(PDiscount_TF.getText());
//        double amount = Double.parseDouble(Amount_TF.getText());
        double amount = calculateProductAmount();

        LocalDate expireDate = ExpireDatePicker.getValue();
        Amount_TF.setText(String.format("%.2f", amount));

        GRNListTable existingRow = checkItemAlreadyExists(selectedProduct, costPrice, salePrice, expireDate, discountPrice);

        if (existingRow != null && editingRow == null) {
            // Merge quantity only
            double newQty = existingRow.getQty() + enteredQty;
            existingRow.setQty(newQty);
            existingRow.recalculateAmount();
            table.refresh();
        } else {
            if (editingRow != null) {
                // ✏️ Update row
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
                // ➕ Add new row (either new product or same ID but different details)
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
            }
        }

        calculateTotal();
        clearInputs();
        table.refresh();
        // UX polish
        ID_TF.requestFocus();
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
        Add_Btn.setText("Add");

        ID_TF.requestFocus();
    }

    private GRNListTable checkItemAlreadyExists(Product product, double costPrice, double salePrice, LocalDate expireDate, double discountPerUnit) {
        for (GRNListTable row : table.getItems()) {
            if (row.getProduct().getId() == product.getId()
                    && row.getCostPrice() == costPrice
                    && row.getSalePrice() == salePrice
                    && row.getExpireDate().equals(expireDate)
                    && (row.getDiscount() / row.getQty()) == discountPerUnit) {

                return row; // exact match found → merge qty
            }
        }
        return null; // no exact match → new row
    }

    private boolean validateTextFields() {

        String id = ID_TF.getText() == null ? "" : ID_TF.getText().trim();
        String productName = ProductName_TF.getText() == null ? "" : ProductName_TF.getText().trim();
        String costPriceText = Cost_TF.getText() == null ? "" : Cost_TF.getText().trim();
        String salePriceText = Sale_TF.getText() == null ? "" : Sale_TF.getText().trim();
        String qtyText = Qty_TF.getText() == null ? "" : Qty_TF.getText().trim();
        String ProductDiscount = PDiscount_TF.getText() == null ? "" : PDiscount_TF.getText().trim();

        if (id.isEmpty()) {
            showError("Product ID is required");
            return false;
        }

        if (productName.isEmpty()) {
            showError("Product name is required");
            return false;
        }

        if (costPriceText.isEmpty() || salePriceText.isEmpty() || qtyText.isEmpty()) {
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

        double costPrice, salePrice, qty, amount;
//        int qty;

        try {
            costPrice = Double.parseDouble(costPriceText);
            salePrice = Double.parseDouble(salePriceText);
            qty = Double.parseDouble(qtyText);
//            qty = Integer.parseInt(qtyText);
        } catch (NumberFormatException e) {
            showError("Invalid number format detected");
            return false;
        }

        if (costPrice <= 0 || salePrice <= 0) {
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

    public void setParentProduct(Product product) {

        loadedProduct = product;
        ID_TF.setText(String.valueOf(loadedProduct.getId()));
        ProductName_TF.setText(String.valueOf(loadedProduct.getProduct()));
        ExpireDatePicker.requestFocus();

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

    private void DiscountAction(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            double amount = calculateProductAmount();
            Amount_TF.setText(String.format("%.2f", amount));
        }
    }

    private double calculateProductAmount() {
        double enteredQty = Double.parseDouble(Qty_TF.getText());
        double costPrice = Double.parseDouble(Cost_TF.getText());
        double discount = Double.parseDouble(PDiscount_TF.getText());
        double finalAmount = (enteredQty * costPrice) - discount * enteredQty;

        return finalAmount;

    }

    @FXML
    private void handleEnterFlow(KeyEvent event) {

        if (event.getCode() != KeyCode.ENTER) {
            return;
        }

        Object src = event.getSource();

        if (src == ExpireDatePicker) {
            Qty_TF.requestFocus();

        } else if (src == Qty_TF) {
            setZeroIfEmpty(Qty_TF);
            Cost_TF.requestFocus();

        } else if (src == Cost_TF) {
            setZeroIfEmpty(Cost_TF);
            Sale_TF.requestFocus();

        } else if (src == Sale_TF) {
            setZeroIfEmpty(Sale_TF);
            PDiscount_TF.requestFocus();

        } else if (src == PDiscount_TF) {
            setZeroIfEmpty(PDiscount_TF);

            double amount = calculateProductAmount();
            Amount_TF.setText(String.format("%.2f", amount));

            Add_Btn.requestFocus();
        } else if (src == GRNID_TF) {
            loadGRNDetails();
        

        }

    }

    private void setZeroIfEmpty(TextField tf) {
        if (tf.getText() == null || tf.getText().trim().isEmpty()) {
            tf.setText("0");
        }
    }

    @FXML
    private void handleTableDoubleClick() {

        table.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) {

                GRNListTable selectedRow = table.getSelectionModel().getSelectedItem();

                if (selectedRow == null) {
                    return;
                }

                // 🔁 ENTER EDIT MODE
                editingRow = selectedRow;
                loadedProduct = selectedRow.getProduct();

                // Fill form
                ID_TF.setText(String.valueOf(loadedProduct.getId()));
                ProductName_TF.setText(loadedProduct.getProduct());

                Qty_TF.setText(String.valueOf(selectedRow.getQty()));
                Cost_TF.setText(String.format("%.2f", selectedRow.getCostPrice()));
                Sale_TF.setText(String.format("%.2f", selectedRow.getSalePrice()));
                PDiscount_TF.setText(String.format("%.2f", selectedRow.getDiscount() / selectedRow.getQty()));
                Amount_TF.setText(String.format("%.2f", selectedRow.getAmount()));

                ExpireDatePicker.setValue(selectedRow.getExpireDate());

                // UX
                Qty_TF.requestFocus();
                Add_Btn.setText("Update");
            }
        });
    }

    @FXML
    private void RefeshBtnAction(ActionEvent event) {
        clearInputs();
    }

    @FXML
    private void swipetable(SwipeEvent event) {
        System.out.println("swipe");
    }

    @FXML
    private void ApplyBtnAction(ActionEvent event) {

        if (SupplierComboBox.getValue() == null) {
//        CustomAlert.show(Alert.AlertType.ERROR, "Validation Error", "Please select a supplier");
            System.out.println("Please select a supplier");
            return;
        }

        if (grnList.isEmpty()) {
//        CustomAlert.show(Alert.AlertType.ERROR, "Validation Error", "GRN item list is empty");
            System.out.println("GRN item list is empty");
            return;
        }

        saveGRN();
    }

    private void saveGRN() {

        JPATransaction.runInTransaction(em -> {
            Config con = null;

            try {
                con = ConfigManager.loadConfig();
            } catch (Exception e) {
            }

            /* =========================
           1️⃣ CREATE GRN
        ========================== */
            Grn grn = new Grn();
            grn.setGrnCode(GRNID_TF.getText().trim());
            grn.setDateTime(new Date());
            grn.setSupplierId(SupplierComboBox.getValue());
            grn.setDiscount(Double.parseDouble(Discount_TF.getText()));

            em.persist(grn);
            em.flush();

            StockStatus availableStatus = em.find(StockStatus.class, 1);

            /* =========================
           2️⃣ LOOP GRN ITEMS
        ========================== */
            for (GRNListTable row : grnList) {

                /* ---- GRN ITEM ---- */
                GrnItem item = new GrnItem();
                item.setGrnId(grn);
                item.setProductId(row.getProduct());
                item.setQty(row.getQty());
                item.setCostPrice(row.getCostPrice());
                item.setSalePrice(row.getSalePrice());
                em.persist(item);

                /* =========================
               3️⃣ STOCK LOGIC
            ========================== */
                if (con.system.multi_stock) {

                    Date expireDate = toDate(row.getExpireDate());

                    Stock existingStock = findMatchingStock(
                            em,
                            row.getProduct(),
                            row.getCostPrice(),
                            row.getSalePrice(),
                            row.getDiscount(),
                            expireDate
                    );

                    if (existingStock != null) {
                        // ✅ SAME BATCH → ADD QTY
                        existingStock.setQty(
                                existingStock.getQty() + row.getQty()
                        );
                        em.merge(existingStock);

                    } else {
                        // ➕ NEW BATCH
                        Stock stock = new Stock();
                        stock.setProductId(row.getProduct());
                        stock.setSupplierId(grn.getSupplierId());
                        stock.setGrnId(grn);
                        stock.setQty(row.getQty());
                        stock.setCostPrice(row.getCostPrice());
                        stock.setSalePrice(row.getSalePrice());
                        stock.setDiscount(row.getDiscount());
                        stock.setReceivedDate(new Date());
                        stock.setExpireDate(expireDate);
                        stock.setStockStatusId(availableStatus);

                        em.persist(stock);
                    }

                } else {
                    // 🔸 SINGLE STOCK → MERGE INTO ONE

                    Stock existingStock = findStockByProduct(em, row.getProduct());

                    if (existingStock != null) {
                        // UPDATE EXISTING STOCK

                        existingStock.setQty(
                                existingStock.getQty() + row.getQty()
                        );

                        // Optional business rules
                        existingStock.setCostPrice(row.getCostPrice());
                        existingStock.setSalePrice(row.getSalePrice());
                        existingStock.setDiscount(row.getDiscount());

                        // Keep earliest expiry
                        Date newExpire = toDate(row.getExpireDate());
                        if (newExpire.before(existingStock.getExpireDate())) {
                            existingStock.setExpireDate(newExpire);
                        }

                        em.merge(existingStock);

                    } else {
                        // CREATE FIRST STOCK

                        Stock stock = new Stock();
                        stock.setProductId(row.getProduct());
                        stock.setSupplierId(grn.getSupplierId());
                        stock.setGrnId(grn);
                        stock.setQty(row.getQty());
                        stock.setCostPrice(row.getCostPrice());
                        stock.setSalePrice(row.getSalePrice());
                        stock.setDiscount(row.getDiscount());
                        stock.setReceivedDate(new Date());
                        stock.setExpireDate(toDate(row.getExpireDate()));
                        stock.setStockStatusId(availableStatus);

                        em.persist(stock);
                    }
                }
            }
        });

        // UI RESET
        System.out.println("GRN saved successfully");
        grnList.clear();
        clearInputs();
        Total_TF.clear();
        Discount_TF.clear();
    }

    private Date toDate(LocalDate localDate) {
        return Date.from(
                localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()
        );
    }

    private Stock findStockByProduct(EntityManager em, Product product) {

        List<Stock> list = em.createQuery(
                "SELECT s FROM Stock s WHERE s.productId = :product",
                Stock.class
        )
                .setParameter("product", product)
                .setMaxResults(1)
                .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    private Stock findMatchingStock(
            EntityManager em,
            Product product,
            double costPrice,
            double salePrice,
            double discount,
            Date expireDate
    ) {
        List<Stock> list = em.createQuery(
                "SELECT s FROM Stock s "
                + "WHERE s.productId = :product "
                + "AND s.costPrice = :costPrice "
                + "AND s.salePrice = :salePrice "
                + "AND s.discount = :discount "
                + "AND s.expireDate = :expireDate "
                + "AND s.stockStatusId.id = 1",
                Stock.class
        )
                .setParameter("product", product)
                .setParameter("costPrice", costPrice)
                .setParameter("salePrice", salePrice)
                .setParameter("discount", discount)
                .setParameter("expireDate", expireDate)
                .setMaxResults(1)
                .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    private void loadGRNDetails() {

        String grnCode = GRNID_TF.getText().trim();

        if (grnCode.isEmpty()) {
            return;
        }

        grnList.clear();

        JPATransaction.runInTransaction(em -> {

            /* =========================
           1️⃣ LOAD GRN
        ========================== */
            Grn grn;

            try {
                grn = em.createQuery(
                        "SELECT g FROM Grn g WHERE g.grnCode = :code",
                        Grn.class
                )
                        .setParameter("code", grnCode)
                        .getSingleResult();

            } catch (NoResultException e) {
             
                    System.out.println("GRN not found");
                    clearInputs();
               
                return;
            }

            /* =========================
           2️⃣ SET HEADER DATA
        ========================== */
           
                SupplierComboBox.setValue(grn.getSupplierId());
                Discount_TF.setText(String.valueOf(grn.getDiscount()));
        

            /* =========================
           3️⃣ LOAD GRN ITEMS
        ========================== */
            List<GrnItem> items = em.createQuery(
                    "SELECT gi FROM GrnItem gi WHERE gi.grnId = :grn",
                    GrnItem.class
            )
                    .setParameter("grn", grn)
                    .getResultList();

            /* =========================
           4️⃣ MAP TO TABLE MODEL
        ========================== */
            for (GrnItem item : items) {

                GRNListTable row = new GRNListTable();

                row.setProduct(item.getProductId());
                row.setQty(item.getQty());
                row.setCostPrice(item.getCostPrice());
                row.setSalePrice(item.getSalePrice());

                // optional defaults
                row.setDiscount(0);

                row.recalculateAmount();

              grnList.add(row);
            }

            /* =========================
           5️⃣ FINAL UI UPDATE
        ========================== */
          
                calculateTotal();
            
        });
    }

}
