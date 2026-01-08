/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.popup;

import com.qb.app.controllers.table_models.CompanyListTable;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Company;
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class PopUpCompanyListController implements Initializable {

    @FXML
    private Group pageIcon;
    @FXML
    private Group closeIcon;
    @FXML
    private TableView<CompanyListTable> companyListTable;
    @FXML
    private TableColumn<CompanyListTable, Integer> companyId;
    @FXML
    private TableColumn<CompanyListTable, String> companyName;
    @FXML
    private TableColumn<CompanyListTable, String> companyMobile;

    public static Object companyListCallingController;

    private final ObservableList<CompanyListTable> companyList
            = FXCollections.observableArrayList();
    @FXML
    private AnchorPane root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configureTable();
        loadCompanies();
    }

    private void configureTable() {

        companyId.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getColId()).asObject()
        );

        companyName.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getColCompanyName())
        );

        companyMobile.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getColMobile())
        );

        companyListTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !companyListTable.getSelectionModel().isEmpty()) {

                CompanyListTable row
                        = companyListTable.getSelectionModel().getSelectedItem();

                Integer companyId = row.getColId(); // 🔑 only ID

                if (companyListCallingController != null) {
                    try {
                        companyListCallingController
                                .getClass()
                                .getMethod("setSelectedCompany", Integer.class)
                                .invoke(companyListCallingController, companyId);

                        closeWindow();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void loadCompanies() {

        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxSize(40, 40);
        companyListTable.setPlaceholder(progress);

        Task<List<CompanyListTable>> task = new Task<>() {
            @Override
            protected List<CompanyListTable> call() {

                return JPATransaction.runInTransaction(em -> {

                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<Company> cq = cb.createQuery(Company.class);
                    Root<Company> root = cq.from(Company.class);

                    cq.select(root);
                    cq.orderBy(cb.asc(root.get("name")));

                    List<Company> companies = em.createQuery(cq).getResultList();

                    return companies.stream()
                            .map(c -> new CompanyListTable(
                            c.getId(),
                            c.getName(),
                            c.getTelephone1()
                    ))
                            .toList();
                });
            }
        };

        task.setOnSucceeded(e -> {
            companyListTable.getItems().clear();
            companyListTable.getItems().addAll(task.getValue());

            if (task.getValue().isEmpty()) {
                companyListTable.setPlaceholder(
                        new Label("No companies found")
                );
            }
        });

        task.setOnFailed(e -> {
            companyListTable.setPlaceholder(
                    new Label("Failed to load companies")
            );
//            getLogger.logger().severe(
//                    "Company load failed", task.getException()
//            );
        });

        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    public void saveCompanyListController(Object controller) {
        this.companyListCallingController = controller;
    }

    public void closeWindow() {
        InterfaceAction.closeWindow(root);
    }

    @FXML
    private void closePopUp(MouseEvent event) {
        closeWindow();
    }

}
