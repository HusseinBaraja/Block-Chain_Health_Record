package Users;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Users {
    private final String role;
    private final List<Users> managedUsers;

    public Admin(String fullName, String DOB, String gender, int age, int phoneNumber,
                 String role) {
        super(fullName, DOB, gender, age, phoneNumber);
        this.role = role;
        this.managedUsers = new ArrayList<>();
    }


    // Add a user to the list of users managed by this admin
    public void addUser(Users users) {
        if(!managedUsers.contains(users)) {
            managedUsers.add(users);
        } else {
            System.out.println("User is already managed by this admin.");
        }
    }

    // Remove a user from the list of users managed by this admin
    public void removeUser(Users users) {
        if(managedUsers.contains(users)) {
            managedUsers.remove(users);
        } else {
            System.out.println("User is not managed by this admin.");
        }
    }
}
