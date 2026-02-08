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
import javafx.concurrent.Task;
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
public class Stock_popupController implements Initializable {

    @FXML
    private TilePane productContainer;
    @FXML
    private Button btnClose;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField tfSalePrice;
    @FXML
    private TextField tfCostPrice;
    @FXML
    private DatePicker dpExpireDate;
    @FXML
    private TextField tfBarcode;
    @FXML
    private Button btnAction;

    private Product selectedProduct;
    public static Object callingController;
    @FXML
    private Label labelParentName;
    private Product parentProduct;
    @FXML
    private TextField tfDiscount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfSalePrice.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfCostPrice.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfDiscount.setTextFormatter(DefaultAPI.createNumericTextFormatter());
    }

    private void loadStocks() {
        System.out.println("Product Name: " + selectedProduct.getProduct());

        Task<List<Stock>> task = new Task<>() {
            @Override
            protected List<Stock> call() throws Exception {
                return StockCRUD.getStockItemsByProduct(selectedProduct);
            }
        };

        task.setOnSucceeded(event -> {
            List<Stock> stocks = task.getValue();

            productContainer.getChildren().clear();

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
        });

        task.setOnFailed(event -> {
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }

    public void saveController(Object object) {
        Stock_popupController.callingController = object;
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
                    .invoke(callingController, stock, selectedProduct);

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

    public void setProduct(Product product) {
        this.parentProduct = null;
        labelParentName.setText("");

        this.selectedProduct = product;
        ProductHasProductType productType = ProductHasProductTypeCRUD.getProductHasProductTypeByProduct(product);

        if (productType != null) {
            this.parentProduct = productType.getReferenceId();
            labelParentName.setText(productType.getReferenceId().getProduct());
        }

        loadStocks();
    }

}
