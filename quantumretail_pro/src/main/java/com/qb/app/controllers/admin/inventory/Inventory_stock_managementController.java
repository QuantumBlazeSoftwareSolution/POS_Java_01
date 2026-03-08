/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.inventory;

import com.qb.app.controllers.table_models.StockManagementTable;
import com.qb.app.database_crud.StockCRUD;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.getLogger;
import com.qb.app.uiComponents.ExpireTrackingActionController;
import com.qb.app.uiComponents.StockManagementActionController;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.DoubleStringConverter;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Inventory_stock_managementController implements Initializable {

    @FXML
    private TextField tfItemName;
    @FXML
    private TextField tfBarCode;
    @FXML
    private TableView<StockManagementTable> table;
    @FXML
    private TableColumn<StockManagementTable, String> colBatchId;
    @FXML
    private TableColumn<StockManagementTable, String> colItemName;
    @FXML
    private TableColumn<StockManagementTable, String> colCostPrice;
    @FXML
    private TableColumn<StockManagementTable, Double> colSalePrice;
    @FXML
    private TableColumn<StockManagementTable, String> colExpireDate;
    @FXML
    private TableColumn<StockManagementTable, String> colAction;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fetchStockItems();
        configureTable();
    }

    private void configureTable() {
        colBatchId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBatchId()));
        colItemName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemName()));
        colCostPrice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCostPrice()));
        colSalePrice.setCellValueFactory(data -> new SimpleDoubleProperty(data.getValue().getSalePrice()).asObject());
        colExpireDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getExpireDate()));

        colSalePrice.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        colSalePrice.setOnEditCommit(event -> {
            StockManagementTable row = event.getRowValue();
            row.setSalePrice(event.getNewValue());

            table.refresh();
        });

        // Action column
        colAction.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlPanel/StockManagementAction.fxml"));
                        AnchorPane actionBox = loader.load();
                        StockManagementActionController actionColumn = loader.getController();

                        StockManagementTable rowItem = getTableView().getItems().get(getIndex());
                        actionColumn.injectData(
                                rowItem.getBatchId(),
                                rowItem.getSalePrice()
                        );
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

    private void fetchStockItems() {

        Task<List<Stock>> task = new Task() {
            @Override
            protected List<Stock> call() throws Exception {
                List<Stock> stockList = StockCRUD.getStocks();

                return stockList;
            }
        };

        task.setOnSucceeded((t) -> {
            List<Stock> stockList = task.getValue();
            addTableItems(stockList);
        });

        new Thread(task).start();
    }

    private void addTableItems(List<Stock> stockList) {
        table.getItems().clear();

        List<StockManagementTable> tableItemsList = new LinkedList<>();

        for (Stock stock : stockList) {
            StockManagementTable row = new StockManagementTable(stock);
            tableItemsList.add(row);
        }

        table.getItems().addAll(tableItemsList);
    }

}
