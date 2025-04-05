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
        String fromCity = fromComboBox.getValue();
        String toCity = toComboBox.getValue();
        LocalDate selectedDate = datePicker.getValue();

        if (fromCity == null || toCity == null || selectedDate == null) {
            showAlert(Alert.AlertType.ERROR, "Incomplete Selection", "Please select all fields before proceeding.");
            return;
        }

        if (fromCity.equals(toCity)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Selection", "Departure and destination cannot be the same.");
            return;
        }

        // Find route that matches
        Route matchingRoute = routes.stream()
                .filter(r -> r.getStartLocation().equals(fromCity) && r.getEndLocation().equals(toCity))
                .findFirst()
                .orElse(null);

        if (matchingRoute == null) {
            showAlert(Alert.AlertType.WARNING, "No Route", "No route found between selected cities.");
            return;
        }

        System.out.println("Navigating to Bus Selection: " + matchingRoute);
        navigateToBusSelection(matchingRoute, selectedDate);
    }


    private void navigateToBusSelection(Route route, LocalDate date) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bus_selection.fxml"));
            Parent root = loader.load();

            BusSelectionController controller = loader.getController();
            controller.setRouteDetails(route.getStartLocation(), route.getEndLocation(), date);

            Stage stage = (Stage) searchButton.getScene().getWindow();
            stage.setScene(new Scene(root));
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
