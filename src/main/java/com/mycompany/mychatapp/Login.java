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

    public boolean checkUsername(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
<<<<<<< Updated upstream
=======
        if (password == null) return false;
>>>>>>> Stashed changes

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {

            char c = password.charAt(i);
<<<<<<< Updated upstream

            if (Character.isUpperCase(c)) {
                hasCapital = true;
            } 
            else if (Character.isDigit(c)) {
                hasNumber = true;
            } 
            else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
=======
            if (Character.isUpperCase(c)) hasCapital = true;
            else if (Character.isDigit(c)) hasNumber = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
>>>>>>> Stashed changes
        }

        return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
    }

    public boolean checkCellPhoneNumber(String phoneNumber) {
        return phoneNumber.startsWith("+27") && phoneNumber.length() <= 12;
    }

    public String registerUser(String username, String password, String phoneNumber) {
        if (!checkUsername(username))
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";

        if (!checkPasswordComplexity(password))
            return "Password is not correctly formatted; please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.";

        if (!checkCellPhoneNumber(phoneNumber))
            return "Cell phone number incorrectly formatted or does not contain international code.";

        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;

        // FIX: changed from "User registered successfully." to match LoginTest expectation
        return "Registration successful.";
    }

    public boolean loginUser(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String returningLoginStatus(boolean success) {
        if (success)
            return "Welcome " + username + ", it is great to see you again.";
        else
            return "Username or password incorrect, please try again.";
    }

<<<<<<< Updated upstream
    boolean checkUserName(String abcdi) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    boolean loginUser() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    void registerUser() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
=======
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
}
>>>>>>> Stashed changes
