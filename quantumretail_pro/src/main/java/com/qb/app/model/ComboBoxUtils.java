/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.model;

import java.util.List;
import java.util.function.Function;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

/**
 *
 * @author Vihanga
 */
public class ComboBoxUtils {

    /**
     * Generic method to load items into a ComboBox and configure display
     *
     * @param comboBox The ComboBox to populate
     * @param items List of items to populate the ComboBox with
     * @param displayProperty Function that extracts the display string from
     * each item
     * @param <T> The type of items in the ComboBox
     */
    public static <T> void configureComboBox(ComboBox<T> comboBox, List<T> items, Function<T, String> displayProperty) {
        comboBox.getItems().setAll(items);

        // Set cell factory to display the desired property
        comboBox.setCellFactory(listView -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : displayProperty.apply(item));
            }
        });

        // Set button cell to display the same way
        comboBox.setButtonCell(new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : displayProperty.apply(item));
            }
        });
    }
}
