package com.qb.app.controllers;

import com.qb.app.model.InderfaceAction;
import com.qb.app.model.SVGIconGroup;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PanelCashierController implements Initializable {

    // <editor-fold desc="FXML init component">
    @FXML
    private Label btnMenu;
    @FXML
    private Circle systemLogo;
    @FXML
    private Button btnExit;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnSession;
    @FXML
    private Button btnInvoice;
    @FXML
    private Button btnCloseSale;
    @FXML
    private Button btnWithdrawal;
    @FXML
    private Button bttnRefund;
    @FXML
    private Button BtnRePrint;
    @FXML
    private StackPane centerPanel;
    @FXML
    private Group iconDashboard;
    @FXML
    private Group iconSession;
    @FXML
    private Group iconInvoice;
    @FXML
    private Group iconCloseSale;
    @FXML
    private Group iconWithdrawal;
    @FXML
    private Group iconRefund;
    @FXML
    private Group iconRePrint;
    @FXML
    private Group iconExit;
    // </editor-fold>
    @FXML
    private BorderPane leftSideMenu;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setIcons();
        setInitialState();
    }

    private void setIcons() {
        iconDashboard.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/dashboard-solid.svg"));
        iconSession.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-session-outline.svg"));
        iconInvoice.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-invoice-outline.svg"));
        iconCloseSale.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-close-sale-outline.svg"));
        iconWithdrawal.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-withdraw-outline.svg"));
        iconRefund.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-refund-outline.svg"));
        iconRePrint.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-re-print-outline.svg"));
        iconExit.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/exit-solid.svg"));
    }

    @FXML
    private void handleActionButtons(ActionEvent event) {
        if (event.getSource() == btnExit) {
            InderfaceAction.closeWindow(btnExit);
        }
    }

    private void setInitialState() {
        setDefaultPanel();
    }

    private void setDefaultPanel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/cashierDashboard.fxml"));
            centerPanel.getChildren().setAll(Collections.singleton(loader.load()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
