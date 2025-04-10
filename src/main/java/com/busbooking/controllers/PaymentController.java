package com.busbooking.controllers;

import com.busbooking.dao.BookingDAO;
import com.busbooking.dao.SeatDAO;
import com.busbooking.models.Passenger;
import com.busbooking.models.Booking;
import com.busbooking.utils.ReceiptPrinter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class PaymentController {

    @FXML private Label fareLabel;
    @FXML private GridPane bookingSummaryGrid;
    @FXML private TextField transactionRefField;
    @FXML private Button printReceiptButton;

    private Booking currentBooking;
    private final BookingDAO bookingDAO = new BookingDAO();
    private final SeatDAO seatDAO = new SeatDAO();

    /**
     * Called from another controller before displaying the Payment.fxml scene.
     */
    public void setPaymentDetails(Passenger passenger, String selectedSeat, String from, String to,
                                  LocalDate date, String time, double fare, int busId) {

        System.out.println("📥 Payment details received:");
        System.out.printf("Passenger: %s | Seat: %s | From: %s | To: %s | Date: %s | Time: %s | Fare: %.2f | Bus ID: %d%n",
                passenger.getFirstName(), selectedSeat, from, to, date, time, fare, busId);

        // Generate a unique PNR
        String pnr = "PNR" + System.currentTimeMillis();

        // Construct booking using the full constructor
        Booking booking = new Booking(
                0, // bookingId (usually auto-generated in DB)
                passenger.getPassengerId(),
                pnr,
                busId,
                selectedSeat,
                date,
                LocalTime.parse(time),
                from + " to " + to,
                "", // boardingPoint (optional for now)
                BigDecimal.valueOf(fare),
                "Unconfirmed", // initial booking status
                "",            // transaction reference
                "Pending"      // initial payment status
        );

        // Pass it to display in the payment view
        setBooking(booking);
    }

    public void setBooking(Booking booking) {
        if (booking == null) {
            System.err.println("⚠️ Booking passed to PaymentController is null.");
            showAlert("Error", "Booking information is missing. Please go back and select a seat.");
            return;
        }

        this.currentBooking = booking;

        // Extract "From" and "To"
        String routeName = booking.getRouteName();
        String from = "N/A";
        String to = "N/A";
        if (routeName != null && routeName.contains(" to ")) {
            String[] parts = routeName.split(" to ");
            from = parts[0];
            to = parts[1];
        }

        // Clear any existing grid content
        bookingSummaryGrid.getChildren().clear();
        bookingSummaryGrid.setVgap(10);
        bookingSummaryGrid.setHgap(20);

        // Fill grid with booking summary
        bookingSummaryGrid.addRow(0, new Label("PNR Number:"), new Label(booking.getPnrNumber()));
        bookingSummaryGrid.addRow(1, new Label("From:"), new Label(from));
        bookingSummaryGrid.addRow(2, new Label("To:"), new Label(to));
        bookingSummaryGrid.addRow(3, new Label("Departure Time:"), new Label(booking.getDepartureTime().toString()));
        bookingSummaryGrid.addRow(4, new Label("Seat Number:"), new Label(booking.getSeatNumber()));
        bookingSummaryGrid.addRow(5, new Label("Travel Date:"), new Label(booking.getTravelDate().toString()));

        // Show fare
        fareLabel.setText("KES " + booking.getTotalFare());

        System.out.println("✅ Booking details initialized in PaymentController.");
    }

    @FXML
    public void handleConfirmBooking(ActionEvent event) {
        if (currentBooking == null) {
            showAlert("Error", "Booking information is missing. Please go back and select a seat.");
            return;
        }

        String transactionRef = transactionRefField.getText().trim();
        if (transactionRef.isEmpty()) {
            showAlert("Missing Info", "Please enter the M-PESA transaction reference.");
            return;
        }

        // Update seat status in DB
//        seatDAO.updateSeatStatus(currentBooking.getSeatNumber(), currentBooking.getBusId(), "Booked");

        // Update booking status and payment
        currentBooking.setTransactionReference(transactionRef);
        currentBooking.setBookingStatus("Confirmed");
        currentBooking.setPaymentStatus("Paid");

        // Save booking
        bookingDAO.createBooking(currentBooking);

        // Enable receipt printing
        printReceiptButton.setDisable(false);

        showAlert("Success", "Booking confirmed! You can now print your receipt.");
    }

    @FXML
    public void handlePrintReceipt(ActionEvent event) {
        if (currentBooking != null) {
            ReceiptPrinter.print(currentBooking);
        } else {
            showAlert("Error", "Cannot print receipt. Booking not found.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
