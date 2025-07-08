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
public class Inventory_stock_adjustmentController implements Initializable {

    @FXML
    private Group StockAdjustmentIcon;
    @FXML
    private ToggleGroup stock_location_toggle;
    @FXML
    private ScrollPane stockAdjustmentTableScrollContainer;
    @FXML
    private VBox stockAdjustmentTableBody;
    @FXML
    private ScrollBar stockAdjustmentTableScroller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
