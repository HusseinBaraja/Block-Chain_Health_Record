package Application;

import Users.Admin;
import Users.Doctor;
import Users.HealthProvider;
import Validation.*;
import Blockchain.Blockchain;
import Validation.JsonHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import Blockchain.MerkleTree;
import Blockchain.TransactionCollection;
import Blockchain.Block;
import Blockchain.Hasher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;


public class Electronic_Health_Record_Application {
    private static final String USER_FILE = "src/database/user_database.json";
    private static final String INSIGNIFICANT_FILE = "src/database/insignificant_data.json";
    private static JSONObject userData;
    private static String masterFolder = "master";
    private static String fileName = masterFolder + "/chain.bin";

    public static void main(String[] args) throws Exception {
        mainMenu();
    }

    public static void addDataToBlockchain(JSONObject significantPatientData) {
        Blockchain bc = Blockchain.getInstance(fileName);
        if (!new File(masterFolder).exists()) {
            System.err.println("> creating Blockchain binary !");
            new File(masterFolder).mkdir();
            bc.genesis();
        } else {
            JSONArray jsonArray = new JSONArray();

            // Add the JSON object directly to the JSON array
            jsonArray.add(significantPatientData);

            MerkleTree mt = MerkleTree.getInstance(Collections.singletonList(jsonArray.toJSONString()));
            mt.build();
            String root = mt.getRoot();

            TransactionCollection trxlst = new TransactionCollection();
            trxlst.setMerkleRoot(root);

            // Set tranxLst as the JSON array
            trxlst.setTranxLst(jsonArray);

            String previousHash = bc.get().getLast().getHeader().getCurrHash();
            Block b1 = new Block(previousHash);
            b1.setTransactions(trxlst);
            bc.nextBlock(b1);
            bc.distribute();
        }
    }

