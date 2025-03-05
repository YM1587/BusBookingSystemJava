package com.busbooking.dao;

import com.busbooking.models.Payment;
import com.busbooking.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
//adds a new payment to the database
    public void addPayment(Payment payment) {
        String sql = "INSERT INTO payments (booking_id, passenger_id, amount_paid, payment_method, payment_status, transaction_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, payment.getBookingId());
            stmt.setInt(2, payment.getPassengerId());
            stmt.setBigDecimal(3, payment.getAmountPaid());
            stmt.setString(4, payment.getPaymentMethod());
            stmt.setString(5, payment.getPaymentStatus());
            stmt.setString(6, payment.getTransactionId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//retrieves a payment record by its ID
    public Payment getPaymentById(int id) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("passenger_id"),
                        rs.getBigDecimal("amount_paid"),
                        rs.getString("payment_method"),
                        rs.getString("payment_status"),
                        rs.getString("transaction_id"),
                        rs.getTimestamp("payment_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
//Retrieves all payment records from the database
    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        String sql = "SELECT * FROM payments";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                payments.add(new Payment(
                        rs.getInt("payment_id"),
                        rs.getInt("booking_id"),
                        rs.getInt("passenger_id"),
                        rs.getBigDecimal("amount_paid"),
                        rs.getString("payment_method"),
                        rs.getString("payment_status"),
                        rs.getString("transaction_id"),
                        rs.getTimestamp("payment_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
//updates the status of a payment
    public void updatePaymentStatus(int paymentId, String newStatus) {
        String sql = "UPDATE payments SET payment_status = ? WHERE payment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newStatus);
            stmt.setInt(2, paymentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//Deletes a payment record by its ID
    public void deletePayment(int id) {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
