package me.otho.customItems;

import java.nio.file.Path;

import me.otho.customItems.registry.BlockRegistry;
import me.otho.customItems.registry.ItemRegistry;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagCollection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class ItemTagsProvider extends TagsProvider<Item> {
	protected ItemTagsProvider(DataGenerator generatorIn, Object dummy) {
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
			Item item = block.asItem();
			if (block instanceof FenceBlock) {
				this.getBuilder(ItemTags.FENCES).add(item);
			} else if (block instanceof SlabBlock) {
				this.getBuilder(ItemTags.SLABS).add(item);
			} else if (block instanceof StairsBlock) {
				this.getBuilder(ItemTags.STAIRS).add(item);
			} else if (block instanceof WallBlock) {
				this.getBuilder(ItemTags.WALLS).add(item);
			}else if (block instanceof DoorBlock) {
				this.getBuilder(ItemTags.DOORS).add(item);
			} else if (block instanceof TrapDoorBlock) {
				this.getBuilder(ItemTags.TRAPDOORS).add(item);
			}
		});
		
		ItemRegistry.foreachItem((item) -> {
			
		});
	}
}
