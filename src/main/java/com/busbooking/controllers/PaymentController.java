package com.busbooking.controllers;

import com.busbooking.dao.BookingDAO;
import com.busbooking.dao.SeatDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import com.busbooking.models.Booking;
import com.busbooking.utils.ReceiptPrinter;

public class PaymentController {

    @FXML private Label fareLabel;
    @FXML private GridPane bookingSummaryGrid;
    @FXML private TextField transactionRefField;
    @FXML private Button printReceiptButton;

    private Booking currentBooking;
    private final BookingDAO bookingDAO = new BookingDAO();
    private final SeatDAO seatDAO = new SeatDAO();

    public void initializeBookingDetails(Booking booking) {
        this.currentBooking = booking;

        // Split routeName into From and To if needed (e.g. "Mombasa to Nairobi")
        String routeName = booking.getRouteName();
        String from = "N/A";
        String to = "N/A";
        if (routeName != null && routeName.contains(" to ")) {
            String[] parts = routeName.split(" to ");
            from = parts[0];
            to = parts[1];
        }

        // Fill in booking summary
        bookingSummaryGrid.addRow(0, new Label("PNR Number:"), new Label(booking.getPnrNumber()));
        bookingSummaryGrid.addRow(1, new Label("From:"), new Label(from));
        bookingSummaryGrid.addRow(2, new Label("To:"), new Label(to));
        bookingSummaryGrid.addRow(3, new Label("Departure Time:"), new Label(booking.getDepartureTime().toString()));
        bookingSummaryGrid.addRow(4, new Label("Seat Number:"), new Label(booking.getSeatNumber()));
        bookingSummaryGrid.addRow(5, new Label("Travel Date:"), new Label(booking.getTravelDate().toString()));

        // Display fare
        fareLabel.setText("KES " + booking.getTotalFare());
    }

    @FXML
    public void handleConfirmBooking(ActionEvent event) {
        String transactionRef = transactionRefField.getText().trim();

        if (transactionRef.isEmpty()) {
            showAlert("Missing Info", "Please enter the M-PESA transaction reference.");
            return;
        }

        // Mark seat as booked (assuming you pass seat info via seatNumber, not seat ID directly)
        seatDAO.updateSeatStatus(currentBooking.getSeatNumber(), currentBooking.getBusId(), "Booked");

        // Update booking status and save
        currentBooking.setPaymentStatus("Paid");
        currentBooking.setBookingStatus("Confirmed");
        currentBooking.setTransactionReference(transactionRef);
        bookingDAO.createBooking(currentBooking);

        // Enable receipt printing
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
