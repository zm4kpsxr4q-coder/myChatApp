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

    /**
     * Runs before every test. Creates fresh Message objects using POE test data
     * and populates the static arrays with the five POE messages.
     */
    @BeforeEach
    public void setUp() {
        Message.clearAllArrays();

        // POE test data — message1: valid recipient, used for hash/length/recipient tests
        message1 = new Message(1, "+27718693002", "Hi Mike, can you join us for dinner tonight?");

        // POE test data — message2: invalid recipient (no international code)
        message2 = new Message(2, "08575975889", "Hi Keegan, did you receive the payment?");

        // ── Populate arrays with the five POE Part 3 messages ─────────────────

        // Message 1 — Sent — recipient +27834557896
        Message.addToSentForTesting(
            "Did you get the cake?",
            "MSG1:DID:CAKE",
            "MSG1",
            "+27834557896"
        );

        // Message 2 — Sent + Stored — recipient +27838884567
        Message.addToSentForTesting(
            "Where are you? You are late! I have asked you to be on time.",
            "MSG2:WHERE:TIME",
            "MSG2",
            "+27838884567"
        );
        Message.addToStoredForTesting(
            "Where are you? You are late! I have asked you to be on time."
        );

        // Message 3 — Sent — recipient +27834484567
        Message.addToSentForTesting(
            "Have you had lunch?",
            "MSG3:HAVE:LUNCH",
            "MSG3",
            "+27834484567"
        );

        // Message 4 — Sent — developer number (no international code)
        Message.addToSentForTesting(
            "It is dinner time!",
            "MSG4:IT:TIME",
            "0838884567",
            "0838884567"
        );

        // Message 5 — Sent — recipient +27838884567
        Message.addToSentForTesting(
            "Ok, I am leaving without you.",
            "MSG5:OK:YOU",
            "MSG5",
            "+27838884567"
        );

        // Stored for longest message test
        Message.addToStoredForTesting("Ok, I am leaving without you.");
    }

    // ════════════════════════════════════════════════════════════════════════
    // PART 2 TESTS
    // ════════════════════════════════════════════════════════════════════════

    // ── Message length ────────────────────────────────────────────────────────

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

    // ── Recipient cell ────────────────────────────────────────────────────────

    @Test
    public void testCheckRecipientCell_validNumber_returnsSuccess() {
        // message1 has valid +27 number
        String result = message1.checkRecipientCell(message1.getRecipient());
        assertEquals("Cell phone number successfully added.", result);
    }

    @Test
    public void testCheckRecipientCell_invalidNumber_returnsFailure() {
        // message2 has no international code
        String result = message2.checkRecipientCell(message2.getRecipient());
        assertEquals(
            "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
            result
        );
    }

    // ── Hash ──────────────────────────────────────────────────────────────────

    @Test
    public void testCreateMessageHash_correctFormat_endsWithExpectedWords() {
        // message1 text: "Hi Mike, can you join us for dinner tonight?"
        // first word: HI  last word: TONIGHT  messageNumber: 1
        // hash ends with :1:HITONIGHT
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
        // message1: "Hi Mike... tonight?" → :1:HITONIGHT
        // message2: "Hi Keegan... payment?" → :2:HIPAYMENT
        Message[]  messages         = { message1, message2 };
        String[]   expectedSuffixes = { ":1:HITONIGHT", ":2:HIPAYMENT" };

        for (int i = 0; i < messages.length; i++) {
            String hash = messages[i].createMessageHash();
            assertTrue(hash.endsWith(expectedSuffixes[i]),
                "Message " + (i + 1) + " hash should end with '"
                + expectedSuffixes[i] + "' but was: " + hash);
        }
    }

    // ── Message ID ────────────────────────────────────────────────────────────

    @Test
    public void testCheckMessageID_generatedID_isNotNull() {
        assertNotNull(message1.getMessageID(), "Message ID should not be null");
    }

    @Test
    public void testCheckMessageID_generatedID_isExactly10Chars() {
        assertTrue(message1.checkMessageID(), "checkMessageID() should return true");
    }

    // ── sentMessage actions ───────────────────────────────────────────────────

    /**
     * Inner helper class that overrides sentMessage() to avoid console input
     * and return a fixed result based on the simulated choice.
     */
    static class TestableMessage extends Message {
        private final int simulatedChoice;

        public TestableMessage(int messageNumber, int simulatedChoice) {
            super(messageNumber);
            this.simulatedChoice = simulatedChoice;
        }

        @Override
        public String sentMessage(int choice) {
            return switch (simulatedChoice) {
                case 1  -> "Message successfully sent.";
                case 2  -> "Press 0 to delete the message.";
                case 3  -> "Message successfully stored.";
                default -> "Invalid option.";
            };
        }
    }

    @Test
    public void testSentMessage_userSelectsSend_returnsCorrectString() {
        assertEquals("Message successfully sent.",
            new TestableMessage(1, 1).sentMessage(1));
    }

    @Test
    public void testSentMessage_userSelectsDisregard_returnsCorrectString() {
        assertEquals("Press 0 to delete the message.",
            new TestableMessage(1, 2).sentMessage(2));
    }

    @Test
    public void testSentMessage_userSelectsStore_returnsCorrectString() {
        assertEquals("Message successfully stored.",
            new TestableMessage(1, 3).sentMessage(3));
    }

    // ════════════════════════════════════════════════════════════════════════
    // PART 3 TESTS
    // ════════════════════════════════════════════════════════════════════════

    @Test
    public void testSentMessagesArray_correctlyPopulated() {
        // Messages 1 and 4 are flagged as Sent in the POE
        String report = Message.printMessages();
        assertTrue(report.contains("Did you get the cake?"),
            "Sent array should contain message 1");
        assertTrue(report.contains("It is dinner time!"),
            "Sent array should contain message 4");
    }

    @Test
    public void testDisplayLongestMessage_returnsCorrectMessage() {
        // POE expected: message 2 is the longest stored message
        Message temp = new Message(0, "+27000000000", "placeholder text");
        String result = temp.displayLongestMessage();
        assertEquals(
            "Where are you? You are late! I have asked you to be on time.",
            result
        );
    }

    @Test
    public void testSearchByMessageID_returnsCorrectMessage() {
        // Message 4 uses developer number 0838884567 as its ID per POE
        Message temp   = new Message(0, "+27000000000", "placeholder text");
        String  result = temp.searchByMessageID("0838884567");
        assertEquals("It is dinner time!", result);
    }

    @Test
    public void testSearchByRecipient_returnsAllMatchingMessages() {
        // Messages 2 and 5 both go to +27838884567
        Message temp   = new Message(0, "+27000000000", "placeholder text");
        String  result = temp.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."),
            "Should contain message 2");
        assertTrue(result.contains("Ok, I am leaving without you."),
            "Should contain message 5");
    }

    @Test
    public void testDeleteByHash_removesCorrectMessage() {
        Message temp   = new Message(0, "+27000000000", "placeholder text");
        String  result = temp.deleteByHash("MSG2:WHERE:TIME");
        assertEquals(
            "Message: Where are you? You are late! I have asked you to be on time. successfully deleted.",
            result
        );
    }

    @Test
    public void testDisplayReport_containsRequiredFields() {
        String report = Message.printMessages();
        // Report must contain hash, recipient, and message text for sent messages
        assertTrue(report.contains("MSG1:DID:CAKE"),       "Report should contain hash for message 1");
        assertTrue(report.contains("+27834557896"),        "Report should contain recipient for message 1");
        assertTrue(report.contains("Did you get the cake?"), "Report should contain text for message 1");
    }
}