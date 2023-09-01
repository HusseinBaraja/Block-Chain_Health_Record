package Application;

import Users.Doctor;
import Users.HealthProvider;
import Users.Receptionist;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Electronic_Health_Record_Application {
    private static final String USER_DATA_FILE = "user_data.json";
    private static JSONObject userData;

    public static void main(String[] args) {
        // Check if the user data file exists, and create it if necessary
        checkUserDataFile();

        // Load existing user data from the JSON file
        loadUserData();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to EHR Blockchain System!");
            System.out.println("1. Signup as Health Provider");
            System.out.println("2. Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerHealthProvider();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    adminLogin();
                    break;
                case 4:
                    System.out.println("Exiting the system...");
                    saveUserData();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void checkUserDataFile() {
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
                userData = new JSONObject();
                userData.put("HealthProviders", new JSONArray());
                userData.put("Doctors", new JSONArray());
                userData.put("Receptionists", new JSONArray());
                saveUserData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void loadUserData() {
        try {
            JSONParser parser = new JSONParser();
            userData = (JSONObject) parser.parse(new FileReader(USER_DATA_FILE));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void saveUserData() {
        try (FileWriter fileWriter = new FileWriter(USER_DATA_FILE)) {
            fileWriter.write(userData.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void registerHealthProvider() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Register as Health Provider:");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your clinic or hospital name: ");
        String clinicOrHospital = scanner.nextLine();
        System.out.print("Enter your contact number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        HealthProvider healthProvider = new HealthProvider(name, clinicOrHospital, contactNumber, username, password);
        healthProvider.setAccessGranted(false);

        // Store health provider data in JSON
        JSONArray healthProviderList = (JSONArray) userData.get("HealthProviders");
        JSONObject providerData = new JSONObject();
        providerData.put("Name", healthProvider.getName());
        providerData.put("ClinicOrHospital", healthProvider.getClinicOrHospital());
        providerData.put("ContactNumber", healthProvider.getContactNumber());
        providerData.put("Username", healthProvider.getUsername());
        providerData.put("Password", healthProvider.getPassword());
        providerData.put("AccessGranted", healthProvider.isAccessGranted());
        healthProviderList.add(providerData);
        userData.put("HealthProviders", healthProviderList);

        System.out.println("Registration successful!");
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (username.equals("admin") && password.equals("adminpassword")) {
            System.out.println("Admin login successful!");
            adminMenu();
        } else {
            JSONArray healthProviderList = (JSONArray) userData.get("HealthProviders");
            JSONArray doctorList = (JSONArray) userData.get("Doctors");
            JSONArray receptionistList = (JSONArray) userData.get("Receptionists");

            // Check if the user is a health provider
            for (Object obj : healthProviderList) {
                JSONObject providerData = (JSONObject) obj;
                String storedUsername = (String) providerData.get("Username");
                String storedPassword = (String) providerData.get("Password");

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    boolean accessGranted = (boolean) providerData.get("AccessGranted");
                    if (accessGranted) {
                        System.out.println("Health Provider login successful!");
                        healthProviderMenu(providerData);
                    } else {
                        System.out.println("Health Provider login failed. Access not granted.");
                    }
                    return;
                }
            }

            // Check if the user is a doctor
            for (Object obj : doctorList) {
                JSONObject doctorData = (JSONObject) obj;
                String storedUsername = (String) doctorData.get("Username");
                String storedPassword = (String) doctorData.get("Password");

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    System.out.println("Doctor login successful!");
                    // Implement doctor functionality here
                    return;
                }
            }

            // Check if the user is a receptionist
            for (Object obj : receptionistList) {
                JSONObject receptionistData = (JSONObject) obj;
                String storedUsername = (String) receptionistData.get("Username");
                String storedPassword = (String) receptionistData.get("Password");

                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    System.out.println("Receptionist login successful!");
                    // Implement receptionist functionality here
                    return;
                }
            }

            System.out.println("Login failed. Invalid credentials.");
        }
    }

    private static void adminMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. Manage Users");
        System.out.println("2. Grant Access to Health Provider");
        System.out.println("3. Revoke Access from Health Provider");
        System.out.println("4. Logout");
    }

    private static void adminLogin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter admin username: ");
        String adminUsername = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String adminPassword = scanner.nextLine();

        if (adminUsername.equals("admin") && adminPassword.equals("adminpassword")) {
            adminMenu();
            System.out.print("Admin Action: ");
            int actionChoice = scanner.nextInt();

            switch (actionChoice) {
                case 1:
                    // Manage Users
                    // Implement user management functionality here
                    break;
                case 2:
                    // Grant Access to Health Provider
                    grantAccessToHealthProvider();
                    break;
                case 3:
                    // Revoke Access from Health Provider
                    revokeAccessFromHealthProvider();
                    break;
                case 4:
                    System.out.println("Going back to the menu.");
                    return;
                default:
                    System.out.println("Invalid admin action choice.");
            }
        } else {
            System.out.println("Admin login failed. Invalid credentials.");
        }
    }

    private static void grantAccessToHealthProvider() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the username of the health provider to grant access: ");
        String username = scanner.nextLine();

        JSONArray healthProviderList = (JSONArray) userData.get("HealthProviders");
        for (Object obj : healthProviderList) {
            JSONObject providerData = (JSONObject) obj;
            String storedUsername = (String) providerData.get("Username");

            if (storedUsername.equals(username)) {
                providerData.put("AccessGranted", true);
                System.out.println("Access granted to the health provider.");
                return;
            }
        }

        System.out.println("Health provider not found.");
    }

    private static void revokeAccessFromHealthProvider() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the username of the health provider to revoke access: ");
        String username = scanner.nextLine();

        JSONArray healthProviderList = (JSONArray) userData.get("HealthProviders");
        for (Object obj : healthProviderList) {
            JSONObject providerData = (JSONObject) obj;
            String storedUsername = (String) providerData.get("Username");

            if (storedUsername.equals(username)) {
                providerData.put("AccessGranted", false);
                System.out.println("Access revoked from the health provider.");
                return;
            }
        }

        System.out.println("Health provider not found.");
    }

    private static void healthProviderMenu(JSONObject providerData) {
        System.out.println("Health Provider Menu:");
        System.out.println("1. Remove Receptionist");
        System.out.println("2. Remove Doctor");
        System.out.println("3. Add Doctor");
        System.out.println("4. Add Receptionist");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                // Remove Receptionist
                break;
            case 2:
                // Remove doctor
                break;
            case 3:
                // Add Doctor
                addDoctor(providerData);
                break;
            case 4:
                // Add Receptionist
                addReceptionist(providerData);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addDoctor(JSONObject providerData) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add Doctor:");
        System.out.print("Enter doctor's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter doctor's DOB: ");
        String DOB = scanner.nextLine();
        System.out.print("Enter doctor's gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter doctor's age: ");
        int age = scanner.nextInt();
        System.out.print("Enter doctor's phone number: ");
        int phoneNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter doctor's username: ");
        String username = scanner.nextLine();
        System.out.print("Enter doctor's password: ");
        String password = scanner.nextLine();

        Doctor doctor = new Doctor(name, DOB, gender, age, phoneNumber, username, password);

        // Store doctor data in the top-level JSON userData object
        JSONArray doctorsList = (JSONArray) userData.getOrDefault("Doctors", new JSONArray());
        JSONObject doctorData = new JSONObject();
        doctorData.put("Name", doctor.getFullName());
        doctorData.put("DOB", doctor.getDOB());
        doctorData.put("Gender", doctor.getGender());
        doctorData.put("Age", doctor.getAge());
        doctorData.put("PhoneNumber", doctor.getPhoneNumber());
        doctorData.put("Username", doctor.getUsername());
        doctorData.put("Password", doctor.getPassword());
        doctorsList.add(doctorData);
        userData.put("Doctors", doctorsList); // Add the doctor to the top-level "Doctors" array

        System.out.println("Doctor added successfully!");
    }

    private static void addReceptionist(JSONObject providerData) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add Receptionist:");
        System.out.print("Enter receptionist's name: ");
        String name = scanner.nextLine();
        System.out.print("Enter receptionist's DOB: ");
        String DOB = scanner.nextLine();
        System.out.print("Enter receptionist's gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter receptionist's age: ");
        int age = scanner.nextInt();
        System.out.print("Enter receptionist's phone number: ");
        int phoneNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter receptionist's username: ");
        String username = scanner.nextLine();
        System.out.print("Enter receptionist's password: ");
        String password = scanner.nextLine();

        Receptionist receptionist = new Receptionist(name, DOB, gender, age, phoneNumber, username, password);

        // Store receptionist data in JSON
        JSONArray receptionistsList = (JSONArray) userData.getOrDefault("Receptionists", new JSONArray());
        JSONObject receptionistData = new JSONObject();
        receptionistData.put("Name", receptionist.getFullName());
        receptionistData.put("DOB", receptionist.getDOB());
        receptionistData.put("Gender", receptionist.getGender());
        receptionistData.put("Age", receptionist.getAge());
        receptionistData.put("PhoneNumber", receptionist.getPhoneNumber());
        receptionistData.put("Username", receptionist.getUsername());
        receptionistData.put("Password", receptionist.getPassword());
        receptionistsList.add(receptionistData);
        providerData.put("Receptionists", receptionistsList);

        System.out.println("Receptionist added successfully!");
    }
}
