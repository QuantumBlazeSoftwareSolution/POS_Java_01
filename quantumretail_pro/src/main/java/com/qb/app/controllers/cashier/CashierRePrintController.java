/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;

import com.qb.app.model.ControllerClose;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class CashierRePrintController implements Initializable, ControllerClose {

    @FXML
    private ScrollPane rePrintInvoiceItemScrollContainer;
    @FXML
    private VBox rePrintInvoiceItemContainer;
    @FXML
    private ScrollBar rePrintInvoiceItemScroller;
    @FXML
    private ScrollPane rePrintInvoiceScrollContainer;
    @FXML
    private VBox rePrintItemContainer;
    @FXML
    private ScrollBar rePrintInvoiceScroller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void close() {

    }

}
