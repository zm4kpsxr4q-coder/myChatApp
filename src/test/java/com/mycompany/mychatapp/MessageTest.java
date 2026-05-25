/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.mychatapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author HLUMELO
 */
public class MessageTest {
    private Message message1;
    private Message message2;

    
    @BeforeEach
    public void setUp() {
        message1 = new Message(1);
        message1.setRecipient("+27718693002");
        message1.setMessageText("Hi Mike, can you join us for dinner tonight?");

        message2 = new Message(2);
        message2.setRecipient("08575975889");
        message2.setMessageText("Hi Keegan, did you receive the payment?");
    }

    // ==== Testing Message Length ====

    @Test
    public void testCheckMessageLength_validMessage_returnsSuccess() {
        String result = message1.checkMessageLength(message1.getMessageText());
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testCheckMessageLength_over250chars_returnsFailureWithCount() {
        String result = message1.checkMessageLength("A".repeat(260));
        assertEquals("Message exceeds 250 characters by 10, please reduce size.", result);
    }

    @Test
    public void testCheckMessageLength_exactlyAtLimit_returnsSuccess() {
        String result = message1.checkMessageLength("B".repeat(250));
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testCheckMessageLength_oneOver_returnsFailureWithCountOf1() {
        String result = message1.checkMessageLength("C".repeat(251));
        assertEquals("Message exceeds 250 characters by 1, please reduce size.", result);
    }

    // === Testing Recipient Cell ====

    @Test
    public void testCheckRecipientCell_validNumber_returnsSuccess() {
        String result = message1.checkRecipientCell(message1.getRecipient());
        assertEquals("Cell phone number successfully added.", result);
    }

    @Test
    public void testCheckRecipientCell_invalidNumber_returnsFailure() {
        String result = message2.checkRecipientCell(message2.getRecipient());
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an "
            + "international code. Please correct the number and try again.",
            result
        );
    }

    // ==== Testing hash ====

    @Test
    public void testCreateMessageHash_correctFormat_endsWithExpectedWords() {
        String hash = message1.createMessageHash();
        assertTrue(hash.endsWith(":1:HITONIGHT"),
                   "Hash should end with :1:HITONIGHT but was: " + hash);
    }

    @Test
    public void testCreateMessageHash_isUppercase() {
        String hash = message1.createMessageHash();
        assertEquals(hash.toUpperCase(), hash, "Hash must be entirely uppercase");
    }

    @Test
    public void testCreateMessageHash_multipleMessages_loopTest() {
        Message[] messages         = { message1, message2 };
        String[]  expectedSuffixes = { ":1:HITONIGHT", ":2:HIPAYMENT" };

        for (int i = 0; i < messages.length; i++) {
            String hash = messages[i].createMessageHash();
            assertTrue(
                hash.endsWith(expectedSuffixes[i]),
                "Message " + (i + 1) + " hash should end with '"
                    + expectedSuffixes[i] + "' but was: " + hash
            );
        }
    }

    // ==== Testing Message ID ====

    @Test
    public void testCheckMessageID_generatedID_isNotNull() {
        assertNotNull("Message ID should not be null", message1.getMessageID());
    }

    @Test
    public void testCheckMessageID_generatedID_isExactly10Chars() {
        assertTrue(message1.checkMessageID(), "checkMessageID() should return true");
    }

    // ====Testing sent Messages=====

    static class TestableMessage extends Message {
        private final int simulatedChoice;

        public TestableMessage(int messageNumber, int simulatedChoice) {
            super(messageNumber);
            this.simulatedChoice = simulatedChoice;
        }

        @Override
        public String sentMessage() {
            return switch (simulatedChoice) {
                case 1 -> "Message successfully sent.";
                case 2 -> "Press 0 to delete the message.";
                case 3 -> "Message successfully stored.";
                default -> "Invalid option.";
            };
        }
    }

    @Test
    public void testSentMessage_userSelectsSend_returnsCorrectString() {
        assertEquals("Message successfully sent.",
                     new TestableMessage(1, 1).sentMessage());
    }

    @Test
    public void testSentMessage_userSelectsDisregard_returnsCorrectString() {
        assertEquals("Press 0 to delete the message.",
                     new TestableMessage(1, 2).sentMessage());
    }

    @Test
    public void testSentMessage_userSelectsStore_returnsCorrectString() {
        assertEquals("Message successfully stored.",
                     new TestableMessage(1, 3).sentMessage());
    }
    
}
