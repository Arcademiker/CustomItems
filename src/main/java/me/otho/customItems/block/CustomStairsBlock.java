package me.otho.customItems.block;

import me.otho.customItems.configuration.block.JsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class CustomStairsBlock extends StairsBlock implements IBlockInterfaces {
	private final JsBlock.BlockData blockData;

	public CustomStairsBlock(JsBlock data) {
		super(()->Blocks.AIR.getDefaultState(), data.blockProp());	// TODO: check 1st param
		this.blockData = data.postConstruction(this);
	}

	@Override
	public String getRenderLayerName() {
		return blockData.layer;
	}
	
	@Override
	public Item asItem() {
		return blockData.blockItem;
	}
	
	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return blockData.opacity;
	}
}
