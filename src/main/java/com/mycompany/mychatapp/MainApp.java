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
    private static String password;
         // Main App must:
    // Demand username,password,phone number
    // Register the user
    // Show registration feedback
    // Demand the user to login
    // Call login methods
    // Show login feedback
        public static void main(String[] args) {
            
            // Scanner allows the user to enter information
            Scanner input = new Scanner(System.in);
            
            // Create an object of the login class so we can call its methods
            Login login = new Login();
            
           // Call existing methods
              login.registerUser();
              boolean loggedIn = false;
              
         while (loggedIn){
             loggedIn = login.loginUser();
             
           if (!loggedIn){
               System.out.println("Try Again");
           }
         }
            
            //- - - REGISTRATION SECTION - - -
            System.out.println("=== USER REGISTRATION ===");
            
            System.out.print("Enter a username: ");
String username = input.nextLine();
          
            
            System.out.print("Enter your South African phone number (+27...):");
String phone = input.nextLine();
            
            // Call the registerUser method and store the message it returns
            String response = login.registerUser(username, password, phone);
            
            // Show the registration message
            System.out.println(response);
            
            // - - - LOGIN SECTION - - -
            System.out.println("\n=== USER LOGIN ===");
            
           System.out.print("Enter your username: ");
            
  System.out.print("Enter your password: ");
        // Call loginUser to check if details match the stored ones
            
            
            //Print out the correct login message
       String loginMessage = login.returningLoginStatus(loggedIn);
System.out.println(loginMessage);
        }

}
