package com.qb.app.controllers.admin;

import com.qb.app.controllers.table_models.ExpireAlertTable;
import com.qb.app.database_crud.StockCRUD;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.getLogger;
import com.qb.app.uiComponents.ExpireAlertActionController;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class AdminDashboardController implements Initializable {

    @FXML
    private TableView<ExpireAlertTable> table;
    @FXML
    private TableColumn<ExpireAlertTable, String> colBatchId;
    @FXML
    private TableColumn<ExpireAlertTable, String> colItemName;
    @FXML
    private TableColumn<ExpireAlertTable, String> colSalePrice;
    @FXML
    private TableColumn<ExpireAlertTable, String> colQty;
    @FXML
    private TableColumn<ExpireAlertTable, String> colExpireDate;
    @FXML
    private TableColumn<ExpireAlertTable, String> colAction;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configureTable();
        fetchExpiringStocks();
    }

    private void fetchExpiringStocks() {

        Task<List<Stock>> task = new Task<>() {
            @Override
            protected List<Stock> call() throws Exception {

                List<Stock> stockList = StockCRUD.getStocks();

                Date today = new Date();

                Calendar cal = Calendar.getInstance();
                cal.setTime(today);
                cal.add(Calendar.DAY_OF_MONTH, 15);
                Date next10Days = cal.getTime();

                List<Stock> expiringStocks = new LinkedList<>();

                for (Stock stock : stockList) {

                    Date expireDate = stock.getExpireDate();

                    if (expireDate != null
                            && !expireDate.before(today)
                            && !expireDate.after(next10Days)) {

                        expiringStocks.add(stock);
                    }
                }

                return expiringStocks;
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

        List<ExpireAlertTable> tableItemsList = new LinkedList<>();

        for (Stock stock : stockList) {
            ExpireAlertTable row = new ExpireAlertTable(stock);
            tableItemsList.add(row);
        }

        table.getItems().addAll(tableItemsList);
    }

    private void configureTable() {
        colBatchId.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBatchId()));
        colItemName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getItemName()));
        colQty.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getQty()));
        colSalePrice.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSalePrice()));
        colExpireDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getExpireDate()));

        colAction.setCellFactory(col -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlPanel/ExpireAlertAction.fxml"));
                        AnchorPane actionBox = loader.load();
                        ExpireAlertActionController actionColumn = loader.getController();

                        ExpireAlertTable rowItem = getTableView().getItems().get(getIndex());
                        actionColumn.injectData(rowItem.getBatchId());
                        actionColumn.setCallback(() -> {
                            fetchExpiringStocks();
                        });
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
}
