package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SeatSelectionController {

    @FXML
    private Label routeLabel;

    @FXML
    private GridPane seatGrid;

    @FXML
    private Label selectedSeatLabel;

    @FXML
    private Button nextButton;

    private String fromCity;
    private String toCity;
    private LocalDate departureDate;
    private String departureTime;
    private double fare;
    private String selectedSeat = null;
    private final Set<String> bookedSeats = Set.of("A1", "B2", "C3"); // Sample booked seats
    private final Set<Button> seatButtons = new HashSet<>();

    public void setBusDetails(String from, String to, LocalDate date, String time, double fare) {
        this.fromCity = from;
        this.toCity = to;
        this.departureDate = date;
        this.departureTime = time;
        this.fare = fare;

        // Update route details UI
        routeLabel.setText(from + " → " + to + " | " + date + " | " + time);
        generateSeatLayout();
    }

    private void generateSeatLayout() {
        String[] rows = {"A", "B", "C", "D"};
        int columns = 4;

        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            for (int colIndex = 1; colIndex <= columns; colIndex++) {
                String seatNumber = rows[rowIndex] + colIndex;
                Button seatButton = new Button(seatNumber);
                seatButton.setStyle(bookedSeats.contains(seatNumber) ? "-fx-background-color: red;" : "-fx-background-color: green;");

                // Disable booked seats
                if (bookedSeats.contains(seatNumber)) {
                    seatButton.setDisable(true);
                } else {
                    seatButton.setOnAction(event -> selectSeat(seatButton));
                }

                seatButtons.add(seatButton);
                seatGrid.add(seatButton, colIndex - 1, rowIndex);
            }
        }
    }

    private void selectSeat(Button seatButton) {
        if (selectedSeat != null) {
            // Reset previous selection
            seatButtons.stream()
                    .filter(btn -> btn.getText().equals(selectedSeat))
                    .findFirst()
                    .ifPresent(btn -> btn.setStyle("-fx-background-color: green;"));
        }

        // Mark new selection
        selectedSeat = seatButton.getText();
        seatButton.setStyle("-fx-background-color: yellow;");
        selectedSeatLabel.setText("Selected Seat: " + selectedSeat);
        nextButton.setDisable(false);

        // Handle next button click
        nextButton.setOnAction(event -> navigateToPassengerDetails());
    }

    private void navigateToPassengerDetails() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/passenger_details.fxml"));
            Parent root = loader.load();

            // Pass data to PassengerDetailsController (to be implemented next)
            PassengerDetailsController controller = loader.getController();
            controller.setPassengerDetails(fromCity, toCity, departureDate, departureTime, fare, selectedSeat);

            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
