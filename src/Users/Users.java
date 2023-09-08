package Users;


public class Users {

    private String fullName, DOB, gender;
    private int age, phoneNumber;
    private String username;
    private String password;
    private UserRole role;

    public Users(String username) {
        this.username = username;
    }

    public enum UserRole { ADMIN, RECEPTIONIST, DOCTOR, PATIENT, HEALTH_PROVIDER, NONE }

    public Users(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public Users(String fullName, String DOB, String gender, int age, int phoneNumber) {
        this.fullName = fullName;
        this.DOB = DOB;
        this.gender = gender;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
