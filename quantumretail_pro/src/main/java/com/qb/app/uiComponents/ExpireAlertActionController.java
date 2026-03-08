/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.uiComponents;

import com.qb.app.controllers.admin.ExpireAlertCallback;
import com.qb.app.controllers.table_models.ExpireAlertTable;
import com.qb.app.database_crud.StockCRUD;
import com.qb.app.database_crud.StockStatusCRUD;
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
import javafx.scene.control.TableCell;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class ExpireAlertActionController implements Initializable {

    @FXML
    private Button btnDispose;
    private String batchId;
    private ExpireAlertCallback callback;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnDispose) {
            disposeStock(this.batchId);
        }
    }

    public void injectData(String batchId) {
        this.batchId = batchId;
    }

    private void disposeStock(String batchId) {

        btnDispose.setDisable(true);
        btnDispose.setText("Disposing...");

        Task<Boolean> task = new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {

                Stock stock = StockCRUD.getStockByBatchId(batchId);

                stock.setQty(0);
                stock.setStockStatusId(StockStatusCRUD.getStockStatus("inactive"));

                return updateStock(stock);
            }
        };

        task.setOnSucceeded(e -> {

            Boolean result = task.getValue();

            if (result) {
                CustomAlert.showStyledAlert(
                        btnDispose,
                        "Stock successfully disposed",
                        "Success",
                        Alert.AlertType.INFORMATION
                );

                if (callback != null) {
                    callback.onStockDisposed();
                }
            } else {
                CustomAlert.showStyledAlert(
                        btnDispose,
                        "Stock disposed failed, Please try again later",
                        "Failed",
                        Alert.AlertType.WARNING
                );
            }

            btnDispose.setDisable(false);
            btnDispose.setText("Dispose");
        });

        task.setOnFailed(e -> {

            CustomAlert.showStyledAlert(
                    btnDispose,
                    task.getException().getMessage(),
                    "Error",
                    Alert.AlertType.WARNING
            );

            btnDispose.setDisable(false);
            btnDispose.setText("Dispose");
        });

        new Thread(task).start();
    }

    private boolean updateStock(Stock stock) {
        try {
            return JPATransaction.runInTransaction((em) -> {

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

    public void setCallback(ExpireAlertCallback callback) {
        this.callback = callback;
    }
}
