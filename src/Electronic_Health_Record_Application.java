import java.util.Scanner;

public class Electronic_Health_Record_Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to EHR System!");
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:

                case 2:

                case 3:
                    System.out.println("Exiting the system...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
