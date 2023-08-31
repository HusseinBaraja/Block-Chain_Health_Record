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


//    @Override
//    public String toString() {
//        return "Patient{" +
//                ", fullName='" + fullName + '\'' +
//                ", dateOfBirth='" + dateOfBirth + '\'' +
//                // ... other fields ...
//                '}';
//    }

}
