package Application;

import Users.Doctor;
import Validation.*;
import Blockchain.Blockchain;
import Validation.JsonHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import Blockchain.MerkleTree;
import Blockchain.TransactionCollection;
import Blockchain.Block;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Electronic_Health_Record_Application {
    private static final String USER_FILE = "src/database/user_database.json";
    private static JSONObject userData;
    private static final Scanner scanner = new Scanner(System.in);

    private static String masterFolder = "master";
    private static String fileName = masterFolder + "/chain.bin";
    private static orderSignificance test;

    public static void main(String[] args) {
        mainMenu();
//        System.out.println(test.getDisplay());
        DisplaySignificantData();
//        addDataToBlockchain();
    }

    private static void DisplaySignificantData(){

    }
    private static void addDataToBlockchain() {
        Blockchain bc = Blockchain.getInstance(fileName);
        if(!new File(masterFolder).exists()) {
            System.err.println("> creating Blockchain binary !");
            new File(masterFolder).mkdir();
            bc.genesis();
        }else {
            String line1 = "bob|alice|debit|100";
            String line2 = "mick|alice|debit|200";
            String line3 = "peter|alice|debit|300";
            String line4 = "ali|alice|debit|201";


            List<String> lst = new ArrayList<>();
            lst.add(line1);
            lst.add(line2);
            lst.add(line3);
            lst.add(line4);


            MerkleTree mt = MerkleTree.getInstance(lst);
            mt.build();
            String root = mt.getRoot();

            TransactionCollection trxlst = new TransactionCollection();
            trxlst.setMerkleRoot(root);
            trxlst.setTranxLst(lst);

            String previousHash = bc.get().getLast().getHeader().getCurrHash();
            Block b1 = new Block(previousHash);
            b1.setTransactions(trxlst);
            bc.nextBlock(b1);
            bc.distribute();

        }
    }

    private static void mainMenu(){
//        while (true) {
            System.out.println("Welcome to EHR Blockchain System!");
            System.out.println("1. Sign in");
            System.out.println("2. Sign up");
            System.out.println("3. Exit");

            int choice = InputValidator.valInt("Choice: ", "choice");

            switch (choice) {
                case 1 -> signIn();
                case 2 -> signUp();
                case 3 -> {
                    System.out.println("Exiting the system...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
//        }
    }

    // validation for clinicOrHospital, username, password, contactNumber
    private static void signUp() {
        System.out.println("Register as Health Provider:");

        String name = InputValidator.valString("Enter the health provider name: ", "name");
        String clinicOrHospital = InputValidator.valString("Enter the type of health provider (clinic or hospital): ",
                "hospital name");
        String contactNumber = InputValidator.valString("Enter health provider contact number: ",
                "contact number");
        String username = InputValidator.valString("Enter a username: ", "username");
        String password = InputValidator.valString("Enter a password: ", "password");

        String[] userAttributes = {"Name", "ClinicOrHospital", "ContactNumber", "Username", "Password", "AccessGranted"};
        Object[] newHealthProvider = {name, clinicOrHospital, contactNumber, username, password, false};

        JsonHandler healthProvider = new JsonHandler();
        healthProvider.addNewUser("HealthProvider", userAttributes, newHealthProvider);

        System.out.println("Registration successful!");
    }

    private static void signIn() {
        String username = InputValidator.valString("Enter username: ", "username");
        String password = InputValidator.valString("Enter password: ", "password");


        JsonHandler rootLogin = new JsonHandler();
        File jsonFile = new File(USER_FILE);
        userData = rootLogin.getRootInfo(jsonFile);

        String[] userTypes = {"Admin", "HealthProvider", "Doctor", "Patient"};

        for (String userType : userTypes) {
            JSONArray userList = (JSONArray) userData.get(userType);
            for (Object obj : userList) {
                JSONObject userData = (JSONObject) obj;
                String storedUsername = (String) userData.get("Username");
                String storedPassword = (String) userData.get("Password");
                if (storedUsername.equals(username) && storedPassword.equals(password)) {
                    System.out.println(userType + " login successful!");
                    switch (userType) {
                        case "Admin" -> adminMenu();
//                        case "HealthProvider" -> healthProviderMenu();
                        case "Doctor" -> new Doctor(username);
//                        case "Patient" -> adminMenu();
                        default -> System.out.println("Invalid choice.");
                    }
                    return; // Exit function after successful login
                }
            }
        }

        System.out.println("Login failed. Invalid credentials. Please try again!");
        signIn();
    }

    private static void adminMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. Grant Access to Health Provider");
        System.out.println("2. Revoke Access from Health Provider");
        System.out.println("3. Logout");

        System.out.print("Admin Action: ");
        int actionChoice = InputValidator.valInt("Enter your name: ", "name");

        JsonHandler manageAccess = new JsonHandler();
        switch (actionChoice) {
            // Grant Access to Health Provider
            case 1 -> manageAccess.grantAccessToHealthProvider();

            // Revoke Access from Health Provider
            case 2 -> manageAccess.revokeAccessFromHealthProvider();

            case 3 -> System.out.println("Going back to the menu.");

            default -> System.out.println("Invalid admin action choice.");
        }

    }

    private static void healthProviderMenu(JSONObject providerData) {
        System.out.println("Health Provider Menu:");
        System.out.println("1. Add Doctor");
        System.out.println("2. Remove Doctor");
        System.out.println("3. Logout");

        int choice = InputValidator.valInt("Choice: ", "choice");

        switch (choice) {
            case 1:
                addDoctor();
                break;
            case 2:
                // Remove doctor
                break;
            case 3:
                System.out.println("Logging out from the account, Bye :-)");
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addDoctor() {
        System.out.println("Add Doctor:");

        String name = InputValidator.valString("Enter doctor's name: ", "doctor's name");
        String username = InputValidator.valString("Enter doctor's username: ", "username");
        String password = InputValidator.valString("Enter doctor's password: ", "password");
        int phoneNumber = InputValidator.valInt("Enter doctor's phone number: ", "phone number");
        String DOB = InputValidator.valString("Enter doctor's DOB: ", "date of birth");
        int age = InputValidator.valInt("Enter doctor's age: ", "age");
        String gender = InputValidator.valString("Enter doctor's gender: ", "gender");


        String[] userAttributes = {"Name", "Username", "Password", "PhoneNumber", "DOB", "Age", "Gender"};
        Object[] newDoctor = {name, username, password, phoneNumber, DOB, age, gender};

        JsonHandler healthProvider = new JsonHandler();
        healthProvider.addNewUser("Doctor", userAttributes, newDoctor);


        System.out.println("Doctor added successfully!");
    }

    private void doctorMenu(){
        // Menu
    }

}
