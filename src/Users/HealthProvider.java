package Users;

import java.util.UUID;

public class HealthProvider {
    private String providerID;
    private String name;
    private String clinicOrHospital;
    private String contactNumber;
    private boolean accessGranted;

    private String username, password;


    public HealthProvider(String name, String clinicOrHospital, String contactNumber, String username, String password) {
        this.providerID = UUID.randomUUID().toString();
        this.name = name;
        this.clinicOrHospital = clinicOrHospital;
        this.contactNumber = contactNumber;
        this.accessGranted = false;
        this.username = username;
        this.password = password;
    }

    public String getProviderID() {
        return providerID;
    }

    public void setProviderID(String providerID) {
        this.providerID = providerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClinicOrHospital() {
        return clinicOrHospital;
    }

    public void setClinicOrHospital(String clinicOrHospital) {
        this.clinicOrHospital = clinicOrHospital;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isAccessGranted() {
        return accessGranted;
    }

    public void setAccessGranted(boolean accessGranted) {
        this.accessGranted = accessGranted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}