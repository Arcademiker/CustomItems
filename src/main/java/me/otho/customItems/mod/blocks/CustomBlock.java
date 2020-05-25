package me.otho.customItems.mod.blocks;

import me.otho.customItems.configuration.jsonReaders.blocks.Cfg_block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class CustomBlock extends Block implements IBlockInterfaces {
	private final Cfg_block.BlockData blockData;
	
	public CustomBlock(Cfg_block data) {
		super(data.blockProp());
		this.blockData = data.postConstruction(this);
		
		// TODO: Check these
		this.canSilkHarvest = data.canSilkHarvest;
		if (data.dropItemName != null) {
			this.dropItem = data.dropItemName;
			this.maxItemDrop = data.maxItemDrop;
			this.minItemDrop = data.minItemDrop;
			this.eachExtraItemDropChance = data.eachExtraItemDropChance;
		}
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
  
  private boolean canSilkHarvest;
  private int maxItemDrop;
  private int minItemDrop;
  private int eachExtraItemDropChance;
  protected String dropItem;
  protected boolean breaks;

//  @Override
//  @SideOnly(Side.CLIENT)
//  public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
//    Block i1 = par1IBlockAccess.getBlock(par2, par3, par4);
//    if (i1 instanceof CustomSlabBlock) {
//      return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
//    } else {
//      return i1 == this ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
//    }
//
//  }

  protected int getItemDropQuantity(World world, int fortune) {
    int ret = 0;
    int i;
    ret = this.minItemDrop;
    for (i = this.minItemDrop; i < this.maxItemDrop + fortune; i++) {
      boolean willDrop = world.rand.nextInt(100) < this.eachExtraItemDropChance;
      if (willDrop) {
        ret++;
      }
    }

    return ret;
  }

//  @Override
//  public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
//    ArrayList<ItemStack> drops = new ArrayList();
//
//    if (dropItem != null) {
//      String[] parser = dropItem.split(":");
//      Item item = GameRegistry.findItem(parser[0], parser[1]);
//
//      if (item != null) {
//        int itemQuantity = getItemDropQuantity(world, fortune);
//        int damage;
//
//        if (parser.length > 2) {
//          damage = Integer.parseInt(parser[2]);
//        } else {
//          damage = 0;
//        }
//        drops.add(new ItemStack(item, itemQuantity, damage));
//      }
//    } else {
//      if (!breaks) {
//        drops.add(new ItemStack(Item.getItemFromBlock(this)));
//      }
//    }
//
//    return drops;
//  }
}
