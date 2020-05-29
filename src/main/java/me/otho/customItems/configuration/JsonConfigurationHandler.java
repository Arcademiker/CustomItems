package me.otho.customItems.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import me.otho.customItems.utility.LogHelper;

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
        JsonReader reader;

        int i;

        for (i = 0; i < listOfFiles.length; i++) {
          File file = listOfFiles[i];

          if (file.isFile() && file.getName().endsWith(".json")) {
            try {
              reader = new JsonReader(new FileReader(file));
              // reader.setLenient(true);

              LogHelper.info("Reading json file:" + file.getName(), 1);

              JsonSchema data = gson.fromJson(reader, JsonSchema.class);
              mergeGson(data, allData);
            } catch (FileNotFoundException e) {

            }
          }
        }
        LogHelper.finishSection();
      }
    } else {
      folder.mkdir();
    }
  }

  private static void mergeGson(JsonSchema data, JsonSchema mergeTo) {
    mergeTo.blocks = ArrayUtils.addAll(data.blocks, mergeTo.blocks);
    mergeTo.items = ArrayUtils.addAll(data.items, mergeTo.items);
    mergeTo.foods = ArrayUtils.addAll(data.foods, mergeTo.foods);
    mergeTo.pickaxes = ArrayUtils.addAll(data.pickaxes, mergeTo.pickaxes);
    mergeTo.axes = ArrayUtils.addAll(data.axes, mergeTo.axes);
    mergeTo.hammers = ArrayUtils.addAll(data.hammers, mergeTo.hammers);
    mergeTo.shovels = ArrayUtils.addAll(data.shovels, mergeTo.shovels);
    mergeTo.hoes = ArrayUtils.addAll(data.hoes, mergeTo.hoes);
    mergeTo.swords = ArrayUtils.addAll(data.swords, mergeTo.swords);
    mergeTo.helmets = ArrayUtils.addAll(data.helmets, mergeTo.helmets);
    mergeTo.chestplates = ArrayUtils.addAll(data.chestplates, mergeTo.chestplates);
    mergeTo.leggings = ArrayUtils.addAll(data.leggings, mergeTo.leggings);
    mergeTo.boots = ArrayUtils.addAll(data.boots, mergeTo.boots);
    mergeTo.fluids = ArrayUtils.addAll(data.fluids, mergeTo.fluids);
    mergeTo.creativeTabs = ArrayUtils.addAll(data.creativeTabs, mergeTo.creativeTabs);
    mergeTo.crops = ArrayUtils.addAll(data.crops, mergeTo.crops);

    mergeTo.entitiesDrop = ArrayUtils.addAll(data.entitiesDrop, mergeTo.entitiesDrop);
    mergeTo.blocksDrop = ArrayUtils.addAll(data.blocksDrop, mergeTo.blocksDrop);
  }
}