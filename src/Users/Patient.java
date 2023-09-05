package Users;

import Users.Users;

import java.util.ArrayList;
import java.util.List;

public class Patient extends Users {
    private final String patientID;
    private final String race;
    private final String bloodType;
    private final List<String> diagnoses;
    private final List<String> allergies;
    private final List<String> immunizations;
    private final List<String> medications;
    private final List<String> procedures;
    private final List<String> labTestResults;
    private final List<String> vitalSigns;
    private final List<String> imagingReports;

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
