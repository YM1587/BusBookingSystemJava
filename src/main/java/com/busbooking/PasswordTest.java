package com.busbooking;

import org.mindrot.jbcrypt.BCrypt;

/*public class PasswordTest {
    public static void main(String[] args) {
        String plainTextPassword = "Bruce1234!@#$"; // Use an actual password you enter
        String storedHash = "$$2a$10$XCb.dPOS2bfwgyNjPqCYduU/1k5OWykA.wYpni3CxI9rUuoZwT2/q"; // Your DB hash

        if (BCrypt.checkpw(plainTextPassword, storedHash)) {
            System.out.println("✅ Password matches!");
        } else {
            System.out.println("❌ Password does NOT match!");
        }
    }
}*/




public class PasswordTest {
    public static void main(String[] args) {
        // The password the user is trying to log in with
        String enteredPassword = "dan1234!@#$";

        // The hashed password stored in the database
        String storedHash = "$2a$10$DeHieTAd3F8sXBzpkAShrOXBLaaCn/xSApun2WrcYdg5SOf.Nt6Qe";

        // Compare entered password with stored hash
        boolean match = BCrypt.checkpw(enteredPassword, storedHash);

        // Print the result
        System.out.println("Password Match: " + match);
    }
}
/*public class PasswordTest {
    public static void main(String[] args) {
        // Password to test
        String originalPassword = "dan1234!@#$";

        // Generate a new hashed password
        String newHash = BCrypt.hashpw(originalPassword, BCrypt.gensalt(10));

        System.out.println("New Hashed Password: " + newHash);
    }
}*/
