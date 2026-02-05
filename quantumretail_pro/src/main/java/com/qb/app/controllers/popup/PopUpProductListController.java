/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.popup;

import com.qb.app.controllers.admin.product.tables.ProductPopupModal;
import com.qb.app.database_crud.ProductCRUD;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.SinhalaInputNormalizer;
import com.qb.app.model.entity.Product;
import com.qb.app.model.getLogger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
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
    private TableView<ProductPopupModal> table;
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

    private boolean searchFullProducts = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTable();
        loadAllProducts(null);
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

        table.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !table.getSelectionModel().isEmpty()) {
                Product selectedProduct = table.getSelectionModel().getSelectedItem().getProduct();
                System.out.println(selectedProduct.getProduct());
                if (callingController != null) {
                    try {
                        callingController
                                .getClass()
                                .getMethod("setParentProduct", Product.class)
                                .invoke(callingController, selectedProduct);

                        closeWindow();

                    } catch (IllegalAccessException | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
                        ex.printStackTrace();
                        getLogger.logger().log(Level.WARNING, "Failed to pass product ID: {0}", ex.getMessage());
                    }
                }

            }
        });
    }

    public void saveController(Object controller) {
        this.callingController = controller;
    }

    public void closeWindow() {
        InterfaceAction.closeWindow(root);
    }

    public void changeSearchArea(boolean isParentProducts) {
        searchFullProducts = !isParentProducts;
    }

    @FXML
    private void handleSearchKeyPressed(KeyEvent event) {
        loadAllProducts(tfSearch.getText());
    }

    private void loadAllProducts(String searchTerm) {

        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxSize(40, 40);
        table.setPlaceholder(progress);

        Task<List<ProductPopupModal>> task = new Task<>() {
            @Override
            protected List<ProductPopupModal> call() {

                return JPATransaction.runInTransaction(em -> {

                    List<Product> products;
                    if (searchFullProducts) {
                        System.out.println("Search Full Products");
                        products = getAllProduct(em, searchTerm);
                    } else {
                        System.out.println("Search Parent Products");
                        products = ProductCRUD.getParentProducts(em, searchTerm);
                    }

                    return products.stream()
                            .map(p -> new ProductPopupModal(
                            p.getId(),
                            p.getProduct(),
                            p.getSalePrice(),
                            p.getCostPrice(),
                            String.valueOf(p.getMeasure()),
                            p.getDiscount(),
                            "",
                            p
                    ))
                            .toList();
                });
            }
        };

        task.setOnSucceeded(e -> {
            table.getItems().setAll(task.getValue());

            if (task.getValue().isEmpty()) {
                table.setPlaceholder(new Label("No products found"));
            }
        });

        task.setOnFailed(e
                -> getLogger.logger().warning("Product load failed : " + task.getException())
        );

        new Thread(task).start();
    }

    private List<Product> getAllProduct(EntityManager em, String searchTerm) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        if (searchTerm != null && !searchTerm.isBlank()) {
            String pattern = "%" + searchTerm.toLowerCase() + "%";
            cq.where(cb.like(cb.lower(root.get("product")), pattern));
        }

        cq.orderBy(cb.asc(root.get("product")));

        List<Product> products = em.createQuery(cq).getResultList();

        return products;
    }

}
