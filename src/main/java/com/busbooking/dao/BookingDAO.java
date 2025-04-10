package com.busbooking.dao;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public boolean createBooking(Booking booking) {
        String sql = "INSERT INTO bookings (passenger_id, pnr_number, bus_id, seat_number, travel_date, departure_time, route_name, boarding_point, total_fare, booking_status, transaction_reference, payment_status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, booking.getPassengerId());
            stmt.setString(2, booking.getPnrNumber());
            stmt.setInt(3, booking.getBusId());
            stmt.setString(4, booking.getSeatNumber());
            stmt.setDate(5, Date.valueOf(booking.getTravelDate()));
            stmt.setTime(6, Time.valueOf(booking.getDepartureTime()));
            stmt.setString(7, booking.getRouteName());
            stmt.setString(8, booking.getBoardingPoint());
            stmt.setBigDecimal(9, booking.getTotalFare());
            stmt.setString(10, booking.getBookingStatus());           // ✅ no more hardcoding
            stmt.setString(11, booking.getTransactionReference());
            stmt.setString(12, booking.getPaymentStatus());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
            e.printStackTrace();
        }
        return bookings;
    }

    public List<Booking> getBookingHistory(int passengerId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE passenger_id = ? ORDER BY travel_date DESC";
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
            e.printStackTrace();
        }
        return bookings;
    }

    public String generateReceipt(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE booking_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Booking booking = new Booking(
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
                );

                StringBuilder receipt = new StringBuilder();
                receipt.append("Booking Receipt\n");
                receipt.append("====================\n");
                receipt.append("Booking ID: ").append(booking.getBookingId()).append("\n");
                receipt.append("PNR Number: ").append(booking.getPnrNumber()).append("\n");
                receipt.append("Seat: ").append(booking.getSeatNumber()).append("\n");
                receipt.append("Route: ").append(booking.getRouteName()).append("\n");
                receipt.append("Boarding Point: ").append(booking.getBoardingPoint()).append("\n");
                receipt.append("Travel Date: ").append(booking.getTravelDate()).append("\n");
                receipt.append("Departure Time: ").append(booking.getDepartureTime()).append("\n");
                receipt.append("Total Fare: ").append(booking.getTotalFare()).append("\n");
                receipt.append("Booking Status: ").append(booking.getBookingStatus()).append("\n");
                receipt.append("Transaction Ref: ").append(booking.getTransactionReference()).append("\n");
                receipt.append("Payment Status: ").append(booking.getPaymentStatus()).append("\n");
                receipt.append("====================\n");
                return receipt.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Booking not found!";
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
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE bookings SET booking_status = ? WHERE booking_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, bookingId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
