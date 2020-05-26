package me.otho.customItems;

import me.otho.customItems.mod.blocks.CustomBlock;
import me.otho.customItems.mod.blocks.CustomDoorBlock;
import me.otho.customItems.mod.blocks.CustomFallingBlock;
import me.otho.customItems.mod.blocks.CustomFenceBlock;
import me.otho.customItems.mod.blocks.CustomGateBlock;
import me.otho.customItems.mod.blocks.CustomPaneBlock;
import me.otho.customItems.mod.blocks.CustomSlabBlock;
import me.otho.customItems.mod.blocks.CustomStairsBlock;
import me.otho.customItems.mod.blocks.CustomTrapDoorBlock;
import me.otho.customItems.mod.blocks.CustomWallBlock;
import me.otho.customItems.mod.blocks.ITextureBlock;
import me.otho.customItems.registry.BlockRegistry;
import me.otho.customItems.registry.ItemRegistry;
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
	
	private <T extends Block&ITextureBlock> ModelFile cube(T block, String blockModelPath) {
		return models().cube(blockModelPath, 
				block.getTextureResLoc(Direction.DOWN), 
				block.getTextureResLoc(Direction.UP), 
				block.getTextureResLoc(Direction.NORTH),
				block.getTextureResLoc(Direction.SOUTH), 
				block.getTextureResLoc(Direction.WEST), 
				block.getTextureResLoc(Direction.EAST))
				.texture("particle", block.getTextureResLoc(Direction.UP));
	}
	
	private <T extends Block&ITextureBlock> void normalBlock(T block) {
		String blockModelPath = "block/"+block.getRegistryName().getPath();
		ModelFile blockModelFile;
		
		int count = block.getTextureCount();
		
		if (count == 0) {
			return;
		} else if (count == 1) {
			blockModelFile = models().cubeAll(blockModelPath, block.getTextureResLoc(null));
		} else {
			blockModelFile = cube(block, blockModelPath);
		}
		getVariantBuilder(block).forAllStates(
				(blockstate)->ConfiguredModel.builder().modelFile(blockModelFile).build());
		
		// Item model
		blockItem(block, blockModelFile);
	}
	
	private void fenceBlock(CustomFenceBlock block) {
		ResourceLocation resLoc = block.getTextureResLoc();
		fenceBlock(block, resLoc);
		
		// Item model
		String fenceInvFileName = "block/"+block.getRegistryName().getPath()+"_inventory";
		ModelFile fenInvModel = models().withExistingParent(fenceInvFileName, "block/fence_inventory").texture("texture", resLoc);
		blockItem(block, fenInvModel);
	}
	
	private void slabBlock(CustomSlabBlock block) {
		String name = block.getRegistryName().getPath();
		ResourceLocation bottom = block.getTextureResLoc(Direction.DOWN);
		ResourceLocation top = block.getTextureResLoc(Direction.UP);
		ResourceLocation side = block.getTextureResLoc(Direction.NORTH);
		ModelFile slabModel = models().slab(name, side, bottom, top);
	
		slabBlock(block, 
				slabModel, 
				models().slabTop(name + "_top", side, bottom, top), 
				cube(block, name + "_double"));
		
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
	
	private void stairsBlock(CustomStairsBlock block) {
		int count = block.getTextureCount();
		
		ModelFile invModel = null;
		if (count == 0) {
			return;
		} else if (count == 1) {
			ResourceLocation resLoc = block.getTextureResLoc(null);
			invModel = stairsBlockInternal(block, resLoc, resLoc, resLoc);
		} else {
			invModel = stairsBlockInternal(block, 
					block.getTextureResLoc(Direction.NORTH),
					block.getTextureResLoc(Direction.DOWN),
					block.getTextureResLoc(Direction.UP)
					);
		}
		
		// Item model
		blockItem(block, invModel);
	}
	
	private void wallBlock(CustomWallBlock block) {
		ResourceLocation resLoc = block.getTextureResLoc();
		wallBlock(block, resLoc);
		
		// Item model
		String invFileName = "block/"+block.getRegistryName().getPath()+"_inventory";
		ModelFile invModel = models().withExistingParent(invFileName, "block/wall_inventory").texture("wall", resLoc);
		blockItem(block, invModel);
	}
	
	private void paneBlock(CustomPaneBlock block) {
		ResourceLocation pane;
		ResourceLocation edge;
		
		if (block.getTextureCount() == 2) {
			pane = block.getTextureResLoc(Direction.UP);
			edge = block.getTextureResLoc(Direction.EAST);
		} else {
			pane = block.getTextureResLoc();
			edge = pane;
		}
		paneBlock(block, pane, edge);
		
		// Item model
		simpleItem(block.asItem(), pane);
	}

	private void doorBlock(CustomDoorBlock block) {
		doorBlock(block, block.getTextureResLoc(Direction.DOWN), block.getTextureResLoc(Direction.UP));
		
		// Item model
		simpleItem(block.asItem(), block.getTextureResLoc(Direction.NORTH));
	}
	
	private void trapdoorBlock(CustomTrapDoorBlock block) {
		ResourceLocation resLoc = block.getTextureResLoc();
		trapdoorBlock(block, resLoc, false);
		
		// Item model
		ModelFile invModel = new ModelFile.UncheckedModelFile(block.getRegistryName().getNamespace() +":block/" + block.getRegistryName().getPath() + "_bottom");
		String invFileName = "item/"+block.getRegistryName().getPath();
		models().getBuilder(invFileName).parent(invModel);
	}
	
	private void gateBlock(CustomGateBlock block) {
		fenceGateBlock(block, block.getTextureResLoc());
		
		// Item model
		ModelFile invModel = new ModelFile.UncheckedModelFile(block.getRegistryName());
		String invFileName = "item/"+block.getRegistryName().getPath();
		models().getBuilder(invFileName).parent(invModel);
	}
	
	@Override
	protected void registerStatesAndModels() {
		BlockRegistry.foreachBlock((block) -> {
			if (block instanceof CustomBlock) {
				normalBlock((CustomBlock)block);
			} else if (block instanceof CustomFenceBlock) {
				fenceBlock((CustomFenceBlock) block);
			} else if (block instanceof CustomSlabBlock) {
				slabBlock((CustomSlabBlock) block);
			} else if (block instanceof CustomStairsBlock) {
				stairsBlock((CustomStairsBlock) block);
			} else if (block instanceof CustomWallBlock) {
				wallBlock((CustomWallBlock) block);
			} else if (block instanceof CustomPaneBlock) {
				paneBlock((CustomPaneBlock) block);
			} else if (block instanceof CustomFallingBlock) {
				normalBlock((CustomFallingBlock)block);
			} else if (block instanceof CustomDoorBlock) {
				doorBlock((CustomDoorBlock)block);
			} else if (block instanceof CustomTrapDoorBlock) {
				trapdoorBlock((CustomTrapDoorBlock)block);
			} else if (block instanceof CustomGateBlock) {
				gateBlock((CustomGateBlock)block);
			}
		});
		
		ItemRegistry.foreachItem((item, resLoc) -> {
			if (item instanceof SwordItem) {
				handheld(item, resLoc);
			} else {
				simpleItem(item, resLoc);
			}
		});
	}

}
