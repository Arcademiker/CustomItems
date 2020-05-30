package me.otho.customItems;

import java.io.File;
import java.util.Map;

import me.otho.customItems.data.CustomTagsProvider;
import me.otho.customItems.data.ResourcePaths;
import net.minecraft.resources.FolderPack;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.IFactory;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

/*
 * Global Datapack System is inspired by:
 * https://github.com/DarkRoleplay/DRP---Global-Data-Pack
 */
@Mod.EventBusSubscriber(modid = CustomItems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GlobalDataPack {
	@SubscribeEvent
	public static void onServerAboutToStart(FMLServerAboutToStartEvent event) {
		ResourcePaths.createEmptyFolders(ResourcePaths.datapacks);
		ResourcePaths.createEmptyFolders(ResourcePaths.datapack_generated);
		ResourcePaths.pack_mcmeta(ResourcePaths.datapack_generated, "CustomItems Global Datapack");
		
		// Scan global datapacks
		for (final File globalDatapack: ResourcePaths.datapacks.toFile().listFiles(File::isDirectory)) {
			event.getServer().getResourcePacks().addPackFinder(new IPackFinder() {
				@Override
				public <T extends ResourcePackInfo> void addPackInfosToMap(
						Map<String, T> nameToPackMap, IFactory<T> packInfoFactory) {
					String name = ResourcePaths.generated;
					T t = ResourcePackInfo.createResourcePack(
							name, 
							false, 
							() -> new FolderPack(globalDatapack), 
							packInfoFactory, 
							ResourcePackInfo.Priority.TOP);
					
			        if (t != null) {
			           nameToPackMap.put(name, t);
			        }
				}
			});
		}		
	}
	
	@SubscribeEvent(priority=EventPriority.HIGHEST)
	public static void onTagUpdate(TagsUpdatedEvent event) {
		if (true)
			return;	// Disable code based tag loading... forever...
		
		// On the server thread, insert tags and let the server do the sync job
		if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
			CustomTagsProvider.addBlockTags();
			CustomTagsProvider.addItemTags();
		}
	}
	
    @SubscribeEvent
    public static void onServerStarting(FMLServerStartingEvent e) {
    	CommandHandler.register(e.getCommandDispatcher());
    }
}
