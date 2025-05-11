package com.qb.app.controllers;

import com.qb.app.model.ControllerClose;
import com.qb.app.model.DefaultAPI;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CashierInvoiceController implements Initializable, ControllerClose {

    // <editor-fold desc="FXML init component" defaultstate="collapsed">
    @FXML
    private VBox invoiceItemContainer;
    @FXML
    private ScrollPane invoiceScrollContainer;
    @FXML
    private ImageView itemImage;
    @FXML
    private ScrollBar invoiceScroller;
    @FXML
    private TextField tfBarCode;
    @FXML
    private TextField tfItemCode;
    @FXML
    private Button btnProductView;
    @FXML
    private Label invoiceNumber;
    @FXML
    private Label labelItemName;
    @FXML
    private Label labelItemPrice;
    @FXML
    private Button btnDecreaseQty;
    // </editor-fold>
    @FXML
    private Button btnViewQty;
    @FXML
    private Button btnIncreaseQty;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnAdd;
    @FXML
    private Label invoiceItemCount;
    @FXML
    private Label invoiceSubTotal;
    @FXML
    private Label invoiceDiscount;
    @FXML
    private Label invoiceTotal;
    @FXML
    private Button btnPayment;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DefaultAPI.bindTableScroll(invoiceScroller, invoiceScrollContainer, invoiceItemContainer);
        
        List<String> itemList = new ArrayList<>();
        itemList.add("Tomato 1KG");
        itemList.add("Chocolate 100g");
        itemList.add("Chocolate 400g");
        itemList.add("Coca Cola 1L");
        itemList.add("Rice 5KG");
        itemList.add("Coconut 1 Unit");
        itemList.add("Milk Powder 400g");
        itemList.add("Tea Leaves 200g");
        itemList.add("Chilli Powder 250g Chilli Powder 250g Chilli Powder 250g");

        for (int i = 0; i < 20; i++) {
            try {
                Random random = new Random();

                // Load the invoiceItem.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlComponent/invoiceItem.fxml"));
                Node invoiceItem = loader.load(); // Load the node

                InvoiceItemController itemController = loader.getController();
                String itemCode = "ITEM #" + (i + 1);
                String itemImage = getClass().getResource("/com/qb/app/assets/images/tomato.png").toExternalForm();
                String itemName = itemList.get(random.nextInt(itemList.size()));
                double itemPrice = 100.00 * (i + 1);
                double quantity = i + 1;
                itemController.InvoiceItemData(itemCode, itemImage, itemName, itemPrice, quantity);

                // Add the invoice item to the VBox
                invoiceItemContainer.getChildren().add(invoiceItem);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
    }

}
