package com.busbooking.models;

import javafx.beans.property.*;

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
    private String bookingStatus;
    private String transactionReference;
    private String paymentStatus;

    // JavaFX properties for UI binding
    private final StringProperty routeProperty = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<LocalTime> departureTimeProperty = new SimpleObjectProperty<>();
    private final StringProperty seatProperty = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> fareProperty = new SimpleObjectProperty<>();

    // Full constructor
    public Booking(int bookingId, int passengerId, String pnrNumber, int busId, String seatNumber,
                   LocalDate travelDate, LocalTime departureTime, String routeName, String boardingPoint,
                   BigDecimal totalFare, String bookingStatus, String transactionReference, String paymentStatus) {
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
        this.transactionReference = transactionReference;
        this.paymentStatus = paymentStatus;

        // Initialize JavaFX properties
        this.routeProperty.set(routeName);
        this.dateProperty.set(travelDate);
        this.departureTimeProperty.set(departureTime);
        this.seatProperty.set(seatNumber);
        this.fareProperty.set(totalFare);
    }

    // Optional mock constructor for testing/demo
    public Booking(String route, String date, String time, String seat, double fare) {
        this(0, 0, "TESTPNR", 0, seat,
                LocalDate.parse(date),
                LocalTime.parse(time.replace(" AM", "").replace(" PM", "")),
                route, "", BigDecimal.valueOf(fare),
                "Confirmed", "TXN123456", "Paid");
    }

    // Getters and setters
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public String getPnrNumber() { return pnrNumber; }
    public void setPnrNumber(String pnrNumber) { this.pnrNumber = pnrNumber; }

    public int getBusId() { return busId; }
    public void setBusId(int busId) { this.busId = busId; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
        this.seatProperty.set(seatNumber);
    }

    public LocalDate getTravelDate() { return travelDate; }
    public void setTravelDate(LocalDate travelDate) {
        this.travelDate = travelDate;
        this.dateProperty.set(travelDate);
    }

    public LocalTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
        this.departureTimeProperty.set(departureTime);
    }

    public String getRouteName() { return routeName; }
    public void setRouteName(String routeName) {
        this.routeName = routeName;
        this.routeProperty.set(routeName);
    }

    public String getBoardingPoint() { return boardingPoint; }
    public void setBoardingPoint(String boardingPoint) { this.boardingPoint = boardingPoint; }

    public BigDecimal getTotalFare() { return totalFare; }
    public void setTotalFare(BigDecimal totalFare) {
        this.totalFare = totalFare;
        this.fareProperty.set(totalFare);
    }

    public String getBookingStatus() { return bookingStatus; }
    public void setBookingStatus(String bookingStatus) { this.bookingStatus = bookingStatus; }

    public String getTransactionReference() { return transactionReference; }
    public void setTransactionReference(String transactionReference) { this.transactionReference = transactionReference; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    // JavaFX property getters
    public StringProperty routeProperty() {
        return routeProperty;
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return dateProperty;
    }

    public ObjectProperty<LocalTime> departureTimeProperty() {
        return departureTimeProperty;
    }

    public StringProperty seatProperty() {
        return seatProperty;
    }

    public ObjectProperty<BigDecimal> fareProperty() {
        return fareProperty;
    }

    @Override
    public String toString() {
        return String.format("Booking ID: %d\nPNR: %s\nRoute: %s\nTravel Date: %s\nDeparture Time: %s\nSeat: %s\nFare: Ksh %.2f\nStatus: %s\nTransaction Ref: %s\nPayment Status: %s",
                bookingId, pnrNumber, routeName, travelDate, departureTime, seatNumber, totalFare, bookingStatus, transactionReference, paymentStatus);
    }
}
