package com.busbooking.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import com.busbooking.models.Booking;
import com.busbooking.services.BookingService;

public class BookingController {

    @FXML private TableView<Booking> bookingHistoryTable;
    @FXML private TableColumn<Booking, String> colRoute;
    @FXML private TableColumn<Booking, String> colDate;
    @FXML private TableColumn<Booking, String> colDepartureTime;
    @FXML private TableColumn<Booking, String> colSeat;
    @FXML private TableColumn<Booking, Double> colFare;

    @FXML private Label lblBookingRoute;
    @FXML private Label lblBookingDate;
    @FXML private Label lblBookingDepartureTime;
    @FXML private Label lblBookingSeat;
    @FXML private Label lblBookingFare;

    @FXML private Button btnReturn;
    @FXML private Button btnViewDetails;

    private BookingService bookingService;

    public void initialize() {
        bookingService = new BookingService();

        // Bind table columns
        colRoute.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRouteName()));
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTravelDate().toString()));
        colDepartureTime.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartureTime().toString()));
        colSeat.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSeatNumber()));
        colFare.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalFare().doubleValue()).asObject());

        loadBookingHistory();

        btnReturn.setOnAction(event -> handleReturnToDashboard());
        btnViewDetails.setOnAction(event -> handleViewBookingDetails());
    }

    private void loadBookingHistory() {
        var bookings = bookingService.getBookingHistoryForUser();
        bookingHistoryTable.getItems().setAll(bookings);
    }

    @FXML
    public void handleReturnToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/dashboard.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) btnReturn.getScene().getWindow();
            Scene scene = new Scene(root, 600, 500); // Set the scene size to 600x500
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleViewBookingDetails() {
        Booking selectedBooking = bookingHistoryTable.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            lblBookingRoute.setText("Route: " + selectedBooking.getRouteName());
            lblBookingDate.setText("Date: " + selectedBooking.getTravelDate().toString());
            lblBookingDepartureTime.setText("Departure Time: " + selectedBooking.getDepartureTime().toString());
            lblBookingSeat.setText("Seat: " + selectedBooking.getSeatNumber());
            lblBookingFare.setText("Fare: Ksh " + selectedBooking.getTotalFare().toString());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Booking Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a booking to view the details.");
            alert.showAndWait();
        }
    }
}
