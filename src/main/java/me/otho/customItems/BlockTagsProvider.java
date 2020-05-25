package me.otho.customItems;

import java.nio.file.Path;

import me.otho.customItems.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BlockTagsProvider extends TagsProvider<Block> {
	protected BlockTagsProvider(DataGenerator generatorIn, Object dummy) {
		super(generatorIn, Registry.BLOCK);
	}

	/**
	 * Gets a name for this provider, to use in logging.
	 */
	@Override
	public String getName() {
		return "CustomItems Block Tags";
	}

	@Override
	protected void registerTags() {
		BlockRegistry.foreachBlock((block) -> {
			if (block instanceof FenceBlock) {
				this.getBuilder(BlockTags.FENCES).add(block);
			} else if (block instanceof SlabBlock) {
				this.getBuilder(BlockTags.SLABS).add(block);
			} else if (block instanceof StairsBlock) {
				this.getBuilder(BlockTags.STAIRS).add(block);
			} else if (block instanceof WallBlock) {
				this.getBuilder(BlockTags.WALLS).add(block);
			}else if (block instanceof DoorBlock) {
				this.getBuilder(BlockTags.DOORS).add(block);
			} else if (block instanceof TrapDoorBlock) {
				this.getBuilder(BlockTags.TRAPDOORS).add(block);
			}
		});
	}

	@Override
	protected void setCollection(TagCollection<Block> colectionIn) {
		BlockTags.setCollection(colectionIn);
	}

	/**
	 * Resolves a Path for the location to save the given tag.
	 */
	@Override
	protected Path makePath(ResourceLocation id) {
		return this.generator.getOutputFolder()
				.resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
	}

}
