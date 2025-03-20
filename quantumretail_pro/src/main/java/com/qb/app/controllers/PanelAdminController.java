package com.qb.app.controllers;

import com.qb.app.model.SVGIconGroup;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PanelAdminController implements Initializable {

    // <editor-fold desc="FXML init component">
    @FXML
    private AnchorPane root;
    @FXML
    private BorderPane mainBorderLayout;
    @FXML
    private BorderPane leftSideMenu;
    @FXML
    private Circle systemLogo;
    @FXML
    private Button btnExit;
    @FXML
    private Group iconExit;
    @FXML
    private Button btnDashboard;
    @FXML
    private Group iconDashboard;
    @FXML
    private Group iconEmployee;
    @FXML
    private Group iconProduct;
    @FXML
    private Group iconDiscount;
    @FXML
    private Group iconInventory;
    @FXML
    private Group iconSupplyManagement;
    @FXML
    private Group iconCustomer;
    @FXML
    private Group iconReport;
    @FXML
    private BorderPane contentBorder;
    @FXML
    private Button btnEmployee;
    @FXML
    private Button btnProduct;
    @FXML
    private Button btnDiscount;
    @FXML
    private Button btnInventory;
    @FXML
    private Button btnSupplyManagement;
    @FXML
    private Button btnCustomer;
    @FXML
    private Button btnReports;
    // </editor-fold>

    // <editor-fold desc="Initial Variables">
    private boolean isMenuCollapsed = false;
    private Admin_top_panelController controller;
    // </editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setIcons();
        setInitialState();
        setSystemLogo();
    }

    @FXML
    private void handleActionButtons(ActionEvent event) {
    }

    private void setIcons() {
        iconCustomer.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-customer.svg"));
        iconDashboard.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/dashboard-solid.svg"));
        iconDiscount.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-discount.svg"));
        iconEmployee.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-employee.svg"));
        iconExit.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-exit-solid.svg"));
        iconInventory.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-inventory.svg"));
        iconProduct.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-product.svg"));
        iconReport.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-report.svg"));
        iconSupplyManagement.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-supply-management.svg"));
    }

    public void toggleMenu() {
        if (isMenuCollapsed) {
            expandMenu();
        } else {
            collapseMenu();
        }
        isMenuCollapsed = !isMenuCollapsed; // Toggle the state
        double menuWidth = leftSideMenu.getWidth();
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
            FXMLLoader dashboard = new FXMLLoader(getClass().getResource("/com/qb/app/adminDashboard.fxml"));
            contentBorder.setCenter(dashboard.load());
            FXMLLoader admin_top_menu = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlComponent/admin_top_panel.fxml"));
            contentBorder.setTop(admin_top_menu.load());
            controller = admin_top_menu.getController();
            controller.setPanelAdminController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeCenterPanel(String fxml, String title) {
        try {
            FXMLLoader panel = new FXMLLoader(getClass().getResource(fxml));
            contentBorder.setCenter(panel.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSystemLogo() {
        Image image = new Image(getClass().getResource("/com/qb/app/assets/images/QB_LOGO.png").toExternalForm());
        systemLogo.setFill(new ImagePattern(image));
    }

}
