package Validation;

import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static Application.Electronic_Health_Record_Application.addDataToBlockchain;

public class orderSignificance {

    public JSONObject readJsonFile(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            return (JSONObject) new JSONParser().parse(reader);
        } catch (Exception e) {
            return new JSONObject(); // Returns an empty JSONObject if there's an error reading the file or if the file doesn't exist yet
        }
    }

    private String formatJson(String jsonString) {
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

    public void sortData(JSONObject inputObject) throws IOException{
        JSONObject significanceObject = readJsonFile("src/database/data_significance.json");
        JSONObject significantDataObject = new JSONObject();
        JSONObject insignificantDataObject = readJsonFile("src/database/insignificant_data.json");

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
                        if ("significant".equals(((JSONObject) significanceObject.get(category)).get(itemKey))) {
                            significantCategoryData.put(itemKey, categoryData.get(itemKey));
                        } else {
                            insignificantCategoryData.put(itemKey, categoryData.get(itemKey));
                        }
                    }

                    if (!significantCategoryData.isEmpty()) {
                        significantPatientData.put(category, significantCategoryData);
                    }
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
                    ((JSONArray) insignificantDataObject.get(patientId)).add(insignificantPatientData);
                } else {
                    JSONArray newArray = new JSONArray();
                    newArray.add(insignificantPatientData);
                    insignificantDataObject.put(patientId, newArray);
                }
            }
        }

        addDataToBlockchain(significantDataObject);

        try (FileWriter insignificantFile = new FileWriter("src/database/insignificant_data.json")) {
            insignificantFile.write(formatJson(insignificantDataObject.toJSONString()));
        }
    }

}
