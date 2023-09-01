package Users;

import java.util.ArrayList;
import java.util.List;

public class Receptionist extends Users {

    private String username, password;

    public Receptionist(String fullName, String DOB, String gender, int age, int phoneNumber, String username, String password) {
        super(fullName, DOB, gender, age, phoneNumber);
        this.username = username;
        this.password = password;
    }

//    Receptionist(String username, String password) {
//        this.patients = new ArrayList<>();
//        this.doctors = new ArrayList<>();
//    }

    void addNewPatient(String patientID, String fullName, String dateOfBirth, String address, String phoneNumber) {

    }

    void assignPatientToDoctor(String patientID, String doctorName) {
//        Doctor doctor = findDoctorByName(doctorName);
//        if (doctor == null) {
//            doctor = new Doctor(doctorName);
//            doctors.add(doctor);
//        }

//        Patient patient = findPatientByID(patientID);
//        if (patient == null) {
//            System.out.println("No patient found with ID: " + patientID);
//            return;
//        }
//        patient.assignedDoctor = doctor;
//        System.out.println("Assigned patient " + patient.fullName + " to doctor " + doctorName);
    }

//    private Doctor findDoctorByName(String doctorName) {
//        for (Doctor doctor : doctors) {
////            if (doctor.doctorName.equals(doctorName)) {
////                return doctor;
////            }
//        }
//        return null;
//    }
//
//    private Patient findPatientByID(String patientID) {
//        for (Patient patient : patients) {
////            if (patient.patientID.equals(patientID)) {
////                return patient;
////            }
//        }
//        return null;
//    }
}
