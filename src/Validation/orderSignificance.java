package Validation;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static Application.Electronic_Health_Record_Application.addDataToBlockchain;

public class orderSignificance {

    public JSONObject readJsonFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return (JSONObject) new JSONParser().parse(reader);
        } catch (Exception e) {
            return new JSONObject(); // Returns an empty JSONObject if there's an error reading the file or if the file doesn't exist yet
        }
    }

    public String formatJson(String jsonString) {
        String indentString = "";
        StringBuilder prettyJSONBuilder = new StringBuilder();
        char[] chars = jsonString.toCharArray();
        for (char c : chars) {
            if (c == '{' || c == '[') {
                prettyJSONBuilder.append(c);
                prettyJSONBuilder.append('\n');
                indentString = indentString + "\t";
                prettyJSONBuilder.append(indentString);
            } else if (c == '}' || c == ']') {
                prettyJSONBuilder.append('\n');
                indentString = indentString.replaceFirst("\t", "");
                prettyJSONBuilder.append(indentString);
                prettyJSONBuilder.append(c);
            } else if (c == ',') {
                prettyJSONBuilder.append(c);
                prettyJSONBuilder.append('\n');
                prettyJSONBuilder.append(indentString);
            } else {
                prettyJSONBuilder.append(c);
            }
        }
        return prettyJSONBuilder.toString();
    }

    public void sortData(JSONObject inputObject, String dataTypeToAdd) throws IOException{
        JSONObject significanceObject = readJsonFile("src/database/categorization.json");
        JSONObject significantDataObject = new JSONObject();
        JSONObject insignificantDataObject = readJsonFile("src/database/insignificant_data.json");

        ArrayList<String> forceOnBoth = new ArrayList<>();
        forceOnBoth.add("DiagnoseID");
        forceOnBoth.add("AllergyID");
        forceOnBoth.add("ProcedureID");
        forceOnBoth.add("VitalSignsID");
        forceOnBoth.add("ImagingReportID");

        for (Object patientIdObj : inputObject.keySet()) {
            String patientId = (String) patientIdObj;
            JSONObject patientData = (JSONObject) inputObject.get(patientId);
            JSONObject significantPatientData = new JSONObject();
            JSONObject insignificantPatientData = new JSONObject();

            for (Object categoryObj : patientData.keySet()) {
                String category = (String) categoryObj;
                if (significanceObject.containsKey(category)) {
                    JSONObject categoryData = (JSONObject) patientData.get(category);
                    JSONObject significantCategoryData = new JSONObject();
                    JSONObject insignificantCategoryData = new JSONObject();

                    for (Object itemKeyObj : categoryData.keySet()) {
                        String itemKey = (String) itemKeyObj;
                        if(forceOnBoth.contains(itemKey)){
                            significantCategoryData.put(itemKey, categoryData.get(itemKey));
                            insignificantCategoryData.put(itemKey, categoryData.get(itemKey));
                        } else {
                            if ("significant".equals(((JSONObject) significanceObject.get(category)).get(itemKey))) {
                                significantCategoryData.put(itemKey, categoryData.get(itemKey));
                            } else {
                                insignificantCategoryData.put(itemKey, categoryData.get(itemKey));
                            }
                        }

                    }

                    if (!significantCategoryData.isEmpty()) {
                        significantPatientData.put(category, significantCategoryData);
                    }
                    // here
                    if (!insignificantCategoryData.isEmpty()) {
                        insignificantPatientData.put(category, insignificantCategoryData);
                    }
                }
            }

            if (!significantPatientData.isEmpty()) {
                if (significantDataObject.containsKey(patientId)) {
                    ((JSONArray) significantDataObject.get(patientId)).add(significantPatientData);
                } else {
                    JSONArray newArray = new JSONArray();
                    newArray.add(significantPatientData);
                    significantDataObject.put(patientId, newArray);
                }
            }

            if (!insignificantPatientData.isEmpty()) {
                if (insignificantDataObject.containsKey(patientId)) {
                    JSONArray patientDataArray = (JSONArray) insignificantDataObject.get(patientId);
                    JSONObject currentPatientData = (JSONObject) patientDataArray.get(0); // assuming each patient ID has only one JSONObject in the array

                    if (currentPatientData.containsKey(dataTypeToAdd)) {
                        JSONObject onlyPatData = (JSONObject) insignificantPatientData.get("Diagnosis");
                        ((JSONArray) currentPatientData.get(dataTypeToAdd)).add(onlyPatData);
                    } else {
                        JSONArray newArray = new JSONArray();
                        newArray.add(insignificantPatientData);
                        currentPatientData.put(dataTypeToAdd, newArray);
                    }
                } else {
                    JSONObject newPatientData = new JSONObject();
                    JSONArray newArray = new JSONArray();
                    newArray.add(insignificantPatientData);
                    newPatientData.put(dataTypeToAdd, newArray);

                    JSONArray newPatientDataArray = new JSONArray();
                    newPatientDataArray.add(newPatientData);
                    insignificantDataObject.put(patientId, newPatientDataArray);
                }
            }
        }

        addDataToBlockchain(significantDataObject);

        try (FileWriter insignificantFile = new FileWriter("src/database/insignificant_data.json")) {
            insignificantFile.write(formatJson(insignificantDataObject.toJSONString()));
        }
    }

    private void addDataToInsignificant(JSONObject insignificantDataObject, String patientId, String dataTypeToAdd, JSONObject dataToAdd) {
        if (insignificantDataObject.containsKey(patientId)) {
            JSONObject currentPatientData = (JSONObject) insignificantDataObject.get(patientId);
            if (currentPatientData.containsKey(dataTypeToAdd)) {
                ((JSONArray) currentPatientData.get(dataTypeToAdd)).add(dataToAdd);
            } else {
                JSONArray newArray = new JSONArray();
                newArray.add(dataToAdd);
                currentPatientData.put(dataTypeToAdd, newArray);
            }
        } else {
            JSONObject newPatientData = new JSONObject();
            JSONArray newArray = new JSONArray();
            newArray.add(dataToAdd);
            newPatientData.put(dataTypeToAdd, newArray);
            insignificantDataObject.put(patientId, newPatientData);
        }
    }



}
