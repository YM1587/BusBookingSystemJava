/*
package com.busbooking.controllers;

import com.busbooking.dao.BookingDAO;
import com.busbooking.models.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import javafx.application.Platform;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class BookingController {

    @FXML
    private TableView<Booking> bookingTable;
    @FXML
    private TableColumn<Booking, Integer> colBookingId;
    @FXML
    private TableColumn<Booking, String> colPNR;
    @FXML
    private TableColumn<Booking, String> colSeatNumber;
    @FXML
    private TableColumn<Booking, String> colRoute;
    @FXML
    private TableColumn<Booking, String> colStatus;

    @FXML
    private TextField txtBusId;
    @FXML
    private TextField txtSeatNumber;
    @FXML
    private DatePicker dpTravelDate;
    @FXML
    private TextField txtDepartureTime;
    @FXML
    private TextField txtRouteName;
    @FXML
    private TextField txtBoardingPoint;
    @FXML
    private TextField txtTotalFare;
    @FXML
    private Label lblMessage;

    private final BookingDAO bookingDAO;
    private final int loggedInPassengerId = 1; // Replace with session-based data later

    public BookingController() {
        this.bookingDAO = new BookingDAO();
    }

    @FXML
    public void initialize() {
        // Set up TableView columns
        colBookingId.setCellValueFactory(cellData -> cellData.getValue().bookingIdProperty().asObject());
        colPNR.setCellValueFactory(cellData -> cellData.getValue().pnrProperty());
        colSeatNumber.setCellValueFactory(cellData -> cellData.getValue().seatNumberProperty());
        colRoute.setCellValueFactory(cellData -> cellData.getValue().routeProperty());
        colStatus.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        loadBookings();
    }

    */
/**
     * Loads all bookings for the currently logged-in passenger.
     *//*

    private void loadBookings() {
        List<Booking> bookings = bookingDAO.getBookingsByPassenger(loggedInPassengerId);
        ObservableList<Booking> observableBookings = FXCollections.observableArrayList(bookings);
        bookingTable.setItems(observableBookings);
    }

    */
/**
     * Handles the booking process when a passenger selects a seat.
     *//*

    @FXML
    private void handleBookSeat() {
        try {
            int busId = Integer.parseInt(txtBusId.getText());
            String seatNumber = txtSeatNumber.getText();
            LocalDate travelDate = dpTravelDate.getValue();
            LocalTime departureTime;

            try {
                departureTime = LocalTime.parse(txtDepartureTime.getText());
            } catch (Exception e) {
                showAlert("Invalid Time", "Please enter a valid departure time in HH:MM format.");
                return;
            }

            String routeName = txtRouteName.getText();
            String boardingPoint = txtBoardingPoint.getText();
            BigDecimal totalFare;

            try {
                totalFare = new BigDecimal(txtTotalFare.getText());
            } catch (NumberFormatException e) {
                showAlert("Invalid Fare", "Please enter a valid amount for the fare.");
                return;
            }

            // Check if the seat is already booked
            if (bookingDAO.isSeatBooked(busId, seatNumber, travelDate)) {
                showAlert("Seat Unavailable", "This seat has already been booked. Please choose another.");
                return;
            }

            // Generate a unique PNR number
            String pnrNumber = "PNR" + System.currentTimeMillis();

            // Create a booking object with "Pending" status
            Booking booking = new Booking(0, loggedInPassengerId, pnrNumber, busId, seatNumber, travelDate, departureTime, routeName, boardingPoint, totalFare, "Pending");

            // Save booking
            boolean success = bookingDAO.createBooking(booking);

            if (success) {
                showNotification("Booking Successful", "Your booking is pending. Please complete payment.");
                loadBookings();
                processPayment(booking); // Trigger payment process
            } else {
                showAlert("Booking Failed", "There was an issue processing your booking. Try again.");
            }
        } catch (Exception e) {
            showAlert("Invalid Input", "Please check your input fields and try again.");
            e.printStackTrace();
        }
    }

    */
/**
     * Simulates payment processing and updates booking status.
     *//*

    private void processPayment(Booking booking) {
        Alert paymentAlert = new Alert(AlertType.CONFIRMATION);
        paymentAlert.setTitle("Payment Confirmation");
        paymentAlert.setHeaderText("Complete Your Payment");
        paymentAlert.setContentText("Click OK to simulate a successful payment, or Cancel to fail.");

        Optional<ButtonType> result = paymentAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Simulate successful payment
            booking.setStatus("Confirmed");
            bookingDAO.updateBookingStatus(booking.getBookingId(), "Confirmed");
            showNotification("Payment Successful", "Your booking has been confirmed!");
        } else {
            showAlert("Payment Failed", "Your booking remains pending. Please retry payment.");
        }

        loadBookings(); // Refresh bookings
    }

    */
/**
     * Handles retrying payment for a selected booking.
     *//*

    @FXML
    private void handleRetryPayment() {
        Booking selectedBooking = bookingTable.getSelectionModel().getSelectedItem();
        if (selectedBooking == null) {
            showAlert("No Booking Selected", "Please select a booking to retry payment.");
            return;
        }

        if (!"Pending".equals(selectedBooking.getStatus())) {
            showAlert("Invalid Action", "This booking is already confirmed.");
            return;
        }

        processPayment(selectedBooking);
    }

    */
/**
     * Shows an alert dialog.
     *//*

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    */
/**
     * Displays a JavaFX notification.
     *//*

    private void showNotification(String title, String message) {
        Platform.runLater(() -> {
            Notifications.create()
                    .title(title)
                    .text(message)
                    .hideAfter(Duration.seconds(3))
                    .position(javafx.geometry.Pos.TOP_RIGHT)
                    .showInformation();
        });
    }
}
*/
