import java.util.Objects;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.regex.Pattern;

public class InputValidatorUtility {

    private Scanner scanner;

    public InputValidatorUtility() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Validate string inputs based on provided pattern.
     *
     * @param Prompt The prompt that should be displayed to the user.
     * @param inputName the name of the string input
     * @return The validated string input.
     */
    public String validateString(String Prompt, String inputName) {
        while (true) {
            System.out.println(Prompt);
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.printf("Please input a valid %s%n", inputName);
            }
        }
    }

    /**
     * Validate integer inputs based on range, sign, and other requirements.
     *
     * @param prompt The minimum acceptable value.
     * @param inputName The maximum acceptable value.
     * @return The validated integer input.
     */
    public int validateNumber(String prompt, String inputName, String numType) {
        while (true) {
            System.out.println(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.printf("You didn't enter anything, Please input a valid %s%n", inputName);
            } else {
                if (numType.equals("int")){
                    try {
                        return Integer.parseInt(input);
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                } else if (numType.equals("float")) {
                    try {
                        float f;
                        f = Float.parseFloat(input);
//                        return Float.parseFloat(input);
                    } catch (NumberFormatException ex) {
                        System.out.println("Invalid input. Please enter a valid number.");
                    }
                }

            }
        }
    }
    
}

