/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mychatapp;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author HLUMELO
 */
public class Message {
     private String messageID;
    private int    messageNumber;
    private String recipient;
    private String messageText;
    private String messageHash;

    // ── Five parallel static arrays (Part 3) ─────────────────────────────────
    private static List<String> sentMessages        = new ArrayList<>();
    private static List<String> disregardedMessages = new ArrayList<>();
    private static List<String> storedMessages      = new ArrayList<>();
    private static List<String> messageHashes       = new ArrayList<>();
    private static List<String> messageIDs          = new ArrayList<>();
    private static List<String> recipientList       = new ArrayList<>();

    private static int totalMessages = 0;

    // ── Constructors ──────────────────────────────────────────────────────────

    /**
     * Constructor used in tests where only a message number is needed.
     * @param messageNumber the message sequence number
     */
    public Message(int messageNumber) {
        this.messageNumber = messageNumber;
        this.messageID     = generateMessageID();
    }

    /**
     * Full constructor used during normal application flow.
     * @param messageNumber the message sequence number
     * @param recipient     the recipient's cell number
     * @param messageText   the message content
     */
    public Message(int messageNumber, String recipient, String messageText) {
        this.messageNumber = messageNumber;
        this.recipient     = recipient;
        this.messageText   = messageText;
        this.messageID     = generateMessageID();
        this.messageHash   = createMessageHash();
    }

    // ── ID generation ─────────────────────────────────────────────────────────

