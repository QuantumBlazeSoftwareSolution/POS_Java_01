package com.qb.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class InvoiceItemController implements Initializable {

    @FXML
    private Label itemCode;
    @FXML
    private ImageView itemImage;
    @FXML
    private Label itemName;
    @FXML
    private Label itemPrice;
    @FXML
    private Label qty;
    @FXML
    private Label itemAmount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void InvoiceItemData(String itemCode, String itemImage, String itemName, double itemPrice, double quantity) {
        this.itemCode.setText(itemCode);
        this.itemName.setText(itemName);
        this.itemPrice.setText(String.format("Rs. %.2f", itemPrice));
        this.qty.setText(String.valueOf(quantity));
        this.itemAmount.setText(String.format("Rs. %.2f", itemPrice * quantity));
        calculateItemAmount();
        setItemImage(itemImage);
    }

    public void setItemImage(String imageUrl) {
        try {
            Image image = new Image(imageUrl);
            itemImage.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to load image: " + imageUrl);
        }
    }

    private void calculateItemAmount() {
        try {
            // Parse the item price and quantity from the labels
            double price = Double.parseDouble(itemPrice.getText().replace("Rs. ", ""));
            double quantity = Double.parseDouble(qty.getText());

            // Calculate the total amount
            double totalAmount = price * quantity;

            // Update the itemAmount label
            itemAmount.setText(String.format("Rs. %.2f", totalAmount));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Failed to calculate item amount: Invalid price or quantity format.");
        }
    }
}
