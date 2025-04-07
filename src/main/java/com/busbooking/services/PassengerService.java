package com.busbooking.services;

import com.busbooking.dao.PassengerDAO;
import com.busbooking.models.Passenger;
import com.busbooking.utils.PasswordUtil;

public class PassengerService {
    private static Passenger loggedInUser;
    private PassengerDAO passengerDAO; // DAO for database interaction

    public PassengerService() {
        this.passengerDAO = new PassengerDAO(); // Initialize DAO
    }

    // ✅ Get currently logged-in user
    public Passenger getLoggedInUser() {
        return loggedInUser;
    }

    // ✅ Set the logged-in user
    public void setLoggedInUser(Passenger passenger) {
        loggedInUser = passenger;
    }

    // ✅ Logout user
    public void logoutUser() {
        loggedInUser = null;
    }

    // ✅ Authenticate user (verify hashed password)
    public Passenger authenticate(String email, String password) {
        try {
            Passenger passenger = passengerDAO.getPassengerByEmail(email);

            if (passenger != null && PasswordUtil.verifyPassword(password, passenger.getPasswordHash())) {
                loggedInUser = passenger; // Set logged-in user
                return passenger; // Successful authentication
            }
        } catch (Exception e) {
            System.out.println("Authentication error: " + e.getMessage());
        }

        return null; // Authentication failed
    }

    // ✅ Register new passenger with hashed password
    public boolean registerPassenger(Passenger passenger) {
        try {

            // Save passenger using DAO
            return passengerDAO.addPassenger(passenger);
        } catch (Exception e) {
            System.out.println("Error registering passenger: " + e.getMessage());
            return false;
        }
    }

    // ✅ Check if an email already exists
    public boolean emailExists(String email) {
        try {
            return passengerDAO.getPassengerByEmail(email) != null;
        } catch (Exception e) {
            System.out.println("Error checking email existence: " + e.getMessage());
            return false; // Assume email doesn't exist if there's an error
        }
    }
}
