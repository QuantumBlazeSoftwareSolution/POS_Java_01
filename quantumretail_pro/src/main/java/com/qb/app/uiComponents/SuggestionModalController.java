/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.uiComponents;

import com.qb.app.model.SinhalaInputNormalizer;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class SuggestionModalController implements Initializable {

    @FXML
    private ListView<String> listView;

    private Consumer<String> onSelect;
    @FXML
    private VBox root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && onSelect != null) {
                onSelect.accept(listView.getSelectionModel().getSelectedItem());
            }
        });

        listView.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case ENTER ->
                    onSelect.accept(
                            listView.getSelectionModel().getSelectedItem()
                    );
            }
        });
    }

    public void setSuggestions(List<String> items) {
        listView.setItems(FXCollections.observableArrayList(items));
    }

    public void setOnSelect(Consumer<String> onSelect) {
        this.onSelect = onSelect;
    }

}
