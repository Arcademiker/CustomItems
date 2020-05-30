package me.otho.customItems;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import me.otho.customItems.client.data.LocalizationGenerator;
import me.otho.customItems.client.data.ModelDataProvider;
import me.otho.customItems.common.LogHelper;
import me.otho.customItems.data.BlockTagsProvider;
import me.otho.customItems.data.ItemTagsProvider;
import me.otho.customItems.data.LootTableDataProvider;
import me.otho.customItems.data.ResourcePaths;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.nio.file.Path;

public class CustomDataGenerator {
	private final static List<BiFunction<DataGenerator, ExistingFileHelper, IDataProvider>> clientDataProviders = new LinkedList<>();
	private final static List<BiFunction<DataGenerator, ExistingFileHelper, IDataProvider>> commonDataProviders = new LinkedList<>();
	
	static {
		clientDataProviders.add(ModelDataProvider::new);
		clientDataProviders.add(LocalizationGenerator::new);
		
		commonDataProviders.add(BlockTagsProvider::new);
		commonDataProviders.add(ItemTagsProvider::new);
		commonDataProviders.add(LootTableDataProvider::new);
	}
	
	public static void gatherClientData(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
		LogHelper.info("Scheduled generation task for client assets");
		clientDataProviders.forEach((dp)->dataGenerator.addProvider(dp.apply(dataGenerator, existingFileHelper)));
	}
	
	public static void gatherCommonData(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
		LogHelper.info("Scheduled generation task for common data");
		commonDataProviders.forEach((dp)->dataGenerator.addProvider(dp.apply(dataGenerator, existingFileHelper)));
	}
	
	public static void runClient() {
		Set<String> mods = new HashSet<>();
		mods.add(CustomItems.MOD_ID);
		Collection<Path> existingPacks = new LinkedList<>();
		existingPacks.add(ResourcePaths.respack_src);
		
		// Output Dir
		Collection<Path> inputs = Collections.emptySet();
		boolean structureValidator = true;
		
		GatherDataEvent.DataGeneratorConfig dataGeneratorConfig = 
				new GatherDataEvent.DataGeneratorConfig(
						mods, 
						ResourcePaths.respack_generated, 
						inputs, 
						true, true, true, true, structureValidator);
		ExistingFileHelper existingFileHelper = new ExistingFileHelper(existingPacks, structureValidator);
		
		DataGenerator dataGenerator = dataGeneratorConfig.makeGenerator(p->p, true);

		gatherClientData(dataGenerator, existingFileHelper);

		dataGeneratorConfig.runAll();
		LogHelper.info("Client Data generation complete");

		ResourcePaths.pack_mcmeta(ResourcePaths.respack_generated, "CustomItems Generated Resources");
	}
	
	public static void runCommon() {
		Set<String> mods = new HashSet<>();
		mods.add(CustomItems.MOD_ID);
		Collection<Path> existingPacks = new LinkedList<>();
		
		// Output Dir
		Collection<Path> inputs = Collections.emptySet();
		boolean structureValidator = true;
		
		GatherDataEvent.DataGeneratorConfig dataGeneratorConfig = 
				new GatherDataEvent.DataGeneratorConfig(
						mods, 
						ResourcePaths.datapack_generated, 
						inputs, 
						true, true, true, true, structureValidator);
		ExistingFileHelper existingFileHelper = new ExistingFileHelper(existingPacks, structureValidator);
		
		DataGenerator dataGenerator = dataGeneratorConfig.makeGenerator(p->p, true);

		gatherCommonData(dataGenerator, existingFileHelper);

		dataGeneratorConfig.runAll();
		LogHelper.info("Common Data generation complete");
	}
}
