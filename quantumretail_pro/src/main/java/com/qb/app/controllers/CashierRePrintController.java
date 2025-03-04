package com.qb.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class CashierRePrintController implements Initializable {

    @FXML
    private VBox rePrintInvoiceItemContainer;
    @FXML
    private VBox rePrintItemContainer;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlComponent/rePrintInvoiceItem.fxml"));
            Node rePrintInvoiceItem = loader.load();

            rePrintInvoiceItemContainer.getChildren().add(rePrintInvoiceItem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
