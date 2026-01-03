package com.qb.app.controllers.admin.product;

import com.qb.app.controllers.admin.product.tables.ProductRegistrationTable;
import com.qb.app.database_crud.ProductCRUD;
import com.qb.app.database_crud.ProductTypeCRUD;
import com.qb.app.model.ComboBoxUtils;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.entity.Brand;
import com.qb.app.model.entity.Category;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductType;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Product_registrationController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Group iconPage;
    @FXML
    private TextField tfItemName;
    @FXML
    private TextField tfBarCode;
    @FXML
    private ComboBox<Brand> cbBrand;
    @FXML
    private TextField tfSalePrice;
    @FXML
    private TextField tfCostPrice;
    @FXML
    private TextField tfDiscount;
    @FXML
    private Label registrationMessage;
    @FXML
    private Button btnPicture;
    @FXML
    private ImageView productImage;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnRegister;
    @FXML
    private ComboBox<Category> cbCategory;
    @FXML
    private ComboBox<ProductType> cbProductType;
    @FXML
    private TextField tfUnitMeasure;
    @FXML
    private TableView<ProductRegistrationTable> tableView;
    @FXML
    private TableColumn<ProductRegistrationTable, String> colItemName;
    @FXML
    private TableColumn<ProductRegistrationTable, String> colType;
    @FXML
    private TableColumn<ProductRegistrationTable, Double> colCostPrice;
    @FXML
    private TableColumn<ProductRegistrationTable, Double> colSalePrice;
    @FXML
    private TableColumn<ProductRegistrationTable, Double> colDiscount;
    @FXML
    private TableColumn<ProductRegistrationTable, Double> colUnitMeasure;
    @FXML
    private TableColumn<ProductRegistrationTable, String> colBarCode;
    @FXML
    private Button btnAdd;
    @FXML
    private TextField tfParentId;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runBackgroundThread();
    }

    private void runBackgroundThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                initializeBackgroundProcess();
            }
        };

        Thread bgThread = new Thread(runnable);
        bgThread.start();
    }

    private void initializeBackgroundProcess() {
        configureTables();
        configureInputs();
        loadComboBoxes();
        handleProductTypeSelector();
    }

    private void loadComboBoxes() {
        ComboBoxUtils.loadComboBoxValues(cbBrand, Brand.class, "brand", Brand::getBrand);
        ComboBoxUtils.loadComboBoxValues(cbCategory, Category.class, "category", Category::getCategory);
        ComboBoxUtils.loadComboBoxValues(cbProductType, ProductType.class, "type", ProductType::getType);
    }

    private void configureInputs() {
        tfUnitMeasure.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfDiscount.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfCostPrice.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfSalePrice.setTextFormatter(DefaultAPI.createNumericTextFormatter());
    }

    private void configureTables() {
        colItemName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemName()));
        colType.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getType().getType()));
        colCostPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getCostPrice()).asObject());
        colSalePrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSalePrice()).asObject());
        colDiscount.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getDiscount()).asObject());
        colUnitMeasure.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getUnitMeasure()).asObject());
        colBarCode.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBarCode()));
    }

    @FXML
    private void handleFileChooser(ActionEvent event) {
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnAdd) {
            addProduct();
        } else if (event.getSource() == cbProductType) {
            handleProductTypeSelector();
        } else if (event.getSource() == btnRegister) {
            registerProducts();
        }
    }

    private void registerProducts() {
        List<ProductRegistrationTable> productsList = tableView.getItems();

        for (ProductRegistrationTable productRegistrationTable : productsList) {
            ProductCRUD.createProduct(productRegistrationTable.getProduct(), productRegistrationTable.getType());
        }

    }

    private void handleProductTypeSelector() {
        if (cbProductType.getValue().getType() == null) {
            tfParentId.setDisable(true);
        } else if (cbProductType.getValue().getType().toLowerCase().equals("child")) {
            tfParentId.setDisable(false);
        } else {
            tfParentId.setDisable(true);
        }
    }

    private boolean isParentCreated = false;

    private void addProduct() {
        if (isProductValid()) {
            if (isproductTypeValid()) {
                Product product = new Product();
                product.setProduct(tfItemName.getText());
                product.setMeasure(Float.parseFloat(tfUnitMeasure.getText()));
                product.setCostPrice(
                        tfCostPrice.getText().trim().isEmpty()
                        ? 0.0
                        : Double.parseDouble(tfCostPrice.getText())
                );

                product.setSalePrice(Double.parseDouble(tfSalePrice.getText()));
                if (!tfBarCode.getText().isEmpty()) {
                    product.setBarCode(tfBarCode.getText());
                }

                if (!tfDiscount.getText().isEmpty()) {
                    product.setDiscount(Double.parseDouble(tfDiscount.getText()));
                }

                ProductRegistrationTable productRegistrationTable = new ProductRegistrationTable(cbProductType.getValue(), product);

                if (!isParentCreated && "parent".equals(cbProductType.getValue().getType())) {
                    isParentCreated = true;
                }

                tableView.getItems().add(productRegistrationTable);

                refreshProductAdd();
            }
        }
    }

    private void refreshProductAdd() {
        tfCostPrice.setText("");
        tfSalePrice.setText("");
        tfDiscount.setText("");
        cbProductType.setValue(ProductTypeCRUD.getProductType("child"));
        tfUnitMeasure.setText("");
        tfBarCode.setText("");
    }

    private boolean isproductTypeValid() {
        if (isParentCreated && !"child".equals(cbProductType.getValue().getType())) {
            showWarning("Only one parent item can be added at a time. Please add child items instead.");
            return false;
        }
        return true;
    }

    private boolean isProductValid() {
        // Item name
        if (tfItemName.getText().trim().isEmpty()) {
            showWarning("Item name is required");
            tfItemName.requestFocus();
            return false;
        }

        // Product type
        if (cbProductType.getValue() == null) {
            showWarning("Please select a product type");
            cbProductType.requestFocus();
            return false;
        }

        boolean isChildProduct
                = cbProductType.getValue().getType().equalsIgnoreCase("child");

        // Brand
        if (cbBrand.getValue() == null) {
            showWarning("Please select a brand");
            cbBrand.requestFocus();
            return false;
        }

        // Category
        if (cbCategory.getValue() == null) {
            showWarning("Please select a category");
            cbCategory.requestFocus();
            return false;
        }

        // Unit measure
        if (tfUnitMeasure.getText().trim().isEmpty()) {
            showWarning("Unit measure is required");
            tfUnitMeasure.requestFocus();
            return false;
        }

        double unitMeasure;
        try {
            unitMeasure = Double.parseDouble(tfUnitMeasure.getText());
            if (unitMeasure <= 0) {
                showWarning("Unit measure must be greater than zero");
                tfUnitMeasure.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showWarning("Invalid unit measure");
            tfUnitMeasure.requestFocus();
            return false;
        }

        Double costPrice = null;

        // Cost price (REQUIRED only for NON-child products)
        if (!isChildProduct) {

            if (tfCostPrice.getText().trim().isEmpty()) {
                showWarning("Cost price is required");
                tfCostPrice.requestFocus();
                return false;
            }

            try {
                costPrice = Double.parseDouble(tfCostPrice.getText());
                if (costPrice < 0) {
                    showWarning("Cost price cannot be negative");
                    tfCostPrice.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                showWarning("Invalid cost price");
                tfCostPrice.requestFocus();
                return false;
            }

        } else {
            // Child product → cost is OPTIONAL
            if (!tfCostPrice.getText().trim().isEmpty()) {
                try {
                    costPrice = Double.parseDouble(tfCostPrice.getText());
                    if (costPrice < 0) {
                        showWarning("Cost price cannot be negative");
                        tfCostPrice.requestFocus();
                        return false;
                    }
                } catch (NumberFormatException e) {
                    showWarning("Invalid cost price");
                    tfCostPrice.requestFocus();
                    return false;
                }
            }
        }

        // Sale price (ALWAYS required)
        if (tfSalePrice.getText().trim().isEmpty()) {
            showWarning("Sale price is required");
            tfSalePrice.requestFocus();
            return false;
        }

        double salePrice;
        try {
            salePrice = Double.parseDouble(tfSalePrice.getText());
            if (salePrice < 0) {
                showWarning("Sale price cannot be negative");
                tfSalePrice.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showWarning("Invalid sale price");
            tfSalePrice.requestFocus();
            return false;
        }

        // Sale >= Cost (only if cost exists)
        if (costPrice != null && salePrice < costPrice) {
            showWarning("Sale price cannot be less than cost price");
            tfSalePrice.requestFocus();
            return false;
        }

        // Discount (optional)
        if (!tfDiscount.getText().trim().isEmpty()) {
            try {
                double discount = Double.parseDouble(tfDiscount.getText());
                if (discount < 0) {
                    showWarning("Discount cannot be negative");
                    tfDiscount.requestFocus();
                    return false;
                }
                if (discount > salePrice) {
                    showWarning("Discount cannot be greater than sale price");
                    tfDiscount.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                showWarning("Invalid discount value");
                tfDiscount.requestFocus();
                return false;
            }
        }

        return true;
    }

    private void showWarning(String message) {
        CustomAlert.showStyledAlert(
                root,
                message,
                "Validation Error",
                Alert.AlertType.WARNING
        );
    }

}
