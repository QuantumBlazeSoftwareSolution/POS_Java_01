package com.qb.app.controllers;

import com.qb.app.controllers.table_models.ExpireTrackingTable;
import com.qb.app.database_crud.StockStatusCRUD;
import com.qb.app.model.ControllerClose;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.getLogger;
import com.qb.app.uiComponents.ExpireTrackingActionController;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class ExpireTrackingController implements Initializable, ControllerClose {

    @FXML
    private ComboBox<String> cbYear;
    @FXML
    private ComboBox<String> cbMonth;
    @FXML
    private TextField tfSearchField;
    @FXML
    private TableView<ExpireTrackingTable> table;
    @FXML
    private TableColumn<ExpireTrackingTable, String> colBatchId;
    @FXML
    private TableColumn<ExpireTrackingTable, String> colItemName;
    @FXML
    private TableColumn<ExpireTrackingTable, String> colQuantity;
    @FXML
    private TableColumn<ExpireTrackingTable, String> colExpireDate;
    @FXML
    private TableColumn<ExpireTrackingTable, String> colReceivedDate;
    @FXML
    private TableColumn<ExpireTrackingTable, String> colAction;

    private Map<String, Integer> months = new LinkedHashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fetchExpireItems();
        configureTable();
        initializeYearAndMonth();
        initializeFiltering();
    }

    private void initializeFiltering() {
        cbYear.setOnAction(e -> fetchExpireItems());
        cbMonth.setOnAction(e -> fetchExpireItems());
        tfSearchField.textProperty().addListener((obs, old, val) -> fetchExpireItems());
    }

    private void initializeYearAndMonth() {

        int currentYear = LocalDate.now().getYear();

        for (int i = 0; i < 2; i++) {
            cbYear.getItems().add(String.valueOf(currentYear - i));
        }

        cbYear.setValue(String.valueOf(currentYear));

        months.put("All", 0);
        months.put("January", 1);
        months.put("February", 2);
        months.put("March", 3);
        months.put("April", 4);
        months.put("May", 5);
        months.put("June", 6);
        months.put("July", 7);
        months.put("August", 8);
        months.put("September", 9);
        months.put("October", 10);
        months.put("November", 11);
        months.put("December", 12);

        cbMonth.getItems().addAll(months.keySet());

        cbMonth.setValue(
                months.entrySet()
                        .stream()
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElse("All")
        );
    }

    private void configureTable() {
        colBatchId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBatchId()));
        colItemName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemName()));
        colQuantity.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQuantity()));
        colExpireDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getExpireDate()));
        colReceivedDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReceiveDate()));
        colAction.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlPanel/ExpireTrackingAction.fxml"));
                        AnchorPane actionBox = loader.load();
                        ExpireTrackingActionController actionColumn = loader.getController();

                        ExpireTrackingTable rowItem = getTableView().getItems().get(getIndex());
                        actionColumn.dataInject(rowItem.getStoc());
                        actionColumn.setRefreshCallback(() -> fetchExpireItems());

                        setGraphic(actionBox);
                    } catch (IOException e) {
                        e.printStackTrace();
                        getLogger.logger().warning(e.toString());
                        setGraphic(null);
                    }
                }
            }
        });
    }

    private void fetchExpireItems() {

        ProgressIndicator loading = new ProgressIndicator();
        loading.setMaxSize(60, 60);
        table.setPlaceholder(loading);

        table.getItems().clear();
        Task<List<Stock>> expireTask = new Task() {
            @Override
            protected List<Stock> call() throws Exception {
                List<Stock> stockList = getExpiredStockList();
                return stockList;
            }
        };

        expireTask.setOnSucceeded(e -> {

            List<Stock> stockList = expireTask.getValue();
            table.getItems().clear();

            if (stockList == null || stockList.isEmpty()) {

                // 🔥 Empty State
                Label emptyLabel = new Label("No expired items found.");
                emptyLabel.setStyle("-fx-text-fill: gray; -fx-font-size: 14px;");
                table.setPlaceholder(emptyLabel);

                return;
            }

            List<ExpireTrackingTable> rowList = new ArrayList<>();

            for (Stock stock : stockList) {
                rowList.add(new ExpireTrackingTable(
                        stock.getBatchId().toString(),
                        stock.getProductId().getProduct(),
                        String.valueOf(stock.getQty()),
                        DefaultAPI.formatDateObject(stock.getExpireDate(), "dd MMM YYYY"),
                        DefaultAPI.formatDateObject(stock.getReceivedDate(), "dd MMM YYYY"),
                        stock
                ));
            }

            table.getItems().addAll(rowList);
        });

        expireTask.setOnFailed((t) -> {
            Button retryBtn = new Button("Retry");
            retryBtn.setOnAction(ev -> fetchExpireItems());

            VBox box = new VBox(new Label("Failed to load data"), retryBtn);
            box.setAlignment(Pos.CENTER);
            box.setSpacing(10);

            table.setPlaceholder(box);
        });

        new Thread(expireTask, "Expire-fetching-thread").start();
    }

    private List<Stock> getExpiredStockList() {

        return JPATransaction.runInTransaction((em) -> {

            LocalDate today = LocalDate.now();

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Stock> cq = cb.createQuery(Stock.class);
            Root<Stock> root = cq.from(Stock.class);

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.notEqual(root.get("qty"), 0));

            predicates.add(cb.notEqual(
                    root.get("stockStatusId"),
                    StockStatusCRUD.getStockStatus("inactive")
            ));

            predicates.add(cb.lessThan(root.get("expireDate"), today));

            if (cbYear.getValue() != null) {

                int year = Integer.parseInt(cbYear.getValue());
                predicates.add(
                        cb.equal(
                                cb.function("YEAR", Integer.class, root.get("expireDate")),
                                year
                        )
                );
            }

            if (cbMonth.getValue() != null) {

                int monthValue = months.get(cbMonth.getValue());

                if (monthValue != 0) {
                    predicates.add(
                            cb.equal(
                                    cb.function("MONTH", Integer.class, root.get("expireDate")),
                                    monthValue
                            )
                    );
                }
            }

            String search = tfSearchField.getText();
            if (search != null && !search.isEmpty()) {

                predicates.add(
                        cb.like(
                                cb.lower(root.get("productId").get("product")),
                                "%" + search.toLowerCase() + "%"
                        )
                );
            }

            cq.where(cb.and(predicates.toArray(Predicate[]::new)));

            return em.createQuery(cq).getResultList();
        });
    }

    @Override
    public void close() {
        
    }
}
