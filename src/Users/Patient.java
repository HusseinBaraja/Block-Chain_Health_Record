package Users;

import Users.Users;


public class Patient extends Users {
    private final String patientID;
    private final String race;
    private final String bloodType;

    public Patient(String fullName, String DOB, String gender, int age, int phoneNumber,
                   String patientID, String race, String bloodType) {
        super(fullName, DOB, gender, age, phoneNumber);
        this.patientID = patientID;
        this.race = race;
        this.bloodType = bloodType;
    }
}

