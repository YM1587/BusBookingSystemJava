package com.busbooking;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordTest {
    public static void main(String[] args) {
        String plainTextPassword = "Rose1234!@#$"; // Use the actual password you tested
        String storedHash = "$2a$12$hk22Z9SOB08sJnxwbg2wKu4amr/0QPE5eBC164VJSey/57CPgpB0K"; // Your DB hash

        if (BCrypt.checkpw(plainTextPassword, storedHash)) {
            System.out.println("Password matches!");
        } else {
            System.out.println("Password does NOT match!");
        }
    }
}
