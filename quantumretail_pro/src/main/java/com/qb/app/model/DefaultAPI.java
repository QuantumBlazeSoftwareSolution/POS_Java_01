package com.qb.app.model;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

public class DefaultAPI {

    public static TextFormatter<String> createNumericTextFormatter() {
        return new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*\\.?\\d*")) { // Allows digits and optional decimal point
                return change;
            }
            return null;
        });
    }

    public static void bindTableScroll(ScrollBar scrollBar, ScrollPane scrollPane, VBox vBox) {
        scrollBar.valueProperty().bindBidirectional(scrollPane.vvalueProperty());

        // Configure the ScrollBar range to match the ScrollPane
        scrollBar.setMin(0);
        scrollBar.setMax(1);
        scrollBar.setVisibleAmount(0.1); // Adjust as needed

        // If you want the ScrollBar to control the viewport size
        scrollBar.visibleAmountProperty().bind(
                scrollPane.viewportBoundsProperty()
                        .map(bounds -> bounds.getHeight() / vBox.getHeight())
        );
    }

    public static boolean isInteger(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
