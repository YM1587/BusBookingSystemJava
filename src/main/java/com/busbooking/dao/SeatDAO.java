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



    public List<Seat> getAvailableSeatsByBusAndDate(int busId, LocalDate travelDate) {
        List<Seat> availableSeats = new ArrayList<>();

        String sql = """
        SELECT s.*
        FROM seats s
        WHERE s.bus_id = ?
          AND s.seat_number NOT IN (
              SELECT b.seat_number
              FROM bookings b
              WHERE b.bus_id = ?
                AND b.travel_date = ?
                AND b.booking_status = 'confirmed'
          )
    """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, busId);
            stmt.setInt(2, busId);
            stmt.setDate(3, Date.valueOf(travelDate));

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Seat seat = new Seat(
                        rs.getInt("seat_id"),
                        rs.getInt("bus_id"),
                        rs.getString("seat_number"),
                        null, // seat_status is being deprecated
                        rs.getTimestamp("created_at")
                );
                availableSeats.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableSeats;
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
