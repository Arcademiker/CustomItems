package me.otho.customItems.mod.handler;

/*This BucketHandler class is the one from Buildcraft, all credits to Spacetoad and the BuildCraft team.
 */
import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BucketHandler {
  public static BucketHandler INSTANCE = new BucketHandler();
  public Map<Block, Item> buckets = new HashMap<Block, Item>();

  private BucketHandler() {
  }

  @SubscribeEvent
  public void onBucketFill(FillBucketEvent event) {

    ItemStack result = fillCustomBucket(event.getWorld(), event.getTarget());

    if (result == ItemStack.EMPTY) {
      return;
    }

    event.setFilledBucket(result);
    event.setResult(Result.ALLOW);
  }

  private ItemStack fillCustomBucket(World world, RayTraceResult rayTraceResult) {
//	  if (!(rayTraceResult instanceof BlockRayTraceResult))
//		  return ItemStack.EMPTY;
//	  BlockPos pos = ((BlockRayTraceResult)rayTraceResult).getPos();
//	  
//    BlockState blockState = world.getBlockState(pos);
//
//    Item bucket = buckets.get(block);
//    if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
//      world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
//      return new ItemStack(bucket);
//    } else {
//      return ItemStack.EMPTY;
//    }
    return ItemStack.EMPTY;
  }
}
