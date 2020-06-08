package me.otho.customItems.configuration.block;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Triple;

import me.otho.customItems.CustomItems;
import me.otho.customItems.block.BlockType;
import me.otho.customItems.block.MaterialLookUp;
import me.otho.customItems.block.SoundTypeLookUp;
import me.otho.customItems.configuration.common.IReloadable;
import me.otho.customItems.configuration.common.ITextureProvider;
import me.otho.customItems.configuration.common.JsRegistriableBase;
import me.otho.customItems.utility.Util;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;

public class JsBlock extends JsRegistriableBase implements IReloadable<JsBlock>, ITextureProvider {
	// Visual and sound
	protected JsSidedTexture multipleTextures;
	protected boolean generateModel = true;
	protected String renderLayer = "solid";
	protected boolean isOpaque = true;
	protected String type = "NORMAL";
	protected String stepSound = "stone";

	// Block Properties
	protected String material = "rock";
	protected String toolClass;
	protected float resistance = 10;
	protected float hardness = 2;
	protected int lightLevel = 0;
	protected int lightOpacity = 0;
	protected int harvestLevel = 0;
	protected float slipperiness = 0.6f;
	protected boolean isCollidable = true;
	protected int flammability = 0;
	protected int fireEncouragement = 0;

	// Drop (Loot Table)
	protected boolean breaks = false;
	protected boolean canSilkHarvest = false;
	protected String dropItemName = null;
	// These are applicable if canSilkHarvest==dropsItSelf==true && dropItemName!=null
	public int minItemDrop = 1;
	public int maxItemDrop = 1;
	public int eachExtraItemDropChance = 0;

	@Override
	public void onJsonReload(JsBlock newVal) {
		this.breaks = newVal.breaks;
		this.canSilkHarvest = newVal.canSilkHarvest;
		this.dropItemName = newVal.dropItemName;
		this.minItemDrop = newVal.minItemDrop;
		this.maxItemDrop = newVal.maxItemDrop;
		this.eachExtraItemDropChance = newVal.eachExtraItemDropChance;
		
		this.textureName = newVal.textureName;
		if (this.multipleTextures == null && newVal.multipleTextures != null) {
			this.multipleTextures = new JsSidedTexture();
		} else if (this.multipleTextures != null && newVal.multipleTextures == null) {
			this.multipleTextures = null;
		}
		
		if (this.multipleTextures != null && newVal.multipleTextures != null) {
			this.multipleTextures.ypos = newVal.multipleTextures.ypos;
			this.multipleTextures.yneg = newVal.multipleTextures.yneg;
			this.multipleTextures.xpos = newVal.multipleTextures.xpos;
			this.multipleTextures.xneg = newVal.multipleTextures.xneg;
			this.multipleTextures.zpos = newVal.multipleTextures.zpos;
			this.multipleTextures.zneg = newVal.multipleTextures.zneg;
		}
	}

	public Material getMaterial() {
		return MaterialLookUp.get(this.material);
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
//			this.renderLayer = "translucent";
		}
	}

	public final BlockType toBlockType() {
		BlockType blockType = BlockType.forName(this.type);
		checkDefaults(blockType);
		return blockType;
	}
  
	public Block.Properties blockProp() {
		Material material = this.getMaterial();
		SoundType soundType = SoundTypeLookUp.get(this.stepSound);
		Block.Properties prop = Block.Properties.create(material).sound(soundType)
				.hardnessAndResistance(this.hardness, this.resistance).slipperiness(this.slipperiness);

		if (!this.isCollidable)
			prop.doesNotBlockMovement();

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
		int opaticy = this.isOpaque ? 255 : Util.range(this.lightOpacity, 0, 255);
		
		BlockData data = new BlockData(blockItem, opaticy, this.renderLayer);
		
		return data;
	}
	
	public static class BlockData {
		public final BlockItem blockItem;
		public final int opacity;
		public final String layer;
		
		private BlockData(BlockItem blockItem, int opacity, String layer) {
			this.blockItem = blockItem;
			this.opacity = opacity;
			this.layer = layer;
		}
	}
	
	/////////////////////////////////////////////
	/// Textures
	/////////////////////////////////////////////
	@Override
	public boolean generateModel() {
		return this.generateModel;
	}
	
	@Override
	public boolean hasSidedTexture() {
		return this.multipleTextures != null;
	}
	
	@Override
	public ResourceLocation getTextureResLoc(@Nullable Direction side) {
		String textureName;
		if (side == null) {
			textureName = this.getDefaultTextureName();
		} else {
			if (this.multipleTextures == null) {
				textureName = this.getDefaultTextureName();
			} else {
				switch (side) {
				case DOWN:
					textureName = this.multipleTextures.yneg;
					break;
				case UP:
					textureName = this.multipleTextures.ypos;
					break;
				case NORTH:
					textureName = this.multipleTextures.zneg;
					break;
				case SOUTH:
					textureName = this.multipleTextures.zpos;
					break;
				case WEST:
					textureName = this.multipleTextures.xneg;
					break;
				case EAST:
					textureName = this.multipleTextures.xpos;
					break;
				default:
					return null;
				}
				
				if (textureName == null)
					textureName = this.getDefaultTextureName();
			}
		}
		
		return Util.resLoc("block", textureName);
	}
		
	/////////////////////////////////////////////
	/// Loot table and Item drops
	/////////////////////////////////////////////
	/*
	 *  Truth table:                                  NORMAL         | SILK_TOUCH
		dropItemName=air, canSilkHarvest=0, breaks=0: original block | original block
		dropItemName=air, canSilkHarvest=1, breaks=0: original block | original block
		dropItemName=xxx, canSilkHarvest=0, breaks=0: dropItemName   | dropItemName
		dropItemName=xxx, canSilkHarvest=1, breaks=0: dropItemName   | original block
		dropItemName=air, canSilkHarvest=0, breaks=1: NOTHING        | NOTHING
		dropItemName=air, canSilkHarvest=1, breaks=1: NOTHING        | original block
		dropItemName=xxx, canSilkHarvest=0, breaks=1: NOTHING        | NOTHING
		dropItemName=xxx, canSilkHarvest=1, breaks=1: NOTHING        | dropItemName
	 */
	public static enum LootType {
		ALWAYS_BLOCK, ALWAYS_ITEM, COAL_ORE, GLASS, NOTHING;
	}
	
	protected Item getDropItemRecord() {
		return Util.parseDropItem(this.dropItemName);
	}
	
	protected Item getDropBlockRecord() {
		return Util.get(Block.class, CustomItems.MOD_ID, this.getRegistryName()).asItem();
	}
	
	public Triple<LootType, Item, Item> getDrops() {
		LootType type;
		Item normal, silktouch;
		if (this.breaks) {
			normal = Items.AIR;
			if (this.canSilkHarvest) {
				type = LootType.GLASS;
				Item item = getDropItemRecord();
				silktouch = item==Items.AIR ? getDropBlockRecord() : item;
			} else {
				type = LootType.NOTHING;
				silktouch = Items.AIR;
			}
		} else {
			Item item = getDropItemRecord();
			Item block = getDropBlockRecord();
			
			if (item==Items.AIR) {
				type = LootType.ALWAYS_BLOCK;
				normal = block;
				silktouch = block;
			} else {
				normal = item;
				if (this.canSilkHarvest) {
					type = LootType.COAL_ORE;
					silktouch = block;
				} else {
					type = LootType.ALWAYS_ITEM;
					silktouch = item;
				}
			}
		}
		
		return Triple.of(type, normal, silktouch);
	}
}
