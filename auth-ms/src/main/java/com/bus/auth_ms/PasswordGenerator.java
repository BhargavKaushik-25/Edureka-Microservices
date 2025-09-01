package com.bus.auth_ms; // use your package

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "myNewPassword123"; // change to your desired password
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("BCrypt encoded password: " + encodedPassword);
    }
}
