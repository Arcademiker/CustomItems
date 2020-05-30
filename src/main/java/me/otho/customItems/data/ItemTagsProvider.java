package me.otho.customItems.data;

import java.nio.file.Path;

import me.otho.customItems.registry.BlockRegistry;
import me.otho.customItems.registry.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ItemTagsProvider extends TagsProvider<Item> {
	@SuppressWarnings("deprecation")
	public ItemTagsProvider(DataGenerator generatorIn, Object dummy) {
		super(generatorIn, Registry.ITEM);
	}
	/**
	 * Gets a name for this provider, to use in logging.
	 */
	@Override
	public String getName() {
		return "CustomItems Item Tags";
	}
	
	@Override
	protected void setCollection(TagCollection<Item> colectionIn) {
		ItemTags.setCollection(colectionIn);
	}

	/**
	 * Resolves a Path for the location to save the given tag.
	 */
	@Override
	protected Path makePath(ResourceLocation id) {
		return this.generator.getOutputFolder()
				.resolve("data/" + id.getNamespace() + "/tags/items/" + id.getPath() + ".json");
	}
	
	@Override
	protected void registerTags() {
		BlockRegistry.foreachBlock((block) -> {
			Tag<Item> tag = CustomTagsProvider.getItemTag(block.getClass());
			if (tag != null)
				this.getBuilder(tag).add(block.asItem());
		});
		
		ItemRegistry.foreachItem((item) -> {
			
		});
	}
}
