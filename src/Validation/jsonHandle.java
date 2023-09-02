package Validation;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class jsonHandle {
    public jsonHandle(String userType, String[] userInfo) {
        File jsonFile = new File("src/database/user_database.json");

        JSONParser parser = new JSONParser();
        JSONObject root;

        // Step 1: Read existing content
        try (FileReader fileReader = new FileReader(jsonFile)) {
            root = (JSONObject) parser.parse(fileReader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return;
        }

        // Step 2: Modify in-memory data
        JSONArray newUserArray;
        if (root.containsKey(userType)) {
            newUserArray = (JSONArray) root.get(userType);
        } else {
            newUserArray = new JSONArray();
            root.put("HealthProvider", newUserArray);
        }

        String[] userTypes = {"Admin", "HealthProvider", "Doctor", "Receptionist", "Patient"};
        JSONObject newUser = new JSONObject();
        if (userType.equals(userTypes[1])){
            newUser.put("Name", userInfo[0]);
            newUser.put("ClinicOrHospital", userInfo[1]);
            newUser.put("ContactNumber", userInfo[2]);
            newUser.put("Username", userInfo[3]);
            newUser.put("Password", userInfo[4]);
            newUser.put("AccessGranted", false);
            newUserArray.add(newUser);
        } else {
            newUser.put("Name", userInfo[0]);
            newUser.put("Username", userInfo[1]);
            newUser.put("Password", userInfo[2]);
            newUserArray.add(newUser);
        }


        // Step 3: Write back to file preserving order and with indentation
        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            fileWriter.write("{\n");
            writeOrderedKeys(fileWriter, root, "Admin", "    ");
            writeOrderedKeys(fileWriter, root, "HealthProvider", "    ");
            writeOrderedKeys(fileWriter, root, "Doctor", "    ");
            writeOrderedKeys(fileWriter, root, "Receptionist", "    ");
            writeOrderedKeys(fileWriter, root, "Patient", "    ", false);
            fileWriter.write("}");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeOrderedKeys(FileWriter writer, JSONObject root, String key, String indent) throws IOException {
        writeOrderedKeys(writer, root, key, indent, true);
    }

    private static void writeOrderedKeys(FileWriter writer, JSONObject root, String key, String indent, boolean withComma) throws IOException {
        if (root.containsKey(key)) {
            writer.write(indent + "\"" + key + "\": [\n");
            JSONArray array = (JSONArray) root.get(key);
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = (JSONObject) array.get(i);

                if ("HealthProvider".equals(key)) {
                    writeHealthProviderOrdered(writer, obj, indent);
                } else {
                    writeUsersOrdered(writer, obj, indent);
//                    writer.write(indent + indent);
//                    writer.write(obj.toString().replace(",", ",\n" + indent + indent).replace("{", "{\n" + indent + indent).replace("}", "\n" + indent + indent + "}"));
                }

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
        }
    }
    private static void writeHealthProviderOrdered(FileWriter writer, JSONObject obj, String indent) throws IOException {
        writer.write(indent + indent + "{\n");
        writeKeyValue(writer, obj, "Name", indent + indent + indent, true);
        writeKeyValue(writer, obj, "ClinicOrHospital", indent + indent + indent, true);
        writeKeyValue(writer, obj, "ContactNumber", indent + indent + indent, true);
        writeKeyValue(writer, obj, "Username", indent + indent + indent, true);
        writeKeyValue(writer, obj, "Password", indent + indent + indent, true);
        writeKeyValue(writer, obj, "AccessGranted", indent + indent + indent, false);
        writer.write("\n" + indent + indent + "}");
    }
    private static void writeUsersOrdered(FileWriter writer, JSONObject obj, String indent) throws IOException {
        writer.write(indent + indent + "{\n");
        writeKeyValue(writer, obj, "Name", indent + indent + indent, true);
        writeKeyValue(writer, obj, "Username", indent + indent + indent, true);
        writeKeyValue(writer, obj, "Password", indent + indent + indent, false);
        writer.write("\n" + indent + indent + "}");
    }

    private static void writeKeyValue(FileWriter writer, JSONObject obj, String key, String indent, boolean withComma) throws IOException {
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
}
