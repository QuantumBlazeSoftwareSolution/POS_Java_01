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
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
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

    @FXML
    private AreaChart<String, Number> overviewAreaChart;
    @FXML
    private PieChart salesPieChart;
    @FXML
    private BarChart<String, Number> topProductsBarChart;
    @FXML
    private LineChart<String, Number> revenueLineChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        configureTable();
        fetchExpiringStocks();
        initializeCharts();
    }

    private void initializeCharts() {
        // --- Area Chart Mock Data ---
        overviewAreaChart.setTitle("Current vs Last Month");
        XYChart.Series<String, Number> seriesCurrent = new XYChart.Series<>();
        seriesCurrent.setName("Current Month");
        seriesCurrent.getData().add(new XYChart.Data<>("Week 1", 5000));
        seriesCurrent.getData().add(new XYChart.Data<>("Week 2", 7000));
        seriesCurrent.getData().add(new XYChart.Data<>("Week 3", 8500));
        seriesCurrent.getData().add(new XYChart.Data<>("Week 4", 12000));

        XYChart.Series<String, Number> seriesLast = new XYChart.Series<>();
        seriesLast.setName("Last Month");
        seriesLast.getData().add(new XYChart.Data<>("Week 1", 4500));
        seriesLast.getData().add(new XYChart.Data<>("Week 2", 6000));
        seriesLast.getData().add(new XYChart.Data<>("Week 3", 6500));
        seriesLast.getData().add(new XYChart.Data<>("Week 4", 9000));

        overviewAreaChart.getData().addAll(seriesCurrent, seriesLast);

        // --- Pie Chart Mock Data ---
        salesPieChart.getData().addAll(
                new PieChart.Data("Electronics", 45),
                new PieChart.Data("Clothing", 25),
                new PieChart.Data("Groceries", 20),
                new PieChart.Data("Furniture", 10));

        // --- Bar Chart Mock Data ---
        topProductsBarChart.setTitle("Units Sold");
        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.setName("Products");
        barSeries.getData().add(new XYChart.Data<>("Laptop", 120));
        barSeries.getData().add(new XYChart.Data<>("Smartphone", 250));
        barSeries.getData().add(new XYChart.Data<>("Headphones", 310));
        barSeries.getData().add(new XYChart.Data<>("Monitor", 85));
        barSeries.getData().add(new XYChart.Data<>("Keyboard", 150));
        topProductsBarChart.getData().add(barSeries);

        // --- Line Chart Mock Data ---
        revenueLineChart.setTitle("Last 7 Days");
        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.setName("Revenue ($)");
        lineSeries.getData().add(new XYChart.Data<>("Mon", 1200));
        lineSeries.getData().add(new XYChart.Data<>("Tue", 1500));
        lineSeries.getData().add(new XYChart.Data<>("Wed", 1100));
        lineSeries.getData().add(new XYChart.Data<>("Thu", 1800));
        lineSeries.getData().add(new XYChart.Data<>("Fri", 2200));
        lineSeries.getData().add(new XYChart.Data<>("Sat", 3100));
        lineSeries.getData().add(new XYChart.Data<>("Sun", 2800));
        revenueLineChart.getData().add(lineSeries);
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
                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/com/qb/app/fxmlPanel/ExpireAlertAction.fxml"));
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
