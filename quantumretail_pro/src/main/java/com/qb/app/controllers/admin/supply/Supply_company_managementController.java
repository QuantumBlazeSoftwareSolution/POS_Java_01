/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.supply;

import com.qb.app.model.CustomAlert;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Company;
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Supply_company_managementController implements Initializable {

    @FXML
    private Group iconSupplyCompanyManagementTopic;
    @FXML
    private TextField rtfCompanyName;
    @FXML
    private TextField rtfCompanyAddress;
    @FXML
    private TextField rtfCompanyMobile01;
    @FXML
    private TextField rtfCompanyMobile02;
    @FXML
    private Button rbtnClear;
    @FXML
    private Button rbtnAddCompany;
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    @FXML
    private void addCompanyActionEvent(ActionEvent event) { 
       if (event.getSource() == rbtnAddCompany) {
            addCompany();
        } else if (event.getSource() == rbtnClear) {
            clearAddCompanyFields();
        }
    }
    
    private void addCompany() {

        if (IsCompanyValid()) {
            if (!isCompanyExist()) {

                JPATransaction.runInTransaction(em -> {
                    try {
                        //save a company
                        Company company = new Company();
                        company.setName(rtfCompanyName.getText());
                        company.setAddress(rtfCompanyAddress.getText());
                        company.setTelephone1(rtfCompanyMobile01.getText());
                        company.setTelephone2(rtfCompanyMobile02.getText());

                        em.persist(company);
                        clearAddCompanyFields();
                        CustomAlert.showStyledAlert(root, "Company added Successful", Alert.AlertType.CONFIRMATION);

                    } catch (Exception e) {
                        e.printStackTrace();
                        getLogger.logger().warning(e.toString());
                    }
                });

            }
        }

    }

    private boolean IsCompanyValid() {
        String companyName = rtfCompanyName.getText().trim();
        if (companyName.isEmpty()) {
            CustomAlert.showStyledAlert(root, "Company name is required.", Alert.AlertType.WARNING);
            rtfCompanyName.requestFocus();
            return false;
        }

        String telephone_1 = rtfCompanyMobile01.getText().trim();
        if (!telephone_1.isEmpty()) {
            if (!telephone_1.matches("\\d{10}")) {
                CustomAlert.showStyledAlert(root, "Telephone Number 01 must be exactly 10 digits.", Alert.AlertType.WARNING);
                rtfCompanyMobile01.requestFocus();
                return false;
            }
        }

        String telephone_2 = rtfCompanyMobile02.getText().trim();
        if (!telephone_2.isEmpty()) {
            if (!telephone_2.matches("\\d{10}")) {
                CustomAlert.showStyledAlert(root, "Telephone Number 02 must be exactly 10 digits.", Alert.AlertType.WARNING);
                rtfCompanyMobile02.requestFocus();
                return false;
            }
        }

        return true;
    }

    private boolean isCompanyExist() {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Company> cQuery = cBuilder.createQuery(Company.class);
            Root<Company> companyTable = cQuery.from(Company.class);

            Predicate companyPredicate1 = cBuilder.equal(
                    cBuilder.lower(companyTable.get("name")),
                    rtfCompanyName.getText().toLowerCase()
            );

            Predicate companyPredicate2 = cBuilder.equal(
                    cBuilder.lower(companyTable.get("address")),
                    rtfCompanyAddress.getText().toLowerCase()
            );

            // Use OR instead of AND
            cQuery.where(cBuilder.or(companyPredicate1, companyPredicate2));

            return !em.createQuery(cQuery).getResultList().isEmpty();
        });
    }

    private void clearAddCompanyFields() {
        rtfCompanyName.setText("");
        rtfCompanyAddress.setText("");
        rtfCompanyMobile01.setText("");
        rtfCompanyMobile02.setText("");
    }
    
    
    

    
    
}
