package com.busbooking.controllers;

import com.busbooking.dao.SeatDAO;
import com.busbooking.models.Seat;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.util.List;

public class SeatSelectionController {

    @FXML
    private Label routeLabel;

    @FXML
    private GridPane seatGrid;

    @FXML
    private Label selectedSeatLabel;

    @FXML
    private Button nextButton;

    private String selectedSeat = null;
    private SeatDAO seatDAO;

    public SeatSelectionController() {
        seatDAO = new SeatDAO(); // Initialize DAO for seat availability
    }

    // Modified method to accept LocalDate and double
    public void setBusDetails(String from, String to, LocalDate date, String time, double fare) {
        // Set route details in the UI
        routeLabel.setText("From: " + from + " To: " + to + " | Date: " + date + " | Time: " + time + " | Fare: " + fare);
        generateSeatLayout();
    }

    private void generateSeatLayout() {
        List<Seat> seats = seatDAO.getSeats(); // Get all available seats from the database

        // Loop to create seat buttons for each row
        for (int row = 1; row <= 10; row++) {
            for (int col = 1; col <= (row == 10 ? 5 : 4); col++) {
                String seatNumber = row + String.valueOf((char) ('A' + col - 1)); // Create seat name e.g. 1A, 1B, etc.
                Button seatButton = new Button(seatNumber);

                // Find the seat status from the database
                Seat seat = getSeatByNumber(seats, seatNumber);
                if (seat != null && "Booked".equals(seat.getSeatStatus())) {
                    seatButton.setStyle("-fx-background-color: lightgray; -fx-pref-width: 50px;");
                    seatButton.setDisable(true); // Disable booked seats
                } else {
                    seatButton.setStyle("-fx-background-color: white; -fx-pref-width: 50px;");
                    seatButton.setOnAction(event -> selectSeat(seatButton)); // Set the button action
                }

                seatGrid.add(seatButton, col - 1, row - 1); // Add seat button to the grid
            }
        }
    }

    private Seat getSeatByNumber(List<Seat> seats, String seatNumber) {
        for (Seat seat : seats) {
            if (seat.getSeatNumber().equals(seatNumber)) {
                return seat;
            }
        }
        return null;
    }

    @FXML
    public void selectSeat(Button seatButton) {
        // Reset previous selection
        if (selectedSeat != null) {
            Button previousSeatButton = (Button) seatGrid.lookup("#" + selectedSeat);
            if (previousSeatButton != null) {
                previousSeatButton.setStyle("-fx-background-color: white; -fx-pref-width: 50px;");
            }
        }

        // Mark the new selection
        selectedSeat = seatButton.getText();
        seatButton.setStyle("-fx-background-color: yellow; -fx-pref-width: 50px;");
        selectedSeatLabel.setText("Selected Seat: " + selectedSeat);

        // Enable the "Next" button
        nextButton.setDisable(false);
    }

    @FXML
    public void goToNextScreen() {
        // Logic to go to the next screen after seat selection
        System.out.println("Proceeding to the next screen...");
    }
}
