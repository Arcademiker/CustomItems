package me.otho.customItems;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import me.otho.customItems.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class LocalizationGenerator implements IDataProvider {
	private static Map<String, String> en_us = new HashMap<>();
	
	private static String getPrefix(Class<?> type) {
		if (Block.class.isAssignableFrom(type)) {
			return "block.";
		} else if (Item.class.isAssignableFrom(type)) {
			return "item.";
		} else if (ItemGroup.class.isAssignableFrom(type)) {
			return "itemGroup.";
		}
		return "";
	}
	
	public static void put(IForgeRegistryEntry<?> key, String value) {
		String prefix = getPrefix(key.getRegistryType());
		en_us.put(prefix+key.getRegistryName().getNamespace()+"."+key.getRegistryName().getPath(), value);
	}
	
	public static void put(Object obj, String entry, String value) {
		String prefix = getPrefix(obj.getClass());
		en_us.put(prefix+entry, value);
	}

	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
	private final DataGenerator generator;
	public LocalizationGenerator(DataGenerator generator, Object dummy) {
		this.generator = generator;
	}
	
	@Override
	public void act(DirectoryCache cache) throws IOException {
		Path path = Paths.get("assets", CustomItems.MOD_ID, "lang", "en_us.json");
		path = this.generator.getOutputFolder().resolve(path);

		BlockRegistry.foreachBlock((block, data) -> put(block, data.getFriendlyName()));
		
		JsonObject en_us_json = new JsonObject();
		en_us.entrySet()
			.stream()
			.sorted((o1,o2)->o1.getKey().compareTo(o2.getKey()))
			.forEach((entry)->en_us_json.addProperty(entry.getKey(), entry.getValue()));
		
		IDataProvider.save(GSON, cache, en_us_json, path);
	}

	@Override
	public String getName() {
		return "CustomItems Localization Json Generator";
	}
}
