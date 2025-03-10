package com.busbooking.dao;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Passenger;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PassengerDAO {

    /**
     * Adds a new passenger to the database.
     *
     * @param passenger The Passenger object containing details.
     *                  Scenario: An admin manually adds a new passenger to the system, possibly without a password requirement.
     */
    public boolean addPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (first_name, last_name, email, phone_number, password_hash, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, passenger.getFirstName());
            stmt.setString(2, passenger.getLastName());
            stmt.setString(3, passenger.getEmail());
            stmt.setString(4, passenger.getPhoneNumber());
            stmt.setString(5, hashPassword(passenger.getPasswordHash())); // BCrypt hashing
            stmt.setString(6, passenger.getRole());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a passenger by their email.
     */
    public Passenger getPassengerByEmail(String email) {
        Connection conn = DatabaseConnection.getConnection();
        Passenger passenger = null;

        if (conn == null) {
            System.out.println("Database connection is null!");
            return null;
        }

        String sql = "SELECT * FROM passengers WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                passenger = new Passenger(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("password")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passenger;
    }



    /**
     * Registers a new passenger with a hashed password.
     * Creates a new passenger?
     * Used for first-time signup?
     * Scenario: A user signs up through a registration form, providing an email, password, and other details.
     */
    public void registerPassenger(Passenger passenger) {
        String sql = "INSERT INTO passengers (first_name, last_name, email, phone_number, password_hash, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, passenger.getFirstName());
            stmt.setString(2, passenger.getLastName());
            stmt.setString(3, passenger.getEmail());
            stmt.setString(4, passenger.getPhoneNumber());
            stmt.setString(5, hashPassword(passenger.getPasswordHash())); // Hashing password using BCrypt
            stmt.setString(6, passenger.getRole());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Authenticates a passenger by verifying their email and password.
     */
    public Passenger authenticatePassenger(String email, String password) {
        String sql = "SELECT * FROM passengers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                if (BCrypt.checkpw(password, storedHash)) { // Verify password with BCrypt
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Authentication failed
    }

    /**
     * Checks if an email is already registered.
     */
    public boolean isEmailExists(String email) {
        String sql = "SELECT email FROM passengers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // If result exists, email is taken
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Hashes a password using BCrypt.
     */
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
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
                passengers.add(new Passenger(
                        rs.getInt("passenger_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("password_hash"),
                        rs.getString("role"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    /**
     * Updates passenger details in the database.
     * Updates existing passenger?
     * Scenario: A passenger updates their email or phone number in their profile settings.
     */
    public void updatePassenger(Passenger passenger) {
        String sql = "UPDATE passengers SET first_name = ?, last_name = ?, email = ?, phone_number = ?, password_hash = ?, role = ? WHERE passenger_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, passenger.getFirstName());
            stmt.setString(2, passenger.getLastName());
            stmt.setString(3, passenger.getEmail());
            stmt.setString(4, passenger.getPhoneNumber());
            stmt.setString(5, hashPassword(passenger.getPasswordHash())); // Hash password on update
            stmt.setString(6, passenger.getRole());
            stmt.setInt(7, passenger.getPassengerId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a passenger from the database.
     */
    public void deletePassenger(int id) {
        String sql = "DELETE FROM passengers WHERE passenger_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
