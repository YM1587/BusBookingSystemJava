package com.busbooking.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import com.busbooking.models.Booking; // Assuming Booking is a model for the booking entity
import services.BookingService; // A service class to fetch the booking history

public class BookingConfirmationController {

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

        // Set up table columns
        colRoute.setCellValueFactory(cellData -> cellData.getValue().routeProperty());
        colDate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
        colDepartureTime.setCellValueFactory(cellData -> cellData.getValue().departureTimeProperty());
        colSeat.setCellValueFactory(cellData -> cellData.getValue().seatProperty());
        colFare.setCellValueFactory(cellData -> cellData.getValue().fareProperty());

        // Load booking history
        loadBookingHistory();

        // Event listeners for buttons
        btnReturn.setOnAction(event -> handleReturnToDashboard());
        btnViewDetails.setOnAction(event -> handleViewBookingDetails());
    }

    private void loadBookingHistory() {
        // Assuming BookingService has a method to fetch a list of bookings for the user
        var bookings = bookingService.getBookingHistoryForUser();

        bookingHistoryTable.getItems().setAll(bookings);
    }

    private void handleReturnToDashboard() {
        // Navigate back to the dashboard screen (assuming we have this logic in place)
        // Change scene or navigate back
    }

    private void handleViewBookingDetails() {
        // Assuming we want to display details of the selected booking in the labels
        Booking selectedBooking = bookingHistoryTable.getSelectionModel().getSelectedItem();
        if (selectedBooking != null) {
            lblBookingRoute.setText("Route: " + selectedBooking.getRoute());
            lblBookingDate.setText("Date: " + selectedBooking.getDate());
            lblBookingDepartureTime.setText("Departure Time: " + selectedBooking.getDepartureTime());
            lblBookingSeat.setText("Seat: " + selectedBooking.getSeat());
            lblBookingFare.setText("Fare: " + selectedBooking.getFare());
        }
    }
}
