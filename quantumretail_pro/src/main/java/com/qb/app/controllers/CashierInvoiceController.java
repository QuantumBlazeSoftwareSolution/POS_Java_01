package com.qb.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class CashierInvoiceController implements Initializable {

    @FXML
    private VBox invoiceItemContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (int i = 0; i < 10; i++) {
            try {
                // Load the invoiceItem.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlComponent/invoiceItem.fxml"));
                Node invoiceItem = loader.load(); // Load the node

                InvoiceItemController itemController = loader.getController();
                String itemCode = "ITEM #" + (i + 1); 
                String itemImage = getClass().getResource("/com/qb/app/assets/images/tomato.png").toExternalForm();
                String itemName = "Product " + (i + 1); 
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

}
