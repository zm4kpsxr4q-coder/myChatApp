/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

/**
 *
 * @author HLUMELO
 */
public class Login {
     // Declare variables
    private String username;
    private String password;
    private String phoneNumber;

    // ==============================================================
    // 1. CHECK USERNAME
    // Must contain "_" and be no more than 5 characters
    // ==============================================================
    public boolean checkUsername(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    // ==============================================================
    // 2. CHECK PASSWORD COMPLEXITY
    // At least 8 characters, 1 capital, 1 number, 1 special char
    // ==============================================================
    public boolean checkPasswordComplexity(String password) {
        if (password == null) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    // ==============================================================
    // 3. CHECK CELL PHONE NUMBER
    // Must start with +27 and be <= 12 characters
    // ==============================================================
    public boolean checkCellPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.startsWith("+27") && phoneNumber.length() <= 12;
    }

    // ==============================================================
    // 4. REGISTER USER
    // Validates all inputs and stores them if correct
    // ==============================================================
    public String registerUser(String username, String password, String phoneNumber) {

        if (!checkUsername(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber(phoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        // Store values if all checks pass
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;

        return "User registered successfully.";
    }

    // ==============================================================
    // 5. LOGIN USER
    // ==============================================================
    public boolean loginUser(String username, String password) {
        return this.username != null
                && this.password != null
                && this.username.equals(username)
                && this.password.equals(password);
    }

    // ==============================================================
    // 6. RETURN LOGIN STATUS
    // ==============================================================
    public String returningLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + username + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