    private static void mainMenu() throws Exception {
        while (true) {
            clearScreen();
            System.out.println("\u001B[34m+---------------------------------+\u001B[0m"); // Blue border
            System.out.println("\u001B[33m|Welcome to EHR Blockchain System!\u001B[0m\u001B[33m|\u001B[0m"); // Cyan header
            System.out.println("\u001B[34m+---------------------------------+\u001B[0m"); // Blue border
            System.out.println("\u001B[33m| \u001B[0m\u001B[32m1. Sign in                      \u001B[0m\u001B[33m|\u001B[0m"); // Green text
            System.out.println("\u001B[33m| \u001B[0m\u001B[32m2. Sign up                      \u001B[0m\u001B[33m|\u001B[0m"); // Green text
            System.out.println("\u001B[33m| \u001B[0m\u001B[32m3. Exit                         \u001B[0m\u001B[33m|\u001B[0m"); // Green text
            System.out.println("\u001B[34m+---------------------------------+\u001B[0m"); // Blue border

            int choice = InputValidator.valInt("\u001B[33mChoose an option: \u001B[0m", "choice");

            switch (choice) {
                case 1:
                    signIn();
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    System.out.println("Exiting the system...");
                    return;
                default:
                    System.out.println("\u001B[31mInvalid choice.\u001B[0m"); // Red text
                    // You can add a delay before clearing the screen
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void signIn() throws Exception {
        clearScreen();

        System.out.println();
        System.out.println("\u001B[36mSign in:\u001B[0m"); // Cyan header

        String username = InputValidator.valString("\u001B[33mEnter username:\u001B[0m ", "username");

        String password = InputValidator.valString("\u001B[33mEnter password:\u001B[0m ", "password");

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

                // Hash the entered password and compare it with stored password
                String hashedEnteredPassword = Hasher.sha384(password);
                if (storedUsername.equals(username) && storedPassword.equals(hashedEnteredPassword)) {
//                    Check this
//                    System.out.println("\u001B[32m" + userType + " login successful!\u001B[0m"); // Green success message
                    switch (userType) {
                        case "Admin" -> new Admin(username);
                        case "HealthProvider" -> new HealthProvider(username);
                        case "Doctor" -> new Doctor(username);
//                        case "Patient" -> patientMenu();
                        default -> System.out.println("\u001B[31mInvalid choice.\u001B[0m"); // Red error message
                    }
                    return;
                }
            }
        }

        System.out.println("\u001B[31mLogin failed. Invalid credentials. Please try again!\u001B[0m"); // Red error message
        signIn();
    }

    private static void signUp() {
        while (true) {
            clearScreen();
            System.out.println("\u001B[34m+---------------------------------+\u001B[0m"); // Blue border
            System.out.println("\u001B[33m|            Sign up !\u001B[0m\u001B[33m            |\u001B[0m"); // Cyan header
            System.out.println("\u001B[34m+---------------------------------+\u001B[0m"); // Blue border
            System.out.println("\u001B[33m| \u001B[0m\u001B[32m1. Sign up as Health Provider   \u001B[0m\u001B[33m|\u001B[0m"); // Green text
            System.out.println("\u001B[33m| \u001B[0m\u001B[32m2. Sign up as Patient           \u001B[0m\u001B[33m|\u001B[0m"); // Green text
            System.out.println("\u001B[33m| \u001B[0m\u001B[32m3. Main Menu                    \u001B[0m\u001B[33m|\u001B[0m"); // Green text
            System.out.println("\u001B[34m+---------------------------------+\u001B[0m"); // Blue border

            int choice = InputValidator.valInt("\u001B[33mChoose an option: \u001B[0m", "choice");

            switch (choice) {
                case 1 -> registerHealthProvider();
                case 2 -> registerPatient();
                case 3 -> {
                    System.out.println("Returning back to main menu...");
                    return;
                }
                default -> {
                    System.out.println("\u001B[31mInvalid choice.\u001B[0m"); // Red text

                    // You can add a delay before clearing the screen
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static void registerPatient() {
        System.out.println();
        System.out.println("\u001B[36mRegister as Patient:\u001B[0m"); // Cyan header

        String username;
        String password;

        // Read the JSON file and get the entire data
        JsonHandler patient = new JsonHandler();
        JSONObject data = patient.getRootInfo(new File(USER_FILE));

        while (true) {
            username = InputValidator.valUsername("\u001B[33mEnter username:\u001B[0m ", "username"); // Yellow input
            password = InputValidator.valPassword("\u001B[33mEnter password:\u001B[0m ", "password"); // Yellow input

            // Hash the password using SHA-384
            String hashedPassword = Hasher.sha384(password);

            // Check if the username already exists in any section
            if (isUsernameExistsInSections(data, username)) {
                System.out.println("\u001B[31mUsername already exists. Please choose another one.\u001B[0m"); // Red error message
            } else {
                // Find the last PatientId and increment it
                String lastPatientId = "";
                JSONArray patientArray = (JSONArray) data.get("Patient");
                if (patientArray.size() > 0) {
                    JSONObject lastPatient = (JSONObject) patientArray.get(patientArray.size() - 1);
                    lastPatientId = (String) lastPatient.get("PatientId");
                }

                String newPatientId = incrementPatientId(lastPatientId);

                String[] userAttributes = {"PatientId", "Username", "Password"};
                Object[] newPatient = {newPatientId, username, hashedPassword};

                patient.addNewUser("Patient", userAttributes, newPatient);

                addToInsignificantData(newPatientId, username);

                System.out.println("\u001B[32mRegistration successful!\u001B[0m"); // Green success message
                break; // Exit the loop when registration is successful
            }
        }
    }

    private static String incrementPatientId(String lastPatientId) {
        if (lastPatientId.isEmpty()) {
            return "P0";
        }

        String[] parts = lastPatientId.split("P");
        int lastId = Integer.parseInt(parts[1]);
        int newId = lastId + 1;
        return "P" + newId;
    }

    private static void registerHealthProvider() {
        System.out.println();
        System.out.println("\u001B[36mRegister as Health Provider:\u001B[0m"); // Cyan header

        String name, username, contactNumber, clinicOrHospital, password;

        JsonHandler healthProvider = new JsonHandler();
        JSONObject data = healthProvider.getRootInfo(new File(USER_FILE));

        while (true) {
            username = InputValidator.valUsername("\u001B[33mEnter a username:\u001B[0m ", "username"); // Yellow input

            if (isUsernameExistsInSections(data, username)) {
                System.out.println("\u001B[31mUsername already exists. Please choose another one.\u001B[0m"); // Red error message
                continue; // Continue to the next iteration of the loop to ask for a new username
            }

            password = InputValidator.valPassword("\u001B[33mEnter a password:\u001B[0m ", "password"); // Yellow input
            name = InputValidator.valString("\u001B[33mEnter the health provider name:\u001B[0m ", "name"); // Yellow input
            clinicOrHospital = InputValidator.valFacilityType("\u001B[33mEnter the type of health provider (clinic or hospital):\u001B[0m ",
                    "facility type"); // Yellow input
            contactNumber = InputValidator.valPhoneNumber("\u001B[33mEnter health provider contact number:\u001B[0m ",
                    "phone number"); // Yellow input

            // Hash the password using SHA-384
            String hashedPassword = Hasher.sha384(password);

            String[] userAttributes = {"Name", "ClinicOrHospital", "ContactNumber", "Username", "Password", "AccessGranted"};
            Object[] newHealthProvider = {name, clinicOrHospital, contactNumber, username, hashedPassword, false};

            healthProvider.addNewUser("HealthProvider", userAttributes, newHealthProvider);

            System.out.println("\u001B[32mRegistration successful!\u001B[0m"); // Green success message
            break; // Exit the loop when registration is successful
        }
    }

    public static boolean isUsernameExistsInSections(JSONObject data, String username) {
        String[] sections = {"Admin", "HealthProvider", "Doctor", "Patient"};
        for (String section : sections) {
            JSONArray sectionArray = (JSONArray) data.get(section);
            if (isUsernameExists(sectionArray, username)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isUsernameExists(JSONArray jsonArray, String username) {
        for (Object obj : jsonArray) {
            JSONObject jsonObject = (JSONObject) obj;
            String existingUsername = (String) jsonObject.get("Username");
            if (existingUsername.equals(username)) {
                return true;
            }
        }
        return false;
    }

    private static void addToInsignificantData(String patientId, String username) {
        try {
            // Read the existing insignificant data JSON file
            JsonHandler insignificantDataHandler = new JsonHandler();
            File insignificantFile = new File(INSIGNIFICANT_FILE);
            JSONObject insignificantData = insignificantDataHandler.getRootInfo(insignificantFile);

            // Create a new block for the patient using the patientId as the key
            JSONObject patientBlock = new JSONObject();

            // Create a JSON object for PatientIdentifiers
            JSONObject patientIdentifiers = new JSONObject();
            patientIdentifiers.put("Username", username);

            // Add the PatientIdentifiers object to the patient block
            patientBlock.put("PatientIdentifiers", patientIdentifiers);
            patientBlock.put("ImagingReports", new JSONArray());
            patientBlock.put("Procedures", new JSONArray());
            patientBlock.put("VitalSigns", new JSONArray());
            patientBlock.put("Diagnosis", new JSONArray());
            patientBlock.put("Allergies", new JSONArray());

            // Add the patient block to the existing insignificant data
            JSONArray patientArray = (JSONArray) insignificantData.get(patientId);
            if (patientArray == null) {
                patientArray = new JSONArray();
            }
            patientArray.add(patientBlock);
            insignificantData.put(patientId, patientArray);

            // Write the updated data back to the JSON file
            insignificantDataHandler.writeDataToFile(insignificantData, insignificantFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
