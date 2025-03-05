package com.busbooking.models;

import java.sql.Timestamp;

public class Seat {
    private int seatId;
    private int busId;
    private String seatNumber;
    private String seatStatus;
    private Timestamp createdAt;

    public Seat(int seatId, int busId, String seatNumber, String seatStatus, Timestamp createdAt) {
        this.seatId = seatId;
        this.busId = busId;
        this.seatNumber = seatNumber;
        this.seatStatus = seatStatus;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getSeatId() { return seatId; }
    public void setSeatId(int seatId) { this.seatId = seatId; }

    public int getBusId() { return busId; }
    public void setBusId(int busId) { this.busId = busId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public String getSeatStatus() { return seatStatus; }
    public void setSeatStatus(String seatStatus) { this.seatStatus = seatStatus; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
