package com.busbooking.controllers;

import com.busbooking.dao.PassengerDAO;
import com.busbooking.models.Passenger;
import com.busbooking.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

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
        // Fetch logged-in passenger by email (you can replace this with your actual session management)
        String loggedInEmail = SessionManager.getLoggedInEmail(); // Assumed helper
        PassengerDAO passengerDAO = new PassengerDAO();
        currentPassenger = passengerDAO.getPassengerByEmail(loggedInEmail);

        if (currentPassenger != null) {
            nameLabel.setText(currentPassenger.getFirstName() + " " + currentPassenger.getLastName());
            idLabel.setText(currentPassenger.getIdNumber());
            phoneLabel.setText(currentPassenger.getMobileNumber());
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
        // TODO: Navigate to payment screen, pass necessary info
        System.out.println("Proceeding to payment...");
    }
}
