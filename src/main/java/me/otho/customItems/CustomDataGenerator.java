package me.otho.customItems;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CustomDataGenerator {
	private final static List<BiFunction<DataGenerator, ExistingFileHelper, IDataProvider>> clientDataProviders = new LinkedList<>();
	private final static List<BiFunction<DataGenerator, ExistingFileHelper, IDataProvider>> commonDataProviders = new LinkedList<>();
	
	static {
		clientDataProviders.add(ModelDataProvider::new);
		
		commonDataProviders.add(BlockTagsProvider::new);
		commonDataProviders.add(ItemTagsProvider::new);
	}
	
	public static void gatherClientData(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
		clientDataProviders.forEach((dp)->dataGenerator.addProvider(dp.apply(dataGenerator, existingFileHelper)));
	}
	
	public static void gatherCommonData(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
		commonDataProviders.forEach((dp)->dataGenerator.addProvider(dp.apply(dataGenerator, existingFileHelper)));
	}
	
	public static void run(boolean includesClient) {
		Set<String> mods = new HashSet<>();
		mods.add("customitems");
		Collection<Path> existingPacks = new LinkedList<>();
		existingPacks.add(Paths.get("D:\\MinecraftDev\\CustomItems\\src\\main\\resources"));
		
		// Output Dir
		Path path = Paths.get("customitems");
		Collection<Path> inputs = Collections.emptySet();
		boolean structureValidator = true;
		
		GatherDataEvent.DataGeneratorConfig dataGeneratorConfig = 
				new GatherDataEvent.DataGeneratorConfig(mods, path, inputs, true, true, true, true, structureValidator);
		ExistingFileHelper existingFileHelper = new ExistingFileHelper(existingPacks, structureValidator);
		
		DataGenerator dataGenerator = dataGeneratorConfig.makeGenerator(
				p->p, 
				true);

		gatherCommonData(dataGenerator, existingFileHelper);
		if (includesClient)
			gatherClientData(dataGenerator, existingFileHelper);
		
		dataGeneratorConfig.runAll();
	}
}
