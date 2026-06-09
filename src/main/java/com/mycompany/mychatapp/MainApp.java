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
        Scanner input = new Scanner(System.in);
        Login login   = new Login();

        // ── REGISTRATION ──────────────────────────────────────────────────────
        System.out.println("=== USER REGISTRATION ===");

        System.out.print("Enter a username: ");
        String username = input.nextLine();

        System.out.print("Enter a password: ");
        String password = input.nextLine();

        System.out.print("Enter your South African phone number (+27...): ");
        String phone = input.nextLine();

        String regResponse = login.registerUser(username, password, phone);
        System.out.println(regResponse);

        if (!regResponse.equals("User registered successfully.")) {
            System.out.println("Registration failed. Please restart and try again.");
            input.close();
            return;
        }

        // ── LOGIN ─────────────────────────────────────────────────────────────
        System.out.println("\n=== USER LOGIN ===");

        System.out.print("Enter your username: ");
        String loginUsername = input.nextLine();

        System.out.print("Enter your password: ");
        String loginPassword = input.nextLine();

        boolean loggedIn = login.loginUser(loginUsername, loginPassword);
        System.out.println(login.returnLoginStatus(loggedIn));

        if (!loggedIn) {
            System.out.println("Login failed. Please restart and try again.");
            input.close();
            return;
        }

        // ── LOAD STORED MESSAGES FROM PREVIOUS SESSION ────────────────────────
        Message.loadStoredMessages();

        System.out.println("\nWelcome to QuickChat.");

        // ── MAIN MENU LOOP ────────────────────────────────────────────────────
        boolean running = true;
        while (running) {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");

            String menuChoice = input.nextLine().trim();

            switch (menuChoice) {
                case "1" -> sendMessages(input);
                case "2" -> System.out.println(Message.printMessages());
                case "3" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                case "4" -> storedMessagesMenu(input);
                default  -> System.out.println("Invalid option. Please choose 1, 2, 3, or 4.");
            }
        }

        input.close();
    }

    // ── SEND MESSAGES ─────────────────────────────────────────────────────────

    /**
     * Runs a loop allowing the user to compose and send multiple messages.
     * Validates recipient and message length before presenting send options.
     * @param input the shared Scanner for user input
     */
    private static void sendMessages(Scanner input) {
        int messageNumber = Message.returnTotalMessages() + 1;
        boolean sending   = true;

        while (sending) {
            System.out.println("\n--- New Message ---");
            System.out.print("Enter recipient number (+27...): ");
            String recipient = input.nextLine();

            System.out.print("Enter your message: ");
            String messageText = input.nextLine();

            Message message = new Message(messageNumber, recipient, messageText);

            // Validate recipient
            String recipientCheck = message.checkRecipientCell(recipient);
            System.out.println(recipientCheck);
            if (!recipientCheck.equals("Cell phone number successfully added.")) {
                System.out.println("Message cancelled due to invalid recipient.");
                continue;
            }

            // Validate message length
            String lengthCheck = message.checkMessageLength(messageText);
            System.out.println(lengthCheck);
            if (!lengthCheck.equals("Message ready to send.")) {
                System.out.println("Message cancelled due to length.");
                continue;
            }

            // Send options
            System.out.println("1) Send");
            System.out.println("2) Disregard");
            System.out.println("3) Store");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter 1, 2, or 3.");
                continue;
            }

            System.out.println(message.sentMessage(choice));
            messageNumber++;

            System.out.print("\nSend another message? (yes/no): ");
            if (!input.nextLine().trim().equalsIgnoreCase("yes")) {
                sending = false;
            }
        }

        System.out.println("Total messages sent: " + Message.returnTotalMessages());
    }

    // ── STORED MESSAGES SUB-MENU ──────────────────────────────────────────────

    /**
     * Displays the stored messages sub-menu and processes the user's choice.
     * Provides six features: display all, longest, search by ID,
     * search by recipient, delete by hash, and full report.
     * @param input the shared Scanner for user input
     */
    private static void storedMessagesMenu(Scanner input) {
        boolean inSubMenu = true;

        while (inSubMenu) {
            System.out.println("\n=== STORED MESSAGES MENU ===");
            System.out.println("a) Display all stored messages");
            System.out.println("b) Display longest message");
            System.out.println("c) Search by message ID");
            System.out.println("d) Search by recipient");
            System.out.println("e) Delete by message hash");
            System.out.println("f) Display full report");
            System.out.println("x) Return to main menu");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim().toLowerCase();

            // A temporary Message instance to call instance methods
            Message temp = new Message(0, "", "placeholder text");

            switch (choice) {
                case "a" -> System.out.println(Message.displayStoredMessages());

                case "b" -> System.out.println(temp.displayLongestMessage());

                case "c" -> {
                    System.out.print("Enter message ID to search: ");
                    String id = input.nextLine().trim();
                    System.out.println(temp.searchByMessageID(id));
                }

                case "d" -> {
                    System.out.print("Enter recipient number to search: ");
                    String rec = input.nextLine().trim();
                    System.out.println(temp.searchByRecipient(rec));
                }

                case "e" -> {
                    System.out.print("Enter message hash to delete: ");
                    String hash = input.nextLine().trim();
                    System.out.println(temp.deleteByHash(hash));
                }

                case "f" -> System.out.println(Message.printMessages());

                case "x" -> inSubMenu = false;

                default  -> System.out.println("Invalid option.");
            }
        }
    }
}