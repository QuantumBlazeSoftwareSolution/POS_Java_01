package com.qb.app.controllers.developer;

import com.qb.app.model.Config;
import com.qb.app.model.ConfigManager;
import com.qb.app.model.CustomAlert;
import com.qb.app.model.DefaultAPI;
import com.qb.app.model.PopUp;
import com.qb.app.model.getLogger;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DeveloperControlPanelController implements Initializable {

    // <editor-fold desc="FXML init component" defaultstate="collapsed">
    @FXML
    private Button btnUpdateSystem;
    @FXML
    private Button btnUpdateReport;
    @FXML
    private TextField tfTimePeriod;
    @FXML
    private TextField tfLicenseCharge;
    @FXML
    private TextField tfDeactivateDate;
    @FXML
    private Button btnUpdateLicense;
    @FXML
    private Button btnLicenseDateRevert;
    @FXML
    private Button btnRenewLicense;
    @FXML
    private TextField tfAdjustmentCount;
    @FXML
    private TextField tfTemporyChance;
    @FXML
    private Button btnUpdateAdjustment;
    @FXML
    private RadioButton systemInventory;
    @FXML
    private RadioButton systemMultiStock;
    @FXML
    private RadioButton systemExpireTracking;
    @FXML
    private RadioButton systemCashWithdrawal;
    @FXML
    private RadioButton systemCreditPayment;
    @FXML
    private RadioButton systemRefund;
    @FXML
    private RadioButton systemBillDiscount;
    @FXML
    private RadioButton systemProductDiscount;
    @FXML
    private RadioButton systemEmployeeManagement;
    @FXML
    private RadioButton reportBinCard;
    @FXML
    private RadioButton reportCashWithdrawal;
    @FXML
    private RadioButton reportCloseSale;
    @FXML
    private RadioButton reportCustomer;
    @FXML
    private RadioButton reportDamage;
    @FXML
    private RadioButton reportDistribute;
    @FXML
    private RadioButton reportGRN;
    @FXML
    private RadioButton reportLocationReturn;
    @FXML
    private RadioButton reportProductList;
    @FXML
    private RadioButton reportProfit;
    @FXML
    private RadioButton reportSaleDetail;
    @FXML
    private RadioButton reportSaleSummary;
    @FXML
    private RadioButton reportSaleProduct;
    @FXML
    private RadioButton reportSession;
    @FXML
    private RadioButton reportStockBalance;
    @FXML
    private RadioButton reportSupplyOrder;
    @FXML
    private RadioButton rbLifeTimeLicense;
    @FXML
    private AnchorPane root;
    // </editor-fold>

    private Config config;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadConfig();
    }

    private void loadConfig() {
        try {
            config = ConfigManager.loadConfig();
            loadTheContent();
        } catch (Exception e) {
            e.printStackTrace();
        getLogger.logger().warning(e.toString());
        }
    }

    private void executeWithVerification(Runnable action) {
        try {
            if (checkTheDeveloperVerification()) {
                action.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        getLogger.logger().warning(e.toString());
        }
    }

    private void loadTheContent() {
        if (this.config.license.isLicenseActive) {
            if (this.config.license.status.equals(this.config.license.licenseType.LIFETIME)) {
                btnUpdateLicense.setDisable(true);
                btnRenewLicense.setDisable(true);
                btnLicenseDateRevert.setDisable(true);
                rbLifeTimeLicense.setSelected(true);
            } else {
                tfTimePeriod.setText(String.valueOf(this.config.license.period));
                tfLicenseCharge.setText(String.valueOf(this.config.license.charge));
                getExpireDate();
                btnUpdateLicense.setText("Update License");
            }
        } else {
            tfTimePeriod.setPromptText("N/A");
            tfLicenseCharge.setPromptText("N/A");
            tfDeactivateDate.setPromptText("N/A");
        }

        tfAdjustmentCount.setText(String.valueOf(this.config.stock_adjustment.adjustment_count));
        tfTemporyChance.setText(String.valueOf(this.config.stock_adjustment.tempory_chance));

        getReportAccessValues();
        getSystemAccessValues();
    }

    private void getExpireDate() {
        if (this.config.license.expire_date != null && !this.config.license.expire_date.isEmpty()) {
            tfDeactivateDate.setText(String.valueOf(this.config.license.expire_date));
        }
    }

    private void getReportAccessValues() {
        reportBinCard.setSelected(this.config.report.bin);
        reportCashWithdrawal.setSelected(this.config.report.cash_withdrawal);
        reportCloseSale.setSelected(this.config.report.close_sale);
        reportCustomer.setSelected(this.config.report.customer);
        reportDamage.setSelected(this.config.report.damage);
        reportDistribute.setSelected(this.config.report.distribute);
        reportGRN.setSelected(this.config.report.grn);
        reportLocationReturn.setSelected(this.config.report.location_return);
        reportProductList.setSelected(this.config.report.product_list);
        reportProfit.setSelected(this.config.report.profit);
        reportSaleDetail.setSelected(this.config.report.sale_detail);
        reportSaleSummary.setSelected(this.config.report.sale_summary);
        reportSaleProduct.setSelected(this.config.report.sale_product);
        reportSession.setSelected(this.config.report.session);
        reportStockBalance.setSelected(this.config.report.stock_balance);
        reportSupplyOrder.setSelected(this.config.report.supplyOrder);
    }

    private void getSystemAccessValues() {
        systemBillDiscount.setSelected(this.config.system.bill_discount);
        systemCashWithdrawal.setSelected(this.config.system.cash_withdrawal);
        systemCreditPayment.setSelected(this.config.system.credit_payment);
        systemEmployeeManagement.setSelected(this.config.system.employee_management);
        systemExpireTracking.setSelected(this.config.system.expire_tracking);
        systemInventory.setSelected(this.config.system.inventory_management);
        systemMultiStock.setSelected(this.config.system.multi_stock);
        systemProductDiscount.setSelected(this.config.system.product_discount);
        systemRefund.setSelected(this.config.system.refund);
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnUpdateSystem) {
            executeWithVerification(this::updateSystemAccess);
        } else if (event.getSource() == btnUpdateReport) {
            executeWithVerification(this::updateReportAccess);
        } else if (event.getSource() == btnUpdateLicense) {
            executeWithVerification(this::updateLicense);
        } else if (event.getSource() == btnRenewLicense) {
            if (this.config.license.isLicenseActive && this.config.license.status.equals(this.config.license.licenseType.TRIAL)) {
                executeWithVerification(this::renewLicense);
            } else {
                CustomAlert.showStyledAlert(root, "License not activated yet. To continue this, you have to create license first.", "License Error", Alert.AlertType.WARNING);
            }
        } else if (event.getSource() == btnLicenseDateRevert) {
            if (this.config.license.isLicenseActive) {
                executeWithVerification(this::revertLicese);
            } else {
                CustomAlert.showStyledAlert(root, "License not activated yet. To continue this, you have to create license first.", "License Error", Alert.AlertType.WARNING);
            }
        } else if (event.getSource() == btnUpdateAdjustment) {
            executeWithVerification(this::updateStockAdjustment);
        }
    }

    private boolean checkTheDeveloperVerification() throws Exception {
        actionVerificationController[] ref = new actionVerificationController[1];
        PopUp.showPopupAndWait(
                "developer/developerActionVerification.fxml",
                root,
                this.root.getScene(),
                PopUp.PopupType.CENTERED_50_WIDTH,
                (actionVerificationController controller) -> {
                    ref[0] = controller;
                }
        );

        if (ref[0] != null && ref[0].isActionConfirmed()) {
            return true;
        } else {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            Stage errorAlertStage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
            errorAlertStage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));
            errorAlert.setTitle("Access Denied");
            errorAlert.setHeaderText("Developer Verification Failed");
            errorAlert.setContentText("Fail to verify you as a developer.");
            errorAlert.showAndWait();
            return false;
        }
    }

    private void updateSystemAccess() {
        this.config.system.bill_discount = systemBillDiscount.isSelected();
        this.config.system.cash_withdrawal = systemCashWithdrawal.isSelected();
        this.config.system.credit_payment = systemCreditPayment.isSelected();
        this.config.system.employee_management = systemEmployeeManagement.isSelected();
        this.config.system.expire_tracking = systemExpireTracking.isSelected();
        this.config.system.inventory_management = systemInventory.isSelected();
        this.config.system.multi_stock = systemMultiStock.isSelected();
        this.config.system.product_discount = systemProductDiscount.isSelected();
        this.config.system.refund = systemRefund.isSelected();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("System Configuration Update");
        alert.setHeaderText("Confirm System Access Changes");
        alert.setContentText("You are about to modify system access permissions.\n\n"
                + "These changes will affect all users immediately.\n"
                + "Do you want to proceed with these updates?");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));

        ButtonType updateButton = new ButtonType("Update Configuration", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(updateButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == updateButton) {
            try {
                ConfigManager.saveConfig(this.config);
                reloadConfig();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                Stage successAlertStage = (Stage) successAlert.getDialogPane().getScene().getWindow();
                successAlertStage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));
                successAlert.setTitle("Update Successful");
                successAlert.setHeaderText("System Configuration Updated");
                successAlert.setContentText("The system access permissions have been successfully updated.");
                successAlert.showAndWait();

                loadTheContent();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                Stage errorAlertStage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
                errorAlertStage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));
                errorAlert.setTitle("Update Failed");
                errorAlert.setHeaderText("Configuration Save Error");
                errorAlert.setContentText("Failed to save system configuration:\n" + e.getMessage());
                errorAlert.showAndWait();

                e.printStackTrace();
        getLogger.logger().warning(e.toString());
            }
        } else {
            loadTheContent();
        }
    }

    private void updateReportAccess() {
        this.config.report.bin = reportBinCard.isSelected();
        this.config.report.cash_withdrawal = reportCashWithdrawal.isSelected();
        this.config.report.close_sale = reportCloseSale.isSelected();
        this.config.report.customer = reportCustomer.isSelected();
        this.config.report.damage = reportDamage.isSelected();
        this.config.report.distribute = reportDistribute.isSelected();
        this.config.report.grn = reportGRN.isSelected();
        this.config.report.location_return = reportLocationReturn.isSelected();
        this.config.report.product_list = reportProductList.isSelected();
        this.config.report.profit = reportProfit.isSelected();
        this.config.report.sale_detail = reportSaleDetail.isSelected();
        this.config.report.sale_summary = reportSaleSummary.isSelected();
        this.config.report.sale_product = reportSaleProduct.isSelected();
        this.config.report.session = reportSession.isSelected();
        this.config.report.stock_balance = reportStockBalance.isSelected();
        this.config.report.supplyOrder = reportSupplyOrder.isSelected();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Report Configuration Update");
        alert.setHeaderText("Confirm Report Access Changes");
        alert.setContentText("You are about to modify report access permissions.\n\n"
                + "These changes will affect all users immediately.\n"
                + "Do you want to proceed with these updates?");

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));

        ButtonType updateButton = new ButtonType("Update Configuration", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(updateButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == updateButton) {
            try {
                ConfigManager.saveConfig(this.config);
                reloadConfig();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                Stage successAlertStage = (Stage) successAlert.getDialogPane().getScene().getWindow();
                successAlertStage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));
                successAlert.setTitle("Update Successful");
                successAlert.setHeaderText("Report Configuration Updated");
                successAlert.setContentText("The report access permissions have been successfully updated.");
                successAlert.showAndWait();

                loadTheContent();
            } catch (Exception e) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                Stage errorAlertStage = (Stage) errorAlert.getDialogPane().getScene().getWindow();
                errorAlertStage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));
                errorAlert.setTitle("Update Failed");
                errorAlert.setHeaderText("Configuration Save Error");
                errorAlert.setContentText("Failed to save report configuration:\n" + e.getMessage());
                errorAlert.showAndWait();

                e.printStackTrace();
        getLogger.logger().warning(e.toString());
            }
        } else {
            loadTheContent();
        }
    }

    private void updateLicense() {
        if (rbLifeTimeLicense.isSelected()) {
            this.config.license.status = this.config.license.licenseType.LIFETIME;
            this.config.license.charge = 0;
            this.config.license.period = 0;
            this.config.license.expire_date = "";

            saveLicense();
        } else if (isLicenseEntryValid()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                if (!this.config.license.isLicenseActive) {
                    LocalDate initDate = LocalDate.now();
                    LocalDate expiryDate = initDate.plusMonths(Integer.parseInt(tfTimePeriod.getText()));
                    this.config.license.init_date = initDate.format(formatter);
                    this.config.license.expire_date = expiryDate.format(formatter);
                    this.config.license.previous_expire_date = expiryDate.format(formatter);
                } else {
                    LocalDate currentExpireDate = LocalDate.parse(this.config.license.previous_expire_date, formatter);
                    LocalDate previousExpireDate = currentExpireDate.minusMonths(this.config.license.previous_period);
                    LocalDate newExpireDate = previousExpireDate.plusMonths(Integer.parseInt(tfTimePeriod.getText()));
                    this.config.license.expire_date = newExpireDate.format(formatter);
                    this.config.license.previous_expire_date = newExpireDate.format(formatter);
                }
                this.config.license.charge = Double.parseDouble(tfLicenseCharge.getText());
                this.config.license.period = Integer.parseInt(tfTimePeriod.getText());
                this.config.license.previous_period = Integer.parseInt(tfTimePeriod.getText());
                this.config.license.status = "trial";

                saveLicense();
            } catch (NumberFormatException e) {
                e.printStackTrace();
        getLogger.logger().warning(e.toString());
            }
        }
    }

    private void saveLicense() {
        try {
            this.config.license.isLicenseActive = true;
            ConfigManager.saveConfig(this.config);
            reloadConfig();
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            Stage successAlertStage = (Stage) successAlert.getDialogPane().getScene().getWindow();
            successAlertStage.getIcons().add(new Image(getClass().getResource("/com/qb/app/assets/images/logo.png").toExternalForm()));
            successAlert.setTitle("License Update Successful");
            successAlert.setHeaderText("License Updated");
            successAlert.setContentText("The License have been successfully updated.");
            successAlert.showAndWait();
            btnUpdateLicense.setDisable(true);
            btnRenewLicense.setDisable(true);
            btnLicenseDateRevert.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
        getLogger.logger().warning(e.toString());
        }
    }

    private void reloadConfig() {
        loadConfig();
    }

    private boolean isLicenseEntryValid() {
        if (tfTimePeriod.getText().isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "Time period is a required field. Please enter a valid period in months.",
                    "Validation Error",
                    Alert.AlertType.WARNING);
            tfTimePeriod.requestFocus();
            return false;
        }

        if (tfLicenseCharge.getText().isEmpty()) {
            CustomAlert.showStyledAlert(
                    root,
                    "License charge is a required field. Please enter a valid license charge.",
                    "Validation Error",
                    Alert.AlertType.WARNING);
            tfLicenseCharge.requestFocus();
            return false;
        }

        return true;
    }

    @FXML
    private void handleLifeTimeLicenseRadioButton(ActionEvent event) {
        if (rbLifeTimeLicense.isSelected()) {
            tfTimePeriod.setDisable(true);
            tfLicenseCharge.setDisable(true);
            tfTimePeriod.setText("");
            tfLicenseCharge.setText("");
            tfDeactivateDate.setText("");
        } else {
            tfTimePeriod.setDisable(false);
            tfLicenseCharge.setDisable(false);
        }
    }

    private void renewLicense() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentExpireDate = LocalDate.parse(this.config.license.expire_date, formatter);
        LocalDate newExpireDate = currentExpireDate.plusMonths(this.config.license.period);
        this.config.license.expire_date = newExpireDate.format(formatter);
        this.config.license.previous_expire_date = currentExpireDate.format(formatter);
        try {
            ConfigManager.saveConfig(this.config);
        } catch (Exception e) {
            e.printStackTrace();
        getLogger.logger().warning(e.toString());
        }
        getExpireDate();
    }

    private void revertLicese() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentExpireDate = LocalDate.parse(this.config.license.expire_date, formatter);
        LocalDate previousExpireDate = currentExpireDate.minusMonths(this.config.license.period);
        this.config.license.expire_date = previousExpireDate.format(formatter);
        this.config.license.previous_expire_date = previousExpireDate.minusMonths(this.config.license.period).format(formatter);

        try {
            ConfigManager.saveConfig(this.config);
        } catch (Exception e) {
            e.printStackTrace();
        getLogger.logger().warning(e.toString());
        }
        getExpireDate();
    }

    private void updateStockAdjustment() {
        if (DefaultAPI.isInteger(tfAdjustmentCount.getText())) {
            this.config.stock_adjustment.adjustment_count = Integer.parseInt(tfAdjustmentCount.getText());
        } else {
            CustomAlert.showStyledAlert(root, "Please enter valid stock adjustment count. Use digits.", "Invalid Value", Alert.AlertType.WARNING);
        }

        if (!tfTemporyChance.getText().isEmpty() && DefaultAPI.isInteger(tfTemporyChance.getText())) {
            this.config.stock_adjustment.tempory_chance = Integer.parseInt(tfTemporyChance.getText());
        } else {
            CustomAlert.showStyledAlert(root, "Please enter valid tempory adjustment count. Use digits.", "Invalid Value", Alert.AlertType.WARNING);
        }

        try {
            ConfigManager.saveConfig(this.config);
            CustomAlert.showStyledAlert(root, "Stock Adjustment Successfuly Updated.", "Success", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
        getLogger.logger().warning(e.toString());
        }
    }

}
