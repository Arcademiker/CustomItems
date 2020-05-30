package me.otho.customItems.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import me.otho.customItems.common.LogHelper;

public class JsonConfigurationHandler {
	private static File configDir;
	public static JsonSchema allData;

	public static void init(String folderPath) throws IOException {
		configDir = new File(folderPath);
		if (configDir.exists()) {
			allData = new JsonSchema();
			parseJsonDir(configDir, allData);
		} else {
			configDir.mkdir();
		}
	}
	
	public static void updateExisting() throws IOException {
		if (configDir.exists()) {
			JsonSchema updated = new JsonSchema();
			parseJsonDir(configDir, updated);
			allData.updateExisting(updated);
		} else {
			configDir.mkdir();
		}
	}
	
	public static void parseJsonDir(File folder, JsonSchema merged) throws IOException {
		File[] listOfFiles = folder.listFiles();

		if (listOfFiles.length > 0) {
			LogHelper.info("Reading config files: ", 0);
			Gson gson = new Gson();

			for (int i = 0; i < listOfFiles.length; i++) {
				File file = listOfFiles[i];

				if (file.isFile() && file.getName().endsWith(".json")) {
					try {
						JsonReader reader = new JsonReader(new FileReader(file));
						// reader.setLenient(true);

						LogHelper.info("Reading json file:" + file.getName(), 1);

						JsonSchema data = gson.fromJson(reader, JsonSchema.class);
						data.mergeTo(merged);
					} catch (FileNotFoundException e) {
						LogHelper.error(e.toString());
					}
				}
			}
			LogHelper.finishSection();
		}
	}	
}