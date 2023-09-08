package Users;

import Blockchain.Hasher;
import Cryptography.KeyAccess;
import Cryptography.MyKeyPair;
import Validation.InputValidator;
import Validation.JsonHandler;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;


public class HealthProvider {
    private String username;

    public HealthProvider(String username) throws IOException {
        this.username = username;
        switch (new JsonHandler().getAccessStatus(username, "HealthProvider")) {
            case 1 -> healthProviderMenu();
            case 0 -> System.out.println("This health provider doesn't have access to the system!");
            case 2 -> System.out.println("This health provider is not in the system!");
            default -> System.out.println("There was an error in the system!");
        }
    }
    private void healthProviderMenu() throws IOException {
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
                removeDoctor();
                break;
            case 3:
                System.out.println("Logging out from the account, Bye :-)");
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void addDoctor() throws IOException {
        clearScreen();

        System.out.println("\u001B[36mAdd Doctor:\u001B[0m"); // Cyan header

        String name = InputValidator.valString("\u001B[33mEnter doctor's name: \u001B[0m", "doctor's name"); // Yellow input
        String username = InputValidator.valUsername("\u001B[33mEnter doctor's username: \u001B[0m", "username"); // Yellow input
        String password = InputValidator.valPassword("\u001B[33mEnter doctor's password: \u001B[0m", "password"); // Yellow input
        String phoneNumber = InputValidator.valPhoneNumber("\u001B[33mEnter doctor's phone number: \u001B[0m", "phone number"); // Yellow input
        String dob = InputValidator.valDateOfBirth("\u001B[33mEnter doctor's date of birth (YYYY-MM-DD): \u001B[0m", "date of birth", "yyyy-MM-dd");
        int age = InputValidator.valInt("\u001B[33mEnter doctor's age: \u001B[0m", "age"); // Yellow input
        String gender = InputValidator.valGender("\u001B[33mEnter your gender ('male,' 'female,' or 'other'): \u001B[0m", "gender");

        String placeOfWork = new JsonHandler().getItem("Name", "HealthProvider", this.username);

        // Generate the doctor's key pair
        MyKeyPair.create();
        PublicKey publicKey = MyKeyPair.getPublicKey();
        PrivateKey privateKey = MyKeyPair.getPrivateKey();

        // Store the doctor's key pair in separate files
        String publicKeyPath = "account_directory/" + username + "_public_key.pem";
        String privateKeyPath = "account_directory/" + username + "_private_key.pem";


        KeyAccess.put(publicKey.getEncoded(), publicKeyPath);
        KeyAccess.put(privateKey.getEncoded(), privateKeyPath);

        // Hash the password using SHA-384
        String hashedPassword = Hasher.sha384(password);

        String[] userAttributes = {"Name", "Username", "Password", "PhoneNumber", "DOB", "Age", "Gender", "PlaceOfWork", "PublicKeyPath", "PrivateKeyPath"};
        Object[] newDoctor = {name, username, hashedPassword, phoneNumber, dob, age, gender, placeOfWork, publicKeyPath, privateKeyPath};

        JsonHandler healthProvider = new JsonHandler();
        healthProvider.addNewUser("Doctor", userAttributes, newDoctor);

        System.out.println("\u001B[32mDoctor added successfully!\u001B[0m"); // Green success message
    }

    private static void removeDoctor() {
        clearScreen();
        System.out.println("\u001B[36mRemove Doctor:\u001B[0m");

        // List all doctors from the JSON data
        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.printItems("Doctor");

        String doctorUsernameToRemove = InputValidator.valString(  "\u001B[33mEnter the username of the doctor to remove: \u001B[0m", "username");

        // Remove the doctor with the specified username
        if (jsonHandler.removeItem("Doctor", "Username", doctorUsernameToRemove)) {
            System.out.println("\u001B[32mDoctor removed successfully!\u001B[0m");
        } else {
            System.out.println("\u001B[31mDoctor not found or could not be removed.\u001B[0m");
        }
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}