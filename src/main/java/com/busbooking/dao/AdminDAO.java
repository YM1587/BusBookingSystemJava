package com.busbooking.dao;

import com.busbooking.config.DatabaseConnection;
import com.busbooking.models.Admin;
import java.sql.*;

public class AdminDAO {
    private Connection conn;

    public AdminDAO() {
        this.conn = DatabaseConnection.getConnection();
    }

    // Register a new admin
    public boolean registerAdmin(Admin admin) {
        String sql = "INSERT INTO admins (first_name, last_name, email, phone_number, password_hash, role) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, admin.getFirstName());
            stmt.setString(2, admin.getLastName());
            stmt.setString(3, admin.getEmail());
            stmt.setString(4, admin.getPhoneNumber());
            stmt.setString(5, admin.getPasswordHash());
            stmt.setString(6, admin.getRole());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Fetch admin by email
    public Admin getAdminByEmail(String email) {
        String sql = "SELECT * FROM admins WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Admin(
                        rs.getInt("admin_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Validate admin login
    public boolean validateAdmin(String email, String passwordHash) {
        String sql = "SELECT * FROM admins WHERE email = ? AND password_hash = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, passwordHash);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
