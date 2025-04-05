package com.busbooking.controllers;

import com.busbooking.models.Route;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BusSelectionController {

    @FXML
    private Label routeLabel;

    @FXML
    private VBox busListContainer;

    private Route fromRoute;
    private Route toRoute;
    private LocalDate departureDate;

    // Bus data structure
    public static class Bus {
        String departureTime;
        int availableSeats;
        double fare;

        public Bus(String departureTime, int availableSeats, double fare) {
            this.departureTime = departureTime;
            this.availableSeats = availableSeats;
            this.fare = fare;
        }
    }

    // Updated method to accept Route objects
    public void setRouteDetails(Route from, Route to, LocalDate date) {
        this.fromRoute = from;
        this.toRoute = to;
        this.departureDate = date;

        // Update UI label with route details
        routeLabel.setText(String.format("Available Buses from %s to %s on %s", from.getStartLocation(), to.getEndLocation(), date));

        // Load available buses (mock data for now)
        List<Bus> buses = getAvailableBuses();
        displayBuses(buses);
    }

    private List<Bus> getAvailableBuses() {
        // Sample data (can be replaced with database retrieval)
        List<Bus> buses = new ArrayList<>();
        buses.add(new Bus("08:00 AM", 5, 25.50));
        buses.add(new Bus("10:30 AM", 12, 30.00));
        buses.add(new Bus("01:00 PM", 7, 22.75));
        return buses;
    }

    private void displayBuses(List<Bus> buses) {
        busListContainer.getChildren().clear();

        for (Bus bus : buses) {
            HBox busCard = createBusCard(bus);
            busListContainer.getChildren().add(busCard);
        }
    }

    private HBox createBusCard(Bus bus) {
        HBox card = new HBox(20);
        card.setStyle("-fx-border-color: black; -fx-padding: 10; -fx-background-color: #f4f4f4;");
        card.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        Label timeLabel = new Label("🕒 " + bus.departureTime);
        Label seatsLabel = new Label("🪑 Seats: " + bus.availableSeats);
        Label fareLabel = new Label("💰 $" + bus.fare);
        Button selectButton = new Button("Select");

        // Lambda for button action
        selectButton.setOnAction(event -> navigateToSeatSelection(bus));

        card.getChildren().addAll(timeLabel, seatsLabel, fareLabel, selectButton);
        return card;
    }

    // Navigate to the Seat Selection screen
    private void navigateToSeatSelection(Bus bus) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/seat_selection.fxml"));
            Parent root = loader.load();

            // Extract city names from Route objects
            String fromCity = fromRoute.getStartLocation();
            String toCity = toRoute.getEndLocation();

            // Pass data to SeatSelectionController
            SeatSelectionController controller = loader.getController();
            controller.setBusDetails(fromCity, toCity, departureDate, bus.getDepartureTime(), bus.getFare());

            Stage stage = (Stage) busListContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Utility method for error alert
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
