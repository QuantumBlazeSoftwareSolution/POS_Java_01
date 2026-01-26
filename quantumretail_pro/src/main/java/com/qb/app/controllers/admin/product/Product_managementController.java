/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.product;

import com.jfoenix.controls.JFXToggleButton;
import com.qb.app.controllers.popup.PopUpProductListController;
import com.qb.app.database_crud.CategoryHasBrandCRUD;
import com.qb.app.database_crud.ProductCRUD;
import com.qb.app.database_crud.ProductHasProductTypeCRUD;
import com.qb.app.database_crud.ProductTypeCRUD;
import com.qb.app.model.ComboBoxUtils;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.PopUp;
import com.qb.app.model.SinhalaInputNormalizer;
import com.qb.app.model.entity.Brand;
import com.qb.app.model.entity.Category;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductHasProductType;
import com.qb.app.model.entity.ProductType;
import com.qb.app.model.getLogger;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private TextField tfId;
    @FXML
    private ComboBox<Category> cbCategory;
    @FXML
    private Label registrationMessage;
    @FXML
    private AnchorPane root;
    @FXML
    private JFXToggleButton statusToggle;
    @FXML
    private Button btnUpdate;

    private boolean isProduct = true;
    private Product loadedProduct;
    private ProductHasProductType loadedProductHasProductType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadComboBoxes();
        configureInputs();

        cbBrand.setPromptText("Select Brand");
        cbCategory.setPromptText("Select Category");
        cbType.setPromptText("Select Product Type");
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
        tfId.setTextFormatter(SinhalaInputNormalizer.createNormalizedNumericFormatter());
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

        if (tfParentID.getText().trim().isEmpty()) {
            showWarning("Parent ID is required");
            tfParentID.requestFocus();
            return false;
        }

        if (ProductCRUD.searchProductById(Integer.parseInt(tfParentID.getText())) == null) {
            showWarning("Invalid parent product ID.");
            tfParentID.requestFocus();
            return false;
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

    @FXML
    private void handleKeyPressEvent(KeyEvent event) {
        if (event.getSource() == tfId && event.getCode() == KeyCode.ENTER) {
            if (!tfId.getText().isEmpty()) {
                loadProductDetails(ProductCRUD.searchProductById(Integer.parseInt(tfId.getText())));
            } else {
                isProduct = true;
                openProductPopup();
            }
        } else if (event.getSource() == tfParentID && event.getCode() == KeyCode.ENTER) {
            isProduct = false;
            openProductPopup();
        }
    }

    private void loadProductDetails(Product product) {
        if (product != null) {
            this.loadedProduct = product;
            ProductHasProductType productHasProductType = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(product);

            this.loadedProductHasProductType = productHasProductType;

            tfItemName.setText(product.getProduct());
            tfBarCode.setText("");
            tfSalePrice.setText(String.valueOf(product.getSalePrice()));
            tfCostPrice.setText(String.valueOf(product.getCostPrice()));
            tfDiscount.setText(String.valueOf(product.getDiscount()));
            tfMeasure.setText(String.valueOf(product.getMeasure()));
            cbBrand.getSelectionModel().select(product.getCategoryHasBrandId().getBrandId());
            cbCategory.getSelectionModel().select(product.getCategoryHasBrandId().getCategoryId());
            cbType.getSelectionModel().select(productHasProductType.getProductTypeId());
            tfParentID.setText(String.valueOf(productHasProductType.getReferenceId().getId()));
            statusToggle.setSelected(product.getProductStatusId().getStatus().toLowerCase().equals("active"));
        } else {
            this.loadedProduct = null;
            this.loadedProductHasProductType = null;
            CustomAlert.showStyledAlert(
                    root,
                    "The selected product could not be found.",
                    "Unable to load product details",
                    Alert.AlertType.WARNING
            );
        }
    }

    public void setParentProduct(Product product) {
        if (isProduct) {
            System.out.println("Product Loaded");
            tfId.setText(String.valueOf(product.getId()));
            loadProductDetails(product);
        } else if (!isProduct) {
            System.out.println("Parent Product Loaded");
            tfParentID.setText(String.valueOf(product.getId()));
        }
    }

    public void openProductPopup() {
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
        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    @FXML
    private void handleComboBoxEvent(ActionEvent event) {
        if (event.getSource() == cbType) {
            if (cbType.getValue() != null && cbType.getValue().getType().toLowerCase().equals("child")) {
                tfParentID.setDisable(false);
            } else {
                tfParentID.setDisable(true);
            }
        }
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnUpdate) {
            validateAndUpdateProduct();
        } else if (event.getSource() == btnClear) {
            refreshProductManagementPage();
        }
    }

    private void validateAndUpdateProduct() {
        if (isProductValid()) {
            if (this.loadedProduct != null) {
                this.loadedProduct.setProduct(tfItemName.getText());
//                this.loadedProduct.setBarCode(tfBarCode.getText());
                this.loadedProduct.setCategoryHasBrandId(
                        CategoryHasBrandCRUD.getCategoryHasBrand(
                                cbBrand.getValue(),
                                cbCategory.getValue()
                        )
                );
                this.loadedProduct.setSalePrice(Double.parseDouble(tfSalePrice.getText()));
                this.loadedProduct.setCostPrice(Double.parseDouble(tfCostPrice.getText()));
                this.loadedProduct.setDiscount(Double.parseDouble(tfDiscount.getText()));
                this.loadedProduct.setMeasure(Float.parseFloat(tfMeasure.getText()));

                Product savedProduct = ProductCRUD.updateProduct(this.loadedProduct);
                this.loadedProductHasProductType.setReferenceId(ProductCRUD.searchProductById(Integer.parseInt(tfParentID.getText())));

                if (savedProduct != null) {
                    refreshProductManagementPage();
                    CustomAlert.showStyledAlert(
                            root,
                            "The product details have been updated successfully.",
                            "Update Successful",
                            Alert.AlertType.INFORMATION
                    );
                } else {
                    CustomAlert.showStyledAlert(
                            root,
                            "We couldn’t update the product. Please review the details and try again.",
                            "Update Failed",
                            Alert.AlertType.ERROR
                    );
                }
            }
        }
    }

    private void refreshProductManagementPage() {

        this.loadedProduct = null;
        this.loadedProductHasProductType = null;
        isProduct = true;

        tfId.clear();
        tfItemName.clear();
        tfBarCode.clear();
        tfSalePrice.clear();
        tfCostPrice.clear();
        tfDiscount.clear();
        tfMeasure.clear();
        tfParentID.clear();

        cbBrand.setValue(null);
        cbCategory.setValue(null);
        cbType.setValue(null);

        tfParentID.setDisable(true);

        statusToggle.setSelected(false);

        productImage.setImage(null);

        tfId.requestFocus();
    }

}
