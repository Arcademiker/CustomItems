package me.otho.customItems;

import me.otho.customItems.data.ResourcePaths;

public class ClientProxy extends CommonProxy {
	@Override
	public void createClientResPackDirs() {
		// Create .minecraft/resources/customitems_resources folder and its pack.mcmeta, if not found
		ResourcePaths.createEmptyFolders(ResourcePaths.respack_src);
		ResourcePaths.pack_mcmeta(ResourcePaths.respack_src, "CustomItems Resources");
		
		ResourcePaths.createEmptyFolders(ResourcePaths.respack_src_block);
		ResourcePaths.createEmptyFolders(ResourcePaths.respack_src_item);
		ResourcePaths.createEmptyFolders(ResourcePaths.respack_src_armor);
	}
	
	@Override
	public void runClientDataGenerators() {
		// Run client data generators
		CustomDataGenerator.runClient();

//		ResourcePackList<ClientResourcePackInfo> resPackList = Minecraft.getInstance().getResourcePackList();
//		String resPackGenName = "file/" + ResourcePaths.generated;
//		ClientResourcePackInfo resPackInfo = resPackList.getPackInfo(resPackGenName);
//		if (resPackInfo == null) {
//			Minecraft.getInstance().gameSettings.resourcePacks.add(resPackGenName);
//			Minecraft.getInstance().gameSettings.fillResourcePackList(resPackList);
//			
//			IResourcePack resPack = resPackList.getPackInfo(resPackGenName).getResourcePack();
//			IResourceManager manager = Minecraft.getInstance().getResourceManager();
//			if (manager instanceof SimpleReloadableResourceManager) {
//				((SimpleReloadableResourceManager) manager).addResourcePack(resPack);
//				LogHelper.info("Add customitems_generated to the resource pack list");
//			} else {
//				LogHelper.warn("Unable to add customitems_generated to the resource pack list");
//			}
//		}
	}
}
