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
                        rs.getTime("estimated_duration").toLocalTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return routes;
    }
}
