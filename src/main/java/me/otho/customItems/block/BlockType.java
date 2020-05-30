package me.otho.customItems.block;

import java.util.function.Function;

import me.otho.customItems.configuration.block.JsBlock;
import net.minecraft.block.Block;

public enum BlockType {
    NORMAL(CustomBlock::new), 
    SLAB(CustomSlabBlock::new), 
    LOG, 
    PILLARS, 
    STAIRS(CustomStairsBlock::new), 
    PANE(CustomPaneBlock::new), 
    FENCE(CustomFenceBlock::new), 
    WALL(CustomWallBlock::new), 
    FALLING(CustomFallingBlock::new),
    // 1.0.9.2
    CROSSED,
    // 1.0.10
    FLOWER, CARPET, TORCH, 
    GATE(CustomGateBlock::new), 
    DOOR(CustomDoorBlock::new), 
    TRAPDOOR(CustomTrapDoorBlock::new), 
    LADDER, BUTTON, LEVER, BED, PRESSUREPLATE;
	
	BlockType() {
		this(null);
	}
	
	private final Function<JsBlock, Block> constructor;
	BlockType(Function<JsBlock, Block> constructor) {
		this.constructor = constructor;
	}
	
	public Block construct(JsBlock data) {
		return this.constructor == null ? null : this.constructor.apply(data);
	}
	
	public static BlockType forName(String nameIn) {
		for (BlockType blockType: BlockType.values()) {
			if (nameIn.toUpperCase().equals(blockType.name()))
				return blockType;
		}
		return null;
	}
}
