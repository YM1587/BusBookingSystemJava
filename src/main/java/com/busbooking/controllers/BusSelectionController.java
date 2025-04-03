package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    private String fromCity;
    private String toCity;
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

    public void setRouteDetails(String from, String to, LocalDate date) {
        this.fromCity = from;
        this.toCity = to;
        this.departureDate = date;

        // Update UI label
        routeLabel.setText("Available Buses from " + from + " to " + to + " on " + date);

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

        selectButton.setOnAction(event -> navigateToSeatSelection(bus));

        card.getChildren().addAll(timeLabel, seatsLabel, fareLabel, selectButton);
        return card;
    }

    private void navigateToSeatSelection(Bus bus) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/seat_selection.fxml"));
            Parent root = loader.load();

            // Pass data to SeatSelectionController (will be implemented next)
            SeatSelectionController controller = loader.getController();
            controller.setBusDetails(fromCity, toCity, departureDate, bus.departureTime, bus.fare);

            Stage stage = (Stage) busListContainer.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
