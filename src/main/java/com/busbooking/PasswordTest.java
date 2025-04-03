package com.busbooking;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordTest {
    public static void main(String[] args) {
        String plainTextPassword = "yourpassword"; // Use the actual password you tested
        String storedHash = "$2a$10$8oWcZx506AXIQNeBRlYkWO32o4rj940eLChn8K3Q9v/UTr577wHc."; // Your DB hash

        if (BCrypt.checkpw(plainTextPassword, storedHash)) {
            System.out.println("✅ Password matches!");
        } else {
            System.out.println("❌ Password does NOT match!");
        }
    }
}
