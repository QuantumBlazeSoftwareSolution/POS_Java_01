/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.product;

import com.jfoenix.controls.JFXToggleButton;
import com.qb.app.controllers.table_models.BrandListTable;
import com.qb.app.controllers.table_models.CategoryListTable;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Brand;
import com.qb.app.model.entity.Category;
import com.qb.app.model.entity.ProductStatus;
import com.qb.app.model.getLogger;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Product_brand_managementController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Group iconPage;
    @FXML
    private TextField tfBrandName;
    @FXML
    private JFXToggleButton toggleBrand;
    @FXML
    private Button btnClearBrand;
    @FXML
    private Button btnActionBrand;
    @FXML
    private TableView<BrandListTable> tvBrand;
    @FXML
    private TableColumn<BrandListTable, Integer> colBrandID;
    @FXML
    private TableColumn<BrandListTable, String> colBrandName;
    @FXML
    private TableColumn<BrandListTable, String> colBrandStatus;
    @FXML
    private TextField tfCategoryName;
    @FXML
    private JFXToggleButton toggleCategory;
    @FXML
    private Button btnClearCategory;
    @FXML
    private Button btnActionCategory;
    @FXML
    private TableView<CategoryListTable> tvCategory;

    @FXML
    private TableColumn<CategoryListTable, Integer> colCategoryID;

    @FXML
    private TableColumn<CategoryListTable, String> colCategoryName;

    @FXML
    private TableColumn<CategoryListTable, String> colCategoryStatus;

    private BrandListTable selectedBrand;

    private boolean isBrandLoaded = false;
    private Brand loadedBrand;

    private boolean isCategoryLoaded = false;
    private Category loadedCategory;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        configureTable();
        loadBrands();
        configureCategoryTable();
        loadCategories();
    }

    private void configureTable() {

        colBrandID.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getColId()).asObject()
        );

        colBrandName.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getColBrandName())
        );

        colBrandStatus.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getColstatus())
        );

        tvBrand.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !tvBrand.getSelectionModel().isEmpty()) {

                BrandListTable row = tvBrand.getSelectionModel().getSelectedItem();

                JPATransaction.runInTransaction(em -> {
                    loadedBrand = em.find(Brand.class, row.getColId());
                });

                tfBrandName.setText(row.getColBrandName());
                toggleBrand.setSelected("Active".equalsIgnoreCase(row.getColstatus()));

                isBrandLoaded = true;
                btnActionBrand.setText("Update Brand");
            }
        });
    }

    private void configureCategoryTable() {

        colCategoryID.setCellValueFactory(
                data -> new SimpleIntegerProperty(data.getValue().getColId()).asObject()
        );

        colCategoryName.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getColCategoryName())
        );

        colCategoryStatus.setCellValueFactory(
                data -> new SimpleStringProperty(data.getValue().getColstatus())
        );

        tvCategory.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1 && !tvCategory.getSelectionModel().isEmpty()) {

                CategoryListTable row = tvCategory.getSelectionModel().getSelectedItem();

                JPATransaction.runInTransaction(em -> {
                    loadedCategory = em.find(Category.class, row.getColId());
                });

                tfCategoryName.setText(row.getColCategoryName());
                toggleCategory.setSelected("Active".equalsIgnoreCase(row.getColstatus()));

                isCategoryLoaded = true;
                btnActionCategory.setText("Update Category");
            }
        });
    }

    private void loadBrands() {

        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxSize(40, 40);
        tvBrand.setPlaceholder(progress);

        Task<List<BrandListTable>> task = new Task<>() {
            @Override
            protected List<BrandListTable> call() {

                return JPATransaction.runInTransaction(em -> {

                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<Brand> cq = cb.createQuery(Brand.class);
                    Root<Brand> root = cq.from(Brand.class);

                    cq.select(root);
                    cq.orderBy(cb.asc(root.get("brand")));

                    List<Brand> brands = em.createQuery(cq).getResultList();

                    return brands.stream()
                            .map(b -> new BrandListTable(
                            b.getId(),
                            b.getBrand(),
                            b.getProductStatusId().getStatus()
                    ))
                            .toList();
                });
            }
        };

        task.setOnSucceeded(e -> {
            tvBrand.getItems().setAll(task.getValue());
            tvBrand.setPlaceholder(new javafx.scene.control.Label("No brands found"));
        });

        task.setOnFailed(e -> {
            tvBrand.setPlaceholder(new javafx.scene.control.Label("Failed to load brands"));
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }

    private void loadCategories() {

        ProgressIndicator progress = new ProgressIndicator();
        progress.setMaxSize(40, 40);
        tvCategory.setPlaceholder(progress);

        Task<List<CategoryListTable>> task = new Task<>() {
            @Override
            protected List<CategoryListTable> call() {

                return JPATransaction.runInTransaction(em -> {

                    CriteriaBuilder cb = em.getCriteriaBuilder();
                    CriteriaQuery<Category> cq = cb.createQuery(Category.class);
                    Root<Category> root = cq.from(Category.class);

                    cq.select(root);
                    cq.orderBy(cb.asc(root.get("category")));

                    List<Category> categories = em.createQuery(cq).getResultList();

                    return categories.stream()
                            .map(c -> new CategoryListTable(
                            c.getId(),
                            c.getCategory(),
                            c.getProductStatusId().getStatus()
                    ))
                            .toList();
                });
            }
        };

        task.setOnSucceeded(e -> {
            tvCategory.getItems().setAll(task.getValue());
            tvCategory.setPlaceholder(new javafx.scene.control.Label("No categories found"));
        });

        task.setOnFailed(e -> {
            tvCategory.setPlaceholder(new javafx.scene.control.Label("Failed to load categories"));
            task.getException().printStackTrace();
        });

        new Thread(task).start();
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {

        if (event.getSource() == btnActionBrand) {
            if ("Update Brand".equals(btnActionBrand.getText())) {
                updateBrand();
            } else {
                registerBrand();
            }
        } else if (event.getSource() == btnClearBrand) {
            clearBrandFields();
        } else if (event.getSource() == btnActionCategory) {
            if ("Update Category".equals(btnActionCategory.getText())) {
                updateCategory();
            } else {
                registerCategory();
            }
        } else if (event.getSource() == btnClearCategory) {
            tfCategoryName.clear();
        }

    }

    private void registerBrand() {

        String brandName = tfBrandName.getText().trim();

        if (brandName.isEmpty()) {
            // you can replace with CustomAlert if you want
            showWarning("Brand name required");
            tfBrandName.requestFocus();
            return;
        }

        if (isBrandExist()) {
            showWarning("Brand already exists");
            return;
        }

        JPATransaction.runInTransaction(em -> {

            ProductStatus activeStatus = em.createQuery(
                    "SELECT ps FROM ProductStatus ps WHERE LOWER(ps.status) = :status",
                    ProductStatus.class
            )
                    .setParameter("status", "active")
                    .getResultStream()
                    .findFirst()
                    .orElseThrow(()
                            -> new RuntimeException("Default ProductStatus 'Active' not found!")
                    );

            // 🔹 Create brand
            Brand brand = new Brand();
            brand.setBrand(brandName);
            brand.setProductStatusId(activeStatus);

            em.persist(brand);

        });

        clearBrandFields();
        loadBrands();
        CustomAlert.showStyledAlert(
                root,
                "Brand added successfully",
                Alert.AlertType.CONFIRMATION
        );

    }

    private boolean isBrandExist() {
        return JPATransaction.runInTransaction(em -> {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Brand> cq = cb.createQuery(Brand.class);
            Root<Brand> root = cq.from(Brand.class);

            Predicate brandNamePredicate = cb.equal(
                    cb.lower(root.get("brand")),
                    tfBrandName.getText().trim().toLowerCase()
            );

            cq.where(brandNamePredicate);

            return !em.createQuery(cq).getResultList().isEmpty();
        });
    }

    private void updateBrand() {

        if (updateBrandValid()) {

            if (isBrandLoaded) {

                JPATransaction.runInTransaction(em -> {
                    try {

                        loadedBrand.setBrand(tfBrandName.getText().trim());

                        String statusText = toggleBrand.isSelected() ? "active" : "inactive";

                        ProductStatus status = em.createQuery(
                                "SELECT ps FROM ProductStatus ps WHERE LOWER(ps.status) = :status",
                                ProductStatus.class
                        )
                                .setParameter("status", statusText)
                                .getResultStream()
                                .findFirst()
                                .orElseThrow(()
                                        -> new RuntimeException("Product status not found: " + statusText)
                                );

                        loadedBrand.setProductStatusId(status);

                        em.merge(loadedBrand);

                        loadBrands();
                        clearBrandFields();

                        CustomAlert.showStyledAlert(
                                root,
                                "Brand successfully updated",
                                Alert.AlertType.CONFIRMATION
                        );

                    } catch (Exception e) {
                        e.printStackTrace();
                        getLogger.logger().warning(e.toString());
                    }
                });
            }
        }
    }

    private boolean updateBrandValid() {

        String brandName = tfBrandName.getText().trim();

        if (!isBrandLoaded || loadedBrand == null) {
            showWarning("Please select a brand to update");
            return false;
        }

        if (brandName.isEmpty()) {
            showWarning("Brand name cannot be empty");
            tfBrandName.requestFocus();
            return false;
        }

        boolean exists = JPATransaction.runInTransaction(em -> {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Brand> root = cq.from(Brand.class);

            cq.select(cb.count(root));

            Predicate sameName = cb.equal(
                    cb.lower(root.get("brand")),
                    brandName.toLowerCase()
            );

            Predicate notSameId = cb.notEqual(
                    root.get("id"),
                    loadedBrand.getId()
            );

            cq.where(cb.and(sameName, notSameId));

            return em.createQuery(cq).getSingleResult() > 0;
        });

        if (exists) {
            showWarning("Another brand with this name already exists");
            return false;
        }

        return true;
    }

    private void clearBrandFields() {
        tfBrandName.clear();
        toggleBrand.setSelected(true);
        isBrandLoaded = false;
        loadedBrand = null;
        btnActionBrand.setText("Add Brand");
    }

    private void registerCategory() {

        String categoryName = tfCategoryName.getText().trim();

        if (categoryName.isEmpty()) {
            
            showWarning("Category name required");
            tfCategoryName.requestFocus();
            return;
        }

        if (isBrandExist()) {
            showWarning("Category already exists");
            return;
        }

        JPATransaction.runInTransaction(em -> {

            
            ProductStatus activeStatus = em.createQuery(
                    "SELECT ps FROM ProductStatus ps WHERE LOWER(ps.status) = :status",
                    ProductStatus.class
            )
                    .setParameter("status", "active")
                    .getResultStream()
                    .findFirst()
                    .orElseThrow(()
                            -> new RuntimeException("Default ProductStatus 'Active' not found!")
                    );

           
            Category category = new Category();
            category.setCategory(categoryName);
            category.setProductStatusId(activeStatus);

            em.persist(category);
        });

        clearCategoryFields();
        loadCategories();
        CustomAlert.showStyledAlert(
                root,
                "Category added successfully",
                Alert.AlertType.CONFIRMATION
        );

    }

    private boolean isCategoryExist() {
        return JPATransaction.runInTransaction(em -> {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Category> cq = cb.createQuery(Category.class);
            Root<Category> root = cq.from(Category.class);

            Predicate categoryNamePredicate = cb.equal(
                    cb.lower(root.get("category")),
                    tfCategoryName.getText().trim().toLowerCase()
            );

            cq.where(categoryNamePredicate);

            return !em.createQuery(cq).getResultList().isEmpty();
        });
    }

    private void updateCategory() {

        if (updateCategoryValid()) {

            JPATransaction.runInTransaction(em -> {
                try {

                    loadedCategory.setCategory(tfCategoryName.getText().trim());

                    String statusText = toggleCategory.isSelected() ? "active" : "inactive";

                    ProductStatus status = em.createQuery(
                            "SELECT ps FROM ProductStatus ps WHERE LOWER(ps.status) = :status",
                            ProductStatus.class
                    )
                            .setParameter("status", statusText)
                            .getResultStream()
                            .findFirst()
                            .orElseThrow(()
                                    -> new RuntimeException("Product status not found: " + statusText)
                            );

                    loadedCategory.setProductStatusId(status);

                    em.merge(loadedCategory);

                    loadCategories();
                    clearCategoryFields();

                    CustomAlert.showStyledAlert(
                            root,
                            "Category successfully updated",
                            Alert.AlertType.CONFIRMATION
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                    getLogger.logger().warning(e.toString());
                }
            });
        }
    }

    private boolean updateCategoryValid() {

        String categoryName = tfCategoryName.getText().trim();

        if (!isCategoryLoaded || loadedCategory == null) {
            showWarning("Please select a category to update");
            return false;
        }

        if (categoryName.isEmpty()) {
            showWarning("Category name cannot be empty");
            tfCategoryName.requestFocus();
            return false;
        }

        boolean exists = JPATransaction.runInTransaction(em -> {

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Category> root = cq.from(Category.class);

            cq.select(cb.count(root));

            Predicate sameName = cb.equal(
                    cb.lower(root.get("category")),
                    categoryName.toLowerCase()
            );

            Predicate notSameId = cb.notEqual(
                    root.get("id"),
                    loadedCategory.getId()
            );

            cq.where(cb.and(sameName, notSameId));

            return em.createQuery(cq).getSingleResult() > 0;
        });

        if (exists) {
            showWarning("Another category with this name already exists");
            return false;
        }

        return true;
    }

    private void clearCategoryFields() {
        tfCategoryName.clear();
        toggleCategory.setSelected(true);
        isCategoryLoaded = false;
        loadedCategory = null;
        btnActionCategory.setText("Add Category");
    }

    private void showWarning(String message) {
        CustomAlert.showStyledAlert(
                root,
                message,
                "Validation Error",
                Alert.AlertType.WARNING
        );
    }

}
