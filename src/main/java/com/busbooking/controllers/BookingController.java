package com.busbooking.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
        bookingService = new BookingService();  // Initialize the service that fetches booking data

        // ✅ Properly bind table columns using getters and conversions
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

    private void handleReturnToDashboard() {
        // Implement scene switching logic here
    }

    private void handleViewBookingDetails() {
        Booking selectedBooking = bookingHistoryTable.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            lblBookingRoute.setText("Route: " + selectedBooking.getRouteName());
            lblBookingDate.setText("Date: " + selectedBooking.getTravelDate().toString());
            lblBookingDepartureTime.setText("Departure Time: " + selectedBooking.getDepartureTime().toString());
            lblBookingSeat.setText("Seat: " + selectedBooking.getSeatNumber());
            lblBookingFare.setText("Fare: Ksh " + selectedBooking.getTotalFare().toString());
        }
    }
}
