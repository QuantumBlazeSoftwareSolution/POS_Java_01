package com.qb.app.controllers;

import com.qb.app.model.DefaultAPI;
import com.qb.app.model.JpaUtil;
import com.qb.app.model.entity.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.HibernateException;

public class CashierSessionController implements Initializable {

    @FXML
    private Button signInMessage;
    @FXML
    private Button signOffMessage;
    @FXML
    private TextField tfSignInUsername;
    @FXML
    private PasswordField tfSignInPassword;
    @FXML
    private TextField tfSignInPettyCash;
    @FXML
    private TextField tfSignOffUsername;
    @FXML
    private PasswordField tfSignOffPassword;
    @FXML
    private TextField tfSignOffCollection;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignOff;

    private static boolean isSignIn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getSessionDetails();
    }

    private void getSessionDetails() {
        EntityManager em = null;
        EntityTransaction transaction = null;

        try {
            em = JpaUtil.getEntityManager(); // Your utility method for getting EntityManager
            transaction = em.getTransaction();
            transaction.begin();

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Session> criteriaQuery = criteriaBuilder.createQuery(Session.class);
            Root<Session> sessionTable = criteriaQuery.from(Session.class);

            LocalDate today = LocalDate.now(); // For '2025-04-27', you can use LocalDate.of(2025, 4, 27);

            // Build Predicate (where DATE(day_in_time) = today)
            Predicate predicate = criteriaBuilder.equal(
                    criteriaBuilder.function("DATE", Date.class, sessionTable.get("dayInTime")),
                    java.sql.Date.valueOf(today)
            );

            criteriaQuery.select(sessionTable).where(predicate);

            try {
                Session sessionsToday = em.createQuery(criteriaQuery).getSingleResult();

                if (sessionsToday.getStatus().equals("OFF")) { // If record status was OFF
                    signInMessage.setText("Day Completed.");
                    signOffMessage.setText("Day Completed.");
                } else { // If record status was ON
                    signInMessage.setText("Already Sign In for Today.");
                    signOffMessage.setText("Waiting for Sign OFF.");
                    isSignIn = true;
                }
            } catch (Exception e) {
                signInMessage.setText("Waiting for Sign In.");
                signOffMessage.setText("Sign OFF is not activated.");
            }

        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Error during login: " + e.getMessage());
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @FXML
    private void handleActionEvent(ActionEvent event) {
        if (event.getSource() == btnSignIn) {
            sessionSignIn();
        } else if (event.getSource() == btnSignIn) {
            sessionSignOff();
        }
    }

    private void sessionSignIn() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);

        // check if there have a Sign IN for today
        if (!isSignIn) { // if not have a record for today
            if (tfSignInUsername.getText().isEmpty() || tfSignInUsername.getText().equals("")) {
                alert.setContentText("Username cannot be empty!");
            } else if (tfSignInPassword.getText().isEmpty() || tfSignInPassword.getText().equals("")) {
                alert.setContentText("Password cannot be empty!");
            } else if (tfSignInPettyCash.getText().isEmpty() || tfSignInPettyCash.getText().equals("")) {
                alert.setContentText("Petty Cash cannot be empty!");
            } else if (!DefaultAPI.isInteger(tfSignInPettyCash.getText())) {
                alert.setContentText("Invalid Cash Amount!");
            }
        } else {
            System.out.println("Going to the Else block");
        }
    }

    private void sessionSignOff() {

    }

}
