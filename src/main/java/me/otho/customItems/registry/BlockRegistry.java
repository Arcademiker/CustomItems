package me.otho.customItems.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import me.otho.customItems.LocalizationGenerator;
import me.otho.customItems.configuration.JsonConfigurationHandler;
import me.otho.customItems.configuration.jsonReaders.blocks.Cfg_block;
import me.otho.customItems.mod.blocks.BlockType;
import me.otho.customItems.mod.blocks.IBlockItemProvider;
//import me.otho.customItems.integration.Integration;
//import me.otho.customItems.integration.NEICustomItemsConfig;
import me.otho.customItems.utility.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.IForgeRegistry;

public class BlockRegistry {
	// Only available during start-up
	private static Map<Block, Cfg_block> blocks = new HashMap<>();

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
		foreachBlock((block)->registry.register(block));
	}
	
	public static void registerBlockItems(IForgeRegistry<Item> registry) {
		foreachBlock((block)-> {
			if (block instanceof IBlockItemProvider) {
				registry.register(block.asItem());
			}
		});
	}
	
	public static void foreachBlock(Consumer<? super Block> consumer) {
		blocks.keySet().forEach(consumer);
	}
	
	public static void foreachBlock(BiConsumer<? super Block, ? super Cfg_block> consumer) {
		blocks.forEach(consumer);
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
			blocks.put(block, data);
		}
		return true;
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
