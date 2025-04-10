package com.busbooking.controllers;

import com.busbooking.dao.SeatDAO;
import com.busbooking.models.Seat;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

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

    // New fields to carry booking information
    private String from;
    private String to;
    private LocalDate date;
    private String time;
    private double fare;
    private int busId;

    public SeatSelectionController() {
        seatDAO = new SeatDAO(); // Initialize DAO for seat availability
    }

    public void setBusDetails(String from, String to, LocalDate date, String time, double fare, int busId) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.fare = fare;
        this.busId = busId;

        routeLabel.setText("From: " + from + " To: " + to + " | Date: " + date + " | Time: " + time + " | Fare: " + fare);
        generateSeatLayout();
    }

    private void generateSeatLayout() {
        // Fetch all seats for the given bus
        List<Seat> seats = seatDAO.getSeats();
        seatGrid.getChildren().clear(); // Clear any previous content

        // Iterate over the rows (1-9) and the last row (10)
        for (int row = 1; row <= 10; row++) {
            int colIndex = 0;

            // Each row has either 4 or 5 seats depending on the row
            for (int col = 1; col <= (row == 10 ? 5 : 4); col++) {
                // Add a spacer between B and C columns
                if (col == 3) {
                    Region spacer = new Region();
                    spacer.setMinWidth(20);
                    seatGrid.add(spacer, colIndex++, row - 1);
                }

                // Generate the seat number (e.g., 1A, 1B, etc.)
                String seatNumber = row + String.valueOf((char) ('A' + col - 1));
                Button seatButton = new Button(seatNumber);
                seatButton.setPrefWidth(50);

                // Get the seat object for the current seat number
                Seat seat = getSeatByNumber(seats, seatNumber);
                if (seat != null) {
                    // Check if the seat is booked by querying its status from the Bookings table
                    if ("Booked".equals(seat.getSeatStatus())) {
                        // Disable the button and mark it as booked (lightgray)
                        seatButton.setStyle("-fx-background-color: lightgray;");
                        seatButton.setDisable(true);
                    } else {
                        // If the seat is available, allow the user to select it (white)
                        seatButton.setStyle("-fx-background-color: white;");
                        seatButton.setOnAction(event -> selectSeat(seatButton));
                    }
                } else {
                    // If no seat data is available for the given seat number, disable it
                    seatButton.setStyle("-fx-background-color: lightgray;");
                    seatButton.setDisable(true);
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

//    @FXML
//    public void goToNextScreen() {
//        System.out.println("Proceeding to the next screen with seat: " + selectedSeat);
//        // Add navigation logic here
//    }

    // New method: navigates to passenger_details.fxml
    @FXML
    public void navigateToPassengerDetails() {
        if (selectedSeat == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/passenger_details.fxml"));
            Parent root = loader.load();

            PassengerDetailsController controller = loader.getController();
            controller.setBookingDetails(selectedSeat, from, to, date, time, fare, busId);

            Stage stage = (Stage) seatGrid.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
