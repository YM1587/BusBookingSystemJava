package com.busbooking.dao;

import com.busbooking.models.Notification;
import com.busbooking.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {
//Adds A new notification record to the database
    public void addNotification(Notification notification) {
        String sql = "INSERT INTO notifications (passenger_id, message, notification_type, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, notification.getPassengerId());
            stmt.setString(2, notification.getMessage());
            stmt.setString(3, notification.getNotificationType());
            stmt.setString(4, notification.getStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// Retrieves a notification by its ID
    public Notification getNotificationById(int id) {
        String sql = "SELECT * FROM notifications WHERE notification_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Notification(
                        rs.getInt("notification_id"),
                        rs.getInt("passenger_id"),
                        rs.getString("message"),
                        rs.getString("notification_type"),
                        rs.getString("status"),
                        rs.getTimestamp("sent_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//Retrieves all notifications from the database.
    public List<Notification> getAllNotifications() {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM notifications";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                notifications.add(new Notification(
                        rs.getInt("notification_id"),
                        rs.getInt("passenger_id"),
                        rs.getString("message"),
                        rs.getString("notification_type"),
                        rs.getString("status"),
                        rs.getTimestamp("sent_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }
//Updates the status of a notification.
    public void updateNotificationStatus(int notificationId, String newStatus) {
        String sql = "UPDATE notifications SET status = ? WHERE notification_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, notificationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//Deletes a notification record by its ID.
    public void deleteNotification(int id) {
        String sql = "DELETE FROM notifications WHERE notification_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
