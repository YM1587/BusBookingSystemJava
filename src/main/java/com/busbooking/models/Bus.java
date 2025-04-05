package com.busbooking.models;


public class Bus {
    private String busNumber;
    private String busType;
    private int capacity;
    private String operatorName;
    private String routeName;
    private String startLocation;
    private String endLocation;
    private double fare;
    private int availableSeats;
    private String departureTime; // Add this field for departure time

    public Bus(String busNumber, String busType, int capacity, String operatorName, String routeName, String startLocation, String endLocation, double fare, int availableSeats, String departureTime) {
        this.busNumber = busNumber;
        this.busType = busType;
        this.capacity = capacity;
        this.operatorName = operatorName;
        this.routeName = routeName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fare = fare;
        this.availableSeats = availableSeats;
        this.departureTime = departureTime; // Initialize departure time
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

    public double getFare() {
        return fare;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public String getDepartureTime() {  // Add getter for departureTime
        return departureTime;
    }
}
