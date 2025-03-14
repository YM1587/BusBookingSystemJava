package com.busbooking.services;

import com.busbooking.dao.PassengerDAO;
import com.busbooking.models.Passenger;
import com.busbooking.utils.PasswordUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.busbooking.config.DatabaseConnection; // Ensure this class exists
import org.mindrot.jbcrypt.BCrypt; // Make sure you have the BCrypt library

public class PassengerService {
    private static Passenger loggedInUser;
    private final PassengerDAO passengerDAO; // Added DAO for database interaction

    public PassengerService() {
        this.passengerDAO = new PassengerDAO(); // Initialize DAO
    }

    public Passenger getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Passenger passenger) {
        loggedInUser = passenger;
    }

    public void logoutUser() {
        loggedInUser = null;
    }


    // Added authentication method
    public Passenger authenticate(String email, String password) {
        String sql = "SELECT * FROM passengers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.trim());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                System.out.println("Database password hash: " + storedHash);

                // Hash the input password to compare
                boolean isMatch = BCrypt.checkpw(password, storedHash);
                System.out.println("Password entered: " + password);
                System.out.println("Password check result: " + isMatch);

                if (isMatch) {
                    System.out.println("✅ Login successful for: " + email);
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
                } else {
                    System.out.println("❌ Password mismatch for: " + email);
                }
            } else {
                System.out.println("⚠️ No user found with email: " + email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Authentication failed
    }



    // ✅ Register new passenger with hashed password
    public boolean registerPassenger(Passenger passenger) {
        // Hash the password before saving
        String hashedPassword = PasswordUtil.hashPassword(passenger.getPasswordHash());
        passenger.setPasswordHash(hashedPassword);

        // Save passenger using DAO
        return passengerDAO.addPassenger(passenger);
    }

    // ✅ Check if an email already exists
    public boolean emailExists(String email) {
        return passengerDAO.getPassengerByEmail(email) != null;
    }




}
