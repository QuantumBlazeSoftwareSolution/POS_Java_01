package com.qb.app.controllers.admin;

import com.qb.app.controllers.cashier.Stock_popupController;
import com.qb.app.controllers.popup.PopUpProductListController;
import com.qb.app.database_crud.ProductCRUD;
import com.qb.app.database_crud.ProductHasProductTypeCRUD;
import com.qb.app.database_crud.StockCRUD;
import com.qb.app.model.Config;
import com.qb.app.model.ConfigManager;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.PopUp;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductHasProductType;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.getLogger;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class DiscountController implements Initializable {

    @FXML
    private Group iconBillDiscountTopic;
    @FXML
    private Group iconProductDiscountTopic;
    @FXML
    private TextField tfDiscountProductId;
    @FXML
    private TextField tfDiscountProductName;
    @FXML
    private TextField tfDiscountValue;
    @FXML
    private Button btnDiscountClear;
    @FXML
    private Button btnDiscountAction;
    @FXML
    private AnchorPane root;
    private Product selectedProduct;
    private Stock selectedStock;
    private boolean isParent;
    private Config systemConfig;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTextFields();
        loadSystemConfig();
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnDiscountClear) {
            refreshDiscountPage();
        } else if (event.getSource() == btnDiscountAction) {
            if (addDiscount()) {
                CustomAlert.showStyledAlert(
                        root,
                        "Discount has been successfully applied to " + this.selectedProduct.getProduct(),
                        "Discount Applied",
                        Alert.AlertType.INFORMATION);
                refreshDiscountPage();
            }
        }
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getSource() == tfDiscountProductId && event.getCode() == KeyCode.ENTER) {
            if (tfDiscountProductId.getText().isEmpty()) {
                openProductListPopUp();
            } else {
                try {
                    int id = Integer.parseInt(tfDiscountProductId.getText());
                    Product product = ProductCRUD.searchProductById(id);

                    if (product != null) {
                        setParentProduct(product);
                    } else {
                        CustomAlert.showStyledAlert(
                                root,
                                "The entered product ID could not be found. Please verify the ID and try again.",
                                "Product Not Found",
                                Alert.AlertType.ERROR);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    getLogger.logger().warning(e.toString());
                    CustomAlert.showStyledAlert(
                            root,
                            "Please enter a valid numeric product ID.",
                            "Invalid Input",
                            Alert.AlertType.ERROR
                    );
                }
            }
        }
    }

    public void closeWithClear() {
        refreshDiscountPage();
    }

    private void loadSystemConfig() {
        try {
            this.systemConfig = ConfigManager.loadConfig();
        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    private void configureTextFields() {
        tfDiscountValue.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfDiscountProductId.setTextFormatter(DefaultAPI.createNumericTextFormatter());
    }

    private void openProductListPopUp() {
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
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
            CustomAlert.showStyledAlert(
                    root,
                    "Unable to load the product list at this time. Please try again later.",
                    "System Error",
                    Alert.AlertType.ERROR);
        }
    }

    public void setParentProduct(Product product) {
        refreshDiscountPage();

        if (product != null) {
            this.selectedProduct = product;

            ProductHasProductType productType = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(product);

            if (productType == null || productType.getProductTypeId() == null) {
                CustomAlert.showStyledAlert(
                        root,
                        "Product type information is missing for this product.",
                        "System Error",
                        Alert.AlertType.ERROR
                );
                return;
            }

            tfDiscountProductId.setText(this.selectedProduct.getId().toString());

            if (productType.getProductTypeId().getType().toLowerCase().equals("parent") && systemConfig.system.multi_stock) {
                this.isParent = true;
                openStockPopup(product);
            } else {
                this.isParent = false;
                tfDiscountProductName.setText(this.selectedProduct.getProduct());
                tfDiscountValue.setText(
                        String.format(
                                DefaultAPI.currencyFloatFormat,
                                this.selectedProduct.getDiscount()
                        )
                );
            }
        }
    }

    private void refreshDiscountPage() {
        this.selectedProduct = null;
        this.selectedStock = null;
        this.isParent = false;
        tfDiscountProductName.setText("");
        tfDiscountProductId.setText("");
        tfDiscountValue.setText("");
    }

    public void setSelectedStock(Stock stock, Product product) {
        this.selectedStock = stock;
        this.selectedProduct = product;
        if (stock != null && product != null) {
            tfDiscountProductName.setText(product.getProduct());
            tfDiscountValue.setText(
                    String.format(
                            DefaultAPI.currencyFloatFormat,
                            stock.getDiscount()
                    )
            );
        }
    }

    private void openStockPopup(Product product) {
        try {
            PopUp.showPopupAndWait(
                    "cashier/stock_popup.fxml",
                    root,
                    this.root.getScene(),
                    PopUp.PopupType.CENTERED_80_WIDTH,
                    (Stock_popupController controller) -> {
                        controller.saveController(this);
                        controller.setProduct(product);
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
            CustomAlert.showStyledAlert(
                    root,
                    "Unable to load stock information for the selected product. Please try again.",
                    "System Error",
                    Alert.AlertType.ERROR);
        }
    }

    private boolean addDiscount() {

        if (tfDiscountValue.getText().isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "Please enter a valid discount amount before proceeding.",
                    "Invalid Input",
                    Alert.AlertType.ERROR);
            return false;
        }

        double discount = Double.parseDouble(tfDiscountValue.getText());

        if (discount <= 0) {
            CustomAlert.showStyledAlert(
                    root,
                    "Discount amount must be greater than Rs. 0.00.",
                    "Invalid Discount Amount",
                    Alert.AlertType.ERROR);
            return false;
        }

        if (this.selectedProduct == null) {
            CustomAlert.showStyledAlert(
                    root,
                    "No product has been selected. Please select a product before applying a discount.",
                    "Action Required",
                    Alert.AlertType.ERROR);
            return false;
        }

        if (this.isParent) {
            if (this.selectedStock != null) {
                applyDiscount(this.isParent);
            } else {
                CustomAlert.showStyledAlert(
                        root,
                        "Please select a stock item before applying a discount to this product.",
                        "Stock Selection Required",
                        Alert.AlertType.ERROR);
                return false;
            }
        } else {
            applyDiscount(this.isParent);
        }

        return true;
    }

    private void applyDiscount(boolean isParent) {
        double discount = Double.parseDouble(tfDiscountValue.getText());

        if (isParent) {
            this.selectedStock.setDiscount(discount);
            StockCRUD.updateStock(this.selectedStock);
        } else {
            this.selectedProduct.setDiscount(discount);
            ProductCRUD.updateProduct(this.selectedProduct);
        }
    }

}
