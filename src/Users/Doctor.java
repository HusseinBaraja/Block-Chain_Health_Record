package Users;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor extends Users {
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
    public void addDiagnosis(){
        int diagnosis_code = 0; // Will be changed
        // Read current diagnoses from file
        diagnosis_code ++;

        System.out.println("Enter Diagnosis description: ");
        String Description = scanner.nextLine();

        System.out.println("Enter Diagnoses Date: ");
        String DateOfDiagnoses = scanner.nextLine();

        System.out.println("Enter Diagnoses status: ");
        String DiagnosesStatus = scanner.nextLine();

        System.out.println("Enter Diagnoses provider: ");
        String DiagnosesProvider = scanner.nextLine();

        System.out.println("Enter additional notes: ");
        String Notes = scanner.nextLine();

    }

    public void addAllergies(){
        System.out.println("Enter Allergy Name: ");
        String AllergyName = scanner.nextLine();

        System.out.println("Enter Reaction severity: ");
        String ReactionSeverity = scanner.nextLine();

        System.out.println("Enter Reaction description: ");
        String ReactionDescription = scanner.nextLine();

       // and more
    }

    public void addImmunization(){

    }
    public void addMedications(){

    }

    public List<Patient> getPatients() {
        return patients;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getWorkingHospital() {
        return workingHospital;
    }

    public void setWorkingHospital(String workingHospital) {
        this.workingHospital = workingHospital;
    }

    public void addPatient(Patient patient) {
        if (!patients.contains(patient)) {
            patients.add(patient);
        } else {
            System.out.println("Patient already exists in the list.");
        }
    }

    public void removePatient(Patient patient) {
        if (patients.contains(patient)) {
            patients.remove(patient);
        } else {
            System.out.println("Patient does not exist in the list.");
        }
    }

//    @Override
//    public String toString() {
//        return "Doctor{" +
//                ", fullName='" + fullName + '\'' +
//                ", speciality='" + speciality + '\'' +
//                ", qualification='" + qualification + '\'' +
//                ", yearsOfExperience=" + yearsOfExperience +
//                ", workingHospital='" + workingHospital + '\'' +
//                ", patients=" + patients.size() +
//                '}';
//    }
}
