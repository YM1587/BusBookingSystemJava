package com.busbooking.utils;

import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
//import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.busbooking.models.Booking;

public class ReceiptPrinter {

    public static void print(Booking booking) {
        VBox receipt = new VBox(10);
        receipt.setStyle("-fx-padding: 20; -fx-font-size: 14px;");

        receipt.getChildren().addAll(
                new Label("🚌 UGWANA BUS BOOKING RECEIPT"),
                new Label("-------------------------------------------------"),
                new Label("PNR Number: " + booking.getPnrNumber()),
                new Label("Route: " + booking.getRouteName()), // Example: Mombasa to Nairobi
                new Label("Boarding Point: " + booking.getBoardingPoint()),
                new Label("Travel Date: " + booking.getTravelDate()),
                new Label("Departure Time: " + booking.getDepartureTime()),
                new Label("Seat Number: " + booking.getSeatNumber()),
                new Label("Fare: KES " + booking.getTotalFare()),
                new Label("Transaction Ref: " + booking.getTransactionReference()),
                new Label("Payment Status: " + booking.getPaymentStatus()),
                new Label("Booking Status: " + booking.getBookingStatus()),
                new Label("-------------------------------------------------"),
                new Label("Thank you for choosing UGWANA 😊")
        );

        // Print or preview
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(null)) {
            boolean success = job.printPage(receipt);
            if (success) {
                job.endJob();
            }
        } else {
            // Show preview if cancelled
            Stage previewStage = new Stage();
            previewStage.setTitle("Receipt Preview");
            previewStage.setScene(new Scene(receipt));
            previewStage.show();
        }
    }
}
