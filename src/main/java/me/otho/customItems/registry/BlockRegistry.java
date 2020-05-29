package me.otho.customItems.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import me.otho.customItems.configuration.JsonConfigurationHandler;
import me.otho.customItems.configuration.jsonReaders.blocks.Cfg_block;
import me.otho.customItems.mod.blocks.BlockType;
import me.otho.customItems.mod.blocks.IBlockItemProvider;
import me.otho.customItems.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRegistry {
	// Values only available during start-up
	private final static Map<Block, Cfg_block> blocks = new HashMap<>();

	public static void initBlocks() {
		Cfg_block[] dataList = JsonConfigurationHandler.allData.blocks;
		if (dataList == null)
			return;

		for (Cfg_block data : dataList) {
			boolean registered = initBlock(data);

			if (!registered) {
				LogHelper.error("Failed to register: Block " + data.getFriendlyName());
			}
		}
	}
	
	public static void registerBlocks(IForgeRegistry<Block> registry) {
		blocks.entrySet().stream().sorted(
				(entry1, entry2) -> 
					Integer.valueOf(entry1.getValue().registerOrder).compareTo(entry2.getValue().registerOrder)
				)
				.forEach((entry)->registry.register(entry.getKey()));
	}
	
	public static void registerBlockItems(IForgeRegistry<Item> registry) {
		foreachBlock((block)-> {
			if (block instanceof IBlockItemProvider) {
				registry.register(block.asItem());
			}
		});
	}
	
	public static void foreachBlock(Consumer<? super Block> consumer) {
		blocks.keySet().forEach(consumer);
	}
	
	public static void foreachBlock(BiConsumer<? super Block, ? super Cfg_block> consumer) {
		blocks.forEach(consumer);
	}
	
	public static void postRegistraion() {
		// TODO: Release the link to the Json data
//		blocks.replaceAll((block, data) -> null);
	}

	private static boolean initBlock(Cfg_block data) {
		LogHelper.info("Instantiating Block: " + data.toString(), 1);
		BlockType blockType = data.toBlockType();
		if (blockType == null) {
			LogHelper.error("Failed to instantiate block: " + data.toString() + ", type was not recognized");
			return false;
		}

		Block block = blockType.construct(data);
		if (block == null) {
			LogHelper.error("Failed to instantiate block: " + data.toString() + ", type was not implemented");
		} else {
			blocks.put(block, data);
		}
		return true;
	}
}
