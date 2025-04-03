package com.busbooking.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    /**
     * Hashes a password using BCrypt with automatic salting.
     * @param password The plain text password.
     * @return A securely hashed password.
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        return BCrypt.hashpw(password, BCrypt.gensalt(12)); // 12 is the work factor (cost parameter)
    }

    /**
     * Verifies a password against a stored hashed password.
     * @param enteredPassword The password entered by the user.
     * @param storedHash The hashed password stored in the database.
     * @return true if the password matches, false otherwise.
     */
    public static boolean verifyPassword(String enteredPassword, String storedHash) {
        if (enteredPassword == null || storedHash == null || storedHash.isEmpty()) {
            return false;
        }
        return BCrypt.checkpw(enteredPassword, storedHash);
    }

    /**
     * Checks if a password meets security requirements.
     * @param password The password to check.
     * @return true if the password is strong, false otherwise.
     */
    public static boolean isPasswordStrong(String password) {
        if (password == null || password.length() < 8) {
            return false; // Must be at least 8 characters
        }
        return password.matches(".*[A-Z].*")   // At least one uppercase letter
                && password.matches(".*[a-z].*")   // At least one lowercase letter
                && password.matches(".*\\d.*")     // At least one digit
                && password.matches(".*[@#$%^&+=!().*].*"); // At least one special character
    }
}
