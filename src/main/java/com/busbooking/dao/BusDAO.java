package com.busbooking.dao;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Bus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class BusDAO {
    private final Connection conn;

    public BusDAO() {
        this.conn = DatabaseConnection.getConnection();
    }

    // Get all available buses for the given route, including departure time and available seats
    public List<Bus> getBusesByRoute(int routeId, LocalDate travelDate) {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT b.bus_id, b.bus_number, b.bus_type, b.operator_name, r.route_name, " +
                "r.start_location, r.end_location, r.fare, b.departure_time, " +
                "COUNT(DISTINCT s.seat_number) AS total_seats, " +
                "COUNT(DISTINCT CASE WHEN bo.seat_number IS NULL THEN s.seat_number END) AS available_seats " +
                "FROM buses b " +
                "JOIN routes r ON b.route_id = r.route_id " +
                "JOIN seats s ON b.bus_id = s.bus_id " +
                "LEFT JOIN bookings bo ON s.seat_number = bo.seat_number AND bo.travel_date = ? " +
                "WHERE r.route_id = ? " +
                "GROUP BY b.bus_id, b.bus_number, b.bus_type, b.operator_name, r.route_name, " +
                "r.start_location, r.end_location, r.fare, b.departure_time";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(travelDate));
            stmt.setInt(2, routeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bus bus = new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("bus_number"),
                        rs.getString("bus_type"),
                        rs.getString("operator_name"),
                        rs.getString("route_name"),
                        rs.getString("start_location"),
                        rs.getString("end_location"),
                        rs.getDouble("fare"),
                        rs.getString("departure_time")
                );
                bus.setAvailableSeats(rs.getInt("available_seats"));
                bus.setTotalSeats(rs.getInt("total_seats")); // Make sure Bus model has this field
                buses.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }

    // Get all buses, including other parameters, departure time, and available seats
    public List<Bus> getAllBuses() {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT b.bus_id, b.bus_number, b.bus_type, b.operator_name, r.route_name, " +
                "r.start_location, r.end_location, r.fare, b.departure_time, " +
                "COUNT(DISTINCT s.seat_number) AS total_seats, " +
                "COUNT(DISTINCT CASE WHEN bo.seat_number IS NULL THEN s.seat_number END) AS available_seats " +
                "FROM buses b " +
                "JOIN routes r ON b.route_id = r.route_id " +
                "JOIN seats s ON b.bus_id = s.bus_id " +
                "LEFT JOIN bookings bo ON s.seat_number = bo.seat_number " +
                "GROUP BY b.bus_id, b.bus_number, b.bus_type, b.operator_name, r.route_name, " +
                "r.start_location, r.end_location, r.fare, b.departure_time";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bus bus = new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("bus_number"),
                        rs.getString("bus_type"),
                        rs.getString("operator_name"),
                        rs.getString("route_name"),
                        rs.getString("start_location"),
                        rs.getString("end_location"),
                        rs.getDouble("fare"),
                        rs.getString("departure_time")
                );
                bus.setAvailableSeats(rs.getInt("available_seats"));
                bus.setTotalSeats(rs.getInt("total_seats"));
                buses.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }
}
