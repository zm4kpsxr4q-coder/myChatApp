/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.util.Scanner;

/**
 *
 * @author HLUMELO
 */
public class MainApp {
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Login login = new Login();

            System.out.println("=== USER REGISTRATION ===");
            System.out.print("Enter a username: ");
            String username = input.nextLine();

            System.out.print("Enter a password: ");
            String password = input.nextLine();

            System.out.print("Enter your South African phone number (+27...): ");
            String phone = input.nextLine();

            String response = login.registerUser(username, password, phone);
            System.out.println(response);

            if (response.equals("User registered successfully.")) {
                System.out.println("\n=== USER LOGIN ===");
                System.out.print("Enter your username: ");
                String loginUsername = input.nextLine();

                System.out.print("Enter your password: ");
                String loginPassword = input.nextLine();

                boolean loggedIn = login.loginUser(loginUsername, loginPassword);
                String loginMessage = login.returningLoginStatus(loggedIn);
                System.out.println(loginMessage);
            }
        }
    }
}
