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
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Product_brand_managementController implements Initializable {


    @FXML
    private AnchorPane root;
    @FXML
    private Group iconPage;
    @FXML
    private TextField tfPrimaryBrandName;
    @FXML
    private Button btnPrimaryClear;
    @FXML
    private Button btnPrimaryRegister;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void handleActionEvent(ActionEvent event) {
    }

}
