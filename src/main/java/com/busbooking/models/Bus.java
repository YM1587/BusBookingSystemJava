package com.busbooking.models;

public class Bus {
    private int busId;
    private String busNumber;
    private String busType;
    private int capacity = 41;
    private String operatorName;
    private String routeName;
    private String startLocation;
    private String endLocation;
    private double fare;
    private String departureTime;
    private int availableSeats;
    private int totalSeats; // 🔹 NEW FIELD

    // Updated constructor (you can add totalSeats if needed in future constructors)
    public Bus(int busId, String busNumber, String busType, String operatorName, String routeName,
               String startLocation, String endLocation, double fare, String departureTime) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.busType = busType;
        this.operatorName = operatorName;
        this.routeName = routeName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.fare = fare;
        this.departureTime = departureTime;
        this.availableSeats = capacity;
    }

    // Getters and Setters
    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    // 🔹 Getter and Setter for totalSeats
    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    // Override toString() with totalSeats
    @Override
    public String toString() {
        return "Bus{" +
                "busId=" + busId +
                ", busNumber='" + busNumber + '\'' +
                ", busType='" + busType + '\'' +
                ", capacity=" + capacity +
                ", operatorName='" + operatorName + '\'' +
                ", routeName='" + routeName + '\'' +
                ", startLocation='" + startLocation + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", fare=" + fare +
                ", departureTime='" + departureTime + '\'' +
                ", availableSeats=" + availableSeats +
                ", totalSeats=" + totalSeats +
                '}';
    }
}
