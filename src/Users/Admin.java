package Users;

import Validation.InputValidator;
import Validation.JsonHandler;

public class Admin extends Users {

    public Admin(String username) {
        super(username);
        adminMenu();
    }

    private void adminMenu() {
        clearScreen();

        System.out.println("\u001B[36mAdmin Menu:\u001B[0m");
        System.out.println("\u001B[32m1. Grant Access to Health Provider\u001B[0m");
        System.out.println("\u001B[32m2. Revoke Access from Health Provider\u001B[0m");
        System.out.println("\u001B[32m3. Logout\u001B[0m");

        System.out.print("\u001B[33mChoose an option: \u001B[0m");
        int actionChoice = InputValidator.valInt("", "name");

        JsonHandler manageAccess = new JsonHandler();
        switch (actionChoice) {
            case 1 -> manageAccess.grantAccessToHealthProvider(); // Grant Access to Health Provider
            case 2 -> manageAccess.revokeAccessFromHealthProvider(); // Revoke Access from Health Provider
            case 3 -> System.out.println("\u001B[32mLogging out from the account, Bye :-)\u001B[0m"); // Green logout message
            default -> System.out.println("\u001B[31mInvalid admin action choice.\u001B[0m");
        }
    }
    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
//
//    // Add a user to the list of users managed by this admin
//    public void addUser(Users users) {
//        if(!managedUsers.contains(users)) {
//            managedUsers.add(users);
//        } else {
//            System.out.println("User is already managed by this admin.");
//        }
//    }
//
//    // Remove a user from the list of users managed by this admin
//    public void removeUser(Users users) {
//        if(managedUsers.contains(users)) {
//            managedUsers.remove(users);
//        } else {
//            System.out.println("User is not managed by this admin.");
//        }
//    }
}
