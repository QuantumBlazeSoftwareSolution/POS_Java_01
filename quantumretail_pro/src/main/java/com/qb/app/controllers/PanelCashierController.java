package com.qb.app.controllers;

import com.qb.app.model.InderfaceAction;
import com.qb.app.model.InterfaceMortion;
import com.qb.app.model.SVGIconGroup;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PanelCashierController implements Initializable {

    //<editor-fold desc="FXML init component">
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
    @FXML
    private BorderPane leftSideMenu;
    @FXML
    private BorderPane mainBorderLayout;
    @FXML
    private BorderPane contentBorder;
    @FXML
    private Button btnToggleMenu; // Add this button for toggling the menu
    @FXML
    private AnchorPane root;
    // </editor-fold>

    private boolean isMenuCollapsed = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setIcons();
        setInitialState();
        setMouseEvent();

        leftSideMenu.setTranslateX(0);
    }

    private void setIcons() {
        iconDashboard.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/dashboard-solid.svg"));
        iconSession.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-session-outline.svg"));
        iconInvoice.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-invoice-outline.svg"));
        iconCloseSale.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-close-sale-outline.svg"));
        iconWithdrawal.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-withdraw-outline.svg"));
        iconRefund.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-refund-outline.svg"));
        iconRePrint.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-re-print-outline.svg"));
        iconExit.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-exit-solid.svg"));
    }

    @FXML
    private void handleActionButtons(ActionEvent event) {
        if (event.getSource() == btnExit) {
            InderfaceAction.closeWindow(btnExit);
        }
    }

    public void toggleMenu() {
        if (isMenuCollapsed) {
            expandMenu();
        } else {
            collapseMenu();
        }
        isMenuCollapsed = !isMenuCollapsed; // Toggle the state
        double menuWidth = leftSideMenu.getWidth();
        System.out.println("Menu Width: " + menuWidth);
    }

    private void collapseMenu() {
        double menuWidth = leftSideMenu.getWidth();

        leftSideMenu.setMinWidth(0); // Ensure it can shrink properly
        leftSideMenu.setMaxWidth(menuWidth);

        // Create a TranslateTransition for the side menu
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), leftSideMenu);
        translateTransition.setToX(-menuWidth); // Move the menu to the left by its width

        // Create a Timeline to animate the width of the side menu
        Timeline widthTransition = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(leftSideMenu.prefWidthProperty(), 0)
                )
        );

        // Combine both transitions into a ParallelTransition
        ParallelTransition parallelTransition = new ParallelTransition(widthTransition, translateTransition);
        parallelTransition.setOnFinished(event -> leftSideMenu.setMaxWidth(0)); // Ensure it stays collapsed
        parallelTransition.play();
    }

    private void expandMenu() {
        double menuWidth = 250;

        leftSideMenu.setMaxWidth(menuWidth);

        // Create a TranslateTransition for the side menu
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), leftSideMenu);
        translateTransition.setToX(0); // Move the menu back to its original position

        // Create a Timeline to animate the width of the side menu
        Timeline widthTransition = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(leftSideMenu.prefWidthProperty(), menuWidth)
                )
        );

        // Combine both transitions into a ParallelTransition
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, widthTransition);
        parallelTransition.setOnFinished(event -> leftSideMenu.setMinWidth(menuWidth)); // Prevent it from resizing back to 140px
        parallelTransition.play();
    }

    private void setInitialState() {
        setDefaultPanel();
    }

    private void setDefaultPanel() {
        try {
            FXMLLoader dashboard = new FXMLLoader(getClass().getResource("/com/qb/app/cashierDashboard.fxml"));
            contentBorder.setCenter(dashboard.load());
            FXMLLoader cashier_top_menu = new FXMLLoader(getClass().getResource("/com/qb/app/cashier_top_panel.fxml"));
            contentBorder.setTop(cashier_top_menu.load());
            Cashier_top_panelController controller = cashier_top_menu.getController();
            controller.setTitle("Dashboard");
            controller.setPanelCashierController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setMouseEvent() {
        InterfaceMortion interfaceMortion = new InterfaceMortion();
        interfaceMortion.enableDrag(root);
    }
}
