/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.product;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Product_analyticsController implements Initializable {

    @FXML
    private Group iconPage;
    @FXML
    private ComboBox<?> cbBrand1;
    @FXML
    private ComboBox<?> cbBrand;
    @FXML
    private ScrollPane tableScrollContainer;
    @FXML
    private VBox tableBody;
    @FXML
    private ScrollBar tableScroller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
