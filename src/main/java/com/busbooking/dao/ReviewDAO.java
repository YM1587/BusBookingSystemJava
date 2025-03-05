package com.busbooking.dao;

import com.busbooking.models.Review;
import com.busbooking.config.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    public void addReview(Review review) {
        String sql = "INSERT INTO reviews (passenger_id, bus_id, rating, comment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, review.getPassengerId());
            stmt.setInt(2, review.getBusId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Review getReviewById(int id) {
        String sql = "SELECT * FROM reviews WHERE review_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Review(
                        rs.getInt("review_id"),
                        rs.getInt("passenger_id"),
                        rs.getInt("bus_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("review_date")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("review_id"),
                        rs.getInt("passenger_id"),
                        rs.getInt("bus_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("review_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
