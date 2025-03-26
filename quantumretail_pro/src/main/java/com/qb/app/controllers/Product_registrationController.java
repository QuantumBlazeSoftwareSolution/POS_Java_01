package com.qb.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class Product_registrationController implements Initializable {

    // <editor-fold desc="FXML init component" defaultstate="collapsed">
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
    private ImageView productImage;
    @FXML
    private Button btnPicture;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnRegister;
//    </editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
