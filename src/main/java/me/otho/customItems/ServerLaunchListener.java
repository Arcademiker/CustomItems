package me.otho.customItems;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.IFactory;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;

public class ServerLaunchListener {
	
	@SubscribeEvent
	public void serverStarting(FMLServerAboutToStartEvent event) {
		File globalDatapack = ResourcePaths.respack_generated.toFile();
		event.getServer().getResourcePacks().addPackFinder(new GlobalDataPackFinder(globalDatapack));
	}
	
	private static class GlobalDataPackFinder implements IPackFinder {

		private File folder;
		
		public GlobalDataPackFinder(File file) {
			this.folder = file;
		}
		
		@Override
		public <T extends ResourcePackInfo> void addPackInfosToMap(Map<String, T> nameToPackMap, IFactory<T> packInfoFactory) {
			String name = CustomItems.MOD_ID + "_generated";
			T t = ResourcePackInfo.createResourcePack(
					name, 
					false, 
					() -> new FolderPack(this.folder), 
					packInfoFactory, 
					ResourcePackInfo.Priority.TOP);
			
	        if (t != null) {
	           nameToPackMap.put(name, t);
	        }
		}
	}
}
