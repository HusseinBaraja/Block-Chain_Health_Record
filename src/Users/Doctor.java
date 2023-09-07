package Users;

import Validation.InputValidator;
import Validation.JsonHandler;
import Validation.orderSignificance;
import org.json.simple.JSONObject;

import java.util.Scanner;

public class Doctor extends Users {
    private String name, username, phoneNumber, DOB, gender, currPatientID;
    private int age;
    private Scanner scanner = new Scanner(System.in);
    private static final String DEMOGRAPHIC_FILE = "src/database/insignificant_data.json";

    public Doctor(String username) {
        super(username); // Assuming the parent class "Users" needs this for its own reasons.
        JsonHandler handler = new JsonHandler();
        JSONObject userInfo = handler.getUserInfoByUserName("Doctor", username);

        if (userInfo != null) {
            this.name = (String) userInfo.get("Name");
            this.username = (String) userInfo.get("Username");
            this.phoneNumber = (String) userInfo.get("PhoneNumber");
            this.DOB = (String) userInfo.get("DOB");
            this.age = ((Long) userInfo.get("Age")).intValue();
            this.gender = (String) userInfo.get("Gender");
        } else {
            System.out.println("Doctor info was not found!");
        }

        switch (new JsonHandler().getAccessStatus(username, "Doctor")) {
            case 1 -> doctorMenu();
            case 0 -> System.out.println("This doctor doesn't have access to the system!");
            case 2 -> System.out.println("This doctor is not in the system!");
            default -> System.out.println("There was an error in the system!");
        }
    }

    private void doctorMenu() {
        System.out.println("\u001B[36mDoctor Menu:\u001B[0m"); // Cyan header
        System.out.println("\u001B[32m1. View Patient Information\u001B[0m"); // Green menu item
        System.out.println("\u001B[32m2. Add Patient Information\u001B[0m"); // Green menu item
        System.out.println("\u001B[32m3. Modify Patient Information Patient\u001B[0m"); // Green menu item
        System.out.println("\u001B[32m4. Logout\u001B[0m"); // Green menu item

        int choice = InputValidator.valInt("\u001B[36mChoice:\u001B[0m ", "choice"); // Cyan input prompt

        switch (choice) {
            case 1:
                viewPatientData();
                break;
            case 2:
                addData();
                break;
            case 4:
//                modifyPatientData();
                break;
            case 3:
                System.out.println("\u001B[32mLogging out from the account, Bye :-)\u001B[0m"); // Green logout message
                return;
            default:
                System.out.println("\u001B[31mInvalid choice.\u001B[0m"); // Red error message
        }
    }

    private void addData() {
        System.out.println("\u001B[36mData Entry Menu:\u001B[0m"); // Cyan header
        System.out.println("1. \u001B[32mAdd Patient Identifiers Data\u001B[0m"); // Green menu item
        System.out.println("2. \u001B[32mAdd Patient Demographic Data\u001B[0m"); // Green menu item
        System.out.println("3. \u001B[32mAdd Patient Diagnosis Data\u001B[0m"); // Green menu item
        System.out.println("4. \u001B[32mAdd Patient Allergies Data\u001B[0m"); // Green menu item
        System.out.println("5. \u001B[32mAdd Patient Immunizations Data\u001B[0m"); // Green menu item
        System.out.println("6. \u001B[32mAdd Patient Medications Data\u001B[0m"); // Green menu item
        System.out.println("7. \u001B[32mAdd Patient Procedures Data\u001B[0m"); // Green menu item
        System.out.println("8. \u001B[32mAdd Patient Laboratory Test Results Data\u001B[0m"); // Green menu item
        System.out.println("9. \u001B[32mAdd Patient Vital Signs Data\u001B[0m"); // Green menu item
        System.out.println("10. \u001B[32mAdd Patient Imaging Reports Data\u001B[0m"); // Green menu item
        System.out.println("11. \u001B[32mBack to Doctor Menu\u001B[0m"); // Green menu item

        int option = InputValidator.valInt("\u001B[36mEnter your choice:\u001B[0m ", "choice"); // Cyan input prompt

        switch (option) {
            case 1:
                addIdentifiersData();
                break;
            case 2:
                addDemographicData();
                break;
            case 3:
                addDiagnosisData();
                break;
            case 4:
                addAllergiesData();
                break;
            case 5:
                addImmunizationsData();
                break;
            case 6:
                addMedicationsData();
                break;
            case 7:
                addProceduresData();
                break;
            case 8:
                addLaboratoryTestResultsData();
                break;
            case 9:
                addVitalSignsData();
                break;
            case 10:
                addImagingReportsData();
                break;
            case 11:
                System.out.println("\u001B[32mReturning to Doctor Menu, Bye :-)\u001B[0m"); // Green return message
                return;
            default:
                System.out.println("\u001B[31mInvalid choice.\u001B[0m"); // Red error message
        }
    }

