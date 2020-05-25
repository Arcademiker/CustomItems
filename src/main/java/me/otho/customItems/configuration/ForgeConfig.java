package me.otho.customItems.configuration;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import me.otho.customItems.CustomItems;

@Mod.EventBusSubscriber(modid = CustomItems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeConfig {
	public static boolean debug = true;
	public static boolean defaultTab = true; // Was nothing
	public static boolean entityIdLog = false;
	public static boolean logFile = false;

	public static BooleanValue defaultTab_Spec;
	public static BooleanValue logFile_Spec;

	private static ForgeConfig instace;
	private static ForgeConfigSpec configSpec;

	public static void construct() {
		Pair<ForgeConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ForgeConfig::new);
		configSpec = specPair.getRight();
		instace = specPair.getLeft();

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgeConfig.configSpec);
	}

	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent) {
		if (configEvent.getConfig().getSpec() == ForgeConfig.configSpec) {
			syncConfig();
			return;
		}
	}

	public static BooleanValue buildBoolean(ForgeConfigSpec.Builder builder, String modID, String key,
			boolean defaultVal, String comment) {
		return builder.comment(comment + "\r\nDefault: " + defaultVal)
				.translation(modID + ".config." + key.toLowerCase().replace(' ', '_')).define(key, defaultVal);
	}

	private ForgeConfig(ForgeConfigSpec.Builder builder) {
		defaultTab_Spec = buildBoolean(builder, "defaultTab", "OPTIONS", true,
				"Set to false, if you dont want the default creative tab");
		logFile_Spec = buildBoolean(builder, "logFile", "OPTIONS", false,
				"Enable this if you want the mod to make a separate log file for easier debugging");
	}

	public static void syncConfig() {
		defaultTab = defaultTab_Spec.get();
		logFile = logFile_Spec.get();
	}
}
