package com.busbooking.utils;

import com.busbooking.models.Passenger;

public class SessionManager {
    private static Passenger loggedInUser = null;

    // Store logged-in user
    public static void setLoggedInUser(Passenger passenger) {
        loggedInUser = passenger;
    }

    // Retrieve logged-in user
    public static Passenger getLoggedInUser() {
        return loggedInUser;
    }

    // Clear session on logout
    public static void logout() {
        loggedInUser = null;
    }

    // Check if user is logged in
    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }
}
