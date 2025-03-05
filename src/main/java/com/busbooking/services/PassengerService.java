package com.busbooking.services;

import com.busbooking.dao.PassengerDAO;
import com.busbooking.models.Passenger;

public class PassengerService {
    private static Passenger loggedInUser;
    private PassengerDAO passengerDAO; // Added DAO for database interaction

    public PassengerService() {
        this.passengerDAO = new PassengerDAO(); // Initialize DAO
    }

    public Passenger getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Passenger passenger) {
        loggedInUser = passenger;
    }

    public void logoutUser() {
        loggedInUser = null;
    }

    // ✅ Added authentication method
    public Passenger authenticate(String email, String password) {
        Passenger passenger = passengerDAO.getPassengerByEmail(email);
        if (passenger != null && passenger.getPasswordHash().equals(password)) {
            loggedInUser = passenger; // Set the logged-in user
            return passenger; // Successful authentication
        }
        return null; // Authentication failed
    }
}
