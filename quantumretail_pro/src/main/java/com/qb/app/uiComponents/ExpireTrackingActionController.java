package com.qb.app.uiComponents;

import com.qb.app.controllers.table_models.ExpireTrackingTable;
import com.qb.app.database_crud.StockCRUD;
import com.qb.app.database_crud.StockStatusCRUD;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.entity.Stock;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ExpireTrackingActionController implements Initializable {

    @FXML
    private Button btnDispose;
    private Stock stock;
    private Runnable refreshCallback;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setRefreshCallback(Runnable refreshCallback) {
        this.refreshCallback = refreshCallback;
    }

    public void dataInject(Stock stock) {
        this.stock = stock;
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnDispose) {
            disposeStock();
        }
    }

    private void disposeStock() {

        String productName = stock.getProductId().getProduct();
        double quantity = stock.getQty();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Dispose Expired Stock");
        alert.setHeaderText("Confirm Stock Disposal");
        alert.setContentText(
                """
                You are about to permanently dispose the following stock:
                
                Product: """ + productName + "\n"
                + "Quantity: " + quantity + "\n\n"
                + "This action cannot be undone.\n"
                + "Do you want to continue?"
        );

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(
                getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()
        ));

        ButtonType confirmButton = new ButtonType("Yes, Dispose", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == confirmButton) {

            Stock disposingStock = this.stock;
            disposingStock.setStockStatusId(StockStatusCRUD.getStockStatus("inactive"));
            disposingStock.setQty(0);

            Stock updatedStock = StockCRUD.updateStock(disposingStock);

            if (updatedStock != null) {
                CustomAlert.showStyledAlert(
                        btnDispose,
                        productName + " stock has been disposed successfully.",
                        Alert.AlertType.INFORMATION
                );

                if (refreshCallback != null) {
                    refreshCallback.run();
                }

            } else {
                CustomAlert.showStyledAlert(
                        btnDispose,
                        "Failed to dispose " + productName + " stock.\n\n"
                        + "The system could not update the inventory record.\n"
                        + "Please try again or contact the system administrator.",
                        Alert.AlertType.ERROR
                );
            }
        }
    }

}
