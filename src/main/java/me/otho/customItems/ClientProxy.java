package me.otho.customItems;

import java.util.List;

import me.otho.customItems.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ClientResourcePackInfo;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackList;
import net.minecraft.resources.SimpleReloadableResourceManager;

public class ClientProxy extends CommonProxy {
	@Override
	public void runClientDataGenerators() {
		// Create .minecraft/resources/customitems_resources folder and its pack.mcmeta, if not found
		ResourcePaths.createEmptyFolders(ResourcePaths.respack_src);
		ResourcePaths.pack_mcmeta(ResourcePaths.respack_src, "CustomItems Resources");

		// Run client data generators
		CustomDataGenerator.runClient();

		String resPackGenName = "file/" + ResourcePaths.generated;
		List<String> resPackNames = Minecraft.getInstance().gameSettings.resourcePacks;
		if (!resPackNames.contains(resPackGenName)) {
			resPackNames.add(resPackGenName);
			
			ResourcePackList<ClientResourcePackInfo> resPackList = Minecraft.getInstance().getResourcePackList();
			Minecraft.getInstance().gameSettings.fillResourcePackList(resPackList);
			
			IResourcePack resPack = resPackList.getPackInfo(resPackGenName).getResourcePack();
			IResourceManager manager = Minecraft.getInstance().getResourceManager();
			if (manager instanceof SimpleReloadableResourceManager) {
				((SimpleReloadableResourceManager) manager).addResourcePack(resPack);
				LogHelper.info("Add customitems_generated to the resource pack list");
			} else {
				LogHelper.warn("Unable to add customitems_generated to the resource pack list");
			}
		}
	}
}
