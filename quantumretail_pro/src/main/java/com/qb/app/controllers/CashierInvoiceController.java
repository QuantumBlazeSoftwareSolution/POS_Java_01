package com.qb.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CashierInvoiceController implements Initializable {

    @FXML
    private VBox invoiceItemContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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

        for (int i = 0; i < 10; i++) {
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
                System.out.println("Component Added Successffully");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Component Error!!!");
            }
        }
    }

}
