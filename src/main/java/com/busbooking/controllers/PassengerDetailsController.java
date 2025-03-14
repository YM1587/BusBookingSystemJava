package com.busbooking.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class PassengerDetailsController {

    @FXML
    private Label routeLabel;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField idNumberField;

    @FXML
    private TextField mobileNumberField;

    @FXML
    private Label errorLabel;

    @FXML
    private Button proceedButton;

    private String fromCity;
    private String toCity;
    private LocalDate departureDate;
    private String departureTime;
    private double fare;
    private String selectedSeat;

    public void setPassengerDetails(String from, String to, LocalDate date, String time, double fare, String seat) {
        this.fromCity = from;
        this.toCity = to;
        this.departureDate = date;
        this.departureTime = time;
        this.fare = fare;
        this.selectedSeat = seat;

        // Update route details UI
        routeLabel.setText(from + " → " + to + " | " + date + " | " + time + " | Seat: " + seat);

        // Add listener to validate form
        addFormValidation();
    }

    private void addFormValidation() {
        firstNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        lastNameField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        idNumberField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());
        mobileNumberField.textProperty().addListener((observable, oldValue, newValue) -> validateForm());

        proceedButton.setOnAction(event -> navigateToPayment());
    }

    private void validateForm() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String idNumber = idNumberField.getText().trim();
        String mobileNumber = mobileNumberField.getText().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || idNumber.isEmpty() || mobileNumber.isEmpty()) {
            errorLabel.setText("All fields are required.");
            proceedButton.setDisable(true);
            return;
        }

        if (!idNumber.matches("\\d{6,10}")) {
            errorLabel.setText("ID Number must be 6-10 digits.");
            proceedButton.setDisable(true);
            return;
        }

        if (!mobileNumber.matches("\\d{10}")) {
            errorLabel.setText("Mobile Number must be 10 digits.");
            proceedButton.setDisable(true);
            return;
        }

        errorLabel.setText("");
        proceedButton.setDisable(false);
    }

    private void navigateToPayment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/payment.fxml"));
            Parent root = loader.load();

            // Pass booking details using static variables in PaymentController
            PaymentController.from = fromCity;
            PaymentController.to = toCity;
            PaymentController.date = departureDate.toString();
            PaymentController.time = departureTime;
            PaymentController.ticketCount = 1; // Assuming 1 seat per booking
            PaymentController.totalAmount = fare;

            Stage stage = (Stage) proceedButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
