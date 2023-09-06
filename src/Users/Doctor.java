package Users;

import Validation.InputValidator;
import Validation.JsonHandler;
import Validation.orderSignificance;
import org.json.simple.JSONObject;

import java.util.Scanner;

public class Doctor extends Users {
    private String name, username, phoneNumber, DOB, gender, currPatientID;
    private int age;

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
        doctorMenu();

    }
    private void doctorMenu(){
        System.out.println("Doctor Menu:");
        System.out.println("1. View Patient Information");
        System.out.println("2. Add patient");


        int choice = InputValidator.valInt("Choice: ", "choice");

        switch (choice) {
            case 1:
                ViewPatientData();
                break;
            case 2:
                AddPatientIdentifiers();
            case 9:
                System.out.println("Logging out from the account, Bye :-)");
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    private static final String DEMOGRAPHIC_FILE = "src/database/insignificant_data.json";

    // Significant
    private void ViewPatientData() {
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

    private void AddPatientIdentifiers(){
//        ViewPatientData();

        Scanner scanner = new Scanner(System.in);
        JSONObject patientData = new JSONObject();

        System.out.println("--- Patient Identifiers ---");
        JSONObject patientIdentifiers = new JSONObject();
        System.out.println("Enter FullName:");
        patientIdentifiers.put("FullName", scanner.nextLine());
        System.out.println("Enter DateOfBirth:");
        patientIdentifiers.put("DateOfBirth", scanner.nextLine());
        System.out.println("Enter Address:");
        patientIdentifiers.put("Address", scanner.nextLine());
        System.out.println("Enter PhoneNumber:");
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

    }

}
