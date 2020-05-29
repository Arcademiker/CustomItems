package me.otho.customItems;

import java.nio.file.Path;

import me.otho.customItems.registry.BlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class BlockTagsProvider extends TagsProvider<Block> {
	@SuppressWarnings("deprecation")
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
			Tag<Block> tag = CustomTagsProvider.getBlockTag(block.getClass());
			if (tag != null)
				this.getBuilder(tag).add(block);
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
