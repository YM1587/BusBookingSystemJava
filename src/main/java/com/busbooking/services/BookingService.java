package com.busbooking.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.busbooking.models.Booking;

public class BookingService {

    public List<Booking> getBookingHistoryForUser() {
        return List.of(
                new Booking(
                        1,                            // bookingId
                        101,                          // passengerId
                        "PNR12345",                   // pnrNumber
                        202,                          // busId
                        "1A",                         // seatNumber
                        LocalDate.parse("2025-04-10"),// travelDate
                        LocalTime.parse("10:00"),     // departureTime
                        "Mombasa to Nairobi",         // routeName
                        "Mombasa",                    // boardingPoint
                        BigDecimal.valueOf(1500),     // totalFare
                        "Confirmed",                  // bookingStatus
                        "TXN12345678",                // transactionReference
                        "Paid"                        // paymentStatus
                ),
                new Booking(
                        2, 101, "PNR54321", 203, "3B",
                        LocalDate.parse("2025-04-12"),
                        LocalTime.parse("14:00"),
                        "Mombasa to Kisumu", "Mombasa",
                        BigDecimal.valueOf(1300),
                        "Confirmed", "TXN87654321", "Paid"
                )
        );
    }
}
