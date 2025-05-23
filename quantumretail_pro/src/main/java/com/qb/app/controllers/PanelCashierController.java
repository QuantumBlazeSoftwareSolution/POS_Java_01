package com.qb.app.controllers;

import com.jfoenix.controls.JFXToggleButton;
import com.qb.app.App;
import com.qb.app.model.ControllerClose;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.SVGIconGroup;
import com.qb.app.session.ApplicationSession;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class PanelCashierController implements Initializable {

    //<editor-fold desc="FXML init component" defaultstate="collapsed">
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
    private AnchorPane root;
    @FXML
    private Button btnRefund;
    @FXML
    private JFXToggleButton trainingModeToggle;
    // </editor-fold>

    // <editor-fold desc="Initial Variables" defaultstate="collapsed">
    private boolean isMenuCollapsed = false;
    private Cashier_top_panelController controller;
    private Object currentController;
    // </editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println(trainingModeToggle.isSelected());
        setIcons();
        setInitialState();
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
        if (event.getSource() == btnDashboard) {
            changeCenterPanel("/com/qb/app/cashierDashboard.fxml", "Dashboard");
        } else if (event.getSource() == btnSession) {
            changeCenterPanel("/com/qb/app/cashierSession.fxml", "Session");
        } else if (event.getSource() == btnInvoice) {
            if (ApplicationSession.getSession() != null) {
                changeCenterPanel("/com/qb/app/cashierInvoice.fxml", "Invoice");
            } else {
                CustomAlert.showStyledAlert(root, "Please authenticate to access this feature", "Sign-in Required", Alert.AlertType.WARNING);
                changeCenterPanel("/com/qb/app/cashierSession.fxml", "Session");
            }
        } else if (event.getSource() == btnCloseSale) {
            changeCenterPanel("/com/qb/app/cashierCloseSale.fxml", "Close Sale");
        } else if (event.getSource() == btnWithdrawal) {
            changeCenterPanel("/com/qb/app/cashierWithdrawal.fxml", "Withdrawal");
        } else if (event.getSource() == btnRefund) {
            changeCenterPanel("/com/qb/app/cashierRefund.fxml", "Refund");
        } else if (event.getSource() == BtnRePrint) {
            changeCenterPanel("/com/qb/app/cashierRePrint.fxml", "Re-Print");
        } else if (event.getSource() == btnExit) {
//            InderfaceAction.closeWindow(btnExit);
            try {
                //            InderfaceAction.closeWindow(root);
                App.setRoot("sytemLogin");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            FXMLLoader cashier_top_menu = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlComponent/cashier_top_panel.fxml"));
            contentBorder.setTop(cashier_top_menu.load());
            controller = cashier_top_menu.getController();
            controller.setTitle("Dashboard");
            controller.setPanelCashierController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeCenterPanel(String fxml, String title) {
        try {
            // 1. Close the previous controller (if exists and implements ControllerClose)
            if (currentController != null && currentController instanceof ControllerClose) {
                ((ControllerClose) currentController).close();
            } else if (currentController != null && !(currentController instanceof ControllerClose)) {
                throw new IllegalStateException(currentController.getClass().getSimpleName() + " Controllr class is not a instance of 'ControllerClose' interface. Please implement 'ControllerClose' interface that in 'com.qb.app.model.ControllerClose'");
            }

            // 2. Load the new FXML file
            FXMLLoader panel = new FXMLLoader(getClass().getResource(fxml));
            Parent FXMLroot = panel.load(); // Must load FIRST before getting controller

            // 3. Store the new controller
            currentController = panel.getController(); // Now safe to access

            // 4. Update the UI
            contentBorder.setCenter(FXMLroot);
            controller.setTitle(title);

            // Optional: Log the controller (if needed)
            if (currentController != null) {
                System.out.println("New controller: " + currentController.getClass().getSimpleName());
            }
        } catch (IOException e) {
            System.out.println("Error while excuting changeCenterPanel() " + e.getMessage());
//            e.printStackTrace();
        }
    }

    @FXML
    private void handleTrainingModeToggle(ActionEvent event) {
        if (!trainingModeToggle.isSelected() == false) {
            disableTrainingMode();
            openVerificationInterface();
        } else {
            disableTrainingMode();
        }
        trainingModeToggle.setFocusTraversable(false);
    }

    private void openVerificationInterface() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlPanel/TrainingVerification.fxml"));
            Parent root = loader.load();

            // Get the verification controller
            TrainingVerificationController verificationController = loader.getController();
            verificationController.setMainController(this);

            // Create a new stage for the verification interface
            Stage stage = new Stage();
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(new Scene(root));
            stage.setResizable(false);

            // Pass the stage to the verification controller
            verificationController.setStage(stage);

            // Show the verification window
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enableTrainingMode() {
        trainingModeToggle.setSelected(true);
    }

    private void disableTrainingMode() {
        trainingModeToggle.setSelected(false);
    }
}
