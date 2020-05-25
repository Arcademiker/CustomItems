package me.otho.customItems;

import me.otho.customItems.mod.blocks.IRenderLayerHandler;
import me.otho.customItems.registry.BlockRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = CustomItems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistrationHandler {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event) {
		DataGenerator generator = event.getGenerator();
		ExistingFileHelper exfh = event.getExistingFileHelper();

		if (event.includeClient()) {
			CustomDataGenerator.gatherClientData(generator, exfh);
		}
	}
	
	@SubscribeEvent
	public static void onClientSetup(FMLClientSetupEvent event){
		BlockRegistry.foreachBlock((block) -> {
			if (block instanceof IRenderLayerHandler) {
				String layer = ((IRenderLayerHandler)block).getRenderLayerName().toLowerCase();
				if (layer.equals("solid"))
					RenderTypeLookup.setRenderLayer(block, RenderType.getSolid());
				else if (layer.equals("cutout"))
					RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
				else if (layer.equals("cutout_mipped"))
					RenderTypeLookup.setRenderLayer(block, RenderType.getCutoutMipped());
				else if (layer.equals("translucent"))
					RenderTypeLookup.setRenderLayer(block, RenderType.getTranslucent());
				else if (layer.equals("translucent_no_crumbling"))
					RenderTypeLookup.setRenderLayer(block, RenderType.getTranslucentNoCrumbling());
			}
		});
	}
}
