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
     private String username;
    private String password;
    private String phoneNumber;

    /**
     * Checks if the username is valid.
     * Must contain an underscore and be no more than 5 characters.
     * @param username the username to check
     * @return
     */
    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    /**
     * Checks if the password meets complexity requirements.
     * Must be at least 8 characters, contain a capital letter, a number, and a special character.
     * @param password the password to check
     * @return true if valid, false otherwise
     */
    public boolean checkPasswordComplexity(String password) {
        if (password == null) return false;

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c))       hasCapital = true;
            else if (Character.isDigit(c))      hasNumber  = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    /**
     * Checks if the cell phone number is valid.
     * Must start with +27 and be no more than 12 characters.
     * @param phone the phone number to check
     * @return true if valid, false otherwise
     */
    public boolean checkCellPhoneNumber(String phone) {
        return phone != null && phone.startsWith("+27") && phone.length() <= 12;
    }

    /**
     * Registers a new user if all validation checks pass.
     * Stores the username, password, and phone number if successful.
     * @param username    the chosen username
     * @param password    the chosen password
     * @param phoneNumber the user's phone number
     * @return a message indicating success or the specific validation failure
     */
    public String registerUser(String username, String password, String phoneNumber) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(phoneNumber)) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        this.username    = username;
        this.password    = password;
        this.phoneNumber = phoneNumber;

        return "User registered successfully.";
    }

    /**
     * Checks if the provided credentials match the registered user.
     * @param username the entered username
     * @param password the entered password
     * @return true if credentials match, false otherwise
     */
    public boolean loginUser(String username, String password) {
        return this.username != null
            && this.password != null
            && this.username.equals(username)
            && this.password.equals(password);
    }

    /**
     * Returns a login status message based on whether login was successful.
     * @param success true if login succeeded
     * @return a welcome message or a failure message
     */
    public String returnLoginStatus(boolean success) {
        if (success) {
            return "Welcome " + username + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }

    /** @return the registered username */
    public String getUsername()    { return username; }

    /** @return the registered password */
    public String getPassword()    { return password; }

    /** @return the registered phone number */
    public String getPhoneNumber() { return phoneNumber; }
}