/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.employee;

import com.qb.app.model.SinhalaInputNormalizer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Employee_managementController implements Initializable {

    @FXML
    private Group IconEmployeeManagementTopic;
    @FXML
    private AnchorPane root;
    @FXML
    private TableColumn<?, ?> colSupplierId;
    @FXML
    private TableColumn<?, ?> colSupplierName;
    @FXML
    private TableColumn<?, ?> colSupplierCompanyName;
    @FXML
    private TableColumn<?, ?> colSupplierStatus;
    @FXML
    private TableColumn<?, ?> colSupplierStatus1;
    @FXML
    private TableColumn<?, ?> colSupplierStatus2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
