/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.popup;

import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Product;
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class PopUpLocationListController implements Initializable {

    @FXML
    private Group pageIcon;
    @FXML
    private Group closeIcon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
