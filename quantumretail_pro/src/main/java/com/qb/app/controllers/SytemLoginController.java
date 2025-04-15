package com.qb.app.controllers;

import com.qb.app.App;
import com.qb.app.model.InterfaceAction;
import com.qb.app.model.InterfaceMortion;
import com.qb.app.model.PasswordEncryption;
import com.qb.app.model.SVGIconGroup;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class SytemLoginController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    //    <editor-fold desc="FXML init component" defaultstate="collapsed">
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;
    @FXML
    private Group iconExit;
    @FXML
    private AnchorPane root;
    @FXML
    private Circle quantumBlazeIcon;
    @FXML
    private Group iconUser;
    //    </editor-fold>

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setMouseEvent();
        setInitialState();
        setQBImage();
    }

    @FXML
    private void handleSystemLogin(ActionEvent event) {
        if (event.getSource() == btnLogin) {
            systemLogin();
        } else if (event.getSource() == btnExit) {
            InterfaceAction.closeWindow(btnExit);
        }
    }

    private void systemLogin() {
        try {
            if (tfUsername.getText().equals("c")) {
                String hashPassword = PasswordEncryption.hashPassword(tfPassword.getText());
                System.out.println("Encrypted: " + hashPassword);
                System.out.println("Validation for Vheshan37: " + PasswordEncryption.verifyPassword(hashPassword, tfPassword.getText()));
                App.setRoot("panelCashier");
            } else if (tfUsername.getText().equals("a")) {
                App.setRoot("panelAdmin");
            } else if (tfUsername.getText().equals("d")) {
                App.setRoot("panelDeveloper");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Session session = HibernateUtil.getSessionFactory().openSession();
//        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
//        Root<Employee> entity = query.from(Employee.class);
//
//        // WHERE name='Vihanga'
//        query.select(entity).where(criteriaBuilder.equal(entity.get("name"), "Vihanga"));
//
//        List<Employee> employeeList = session.createQuery(query).getResultList();
//        System.out.println("Employee Count: " + employeeList.size());
//        for (Employee employee : employeeList) {
//            System.out.println(employee.getName() + " - " + employee.getUsername());
//        }
    }

    private void setInitialState() {
        setIcons();
        Rectangle clip = new Rectangle(root.getPrefWidth(), root.getPrefHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        root.setClip(clip);
    }

    private void setIcons() {
        iconUser.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/users-solid.svg"));
        iconExit.getChildren().add(new SVGIconGroup("/com/qb/app/assets/icons/exit-solid.svg"));
    }

    private void setMouseEvent() {
        InterfaceMortion interfaceMortion = new InterfaceMortion();
        interfaceMortion.enableDrag(root);
    }

    private void setQBImage() {
        Image image = new Image(getClass().getResource("/com/qb/app/assets/images/QB_LOGO.png").toExternalForm());
        quantumBlazeIcon.setFill(new ImagePattern(image));
    }
}
