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
    private String departureTime; // Field for departure time

    // Constructor
    public Bus(String busNumber, String busType, int capacity, String operatorName, String routeName,
               String startLocation, String endLocation, double fare, String departureTime) {
        this.busNumber = busNumber;
        this.busType = busType;
        this.capacity = capacity;
        this.operatorName = operatorName;
        this.routeName = routeName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fare = fare;
        this.departureTime = departureTime;
    }

    // Getters
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

    public String getDepartureTime() {
        return departureTime;
    }

    // Override toString() for easy logging and debugging
    @Override
    public String toString() {
        return "Bus{" +
                "busNumber='" + busNumber + '\'' +
                ", busType='" + busType + '\'' +
                ", capacity=" + capacity +
                ", operatorName='" + operatorName + '\'' +
                ", routeName='" + routeName + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", fare=" + fare +
                ", departureTime='" + departureTime + '\'' +
                '}';
    }
}
