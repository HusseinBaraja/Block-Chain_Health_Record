package Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Users {
    enum UserRole { ADMIN, RECEPTIONIST, DOCTOR, PATIENT, HEALTH_PROVIDER }
    String fullName, DOB, gender;
    UserRole role;
    int age, phoneNumber;

    List<String> userDetails = new ArrayList<>();
    private static HashMap<String, List<String>> userDatabase = new HashMap<>();

    public Users(String fullName, String DOB, String gender, int age, int phoneNumber) {
        this.fullName = fullName;
        this.DOB = DOB;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }
    public boolean signup(String username, String password, UserRole role) {
        if(userDatabase.containsKey(username)) {
            System.out.println("Username already exists. Choose another.");
            return false;
        }

        userDetails.add(password);
        userDetails.add(role.toString());
        userDatabase.put(username, userDetails);

        System.out.println("Signup successful!");
        return true;
    }

    public boolean login(String username, String password) {
        if(userDatabase.containsKey(username)) {
            if(userDatabase.get(username).equals(encryptPassword(password))) {
                System.out.println("Login successful!");
                return true;
            }
        }
        if(role == UserRole.ADMIN){

        } else if (role == UserRole.DOCTOR) {


        } else if (role == UserRole.PATIENT) {

        } else if (role == UserRole.RECEPTIONIST) {

        } else if (role == UserRole.HEALTH_PROVIDER) {

        } else {

        }
        System.out.println("Invalid username or password.");
        return false;
    }

    private static String encryptPassword(String password) {
        return Integer.toString(password.hashCode());
    }
}
