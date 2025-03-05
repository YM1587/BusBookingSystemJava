package com.busbooking.dao;

import com.busbooking.models.Seat;
import com.busbooking.config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeatDAO {

    public void addSeat(Seat seat) {
        String sql = "INSERT INTO seats (bus_id, seat_number, seat_status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, seat.getBusId());
            stmt.setString(2, seat.getSeatNumber());
            stmt.setString(3, seat.getSeatStatus());

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
                        rs.getString("seat_status"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Seat> getAllSeats() {
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
                        rs.getString("seat_status"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    public void updateSeatStatus(int seatId, String newStatus) {
        String sql = "UPDATE seats SET seat_status = ? WHERE seat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, seatId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
