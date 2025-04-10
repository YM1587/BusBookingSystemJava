package com.busbooking.dao;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Bus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

import java.sql.Date;


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
                "COUNT(s.seat_id) AS available_seats " + // Counting available seats (seat_status = 'Available')
                "FROM buses b " +
                "JOIN routes r ON b.route_id = r.route_id " +
                "JOIN seats s ON b.bus_id = s.bus_id " +
                "LEFT JOIN bookings bo ON s.seat_id = bo.seat_id AND bo.travel_date = ? " + // Join with bookings to filter by date
                "WHERE r.route_id = ? AND s.seat_status = 'Available' " +
                "AND bo.seat_id IS NULL " + // Ensure the seat is not booked for this travel date
                "GROUP BY b.bus_id, b.bus_number, b.bus_type, b.operator_name, r.route_name, " +
                "r.start_location, r.end_location, r.fare, b.departure_time";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(travelDate)); // Set the travel date parameter
            stmt.setInt(2, routeId); // Set the route ID parameter
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Create Bus object with busId included
                Bus bus = new Bus(
                        rs.getInt("bus_id"),           // busId
                        rs.getString("bus_number"),    // busNumber
                        rs.getString("bus_type"),      // busType
                        rs.getString("operator_name"), // operatorName
                        rs.getString("route_name"),    // routeName
                        rs.getString("start_location"),// startLocation
                        rs.getString("end_location"),  // endLocation
                        rs.getDouble("fare"),          // fare
                        rs.getString("departure_time") // departureTime
                );
                // Set available seats from the query result
                bus.setAvailableSeats(rs.getInt("available_seats"));
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
                "COUNT(s.seat_id) AS available_seats " + // Counting the available seats (seat_status = 'Available')
                "FROM buses b " +
                "JOIN routes r ON b.route_id = r.route_id " +
                "JOIN seats s ON b.bus_id = s.bus_id " +
                "WHERE s.seat_status = 'Available' " + // Filtering for available seats
                "GROUP BY b.bus_id, b.bus_number, b.bus_type, b.operator_name, r.route_name, " +
                "r.start_location, r.end_location, r.fare, b.departure_time";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Create Bus object with busId included
                Bus bus = new Bus(
                        rs.getInt("bus_id"),           // busId
                        rs.getString("bus_number"),    // busNumber
                        rs.getString("bus_type"),      // busType
                        rs.getString("operator_name"), // operatorName
                        rs.getString("route_name"),    // routeName
                        rs.getString("start_location"),// startLocation
                        rs.getString("end_location"),  // endLocation
                        rs.getDouble("fare"),          // fare
                        rs.getString("departure_time") // departureTime
                );
                // Set available seats from the query result
                bus.setAvailableSeats(rs.getInt("available_seats"));
                buses.add(bus);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }
}
