package com.busbooking.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private int bookingId;
    private int passengerId;
    private String pnrNumber;
    private int busId;
    private String seatNumber;
    private LocalDate travelDate;
    private LocalTime departureTime;
    private String routeName;
    private String boardingPoint;
    private BigDecimal totalFare;
    private String bookingStatus; // Pending, Confirmed, Canceled

    // Constructor
    public Booking(int bookingId, int passengerId, String pnrNumber, int busId, String seatNumber,
                   LocalDate travelDate, LocalTime departureTime, String routeName, String boardingPoint,
                   BigDecimal totalFare, String bookingStatus) {
        this.bookingId = bookingId;
        this.passengerId = passengerId;
        this.pnrNumber = pnrNumber;
        this.busId = busId;
        this.seatNumber = seatNumber;
        this.travelDate = travelDate;
        this.departureTime = departureTime;
        this.routeName = routeName;
        this.boardingPoint = boardingPoint;
        this.totalFare = totalFare;
        this.bookingStatus = bookingStatus;
    }

    // Getters and Setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public String getPnrNumber() { return pnrNumber; }
    public void setPnrNumber(String pnrNumber) { this.pnrNumber = pnrNumber; }

    public int getBusId() { return busId; }
    public void setBusId(int busId) { this.busId = busId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public LocalDate getTravelDate() { return travelDate; }
    public void setTravelDate(LocalDate travelDate) { this.travelDate = travelDate; }

    public LocalTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalTime departureTime) { this.departureTime = departureTime; }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) { this.routeName = routeName; }

    public String getBoardingPoint() { return boardingPoint; }
    public void setBoardingPoint(String boardingPoint) { this.boardingPoint = boardingPoint; }

    public BigDecimal getTotalFare() { return totalFare; }
    public void setTotalFare(BigDecimal totalFare) { this.totalFare = totalFare; }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }
}
