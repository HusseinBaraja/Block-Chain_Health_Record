package Users;

import Application.Electronic_Health_Record_Application;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Users {
    public enum UserRole { ADMIN, RECEPTIONIST, DOCTOR, PATIENT, HEALTH_PROVIDER, NONE }

    private String fullName, DOB, gender;
    private int age, phoneNumber;
    private String username;
    private String password;
    private UserRole role;

    public Users(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
        loadUserDatabaseFromJson();
    }

    public Users(String fullName, String DOB, String gender, int age, int phoneNumber) {
        this.fullName = fullName;
        this.DOB = DOB;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public boolean signup() {
        if (Electronic_Health_Record_Application.userDatabase.containsKey(username)) {
            System.out.println("Username already exists. Choose another.");
            return false;
        }

        List<String> userDetails = new ArrayList<>();
        userDetails.add(encryptPassword(password));
        userDetails.add(role.toString());
        userDetails.add("false"); // Initial authorization status

        Electronic_Health_Record_Application.userDatabase.put(username, userDetails);
        saveUserDatabaseToJson();

        return true;
    }

    public boolean login() {
        if (Electronic_Health_Record_Application.userDatabase.containsKey(username)) {
            List<String> userDetails = Electronic_Health_Record_Application.userDatabase.get(username);
            String storedPasswordHash = userDetails.get(0);
            String storedRole = userDetails.get(1);
            String authorized = userDetails.get(2);

            if (encryptPassword(password).equals(storedPasswordHash)) {
                if (authorized.equals("true")) {
                    role = UserRole.valueOf(storedRole); // Update user role
                    return true;
                } else {
                    System.out.println("User not yet authorized by admin.");
                }
            }
        }

        System.out.println("Invalid username or password.");
        return false;
    }

    public static void saveUserDatabaseToJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(Electronic_Health_Record_Application.userDatabase);

        try (FileWriter file = new FileWriter("user_database.json")) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserDatabaseFromJson() {
        try (FileReader fileReader = new FileReader("user_database.json")) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);

            for (Object key : jsonObject.keySet()) {
                String username = (String) key;
                List<String> userDetails = (List<String>) jsonObject.get(username);

                // Decrypt the stored password
                String storedPasswordHash = userDetails.get(0);
                String decryptedPassword = decryptPassword(storedPasswordHash);
                userDetails.set(0, decryptedPassword);

                Electronic_Health_Record_Application.userDatabase.put(username, userDetails);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private String decryptPassword(String encryptedPassword) {
        return encryptedPassword;
    }

    private static String encryptPassword(String password) {
        return Integer.toString(password.hashCode());
    }

    public UserRole getRole() {
        return role;
    }
}
