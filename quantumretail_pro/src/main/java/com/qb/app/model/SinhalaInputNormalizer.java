/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.model;

import java.util.Map;
import java.util.function.UnaryOperator;
import javafx.scene.Parent;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;

/**
 *
 * @author Vihanga
 */
public class SinhalaInputNormalizer {

    private static final Map<Character, Character> FIX_MAP = Map.of(
            '/', '0',
            '0', '1',
            '1', '2',
            '2', '3',
            '3', '4',
            '4', '5',
            '5', '6',
            '6', '7',
            '7', '8',
            '8', '9'
    );

    public static String normalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder sb = new StringBuilder();

        for (char c : input.toCharArray()) {
            sb.append(FIX_MAP.getOrDefault(c, c));
        }

        return sb.toString();
    }

    public static void attachSinhalaFix(TextInputControl field) {

        field.textProperty().addListener((obs, oldText, newText) -> {
            String fixed = SinhalaInputNormalizer.normalize(newText);

            if (!newText.equals(fixed)) {
                int pos = field.getCaretPosition();
                field.setText(fixed);
                field.positionCaret(Math.min(pos, fixed.length()));
            }
        });
    }

    public static void applySinhalaFixRecursively(Parent parent) {

        parent.getChildrenUnmodifiable().forEach(node -> {

            if (node instanceof TextInputControl textInputControl) {
                attachSinhalaFix(textInputControl);
            }

            if (node instanceof Parent parent1) {
                applySinhalaFixRecursively(parent1);
            }
        });
    }

    public static TextFormatter<String> createNormalizedNumericFormatter() {

        UnaryOperator<TextFormatter.Change> filter = change -> {

            // Normalize ONLY the typed part
            String normalized = normalize(change.getText());
            change.setText(normalized);

            // Allow empty (backspace)
            if (change.getControlNewText().isEmpty()) {
                return change;
            }

            // Numeric validation (adjust if needed)
            if (!change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return null;
            }

            return change;
        };

        return new TextFormatter<>(filter);
    }
}
