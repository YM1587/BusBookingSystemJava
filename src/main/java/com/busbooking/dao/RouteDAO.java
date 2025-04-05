package com.busbooking.dao;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Route;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RouteDAO {
    private Connection conn;

    public RouteDAO() {
        this.conn = DatabaseConnection.getConnection();
    }

    // Fetch all routes
    public List<Route> getAllRoutes() {
        List<Route> routes = new ArrayList<>();
        String sql = "SELECT * FROM routes";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                routes.add(new Route(
                        rs.getInt("route_id"),
                        rs.getString("route_name"),
                        rs.getString("start_location"),
                        rs.getString("end_location"),
                        rs.getBigDecimal("distance_km"),
                        rs.getTime("estimated_duration").toLocalTime(),
                        rs.getBigDecimal("fare") // Fetch the fare as well
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }

    // Insert a new route
    public void insertRoute(Route route) {
        String query = "INSERT INTO routes (route_name, start_location, end_location, distance_km, estimated_duration, fare) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, route.getRouteName());
            stmt.setString(2, route.getStartLocation());
            stmt.setString(3, route.getEndLocation());
            stmt.setBigDecimal(4, route.getDistanceKm());
            stmt.setTime(5, Time.valueOf(route.getEstimatedDuration()));
            stmt.setBigDecimal(6, route.getFare());  // Setting the fare parameter

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Route inserted successfully!");
            } else {
                System.out.println("Failed to insert route.");
            }
        } catch (SQLException e) {
            System.out.println("Error while inserting route: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Update an existing route
    public void updateRoute(Route route) {
        String query = "UPDATE routes SET route_name = ?, start_location = ?, end_location = ?, distance_km = ?, estimated_duration = ?, fare = ? WHERE route_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, route.getRouteName());
            stmt.setString(2, route.getStartLocation());
            stmt.setString(3, route.getEndLocation());
            stmt.setBigDecimal(4, route.getDistanceKm());
            stmt.setTime(5, Time.valueOf(route.getEstimatedDuration()));
            stmt.setBigDecimal(6, route.getFare());  // Updating the fare
            stmt.setInt(7, route.getRouteId());  // Ensure you update the route by its ID

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Route updated successfully!");
            } else {
                System.out.println("Failed to update route.");
            }
        } catch (SQLException e) {
            System.out.println("Error while updating route: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
