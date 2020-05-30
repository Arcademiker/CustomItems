package me.otho.customItems.block;

import me.otho.customItems.configuration.block.JsBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class CustomWallBlock extends WallBlock implements IBlockInterfaces {
	private final JsBlock.BlockData blockData;
	
	public CustomWallBlock(JsBlock data) {
		super(data.blockProp());
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
	public String[] getTextureNamesRaw() {
		return blockData.textureNames;
	}
	
	@Override
	public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return blockData.opacity;
	}
}