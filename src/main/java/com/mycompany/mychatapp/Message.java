/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author HLUMELO
 */
public class Message {
     private String messageID;
    private int messageNumber; 
    private String recipient;
    private String messageText;
    private String messageHash;

    private static int totalMessages = 0;

    // Constructor used in tests
    public Message(int messageNumber) {

        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
    }

    // Optional constructor
    public Message(int messageNumber, String recipient, String messageText) {

        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageID = generateMessageID();
        this.messageHash = createMessageHash();
    }

    // Generate random 10-digit ID
    private String generateMessageID() {

        Random rand = new Random();

        String id = "";

        for (int i = 0; i < 10; i++) {
            id += rand.nextInt(10);
        }

        return id;
    }

    // Getters and setters
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageID() {
        return messageID;
    }

    // Check ID length
    public boolean checkMessageID() {

        return messageID.length() == 10;
    }

    // Validate recipient number
    public String checkRecipientCell(String recipient) {

        if (recipient.startsWith("+") && recipient.length() <= 13) {

            return "Cell phone number successfully added.";

        } else {

            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    // Message length validation
    public String checkMessageLength(String message) {

        if (message.length() <= 250) {

            return "Message ready to send.";

        } else {

            int over = message.length() - 250;

            return "Message exceeds 250 characters by "
                    + over
                    + ", please reduce size.";
        }
    }

    // Create hash
    public String createMessageHash() {

        String idPart = messageID.substring(0, 2);

        String[] words = messageText.split(" ");

        String firstWord = words[0];

        String lastWord = words[words.length - 1];

        // Remove punctuation
        lastWord = lastWord.replaceAll("[^a-zA-Z]", "");

        String hash = idPart
                + ":"
                + messageNumber
                + ":"
                + firstWord
                + lastWord;

        messageHash = hash.toUpperCase();

        return messageHash;
    }

    // Send
    // store 
    //disregard
    public String sentMessage() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("1) Send Message");
        System.out.println("2) Disregard Message");
        System.out.println("3) Store Message");

        int choice = scanner.nextInt();

        switch (choice) {

            case 1:

                totalMessages++;

                printMessageDetails();

                return "Message successfully sent.";

            case 2:

                return "Press 0 to delete the message.";

            case 3:

                storeMessage();

                return "Message successfully stored.";

            default:

                return "Invalid option.";
        }
    }

    // Print details
    public void printMessageDetails() {

        System.out.println("Message ID: " + messageID);
        System.out.println("Message Hash: " + createMessageHash());
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + messageText);
    }

    // Total messages
    public static int returnTotalMessages() {

        return totalMessages;
    }

    // Store message
    public void storeMessage() {

        try {

            FileWriter file = new FileWriter("messages.json", true);

            file.write("Message ID: " + messageID + "\n");
            file.write("Recipient: " + recipient + "\n");
            file.write("Message: " + messageText + "\n");
            file.write("-----------------------\n");

            file.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
