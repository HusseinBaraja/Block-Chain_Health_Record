package Validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    /**
     * Validate a date using regex and date format.
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the date input.
     * @param dateFormat The expected date format (e.g., "yyyy-MM-dd").
     * @return The validated date as a string in the specified format.
     */
    public static String valDate(String prompt, String inputName, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

        while (true) {
            System.out.print(prompt);
            String dateString = scanner.nextLine().trim();

            Matcher matcher = datePattern.matcher(dateString);

            if (matcher.matches()) {
                // The input matches the expected date format, now check if it's a valid date
                try {
                    Date date = sdf.parse(dateString);
                    if (date != null) {
                        return dateString;
                    } else {
                        System.out.printf("\u001B[31mInvalid %s. Please enter a valid date in the format '%s'.\u001B[0m%n", inputName, dateFormat);
                    }
                } catch (ParseException e) {
                    System.out.printf("\u001B[31mInvalid %s. Please enter a valid date in the format '%s'.\u001B[0m%n", inputName, dateFormat);
                }
            } else {
                System.out.printf("\u001B[31mInvalid %s. Please enter a valid date in the format '%s'.\u001B[0m%n", inputName, dateFormat);
            }
        }
    }

    /**
     * Validate a weight input in kilograms.
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the weight input.
     * @return The validated weight input in kilograms.
     */
    public static float valWeight(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.printf("You didn't enter anything. Please input a valid %s%n", inputName);
            } else {
                try {
                    float weight = Float.parseFloat(input);
                    // You can add additional validation logic here if needed.
                    return weight;
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter a valid weight.");
                }
            }
        }
    }

    /**
     * Validate a height input in centimeters.
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the height input.
     * @return The validated height input in centimeters.
     */
    public static float valHeight(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.printf("You didn't enter anything. Please input a valid %s%n", inputName);
            } else {
                try {
                    float height = Float.parseFloat(input);
                    // You can add additional validation logic here if needed.
                    return height;
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter a valid height.");
                }
            }
        }
    }

    /**
     * Validate a temperature input in Celsius.
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the temperature input.
     * @return The validated temperature input in Celsius.
     */
    public static float valTemperature(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.printf("You didn't enter anything. Please input a valid %s%n", inputName);
            } else {
                try {
                    float temperature = Float.parseFloat(input);
                    // You can add additional validation logic here if needed.
                    return temperature;
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter a valid temperature.");
                }
            }
        }
    }

    /**
     * Validate a blood pressure input in the format "systolic/diastolic" (e.g., "120/80").
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the blood pressure input.
     * @return The validated blood pressure input in the specified format.
     */
    public static String valBloodPressure(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            // Define a regular expression pattern for valid blood pressure (e.g., "120/80")
            String bloodPressurePattern = "\\d{1,3}/\\d{1,3}";

            if (input.matches(bloodPressurePattern)) {
                return input;
            } else {
                System.out.printf("\u001B[31mInvalid %s. Please enter a valid blood pressure in the format 'systolic/diastolic' (e.g., '120/80').\u001B[0m%n", inputName);
            }
        }
    }

    /**
     * Validate a heart rate input in beats per minute (BPM).
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the heart rate input.
     * @return The validated heart rate input in BPM.
     */
    public static int valHeartRate(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                System.out.printf("You didn't enter anything. Please input a valid %s%n", inputName);
            } else {
                try {
                    int heartRate = Integer.parseInt(input);
                    // You can add additional validation logic here if needed.
                    return heartRate;
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid input. Please enter a valid heart rate (BPM).");
                }
            }
        }
    }
    /**
     * Validate a status input to be either "active" or "not active" (case-insensitive).
     *
     * @param prompt The prompt that should be displayed to the user.
     * @param inputName The name of the status input.
     * @return The validated status input ("active" or "not active").
     */
    public static String valStatus(String prompt, String inputName) {
        while (true) {
            System.out.print(prompt);
            String status = scanner.nextLine().trim().toLowerCase();

            if (status.equals("active") || status.equals("not active")) {
                return status;
            } else {
                System.out.printf("\u001B[31mInvalid %s. Please enter either 'active' or 'not active'.\u001B[0m%n", inputName);
            }
        }
    }



}

