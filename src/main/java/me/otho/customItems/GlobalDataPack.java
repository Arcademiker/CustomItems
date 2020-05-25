package me.otho.customItems;

import java.nio.file.Path;

import net.minecraftforge.common.MinecraftForge;

// https://github.com/DarkRoleplay/DRP---Global-Data-Pack/blob/master/src/main/java/net/dark_roleplay/drpglobaldatapack/DarkRoleplayGlobalDatapack.java
public class GlobalDataPack {
	public static void load() {
		MinecraftForge.EVENT_BUS.register(new ServerLaunchListener());
//		Path full = ResourcePaths.respack_generated;
//		if (!full.toFile().exists()) {
//			full.toFile().mkdirs();
//		}
		
//		ResourcePaths.pack_mcmeta(full, "CustomItems Generated Data");
	}
}
