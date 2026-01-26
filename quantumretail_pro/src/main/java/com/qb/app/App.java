package com.qb.app;

import com.qb.app.model.ControllerClose;
import com.qb.app.model.SinhalaInputNormalizer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.StageStyle;

public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;
    private static Object currentController; // Store current controller

    @Override
    public void start(Stage stage) throws IOException {
        Font.loadFont(
                getClass().getResource("/com/qb/app/assets/fonts/Roboto-Regular.ttf").toExternalForm(),
                10);
        primaryStage = stage;
        primaryStage.getIcons()
                .add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));
        scene = new Scene(loadFXML("sytemLogin"));

        // ✨ Fix Sinhala keyboard input issue globally for all text fields
        SinhalaInputNormalizer.enableGlobalFix(scene);

        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        if (currentController instanceof ControllerClose controllerClose) {
            // System.out.println("instanceof ControllerClose: Going to trigger close
            // method.");
            controllerClose.close();
        } else {
            // System.out.println("Not instanceof ControllerClose");
        }

        // Load new FXML
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();

        // Save the new controller
        currentController = fxmlLoader.getController();

        // Load additional stylesheets if needed
        if (fxml.equals("admin/panelAdmin")) {
            scene.getStylesheets()
                    .add(App.class.getResource("/com/qb/app/css/annualSaleChartDesign.css").toExternalForm());
            scene.getStylesheets().add(App.class.getResource("/com/qb/app/css/adminStyle.css").toExternalForm());
        }

        scene.setRoot(root);

        if (fxml.equals("admin/adminVerification") || fxml.equals("sytemLogin")
                || fxml.equals("developer/developerVerification")) {
            primaryStage.setMaximized(false);
            primaryStage.sizeToScene();
            centerStageOnScreen();
        } else {
            // Default behavior for other windows
            primaryStage.setMaximized(true);
            resetWindowPosition(); // Reset to top-left when maximizing
        }
    }

    private static void centerStageOnScreen() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double centerX = (screenBounds.getWidth() - primaryStage.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - primaryStage.getHeight()) / 2;
        primaryStage.setX(centerX);
        primaryStage.setY(centerY);
    }

    private static void resetWindowPosition() {
        primaryStage.setX(0);
        primaryStage.setY(0);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        currentController = fxmlLoader.getController(); // Store the initial controller
        return root;
    }

    public static void main(String[] args) {
        launch();
    }
}
