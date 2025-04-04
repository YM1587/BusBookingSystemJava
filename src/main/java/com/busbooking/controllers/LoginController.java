package com.busbooking.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.busbooking.models.Passenger;
import com.busbooking.services.PassengerService;
import com.busbooking.utils.SessionManager;
//import javafx.stage.Window;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.Node;

public class LoginController {
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;


    @FXML
    private void initialize() {
        loginButton.setOnAction(this::handleLogin);  // Now loginButton is accessed
    }


    private final PassengerService passengerService = new PassengerService();

    @FXML
    private void handleLogin(ActionEvent event) {
        String email = emailField.getText();
        String password = passwordField.getText();

        Passenger passenger = passengerService.authenticate(email, password);

        if (passenger != null) {
            // Store in session
            SessionManager.setLoggedInUser(passenger);
            navigateTo("/views/dashboard.fxml", "Dashboard", event);
        } else {
            showAlert();
        }
    }


    @FXML
    private void goToRegister(ActionEvent event) {
        navigateTo("/views/register.fxml", "Register", event);
    }

    private void navigateTo(String fxmlPath, String title, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            if (loader.getLocation() == null) {
                throw new IOException("FXML file not found: " + fxmlPath);
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load(), 600, 500);

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Navigation error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText("Authentication Error");
        alert.setContentText("Invalid email or password. Please try again.");
        alert.showAndWait();
    }
}
