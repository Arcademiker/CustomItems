package me.otho.customItems.block;

import me.otho.customItems.configuration.block.JsBlock;
import net.minecraft.block.AbstractButtonBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class CustomButtonBlock extends AbstractButtonBlock implements IBlockInterfaces {
	private final JsBlock.BlockData blockData;
	private final boolean isWooden;
	
	public CustomButtonBlock(JsBlock data) {
		super(data.getMaterial()==Material.WOOD, data.blockProp());
		this.blockData = data.postConstruction(this);
		this.isWooden = data.getMaterial()==Material.WOOD;
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

	@Override
	protected SoundEvent getSoundEvent(boolean isPush) {
		if (this.isWooden) {
			return isPush ? SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_ON : SoundEvents.BLOCK_WOODEN_BUTTON_CLICK_OFF;
		} else {
			return isPush ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
		}
	}
}

