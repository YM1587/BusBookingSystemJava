package com.busbooking.dao;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Bus;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {
    private Connection conn;

    public BusDAO() {
        this.conn = DatabaseConnection.getConnection();
    }

    // Get all available buses
    public List<Bus> getAllBuses() {
        List<Bus> buses = new ArrayList<>();
        String sql = "SELECT * FROM buses";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                buses.add(new Bus(
                        rs.getInt("bus_id"),
                        rs.getString("bus_number"),
                        rs.getString("bus_type"),
                        rs.getInt("capacity"),
                        rs.getString("operator_name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buses;
    }
}
