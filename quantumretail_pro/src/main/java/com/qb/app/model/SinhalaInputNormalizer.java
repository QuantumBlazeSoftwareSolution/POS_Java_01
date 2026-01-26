/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.model;

import java.util.Map;
import java.util.function.UnaryOperator;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldListCell;

/**
 * Fixes Sinhala keyboard input issue in JavaFX where number keys produce
 * shifted characters.
 * 
 * USAGE:
 * 1. Call once in your main application class after stage is shown:
 * SinhalaInputNormalizer.enableGlobalFix(primaryStage.getScene());
 * 
 * 2. Or apply to specific controls:
 * textField.setTextFormatter(SinhalaInputNormalizer.createTextFormatter());
 * 
 * @author Vihanga
 */
public class SinhalaInputNormalizer {

    // Toggle this flag to enable/disable the Sinhala number fix
    private static final boolean ENABLE_FIX = false; // Set to true to enable mapping

    // Mapping: What keyboard sends → What should be displayed
    // Based on testing: When you press number N, keyboard sends (N-1)
    private static final Map<Character, Character> SINHALA_NUMBER_FIX = Map.of(
            '/', '0', // Pressing 0 sends '/', map to '0'
            '0', '1', // Pressing 1 sends '0', map to '1'
            '1', '2', // Pressing 2 sends '1', map to '2'
            '2', '3', // Pressing 3 sends '2', map to '3'
            '3', '4', // Pressing 4 sends '3', map to '4'
            '4', '5', // Pressing 5 sends '4', map to '5'
            '5', '6', // Pressing 6 sends '5', map to '6'
            '6', '7', // Pressing 7 sends '6', map to '7'
            '7', '8', // Pressing 8 sends '7', map to '8'
            '8', '9' // Pressing 9 sends '8', map to '9'
    );

    /**
     * Normalizes input text by fixing Sinhala keyboard number mapping.
     */
    public static String normalize(String input) {
        if (!ENABLE_FIX || input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            sb.append(SINHALA_NUMBER_FIX.getOrDefault(c, c));
        }
        return sb.toString();
    }

    /**
     * Creates a TextFormatter that automatically fixes Sinhala input.
     * Use this for general text fields that may contain numbers and text.
     */
    public static TextFormatter<String> createTextFormatter() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            // Normalize the newly typed text
            String newText = change.getText();
            if (newText != null && !newText.isEmpty()) {
                String normalized = normalize(newText);
                change.setText(normalized);
            }
            return change;
        };
        return new TextFormatter<>(filter);
    }

    /**
     * Creates a TextFormatter for numeric-only fields with Sinhala fix.
     * Allows only numbers and optional decimal point.
     */
    public static TextFormatter<String> createNumericFormatter() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            // Normalize the input first
            String newText = change.getText();
            if (newText != null && !newText.isEmpty()) {
                String normalized = normalize(newText);
                change.setText(normalized);
            }

            // Allow empty (for backspace/delete)
            if (change.getControlNewText().isEmpty()) {
                return change;
            }

            // Validate: only digits and optional decimal point
            if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
                return change;
            }

            return null; // Reject invalid input
        };
        return new TextFormatter<>(filter);
    }

    /**
     * Creates a TextFormatter for integer-only fields with Sinhala fix.
     * Allows only whole numbers.
     */
    public static TextFormatter<String> createIntegerFormatter() {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            // Normalize the input first
            String newText = change.getText();
            if (newText != null && !newText.isEmpty()) {
                String normalized = normalize(newText);
                change.setText(normalized);
            }

            // Allow empty (for backspace/delete)
            if (change.getControlNewText().isEmpty()) {
                return change;
            }

            // Validate: only digits
            if (change.getControlNewText().matches("\\d*")) {
                return change;
            }

            return null; // Reject invalid input
        };
        return new TextFormatter<>(filter);
    }

    /**
     * RECOMMENDED: Apply Sinhala fix globally to entire scene.
     * Call this ONCE in your main application after the scene is created.
     * 
     * Example:
     * 
     * @Override
     *           public void start(Stage primaryStage) {
     *           Scene scene = new Scene(root);
     *           SinhalaInputNormalizer.enableGlobalFix(scene);
     *           primaryStage.setScene(scene);
     *           primaryStage.show();
     *           }
     */
    public static void enableGlobalFix(Scene scene) {
        if (scene == null) {
            return;
        }

        // Listen for any new nodes added to the scene
        scene.rootProperty().addListener((obs, oldRoot, newRoot) -> {
            if (newRoot != null) {
                applyToNode(newRoot);
            }
        });

        // Apply to current root
        if (scene.getRoot() != null) {
            applyToNode(scene.getRoot());
        }
    }

    /**
     * Apply Sinhala fix to a specific parent node and all its children recursively.
     * Use this if you want to apply the fix to a specific container.
     */
    public static void applyToNode(Parent parent) {
        if (parent == null) {
            return;
        }

        parent.getChildrenUnmodifiable().forEach(node -> {
            // Apply to text input controls
            if (node instanceof TextInputControl textInput) {
                // Only apply if no formatter is already set
                if (textInput.getTextFormatter() == null) {
                    textInput.setTextFormatter(createTextFormatter());
                }
            }

            // Recursively apply to child containers
            if (node instanceof Parent childParent) {
                applyToNode(childParent);
            }

            // Handle TableView editable cells
            if (node instanceof TableView<?> tableView) {
                // Note: For editable TableView cells, you may need to set
                // cell factories that use the formatter. This is a more
                // complex scenario - see applyToTableColumn() method.
            }

            // Handle ListView editable cells
            if (node instanceof ListView<?> listView) {
                // Similar to TableView - requires custom cell factory
            }
        });
    }

    /**
     * Helper method to apply formatter to a specific TextInputControl.
     * Use this for individual controls where you want explicit control.
     */
    public static void applyTo(TextInputControl control) {
        if (control != null && control.getTextFormatter() == null) {
            control.setTextFormatter(createTextFormatter());
        }
    }

    /**
     * Helper method to apply numeric formatter to a specific TextInputControl.
     */
    public static void applyNumericTo(TextInputControl control) {
        if (control != null) {
            control.setTextFormatter(createNumericFormatter());
        }
    }

    /**
     * Helper method to apply integer formatter to a specific TextInputControl.
     */
    public static void applyIntegerTo(TextInputControl control) {
        if (control != null) {
            control.setTextFormatter(createIntegerFormatter());
        }
    }
}
