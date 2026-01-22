/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Stock_item_cardController implements Initializable {

    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label expLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    private String itemName;

    public void setData(String name, double price, String expireDate) {
        this.itemName = name;
        nameLabel.setText(name);
        priceLabel.setText("$" + price);
        expLabel.setText(expireDate);
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        System.out.println("Selected Name" + itemName);
    }

}
