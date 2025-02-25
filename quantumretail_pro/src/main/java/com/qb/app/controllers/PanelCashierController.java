package com.qb.app.controllers;

import com.qb.app.model.SVGIconGroup;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setIcons();
    }

    private SVGIconGroup svgIconGroup;

    private void setIcons() {
//        svgIconLoader.loadSVGIcon(iconDashboard, ("/com/qb/app/assets/icons/dashboard-solid.svg"));
//        svgIconLoader.loadSVGIcon(iconSession, ("/com/qb/app/assets/icons/cashier-session-outline.svg"));
//        svgIconLoader.loadSVGIcon(iconInvoice, ("/com/qb/app/assets/icons/cashier-invoice-outline.svg"));
//        svgIconLoader.loadSVGIcon(iconCloseSale, ("/com/qb/app/assets/icons/cashier-close-sale-outline.svg"));
//        svgIconLoader.loadSVGIcon(iconWithdrawal, ("/com/qb/app/assets/icons/cashier-withdrawal-outline.svg"));
//        svgIconLoader.loadSVGIcon(iconRefund, ("/com/qb/app/assets/icons/cashier-refund-outline.svg"));
//        svgIconLoader.loadSVGIcon(iconRePrint, ("/com/qb/app/assets/icons/cashier-re-print.svg"));
//        svgIconLoader.loadSVGIcon(iconExit, ("/com/qb/app/assets/icons/exit-solid.svg"));

        iconDashboard.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/dashboard-solid.svg"));
    }

}
