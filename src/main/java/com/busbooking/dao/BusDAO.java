package com.busbooking.dao;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Bus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {
    private final Connection conn;

    public BusDAO() {
        this.conn = DatabaseConnection.getConnection();
    }

    // Get all available buses for the given route, now including departure time and available seats
    public List<Bus> getBusesByRoute(int routeId) {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT b.bus_number, b.bus_type, b.capacity, b.operator_name, r.route_name, " +
                "r.start_location, r.end_location, r.fare, SUM(s.available_seats) AS available_seats, b.departure_time " +
                "FROM buses b " +
                "JOIN routes r ON b.route_id = r.route_id " +
                "JOIN seats s ON b.bus_id = s.bus_id " +
                "WHERE r.route_id = ? " +
                "GROUP BY b.bus_number, b.bus_type, b.capacity, b.operator_name, r.route_name, " +
                "r.start_location, r.end_location, r.fare, b.departure_time";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, routeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Create Bus object, including availableSeats from the database query
                Bus bus = new Bus(
                        rs.getString("bus_number"),
                        rs.getString("bus_type"),
                        rs.getInt("capacity"),
                        rs.getString("operator_name"),
                        rs.getString("route_name"),
                        rs.getString("start_location"),
                        rs.getString("end_location"),
                        rs.getDouble("fare"),
                        rs.getString("departure_time"),
                        rs.getInt("available_seats") // Setting the available seats
                );
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
        String sql = "SELECT b.bus_number, b.bus_type, b.capacity, b.operator_name, r.route_name, " +
                "r.start_location, r.end_location, r.fare, SUM(s.available_seats) AS available_seats, b.departure_time " +
                "FROM buses b " +
                "JOIN routes r ON b.route_id = r.route_id " +
                "JOIN seats s ON b.bus_id = s.bus_id " +
                "GROUP BY b.bus_number, b.bus_type, b.capacity, b.operator_name, r.route_name, " +
                "r.start_location, r.end_location, r.fare, b.departure_time";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Create Bus object, including availableSeats from the database query
                Bus bus = new Bus(
                        rs.getString("bus_number"),
                        rs.getString("bus_type"),
                        rs.getInt("capacity"),
                        rs.getString("operator_name"),
                        rs.getString("route_name"),
                        rs.getString("start_location"),
                        rs.getString("end_location"),
                        rs.getDouble("fare"),
                        rs.getString("departure_time"),
                        rs.getInt("available_seats") // Setting the available seats
                );
                buses.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }
}
