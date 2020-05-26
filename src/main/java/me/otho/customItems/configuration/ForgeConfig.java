package me.otho.customItems.configuration;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import me.otho.customItems.CustomItems;

public class ForgeConfig {
	public static boolean debug = true;
	public static boolean entityIdLog = false;

	public static ForgeConfigSpec.BooleanValue defaultTab;
	public static ForgeConfigSpec.BooleanValue logFile;
	public static ForgeConfigSpec.IntValue generateData;

	public static ForgeConfig instace;
	private static ForgeConfigSpec configSpec;

	public static void construct() {
		Pair<ForgeConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ForgeConfig::new);
		configSpec = specPair.getRight();
		instace = specPair.getLeft();

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfig.configSpec);
	}

	public static ForgeConfigSpec.BooleanValue buildBoolean(ForgeConfigSpec.Builder builder, String modID, String key,
			boolean defaultVal, String comment) {
		return builder.comment(comment + "\r\nDefault: " + defaultVal)
				.translation(modID + ".config." + key.toLowerCase().replace(' ', '_')).define(key, defaultVal);
	}

    public static ForgeConfigSpec.IntValue buildInt(ForgeConfigSpec.Builder builder, String modID, String key, 
    		int defaultVal, int min, int max, String comment) {
        return builder
                .comment(comment + "\r\nDefault: "+ defaultVal)
                .translation(modID + ".config." + key.toLowerCase().replace(' ', '_'))
                .defineInRange(key, defaultVal, min, max);
    }

	private ForgeConfig(ForgeConfigSpec.Builder builder) {
		String domain = CustomItems.MOD_ID;
		builder.push("Common Settings");
		defaultTab = buildBoolean(builder, domain, "defaultTab", true,
				"Set to false, if you dont want the default creative tab");
		logFile = buildBoolean(builder, domain, "logFile", false,
				"Enable this if you want the mod to make a separate log file for easier debugging");
		generateData = buildInt(builder, domain, "generateData", 0, 0, 3,
				"Set this flag to trigger an assest and/or data generation (Or-ble): 1 - Assests, 2 - Data");
		builder.pop();
	}
}
