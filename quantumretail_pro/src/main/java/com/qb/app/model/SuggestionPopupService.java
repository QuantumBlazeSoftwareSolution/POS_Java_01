/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.model;

import com.qb.app.uiComponents.SuggestionModalController;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.PopupControl;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Window;

public class SuggestionPopupService {

    private final PopupControl popup = new PopupControl();
    private final SuggestionModalController controller;
    private final Region content;

    public SuggestionPopupService() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/qb/app/uiComponents/suggestionModal.fxml")
            );

            content = (Region) loader.load();
            controller = loader.getController();

            popup.getScene().setRoot(content);
            popup.setAutoHide(true);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load suggestion popup", e);
        }
    }

    public void attach(
            TextField textField,
            Function<String, List<String>> suggestionProvider,
            Consumer<String> onSelect
    ) {

        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null || newVal.isBlank()) {
                popup.hide();
                return;
            }

            List<String> suggestions = suggestionProvider.apply(newVal);

            if (suggestions.isEmpty()) {
                popup.hide();
                return;
            }

            controller.setSuggestions(suggestions);
            controller.setOnSelect(value -> {
                textField.setText(value);
                popup.hide();
                onSelect.accept(value);   // 🔥 THIS IS THE KEY
            });

            showPopup(textField);
        });
    }

    private void showPopup(TextField field) {

        // Always recalculate width for the active field
        double width = field.getWidth();
        content.setMinWidth(width);
        content.setPrefWidth(width);
        content.setMaxWidth(width);

        Window window = field.getScene().getWindow();

        var point = field.localToScreen(0, field.getHeight());
        double x = point.getX();
        double y = point.getY();

        if (popup.isShowing()) {
            popup.hide();
        }

        popup.show(window, x, y);
    }

}
