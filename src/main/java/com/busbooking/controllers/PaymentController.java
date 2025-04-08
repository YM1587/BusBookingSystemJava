package com.busbooking.controllers;

import com.busbooking.dao.BookingDAO;
import com.busbooking.dao.SeatDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import com.busbooking.models.Booking;
import com.busbooking.models.Passenger;
import com.busbooking.utils.ReceiptPrinter;

import java.time.LocalDate;

public class PaymentController {

    @FXML private Label fareLabel;
    @FXML private GridPane bookingSummaryGrid;
    @FXML private TextField transactionRefField;
    @FXML private Button printReceiptButton;

    private Booking currentBooking;
    private BookingDAO bookingDAO = new BookingDAO();
    private SeatDAO seatDAO = new SeatDAO();

    public void initializeBookingDetails(Booking booking) {
        this.currentBooking = booking;

        // Populate summary grid (e.g. Passenger Name, Seat No., Date)
        bookingSummaryGrid.addRow(0, new Label("Passenger Name:"), new Label(booking.getPassengerName()));
        bookingSummaryGrid.addRow(1, new Label("From:"), new Label(booking.getFrom()));
        bookingSummaryGrid.addRow(2, new Label("To:"), new Label(booking.getTo()));
        bookingSummaryGrid.addRow(3, new Label("Departure Time:"), new Label(booking.getDepartureTime()));
        bookingSummaryGrid.addRow(4, new Label("Seat Number:"), new Label(booking.getSeatNumber()));
        bookingSummaryGrid.addRow(5, new Label("Date:"), new Label(String.valueOf(booking.getBookingDate())));

        // Set fare
        fareLabel.setText("KES " + booking.getFare());
    }

    @FXML
    public void handleConfirmBooking(ActionEvent event) {
        String transactionRef = transactionRefField.getText().trim();

        if (transactionRef.isEmpty()) {
            showAlert("Missing Info", "Please enter the M-PESA transaction reference.");
            return;
        }

        // 1. Mark seat as booked
        seatDAO.updateSeatStatus(currentBooking.getSeatId(), "Booked");

        // 2. Save booking to DB
        currentBooking.setPaymentStatus("Completed");
        currentBooking.setTransactionRef(transactionRef);
        bookingDAO.createBooking(currentBooking);

        // 3. Enable receipt
        printReceiptButton.setDisable(false);

        showAlert("Success", "Booking confirmed! You can now print your receipt.");
    }

    @FXML
    public void handlePrintReceipt(ActionEvent event) {
        ReceiptPrinter.print(currentBooking);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
