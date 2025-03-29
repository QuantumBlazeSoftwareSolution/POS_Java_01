/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class TestController implements Initializable {

    @FXML
    private ScrollPane bodySection;
    @FXML
    private VBox scrollContainer;
    @FXML
    private ScrollBar scroller;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Bind the ScrollBar to the ScrollPane's vvalue property
        scroller.valueProperty().bindBidirectional(bodySection.vvalueProperty());

        // Configure the ScrollBar range to match the ScrollPane
        scroller.setMin(0);
        scroller.setMax(1);
        scroller.setVisibleAmount(0.1); // Adjust as needed

        // If you want the ScrollBar to control the viewport size
        scroller.visibleAmountProperty().bind(
                bodySection.viewportBoundsProperty()
                        .map(bounds -> bounds.getHeight() / scrollContainer.getHeight())
        );
    }

}
