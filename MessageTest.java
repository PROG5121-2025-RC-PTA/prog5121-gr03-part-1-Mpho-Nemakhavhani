package com.yourcompany;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    private Message validMessage;

    @Before
    public void setUp() {
        validMessage = new Message(5, "+27821234567", "Hello from Cape Town");
    }

    @After
    public void tearDown() {
        validMessage = null;
    }

    @Test
    public void testCheckMessageID() {
        assertTrue("Message ID should be 10 characters or less", validMessage.checkMessageID());
    }

    @Test
    public void testCheckRecipientCell_Valid() {
        assertTrue("Valid South African cell number should pass", validMessage.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        Message invalidRecipient = new Message(2, "+27123456789", "Test message");
        assertFalse("Invalid number (wrong prefix)", invalidRecipient.checkRecipientCell());

        Message tooLong = new Message(2, "+278212345678", "Another test");
        assertFalse("Too long cell number", tooLong.checkRecipientCell());
    }

    @Test
    public void testCreateMessageHash() {
        String hash = validMessage.createMessageHash();
        assertNotNull("Hash should not be null", hash);

        // Check if hash follows expected pattern
        String[] parts = hash.split(":");
        assertEquals("Hash should contain 3 parts", 3, parts.length);
        assertEquals("First part of hash should match first 2 of ID", 
                     validMessage.getMessageID().substring(0, 2), parts[0]);
    }

    @Test
    public void testGetMessageHash() {
        assertEquals("Hash from getter should match hash from method", 
                     validMessage.createMessageHash(), validMessage.getMessageHash());
    }

    @Test
    public void testGetMessageID() {
        String id = validMessage.getMessageID();
        assertNotNull("Message ID should not be null", id);
        assertEquals("Message ID should be 10 characters", 10, id.length());
    }
}
