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
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
import com.qb.app.database_crud.DashboardCRUD;

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
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Fetch real data from DB asynchronously
                Map<String, Double> currentMonthData = DashboardCRUD.getMonthlyRevenue(true);
                Map<String, Double> lastMonthData = DashboardCRUD.getMonthlyRevenue(false);
                Map<String, Double> categoryData = DashboardCRUD.getSalesByCategory();
                Map<String, Double> topProductsData = DashboardCRUD.getTopSellingProducts();
                Map<String, Double> lineData = DashboardCRUD.getRevenueLast7Days();

                // Update UI on JavaFX main thread
                Platform.runLater(() -> {
                    // --- Area Chart DB Data ---
                    overviewAreaChart.setTitle("Current vs Last Month");
                    XYChart.Series<String, Number> seriesCurrent = new XYChart.Series<>();
                    seriesCurrent.setName("Current Month");
                    currentMonthData.forEach((k, v) -> seriesCurrent.getData().add(new XYChart.Data<>(k, v)));

                    XYChart.Series<String, Number> seriesLast = new XYChart.Series<>();
                    seriesLast.setName("Last Month");
                    lastMonthData.forEach((k, v) -> seriesLast.getData().add(new XYChart.Data<>(k, v)));

                    overviewAreaChart.getData().clear();
                    overviewAreaChart.getData().addAll(seriesCurrent, seriesLast);

                    // --- Pie Chart DB Data ---
                    salesPieChart.getData().clear();
                    categoryData.forEach((k, v) -> salesPieChart.getData().add(new PieChart.Data(k, v)));

                    // --- Bar Chart DB Data ---
                    topProductsBarChart.setTitle("Units Sold");
                    XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
                    barSeries.setName("Products");
                    topProductsData.forEach((k, v) -> barSeries.getData().add(new XYChart.Data<>(k, v)));
                    topProductsBarChart.getData().clear();
                    topProductsBarChart.getData().add(barSeries);

                    // --- Line Chart DB Data ---
                    revenueLineChart.setTitle("Last 7 Days");
                    XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
                    lineSeries.setName("Revenue ($)");
                    lineData.forEach((k, v) -> lineSeries.getData().add(new XYChart.Data<>(k, v)));
                    revenueLineChart.getData().clear();
                    revenueLineChart.getData().add(lineSeries);
                });
                return null;
            }
        };

        new Thread(task).start();
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
