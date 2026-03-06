/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.uiComponents;

import com.qb.app.database_crud.StockCRUD;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.entity.Stock;
import com.qb.app.model.getLogger;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class StockManagementActionController implements Initializable {

    @FXML
    private Button btnApply;
    private String batchId;
    private double newPrice;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnApply) {
            applyNewSalePrice();
        }
    }

    private void applyNewSalePrice() {

        btnApply.setDisable(true);
        btnApply.setText("Updating...");

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {

                Stock stock = StockCRUD.getStockByBatchId(batchId);

                if (newPrice <= 0) {
                    throw new Exception("Sale price should be greater than Rs. 0.00");
                }

                if (stock.getSalePrice() == newPrice) {
                    throw new Exception("Cannot update the sale price with previous price");
                }

                return changeStockPrice(stock);
            }
        };

        task.setOnSucceeded(e -> {

            Boolean result = task.getValue();

            if (result) {
                CustomAlert.showStyledAlert(
                        btnApply,
                        "Stock successfully updated",
                        "Success",
                        Alert.AlertType.INFORMATION
                );
            } else {
                CustomAlert.showStyledAlert(
                        btnApply,
                        "Stock update failed, Please try again later",
                        "Failed",
                        Alert.AlertType.WARNING
                );
            }

            btnApply.setDisable(false);
            btnApply.setText("Apply");
        });

        task.setOnFailed(e -> {

            CustomAlert.showStyledAlert(
                    btnApply,
                    task.getException().getMessage(),
                    "Error",
                    Alert.AlertType.WARNING
            );

            btnApply.setDisable(false);
            btnApply.setText("Apply");
        });

        new Thread(task).start();
    }

    private boolean changeStockPrice(Stock stock) {
        try {
            return JPATransaction.runInTransaction((em) -> {
                stock.setSalePrice(this.newPrice);

                em.merge(stock);
                em.flush();

                return true;
            });
        } catch (Exception e) {
            getLogger.logger().warning(e.toString());
            e.printStackTrace();
            return false;
        }
    }

    public void injectData(String batchId, double newPrice) {
        this.batchId = batchId;
        this.newPrice = newPrice;
    }

}
