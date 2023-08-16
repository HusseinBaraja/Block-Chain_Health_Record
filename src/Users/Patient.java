package Users;

import Users.Users;

public class Patient extends Users {
    String patientID;

    public Patient(String fullName, String DOB, String gender, int age, int phoneNumber, String patientID) {
        super(fullName, DOB, gender, age, phoneNumber);
        this.patientID = patientID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }
}
