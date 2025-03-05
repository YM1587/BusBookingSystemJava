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

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton, registerButton;

    private PassengerService passengerService = new PassengerService();

    @FXML
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        Passenger passenger = passengerService.authenticate(email, password);

        if (passenger != null) {
            // Store in session
            SessionManager.setLoggedInUser(passenger);
            navigateTo("/views/dashboard.fxml", "Dashboard");
        } else {
            showAlert("Login Failed", "Invalid email or password.");
        }
    }

    @FXML
    private void handleRegisterRedirect() {
        navigateTo("/views/register.fxml", "Register");
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            Stage stage = (Stage) loginButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
