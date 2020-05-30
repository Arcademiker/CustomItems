package me.otho.customItems.client.data;

import me.otho.customItems.CustomItems;
import me.otho.customItems.block.CustomBlock;
import me.otho.customItems.block.CustomDoorBlock;
import me.otho.customItems.block.CustomFallingBlock;
import me.otho.customItems.block.CustomFenceBlock;
import me.otho.customItems.block.CustomGateBlock;
import me.otho.customItems.block.CustomPaneBlock;
import me.otho.customItems.block.CustomSlabBlock;
import me.otho.customItems.block.CustomStairsBlock;
import me.otho.customItems.block.CustomTrapDoorBlock;
import me.otho.customItems.block.CustomWallBlock;
import me.otho.customItems.configuration.block.JsBlock;
import me.otho.customItems.registry.BlockRegistry;
import me.otho.customItems.registry.ItemRegistry;
import me.otho.customItems.utility.Util;
import net.minecraft.block.Block;
import net.minecraft.block.StairsBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;

public class ModelDataProvider extends BlockStateProvider {
	public ModelDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
		super(gen, CustomItems.MOD_ID, exFileHelper);
	}
	
	private void blockItem(Block block, ModelFile blockModelFile) {
		// Item model
		models().getBuilder("item/"+block.getRegistryName().getPath()).parent(blockModelFile);
	}
	
	private void simpleItem(Item item, ResourceLocation texture) {
		String itemModelPath = "item/"+item.getRegistryName().getPath();
		models().withExistingParent(itemModelPath, mcLoc("item/generated")).texture("layer0", texture);
	}
	
	private void handheld(Item item, ResourceLocation texture) {
		String itemModelPath = "item/"+item.getRegistryName().getPath();
		models().getBuilder(itemModelPath)
		.parent(new ModelFile.ExistingModelFile(mcLoc("item/handheld"), models().existingFileHelper))
		.texture("layer0", texture);
	}
	
	private ModelFile cube(Block block, JsBlock data, String blockModelPath) {
		return models().cube(blockModelPath,
				data.getTextureResLoc(Direction.DOWN), 
				data.getTextureResLoc(Direction.UP), 
				data.getTextureResLoc(Direction.NORTH),
				data.getTextureResLoc(Direction.SOUTH), 
				data.getTextureResLoc(Direction.WEST), 
				data.getTextureResLoc(Direction.EAST))
				.texture("particle", data.getTextureResLoc(Direction.UP));
	}
	
	private void normalBlock(Block block, JsBlock data) {
		String blockModelPath = "block/"+block.getRegistryName().getPath();
		ModelFile blockModelFile;
		
		if (data.hasSidedTexture()) {
			blockModelFile = cube(block, data, blockModelPath);
		} else {
			blockModelFile = models().cubeAll(blockModelPath, Util.resLoc(data.getDefaultTextureName()));
		}
		
		getVariantBuilder(block).forAllStates(
				(blockstate)->ConfiguredModel.builder().modelFile(blockModelFile).build());
		
		// Item model
		blockItem(block, blockModelFile);
	}
	
	private void fenceBlock(CustomFenceBlock block, JsBlock data) {
		ResourceLocation resLoc = data.getTextureResLoc(null);
		fenceBlock(block, resLoc);
		
		// Item model
		String fenceInvFileName = "block/"+block.getRegistryName().getPath()+"_inventory";
		ModelFile fenInvModel = models().withExistingParent(fenceInvFileName, "block/fence_inventory").texture("texture", resLoc);
		blockItem(block, fenInvModel);
	}
	
	private void slabBlock(CustomSlabBlock block, JsBlock data) {
		String name = block.getRegistryName().getPath();
		ResourceLocation bottom = data.getTextureResLoc(Direction.DOWN);
		ResourceLocation top = data.getTextureResLoc(Direction.UP);
		ResourceLocation side = data.getTextureResLoc(Direction.NORTH);
		
		ModelFile slabModel = models().slab(name, side, bottom, top);
	
		slabBlock(block, 
				slabModel, 
				models().slabTop(name + "_top", side, bottom, top), 
				cube(block, data, name + "_double"));
		
		// Item model
		blockItem(block, slabModel);
	}
	
    private ModelFile stairsBlockInternal(StairsBlock block, ResourceLocation side, ResourceLocation bottom, ResourceLocation top) {
        String baseName = block.getRegistryName().toString();
    	ModelFile stairs = models().stairs(baseName, side, bottom, top);
        ModelFile stairsInner = models().stairsInner(baseName + "_inner", side, bottom, top);
        ModelFile stairsOuter = models().stairsOuter(baseName + "_outer", side, bottom, top);
        stairsBlock(block, stairs, stairsInner, stairsOuter);
        
        return stairs;
    }
	
	private void stairsBlock(CustomStairsBlock block, JsBlock data) {
		ModelFile invModel = stairsBlockInternal(block, 
					data.getTextureResLoc(Direction.NORTH),
					data.getTextureResLoc(Direction.DOWN),
					data.getTextureResLoc(Direction.UP)
					);
		
		// Item model
		blockItem(block, invModel);
	}
	
	private void wallBlock(CustomWallBlock block, JsBlock data) {
		ResourceLocation resLoc = data.getTextureResLoc(null);
		wallBlock(block, resLoc);
		
		// Item model
		String invFileName = "block/"+block.getRegistryName().getPath()+"_inventory";
		ModelFile invModel = models().withExistingParent(invFileName, "block/wall_inventory").texture("wall", resLoc);
		blockItem(block, invModel);
	}
	
	private void paneBlock(CustomPaneBlock block, JsBlock data) {
		ResourceLocation pane = data.getTextureResLoc(Direction.UP);
		ResourceLocation edge = data.getTextureResLoc(Direction.EAST);

		paneBlock(block, pane, edge);
		
		// Item model
		simpleItem(block.asItem(), pane);
	}

	private void doorBlock(CustomDoorBlock block, JsBlock data) {
		doorBlock(block, data.getTextureResLoc(Direction.DOWN), data.getTextureResLoc(Direction.UP));
		
		ResourceLocation itemResLoc = data.getTextureResLoc(Direction.NORTH);
		
		// Item model
		simpleItem(block.asItem(), itemResLoc);
	}
	
	private void trapdoorBlock(CustomTrapDoorBlock block, JsBlock data) {
		ResourceLocation resLoc = data.getTextureResLoc(null);
		trapdoorBlock(block, resLoc, false);
		
		// Item model
		ModelFile invModel = new ModelFile.UncheckedModelFile(block.getRegistryName().getNamespace() +":block/" + block.getRegistryName().getPath() + "_bottom");
		String invFileName = "item/"+block.getRegistryName().getPath();
		models().getBuilder(invFileName).parent(invModel);
	}
	
	private void gateBlock(CustomGateBlock block, JsBlock data) {
		fenceGateBlock(block, data.getTextureResLoc(null));
		
		// Item model
		ModelFile invModel = new ModelFile.UncheckedModelFile(block.getRegistryName());
		String invFileName = "item/"+block.getRegistryName().getPath();
		models().getBuilder(invFileName).parent(invModel);
	}
	
	@Override
	protected void registerStatesAndModels() {
		BlockRegistry.foreachBlock((block, data) -> {
			if (block instanceof CustomBlock) {
				normalBlock((CustomBlock)block, data);
			} else if (block instanceof CustomFenceBlock) {
				fenceBlock((CustomFenceBlock) block, data);
			} else if (block instanceof CustomSlabBlock) {
				slabBlock((CustomSlabBlock) block, data);
			} else if (block instanceof CustomStairsBlock) {
				stairsBlock((CustomStairsBlock) block, data);
			} else if (block instanceof CustomWallBlock) {
				wallBlock((CustomWallBlock) block, data);
			} else if (block instanceof CustomPaneBlock) {
				paneBlock((CustomPaneBlock) block, data);
			} else if (block instanceof CustomFallingBlock) {
				normalBlock((CustomFallingBlock)block, data);
			} else if (block instanceof CustomDoorBlock) {
				doorBlock((CustomDoorBlock)block, data);
			} else if (block instanceof CustomTrapDoorBlock) {
				trapdoorBlock((CustomTrapDoorBlock)block, data);
			} else if (block instanceof CustomGateBlock) {
				gateBlock((CustomGateBlock)block, data);
			}
		});
		
		ItemRegistry.foreachItem((item, data) -> {
			if (item instanceof SwordItem) {
				handheld(item, data.getTextureResLoc());
			} else {
				simpleItem(item, data.getTextureResLoc());
			}
		});
	}

}
