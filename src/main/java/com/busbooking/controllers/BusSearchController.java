package com.busbooking.controllers;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Route;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class BusSearchController {

    @FXML
    private ComboBox<String> fromComboBox;

    @FXML
    private ComboBox<String> toComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Button searchButton;

    private final ObservableList<Route> routes = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadRoutesFromDatabase();

        // Disable past dates in DatePicker
        datePicker.setValue(LocalDate.now());
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(LocalDate.now()));
            }
        });

        searchButton.setOnAction(event -> handleSearch());
    }

    private void loadRoutesFromDatabase() {
        String query = "SELECT * FROM routes";
        ObservableList<String> startLocations = FXCollections.observableArrayList();
        ObservableList<String> endLocations = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Create Route objects and add to list if needed
                Route route = new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        rs.getString("start_location"),
                        rs.getString("end_location"),
                        rs.getBigDecimal("distance_km"),
                        rs.getTime("estimated_duration").toLocalTime()
                );
                routes.add(route);

                // Avoid duplicates using contains check
                String start = route.getStartLocation();
                String end = route.getEndLocation();

                if (!startLocations.contains(start)) {
                    startLocations.add(start);
                }
                if (!endLocations.contains(end)) {
                    endLocations.add(end);
                }
            }

            fromComboBox.setItems(startLocations);
            toComboBox.setItems(endLocations);

            System.out.println("Routes loaded: " + routes.size());
            System.out.println("From cities: " + startLocations);
            System.out.println("To cities: " + endLocations);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load routes.");
            e.printStackTrace();
        }
    }
    private void handleSearch() {
        System.out.println("Make a Booking button clicked!"); // Debugging

        Route fromRoute = fromComboBox.getValue();
        Route toRoute = toComboBox.getValue();
        LocalDate selectedDate = datePicker.getValue();

        // Check if the user has selected valid routes and a date
        if (fromRoute == null || toRoute == null || selectedDate == null) {
            showAlert(Alert.AlertType.ERROR, "Incomplete Selection", "Please select all fields before proceeding.");
            return;
        }

        // Prevent the user from selecting the same route for both from and to
        if (fromRoute.getRouteId() == toRoute.getRouteId()) {
            showAlert(Alert.AlertType.ERROR, "Invalid Selection", "Departure and destination routes cannot be the same.");
            return;
        }

        // Query the database to check if there is a route between the selected cities
        if (!isRouteAvailable(fromRoute, toRoute)) {
            showAlert(Alert.AlertType.ERROR, "No Route Found", "No route found between " + fromRoute.getStartLocation() + " and " + toRoute.getEndLocation() + ".");
            return;
        }

        // If everything is okay, proceed to the next step
        System.out.println("Navigating to Bus Selection with: " + fromRoute.getStartLocation() + " → " + toRoute.getEndLocation() + " on " + selectedDate);
        navigateToBusSelection(fromRoute, toRoute, selectedDate);
    }

    // Method to check if a route exists between the selected cities
    private boolean isRouteAvailable(Route fromRoute, Route toRoute) {
        String query = "SELECT * FROM routes WHERE start_location = ? AND end_location = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, fromRoute.getStartLocation());
            stmt.setString(2, toRoute.getEndLocation());

            ResultSet rs = stmt.executeQuery();
            return rs.next();  // Returns true if a row is found, meaning the route exists

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to check for routes.");
            e.printStackTrace();
            return false;
        }
    }
    private void navigateToBusSelection(Route from, Route to, LocalDate date) {
        try {
            System.out.println("Loading bus_selection.fxml..."); // Debugging
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bus_selection.fxml"));
            Parent root = loader.load();

            // Pass data to BusSelectionController
            BusSelectionController controller = loader.getController();
            if (controller == null) {
                System.out.println("Error: BusSelectionController not found.");
                return;
            }

            // Pass the entire Route objects (from and to) to the BusSelectionController
            controller.setRouteDetails(from, to, date);

            Stage stage = (Stage) searchButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Navigation successful!"); // Debugging

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load the bus selection page.");
            System.out.println("Error loading bus_selection.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }







    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
