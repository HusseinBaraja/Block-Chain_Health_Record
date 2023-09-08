package Users;

import Validation.InputValidator;
import Validation.JsonHandler;
import Validation.orderSignificance;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Doctor extends Users {
    private static String currPatientID;
    private static final String DEMOGRAPHIC_FILE = "src/database/insignificant_data.json";

    public Doctor(String username) {
        super(username); // Assuming the parent class "Users" needs this for its own reasons.
        JsonHandler handler = new JsonHandler();
        JSONObject userInfo = handler.getUserInfoByUserName("Doctor", username);

        if (userInfo != null) {
            username = (String) userInfo.get("Username");
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
            case 3:
                String patientUsername = InputValidator.valString("Enter the patient's username: ", "username");
                modifyPatientData(patientUsername);
                break;
            case 4:
                System.out.println("\u001B[32mLogging out from the account, Bye :-)\u001B[0m"); // Green logout message
                return;
            default:
                System.out.println("\u001B[31mInvalid choice.\u001B[0m"); // Red error message
        }
    }

    private void modifyPatientData(String patientUsername) {
        // Retrieve patient data from the JSON file
        JsonHandler jsonHandler = new JsonHandler();
        JSONObject patientInfo = jsonHandler.getPatientInfoByUsername(patientUsername);

        if (patientInfo != null) {
            // Get the categories available for modification from the JSON data
            Set<String> categories = patientInfo.keySet();

            // Display categories to the doctor and ask for selection
            int categoryChoice = displayAndSelectCategory(categories);

            if (categoryChoice >= 0 && categoryChoice < categories.size()) {
                String selectedCategory = (String) categories.toArray()[categoryChoice];
                JSONObject categoryData = (JSONObject) patientInfo.get(selectedCategory);

                // Get the fields available for modification in the selected category
                Set<String> fields = categoryData.keySet();

                // Display fields to the doctor and ask for selection
                int fieldChoice = displayAndSelectField(fields);

                if (fieldChoice >= 0 && fieldChoice < fields.size()) {
                    String selectedField = (String) fields.toArray()[fieldChoice];

                    // Ask the doctor to enter the new value for the selected field
                    String newValue = InputValidator.valString("Enter the new value for " + selectedField + ": ", selectedField);

                    // Update the selected field with the new value
                    categoryData.put(selectedField, newValue);

                    // Save the updated patient data
                    jsonHandler.updatePatientData(patientUsername, patientInfo);
                    System.out.println("Field updated successfully.");
                } else {
                    System.out.println("Invalid field choice.");
                }
            } else {
                System.out.println("Invalid category choice.");
            }
        } else {
            System.out.println("Patient not found.");
        }
    }

    private int displayAndSelectCategory(Set<String> categories) {
        System.out.println("Select a category to update:");

        int index = 0;
        for (String category : categories) {
            System.out.println((index + 1) + ". " + category);
            index++;
        }

        return InputValidator.valInt("Enter the number of the category to update (1-" + categories.size() + "): ", "category") - 1;
    }

    private int displayAndSelectField(Set<String> fields) {
        System.out.println("Select a field to update:");

        int index = 0;
        for (String field : fields) {
            System.out.println((index + 1) + ". " + field);
            index++;
        }

        return InputValidator.valInt("Enter the number of the field to update (1-" + fields.size() + "): ", "field") - 1;
    }

    private String CheckItemsExist(String reqItem, String username){
        JsonHandler getPatInfo = new JsonHandler();
        getPatInfo.getPatientInfoByUsername(username);

        String currentInfo = getPatInfo.getPatientKey();
        JsonHandler handler = new JsonHandler();
        String patientDataKeys = handler.getPatientDataKeys(currentInfo);
        List<String> keyList = Arrays.asList(patientDataKeys.split(","));
        return Boolean.toString(keyList.contains(reqItem));
    }

    private String currPatientName;
    private void addData() {
        String patient_username = InputValidator.valString("Enter patient username: ", "username");
        currPatientName = patient_username;
        String reqItem = "PatientIdentifiers";

        JsonHandler getPatInfo = new JsonHandler();

        JSONObject patientInfo = getPatInfo.getPatientInfoByUsername(patient_username);

        getPatInfo.getPatientInfoByUsername(patient_username);

        currPatientID = getPatInfo.getPatientKey();
        while (true){
            switch (CheckItemsExist(reqItem, patient_username)){
                case "true":
                    break;
                case "False":
                    String checkDocOptions = InputValidator.valString(
                            "This patient didn't add his basic information yet, would you like to do that? ",
                            "yes or no");
                    switch (checkDocOptions) {
                        case "yes" -> addIdentifiersData(currPatientID);
                        case "no" -> {
                            return;
                        }
                        default -> {
                            System.out.println("Wrong input, please input yes or no!");
                            continue;
                        }
                    }

            }
            break;
        }

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
                addIdentifiersData(currPatientID);
                break;
            case 2:
                addDemographicData(currPatientID);
                break;
            case 3:
                addDiagnosisData(currPatientID, patientInfo);
                break;
            case 4:
                addAllergiesData(currPatientID, patientInfo);
                break;
            case 5:
                addImmunizationsData(currPatientID);
                break;
            case 6:
                addMedicationsData(currPatientID);
                break;
            case 7:
                addProceduresData(currPatientID, patientInfo);
                break;
            case 8:
                addLaboratoryTestResultsData(currPatientID);
                break;
            case 9:
                addVitalSignsData(currPatientID, patientInfo);
                break;
            case 10:
                addImagingReportsData(currPatientID, patientInfo);
                break;
            case 11:
                System.out.println("\u001B[32mReturning to Doctor Menu, Bye :-)\u001B[0m"); // Green return message
                return;
            default:
                System.out.println("\u001B[31mInvalid choice.\u001B[0m"); // Red error message
        }
    }

    private void addDemographicData(String patientId) {
        JSONObject patientData = new JSONObject();

        System.out.println("--- Patient Demographic Data ---");
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
        inputData.put(patientId, patientData);

        orderSignificance addUser = new orderSignificance();

        try {
            addUser.sortData(inputData, "DemographicInformation");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addData();
    }

    private void addDiagnosisData(String patientId, JSONObject data) {
        JSONObject patientData = new JSONObject();
        System.out.println("--- Add Patient Diagnosis Data ---");
        JSONObject DiagnosisInformation = new JSONObject();

        String diagnosisID = generateAndIncrementID(data, "Diagnosis");
        DiagnosisInformation.put("DiagnosisID", diagnosisID);

        String diagnosisDescription = InputValidator.valString("Enter Diagnosis Description: ", "Diagnosis Description");
        DiagnosisInformation.put("DiagnosisDescription", diagnosisDescription);

        String dateOfDiagnosis = InputValidator.valString("Enter Date of Diagnosis: ", "Date Of Diagnosis");
        DiagnosisInformation.put("DateOfDiagnosis", dateOfDiagnosis);

        String diagnosisStatus = InputValidator.valString("Enter Diagnosis Status: ", "Diagnosis Status");
        DiagnosisInformation.put("DiagnosisStatus", diagnosisStatus);

        // check again
        String diagnosisProvider = InputValidator.valString("Enter Diagnosis Provider: ", "Diagnosis Provider");
        DiagnosisInformation.put("DiagnosisProvider", diagnosisProvider);

        String notes = InputValidator.valString("Enter Notes: ", "Notes");
        DiagnosisInformation.put("Notes", notes);

        patientData.put("Diagnosis", DiagnosisInformation);

        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put(patientId, patientData);

        orderSignificance addUser = new orderSignificance();

        try {
            addUser.sortData(inputData, "Diagnosis");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addData();
    }

    private void addAllergiesData(String patientId, JSONObject data) {
        JSONObject patientData = new JSONObject();

        System.out.println("--- Add Patient Allergy Data ---");
        JSONObject allergiesInformation = new JSONObject();

        String allergyID = generateAndIncrementID(data, "Allergies");
        allergiesInformation.put("AllergiesID", allergyID);

        String allergenName = InputValidator.valString("Enter Allergen Name: ", "Allergen Name");
        allergiesInformation.put("AllergenName", allergenName);

        String reactionSeverity = InputValidator.valString("Enter Reaction Severity: ", "Reaction Severity");
        allergiesInformation.put("ReactionSeverity", reactionSeverity);

        String reactionDescription = InputValidator.valString("Enter Reaction Description: ", "Reaction Description");
        allergiesInformation.put("ReactionDescription", reactionDescription);

        String diagnosisDate = InputValidator.valString("Enter Diagnosis Date: ", "Diagnosis Date");
        allergiesInformation.put("DiagnosisDate", diagnosisDate);

        String treatmentPlan = InputValidator.valString("Enter Treatment Plan: ", "Treatment Plan");
        allergiesInformation.put("TreatmentPlan", treatmentPlan);

        String status = InputValidator.valString("Enter Status: ", "Status");
        allergiesInformation.put("Status", status);

        String medicationToAvoid = InputValidator.valString("Enter Medication To Avoid: ", "Medication To Avoid");
        allergiesInformation.put("MedicationToAvoid", medicationToAvoid);

        patientData.put("Allergies", allergiesInformation);

        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put(patientId, patientData);

        orderSignificance addUser = new orderSignificance();

        try {
            addUser.sortData(inputData, "Allergies");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addData();
    }

    private void addImmunizationsData(String patientId) {
        JSONObject patientData = new JSONObject();

        System.out.println("--- Add Patient Immunization Data ---");
        JSONObject immunizationsInformation = new JSONObject();


        String vaccineName = InputValidator.valString("Enter Vaccine Name: ", "Vaccine Name");
        immunizationsInformation.put("VaccineName", vaccineName);

        String dateAdministered = InputValidator.valString("Enter Date Administered: ", "Date Administered");
        immunizationsInformation.put("DateAdministered", dateAdministered);

        String administeringClinic = InputValidator.valString("Enter Administering Clinic or Hospital: ", "Administering Clinic");
        immunizationsInformation.put("AdministeringClinic", administeringClinic);

        patientData.put("Immunizations", immunizationsInformation);

        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put(patientId, patientData);

        orderSignificance addUser = new orderSignificance();

        try {
            addUser.sortData(inputData, "Immunizations");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addData();
    }

    private void addMedicationsData(String patientId) {
        JSONObject patientData = new JSONObject();

        System.out.println("--- Add Patient Medication Data ---");

        JSONObject medicationsInformation = new JSONObject();

        String medicationName = InputValidator.valString("Enter Medication Name: ", "Medication Name");
        medicationsInformation.put("MedicationName", medicationName);

        String dosage = InputValidator.valString("Enter Dosage: ", "Dosage");
        medicationsInformation.put("Dosage", dosage);

        String frequency = InputValidator.valString("Enter Frequency: ", "Frequency");
        medicationsInformation.put("Frequency", frequency);

        patientData.put("Medications", medicationsInformation);

        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put(patientId, patientData);

        orderSignificance addUser = new orderSignificance();

        try {
            addUser.sortData(inputData, "Medications");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addData();
    }

    private void addProceduresData(String patientId, JSONObject data) {
        JSONObject patientData = new JSONObject();

        System.out.println("--- Add Patient Procedure Data---");
        JSONObject proceduresInformation = new JSONObject();

        String procedureID = generateAndIncrementID(data, "Procedures");
        proceduresInformation.put("ProceduresID", procedureID);

        String procedureName = InputValidator.valString("Enter Procedure Name: ", "Procedure Name");
        proceduresInformation.put("ProcedureName", procedureName);

        String procedureDate = InputValidator.valString("Enter Procedure Date: ", "Procedure Date");
        proceduresInformation.put("ProcedureDate", procedureDate);

        String procedureDoctor = InputValidator.valString("Enter Procedure Doctor: ", "Procedure Doctor");
        proceduresInformation.put("ProcedureDoctor", procedureDoctor);

        String procedureNotes = InputValidator.valString("Enter Procedure Notes: ", "Procedure Notes");
        proceduresInformation.put("ProcedureNotes", procedureNotes);

        patientData.put("Procedures", proceduresInformation);

        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put(patientId, patientData);

        orderSignificance addUser = new orderSignificance();

        try {
            addUser.sortData(inputData, "Procedures");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addData();
    }

    private void addVitalSignsData(String patientId, JSONObject data) {
        JSONObject patientData = new JSONObject();

        System.out.println("--- Add Patient Vital Signs Data---");

        JSONObject vitalSignsInformation = new JSONObject();

        String vitalSignsID = generateAndIncrementID(data, "VitalSigns");
        vitalSignsInformation.put("VitalSignsID", vitalSignsID);

        String temperature = InputValidator.valString("Enter Temperature: ", "Temperature");
        vitalSignsInformation.put("Temperature", temperature);

        String height = InputValidator.valString("Enter Height: ", "Height");
        vitalSignsInformation.put("Height", height);

        String weight = InputValidator.valString("Enter Weight: ", "Procedure Doctor");
        vitalSignsInformation.put("Weight", weight);

        String bloodPressure = InputValidator.valString("Enter Blood Pressures: ", "Blood Pressure");
        vitalSignsInformation.put("BloodPressure", bloodPressure);

        String heartRate = InputValidator.valString("Enter Heart Rate: ", "Heart Rate");
        vitalSignsInformation.put("HeartRate", heartRate);

        patientData.put("VitalSigns", vitalSignsInformation);
        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put(patientId, patientData);

        orderSignificance addUser = new orderSignificance();

        try {
            addUser.sortData(inputData, "VitalSigns");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addData();
    }

    private void addLaboratoryTestResultsData(String patientId) {
        JSONObject patientData = new JSONObject();

        System.out.println("--- Add Patient Laboratory Test Result Data ---");

        JSONObject laboratoryTestResultsInformation = new JSONObject();
        String testName = InputValidator.valString("Enter Test Name: ", "Test Name");

        laboratoryTestResultsInformation.put("TestName", testName);
        String result = InputValidator.valString("Enter Result: ", "Result");

        laboratoryTestResultsInformation.put("Result", result);
        String labTechnicianName = InputValidator.valString("Enter Lab Technician Name: ", "Lab Technician Name");

        laboratoryTestResultsInformation.put("LabTechnicianName", labTechnicianName);
        // Modify
        String testTimestamp = InputValidator.valString("Enter TestTimestamp: ", "Test Timestamp");

        laboratoryTestResultsInformation.put("TestTimestamp", testTimestamp);
        patientData.put("LaboratoryTestResults", laboratoryTestResultsInformation);

        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put(patientId, patientData);

        orderSignificance addUser = new orderSignificance();

        try {
            addUser.sortData(inputData, "LaboratoryTestResults");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addData();

    }

    private void addImagingReportsData(String patientId, JSONObject data) {
        JSONObject patientData = new JSONObject();

        System.out.println("--- Add Patient Imaging Reports Data---");

        JSONObject imagingReportsInformation = new JSONObject();

        String imagingReportID = generateAndIncrementID(data, "ImagingReports");
        imagingReportsInformation.put("ImagingReportsID", imagingReportID);

        String reportName = InputValidator.valString("Enter Report Name: ", "Report Name");
        imagingReportsInformation.put("Report Name", reportName);

        String reportResult = InputValidator.valString("Enter Report Result: ", "ReportResult");
        imagingReportsInformation.put("ReportResult", reportResult);

        String administeringClinic = InputValidator.valString("Enter Administering Clinic: ", "Administering Clinic");
        imagingReportsInformation.put("AdministeringClinic", administeringClinic);

        patientData.put("ImagingReports", imagingReportsInformation);

        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put(patientId, patientData);

        orderSignificance addUser = new orderSignificance();

        try {
            addUser.sortData(inputData, "ImagingReports");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addData();
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

    private void addIdentifiersData(String patientId){

        JSONObject patientData = new JSONObject();

        System.out.println("--- Patient Identifiers Data ---");

        JSONObject patientIdentifiers = new JSONObject();

        String fullName = InputValidator.valString("Enter FullName: ", "Full Name");
        patientIdentifiers.put("FullName", fullName);

        String dob = InputValidator.valDateOfBirth("Enter DateOfBirth: ", "Date of Birth", "yyyy-MM-dd");
        patientIdentifiers.put("DateOfBirth", dob);

        String address = InputValidator.valString("Enter Address: ", "Address");
        patientIdentifiers.put("Address", address);

        String phoneNumber = InputValidator.valPhoneNumber("Enter PhoneNumber: ", "Phone Number");
        patientIdentifiers.put("PhoneNumber", phoneNumber);

        patientIdentifiers.put("Username", currPatientName);

        patientData.put("PatientIdentifiers", patientIdentifiers);

        JSONObject inputData = new JSONObject();

        //Here we should generate new PatientIDs for new patients
        inputData.put(patientId, patientData);

        orderSignificance addUser = new orderSignificance();

        try {
            addUser.sortData(inputData, "PatientIdentifiers");
        } catch (Exception e) {
            e.printStackTrace();
        }
        addData();
    }

    private String generateAndIncrementID(JSONObject patientData, String category) {
        String lastID = "";
        JSONArray categoryArray = (JSONArray) patientData.get(category);

        if (categoryArray.size() > 0) {
            JSONObject lastItem = (JSONObject) categoryArray.get(categoryArray.size() - 1);
            lastID = (String) lastItem.get(category + "ID");
        }

        // Generate the new ID by incrementing the last ID
        String newID;
        if (!lastID.isEmpty()) {
            int lastIDNumber = Integer.parseInt(lastID.substring(2));
            if (category == "VitalSigns") newID = "VS" + (lastIDNumber + 1);
            else if(category == "Procedures") newID = "Pro" + (lastIDNumber + 1);
            else if (category == "Diagnosis") newID = "Dia" + (lastIDNumber + 1);
            else if (category == "Allergies") newID = "AL" + (lastIDNumber + 1);
            else newID = "IR" + (lastIDNumber + 1);
        } else {
            if (category == "VitalSigns") newID = "VS1";
            else if(category == "Procedures") newID = "Pro1";
            else if (category == "Diagnosis") newID = "Dia1";
            else if (category == "Allergies") newID = "AL1";
            else newID = "IR1";

        }

        return newID;
    }

}
