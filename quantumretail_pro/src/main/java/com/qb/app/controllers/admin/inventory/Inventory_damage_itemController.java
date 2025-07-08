/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.inventory;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Inventory_damage_itemController implements Initializable {


    @FXML
    private Group damageItemIcom;
    @FXML
    private ToggleGroup stock_location_toggl;
    @FXML
    private ToggleGroup damage_type_toggle;
    @FXML
    private ScrollPane damageItemTableScrollContainer;
    @FXML
    private VBox damageItemTableBody;
    @FXML
    private ScrollBar damageItemTableScroller;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
