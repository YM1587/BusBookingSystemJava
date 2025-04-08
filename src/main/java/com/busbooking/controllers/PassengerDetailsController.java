package com.busbooking.controllers;

import com.busbooking.models.Passenger;
import com.busbooking.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class PassengerDetailsController {

    @FXML private Label nameLabel;
    @FXML private Label idLabel;
    @FXML private Label phoneLabel;
    @FXML private Label seatLabel;
    @FXML private Label fromLabel;
    @FXML private Label toLabel;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private Label fareLabel;
    @FXML private Button proceedButton;

    private String selectedSeat;
    private String from;
    private String to;
    private LocalDate date;
    private String time;
    private double fare;
    private int busId;

    private Passenger currentPassenger;

    public void initialize() {
        // Fetch logged-in passenger from session
        currentPassenger = SessionManager.getLoggedInUser();

        if (currentPassenger != null) {
            nameLabel.setText(currentPassenger.getFirstName() + " " + currentPassenger.getLastName());
            idLabel.setText(String.valueOf(currentPassenger.getPassengerId())); // Replaced getIdNumber()
            phoneLabel.setText(currentPassenger.getPhoneNumber()); // Replaced getMobileNumber()
        } else {
            System.out.println("No logged-in passenger found.");
        }
    }

    public void setBookingDetails(String selectedSeat, String from, String to, LocalDate date, String time, double fare, int busId) {
        this.selectedSeat = selectedSeat;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.fare = fare;
        this.busId = busId;

        seatLabel.setText(selectedSeat);
        fromLabel.setText(from);
        toLabel.setText(to);
        dateLabel.setText(date.toString());
        timeLabel.setText(time);
        fareLabel.setText(String.format("%.2f", fare));
    }


    @FXML
    public void handleProceedToPayment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/payment.fxml"));
            Parent root = loader.load();

            // Pass data to PaymentController
            PaymentController paymentController = loader.getController();
            paymentController.setPaymentDetails(
                    currentPassenger,
                    selectedSeat,
                    from,
                    to,
                    date,
                    time,
                    fare,
                    busId
            );

            // Set the new scene
            Stage stage = (Stage) proceedButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Payment");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to load payment.fxml");
        }
    }


}
