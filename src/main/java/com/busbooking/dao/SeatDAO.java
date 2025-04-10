package com.busbooking.dao;

import com.busbooking.models.Seat;
import com.busbooking.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class SeatDAO {

    public void addSeat(Seat seat) {
        String sql = "INSERT INTO seats (bus_id, seat_number) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, seat.getBusId());
            stmt.setString(2, seat.getSeatNumber());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Seat getSeatById(int id) {
        String sql = "SELECT * FROM seats WHERE seat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Seat(
                        rs.getInt("seat_id"),
                        rs.getInt("bus_id"),
                        rs.getString("seat_number"),
                        null, // seat_status is no longer needed
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Seat> getSeats() {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                seats.add(new Seat(
                        rs.getInt("seat_id"),
                        rs.getInt("bus_id"),
                        rs.getString("seat_number"),
                        null, // seat_status is no longer needed
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }
    public List<Seat> getSeatsByBusAndDate(int busId, LocalDate travelDate) {
        List<Seat> seats = new ArrayList<>();

        String sql = """
        SELECT s.*, 
               CASE 
                   WHEN b.seat_number IS NOT NULL THEN 'Booked'
                   ELSE 'Available'
               END AS seat_status
        FROM seats s
        LEFT JOIN bookings b 
            ON s.bus_id = b.bus_id 
            AND s.seat_number = b.seat_number 
            AND b.travel_date = ? 
            AND b.booking_status = 'confirmed'
        WHERE s.bus_id = ?
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(travelDate));
            stmt.setInt(2, busId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat(
                        rs.getInt("seat_id"),
                        rs.getInt("bus_id"),
                        rs.getString("seat_number"),
                        rs.getString("seat_status"),
                        rs.getTimestamp("created_at")
                );
                seats.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seats;
    }







    public void deleteSeat(int id) {
        String sql = "DELETE FROM seats WHERE seat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
