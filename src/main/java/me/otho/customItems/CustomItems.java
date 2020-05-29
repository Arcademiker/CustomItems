package me.otho.customItems;

import java.io.File;
import java.io.IOException;

import me.otho.customItems.configuration.ForgeConfig;
import me.otho.customItems.configuration.JsonConfigurationHandler;
import me.otho.customItems.mod.creativeTab.CustomTab;
import me.otho.customItems.registry.BlockRegistry;
import me.otho.customItems.registry.CommonRegistry;
import me.otho.customItems.registry.ItemRegistry;
import me.otho.customItems.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(CustomItems.MOD_ID)
public class CustomItems {
	public static final String MOD_ID = "customitems";

	public static final String LOG_FILE_NAME = "MM-CI_log.log";

	private static File modConfigDirectory;
	private static File minecraftFolder;

	public static CustomItems instance;

//	public static CommonProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new CommonProxy());

	public CustomItems() {
		if (instance == null)
			instance = this;
		else
			throw new RuntimeException("Duplicated Class Instantiation: CustomItems");
		
		ForgeConfig.construct();
	}

	@Mod.EventBusSubscriber(modid = CustomItems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistrationHandler {
    	@SubscribeEvent
    	public static void newRegistry(RegistryEvent.NewRegistry event) throws IOException {
			modConfigDirectory = FMLPaths.CONFIGDIR.get().toFile();
			minecraftFolder = modConfigDirectory.getParentFile();

			String configFolderPath = modConfigDirectory.toString() + File.separator + CustomItems.MOD_ID + File.separator;

			if (ForgeConfig.logFile.get()) {
				LogHelper.openLog(minecraftFolder);
			}
    		
			// Parse Json data files
			JsonConfigurationHandler.init(configFolderPath);
			
			// Init and register all creative tabs
			CustomTab.createDefaultTab();
			CommonRegistry.registerCreativeTabs(JsonConfigurationHandler.allData.creativeTabs);
    	}
		
    	@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
    		BlockRegistry.initBlocks();
    		BlockRegistry.registerBlocks(event.getRegistry());
    	}
    	
    	@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
    		BlockRegistry.registerBlockItems(event.getRegistry());
    		ItemRegistry.registerItems(event.getRegistry());
    	}
    	
		@SubscribeEvent
		public static void onCommonSetup(FMLCommonSetupEvent event) throws IOException {
			// Post init
			BlockRegistry.postRegistraion();
			ItemRegistry.postRegistraion();

			LogHelper.info("End of customization");
			
			int generateData = ForgeConfig.generateData.get();
			if (generateData > 0) {
				ClientRegistrationHandler.runClientDataGenerators = (generateData|1)>0;
				
				if ((generateData|2)>0)
					CustomDataGenerator.runCommon();
				
				ForgeConfig.generateData.set(0);
				ForgeConfig.generateData.save();
			}
		}
		
		@SubscribeEvent
		public static void gatherData(GatherDataEvent event) {
			DataGenerator dataGenerator = event.getGenerator();
			ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
			CustomDataGenerator.gatherCommonData(dataGenerator, existingFileHelper);
		}
	}
}