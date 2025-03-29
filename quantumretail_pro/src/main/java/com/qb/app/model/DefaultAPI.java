package com.qb.app.model;

import javafx.scene.control.TextFormatter;

public class DefaultAPI {
 
    public static TextFormatter<String> createNumericTextFormatter() {
        return new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*\\.?\\d*")) { // Allows digits and optional decimal point
                return change;
            }
            return null;
        });
    }
    
}
