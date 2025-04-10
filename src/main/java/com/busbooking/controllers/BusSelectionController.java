package com.busbooking.controllers;

import com.busbooking.dao.BusDAO;
import com.busbooking.models.Route;
import com.busbooking.models.Bus;
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

    private BusDAO busDAO;

    // Initialize BusDAO
    public BusSelectionController() {
        this.busDAO = new BusDAO();  // DAO to handle database interactions
    }

    // Accept Route objects and date
    public void setRouteDetails(Route from, Route to, LocalDate date) {
        this.fromRoute = from;
        this.toRoute = to;
        this.departureDate = date;

        routeLabel.setText(String.format("Available Buses from %s to %s on %s",
                from.getStartLocation(), to.getEndLocation(), date));

        // Load buses from the database
        List<Bus> buses = getAvailableBuses();
        displayBuses(buses);
    }

    // Fetch buses from the database using the BusDAO
    private List<Bus> getAvailableBuses() {
        List<Bus> buses = new ArrayList<>();
        int routeId = fromRoute.getRouteId();  // Fetch route ID
        buses = busDAO.getBusesByRoute(routeId, departureDate);  // Fetch buses for the selected route and date

        // Return the list of buses with their available seats
        return buses;
    }


    // Display buses on the UI
    private void displayBuses(List<Bus> buses) {
        busListContainer.getChildren().clear();

        // Check if no buses are available for the selected route
        if (buses.isEmpty()) {
            Label noBusesLabel = new Label("No buses available for this route at the selected time.");
            busListContainer.getChildren().add(noBusesLabel);
        } else {
            // If buses are available, display each bus
            for (Bus bus : buses) {
                HBox busCard = createBusCard(bus);
                busListContainer.getChildren().add(busCard);
            }
        }
    }

    // Create a card for each bus
    private HBox createBusCard(Bus bus) {
        HBox card = new HBox(20);
        card.setStyle("-fx-border-color: black; -fx-padding: 10; -fx-background-color: #f4f4f4;");
        card.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

        // Fetch the available seats from the Bus object (it is now included in the model)
        int availableSeats = bus.getAvailableSeats();  // Fetching from the Bus model
        Label timeLabel = new Label("🕒 Departure Time: " + bus.getDepartureTime());
        Label seatsLabel = new Label("🪑 Available Seats: " + availableSeats);
        Label fareLabel = new Label("💰 Fare: KSH" + bus.getFare());
        Button selectButton = new Button("Select");

        // Button action to navigate to Seat Selection screen
        selectButton.setOnAction(event -> navigateToSeatSelection(bus));

        card.getChildren().addAll(timeLabel, seatsLabel, fareLabel, selectButton);
        return card;
    }

    // Navigate to the Seat Selection screen
    private void navigateToSeatSelection(Bus bus) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/seat_selection.fxml"));
            Parent root = loader.load();

            String fromCity = fromRoute.getStartLocation();
            String toCity = toRoute.getEndLocation();

            // Pass data to SeatSelectionController
            SeatSelectionController controller = loader.getController();

            // Ensure you're using the correct getter method for busId
            controller.setBusDetails(fromCity, toCity, departureDate, bus.getDepartureTime(), bus.getFare(), bus.getBusId()); // Corrected here

            // Set the scene with desired size
            Scene scene = new Scene(root, 1000, 700);
            Stage stage = (Stage) busListContainer.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showErrorAlert("Navigation Error", "Failed to load seat selection screen.");
        }
    }


    // Show an error alert
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
