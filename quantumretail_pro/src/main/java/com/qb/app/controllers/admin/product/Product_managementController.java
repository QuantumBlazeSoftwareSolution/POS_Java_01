/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.product;

import com.jfoenix.controls.JFXToggleButton;
import com.qb.app.model.ComboBoxUtils;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.entity.Brand;
import com.qb.app.model.entity.Category;
import com.qb.app.model.entity.ProductType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Product_managementController implements Initializable {

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
    private TextField tfMeasure;
    @FXML
    private ComboBox<ProductType> cbType;
    @FXML
    private TextField tfParentID;
    @FXML
    private Button btnPicture;
    @FXML
    private ImageView productImage;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnRegister;
    @FXML
    private TextField tfId;
    @FXML
    private ComboBox<Category> cbCategory;
    @FXML
    private Label registrationMessage;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXToggleButton statusToggle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboBoxes();
        configureInputs();
    }

    @FXML
    private void handleFileChooser(ActionEvent event) {
    }

    private void loadComboBoxes() {
        ComboBoxUtils.loadComboBoxValues(cbBrand, Brand.class, "brand", Brand::getBrand);
        ComboBoxUtils.loadComboBoxValues(cbCategory, Category.class, "category", Category::getCategory);
        ComboBoxUtils.loadComboBoxValues(cbType, ProductType.class, "type", ProductType::getType);
    }

    private void configureInputs() {
        tfId.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfSalePrice.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfCostPrice.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfDiscount.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfMeasure.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfParentID.setTextFormatter(DefaultAPI.createNumericTextFormatter());
    }
    
    private boolean isProductValid() {
        // Item name
        if (tfItemName.getText().trim().isEmpty()) {
            showWarning("Item name is required");
            tfItemName.requestFocus();
            return false;
        }

        // Product type
        if (cbType.getValue() == null) {
            showWarning("Please select a product type");
            cbType.requestFocus();
            return false;
        }

        boolean isChildProduct
                = cbType.getValue().getType().equalsIgnoreCase("child");

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
        if (tfMeasure.getText().trim().isEmpty()) {
            showWarning("Unit measure is required");
            tfMeasure.requestFocus();
            return false;
        }

        double unitMeasure;
        try {
            unitMeasure = Double.parseDouble(tfMeasure.getText());
            if (unitMeasure <= 0) {
                showWarning("Unit measure must be greater than zero");
                tfMeasure.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showWarning("Invalid unit measure");
            tfMeasure.requestFocus();
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
