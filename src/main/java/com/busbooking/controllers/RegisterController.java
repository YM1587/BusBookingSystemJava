package com.busbooking.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.busbooking.models.Passenger;
import com.busbooking.services.PassengerService;
import com.busbooking.utils.PasswordUtil;

import java.io.IOException;

public class RegisterController {
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorLabel;
    @FXML private Button registerButton;

    private final PassengerService passengerService = new PassengerService();

    @FXML
    private void handleRegister() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Input Validation
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            errorLabel.setText("All fields are required!");
            return;
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            errorLabel.setText("Invalid email format!");
            return;
        }
        if (!phone.matches("\\d{10,15}")) {
            errorLabel.setText("Invalid phone number! Must be 10-15 digits.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            errorLabel.setText("Passwords do not match!");
            return;
        }
        if (passengerService.emailExists(email)) {
            errorLabel.setText("Email already registered!");
            return;
        }

        // Hash the password before storing
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Create a new Passenger object
        Passenger passenger = new Passenger();
        passenger.setFirstName(firstName);
        passenger.setLastName(lastName);
        passenger.setEmail(email);
        passenger.setPhoneNumber(phone);
        passenger.setPasswordHash(hashedPassword);
        passenger.setRole("Passenger"); // Default role

        // Save to the database
        if (passengerService.registerPassenger(passenger)) {
            showAlert("Registration Successful", "You can now log in.");
            navigateTo("/views/login.fxml", "Login");
        } else {
            errorLabel.setText("Registration failed. Try again.");
        }
    }

    @FXML
    private void handleLoginRedirect() {
        navigateTo("/views/login.fxml", "Login");
    }

    private void navigateTo(String fxmlPath, String title) {
        try {
            Stage stage = (Stage) registerButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(title);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void initialize() {
        if (registerButton != null) {
            registerButton.setOnAction(event -> handleRegister());
        } else {
            System.out.println("registerButton is null!");
        }
    }





}
