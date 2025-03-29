package com.qb.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("test"));
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(null);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        if (fxml.equals("panelAdmin")) {
            scene.getStylesheets().add(App.class.getResource("/com/qb/app/css/annualSaleChartDesign.css").toExternalForm());
            scene.getStylesheets().add(App.class.getResource("/com/qb/app/css/adminStyle.css").toExternalForm());
        }
        scene.setRoot(loadFXML(fxml));
        primaryStage.sizeToScene();
        primaryStage.setMaximized(true);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(null);
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
