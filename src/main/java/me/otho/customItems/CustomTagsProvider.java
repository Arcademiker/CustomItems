package me.otho.customItems;

import java.util.HashSet;
import java.util.Set;

import me.otho.customItems.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;

public class CustomTagsProvider {
	private final static Set<BlockTagDefinition> definitions = new HashSet<>();
	
	private static class BlockTagDefinition {
		private final Class<? extends Block> baseCls;
		private final Tag<Block> blockTag;
		private final Tag<Item> itemTag;
		private BlockTagDefinition(Class<? extends Block> baseCls, Tag<Block> blockTag, Tag<Item> itemTag) {
			this.baseCls = baseCls;
			this.blockTag = blockTag;
			this.itemTag = itemTag;
		}
	}
	
	static {
		definitions.add(new BlockTagDefinition(FenceBlock.class, BlockTags.FENCES, ItemTags.FENCES));
		definitions.add(new BlockTagDefinition(SlabBlock.class, BlockTags.SLABS, ItemTags.SLABS));
		definitions.add(new BlockTagDefinition(StairsBlock.class, BlockTags.STAIRS, ItemTags.STAIRS));
		definitions.add(new BlockTagDefinition(WallBlock.class, BlockTags.WALLS, ItemTags.WALLS));
		definitions.add(new BlockTagDefinition(DoorBlock.class, BlockTags.DOORS, ItemTags.DOORS));
		definitions.add(new BlockTagDefinition(TrapDoorBlock.class, BlockTags.TRAPDOORS, ItemTags.TRAPDOORS));
	}
	
	private static BlockTagDefinition getBlockTagDefinition(Class<? extends Block> blockCls) {
		for (BlockTagDefinition def: definitions) {
			if (def.baseCls.isAssignableFrom(blockCls))
				return def;
		}
		return null;
	}
	
	public static Tag<Block> getBlockTag(Class<? extends Block> blockCls) {
		BlockTagDefinition def = getBlockTagDefinition(blockCls);
		return def==null? null : def.blockTag;
	}

	public static Tag<Item> getItemTag(Class<? extends Block> blockCls) {
		BlockTagDefinition def = getBlockTagDefinition(blockCls);
		return def==null? null : def.itemTag;
	}
	
	public static void addBlockTags() {
		BlockRegistry.foreachBlock((block) -> {
			Tag<Block> tag = getBlockTag(block.getClass());
			if (tag != null)
				tag.getAllElements().add(block);
		});
	}
	
	public static void addItemTags() {
		BlockRegistry.foreachBlock((block) -> {
			Tag<Item> tag = getItemTag(block.getClass());
			if (tag != null)
				tag.getAllElements().add(block.asItem());
		});	
	}
}
