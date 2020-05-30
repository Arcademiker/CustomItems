package me.otho.customItems.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import me.otho.customItems.common.LogHelper;

public class JsonConfigurationHandler {
	public static JsonSchema allData;

	public static void init(String folderPath) throws IOException {
		File folder = new File(folderPath);
		allData = new JsonSchema();

		if (folder.exists()) {
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
							data.mergeTo(allData);
						} catch (FileNotFoundException e) {
							LogHelper.error(e.toString());
						}
					}
				}
				LogHelper.finishSection();
			}
		} else {
			folder.mkdir();
		}
	}
}