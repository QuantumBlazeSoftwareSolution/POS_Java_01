package com.qb.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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

    public InvoiceItemController(Label itemCode, ImageView itemImage, Label itemName, Label itemPrice, Label qty, Label itemAmount) {
        this.itemCode = itemCode;
        this.itemImage = itemImage;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.qty = qty;
        this.itemAmount = itemAmount;
    }
}
