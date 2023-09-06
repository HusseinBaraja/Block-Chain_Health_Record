package Validation;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * Validate a phone number based on a regular expression pattern.
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName the name of the phone number input.
     * @return The validated phone number input.
     */
    public static String valPhoneNumber(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String phoneNumber = scanner.nextLine();

            // Define a regular expression pattern for a simple phone number (change as needed)
            String phoneNumberPattern = "^\\d{10}$"; // Assumes a 10-digit phone number

            if (phoneNumber.matches(phoneNumberPattern)) {
                return phoneNumber;
            } else {
                System.out.printf("\u001B[31mInvalid %s. Please enter a valid 10-digit phone number.\u001B[0m%n", inputName);
            }
        }
    }

    /**
     * Validate a username based on the specified criteria.
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the username input.
     * @return The validated username input.
     */
    public static String valUsername(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String username = scanner.nextLine();

            // Define a regular expression pattern for username validation
            String usernamePattern = "^[A-Za-z][A-Za-z0-9_]{5,29}$"; // 6 to 30 characters, starts with a letter

            if (username.matches(usernamePattern)) {
                return username;
            } else {
                System.out.printf("\u001B[31mInvalid %s. Please enter a valid username.\u001B[0m%n", inputName);
                System.out.println("\u001B[31mUsername must be 6 to 30 characters, start with a letter, and only contain alphanumeric characters and underscores.\u001B[0m");
            }
        }
    }

    /**
     * Validate a password based on the specified regex pattern.
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the password input.
     * @return The validated password input.
     */
    public static String valPassword(String prompt, String inputName) {
        // Define a regex pattern for password validation
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*(password|123456|qwerty)).{8,}$";

        Pattern pattern = Pattern.compile(passwordPattern);

        while (true) {
            System.out.print(prompt);
            String password = scanner.nextLine();

            Matcher matcher = pattern.matcher(password);

            if (matcher.matches()) {
                return password;
            } else {
                System.out.printf("\u001B[31mInvalid %s. Password must meet the specified criteria.\u001B[0m%n", inputName);
                System.out.println("\u001B[31mPassword must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, and one number. It should not be a common word or easily guessed pattern.\u001B[0m");            }
        }
    }

    /**
     * Validate user input to be either "clinic" or "hospital".
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the input (e.g., "facility type").
     * @return The validated facility type input ("clinic" or "hospital").
     */
    public static String valFacilityType(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String facilityType = scanner.nextLine().trim().toLowerCase();

            if (facilityType.equals("clinic") || facilityType.equals("hospital")) {
                return facilityType;
            } else {
                System.out.printf("\u001B[31mInvalid %s. Please enter either 'clinic' or 'hospital'.\u001B[0m%n", inputName);
            }
        }
    }

    /**
     * Validate a date of birth (DOB) using regex and date format.
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the DOB input.
     * @param dateFormat The expected date format (e.g., "yyyy-MM-dd").
     * @return The validated DOB as a string in the specified format.
     */
    public static String valDateOfBirth(String prompt, String inputName, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

        while (true) {
            System.out.print(prompt);
            String dobString = scanner.nextLine().trim();

            Matcher matcher = datePattern.matcher(dobString);

            if (matcher.matches()) {
                // The input matches the expected date format, now check if it's a valid date
                try {
                    sdf.parse(dobString);
                    return dobString;
                } catch (Exception e) {
                    System.out.printf("\u001B[31mInvalid %s. Please enter a valid date of birth in the format '%s'.\u001B[0m%n", inputName, dateFormat);
                }
            } else {
                System.out.printf("\u001B[31mInvalid %s. Please enter a valid date of birth in the format '%s'.\u001B[0m%n", inputName, dateFormat);
            }
        }
    }

    /**
     * Validate gender input to be one of "male," "female," or "other."
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the gender input.
     * @return The validated gender input ("male," "female," or "other").
     */
    public static String valGender(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String gender = scanner.nextLine().trim().toLowerCase();

            if (gender.equals("male") || gender.equals("female") || gender.equals("other")) {
                return gender;
            } else {
                System.out.printf("\u001B[31mInvalid %s. Please enter a valid gender ('male,' 'female,' or 'other').\u001B[0m%n", inputName);
            }
        }
    }
    /**
     * Validate blood type input to follow a valid blood type pattern.
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the blood type input.
     * @return The validated blood type input.
     */
    public static String valBloodType(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String bloodType = scanner.nextLine().trim().toUpperCase();

            // Define a regular expression pattern for valid blood types
            String bloodTypePattern = "^(A|B|AB|O)[\\+\\-]$";

            if (bloodType.matches(bloodTypePattern)) {
                return bloodType;
            } else {
                System.out.printf("\u001B[31mInvalid %s. Please enter a valid blood type (e.g., 'A+', 'B-', 'AB+').\u001B[0m%n", inputName);
            }
        }
    }
}

