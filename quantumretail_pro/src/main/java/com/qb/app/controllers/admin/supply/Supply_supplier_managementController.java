/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.supply;

import com.qb.app.controllers.popup.PopUpSupplierListController;
import com.qb.app.model.ComboBoxUtils;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.PopUp;
import com.qb.app.model.SinhalaInputNormalizer;
import com.qb.app.model.entity.Company;
import com.qb.app.model.entity.Supplier;
import com.qb.app.model.entity.SupplyStatus;
import com.qb.app.model.getLogger;
import jakarta.persistence.NoResultException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Supply_supplier_managementController implements Initializable {

    @FXML
    private Group suppierManagementIcon;
    @FXML
    private TextField tfSupplierName;
    @FXML
    private TextField tfSupplierMobile;
    @FXML
    private ComboBox<Company> cbComapany;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnAddSupplier;
    @FXML
    private AnchorPane root;
    @FXML
    private TextField utfSupplierId;
    @FXML
    private TextField utfSupplierName;
    @FXML
    private ComboBox<Company> ucbSupplierCompany;
    @FXML
    private ComboBox<SupplyStatus> ucbSupplierStatus;
    @FXML
    private Button ubtnClear;
    @FXML
    private Button ubtnUpdateChanges;

    private Supplier loadedSupplier;
    private boolean isSupplierLoaded;
    @FXML
    private TextField utfSupplierMobile;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadSupplierCompanyComboBox();
    }

    private void loadSupplierCompanyComboBox() {
        ComboBoxUtils.loadComboBoxValues(cbComapany, Company.class, "name", Company::getName);
        ComboBoxUtils.loadComboBoxValues(ucbSupplierCompany, Company.class, "name", Company::getName);
        ComboBoxUtils.loadComboBoxValues(ucbSupplierStatus, SupplyStatus.class, "status", SupplyStatus::getStatus);
    }

    @FXML
    private void addSupplierActionEvent(ActionEvent event) {
        if (event.getSource() == btnAddSupplier) {
            addSupplier();
        } else if (event.getSource() == btnClear) {
            clearAddSupplierFields();
        }

    }

    private void addSupplier() {

        if (IsSupplierValid()) {
            if (!isSupplierExist()) {
                JPATransaction.runInTransaction(em -> {
                    try {
                        //save a Supplier
                        Supplier supplier = new Supplier();
                        supplier.setName(tfSupplierName.getText());
                        supplier.setTelephone(tfSupplierMobile.getText());
                        supplier.setCompanyId(cbComapany.getValue());
                        supplier.setSupplierStatusId(getSupplierStatus());

                        em.persist(supplier);

                        clearAddSupplierFields();
                        CustomAlert.showStyledAlert(root, "Supplier added Successful.", Alert.AlertType.CONFIRMATION);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } else {
                CustomAlert.showStyledAlert(root, "This supplier is already added", Alert.AlertType.WARNING);
            }

        }

    }

    private boolean IsSupplierValid() {
        if (tfSupplierName.getText().isEmpty()) {
            CustomAlert.showStyledAlert(root, "Supplier name is required.", Alert.AlertType.WARNING);
            tfSupplierName.requestFocus();
            return false;
        }

        String telephone_1 = tfSupplierMobile.getText();
        if (telephone_1 != null && !telephone_1.trim().isEmpty()) {
            if (telephone_1.length() != 10) {
                CustomAlert.showStyledAlert(root, "Telephone Number 01 must be exactly 10 digits.", Alert.AlertType.WARNING);
                tfSupplierMobile.requestFocus();
                return false;
            }
        }

        if (cbComapany.getValue() == null) {
            CustomAlert.showStyledAlert(root, "Please select a Company.", Alert.AlertType.WARNING);
            cbComapany.requestFocus();
            return false;
        }

        return true;
    }

    private boolean isSupplierExist() {
        return JPATransaction.runInTransaction((em) -> {
            CriteriaBuilder cBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Supplier> cQuery = cBuilder.createQuery(Supplier.class);
            Root<Supplier> supplierTable = cQuery.from(Supplier.class);

            Predicate supplierPredicate1 = cBuilder.equal(
                    cBuilder.lower(supplierTable.get("name")),
                    tfSupplierName.getText().toLowerCase()
            );

            Predicate supplierPredicate2 = cBuilder.equal(
                    cBuilder.lower(supplierTable.get("telephone")),
                    tfSupplierMobile.getText().toLowerCase()
            );

            // Use OR instead of AND
            cQuery.where(cBuilder.or(supplierPredicate1, supplierPredicate2));

            return !em.createQuery(cQuery).getResultList().isEmpty();
        });
    }

    private SupplyStatus getSupplierStatus() {
        return JPATransaction.runInTransaction((em) -> {
            try {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<SupplyStatus> cq = cb.createQuery(SupplyStatus.class);
                Root<SupplyStatus> root = cq.from(SupplyStatus.class);

                cq.where(cb.equal(root.get("status"), "Active"));

                return em.createQuery(cq).getSingleResult();

            } catch (NoResultException e) {
                // Handle case where no "Enable" status exists
                System.err.println("No SupplierStatus with status='Active' found");
                return null;
            }
        });
    }

    private void clearAddSupplierFields() {
        tfSupplierName.setText("");
        tfSupplierMobile.setText("");
        cbComapany.getSelectionModel().clearSelection();
        cbComapany.setPromptText("Select Company");
    }

    @FXML
    private void handlePopUpSupplierView(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (utfSupplierId.getText().isEmpty()) {
//                openPopUp();
                try {
                    PopUp.showPopupAndWait(
                            "popup/popUpSupplierList.fxml",
                            root,
                            this.root.getScene(),
                            PopUp.PopupType.CENTERED_80_WIDTH,
                            (PopUpSupplierListController controller) -> {
                                controller.saveSupplierListController(this);
                            }
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    getLogger.logger().warning(e.toString());
                }
            } else {
                loadSupplier();
            }
        }
    }

    public void setSelectedSupplier(Integer companyId) {

        Supplier supplier = JPATransaction.runInTransaction(em -> {
            return em.find(Supplier.class, companyId);
        });

        if (supplier != null) {
            this.loadedSupplier = supplier;
            isSupplierLoaded = true;
            utfSupplierId.setText(String.valueOf(supplier.getId()));
            utfSupplierName.setText(supplier.getName());
            utfSupplierMobile.setText(supplier.getTelephone());
            ucbSupplierCompany.setValue(supplier.getCompanyId());
            ucbSupplierStatus.setValue(supplier.getSupplierStatusId());
        }
    }

    private void loadSupplier() {
        JPATransaction.runInTransaction((em) -> {
            try {
                int SupplierId = Integer.parseInt(utfSupplierId.getText());
                Supplier supplier = em.find(Supplier.class, SupplierId);
                if (supplier != null) {
                    this.loadedSupplier = supplier;
                    isSupplierLoaded = true;
                    utfSupplierName.setText(supplier.getName());
                    utfSupplierMobile.setText(supplier.getTelephone());
                    ucbSupplierCompany.setValue(supplier.getCompanyId());
                    ucbSupplierStatus.setValue(supplier.getSupplierStatusId());

                } else {
                    CustomAlert.showStyledAlert(root, "Supplier not found.", Alert.AlertType.WARNING);
                }
            } catch (Exception e) {
                e.printStackTrace();
                getLogger.logger().warning(e.toString());
            }
        });
    }

    @FXML
    private void updateSupplierActionEvent(ActionEvent event) {

        if (event.getSource() == ubtnUpdateChanges) {
            UpdateSupplier();
        } else if (event.getSource() == ubtnClear) {
            clearUpdateSupplierFields();
        }

    }

    private void UpdateSupplier() {

        if (UpdateSupplierValid()) {
            if (isSupplierLoaded) {
                JPATransaction.runInTransaction((em) -> {
                    try {

                        Supplier supplier = new Supplier();
                        loadedSupplier.setName(utfSupplierName.getText());
                        loadedSupplier.setTelephone(utfSupplierMobile.getText());
                        loadedSupplier.setCompanyId(ucbSupplierCompany.getValue());
                        loadedSupplier.setSupplierStatusId(ucbSupplierStatus.getValue());

                        em.merge(loadedSupplier);
                        clearUpdateSupplierFields();

                        CustomAlert.showStyledAlert(root, "Supplier successfully Updated", Alert.AlertType.CONFIRMATION);

                    } catch (Exception e) {
                        e.printStackTrace();
                        getLogger.logger().warning(e.toString());
                    }

                });
            }

        }

    }

    private boolean UpdateSupplierValid() {
        if (utfSupplierId.getText().isEmpty()) {
            CustomAlert.showStyledAlert(root, "Supplier ID is required.", Alert.AlertType.WARNING);
            utfSupplierId.requestFocus();
            return false;
        }

        if (utfSupplierName.getText().isEmpty()) {
            CustomAlert.showStyledAlert(root, "Supplier name is required.", Alert.AlertType.WARNING);
            utfSupplierName.requestFocus();
            return false;
        }

        String telephone_1 = utfSupplierMobile.getText();
        if (telephone_1 != null && !telephone_1.trim().isEmpty()) {
            if (telephone_1.length() != 10) {
                CustomAlert.showStyledAlert(root, "Telephone Number 01 must be exactly 10 digits.", Alert.AlertType.WARNING);
                utfSupplierMobile.requestFocus();
                return false;
            }
        }
        if (ucbSupplierCompany.getValue() == null) {
//            displayRegistrationMessage("Please select a Company.", false);
            CustomAlert.showStyledAlert(root, "Please select a Company.", Alert.AlertType.WARNING);
            ucbSupplierCompany.requestFocus();
            return false;
        }

        return true;
    }

    private void clearUpdateSupplierFields() {
        utfSupplierId.setText("");
        utfSupplierName.setText("");
        utfSupplierMobile.setText("");
        ucbSupplierCompany.getSelectionModel().clearSelection();
        ucbSupplierStatus.getSelectionModel().clearSelection();

        isSupplierLoaded = false;
        loadedSupplier = null;

    }

}
