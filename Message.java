package com.yourcompany;
import java.util.UUID;

public class Message {
    private String messageId;
    private int messageCount;
    private String recipient;
    private String message;
    private String messageHash;

    public Message(int messageCount, String recipient, String message) {
        this.messageId = generateMessageID();
        this.messageCount = messageCount;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
    }

    Message(String msG123, String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean checkMessageID() {
        return messageId.length() <= 10;
    }

    public boolean checkRecipientCell() {
        return recipient.matches("^(\\+27)(6|7|8)[0-9]{8}$") && recipient.length() <= 12;
    }

    public String createMessageHash() {
        String first2 = messageId.substring(0, 2);
        String hash = first2 + ":" + messageCount + ":" +
                      message.split(" ")[0].toUpperCase() + 
                      message.substring(message.lastIndexOf(" ") + 1).toUpperCase();
        return hash;
    }

    public void printMessage() {
        System.out.println("Message ID: " + messageId);
        System.out.println("Hash: " + messageHash);
        System.out.println("Recipient: " + recipient);
        System.out.println("Message: " + message);
    }

    public String getMessageHash() {
        return messageHash;
    }

    public String getMessageID() {
        return messageId;
    }

    private String generateMessageID() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
