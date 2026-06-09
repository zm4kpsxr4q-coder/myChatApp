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
<<<<<<< Updated upstream
            
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
=======

            // ========= REGISTRATION =================================
>>>>>>> Stashed changes
            System.out.println("=== USER REGISTRATION ===");
            
            System.out.print("Enter a username: ");
String username = input.nextLine();
          
            
            System.out.print("Enter your South African phone number (+27...):");
String phone = input.nextLine();
            
            // Call the registerUser method and store the message it returns
            String response = login.registerUser(username, password, phone);
            
            // Show the registration message
            System.out.println(response);
<<<<<<< Updated upstream
            
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
=======

            // FIX: updated string to match Login.registerUser() return value
            if (!response.equals("Registration successful.")) return;

            // ============== LOGIN ========================================
            System.out.println("\n=== USER LOGIN ===");
            System.out.print("Enter your username: ");
            String loginUsername = input.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = input.nextLine();

            boolean loggedIn = login.loginUser(loginUsername, loginPassword);
            System.out.println(login.returningLoginStatus(loggedIn));

            if (!loggedIn) return;

            // ============= MESSAGING ===================================
            // FIX: added messaging flow so MainApp actually uses Message.java
            int messageNumber = 1;

            while (true) {
                System.out.println("\n=== NEW MESSAGE ===");
                System.out.print("Enter recipient number (+27...): ");
                String recipient = input.nextLine();

                System.out.print("Enter your message: ");
                String messageText = input.nextLine();

                Message message = new Message(messageNumber, recipient, messageText);

                String recipientCheck = message.checkRecipientCell(recipient);
                System.out.println(recipientCheck);
                if (!recipientCheck.equals("Cell phone number successfully added.")) continue;

                String lengthCheck = message.checkMessageLength(messageText);
                System.out.println(lengthCheck);
                if (!lengthCheck.equals("Message ready to send.")) continue;

                System.out.println("1 - Send  |  2 - Disregard  |  3 - Store");
                System.out.print("Enter choice: ");

                int choice;
                try {
                    choice = Integer.parseInt(input.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Enter 1, 2, or 3.");
                    continue;
                }

                System.out.println(message.sentMessage(choice));
                messageNumber++;

                System.out.print("\nSend another message? (yes/no): ");
                if (!input.nextLine().trim().equalsIgnoreCase("yes")) break;
            }

            // =========== FINAL REPORT ==================================
            System.out.println("\n" + Message.printMessages());
            System.out.println("Total messages sent: " + Message.returnTotalMessages());
        }
    }
}
>>>>>>> Stashed changes
