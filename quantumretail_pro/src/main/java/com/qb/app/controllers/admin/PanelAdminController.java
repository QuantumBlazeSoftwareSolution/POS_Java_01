package com.qb.app.controllers.admin;

import com.qb.app.App;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.SVGIconGroup;
import com.qb.app.model.getLogger;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PanelAdminController implements Initializable {

    // <editor-fold desc="FXML init component" defaultstate="collapsed">
    @FXML
    private AnchorPane root;
    @FXML
    private BorderPane mainBorderLayout;
    @FXML
    private BorderPane leftSideMenu;
    @FXML
    private Circle systemLogo;
    @FXML
    private Button btnDashboard;
    @FXML
    private Group iconDashboard;
    @FXML
    private Button btnEmployee;
    @FXML
    private Group iconEmployee;
    @FXML
    private VBox subMenuEmployee;
    @FXML
    private HBox btnEmpOverview;
    @FXML
    private Group iconEmpOverview;
    @FXML
    private HBox btnEmpRegistration;
    @FXML
    private Group iconEmpRegistration;
    @FXML
    private HBox btnEmpManagement;
    @FXML
    private Group iconEmpManagement;
    @FXML
    private HBox btnEmpRoleManagement;
    @FXML
    private Group iconEmpRoleManagement;
    @FXML
    private Button btnProduct;
    @FXML
    private Group iconProduct;
    @FXML
    private VBox subMenuProduct;
    @FXML
    private HBox btnProductOverview;
    @FXML
    private Group iconProductOverview;
    @FXML
    private HBox btnProductAnalytics;
    @FXML
    private Group iconProductAnalytics;
    @FXML
    private HBox btnProductRegistration;
    @FXML
    private Group iconProductRegistration;
    @FXML
    private HBox btnProductManagement;
    @FXML
    private Group iconProductManagement;
    @FXML
    private HBox btnProductBrandManagement;
    @FXML
    private Group iconBrandManagement;
    @FXML
    private Button btnDiscount;
    @FXML
    private Group iconDiscount;
    @FXML
    private Button btnInventory;
    @FXML
    private Group iconInventory;
    @FXML
    private VBox subMenuInventory;
    @FXML
    private HBox btnInventoryGRN;
    @FXML
    private Group iconInventoryGrn;
    @FXML
    private HBox btnInventoryDistribute;
    @FXML
    private Group iconInventoryDistribute;
    @FXML
    private HBox btnInventoryDamageItem;
    @FXML
    private Group iconInventoryDamageReturn;
    @FXML
    private HBox btnInventoryLocationReturn;
    @FXML
    private Group iconInventoryLocationReturn;
    @FXML
    private HBox btnInventoryStockAdjustment;
    @FXML
    private Group iconInventoryStockAdjustment;
    @FXML
    private HBox btnInventoryLocationManagement;
    @FXML
    private Group iconInventoryLocationManagement;
    @FXML
    private Button btnSupplyManagement;
    @FXML
    private Group iconSupplyManagement;
    @FXML
    private VBox subMenuSupply;
    @FXML
    private HBox btnSupplyCompanyOverview;
    @FXML
    private Group iconCompanyOverview;
    @FXML
    private HBox btnSupplyCompanyManagement;
    @FXML
    private Group iconCompanyManagement;
    @FXML
    private HBox btnSupplySupplierManagement;
    @FXML
    private Group iconSupplierManagement;
    @FXML
    private HBox btnSupplyOrder;
    @FXML
    private Group iconSupplyOrder;
    @FXML
    private HBox btnSupplyDamageReturn;
    @FXML
    private Group iconSupplyDamage;
    @FXML
    private Button btnCustomer;
    @FXML
    private Group iconCustomer;
    @FXML
    private Button btnReports;
    @FXML
    private Group iconReport;
    @FXML
    private VBox subMenuReport;
    @FXML
    private HBox btnReportBIN;
    @FXML
    private Group iconReportBIN;
    @FXML
    private HBox btnReportCashWithdrawal;
    @FXML
    private Group iconReportCashWithdrawal;
    @FXML
    private HBox btnReportCloseSale;
    @FXML
    private Group iconReportCloseSale;
    @FXML
    private HBox btnReportCustomer;
    @FXML
    private Group iconReportCustomer;
    @FXML
    private HBox btnReportDamage;
    @FXML
    private Group iconReportDamage;
    @FXML
    private HBox btnReportDetailSale;
    @FXML
    private Group iconReportSale1;
    @FXML
    private HBox btnReportSummarySale;
    @FXML
    private Group iconReportSale2;
    @FXML
    private HBox btnReportProductSale;
    @FXML
    private Group iconReportSale3;
    @FXML
    private HBox btnReportDistribute;
    @FXML
    private Group iconReportDistribute;
    @FXML
    private HBox btnReportGRN;
    @FXML
    private Group iconReportGRN;
    @FXML
    private HBox btnReportLocationReturn;
    @FXML
    private Group iconReportLocationReturn;
    @FXML
    private HBox btnReportProductList;
    @FXML
    private Group iconReportProductList;
    @FXML
    private HBox btnReportProfit;
    @FXML
    private Group iconReportProfit;
    @FXML
    private HBox btnReportSession;
    @FXML
    private Group iconReportSession;
    @FXML
    private HBox btnReportStockBalance;
    @FXML
    private Group iconReportStockBalance;
    @FXML
    private Button btnExit;
    @FXML
    private Group iconExit;
    @FXML
    private BorderPane contentBorder;
    // </editor-fold>

    // <editor-fold desc="Initial Variables" defaultstate="collapsed">
    private boolean isMenuCollapsed = false;
    private Admin_top_panelController controller;
    // </editor-fold>
    @FXML
    private HBox btnInventoryExpireItems;
    @FXML
    private Group iconInventoryLocationManagement1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setIcons();
        setInitialState();
        setSystemLogo();
        setSubMenuState();
    }

    private void subMenuToggle(VBox subMenu) {
        subMenu.setVisible(!subMenu.isVisible());
        subMenu.setManaged(!subMenu.isManaged());
    }

    @FXML
    private void handleActionButtons(ActionEvent event) {
        if (event.getSource() == btnEmployee) {
            subMenuToggle(subMenuEmployee);
            setSubMenuState(subMenuEmployee);
        } else if (event.getSource() == btnProduct) {
            subMenuToggle(subMenuProduct);
            setSubMenuState(subMenuProduct);
        } else if (event.getSource() == btnInventory) {
            subMenuToggle(subMenuInventory);
            setSubMenuState(subMenuInventory);
        } else if (event.getSource() == btnSupplyManagement) {
            subMenuToggle(subMenuSupply);
            setSubMenuState(subMenuSupply);
        } else if (event.getSource() == btnReports) {
            subMenuToggle(subMenuReport);
            setSubMenuState(subMenuReport);
        } else if (event.getSource() == btnExit) {
            try {
                App.setRoot("sytemLogin");
            } catch (Exception e) {
                e.printStackTrace();
                getLogger.logger().warning(e.toString());
            }
        } else if (event.getSource() == btnDiscount) {
            loadCenterPanel("admin/discount");
        } else if (event.getSource() == btnDashboard) {
            loadCenterPanel("admin/adminDashboard");
        } else if (event.getSource() == btnCustomer) {
//            loadCenterPanel("admin/customer");
            CustomAlert.showStyledAlert(
                    root,
                    "You do not have permission to access this page.\n\nPlease contact your system administrator to request access.",
                    "Access Restricted",
                    Alert.AlertType.WARNING
            );
        }
    }

    private void setIcons() {
        iconCustomer.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-customer.svg"));
        iconDashboard.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/dashboard-solid.svg"));
        iconDiscount.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-discount.svg"));
        iconEmployee.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-employee.svg"));
        iconExit.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/cashier-exit-solid.svg"));
        iconInventory.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-inventory.svg"));
        iconProduct.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-product.svg"));
        iconReport.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-report.svg"));
        iconSupplyManagement.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/admin-supply-management.svg"));

        iconEmpOverview.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/employee-overview.svg"));
        iconEmpRegistration.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/employee-registration.svg"));
        iconEmpManagement.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/employee-management.svg"));
        iconEmpRoleManagement.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/employee-role-management.svg"));

        iconProductOverview.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/product-overview.svg"));
        iconProductAnalytics.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/product-analytics.svg"));
        iconProductRegistration.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/product-registration.svg"));
        iconProductManagement.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/product-management.svg"));
        iconBrandManagement.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/brand-management.svg"));

        iconInventoryGrn.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/inventory-grn.svg"));
        iconInventoryDistribute.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/inventory-distribute.svg"));
        iconInventoryDamageReturn.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/inventory-damage-item.svg"));
        iconInventoryLocationReturn.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/inventory-location-return.svg"));
        iconInventoryStockAdjustment.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/inventory-stock-adjustment.svg"));
        iconInventoryLocationManagement.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/inventory-location-management.svg"));

        iconCompanyOverview.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/companyOverview.svg"));
        iconCompanyManagement.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/companyManagement.svg"));
        iconSupplierManagement.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/supplierManagement.svg"));
        iconSupplyOrder.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/supplyOrder.svg"));
        iconSupplyDamage.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/supplyDamageReturn.svg"));

        iconReportBIN.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportCashWithdrawal.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportCloseSale.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportCustomer.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportDamage.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportDistribute.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportGRN.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportLocationReturn.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportProductList.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportProfit.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportSale1.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportSale2.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportSale3.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportSession.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
        iconReportStockBalance.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/reports.svg"));
    }

    public void toggleMenu() {
        if (isMenuCollapsed) {
            expandMenu();
        } else {
            collapseMenu();
        }
        isMenuCollapsed = !isMenuCollapsed; // Toggle the state
        double menuWidth = leftSideMenu.getWidth();
    }

    private void collapseMenu() {
        double menuWidth = leftSideMenu.getWidth();

        leftSideMenu.setMinWidth(0); // Ensure it can shrink properly
        leftSideMenu.setMaxWidth(menuWidth);

        // Create a TranslateTransition for the side menu
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), leftSideMenu);
        translateTransition.setToX(-menuWidth); // Move the menu to the left by its width

        // Create a Timeline to animate the width of the side menu
        Timeline widthTransition = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(leftSideMenu.prefWidthProperty(), 0)
                )
        );

        // Combine both transitions into a ParallelTransition
        ParallelTransition parallelTransition = new ParallelTransition(widthTransition, translateTransition);
        parallelTransition.setOnFinished(event -> leftSideMenu.setMaxWidth(0)); // Ensure it stays collapsed
        parallelTransition.play();
    }

    private void expandMenu() {
        double menuWidth = 250;

        leftSideMenu.setMaxWidth(menuWidth);

        // Create a TranslateTransition for the side menu
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(300), leftSideMenu);
        translateTransition.setToX(0); // Move the menu back to its original position

        // Create a Timeline to animate the width of the side menu
        Timeline widthTransition = new Timeline(
                new KeyFrame(Duration.millis(300),
                        new KeyValue(leftSideMenu.prefWidthProperty(), menuWidth)
                )
        );

        // Combine both transitions into a ParallelTransition
        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, widthTransition);
        parallelTransition.setOnFinished(event -> leftSideMenu.setMinWidth(menuWidth)); // Prevent it from resizing back to 140px
        parallelTransition.play();
    }

    private void setInitialState() {
        setDefaultPanel();
    }

    private void setDefaultPanel() {
        try {
            FXMLLoader dashboard = new FXMLLoader(getClass().getResource("/com/qb/app/admin/adminDashboard.fxml"));
            contentBorder.setCenter(dashboard.load());
            FXMLLoader admin_top_menu = new FXMLLoader(getClass().getResource("/com/qb/app/fxmlComponent/admin_top_panel.fxml"));
            contentBorder.setTop(admin_top_menu.load());
            controller = admin_top_menu.getController();
            controller.setPanelAdminController(this);
        } catch (IOException e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    private void changeCenterPanel(String fxml, String title) {
        try {
            FXMLLoader panel = new FXMLLoader(getClass().getResource(fxml));
            contentBorder.setCenter(panel.load());
        } catch (IOException e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    private void setSystemLogo() {
        Image image = new Image(getClass().getResource("/com/qb/app/assets/images/QB_LOGO.png").toExternalForm());
        systemLogo.setFill(new ImagePattern(image));
    }

    private void setSubMenuState() {
        for (VBox subMenu : getMenu()) {
            subMenu.setVisible(false);
            subMenu.setManaged(false);
        }
    }

    private VBox[] getMenu() {
        VBox[] subMenus = {subMenuEmployee, subMenuProduct, subMenuInventory, subMenuSupply, subMenuReport};
        return subMenus;
    }

    private void setSubMenuState(VBox excludeSubMenu) {
        for (VBox subMenu : getMenu()) {
            if (subMenu != excludeSubMenu) { // Skip the excluded submenu
                subMenu.setVisible(false);
                subMenu.setManaged(false);
            }
        }
    }

    @FXML
    private void handleSubMenuItems(MouseEvent event) {
        if (event.getSource() == btnEmpOverview) {
            loadCenterPanel("admin/employee/employee_overview");
        } else if (event.getSource() == btnEmpRegistration) {
            loadCenterPanel("admin/employee/employee_registration");
        } else if (event.getSource() == btnEmpManagement) {
            loadCenterPanel("admin/employee/employee_management");
        } else if (event.getSource() == btnEmpRoleManagement) {
//            loadCenterPanel("admin/employee/employee_role_management");
            showPermissionMessage();
        } else if (event.getSource() == btnProductAnalytics) {
            loadCenterPanel("admin/product/product_analytics");
        } else if (event.getSource() == btnProductBrandManagement) {
            loadCenterPanel("admin/product/product_brand_management");
        } else if (event.getSource() == btnProductManagement) {
            loadCenterPanel("admin/product/product_management");
        } else if (event.getSource() == btnProductOverview) {
            loadCenterPanel("admin/product/product_overview");
        } else if (event.getSource() == btnProductRegistration) {
            loadCenterPanel("admin/product/product_registration");
        } else if (event.getSource() == btnInventoryDamageItem) {
//            loadCenterPanel("admin/inventory/inventory_damage_item");
            showPermissionMessage();
        } else if (event.getSource() == btnInventoryDistribute) {
//            loadCenterPanel("admin/inventory/inventory_distribute");
            showPermissionMessage();
        } else if (event.getSource() == btnInventoryGRN) {
            loadCenterPanel("admin/inventory/inventory_grn");
        } else if (event.getSource() == btnInventoryLocationManagement) {
//            loadCenterPanel("admin/inventory/inventory_location_management");
            showPermissionMessage();
        } else if (event.getSource() == btnInventoryLocationReturn) {
//            loadCenterPanel("admin/inventory/inventory_location_return");
            showPermissionMessage();
        } else if (event.getSource() == btnInventoryStockAdjustment) {
            loadCenterPanel("admin/inventory/inventory_stock_adjustment");
        } else if (event.getSource() == btnInventoryExpireItems) {
            loadCenterPanel("fxmlPanel/ExpireTracking");
        } else if (event.getSource() == btnSupplyCompanyManagement) {
            loadCenterPanel("admin/supply/supply_company_management");
        } else if (event.getSource() == btnSupplyCompanyOverview) {
            loadCenterPanel("admin/supply/supply_company_overview");
        } else if (event.getSource() == btnSupplyDamageReturn) {
//            loadCenterPanel("admin/supply/supply_damage_return");
            showPermissionMessage();
        } else if (event.getSource() == btnSupplySupplierManagement) {
            loadCenterPanel("admin/supply/supply_supplier_management");
        } else if (event.getSource() == btnSupplyOrder) {
//            loadCenterPanel("admin/supply/supply_order");
            showPermissionMessage();
        } else if (event.getSource() == btnReportBIN) {
            loadCenterPanel("reportFXML/reportBIN");
        } else if (event.getSource() == btnReportCashWithdrawal) {
            loadCenterPanel("reportFXML/reportCashWithdrawal");
        } else if (event.getSource() == btnReportDistribute) {
            loadCenterPanel("reportFXML/reportDistribute");
        } else if (event.getSource() == btnReportGRN) {
            loadCenterPanel("reportFXML/reportGRN");
        } else if (event.getSource() == btnReportLocationReturn) {
            loadCenterPanel("reportFXML/reportLocationReturn");
        } else if (event.getSource() == btnReportProfit) {
            loadCenterPanel("reportFXML/reportProfit");
        } else if (event.getSource() == btnReportDetailSale) {
            loadCenterPanel("reportFXML/reportSaleDetail");
        } else if (event.getSource() == btnReportProductSale) {
            loadCenterPanel("reportFXML/reportSaleProduct");
        } else if (event.getSource() == btnReportSummarySale) {
            loadCenterPanel("reportFXML/reportSaleSummary");
        } else if (event.getSource() == btnReportStockBalance) {
            loadCenterPanel("reportFXML/reportStockBalance");
        }
    }

    private void loadCenterPanel(String fxml) {
        try {
            FXMLLoader panel = new FXMLLoader(getClass().getResource("/com/qb/app/" + fxml + ".fxml"));
            contentBorder.setCenter(panel.load());
        } catch (IOException e) {
            e.printStackTrace();
            getLogger.logger().warning(e.toString());
        }
    }

    private void showPermissionMessage() {
        CustomAlert.showStyledAlert(
                root,
                "You do not have permission to access this page.\n\nPlease contact your system administrator to request access.",
                "Access Restricted",
                Alert.AlertType.WARNING
        );
    }

}
