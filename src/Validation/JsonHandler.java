package Validation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class JsonHandler {
    private static final String USER_FILE = "src/database/user_database.json";
    private JSONObject userData;
    public JsonHandler() {
        checkUserDataFile();
        loadUserData();
    }
    private void checkUserDataFile() {
        File file = new File(USER_FILE);
        if (!file.exists() || file.length() == 0) {
            try {
                file.createNewFile();
                userData = new JSONObject();

                JSONArray adminArray = new JSONArray();
                JSONArray healthProviderArray = new JSONArray();
                JSONArray doctorArray = new JSONArray();
                JSONArray patientArray = new JSONArray();
                userData.put("Admin", adminArray);
                userData.put("HealthProvider", healthProviderArray);
                userData.put("Doctor", doctorArray);
                userData.put("Patient", patientArray);

                saveUserData();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void loadUserData() {
        try {
            JSONParser parser = new JSONParser();
            userData = (JSONObject) parser.parse(new FileReader(USER_FILE));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void saveUserData() {
        try (FileWriter file = new FileWriter(USER_FILE)) {
            file.write(userData.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getItem(String itemToGet, String userType, String username) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(Paths.get(USER_FILE).toFile()));

            JSONArray healthProviders = (JSONArray) jsonObject.get(userType);

            for (Object o : healthProviders) {
                JSONObject provider = (JSONObject) o;
                String user = (String) provider.get("Username");
                if(user.equals(username)) { // Check for the provided username
                    return (String) provider.get(itemToGet);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public JSONObject getUserInfoByUserName(String userType, String username) {
        if (userData.containsKey(userType)) {
            JSONArray userArray = (JSONArray) userData.get(userType);
            for (Object o : userArray) {
                JSONObject user = (JSONObject) o;
                if (user.get("Username").equals(username)) {
                    return user;
                }
            }
        }
        return null; // Return null if user is not found.
    }
    private String patientKey;
    private static final String PATIENT_FILE = "src/database/insignificant_data.json";
    public JSONObject getPatientInfoByUsername(String username) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject patInfo = (JSONObject) parser.parse(new FileReader(Paths.get(PATIENT_FILE).toFile()));

            for (Object key : patInfo.keySet()) {
                if (!(patInfo.get(key) instanceof JSONArray)) {
                    continue;
                }
                JSONArray patientArray = (JSONArray) patInfo.get(key);
                for (Object obj : patientArray) {
                    JSONObject patient = (JSONObject) obj;
                    JSONObject identifiers = (JSONObject) patient.get("PatientIdentifiers");
                    if (username.equalsIgnoreCase((String) identifiers.get("Username"))) {
                        patientKey = (String) key;
                        return patient; // return the matching patient's information
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPatientKey() {
        return patientKey;
    }

    public JSONObject getRootInfo(File jsonFile){
        JSONParser parser = new JSONParser();
        JSONObject root;

        // Step 1: Read existing content
        try (FileReader fileReader = new FileReader(jsonFile)) {
            root = (JSONObject) parser.parse(fileReader);
            return root;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void grantAccessToHealthProvider() {
        String username = InputValidator.valString(
                "Enter the username of the health provider to grant access: ", "username");
        if (modifyAccessToHealthProvider(username, true)) {
            System.out.println("Access granted to the health provider.");
        } else {
            System.out.println("Health provider not found.");
        }
    }

    public void revokeAccessFromHealthProvider() {
        String username = InputValidator.valString(
                "Enter the username of the health provider to revoke access: ", "username");
        if (modifyAccessToHealthProvider(username, false)) {
            System.out.println("Access revoked from the health provider.");
        } else {
            System.out.println("Health provider not found.");
        }
    }

    private boolean modifyAccessToHealthProvider(String username, boolean grantAccess) {
        JSONArray healthProviderList = (JSONArray) userData.get("HealthProvider");
        for (Object obj : healthProviderList) {
            JSONObject providerData = (JSONObject) obj;
            String storedUsername = (String) providerData.get("Username");

            if (storedUsername.equals(username)) {
                providerData.put("AccessGranted", grantAccess);
                saveUserData(); // Save the updated data.
                return true;
            }
        }
        return false;
    }

    private static final String[] USER_TYPES = {"Admin", "HealthProvider", "Doctor", "Patient"};
    private final List<String[]> USER_FIELDS = Arrays.asList(
            new String[]{"Name", "Username", "Password"},
            new String[]{"Name", "ClinicOrHospital", "ContactNumber", "Username", "Password", "AccessGranted"},
            new String[]{"Name", "Username", "Password", "PhoneNumber", "DOB", "Age", "Gender", "PlaceOfWork"},
            new String[]{"PatientId", "Username", "Password"}
    );

    private int counter;
    public void addNewUser(String userType, String[] userAttributes, Object[] userInfo){
        File jsonFile = new File(USER_FILE);

        JSONObject root = userData;
        if (root == null){
            System.out.println("The JSON file is empty!");
            return;
        }

        // Step 2: Get level 1
        JSONArray newUserArray;
        if (root.containsKey(userType)) {
            newUserArray = (JSONArray) root.get(userType);
        } else {
            newUserArray = new JSONArray();
            root.put(userType, newUserArray);
        }

        JSONObject newUser = new JSONObject();



        for (int i = 0; i < userInfo.length; i++) {
            newUser.put(userAttributes[i], userInfo[i]);
        }
        newUserArray.add(newUser);

        counter = 0;
        // Step 3: Write back to file preserving order and with indentation
        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            fileWriter.write("{\n");
            for(int i = 0; i < USER_TYPES.length; i++){
                if(!(i == USER_TYPES.length - 1)){
                    writeOrderedKeys(fileWriter, root, USER_TYPES[i], "    ", true);
                } else {
                    writeOrderedKeys(fileWriter, root, USER_TYPES[i], "    ", false);
                }

            }
            fileWriter.write("}");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeOrderedKeys(FileWriter writer, JSONObject root, String userKey, String indent, boolean withComma) throws IOException {
        if (root.containsKey(userKey)) {
            writer.write(indent + "\"" + userKey + "\": [\n");
            JSONArray array = (JSONArray) root.get(userKey);
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = (JSONObject) array.get(i);

                String[] userAttributes = USER_FIELDS.get(counter);
                writeUsersOrdered(writer, obj, indent, userAttributes);


                if (i < array.size() - 1) {
                    writer.write(",\n");
                } else {
                    writer.write("\n");
                }
            }
            writer.write(indent + "]");
            if (withComma) {
                writer.write(",\n");
            }
            counter++;
        }
    }
    private void writeUsersOrdered(FileWriter writer, JSONObject obj, String indent, String[] userAttributes) throws IOException {
        writer.write(indent + indent + "{\n");
        for (int i = 0; i < userAttributes.length; i++){
            if(!(i == userAttributes.length - 1)){
                writeKeyValue(writer, obj, userAttributes[i], indent + indent + indent, true);
            } else {
                writeKeyValue(writer, obj, userAttributes[i], indent + indent + indent, false);
            }


        }
        writer.write("\n" + indent + indent + "}");
    }

    private void writeKeyValue(FileWriter writer, JSONObject obj, String key, String indent, boolean withComma) throws IOException {
        writer.write(indent + "\"" + key + "\": ");
        if (obj.get(key) instanceof String) {
            writer.write("\"" + obj.get(key) + "\"");
        } else {
            writer.write(obj.get(key).toString());
        }
        if (withComma) {
            writer.write(",\n");
        }
    }

    public int getAccessStatus(String username, String userType) {
        JSONParser parser = new JSONParser();

        try {
            // Parse the JSON string into a JSONObject
            JSONObject rootObject = (JSONObject) parser.parse(new FileReader(Paths.get(USER_FILE).toFile()));

            // Get the JSONArray for the 'Doctor' key
            JSONArray userArray = (JSONArray) rootObject.get(userType);

            // Variable to store the place of work for the given username
            String placeOfWork = null;


            // Iterate through the doctors array to get the place of work for the given username
            for (Object userObject : userArray) {
                JSONObject user = (JSONObject) userObject;
                if (username.equals(user.get("Username"))) {
                    switch (userType) {
                        case "Doctor" -> placeOfWork = (String) user.get("PlaceOfWork");
                        case "HealthProvider" -> {
                            String access = Boolean.toString((Boolean) user.get("AccessGranted"));
                            switch (access) {
                                case "true" -> {
                                    return 1;
                                }
                                case "false" -> {
                                    return 0;
                                }
                            }
                        }
                    }
                    break;
                }
            }

            // If we couldn't find the doctor or the place of work, return false
            if (placeOfWork == null) {
                return 2;
            }

            // Get the JSONArray for the 'HealthProvider' key
            JSONArray healthProvidersArray = (JSONArray) rootObject.get("HealthProvider");

            // Iterate through the health providers array to match the place of work and get the 'AccessGranted' status
            for (Object healthProviderObject : healthProvidersArray) {
                JSONObject healthProvider = (JSONObject) healthProviderObject;
                if (placeOfWork.equals(healthProvider.get("Name"))) {
                    String access = Boolean.toString ((Boolean) healthProvider.get("AccessGranted"));
                    switch (access){
                        case "true":
                            return 1;
                        case "false":
                            return 0;
                    }
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // If we reach here, it means we couldn't find the matching health provider, so return false
        return 2;
    }

    public void printItems(String itemType) {
        JSONArray itemArray = (JSONArray) userData.get(itemType);
        if (itemArray == null || itemArray.isEmpty()) {
            System.out.println("No items found in " + itemType);
        } else {
            System.out.println("Items in " + itemType + ":");
            for (Object item : itemArray) {
                System.out.println(item.toString());
            }
        }
    }

    public boolean removeItem(String itemType, String key, String value) {
        JSONArray itemArray = (JSONArray) userData.get(itemType);
        if (itemArray == null || itemArray.isEmpty()) {
            return false;
        }

        for (int i = 0; i < itemArray.size(); i++) {
            JSONObject item = (JSONObject) itemArray.get(i);
            if (value.equals(item.get(key))) {
                itemArray.remove(i);
                saveUserData();
                return true;
            }
        }

        return false; // Item not found
    }
    public void updatePatientData(String username, JSONObject updatedData) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject patInfo = (JSONObject) parser.parse(new FileReader(Paths.get(PATIENT_FILE).toFile()));

            for (Object key : patInfo.keySet()) {
                if (!(patInfo.get(key) instanceof JSONArray)) {
                    continue;
                }
                JSONArray patientArray = (JSONArray) patInfo.get(key);
                for (Object obj : patientArray) {
                    JSONObject patient = (JSONObject) obj;
                    JSONObject identifiers = (JSONObject) patient.get("PatientIdentifiers");
                    if (username.equalsIgnoreCase((String) identifiers.get("Username"))) {
                        // Update the patient's data
                        patient.putAll(updatedData);
                        // Save the updated JSON data back to the file
                        try (FileWriter file = new FileWriter(PATIENT_FILE)) {
                            file.write(patInfo.toJSONString());
                            file.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPatientDataKeys(String patientId) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader("src/database/insignificant_data.json")) {
            // Load and parse the JSON file
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            // Check if the patient ID exists
            if (jsonObject.containsKey(patientId)) {
                JSONArray patientArray = (JSONArray) jsonObject.get(patientId);
                if (patientArray.size() == 0) {
                    return "The patient exists but has no data.";
                }

                JSONObject patientData = (JSONObject) patientArray.get(0);

                // Retrieve the keys and create a comma-separated string
                Set<String> keySet = patientData.keySet();
                return String.join(",", keySet);

            } else {
                return "Error: Patient not found.";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: Failed to read the JSON file.";
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error: Failed to parse the JSON content.";
        }
    }

}
