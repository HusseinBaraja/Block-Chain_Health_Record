package Blockchain;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import com.google.gson.*;


public class Blockchain {
	private static LinkedList<Block> db = new LinkedList<>();
	private static Blockchain _instance = null;
	public String chainFile;

	public Blockchain(String chainFile) {
		this.chainFile = chainFile;
	}

	public static Blockchain getInstance(String chainFile) {
		if (_instance == null) {
			_instance = new Blockchain(chainFile);
		}
		return _instance;
	}

	public void genesis() {
		Block genesis = new Block(0, "0"); // Set index to 0 for the genesis block
		db.add(genesis);
		persist();
	}

	public void nextBlock(Block newBlock) {
		db = get();
		db.add(newBlock);
		persist();
	}

	public LinkedList<Block> get() {
		try (FileInputStream fin = new FileInputStream(this.chainFile);
			 ObjectInputStream in = new ObjectInputStream(fin)) {
			return (LinkedList<Block>) in.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void persist() {
		try (FileOutputStream fout = new FileOutputStream(this.chainFile);
			 ObjectOutputStream out = new ObjectOutputStream(fout)) {
			out.writeObject(db);
			System.out.println("> Master file is updated!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String chain;
	public void distribute() {
		chain = new GsonBuilder().setPrettyPrinting().create().toJson(db);

	}

	public void writeChain() {
		db = get();
		distribute();
		JsonParser parser = new JsonParser();
		JsonElement jsonElement = parser.parse(chain);
		if (jsonElement.isJsonArray()) {
			JsonArray fullJson = jsonElement.getAsJsonArray();

			for (JsonElement element : fullJson) {
				if (element.isJsonObject()) {
						JsonObject obj = element.getAsJsonObject();
					if (obj.has("tranxLst")) {
						JsonObject tranxObject = obj.getAsJsonObject("tranxLst");
						JsonArray tranxArray = tranxObject.getAsJsonArray("tranxLst");
						JsonObject patientObject = tranxArray.get(0).getAsJsonObject();

						String key1 = patientObject.entrySet().iterator().next().getKey();
						JsonArray patientArray = patientObject.getAsJsonArray(key1);

						JsonObject actualPatientObject = patientArray.get(0).getAsJsonObject();

						String key2 = actualPatientObject.entrySet().iterator().next().getKey();
						JsonObject categoryObject = actualPatientObject.getAsJsonObject(key2);


						writeToFile(key1, key2, categoryObject);

					}
				}
			}
		}
	}

	private void writeToFile(String key1, String key2, JsonObject categoryObject){
		String FILE_PATH = "src/database/ToBeViewed.json";
		try {
			JsonObject root;
			if (Files.exists(Paths.get(FILE_PATH))) {
				root = new JsonParser().parse(new FileReader(FILE_PATH)).getAsJsonObject();
			} else {
				root = new JsonObject();
			}

			JsonArray patientArray;

			// Check if key1 already exists in the JSON file
			if (root.has(key1)) {
				patientArray = root.getAsJsonArray(key1);
			} else {
				patientArray = initializeNewPatientArray();
				root.add(key1, patientArray);
			}

			// Locate the appropriate category using key2 and update it
			for (JsonElement element : patientArray) {
				JsonObject obj = element.getAsJsonObject();
				if (obj.has(key2)) {
					obj.getAsJsonArray(key2).add(categoryObject);
					break;
				}
			}

			// Write updated data back to the file
			try (FileWriter file = new FileWriter(FILE_PATH)) {
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				file.write(gson.toJson(root));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private JsonArray initializeNewPatientArray() {
		JsonObject newPatient = new JsonObject();
		newPatient.add("PatientIdentifiers", new JsonArray());
		newPatient.add("DemographicInformation", new JsonArray());
		newPatient.add("Diagnosis", new JsonArray());
		newPatient.add("Allergies", new JsonArray());
		newPatient.add("Immunizations", new JsonArray());
		newPatient.add("Medications", new JsonArray());
		newPatient.add("Procedures", new JsonArray());
		newPatient.add("LaboratoryTestResults", new JsonArray());
		newPatient.add("VitalSigns", new JsonArray());
		newPatient.add("ImagingReports", new JsonArray());

		JsonArray newArray = new JsonArray();
		newArray.add(newPatient);
		return newArray;
	}
}