    private void addDemographicData() {
        JSONObject patientData = new JSONObject();

        System.out.println("--- Patient Demographic ---");
        JSONObject DemographicInformation = new JSONObject();

        // Validate and set patient gender
        String gender = InputValidator.valGender("Enter patient gender ('male,' 'female,' or 'other'): ", "gender");
        DemographicInformation.put("Gender", gender);

        // Validate and set patient age
        int age = InputValidator.valInt("Enter patient age: ", "age");
        DemographicInformation.put("Age", age);

        String race = InputValidator.valString("Enter patient race: ", "race");
        DemographicInformation.put("Race", race);

        // Validate and set patient blood type
        String bloodType = InputValidator.valBloodType("Enter patient blood type (e.g., 'A+', 'B-', 'AB+'): ", "blood type");
        DemographicInformation.put("BloodType", bloodType);

        patientData.put("DemographicInformation", DemographicInformation);

        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put("P3", patientData);

        orderSignificance addUser = new orderSignificance();
        try {
            addUser.sortData(inputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doctorMenu();
    }

    private void addDiagnosisData() {
    }

    private void addAllergiesData() {
    }

    private void addImmunizationsData() {
    }

    private void addMedicationsData() {
    }

    private void addProceduresData() {
    }

    private void addVitalSignsData() {
    }

    private void addLaboratoryTestResultsData() {
    }

    private void addImagingReportsData() {
    }

    // Significant
    private void viewPatientData() {
        String patient_username = InputValidator.valString("Enter patient username: ", "username");

        JsonHandler getPatInfo = new JsonHandler();
        JSONObject demographicData = getPatInfo.getPatientInfoByUsername(patient_username);

        currPatientID = getPatInfo.getPatientKey();

        JSONObject identifiers = (JSONObject) demographicData.get("PatientIdentifiers");
        JSONObject diagnosis = (JSONObject) demographicData.get("Diagnosis");
        JSONObject allergies = (JSONObject) demographicData.get("Allergies");
        JSONObject procedures = (JSONObject) demographicData.get("Procedures");
        JSONObject vitalSigns = (JSONObject) demographicData.get("VitalSigns");
        JSONObject imagingReports = (JSONObject) demographicData.get("ImagingReports");

        System.out.println("Patient Identifiers:");
        System.out.println("username: " + identifiers.get("DateOfBirth"));
        System.out.println("Address: " + identifiers.get("Address"));
        System.out.println("Phone Number: " + identifiers.get("PhoneNumber"));

        System.out.println("\nDiagnosis:");
        System.out.println("Diagnose ID: " + diagnosis.get("DiagnoseID"));
        System.out.println("Description: " + diagnosis.get("DiagnosisDescription"));
        System.out.println("Notes: " + diagnosis.get("Notes"));

        System.out.println("\nAllergies:");
        System.out.println("Reaction Description: " + allergies.get("ReactionDescription"));
        System.out.println("Treatment Plan: " + allergies.get("TreatmentPlan"));

        System.out.println("\nProcedures:");
        System.out.println("Procedure Notes: " + procedures.get("ProcedureNotes"));

        System.out.println("\nVital Signs:");
        System.out.println("Height: " + vitalSigns.get("Height"));
        System.out.println("Weight: " + vitalSigns.get("Weight"));

        System.out.println("\nImaging Reports:");
        System.out.println("Administering Clinic: " + imagingReports.get("AdministeringClinic"));

    }

    private void addIdentifiersData(){
        JSONObject patientData = new JSONObject();

        System.out.println("--- Patient Identifiers ---");
        JSONObject patientIdentifiers = new JSONObject();
        System.out.print("Enter FullName: ");
        patientIdentifiers.put("FullName", scanner.nextLine());
        System.out.print("Enter DateOfBirth: ");
        patientIdentifiers.put("DateOfBirth", scanner.nextLine());
        System.out.print("Enter Address: ");
        patientIdentifiers.put("Address", scanner.nextLine());
        System.out.print("Enter PhoneNumber: ");
        patientIdentifiers.put("PhoneNumber", scanner.nextLine());

        patientData.put("PatientIdentifiers", patientIdentifiers);

        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put("P3", patientData);

        orderSignificance addUser = new orderSignificance();
        try {
            addUser.sortData(inputData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        doctorMenu();
    }

}
