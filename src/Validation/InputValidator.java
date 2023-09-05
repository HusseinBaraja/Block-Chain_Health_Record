package Validation;

import java.util.Scanner;

public class InputValidator {

    private static final Scanner scanner = new Scanner(System.in);

    public InputValidator() {

    }

    /**
     * Validate string inputs based on provided pattern.
     *
     * @param Prompt The prompt that should be displayed to the user.
     * @param inputName the name of the string input.
     * @return The validated string input.
     */

    // clinic or hospital
    public static String valString(String Prompt, String inputName) {
        while (true) {
            System.out.print(Prompt);
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
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName the name of the Integer input.
     * @return The validated integer input.
     */
    public static int valInt(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.printf("You didn't enter anything, Please input a valid %s%n", inputName);
            } else {
                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter a whole Number.");
                }
            }
        }
    }

    /**
     * Validate integer inputs based on range, sign, and other requirements.
     *
     * @param prompt The prompt to be displayed.
     * @param inputName the name of the float input.
     * @return The validated Float input.
     */
    public static float valFloat(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.printf("You didn't enter anything, Please input a valid %s%n", inputName);
            } else {
                try {
                    return Float.parseFloat(input);
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter a whole or fractional number.");
                }

            }
        }
    }
}

