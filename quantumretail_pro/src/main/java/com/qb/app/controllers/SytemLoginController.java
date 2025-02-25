package com.qb.app.controllers;

import com.qb.app.model.HibernateUtil;
import com.qb.app.model.InterfaceMortion;
import com.qb.app.model.SVGIconLoader;
import com.qb.app.model.entity.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import org.hibernate.Session;

public class SytemLoginController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    //    <editor-fold desc="FXML init component">
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnExit;
    @FXML
    private SVGPath iconUsers;
    @FXML
    private SVGPath iconExit;
    @FXML
    private AnchorPane root;
    @FXML
    private Circle quantumBlazeIcon;
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
            Stage stage = (Stage) btnExit.getScene().getWindow();
            stage.close();
        }
    }

    private void systemLogin() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Employee> query = criteriaBuilder.createQuery(Employee.class);
        Root<Employee> entity = query.from(Employee.class);

        // WHERE name='Vihanga'
        query.select(entity).where(criteriaBuilder.equal(entity.get("name"), "Vihanga"));

        List<Employee> employeeList = session.createQuery(query).getResultList();
        System.out.println("Employee Count: " + employeeList.size());
        for (Employee employee : employeeList) {
            System.out.println(employee.getName() + " - " + employee.getUsername());
        }

        Employee employee = session.createQuery(query).getSingleResult();
    }

    private void setInitialState() {
        setIcons();
        Rectangle clip = new Rectangle(root.getPrefWidth(), root.getPrefHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        root.setClip(clip);
    }

    private void setIcons() {
        SVGIconLoader.loadSVGIcon(iconUsers, ("/com/qb/app/assets/icons/users-solid.svg"));
        SVGIconLoader.loadSVGIcon(iconExit, ("/com/qb/app/assets/icons/exit-solid.svg"));
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
