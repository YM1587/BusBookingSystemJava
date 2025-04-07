package com.busbooking.controllers;

import com.busbooking.dao.SeatDAO;
import com.busbooking.models.Seat;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

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

    public void setBusDetails(String from, String to, LocalDate date, String time, double fare) {
        routeLabel.setText("From: " + from + " To: " + to + " | Date: " + date + " | Time: " + time + " | Fare: " + fare);
        generateSeatLayout();
    }

    private void generateSeatLayout() {
        List<Seat> seats = seatDAO.getSeats(); // Get all available seats from the database
        seatGrid.getChildren().clear(); // Clear any previous content

        for (int row = 1; row <= 10; row++) {
            int colIndex = 0;

            for (int col = 1; col <= (row == 10 ? 5 : 4); col++) {
                // Add spacer between B and C
                if (col == 3) {
                    Region spacer = new Region();
                    spacer.setMinWidth(20);
                    seatGrid.add(spacer, colIndex++, row - 1);
                }

                String seatNumber = row + String.valueOf((char) ('A' + col - 1));
                Button seatButton = new Button(seatNumber);
                seatButton.setPrefWidth(50);

                Seat seat = getSeatByNumber(seats, seatNumber);
                if (seat != null && "Booked".equals(seat.getSeatStatus())) {
                    seatButton.setStyle("-fx-background-color: lightgray;");
                    seatButton.setDisable(true);
                } else {
                    seatButton.setStyle("-fx-background-color: white;");
                    seatButton.setOnAction(event -> selectSeat(seatButton));
                }

                seatGrid.add(seatButton, colIndex++, row - 1);
            }
        }

        seatGrid.setPadding(new Insets(10));
    }

    private Seat getSeatByNumber(List<Seat> seats, String seatNumber) {
        return seats.stream()
                .filter(seat -> seat.getSeatNumber().equals(seatNumber))
                .findFirst()
                .orElse(null);
    }

    private Button getButtonBySeatNumber(String seatNumber) {
        for (var node : seatGrid.getChildren()) {
            if (node instanceof Button button && button.getText().equals(seatNumber)) {
                return button;
            }
        }
        return null;
    }

    @FXML
    public void selectSeat(Button seatButton) {
        if (selectedSeat != null) {
            Button previousSeatButton = getButtonBySeatNumber(selectedSeat);
            if (previousSeatButton != null) {
                previousSeatButton.setStyle("-fx-background-color: white;");
            }
        }

        selectedSeat = seatButton.getText();
        seatButton.setStyle("-fx-background-color: yellow;");
        selectedSeatLabel.setText("Selected Seat: " + selectedSeat);
        nextButton.setDisable(false);
    }

    @FXML
    public void goToNextScreen() {
        System.out.println("Proceeding to the next screen with seat: " + selectedSeat);
        // Add navigation logic here
    }
}
