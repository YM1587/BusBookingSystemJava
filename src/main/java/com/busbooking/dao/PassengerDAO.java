package com.busbooking.dao;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Passenger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO {

    /**
     * Adds a new passenger to the database.
     */
    public boolean addPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (first_name, last_name, email, phone_number, password_hash, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, passenger.getFirstName());
            stmt.setString(2, passenger.getLastName());
            stmt.setString(3, passenger.getEmail());
            stmt.setString(4, passenger.getPhoneNumber());
            stmt.setString(5, passenger.getPasswordHash()); // Use already hashed password
            stmt.setString(6, passenger.getRole());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println("Error adding passenger: " + e.getMessage());
            return false;
        }
    }

    /**
     * Retrieves a passenger by their ID.
     */
    public Passenger getPassengerById(int id) {
        String sql = "SELECT * FROM passengers WHERE passenger_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPassenger(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving passenger by ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves a passenger by their email.
     */
    public Passenger getPassengerByEmail(String email) {
        String sql = "SELECT * FROM passengers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToPassenger(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving passenger by email: " + e.getMessage());
        }
        return null;
    }

    /**
     * Checks if an email is already registered.
     */
    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM passengers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If result exists, email is taken
        } catch (SQLException e) {
            System.out.println("Error checking email existence: " + e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves all passengers from the database.
     */
    public List<Passenger> getAllPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        String sql = "SELECT * FROM passengers";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                passengers.add(mapResultSetToPassenger(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving all passengers: " + e.getMessage());
        }
        return passengers;
    }

    /**
     * Updates passenger details in the database.
     */
    public boolean updatePassenger(Passenger passenger) {
        String sql = "UPDATE passengers SET first_name = ?, last_name = ?, email = ?, phone_number = ?, password_hash = ?, role = ? WHERE passenger_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, passenger.getFirstName());
            stmt.setString(2, passenger.getLastName());
            stmt.setString(3, passenger.getEmail());
            stmt.setString(4, passenger.getPhoneNumber());

            
            if (passenger.getPasswordHash() != null && !passenger.getPasswordHash().isEmpty()) {
                stmt.setString(5, passenger.getPasswordHash()); // Use already hashed password
            } else {
                stmt.setString(5, getPassengerById(passenger.getPassengerId()).getPasswordHash());
            }

            stmt.setString(6, passenger.getRole());
            stmt.setInt(7, passenger.getPassengerId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating passenger: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a passenger from the database.
     */
    public boolean deletePassenger(int id) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting passenger: " + e.getMessage());
            return false;
        }
    }

    /**
     * Maps a ResultSet row to a Passenger object.
     */
    private Passenger mapResultSetToPassenger(ResultSet rs) throws SQLException {
        return new Passenger(
                rs.getInt("passenger_id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("phone_number"),
                rs.getString("password_hash"),
                rs.getString("role"),
                rs.getTimestamp("created_at")
        );
    }
}
