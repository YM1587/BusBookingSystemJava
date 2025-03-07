package com.busbooking.models;

public class Bus {
    private int busId;
    private String busNumber;
    private String busType;
    private int capacity;
    private String operatorName;

    // Constructor
    public Bus(int busId, String busNumber, String busType, int capacity, String operatorName) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.busType = busType;
        this.capacity = capacity;
        this.operatorName = operatorName;
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
}
