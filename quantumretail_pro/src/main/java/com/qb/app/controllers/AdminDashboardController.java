package com.qb.app.controllers;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;

public class AdminDashboardController implements Initializable {

    @FXML
    private AreaChart<String, Number> annualChart;
    @FXML
    private BarChart<Number, String> brandChart;
    @FXML
    private PieChart systemChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("2025");
        series1.getData().add(new XYChart.Data<>("Jan", 80));
        series1.getData().add(new XYChart.Data<>("Feb", 60));
        series1.getData().add(new XYChart.Data<>("Mar", 90));
        series1.getData().add(new XYChart.Data<>("Apr", 40));
        series1.getData().add(new XYChart.Data<>("May", 50));
        series1.getData().add(new XYChart.Data<>("Jun", 70));
        series1.getData().add(new XYChart.Data<>("Jul", 30));
        series1.getData().add(new XYChart.Data<>("Aug", 80));
        series1.getData().add(new XYChart.Data<>("Sep", 90));
        series1.getData().add(new XYChart.Data<>("Oct", 40));
        series1.getData().add(new XYChart.Data<>("Nov", 70));
        series1.getData().add(new XYChart.Data<>("Dec", 60));

        // Create Data Series for 2023
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("2024");
        series2.getData().add(new XYChart.Data<>("Jan", 60));
        series2.getData().add(new XYChart.Data<>("Feb", 70));
        series2.getData().add(new XYChart.Data<>("Mar", 50));
        series2.getData().add(new XYChart.Data<>("Apr", 30));
        series2.getData().add(new XYChart.Data<>("May", 60));
        series2.getData().add(new XYChart.Data<>("Jun", 40));
        series2.getData().add(new XYChart.Data<>("Jul", 80));
        series2.getData().add(new XYChart.Data<>("Aug", 50));
        series2.getData().add(new XYChart.Data<>("Sep", 90));
        series2.getData().add(new XYChart.Data<>("Oct", 60));
        series2.getData().add(new XYChart.Data<>("Nov", 40));
        series2.getData().add(new XYChart.Data<>("Dec", 80));

        annualChart.getData().addAll(series1, series2);

        XYChart.Series<Number, String> series3 = new XYChart.Series<>();
        series3.setName("2024");
        series3.getData().add(new XYChart.Data<>(60, "Munchee"));
        series3.getData().add(new XYChart.Data<>(70, "Keels"));
        series3.getData().add(new XYChart.Data<>(50, "Maggie"));
        series3.getData().add(new XYChart.Data<>(30, "Fruit & Nut"));
        series3.getData().add(new XYChart.Data<>(60, "Maliban"));
        series3.getData().add(new XYChart.Data<>(40, "MD"));
        series3.getData().add(new XYChart.Data<>(80, "Prima"));
        series3.getData().add(new XYChart.Data<>(50, "Nipuna"));

        brandChart.getData().add(series3);

        systemChart.getData().add(new PieChart.Data("Employee", getRandomValue()));
        systemChart.getData().add(new PieChart.Data("Cashier", getRandomValue()));
        systemChart.getData().add(new PieChart.Data("Product", getRandomValue()));
        systemChart.getData().add(new PieChart.Data("Category", getRandomValue()));
        systemChart.getData().add(new PieChart.Data("Pending Creditors", getRandomValue()));
        systemChart.getData().add(new PieChart.Data("Supply Companies", getRandomValue()));
        systemChart.getData().add(new PieChart.Data("Suppliers", getRandomValue()));

        // Add tooltips to each slice
        for (PieChart.Data data : systemChart.getData()) {
            Tooltip tooltip = new Tooltip(data.getName() + ": " + data.getPieValue());
            Tooltip.install(data.getNode(), tooltip);
        }
    }

    private int getRandomValue() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }
}
