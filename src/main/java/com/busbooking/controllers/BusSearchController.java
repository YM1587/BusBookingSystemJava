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

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            ObservableList<String> startLocations = FXCollections.observableArrayList();
            ObservableList<String> endLocations = FXCollections.observableArrayList();

            while (rs.next()) {
                Route route = new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        rs.getString("start_location"),
                        rs.getString("end_location"),
                        rs.getBigDecimal("distance_km"),
                        rs.getTime("estimated_duration").toLocalTime()
                );
                routes.add(route);

                if (!startLocations.contains(route.getStartLocation())) {
                    startLocations.add(route.getStartLocation());
                }
                if (!endLocations.contains(route.getEndLocation())) {
                    endLocations.add(route.getEndLocation());
                }
            }

            fromComboBox.setItems(startLocations);
            toComboBox.setItems(endLocations);

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to load routes.\n" + e.getMessage());
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
            showAlert(Alert.AlertType.ERROR, "Invalid Selection", "Start and destination cities cannot be the same.");
            return;
        }

        // Find matching route
        Route selectedRoute = null;
        for (Route route : routes) {
            if (route.getStartLocation().equals(fromCity) && route.getEndLocation().equals(toCity)) {
                selectedRoute = route;
                break;
            }
        }

        if (selectedRoute == null) {
            showAlert(Alert.AlertType.ERROR, "Route Not Found", "No available route for selected cities.");
            return;
        }

        navigateToBusSelection(selectedRoute, selectedDate);
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
            showAlert(Alert.AlertType.ERROR, "Navigation Error", "Could not load the bus selection screen.");
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
