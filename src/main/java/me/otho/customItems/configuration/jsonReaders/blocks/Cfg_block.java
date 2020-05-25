package me.otho.customItems.configuration.jsonReaders.blocks;

import me.otho.customItems.configuration.jsonReaders.common.Cfg_basicData;
import me.otho.customItems.mod.blocks.BlockType;
import me.otho.customItems.mod.materials.CI_Material;
import me.otho.customItems.utility.Util;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraftforge.common.ToolType;

public class Cfg_block extends Cfg_basicData {
	// Visual and sound
	protected Cfg_sideTextures multipleTextures;
	protected String renderLayer = "solid";
	protected boolean isOpaque = true;
	protected String type = "NORMAL";
	protected String stepSound = "stone";

  // Block Properties
	protected String material = "rock";
  public String toolClass;
	protected float resistance = 10;
	protected float hardness = 2;
	protected int lightLevel = 0;
	protected int lightOpacity = 0;
  public int harvestLevel = 0;
	protected float slipperiness = 0.6f;
	protected boolean isCollidable = true;
	protected int flammability = 0;
	protected int fireEncouragement = 0;

  // Drop
  public boolean dropsItSelf = false;
  public boolean canSilkHarvest = false;

  public String dropItemName;
  public int dropItemDamage = 0;
  public int minItemDrop = 1;
  public int maxItemDrop = 1;
  public int eachExtraItemDropChance = 50;

  // button/pressure plate
  public int tickRate = 30;

	public Material getMaterial() {
		return CI_Material.getMaterial(this.material);
	}
  
	/**
	 * Enforce field value, according to the BlockType
	 * 
	 * @param blockType
	 */
	protected void checkDefaults(BlockType blockType) {
		if (
				blockType == BlockType.SLAB ||
				blockType == BlockType.FENCE ||
				blockType == BlockType.WALL
				)
			this.isOpaque = false;
		

		Material material = this.getMaterial();
		if (material == Material.GLASS) {
			this.isOpaque = false;
			this.renderLayer = "translucent";
		}
	}

	public final BlockType toBlockType() {
		BlockType blockType = BlockType.forName(this.type);
		checkDefaults(blockType);
		return blockType;
	}
  
	public Block.Properties blockProp() {
		Material material = this.getMaterial();
		SoundType soundType = Util.parseSoundType(this.stepSound);
		Block.Properties prop = Block.Properties.create(material).sound(soundType)
				.hardnessAndResistance(this.hardness, this.resistance).slipperiness(this.slipperiness);

		if (!this.isCollidable)
			prop.doesNotBlockMovement();
		if (!this.dropsItSelf)
			prop.noDrops();

		prop.lightValue(Util.range(this.lightLevel, 0, 15));

		if (!this.isOpaque)
			prop.notSolid();

		if (this.toolClass != null) {
			prop.harvestLevel(this.harvestLevel);
			prop.harvestTool(ToolType.get(this.toolClass));
		}

		return prop;
	}
	
	public BlockData postConstruction(Block block) {
		block.setRegistryName(this.getRegistryName());
		
		// BlockItem
		BlockItem blockItem = new BlockItem(block, this.itemProp());
		blockItem.setRegistryName(block.getRegistryName());
		
		// Block properties
		((FireBlock)Blocks.FIRE).setFireInfo(block, this.fireEncouragement, this.flammability);
		int opaticy = this.isOpaque ? 255 : Util.range(this.lightOpacity, 0, 15);
		
		BlockData data = new BlockData(blockItem, opaticy, this.renderLayer);
		this.fillTextureNames(data.textureNames);
		
		return data;
	}
	
	private void fillTextureNames(String[] textureNames) {
		// Client-only
		// For compatibility purpose
		// First check for "textureName" - simple texture (e.g. Stone)
		// Then check for "multipleTextures" - complex texture (e.g. Furnace)
		// Otherwise assumes that a json block model is available
		if (this.textureName != null) {
			textureNames[0] = this.textureName;
		} else if (this.multipleTextures != null) {
			textureNames[0] = this.multipleTextures.yneg;
			textureNames[1] = this.multipleTextures.ypos;
			textureNames[2] = this.multipleTextures.zneg;
			textureNames[3] = this.multipleTextures.zpos;
			textureNames[4] = this.multipleTextures.xneg;
			textureNames[5] = this.multipleTextures.xpos;
		}
	}
	
	public static class BlockData {
		public final BlockItem blockItem;
		public final int opacity;
		public final String layer;
		public final String[] textureNames = new String[6];
		
		private BlockData(BlockItem blockItem, int opacity, String layer) {
			this.blockItem = blockItem;
			this.opacity = opacity;
			this.layer = layer;
		}
	}
}
