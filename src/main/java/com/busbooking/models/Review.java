package com.busbooking.models;

import java.sql.Timestamp;

public class Review {
    private int reviewId;
    private int passengerId;
    private int busId;
    private int rating;
    private String comment;
    private Timestamp reviewDate;

    public Review(int reviewId, int passengerId, int busId, int rating, String comment, Timestamp reviewDate) {
        this.reviewId = reviewId;
        this.passengerId = passengerId;
        this.busId = busId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    // Getters and Setters
    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public int getBusId() { return busId; }
    public void setBusId(int busId) { this.busId = busId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Timestamp getReviewDate() { return reviewDate; }
    public void setReviewDate(Timestamp reviewDate) { this.reviewDate = reviewDate; }
}
