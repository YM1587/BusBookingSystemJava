package com.busbooking.dao;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Booking;

import java.sql.*;
//import java.math.BigDecimal;
import java.time.LocalDate;
//import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;



public class BookingDAO {

    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (passenger_id, pnr_number, bus_id, seat_number, travel_date, departure_time, route_name, boarding_point, total_fare, booking_status, transaction_reference, payment_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the prepared statement
            stmt.setInt(1, booking.getPassengerId());
            stmt.setString(2, booking.getPnrNumber());
            stmt.setInt(3, booking.getBusId());
            stmt.setString(4, booking.getSeatNumber());
            stmt.setDate(5, Date.valueOf(booking.getTravelDate()));
            stmt.setTime(6, Time.valueOf(booking.getDepartureTime()));
            stmt.setString(7, booking.getRouteName());
            stmt.setString(8, booking.getBoardingPoint());
            stmt.setBigDecimal(9, booking.getTotalFare());
            stmt.setString(10, booking.getBookingStatus());
            stmt.setString(11, booking.getTransactionReference());
            stmt.setString(12, booking.getPaymentStatus());

            // Execute the insert statement and check if the booking was inserted successfully
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Booking successfully inserted into database.");
                return true;
            } else {
                System.err.println("Booking insertion failed.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("SQL error while inserting booking: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Booking> getBookingsByPassenger(int passengerId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE passenger_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, passengerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(new Booking(
                        rs.getInt("booking_id"),
                        rs.getInt("passenger_id"),
                        rs.getString("pnr_number"),
                        rs.getInt("bus_id"),
                        rs.getString("seat_number"),
                        rs.getDate("travel_date").toLocalDate(),
                        rs.getTime("departure_time").toLocalTime(),
                        rs.getString("route_name"),
                        rs.getString("boarding_point"),
                        rs.getBigDecimal("total_fare"),
                        rs.getString("booking_status"),
                        rs.getString("transaction_reference"),
                        rs.getString("payment_status")
                ));
            }
        } catch (SQLException e) {
            System.err.println("SQL error while retrieving bookings: " + e.getMessage());
            e.printStackTrace();
        }
        return bookings;
    }

    public boolean isSeatBooked(int busId, String seatNumber, LocalDate travelDate) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE bus_id = ? AND seat_number = ? AND travel_date = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, busId);
            stmt.setString(2, seatNumber);
            stmt.setDate(3, Date.valueOf(travelDate));
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true; // Seat is already booked
            }
        } catch (SQLException e) {
            System.err.println("SQL error while checking seat booking: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Seat is available
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET booking_status = ? WHERE booking_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0; // Returns true if the update was successful
        } catch (SQLException e) {
            System.err.println("SQL error while updating booking status: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Update failed
    }
}
