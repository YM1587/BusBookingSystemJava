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
import java.math.BigDecimal;
import java.sql.SQLException;
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
        this.busDAO = new BusDAO();  // You should have a DAO to handle the database interactions
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
        try {
            // Assuming routeId is available in the Route object
            int routeId = fromRoute.getRouteId();  // Assuming Route class has getRouteId method
            buses = busDAO.getBusesByRoute(routeId);  // Fetching buses for the selected route
        } catch (SQLException e) {
            e.printStackTrace();
            showErrorAlert("Database Error", "Failed to fetch buses from the database.");
        }
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

        Label timeLabel = new Label("🕒 Departure Time: " + bus.getDepartureTime());  // This now works
        Label seatsLabel = new Label("🪑 Available Seats: " + bus.getAvailableSeats());
        Label fareLabel = new Label("💰 Fare: $" + bus.getFare());
        Button selectButton = new Button("Select");

        selectButton.setOnAction(event -> navigateToSeatSelection(bus));

        card.getChildren().addAll(timeLabel, seatsLabel, fareLabel, selectButton);
        return card;
    }

    private void navigateToSeatSelection(Bus bus) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/seat_selection.fxml"));
            Parent root = loader.load();

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
            showErrorAlert("Navigation Error", "Failed to load seat selection screen.");
        }
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
