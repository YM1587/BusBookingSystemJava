package com.busbooking.models;
import java.math.BigDecimal;
import java.time.LocalTime;

public class Bus {
    private String busNumber;
    private String busType;
    private int capacity;
    private String operatorName;
    private String routeName;
    private String startLocation;
    private String endLocation;
    private BigDecimal fare;
    private int availableSeats;

    public Bus(String busNumber, String busType, int capacity, String operatorName, String routeName, String startLocation, String endLocation, BigDecimal fare, int availableSeats) {
        this.busNumber = busNumber;
        this.busType = busType;
        this.capacity = capacity;
        this.operatorName = operatorName;
        this.routeName = routeName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fare = fare;
        this.availableSeats = availableSeats;
    }

    // Getters and setters
    public String getBusNumber() {
        return busNumber;
    }

    public String getBusType() {
        return busType;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getRouteName() {
        return routeName;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public BigDecimal getFare() {
        return fare;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