    /**
     * Generates a random 10-digit numeric message ID.
     * @return a 10-character numeric string
     */
    private String generateMessageID() {
        Random rand = new Random();
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(rand.nextInt(10));
        }
        return id.toString();
    }

    // ── Getters and Setters ───────────────────────────────────────────────────

    /** @return the message ID */
    public String getMessageID()   { return messageID; }

    /** @return the recipient cell number */
    public String getRecipient()   { return recipient; }

    /** @param recipient the recipient to set */
    public void setRecipient(String recipient) { this.recipient = recipient; }

    /** @return the message text */
    public String getMessageText() { return messageText; }

    /** @param messageText the message text to set */
    public void setMessageText(String messageText) { this.messageText = messageText; }

    // ── Validation ────────────────────────────────────────────────────────────

    /**
     * Checks whether the generated message ID is exactly 10 characters.
     * @return true if the ID is 10 characters long
     */
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    /**
     * Validates the recipient cell number format.
     * Must start with + and be no more than 13 characters.
     * @param recipient the cell number to validate
     * @return a success or failure message
     */
    public String checkRecipientCell(String recipient) {
        if (recipient != null && recipient.startsWith("+") && recipient.length() <= 13) {
            return "Cell phone number successfully added.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    /**
     * Validates that the message does not exceed 250 characters.
     * @param message the message text to check
     * @return success message or failure message including character overage
     */
    public String checkMessageLength(String message) {
        if (message.length() <= 250) {
            return "Message ready to send.";
        }
        int over = message.length() - 250;
        return "Message exceeds 250 characters by " + over + ", please reduce size.";
    }

    // ── Hash ──────────────────────────────────────────────────────────────────

    /**
     * Creates a message hash using the first two digits of the ID,
     * the message number, and the first and last words of the message text.
     * Format: XX:N:FIRSTWORDLASTWORD (all uppercase)
     * @return the generated hash string
     */
    public String createMessageHash() {
        String idPart    = messageID.substring(0, 2);
        String[] words   = messageText.split(" ");
        String firstWord = words[0];
        String lastWord  = words[words.length - 1].replaceAll("[^a-zA-Z]", "");

        messageHash = (idPart + ":" + messageNumber + ":" + firstWord + lastWord).toUpperCase();
        return messageHash;
    }

    // ── Send / Store / Discard ────────────────────────────────────────────────

    /**
     * Processes the user's choice for the message: send, disregard, or store.
     * Populates the relevant arrays based on the choice made.
     * @param choice 1 = Send, 2 = Disregard, 3 = Store
     * @return a string describing the outcome
     */
    public String sentMessage(int choice) {
        return switch (choice) {
            case 1 -> {
                totalMessages++;
                sentMessages.add(messageText);
                messageHashes.add(messageHash);
                messageIDs.add(messageID);
                recipientList.add(recipient);
                printMessageDetails();
                yield "Message successfully sent.";
            }
            case 2 -> {
                disregardedMessages.add(messageText);
                yield "Press 0 to delete the message.";
            }
            case 3 -> {
                messageHashes.add(messageHash);
                messageIDs.add(messageID);
                storeMessage();
                yield "Message successfully stored.";
            }
            default -> "Invalid option.";
        };
    }

    /**
     * Prints the full details of this message to the console.
     */
    public void printMessageDetails() {
        System.out.println("Message ID: "   + messageID);
        System.out.println("Message Hash: " + messageHash);
        System.out.println("Recipient: "    + recipient);
        System.out.println("Message: "      + messageText);
    }

    /**
     * Returns the total number of successfully sent messages.
     * @return total sent message count
     */
    public static int returnTotalMessages() {
        return totalMessages;
    }

    // ── JSON Storage ──────────────────────────────────────────────────────────

    /**
     * Writes this message to messages.json as a JSON object on a single line.
     * Each call appends a new line to the file.
     */
    public void storeMessage() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("messages.json", true))) {
            String jsonLine = "{\"messageID\":\""   + messageID
                + "\",\"messageText\":\""           + messageText.replace("\"", "\\\"")
                + "\",\"recipient\":\""             + recipient
                + "\",\"messageHash\":\""           + messageHash + "\"}";
            bw.write(jsonLine);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error storing message: " + e.getMessage());
        }
    }

    /**
     * Reads stored messages from messages.json into the storedMessages array.
     * Called once at startup after login.
     * Attribution: simple line-by-line JSON parsing without external library.
     */
    public static void loadStoredMessages() {
        try (BufferedReader br = new BufferedReader(new FileReader("messages.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    int start = line.indexOf("\"messageText\":\"") + 15;
                    int end   = line.indexOf("\",\"recipient\"");
                    if (start >= 15 && end > start) {
                        String text = line.substring(start, end).replace("\\\"", "\"");
                        storedMessages.add(text);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("No stored messages found from previous session.");
        }
    }

    // ── Part 3 Features ───────────────────────────────────────────────────────

    /**
     * Finds and returns the longest message in the storedMessages array.
     * @return the longest stored message, or a notice if none exist
     */
    public String displayLongestMessage() {
        String longest = "";
        for (String msg : storedMessages) {
            if (msg.length() > longest.length()) {
                longest = msg;
            }
        }
        return longest.isEmpty() ? "No stored messages found." : longest;
    }

    /**
     * Searches for a message by its ID.
     * @param id the message ID to search for
     * @return the matching message text, or a not-found message
     */
    public String searchByMessageID(String id) {
        for (int i = 0; i < messageIDs.size(); i++) {
            if (messageIDs.get(i).equals(id) && i < sentMessages.size()) {
                return sentMessages.get(i);
            }
        }
        return "Message not found.";
    }

    /**
     * Returns all messages sent to the given recipient number.
     * @param recipient the recipient cell number to search for
     * @return all matching messages, or a not-found message
     */
    public String searchByRecipient(String recipient) {
        StringBuilder results = new StringBuilder();
        for (int i = 0; i < recipientList.size(); i++) {
            if (recipientList.get(i).equals(recipient)) {
                results.append(sentMessages.get(i)).append("\n");
            }
        }
        return results.length() == 0
            ? "No messages found for recipient: " + recipient
            : results.toString().trim();
    }

    /**
     * Deletes a message entry identified by its hash.
     * Removes the matching entry from all parallel arrays.
     * @param hash the message hash to delete
     * @return a success message with the deleted text, or a not-found message
     */
    public String deleteByHash(String hash) {
        for (int i = 0; i < messageHashes.size(); i++) {
            if (messageHashes.get(i).equals(hash)) {
                String deletedText;
                if (i < sentMessages.size()) {
                    deletedText = sentMessages.remove(i);
                    recipientList.remove(i);
                } else {
                    deletedText = "[stored message]";
                }
                messageHashes.remove(i);
                if (i < messageIDs.size()) messageIDs.remove(i);
                return "Message: " + deletedText + " successfully deleted.";
            }
        }
        return "Hash not found.";
    }

    /**
     * Returns all stored messages as a formatted string.
     * @return formatted list of stored messages, or a notice if none exist
     */
    public static String displayStoredMessages() {
        if (storedMessages.isEmpty()) return "No stored messages available.";
        StringBuilder sb = new StringBuilder("=== Stored Messages ===\n");
        for (int i = 0; i < storedMessages.size(); i++) {
            sb.append(i + 1).append(". ").append(storedMessages.get(i)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Builds and returns a full report of all sent messages.
     * Each entry shows the hash, recipient, and message text.
     * @return formatted report string
     */
    public static String printMessages() {
        StringBuilder report = new StringBuilder("=== Message Report ===\n");
        for (int i = 0; i < sentMessages.size(); i++) {
            report.append("--------------------------\n");
            report.append("Hash      : ").append(messageHashes.get(i)).append("\n");
            report.append("Recipient : ").append(recipientList.get(i)).append("\n");
            report.append("Message   : ").append(sentMessages.get(i)).append("\n");
        }
        report.append("==========================\n");
        return report.toString();
    }

    // ── Test Helpers ──────────────────────────────────────────────────────────

    /**
     * Adds a message to the sent arrays directly for testing purposes.
     * @param msg       the message text
     * @param hash      the message hash
     * @param id        the message ID
     * @param recipient the recipient number
     */
    public static void addToSentForTesting(String msg, String hash, String id, String recipient) {
        sentMessages.add(msg);
        messageHashes.add(hash);
        messageIDs.add(id);
        recipientList.add(recipient);
    }

    /**
     * Adds a message to the stored array directly for testing purposes.
     * @param msg the message text to store
     */
    public static void addToStoredForTesting(String msg) {
        storedMessages.add(msg);
    }

    /**
     * Clears all static arrays. Called in @Before to reset state between tests.
     */
    public static void clearAllArrays() {
        sentMessages.clear();
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();
        recipientList.clear();
        totalMessages = 0;
    }
}