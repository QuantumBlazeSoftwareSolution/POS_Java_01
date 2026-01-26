/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.supply;

import com.qb.app.controllers.popup.PopUpCompanyListController;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.PopUp;
import com.qb.app.model.SinhalaInputNormalizer;
import com.qb.app.model.entity.Company;
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    @FXML
    private TextField utfCompanyId;
    @FXML
    private TextField utfCompanyName;
    @FXML
    private TextField utfCompanyAddress;
    @FXML
    private TextField utfCompanyMobile01;
    @FXML
    private TextField utfCompanyMobile02;
    @FXML
    private Button ubtnclear;
    @FXML
    private Button ubtnUpdateCompany;

    private Company loadedCompany;
    private boolean isCompanyLoaded;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SinhalaInputNormalizer.applySinhalaFixRecursively(root);
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

    @FXML
    private void handlePopUpCompanyView(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            if (utfCompanyId.getText().isEmpty()) {

                try {
                    PopUp.showPopupAndWait(
                            "popup/popUpCompanyList.fxml",
                            root,
                            this.root.getScene(),
                            PopUp.PopupType.CENTERED_80_WIDTH,
                            (PopUpCompanyListController controller) -> {
                                controller.saveCompanyListController(this);
                            }
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    getLogger.logger().warning(e.toString());
                }

            } else {
                boolean isValid = isValidCompanyID();

                if (isValid) {
                    System.out.println("valid id");
                    loadCompany();
                } else {
                    System.out.println("invalid id");
                }

            }

        } else {

        }
    }

    public void setSelectedCompany(Integer companyId) {

        Company company = JPATransaction.runInTransaction(em -> {
            return em.find(Company.class, companyId);
        });

        if (company != null) {
            this.loadedCompany = company;      // ✅ IMPORTANT
            this.isCompanyLoaded = true;

            utfCompanyId.setText(String.valueOf(company.getId()));
            utfCompanyName.setText(company.getName());
            utfCompanyAddress.setText(company.getAddress());
            utfCompanyMobile01.setText(company.getTelephone1());
            utfCompanyMobile02.setText(company.getTelephone2());
        }
    }

    private boolean isValidCompanyID() {
        return JPATransaction.runInTransaction((em) -> {

            CriteriaBuilder cBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Company> cQuery = cBuilder.createQuery(Company.class);
            Root<Company> companyTable = cQuery.from(Company.class);

            Predicate predicate2 = cBuilder.equal(companyTable.get("id"), Integer.parseInt(utfCompanyId.getText()));
            cQuery.where(predicate2);

            return !em.createQuery(cQuery).getResultList().isEmpty();
        });
    }

    private void loadCompany() {
        JPATransaction.runInTransaction((em) -> {
            try {
                int CompanyId = Integer.parseInt(utfCompanyId.getText());
                Company company = em.find(Company.class, CompanyId);
                if (company != null) {
                    this.loadedCompany = company;
                    isCompanyLoaded = true;
                    utfCompanyName.setText(company.getName());
                    utfCompanyAddress.setText(company.getAddress());
                    utfCompanyMobile01.setText(String.valueOf(company.getTelephone1()));
                    utfCompanyMobile02.setText(String.valueOf(company.getTelephone2()));

                } else {
                    CustomAlert.showStyledAlert(root, "Company not found", Alert.AlertType.WARNING);
                }
            } catch (Exception e) {
                e.printStackTrace();
                getLogger.logger().warning(e.toString());
            }
        });
    }

    @FXML
    private void updateCompanyActionEvent(ActionEvent event) {
        if (event.getSource() == ubtnUpdateCompany) {
            updateCompany();
        } else if (event.getSource() == ubtnclear) {

            clearUpdateCompanyFields();
        }
    }

    private void updateCompany() {

        if (IsValidCompanyDetails()) {
            if (isCompanyLoaded) {
                JPATransaction.runInTransaction((em) -> {
                    try {

                        Company company = new Company();
                        loadedCompany.setName(utfCompanyName.getText());
                        loadedCompany.setAddress(utfCompanyAddress.getText());
                        loadedCompany.setTelephone1(utfCompanyMobile01.getText());
                        loadedCompany.setTelephone2(utfCompanyMobile02.getText());

                        em.merge(loadedCompany);
                        clearUpdateCompanyFields();

                        CustomAlert.showStyledAlert(root, "Company successfully Updated", Alert.AlertType.CONFIRMATION);

                    } catch (Exception e) {
                        e.printStackTrace();
                        getLogger.logger().warning(e.toString());
                    }
                });
            }
        }
    }

    private boolean IsValidCompanyDetails() {
        if (utfCompanyId.getText().isEmpty()) {
            CustomAlert.showStyledAlert(root, "Company ID is required.", Alert.AlertType.WARNING);
            utfCompanyId.requestFocus();
            return false;
        }

        if (utfCompanyName.getText().trim().isEmpty()) {
            CustomAlert.showStyledAlert(root, "Company Name is required.", Alert.AlertType.WARNING);
            utfCompanyName.requestFocus();
            return false;
        }

        String telephone_1 = utfCompanyMobile01.getText();
        if (telephone_1 != null && !telephone_1.trim().isEmpty()) {
            if (telephone_1.length() != 10) {
                CustomAlert.showStyledAlert(root, "Telephone Number 01 must be exactly 10 digits.", Alert.AlertType.WARNING);
                utfCompanyMobile01.requestFocus();
                return false;
            }
        }

        String telephone_2 = utfCompanyMobile02.getText();
        if (telephone_2 != null && !telephone_2.trim().isEmpty()) {
            if (telephone_2.length() != 10) {
                CustomAlert.showStyledAlert(root, "Telephone Number 02 must be exactly 10 digits.", Alert.AlertType.WARNING);
                utfCompanyMobile02.requestFocus();
                return false;
            }
        }
        return true;
    }

    private void clearUpdateCompanyFields() {
        utfCompanyId.setText("");
        utfCompanyName.setText("");
        utfCompanyAddress.setText("");
        utfCompanyMobile01.setText("");
        utfCompanyMobile02.setText("");

        isCompanyLoaded = false;
        loadedCompany = null;
    }

}
