package Application;

import Users.Users;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Electronic_Health_Record_Application {
    public static HashMap<String, List<String>> userDatabase = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to EHR Blockchain System!");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    signup();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Exiting the system...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void signup() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.println("Select role:");
        System.out.print("1. RECEPTIONIST\n2. DOCTOR\n3. PATIENT\n4. HEALTH_PROVIDER\nEnter the Role:");
        int roleChoice = scanner.nextInt();

        Users.UserRole role = getUsersRoleFromChoice(roleChoice);

        Users user = new Users(username, password, role);

        if (user.signup()) {
            System.out.println("Signup successful! Waiting for admin approval.");
        }
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Users user = new Users(username, password, Users.UserRole.NONE);

        if (user.login()) {
            System.out.println("Login successful!");

            // Check the user's role and perform actions accordingly
            if (user.getRole() == Users.UserRole.ADMIN) {
                System.out.println("Admin Menu:");
                System.out.println("1. Authorize User");
                System.out.print("Choice: ");
                int adminChoice = scanner.nextInt();

                switch (adminChoice) {
                    case 1:
                        authorizeUser();
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else if (user.getRole() == Users.UserRole.RECEPTIONIST) {
            } else if (user.getRole() == Users.UserRole.DOCTOR) {
            } else if (user.getRole() == Users.UserRole.PATIENT) {
            } else if (user.getRole() == Users.UserRole.HEALTH_PROVIDER) {
            }
        } else {
            System.out.println("Login failed.");
        }
    }

    private static void authorizeUser() {
        Scanner scanner = new Scanner(System.in);

        // Check admin credentials
        System.out.print("Enter username to authorize: ");
        String username = scanner.nextLine();

        if (Electronic_Health_Record_Application.userDatabase.containsKey(username)) {
            List<String> userDetails = Electronic_Health_Record_Application.userDatabase.get(username);
            userDetails.set(2, "true");
            System.out.println("User authorized.");
            Users.saveUserDatabaseToJson();
        } else {
            System.out.println("User not found.");
        }
    }

    private static Users.UserRole getUsersRoleFromChoice(int choice) {
        switch (choice) {
            case 1:
                return Users.UserRole.RECEPTIONIST;
            case 2:
                return Users.UserRole.DOCTOR;
            case 3:
                return Users.UserRole.PATIENT;
            case 4:
                return Users.UserRole.HEALTH_PROVIDER;
            default:
                throw new IllegalArgumentException("Invalid role choice.");
        }
    }
}
