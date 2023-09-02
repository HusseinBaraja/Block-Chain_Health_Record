package Application;

import Users.Doctor;
import Validation.InputValidator;
import Users.Receptionist;
import Validation.jsonHandle;
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
    private static final String USER_DATA_FILE = "user_database.json";
    private static JSONObject userData;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        checkUserDataFile();
        loadUserData();

        while (true) {
            System.out.println("Welcome to EHR Blockchain System!");
            System.out.println("1. Signup as Health Provider");
            System.out.println("2. Login");
            System.out.println("3. Exit");

            int choice = InputValidator.valInt("Choice: ", "choice");

            switch (choice) {
                case 1 -> registerHealthProvider();
                case 2 -> login();
                case 3 -> {
                    System.out.println("Exiting the system...");
                    saveUserData();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void checkUserDataFile() {
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
                userData = new JSONObject();
                userData.put("HealthProvider", new JSONArray());
                userData.put("Doctor", new JSONArray());
                userData.put("Receptionist", new JSONArray());
                userData.put("Patient", new JSONArray());
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
        System.out.println("Register as Health Provider:");

        String name = InputValidator.valString("Enter your name: ", "name");

        String clinicOrHospital = InputValidator.valString("Enter your clinic or hospital name: ",
                "hospital name");

        String contactNumber = InputValidator.valString("Enter your contact number: ",
                "contact number");

        String username = InputValidator.valString("Enter a username: ", "username");

        String password = InputValidator.valString("Enter a password: ", "password");

        String[] newHealthProvider = {name, clinicOrHospital, contactNumber, username, password};
        String[] testAdmin = {name, username, password};

        new jsonHandle("HealthProvider", newHealthProvider);
        new jsonHandle("Admin", testAdmin);

        if (userData != null) {
            // The data you provided
            JSONObject providerData = new JSONObject();
            providerData.put("Name", "New Provider");
            providerData.put("ClinicOrHospital", "New Clinic");
            providerData.put("ContactNumber", "111-222-3333");
            providerData.put("Username", "newuser");
            providerData.put("Password", "newpass");
            providerData.put("AccessGranted", false);

            JSONArray healthProviderList = (JSONArray) userData.get("HealthProvider");
            healthProviderList.add(providerData);

            System.out.println("Registration successful!");

            // Write the updated data back to user_database.json
            try (FileWriter file = new FileWriter("user_database.json")) {
                file.write(userData.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String[] userTypes = {"Admin", "HealthProvider", "Doctor", "Receptionist", "Patient"};

        for (String userType : userTypes) {
            JSONArray userList = (JSONArray) userData.get(userType);
            for (Object obj : userList) {
                JSONObject userData = (JSONObject) obj;
                String storedUsername = (String) userData.get("Username");
                String storedPassword = (String) userData.get("Password");
                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    System.out.println(userType + " login successful!");
                    if (userType.equals("Admin")){
                        adminMenu();
                    } else if (userType.equals("HealthProvider")) {
                        
                    } else if (userType.equals("Doctor")) {
                        
                    } else if (userType.equals("Receptionist")) {
                        
                    } else if (userType.equals("Patient")) {

                    }
                    return; // Exit function after successful login
                }
            }
        }

        System.out.println("Login failed. Invalid credentials. Please try again!");
        login();
    }

    private static void adminMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. Manage Users");
        System.out.println("2. Grant Access to Health Provider");
        System.out.println("3. Revoke Access from Health Provider");
        System.out.println("4. Logout");

        System.out.print("Admin Action: ");
        int actionChoice = InputValidator.valInt("Enter your name: ", "name");

        switch (actionChoice) {
            case 1:
                // Grant Access to Health Provider
                // Implement user management functionality here
                break;
            case 2:
                // Revoke Access from Health Provider
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

    }

    // Will change
    private static void grantAccessToHealthProvider() {
        System.out.print("Enter the username of the health provider to grant access: ");
        String username = scanner.nextLine();
//        scanner.nextLine(); //throw away the \n not consumed by nextInt()

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

    // Will change
    private static void revokeAccessFromHealthProvider() {
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

        int choice = InputValidator.valInt("Choice: ", "choice");

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
