/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.popup;

import com.qb.app.controllers.table_models.SupplierListTable;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.SinhalaInputNormalizer;
import com.qb.app.model.entity.Company;
import com.qb.app.model.entity.Supplier;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class PopUpSupplierListController implements Initializable {

    @FXML
    private Group pageIcon;
    @FXML
    private Group closeIcon;
    @FXML
    private TableView<SupplierListTable> supplierListTable;
    @FXML
    private TableColumn<SupplierListTable, Integer> colSupplierId;
    @FXML
    private TableColumn<SupplierListTable, String> colSupplierName;
    @FXML
    private TableColumn<SupplierListTable, String> colSupplierCompanyName;
    @FXML
    private TableColumn<SupplierListTable, String> colSupplierStatus;
    @FXML
    private AnchorPane root;
    
    public static Object supplierListCallingController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configureTable();
        loadSuppliers();
        SinhalaInputNormalizer.applySinhalaFixRecursively(root);
    }

    private void configureTable() {

        colSupplierId.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getColId()).asObject()
        );

        colSupplierName.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getColSupplierName())
        );

        colSupplierCompanyName.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getColCompanyName())
        );

        colSupplierStatus.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getColStatus())
        );

        supplierListTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !supplierListTable.getSelectionModel().isEmpty()) {

                SupplierListTable row
                        = supplierListTable.getSelectionModel().getSelectedItem();

                Integer supplierId = row.getColId(); // 🔑 only ID

                if (supplierListCallingController != null) {
                    try {
                        supplierListCallingController
                                .getClass()
                                .getMethod("setSelectedSupplier", Integer.class)
                                .invoke(supplierListCallingController, supplierId);

                        closeWindow();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void loadSuppliers() {

        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxSize(40, 40);
        supplierListTable.setPlaceholder(progress);

        Task<List<SupplierListTable>> task = new Task<>() {
            @Override
            protected List<SupplierListTable> call() {

                return JPATransaction.runInTransaction(em -> {

                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<Supplier> cq = cb.createQuery(Supplier.class);
                    Root<Supplier> root = cq.from(Supplier.class);

                    cq.orderBy(cb.asc(root.get("name")));

                    List<Supplier> suppliers = em.createQuery(cq).getResultList();

                    return suppliers.stream()
                            .map(s -> new SupplierListTable(
                            s.getId(),
                            s.getName(),
                            s.getCompanyId().getName(),
                            s.getSupplierStatusId().getStatus()
                    ))
                            .toList();
                });
            }
        };

        task.setOnSucceeded(e -> {
            supplierListTable.getItems().setAll(task.getValue());

            if (task.getValue().isEmpty()) {
                supplierListTable.setPlaceholder(
                        new Label("No suppliers found")
                );
            }
        });

        task.setOnFailed(e -> {
            supplierListTable.setPlaceholder(
                    new Label("Failed to load suppliers")
            );
            task.getException().printStackTrace();
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
    
    
    public void saveSupplierListController(Object controller) {
        this.supplierListCallingController = controller;
    }

    public void closeWindow() {
        InterfaceAction.closeWindow(root);
    }


}
