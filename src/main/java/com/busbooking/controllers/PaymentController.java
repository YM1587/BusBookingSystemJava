package com.busbooking.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.application.Platform;

public class PaymentController {

    @FXML private Label lblFrom, lblTo, lblDate, lblTime, lblTickets, lblAmount, lblError;
    @FXML private Label lblFirstName, lblLastName, lblIdNumber, lblMobile;  // Added labels for passenger details
    @FXML private TextField txtAmount, txtReference;
    @FXML private Button btnFinishPayment, btnDisplayReceipt;

    // Static variables to hold booking details
    public static String from, to, date, time;
    public static int ticketCount;
    public static double totalAmount;

    // Static variables to hold passenger details
    public static String firstName, lastName, idNumber, mobile;

    @FXML
    public void initialize() {
        // Debug: Check the booking and passenger details
        System.out.println("From: " + from);
        System.out.println("To: " + to);
        System.out.println("Date: " + date);
        System.out.println("Time: " + time);
        System.out.println("Ticket Count: " + ticketCount);
        System.out.println("Total Amount: " + totalAmount);

        // Ensure UI updates are done on the JavaFX Application Thread
        Platform.runLater(() -> {
            // Automatically populate UI labels with booking details
            lblFrom.setText(from);
            lblTo.setText(to);
            lblDate.setText(date);
            lblTime.setText(time);
            lblTickets.setText(String.valueOf(ticketCount));
            lblAmount.setText(String.format("Ksh %.2f", totalAmount));

            // Populate passenger details
            lblFirstName.setText(firstName);
            lblLastName.setText(lastName);
            lblIdNumber.setText(idNumber);
            lblMobile.setText(mobile);
        });

        btnFinishPayment.setOnAction(event -> handlePayment());
        btnDisplayReceipt.setOnAction(event -> handleDisplayReceipt());
    }

    private void handlePayment() {
        String amountEntered = txtAmount.getText().trim();
        String transactionRef = txtReference.getText().trim();

        // Validation: Check if reference number is empty
        if (transactionRef.isEmpty()) {
            lblError.setText("Transaction reference cannot be empty!");
            return;
        }

        // Optional: Validate the amount entered (should match expected amount)
        double enteredAmount;
        try {
            enteredAmount = Double.parseDouble(amountEntered);
            if (enteredAmount < totalAmount) {
                lblError.setText("Insufficient payment! Please enter the full amount.");
                return;
            }
        } catch (NumberFormatException e) {
            lblError.setText("Invalid amount entered!");
            return;
        }

        // If payment is valid, mark seat as booked
        markSeatAsBooked();

        // Set payment status to Completed
        lblError.setText("Payment successful! Generating receipt...");

        // Simulate receipt generation
        generateReceipt();
    }

    private void markSeatAsBooked() {
        System.out.println("Seat successfully booked for: " + from + " to " + to);
    }

    private void generateReceipt() {
        System.out.println("Receipt generated: Downloadable now.");
        // You can implement code to save or display the receipt in a specific format
    }

    private void handleDisplayReceipt() {
        // Logic to display the receipt when the button is clicked
        String receipt = generateReceiptText();  // Generate receipt text
        showReceipt(receipt);  // Show receipt in a dialog
    }

    private String generateReceiptText() {
        // Create the receipt text to be displayed
        return "Receipt:\n" +
                "From: " + lblFrom.getText() + "\n" +
                "To: " + lblTo.getText() + "\n" +
                "Date: " + lblDate.getText() + "\n" +
                "Departure Time: " + lblTime.getText() + "\n" +
                "Tickets: " + lblTickets.getText() + "\n" +
                "Total Amount: " + lblAmount.getText() + "\n" +
                "Passenger Details:\n" +
                "First Name: " + lblFirstName.getText() + "\n" +
                "Last Name: " + lblLastName.getText() + "\n" +
                "ID Number: " + lblIdNumber.getText() + "\n" +
                "Mobile: " + lblMobile.getText();
    }

    private void showReceipt(String receipt) {
        // Display the receipt in an Alert dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Receipt");
        alert.setHeaderText("Your Booking Receipt");
        alert.setContentText(receipt);
        alert.showAndWait();
    }
}
