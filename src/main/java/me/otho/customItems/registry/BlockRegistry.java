package me.otho.customItems.registry;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.ArrayUtils;

import me.otho.customItems.LocalizationGenerator;
import me.otho.customItems.configuration.JsonConfigurationHandler;
import me.otho.customItems.configuration.jsonReaders.blocks.Cfg_block;
import me.otho.customItems.configuration.jsonReaders.blocks.Cfg_blockDrop;
import me.otho.customItems.mod.blocks.BlockType;
import me.otho.customItems.mod.blocks.IBlockItemProvider;
//import me.otho.customItems.integration.Integration;
//import me.otho.customItems.integration.NEICustomItemsConfig;
import me.otho.customItems.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRegistry {
	private static List<Block> blocks = new LinkedList<>();

	public static void initBlocks() {
		Cfg_block[] dataList = JsonConfigurationHandler.allData.blocks;
		if (dataList == null)
			return;

		for (Cfg_block data : dataList) {
			boolean registered = initBlock(data);

			if (!registered) {
				LogHelper.error("Failed to register: Block " + data.getFriendlyName());
			}
		}
	}
	
	public static void registerBlocks(IForgeRegistry<Block> registry) {
		for (Block block: blocks)
			registry.register(block);
	}
	
	public static void registerBlockItems(IForgeRegistry<Item> registry) {
		for (Block block: blocks) {
			if (block instanceof IBlockItemProvider) {
				registry.register(block.asItem());
			}
		}
	}
	
	public static void foreachBlock(Consumer<? super Block> consumer) {
		blocks.forEach(consumer);
	}
	
  public static HashMap<String, Cfg_blockDrop> drops = new HashMap<String, Cfg_blockDrop>();

  public static boolean registerBlockDrop(Cfg_blockDrop data) {
    LogHelper.info("Registering Block Drop: " + data.id, 1);

    String[] parser = data.id.split(":");
    if (parser.length < 3) {
      data.id = data.id.concat(":0");
    }

    if (drops.containsKey(data.id)) {

      Cfg_blockDrop drop = drops.get(data.id);

      drop.drops = ArrayUtils.addAll(drop.drops, data.drops);

      drops.put(data.id, drop);
    } else {
      drops.put(data.id, data);
    }

    return true;
  }

  public static boolean registerBlockDrop(Cfg_blockDrop[] data) {
    int i;

    for (i = 0; i < data.length; i++) {
      boolean registered = registerBlockDrop(data[i]);

      if (!registered) {
        LogHelper.error("Failed to register: Block drop for" + data[i].id);
        return false;
      }
    }

    return true;
  }

  public static boolean initBlock(Cfg_block data) {
	  LogHelper.info("Instantiating Block: " + data.toString(), 1);
	  BlockType blockType = data.toBlockType();
	  if (blockType == null) {
		  LogHelper.error("Failed to instantiate block: " + data.toString() + ", type was not recognized");
		  return false;
	  }
	  
	  Block block = blockType.construct(data);
	  if (block == null) {
		  LogHelper.error("Failed to instantiate block: " + data.toString() + ", type was not implemented");
	  } else {			
		  // Localization
		  LocalizationGenerator.put(block, data.getFriendlyName());
		
		  blocks.add(block);
	  }
	  return true;
	  
//    data.toolClass = Util.validateToolClass(data.toolClass);
//    if (Util.validateType(data.type)) {
//      BlockType blockType = BlockType.valueOf(data.type.toUpperCase());
//      switch (blockType) {
//        // 1.0.10
//        case FLOWER:
//          registerFlowerBlock(data);
//        break;
//        case CARPET:
//          registerCarpetBlock(data);
//        break;
//        case TORCH:
//          registerTorchBlock(data);
//        break;
//        case GATE:
//          registerGateBlock(data);
//        break;
//        case DOOR:
//          registerDoorBlock(data);
//        break;
//        case TRAPDOOR:
//          registerTrapDoorBlock(data);
//        break;
//        case LADDER:
//          registerLadderBlock(data);
//        break;
//        case BUTTON:
//          registerButtonBlock(data);
//        break;
//        case LEVER:
//          registerLeverBlock(data);
//        break;
//        case BED:
//          registerBedBlock(data);
//        break;
//        case PRESSUREPLATE:
//          registerPressurePlateBlock(data);
//        break;
//        // pre 1.0.10
//        case FENCE:
//          registerFenceBlock(data);
//        break;
//        case LOG:
//        case PILLARS:
//          registerLogBlock(data);
//        break;
//        case PANE:
//          registerPaneBlock(data);
//        break;
//        case SLAB:
//          registerSlabBlock(data);
//        break;
//        case STAIRS:
//          registerStairsBlock(data);
//        break;
//        case WALL:
//          registerWallBlock(data);
//        break;
//        case FALLING:
//          registerFallingBlock(data);
//        break;
//        case CROSSED:
//          registerCrossedBlock(data);
//        break;
//        case NORMAL:
//          registerNormalBlock(data);
//        break;
//        default:
//          LogHelper.error("Failed to register block: " + data.name + ", type was not recognized");
//        break;
//      }
//    }
  }

//  public static boolean registerCrop(Cfg_crop data) {
//    LogHelper.info("Registering crop: " + data.name, 1);
//
//    String registerName = Util.parseRegisterName(data.name);
//
//    int cropRender;
//
//    if (data.renderType.equals("crops")) {
//      cropRender = 6;
//    } else if (data.renderType.equals("flower")) {
//      cropRender = 1;
//    } else {
//      cropRender = 6;
//    }
//
//    CustomCrop crop = new CustomCrop(data.fruitName, cropRender);
//    CustomSeed seed = new CustomSeed(crop);
//    crop.setSeed(seed);
//
//    crop.setAcceptBoneMeal(data.acceptBoneMeal);
//    crop.setDropSeedWhenMature(data.dropSeedWhenMature);
//    crop.setEachExtraFruitDropChance(data.eachExtraFruitDropChance);
//    crop.setEachExtraSeedDropChance(data.eachExtraSeedDropChance);
//    crop.setFruitQuantityDropRange(data.minFruitDrop, data.maxFruitDrop);
//    crop.setSeedQuantityDropRange(data.minSeedDrop, data.maxSeedDrop);
//    crop.setFruitItemDamage(data.dropFruitDamage);
//
//    crop.setBlockTextureName(data.textureName);
//
//    seed.setTextureName(data.textureName + "_seed");
//
//    GameRegistry.registerBlock(crop, registerName + "_crop");
//    crop.setBlockName(Registry.mod_id.toLowerCase() + ":" + registerName + "_crop");
//    LanguageRegistry.instance().addStringLocalization(crop.getUnlocalizedName() + ".name", "en_US", data.name);
//
//    GameRegistry.registerItem(seed, registerName + "_seed");
//    seed.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + registerName + "_seed");
//    LanguageRegistry.instance().addStringLocalization(seed.getUnlocalizedName() + ".name", "en_US",
//        data.name + " Seeds");
//
//    Registry.itemsList.add(seed);
//    Registry.itemsList.add(data.creativeTab);
//
//    if (data.dropFromGrassChance > 0) {
//      MinecraftForge.addGrassSeed(new ItemStack(seed), data.dropFromGrassChance);
//    }
//
//    if (Integration.isNEI()) {
//      NEICustomItemsConfig.addItemToHide(Registry.mod_id + ":" + registerName);
//    }
//    return true;
//  }
//
//  public static boolean registerCrop(Cfg_crop[] data) {
//    int i;
//
//    for (i = 0; i < data.length; i++) {
//      boolean registered = registerCrop(data[i]);
//
//      if (!registered) {
//        LogHelper.error("Failed to register: Crop " + data[i].name);
//        return false;
//      }
//    }
//
//    return true;
//  }
//
//  public static boolean registerFluid(Cfg_fluid data) {
//    LogHelper.info("Registering Fluid: " + data.name, 1);
//
//    String registerName = Util.parseRegisterName(data.name);
//    data.luminosity = Util.range(data.luminosity, 0, 15);
//
//    Fluid fluid = new Fluid(registerName);
//
//    fluid.setLuminosity(data.luminosity);
//    fluid.setDensity(data.density);
//    fluid.setTemperature(data.temperature);
//    fluid.setViscosity(data.viscosity);
//    fluid.setGaseous(data.isGas);
//
//    FluidRegistry.registerFluid(fluid);
//
//    Material material;
//    if (data.material.equals("lava")) {
//      material = Material.lava;
//    } else {
//      material = Material.water;
//    }
//
//    CustomFluidBlock fluidBlock = new CustomFluidBlock(fluid, material);
//
//    fluidBlock.setQuantaPerBlock(data.flowLength);
//
//    fluidBlock.setBlockTextureName(data.textureName);
//
//    Registry.blocksList.add(fluidBlock);
//    Registry.blocksList.add(data.creativeTab);
//
//    fluidBlock.setBlockName(Registry.mod_id.toLowerCase() + ":" + data.name);
//    GameRegistry.registerBlock(fluidBlock, registerName);
//
//    fluid.setUnlocalizedName(
//        fluidBlock.getUnlocalizedName().substring(fluidBlock.getUnlocalizedName().indexOf(":") + 1));
//    LanguageRegistry.instance().addStringLocalization(fluidBlock.getUnlocalizedName() + ".name", "en_US", data.name);
//    LanguageRegistry.instance().addStringLocalization(fluid.getUnlocalizedName(), "en_US", data.name);
//    fluid.setBlock(fluidBlock);
//
//    if (data.bucket.name == null) {
//      data.bucket.name = data.name + " Bucket";
//    }
//
//    String BucketRegisterName = Util.parseRegisterName(data.bucket.name);
//
//    if (data.bucket.creativeTab == null) {
//      data.bucket.creativeTab = data.creativeTab;
//    }
//
//    if (data.bucket.textureName == null) {
//      data.bucket.textureName = data.textureName + "_bucket";
//    }
//
//    CustomBucket bucket = new CustomBucket(fluidBlock, data.bucket.textureName);
//
//    bucket.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + data.bucket.name);
//    bucket.setContainerItem(Items.bucket);
//
//    Registry.itemsList.add(bucket);
//    Registry.itemsList.add(data.bucket.creativeTab);
//
//    bucket.setTextureName(data.bucket.textureName);
//    GameRegistry.registerItem(bucket, BucketRegisterName);
//
//    FluidContainerRegistry.registerFluidContainer(
//        FluidRegistry.getFluidStack(fluid.getName(), FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(bucket),
//        new ItemStack(Items.bucket));
//    LanguageRegistry.instance().addStringLocalization(bucket.getUnlocalizedName() + ".name", "en_US", data.bucket.name);
//    BucketHandler.INSTANCE.buckets.put(fluidBlock, bucket);
//    MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
//
//    return true;
//  }
//
//  public static boolean registerFluid(Cfg_fluid[] data) {
//    int i;
//
//    for (i = 0; i < data.length; i++) {
//      boolean registered = registerFluid(data[i]);
//
//      if (!registered) {
//        LogHelper.error("Failed to register: Fluid " + data[i].name);
//        return false;
//      }
//    }
//
//    return true;
//  }
}
