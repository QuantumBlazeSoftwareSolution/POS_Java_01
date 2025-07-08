/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.cashier;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class CashierSessionController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Label sessionHours;
    @FXML
    private Label sessionMinutes;
    @FXML
    private Label sessionAMPM;
    @FXML
    private Button signInMessage;
    @FXML
    private TextField tfSignInUsername;
    @FXML
    private PasswordField tfSignInPassword;
    @FXML
    private TextField tfSignInPettyCash;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button signOffMessage;
    @FXML
    private TextField tfSignOffUsername;
    @FXML
    private PasswordField tfSignOffPassword;
    @FXML
    private TextField tfSignOffCollection;
    @FXML
    private Button btnSignOff;

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
