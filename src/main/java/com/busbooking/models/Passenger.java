package com.busbooking.models;

import java.sql.Timestamp;

public class Passenger {
    private int passengerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String passwordHash; // Added password for authentication
    private String role; // "Passenger" or "Admin"
    private Timestamp createdAt; // Timestamp for record creation


    //No-argument constructor (Fixes your error)
    public Passenger() {
    }

    // Constructor
    public Passenger(int passengerId, String firstName, String lastName, String email, String phoneNumber, String passwordHash, String role, Timestamp createdAt) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
        this.role = role;
        this.createdAt = createdAt;
    }
    // Overloaded constructor (Fixes your issue)
    public Passenger(int passengerId, String firstName, String lastName, String email, String passwordHash) {
        this.passengerId = passengerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = ""; // Default empty
        this.role = "Passenger"; // Default role
        this.createdAt = new Timestamp(System.currentTimeMillis()); // Auto timestamp
    }


    // Getters and Setters
    public int getPassengerId() { return passengerId; }
    public void setPassengerId(int passengerId) { this.passengerId = passengerId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    // Get profile image path (Returns default avatar if no image is set)
    public String getProfileImagePath() {
        return "/assets/default_avatar.png"; // Default avatar
    }
}
