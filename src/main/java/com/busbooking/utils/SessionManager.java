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
/*Since your bus booking system needs to track the logged-in user across multiple screens,
we need a Session Handler to store and retrieve the current user. This will ensure that
once a passenger logs in, their details are available throughout the system.

 */