package com.yourcompany;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * A simple registration and login system.
 * This program collects user information and validates it.
 * 
 * @author
 */
public class MainPage {
    private static int messageCount = 0;
    private static final List<Message> messages = new ArrayList<>();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        GetStarted();
    }

    public static void GetStarted() {

        System.out.println("Select an option:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        int option = scan.nextInt();

        scan.nextLine();
        if (option != 1 && option != 2) {
            System.out.println("Invalid option selected. Please restart the program.");
            scan.close();
            return;
        }

        System.out.println("You have selected option: " + option);
        if (option == 2) {

            System.out.println("Please enter your username and password to login.");
            System.out.println("Enter username:");
            String userName = scan.nextLine();
            System.out.println("Enter password:");
            String password = scan.nextLine();

            boolean loggedIn = checkLoginDetails(userName, password);
            if(loggedIn)
            {
                 LoadOptions();
            }
            else
            {
                GetStarted();
            }
            

        }else {
            System.out.println("You have chosen to register. Press Enter to continue.");
            scan.nextLine();
            System.out.println("Please enter your details to proceed.");

            // Collect user details
            System.out.println("Enter your first name:");
            String firstName = scan.nextLine();

            System.out.println("Enter your last name:");
            String lastName = scan.nextLine();

            System.out.println("Enter username:");
            String userName = scan.nextLine();

            System.out.println("Enter password:");
            String password = scan.nextLine();

            System.out.println("Enter cell phone number:");
            String cellPhoneNumber = scan.nextLine();

            // Register and validate user details
            registerUser(userName, password, cellPhoneNumber);

            // Attempt login and show welcome message if valid
            returnLoginStatus(userName, password, firstName, lastName);

            scan.close();
        }
    }

    // Check if the username is valid
    public static boolean checkUserName(String userName) {
        // Must include an underscore and be no longer than 5 characters
        if (userName.matches("^(?=.*_)[A-Za-z0-9_]{1,5}$")) {
            System.out.println("Username successfully captured.");
            return true;
        } else {
            System.out.println(
                    "Username is not correctly formatted. It should contain an underscore and be no more than 5 characters long.");
            return false;
        }
    }

    // Check password complexity
    public static boolean checkPasswordComplexity(String password) {
        // Must contain at least one capital letter, one number, one special character,
        // and be at least 8 characters
        if (password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{8,}$")) {
            System.out.println("Password successfully captured.");
            return true;
        } else {
            System.out.println(
                    "Password is not correctly formatted. It should contain at least 8 characters, a capital letter, a number, and a special character.");
            return false;
        }
    }

    // Check if the phone number is in correct South African format
    public static boolean checkCellPhoneNumber(String phone) {
        // Must start with +27 and followed by 8 digits starting with 6, 7, or 8
        if (phone.matches("^(\\+27)(6|7|8)[0-9]{8}$")) {
            System.out.println("Cell phone number successfully added.");
            return true;
        } else {
            System.out.println("Cell phone number is incorrectly formatted. Please use +27 followed by 9 digits.");
            return false;
        }
    }

    // Register the user and validate input
    public static void registerUser(String userName, String password, String phone) {
        checkUserName(userName);
        checkPasswordComplexity(password);
        checkCellPhoneNumber(phone);
    }

    // Check if the user login is successful
    public static boolean loginUser(String userName, String password) {
        boolean isUserNameValid = checkUserName(userName);
        boolean isPasswordValid = checkPasswordComplexity(password);

        return isUserNameValid && isPasswordValid;
    }

    // Display final login message
    public static void returnLoginStatus(String userName, String password, String firstName, String lastName) {
        if (loginUser(userName, password)) {
            System.out.println("Welcome " + firstName + " " + lastName + ", it is great to see you.");
        } else {
            System.out.println("Username or password is incorrect, please try again.");
        }
    }

    public static boolean checkLoginDetails(String userName, String password) {
        if (loginUser(userName, password)) {
            System.out.println("Welcome to QuickChat, " + userName + "!");
            return true;
        } else {
            System.out.println("Login failed. Please check your username and password.");
            return false;
        }
    }

    public static void LoadOptions() {

        System.out.println("1. Send messages");
        System.out.println("2. Recent messages");
        System.out.println("3. Quit");

        String option = scan.nextLine();

        if (!option.matches("[1-3]")) {
            System.out.println("Invalid option selected. Please try again.");
            LoadOptions();
            return;
        }

        switch (option) {
            case "1":
                sendMessages();
                scan.close();
                break;
            case "2":
                recentMessages();
                scan.close();
                break;
            case "3":
                quit();
                scan.close();
                break;
            default:
                System.out.println("Invalid option selected. Please try again.");
                LoadOptions();
        }

    }

    public static void quit() {
        System.out.println("Thank you for using QuickChat. Goodbye!");
        scan.close();
        System.exit(0);
    }

    public static void sendMessages() {
        String input = null;
        int maxMessages = 0;

        // Prompt user for max messages
        while (maxMessages <= 0) {
            input = JOptionPane.showInputDialog(null, "Enter the maximum number of messages you want to send:");
            if (input == null) {
                JOptionPane.showMessageDialog(null, "Operation cancelled.");
                return; // Exit if user cancels
            }
            try {
                maxMessages = Integer.parseInt(input);
                if (maxMessages <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a number greater than 0.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid integer number.");
            }
        }

        // Reset message count
        messageCount = 0;

        while (messageCount < maxMessages) {
            String recipient = JOptionPane.showInputDialog(null, "Enter recipient cell number (starts with +):");
            if (recipient == null || !recipient.matches("^(\\+27)(6|7|8)[0-9]{8}$")) {
                JOptionPane.showMessageDialog(null, "Invalid recipient. Must start with +27 and be 12 digits total.");
                continue;
            }

            String messageText = JOptionPane.showInputDialog(null, "Enter your message (50 to 250 chars):");
            if (messageText == null || messageText.length() < 50 || messageText.length() > 250) {
                JOptionPane.showMessageDialog(null, "Message must be between 50 and 250 characters.");
                continue;
            }

            messageCount++;
            Message message = new Message(messageCount, recipient, messageText);
            messages.add(message);

            JOptionPane.showMessageDialog(null,
                    "Message ID: " + message.getMessageID() +
                            "\nHash: " + message.getMessageHash() +
                            "\nTo: " + recipient +
                            "\nMessage:\n" + messageText);
        }

        storeMessages(messages);
        JOptionPane.showMessageDialog(null, "You have sent the maximum number of messages allowed.");
        System.out.println("You have sent " + messageCount + " messages.");
        System.out.println("Returning to main menu...");
        LoadOptions();
    }

    public static void recentMessages() {
        System.out.println("Coming Soon...");
        LoadOptions();
    }

    public static void storeMessages(List<Message> messages) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("messages.json")) {
            gson.toJson(messages, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
