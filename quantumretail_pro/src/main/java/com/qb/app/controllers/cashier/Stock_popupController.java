package com.qb.app.controllers.cashier;

import com.qb.app.database_crud.ProductCRUD;
import com.qb.app.database_crud.StockCRUD;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.entity.Product;
import com.qb.app.model.entity.Stock;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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

        List<Stock> stocks = StockCRUD.getStockItemsByProduct(selectedProduct);

        try {
            for (Stock stock : stocks) {
                // 2. Load the FXML for the individual card
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/qb/app/cashier/stock_item_card.fxml")); // Adjust path to match your package!

                VBox cardBox = fxmlLoader.load();

                // 3. Get the controller of the card and set data
                Stock_item_cardController cardController = fxmlLoader.getController();
                
                
                
                cardController.setData(stock.getProductId().getProduct(), stock.getSalePrice(), stock.getExpireDate());

                // 4. Add the card to the TilePane (Layout handles itself automatically)
                productContainer.getChildren().add(cardBox);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveController(Object object) {
        this.callingController = object;
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnClose) {
            closeWindow();
        }
    }

    public void closeWindow() {
        InterfaceAction.closeWindow(root);
    }

    public void setProduct(Product product) {
        this.selectedProduct = product;
    }

}
