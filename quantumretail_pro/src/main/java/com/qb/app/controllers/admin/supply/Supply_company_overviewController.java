/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.qb.app.controllers.admin.supply;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author Vihanga
 */
public class Supply_company_overviewController implements Initializable {

    @FXML
    private Group iconCompanyOrverview;
    @FXML
    private PieChart conpanyOrverviewChart;
    @FXML
    private BarChart<?, ?> supplyHistoryBarchart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
