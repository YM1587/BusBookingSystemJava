package com.busbooking.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PaymentController {

    @FXML private Label lblFrom, lblTo, lblDate, lblTime, lblTickets, lblAmount, lblError;
    @FXML private TextField txtAmount, txtReference;
    @FXML private Button btnFinishPayment;

    // Static variables to hold booking details
    public static String from, to, date, time;
    public static int ticketCount;
    public static double totalAmount;

    @FXML
    public void initialize() {
        // Automatically populate UI labels with booking details
        lblFrom.setText(from);
        lblTo.setText(to);
        lblDate.setText(date);
        lblTime.setText(time);
        lblTickets.setText(String.valueOf(ticketCount));
        lblAmount.setText(String.format("Ksh %.2f", totalAmount));

        // Handle payment when the button is clicked
        btnFinishPayment.setOnAction(event -> handlePayment());
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
    }
}
