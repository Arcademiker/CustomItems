package me.otho.customItems.mod.blocks;

import net.minecraft.item.BlockItem;
import net.minecraft.util.IItemProvider;

/**
 * Implemented by blocks. <p>
 * Blocks implementing this should: <p>
 * 1. Instantiate its BlockItem instance during Block class instantiation. <p>
 * 2. Override {@link net.minecraft.block.Block#asItem()} and return the BlockItem instance.
 * Otherwise the game may crashing during initialization.
 */
public interface IBlockItemProvider extends IItemProvider {
	default BlockItem asBlockItem() {
		return (BlockItem) this.asItem();
	}
}
