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

    @FXML
    public void initialize() {
        loadRouteLocations();

        // Disable past dates
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
    private void loadRouteLocations() {
        ObservableList<String> startLocations = FXCollections.observableArrayList();
        ObservableList<String> endLocations = FXCollections.observableArrayList();

        String query = "SELECT DISTINCT start_location, end_location FROM routes";  // Your original query

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Log results to verify
            System.out.println("Fetching distinct start and end locations...");

            while (rs.next()) {
                String startLocation = rs.getString("start_location");
                String endLocation = rs.getString("end_location");

                System.out.println("Start: " + startLocation + " | End: " + endLocation);  // Debugging log

                if (!startLocations.contains(startLocation)) startLocations.add(startLocation);
                if (!endLocations.contains(endLocation)) endLocations.add(endLocation);
            }

            // Check the list sizes
            System.out.println("Start Locations: " + startLocations.size());
            System.out.println("End Locations: " + endLocations.size());

            // Set the ComboBoxes
            fromComboBox.setItems(startLocations);
            toComboBox.setItems(endLocations);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load locations.");
            e.printStackTrace();
        }
    }



    private void handleSearch() {
        String fromCity = fromComboBox.getValue();
        String toCity = toComboBox.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (fromCity == null || toCity == null || selectedDate == null) {
            showAlert(Alert.AlertType.ERROR, "Incomplete Selection", "Please select all fields.");
            return;
        }

        if (fromCity.equals(toCity)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Route", "Departure and destination cannot be the same.");
            return;
        }

        // Fetch matching route
        Route selectedRoute = getRouteByCities(fromCity, toCity);
        if (selectedRoute == null) {
            showAlert(Alert.AlertType.ERROR, "Route Not Found", "No route found between the selected cities.");
            return;
        }

        // Proceed to bus selection screen
        navigateToBusSelection(selectedRoute, selectedRoute, selectedDate);
    }

    private Route getRouteByCities(String start, String end) {
        String query = "SELECT route_id, route_name, start_location, end_location, distance_km, estimated_duration, fare FROM routes WHERE start_location = ? AND end_location = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, start);
            stmt.setString(2, end);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        rs.getString("start_location"),
                        rs.getString("end_location"),
                        rs.getBigDecimal("distance_km"),
                        rs.getTime("estimated_duration").toLocalTime(),
                        rs.getBigDecimal("fare")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void navigateToBusSelection(Route from, Route to, LocalDate date) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bus_selection.fxml"));
            Parent root = loader.load();

            BusSelectionController controller = loader.getController();
            controller.setRouteDetails(from, to, date);

            // Set the scene with custom dimensions
            Scene scene = new Scene(root, 1000, 700); // Adjust width and height as needed
            Stage stage = (Stage) searchButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load the bus selection page.");
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
