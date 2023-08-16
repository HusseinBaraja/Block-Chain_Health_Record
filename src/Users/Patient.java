package Users;

import Users.Users;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Users {
    private String patientID;
    private String race;
    private String bloodType;
    private List<String> diagnoses, allergies, immunizations, medications,
            procedures, labTestResults, vitalSigns, imagingReports;

    public Patient(String fullName, String DOB, String gender, int age, int phoneNumber,
                   String patientID, String race, String bloodType) {
        super(fullName, DOB, gender, age, phoneNumber);
        this.patientID = patientID;
        this.race = race;
        this.bloodType = bloodType;
        this.diagnoses = new ArrayList<>();
        this.allergies = new ArrayList<>();
        this.immunizations = new ArrayList<>();
        this.medications = new ArrayList<>();
        this.procedures = new ArrayList<>();
        this.labTestResults = new ArrayList<>();
        this.vitalSigns = new ArrayList<>();
        this.imagingReports = new ArrayList<>();
    }

    public void addDiagnosis(String diagnosis) {
        diagnoses.add(diagnosis);
    }

    public void addAllergy(String allergy) {
        allergies.add(allergy);
    }

    public void addImmunization(String immunization) {
        immunizations.add(immunization);
    }

    public void addMedication(String medication) {
        medications.add(medication);
    }

    public void addProcedure(String procedure) {
        procedures.add(procedure);
    }

    public void addLabTestResult(String labTestResult) {
        labTestResults.add(labTestResult);
    }

    public void addImagingReport(String imagingReport) {
        imagingReports.add(imagingReport);
    }

//    @Override
//    public String toString() {
//        return "Patient{" +
//                ", fullName='" + fullName + '\'' +
//                ", dateOfBirth='" + dateOfBirth + '\'' +
//                // ... other fields ...
//                '}';
//    }

}
