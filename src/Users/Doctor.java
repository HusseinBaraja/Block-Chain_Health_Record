package Users;

import Application.Electronic_Health_Record_Application;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Doctor extends Users {
    private HashMap<String, PatientRecord> patientRecords;
    private String workingHospital, speciality, qualification;
    private int yearsOfExperience;
    private List<Patient> patients;

    public Doctor(String fullName, String DOB, String gender, int age, int phoneNumber,
                  String workingHospital, String speciality, String qualification, int yearsOfExperience) {
        super(fullName, DOB, gender, age, phoneNumber);
        this.workingHospital = workingHospital;
        this.speciality = speciality;
        this.qualification = qualification;
        this.yearsOfExperience = yearsOfExperience;
        patients = new ArrayList<>();
    }
    Scanner scanner = new Scanner(System.in);
    public void DisplayOptions(){
        System.out.println("""
                1- Add Diagnosis
                2- Add Allergies
                3- Add Immunizations
                4- Add Medications
                5- Add Procedures
                6- Add Lab Test Results
                7- Vital Signs
                8- Add Imaging Reports\s""");
        int option = scanner.nextInt();

//        switch (option) {
//            case 1:
//                addDiagnosisToPatient();
//            case 2:
//                return Users.UserRole.DOCTOR;
//            case 3:
//                return Users.UserRole.PATIENT;
//            case 4:
//                return Users.UserRole.HEALTH_PROVIDER;
//            default:
//                throw new IllegalArgumentException("Invalid role choice.");
//        }
    }

    private void addDiagnosisToPatient() {
        System.out.println("Please enter the patient's name: ");
        String patientName = scanner.nextLine();


    }
    public void AddDiagnosis(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.putAll(Electronic_Health_Record_Application.userDatabase);

        try (FileWriter file = new FileWriter("user_database.json")) {
            file.write(jsonObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static class PatientRecord {
        String patientID;
        List<Diagnosis> diagnoses;
        List<Allergy> allergies;
        List<Immunization> immunizations;
        List<Medication> medications;
        List<Procedure> procedures;
        List<LabTestResult> labTestResults;
        List<VitalSign> vitalSigns;
        List<ImagingReport> imagingReports;

        PatientRecord(String patientID) {
            this.patientID = patientID;
            this.diagnoses = new ArrayList<>();
            this.allergies = new ArrayList<>();
            this.immunizations = new ArrayList<>();
            this.medications = new ArrayList<>();
            this.procedures = new ArrayList<>();
            this.labTestResults = new ArrayList<>();
            this.vitalSigns = new ArrayList<>();
            this.imagingReports = new ArrayList<>();
        }
    }

    class Diagnosis {
        private String diagnosisCode;
        private String description;
        private String date;
        private String status;
        private String provider;
        private String notes;

        public Diagnosis(String diagnosisCode, String description, String date, String status, String provider, String notes) {
            this.diagnosisCode = diagnosisCode;
            this.description = description;
            this.date = date;
            this.status = status;
            this.provider = provider;
            this.notes = notes;
        }

        public void AddDiagnosis(){
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(Electronic_Health_Record_Application.userDatabase);

            try (FileWriter file = new FileWriter("user_database.json")) {
                file.write(jsonObject.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Allergy {
        private String allergenName;
        private String reactionSeverity;
        private String reactionDescription;
        private String date;
        private String treatmentPlan;
        private String status;
        private String medicationToAvoid;

        public Allergy(String allergenName, String reactionSeverity, String reactionDescription, String date, String treatmentPlan, String status, String medicationToAvoid) {
            this.allergenName = allergenName;
            this.reactionSeverity = reactionSeverity;
            this.reactionDescription = reactionDescription;
            this.date = date;
            this.treatmentPlan = treatmentPlan;
            this.status = status;
            this.medicationToAvoid = medicationToAvoid;
        }
    }

    class Immunization {
        private String vaccineName;
        private String date;
        private String clinic;

        public Immunization(String vaccineName, String date, String clinic) {
            this.vaccineName = vaccineName;
            this.date = date;
            this.clinic = clinic;
        }

    }


    class Medication {
        private String medicationName;
        private String dosage;
        private String frequency;

        public Medication(String medicationName, String dosage, String frequency) {
            this.medicationName = medicationName;
            this.dosage = dosage;
            this.frequency = frequency;
        }
    }

    class Procedure {
        private String procedureName;
        private String date;
        private String doctor;
        private String notes;

        public Procedure(String procedureName, String date, String doctor, String notes) {
            this.procedureName = procedureName;
            this.date = date;
            this.doctor = doctor;
            this.notes = notes;
        }

    }

    class LabTestResult {
        private String testName;
        private String result;
        private String technicianName;
        private String timestamp;

        public LabTestResult(String testName, String result, String technicianName, String timestamp) {
            this.testName = testName;
            this.result = result;
            this.technicianName = technicianName;
            this.timestamp = timestamp;
        }
    }


    class VitalSign {
        private float temperature;
        private float height; //
        private float weight;
        private String bloodPressure;
        private int heartRate;

        public VitalSign(float temperature, float height, float weight, String bloodPressure, int heartRate) {
            this.temperature = temperature;
            this.height = height;
            this.weight = weight;
            this.bloodPressure = bloodPressure;
            this.heartRate = heartRate;
        }
    }

    class ImagingReport {
        private String reportName;
        private String result;
        private String clinic;

        public ImagingReport(String reportName, String result, String clinic) {
            this.reportName = reportName;
            this.result = result;
            this.clinic = clinic;
        }
    }


    void viewPatientInfo(String patientID) {
        PatientRecord record = patientRecords.get(patientID);
        if (record == null) {
            System.out.println("No record found for patient ID: " + patientID);
            return;
        }
    }




//    Scanner scanner = new Scanner(System.in);
//    public void addDiagnosis(){
//        int diagnosis_code = 0; // Will be changed
//        // Read current diagnoses from file
//        diagnosis_code ++;
//
//        System.out.println("Enter Diagnosis description: ");
//        String Description = scanner.nextLine();
//
//        System.out.println("Enter Diagnoses Date: ");
//        String DateOfDiagnoses = scanner.nextLine();
//
//        System.out.println("Enter Diagnoses status: ");
//        String DiagnosesStatus = scanner.nextLine();
//
//        System.out.println("Enter Diagnoses provider: ");
//        String DiagnosesProvider = scanner.nextLine();
//
//        System.out.println("Enter additional notes: ");
//        String Notes = scanner.nextLine();
//
//    }
//
//    public void addAllergies(){
//        System.out.println("Enter Allergy Name: ");
//        String AllergyName = scanner.nextLine();
//
//        System.out.println("Enter Reaction severity: ");
//        String ReactionSeverity = scanner.nextLine();
//
//        System.out.println("Enter Reaction description: ");
//        String ReactionDescription = scanner.nextLine();
//
//       // and more
//    }
//
//    public void addPatient(Patient patient) {
//        if (!patients.contains(patient)) {
//            patients.add(patient);
//        } else {
//            System.out.println("Patient already exists in the list.");
//        }
//    }
//
//    public void removePatient(Patient patient) {
//        if (patients.contains(patient)) {
//            patients.remove(patient);
//        } else {
//            System.out.println("Patient does not exist in the list.");
//        }
//    }

}
