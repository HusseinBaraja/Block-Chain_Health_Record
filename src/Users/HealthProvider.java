package Users;

import java.util.UUID;

class HealthProvider {
    private String providerID;
    private String name;
    private String clinicOrHospital;
    private String contactNumber;
    private boolean accessGranted;


    public HealthProvider(String name, String clinicOrHospital, String contactNumber) {
        this.providerID = UUID.randomUUID().toString();
        this.name = name;
        this.clinicOrHospital = clinicOrHospital;
        this.contactNumber = contactNumber;
        this.accessGranted = false;
    }

    public void requestAccess() {
        System.out.println("Access request sent by HealthProvider: " + this.name);
    }
}