package com.qb.app.controllers.cashier;

import com.qb.app.database_crud.StockCRUD;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.entity.Product;
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
import javafx.scene.control.Button;
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

    public static Object callingController;
    private Product selectedProduct;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
            closeWindow();
        }
    }

    public void setStock(Stock stock) {
        try {
            callingController
                    .getClass()
                    .getMethod("setSelectedStock", Stock.class)
                    .invoke(callingController, stock);

            closeWindow();
        } catch (Exception e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    public void closeWindow() {
        InterfaceAction.closeWindow(root);
    }

    public void setProduct(Product product) {
        this.selectedProduct = product;
        loadStocks();
    }

}
