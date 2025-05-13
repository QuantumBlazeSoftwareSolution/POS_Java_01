package com.qb.app.controllers;

import com.qb.app.model.ComboBoxUtils;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.JPATransaction;
import com.qb.app.model.SVGIconGroup;
import com.qb.app.model.entity.Brand;
import com.qb.app.model.entity.ProductType;
import com.qb.app.model.entity.ProductUnit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class Product_registrationController implements Initializable {

    // <editor-fold desc="FXML init component" defaultstate="collapsed">
    @FXML
    private Group iconPage;
    @FXML
    private TextField tfItemName;
    @FXML
    private TextField tfBarCode;
    @FXML
    private ComboBox<Brand> cbBrand;
    @FXML
    private TextField tfSalePrice;
    @FXML
    private TextField tfCostPrice;
    @FXML
    private TextField tfDiscount;
    @FXML
    private ComboBox<ProductUnit> cbUnit;
    @FXML
    private TextField tfMeasure;
    @FXML
    private ComboBox<ProductType> cbType;
    @FXML
    private TextField tfParentID;
    @FXML
    private ImageView productImage;
    @FXML
    private Button btnPicture;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnRegister;
//    </editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInitialState();
    }

    private void setInitialState() {
        tfCostPrice.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfSalePrice.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfDiscount.setTextFormatter(DefaultAPI.createNumericTextFormatter());
        tfMeasure.setTextFormatter(DefaultAPI.createNumericTextFormatter());

        iconPage.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/page-icon.svg"));
        loadComboBoxData();
    }

    @FXML
    private void handleFileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        // Set extension filters
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                "Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                // 1. Save to resources folder
                String destPath = saveToResources(selectedFile);

                // 2. Display in ImageView
                displayImage(destPath);

            } catch (IOException e) {
                CustomAlert.showStyledAlert(e.getMessage(), "Error loading image", Alert.AlertType.ERROR);
            }
        }
    }

    private String saveToResources(File sourceFile) throws IOException {
        // Define target directory in resources
        String resourcesDir = "src/main/resources/com/qb/app/assets/images/product/";

        // Create directory if it doesn't exist
        Path dirPath = Paths.get(resourcesDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }

        // Generate unique filename to avoid overwrites
        String fileName = "product_" + System.currentTimeMillis()
                + getFileExtension(sourceFile.getName());
        Path destination = Paths.get(resourcesDir + fileName);

        // Copy file to resources
        Files.copy(sourceFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
        return destination.toString();
    }

    private void displayImage(String imagePath) {
        try {
            // For development (using file system path)
            Image image = new Image(new File(imagePath).toURI().toString());

            // For production (when packaged in JAR)
            // Image image = new Image(getClass().getResourceAsStream(
            //     "/com/qb/app/assets/images/product/" + Paths.get(imagePath).getFileName()));
            productImage.setImage(image);
        } catch (Exception e) {
            CustomAlert.showStyledAlert(e.getMessage(), "Error loading image", Alert.AlertType.ERROR);
        }
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }

//    private void showErrorAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnRegister) {
            productRegister();
        }
    }

    private void productRegister() {
        if (IsProductValid()) {

        }
    }

    private boolean IsProductValid() {
        boolean status = false;
        if (tfItemName.getText().isEmpty() || tfItemName.getText().equals("")) {
            CustomAlert.showStyledAlert("Product name is required.", Alert.AlertType.WARNING);
        } else if (tfBarCode.getText().isEmpty() || tfBarCode.getText().equals("")) {
            CustomAlert.showStyledAlert("Product name is required.", Alert.AlertType.WARNING);
        } else if (tfSalePrice.getText().isEmpty() || tfSalePrice.getText().equals("")) {
            CustomAlert.showStyledAlert("Product name is required.", Alert.AlertType.WARNING);
        } else if (!DefaultAPI.isDouble(tfSalePrice.getText())) {
            CustomAlert.showStyledAlert("Product name is required.", Alert.AlertType.WARNING);
        } else if (tfCostPrice.getText().isEmpty() || tfCostPrice.getText().equals("")) {
            CustomAlert.showStyledAlert("Product name is required.", Alert.AlertType.WARNING);
        } else if (!DefaultAPI.isDouble(tfCostPrice.getText())) {
            CustomAlert.showStyledAlert("Product name is required.", Alert.AlertType.WARNING);
        } else if (!tfDiscount.getText().isEmpty()) {
            if (!DefaultAPI.isDouble(tfDiscount.getText())) {
                CustomAlert.showStyledAlert("Product name is required.", Alert.AlertType.WARNING);
            }
        } else if (tfMeasure.getText().isEmpty() || tfMeasure.getText().equals("")) {
            CustomAlert.showStyledAlert("Product name is required.", Alert.AlertType.WARNING);
        } else {
            status = true;
        }
        return status;
    }

    private void loadComboBoxData() {
        loadBrandList();
    }

    private void loadBrandList() {
        try {
            JPATransaction.runInTransaction((em) -> {
                CriteriaBuilder cBuilder = em.getCriteriaBuilder();
                CriteriaQuery<Brand> cQuery = cBuilder.createQuery(Brand.class);
                Root<Brand> brandTable = cQuery.from(Brand.class);
                cQuery.orderBy(cBuilder.asc(brandTable.get("brand")));
                List<Brand> brandList = em.createQuery(cQuery).getResultList();

                // add items to comboBox
                ComboBoxUtils.configureComboBox(cbBrand, brandList, Brand::getBrand);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
