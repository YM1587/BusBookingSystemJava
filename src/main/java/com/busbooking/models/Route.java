package com.busbooking.models;

import java.math.BigDecimal;
import java.time.LocalTime;

public class Route {
    private int routeId;
    private String routeName;
    private String startLocation;
    private String endLocation;
    private BigDecimal distanceKm;
    private LocalTime estimatedDuration;
    private BigDecimal fare;

    // Constructor
    public Route(int routeId, String routeName, String startLocation, String endLocation, BigDecimal distanceKm, LocalTime estimatedDuration, BigDecimal fare) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.distanceKm = distanceKm;
        this.estimatedDuration = estimatedDuration;
        this.fare = fare;
    }

    // Getters and Setters
    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
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

    public BigDecimal getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(BigDecimal distanceKm) {
        this.distanceKm = distanceKm;
    }

    public LocalTime getEstimatedDuration() {
        return estimatedDuration;
    }

    public void setEstimatedDuration(LocalTime estimatedDuration) {
        this.estimatedDuration = estimatedDuration;
    }
    public BigDecimal getFare() {
        return fare;
    }
    public void setFare(BigDecimal fare) {
        this.fare = fare;
    }
    @Override
    public String toString() {
        return startLocation + " → " + endLocation; // This ensures proper display in ComboBox
    }
}
