package com.qb.app.controllers.cashier;

import com.qb.app.database_crud.ProductHasProductTypeCRUD;
import com.qb.app.database_crud.StockCRUD;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.ProductHasProductType;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.getLogger;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Stock_popup_barcodeController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button btnClose;
    @FXML
    private TilePane productContainer;
    @FXML
    private Label labelParentName;
    @FXML
    private TextField tfSalePrice;
    @FXML
    private TextField tfCostPrice;
    @FXML
    private DatePicker dpExpireDate;
    @FXML
    private TextField tfDiscount;
    @FXML
    private TextField tfBarcode;
    @FXML
    private Button btnAction;

    public static Object callingController;
    private Product parentProduct;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfSalePrice.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfCostPrice.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfDiscount.setTextFormatter(DefaultAPI.createNumericTextFormatter());
    }

    public void saveController(CashierInvoiceController object) {
        Stock_popup_barcodeController.callingController = object;
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnClose) {
            closeWindow(false);
        } else if (event.getSource() == btnAction) {
            createStock();
        }
    }

    private void createStock() {
        try {
            String discountStr = tfDiscount.getText();
            double discount = 0;
            if (discountStr.isEmpty() || "".equals(discountStr)) {
                discount = 0;
            } else {
                discount = Double.parseDouble(discountStr);
            }

            Stock stock = StockCRUD.createSingleTemporaryStock(
                    this.parentProduct,
                    tfSalePrice.getText(),
                    tfCostPrice.getText(),
                    dpExpireDate.getValue(),
                    tfBarcode.getText(),
                    discount
            );

            setStock(stock);

            System.out.println("New Stock Created");
        } catch (Exception e) {
            CustomAlert.showStyledAlert(
                    root,
                    "Tempory stock creation failed, please try again later",
                    "Stock creation failed",
                    Alert.AlertType.WARNING);
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    public void setStock(Stock stock) {
        try {
            callingController
                    .getClass()
                    .getMethod("setSelectedStock", Stock.class, Product.class)
                    .invoke(callingController, stock, stock.getProductId());

            closeWindow(true);
        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    public void closeWindow(boolean closeWithStock) {
        try {
            if (!closeWithStock) {
                callingController
                        .getClass()
                        .getMethod("closeWithClear")
                        .invoke(callingController);
            }

            InterfaceAction.closeWindow(root);
        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }

    }

    public void setStocks(List<Stock> stocks) {
        this.parentProduct = null;
        labelParentName.setText("");

        Stock stockItem = stocks.get(0);
        Product product = stockItem.getProductId();

        ProductHasProductType productType = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(product);
        if (productType != null) {
            this.parentProduct = productType.getReferenceId();
            labelParentName.setText(productType.getReferenceId().getProduct());
        }

        for (Stock stock : stocks) {
            try {
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/qb/app/cashier/stock_item_card.fxml")
                );

                VBox cardBox = loader.load();

                Stock_item_cardController controller = loader.getController();
                controller.setData(
                        stock,
                        stock.getProductId().getProduct(),
                        stock.getSalePrice(),
                        DefaultAPI.formatDateObject(
                                stock.getExpireDate(),
                                "dd MMM, yyyy"
                        )
                );
                controller.setParent(this);

                productContainer.getChildren().add(cardBox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
