/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.popup;

import com.qb.app.controllers.admin.product.tables.ProductPopupModal;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.entity.Product;
import com.qb.app.model.getLogger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class PopUpProductListController implements Initializable {

    public static Object callingController;

    @FXML
    private AnchorPane root;
    @FXML
    private Group pageIcon;
    @FXML
    private Group closeIcon;
    @FXML
    private TextField tfSearch;
    @FXML
    private TableColumn<ProductPopupModal, Integer> colId;
    @FXML
    private TableColumn<ProductPopupModal, String> colProduct;
    @FXML
    private TableColumn<ProductPopupModal, Double> colSalePrice;
    @FXML
    private TableColumn<ProductPopupModal, Double> colCostPrice;
    @FXML
    private TableColumn<ProductPopupModal, String> colMeasure;
    @FXML
    private TableColumn<ProductPopupModal, Double> colDiscount;
    @FXML
    private TableColumn<ProductPopupModal, String> colBarcode;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTable();
    }

    @FXML
    private void closePopUp(MouseEvent event) {
        closeWindow();
    }

    private void configureTable() {
        colId.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getProduct().getId()).asObject());
        colProduct.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getProduct()));
        colSalePrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getProduct().getSalePrice()).asObject());
        colCostPrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getProduct().getCostPrice()).asObject());
        colMeasure.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getProduct().getMeasure())));
        colDiscount.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getProduct().getDiscount()).asObject());
        colBarcode.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getProduct().getProductStatusId().getStatus()));

//        if (callingController != null) {
//            try {
//                callingController
//                        .getClass()
//                        .getMethod("setParentID", Product.class)
//                        .invoke(callingController, null);
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                getLogger.logger().warning("Failed to pass product ID: " + ex.getMessage());
//            }
//        }
    }

    public void saveProductRegistrationController(Object controller) {
        this.callingController = controller;
    }

    public void closeWindow() {
        InterfaceAction.closeWindow(root);
    }

    @FXML
    private void handleSearchKeyPressed(KeyEvent event) {
    }

}
