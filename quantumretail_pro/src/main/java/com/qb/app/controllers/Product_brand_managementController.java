package com.qb.app.controllers;

import com.qb.app.model.SVGIconGroup;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;

public class Product_brand_managementController implements Initializable {

    //<editor-fold desc="FXML init component" defaultstate="collapsed">
    @FXML
    private Group iconPage;
    //</editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        iconPage.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/page-icon.svg"));
    }

}
