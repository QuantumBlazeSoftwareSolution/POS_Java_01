/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.product;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Product_managementController implements Initializable {


    @FXML
    private Group iconPage;
    @FXML
    private TextField tfItemName;
    @FXML
    private TextField tfBarCode;
    @FXML
    private ComboBox<?> cbBrand;
    @FXML
    private TextField tfSalePrice;
    @FXML
    private TextField tfCostPrice;
    @FXML
    private TextField tfDiscount;
    @FXML
    private ComboBox<?> cbUnit;
    @FXML
    private TextField tfMeasure;
    @FXML
    private ComboBox<?> cbType;
    @FXML
    private TextField tfParentID;
    @FXML
    private Button btnPicture;
    @FXML
    private ImageView productImage;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnRegister;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleFileChooser(ActionEvent event) {
    }

}
