package com.busbooking;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHasher {

    /**
     * Hashes a password using BCrypt.
     *
     * @param plainTextPassword The password to hash.
     * @return The hashed password.
     */
    public static String hashPassword(String plainTextPassword) {
        // Generate a salt and hash the password with BCrypt
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt(12));  // 12 is the log rounds (cost factor)
    }

    /**
     * Verifies if the given password matches the stored hash.
     *
     * @param plainTextPassword The plain text password entered by the user.
     * @param storedHash The hashed password retrieved from the database.
     * @return true if the password matches, false otherwise.
     */
    public static boolean verifyPassword(String plainTextPassword, String storedHash) {
        return BCrypt.checkpw(plainTextPassword, storedHash);
    }

    public static void main(String[] args) {
        // Example usage:

        String plainTextPassword = "Rose1234!@#$"; // Password to hash
        String storedHash = hashPassword(plainTextPassword); // Store this hash in the DB
        System.out.println("Stored Hash: " + storedHash); // Just for checking, you can ignore this in real implementation

        // Verifying the password entered by the user
        if (verifyPassword("Rose1234!@#$", storedHash)) {
            System.out.println("Password matches!");
        } else {
            System.out.println("Password does NOT match!");
        }
    }
}
