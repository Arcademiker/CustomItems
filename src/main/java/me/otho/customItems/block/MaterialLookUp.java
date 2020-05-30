package me.otho.customItems.block;

import java.util.HashMap;
import java.util.Map;

import me.otho.customItems.common.LogHelper;
import net.minecraft.block.material.Material;

public class MaterialLookUp {
	private final static Map<String, Material> materials = new HashMap<>();
	public final static Material defaultVal = Material.ROCK;
	static {
		materials.put("air", Material.AIR);
		materials.put("structured_void", Material.STRUCTURE_VOID);
		materials.put("portal", Material.PORTAL);
		materials.put("carpet", Material.CARPET);
		materials.put("ocean_plant", Material.OCEAN_PLANT);
		materials.put("plants", Material.PLANTS);
		materials.put("tall_plants", Material.TALL_PLANTS);
		materials.put("sea_grass", Material.SEA_GRASS);
		materials.put("water", Material.WATER);
		materials.put("tall_plants", Material.TALL_PLANTS);
		materials.put("bubble_column", Material.BUBBLE_COLUMN);
		materials.put("lava", Material.LAVA);
		materials.put("snow", Material.SNOW);
		materials.put("fire", Material.FIRE);
		materials.put("miscellaneous", Material.MISCELLANEOUS);
		materials.put("web", Material.WEB);
		materials.put("redstone_light", Material.REDSTONE_LIGHT);
		materials.put("clay", Material.CLAY);
		materials.put("earth", Material.EARTH);
		materials.put("organic", Material.ORGANIC);
		materials.put("packed_ice", Material.PACKED_ICE);
		materials.put("sand", Material.SAND);
		materials.put("sponge", Material.SPONGE);
		materials.put("shulker", Material.SHULKER);
		materials.put("wood", Material.WOOD);
		materials.put("bamboo_sapling", Material.BAMBOO_SAPLING);
		materials.put("bamboo", Material.BAMBOO);
		materials.put("wool", Material.WOOL);
		materials.put("tnt", Material.TNT);
		materials.put("leaves", Material.LEAVES);
		materials.put("glass", Material.GLASS);
		materials.put("ice", Material.ICE);
		materials.put("cactus", Material.CACTUS);
		materials.put("rock", Material.ROCK);
		materials.put("iron", Material.IRON);
		materials.put("snow_block", Material.SNOW_BLOCK);
		materials.put("anvil", Material.ANVIL);
		materials.put("barrier", Material.BARRIER);
		materials.put("piston", Material.PISTON);
		materials.put("coral", Material.CORAL);
		materials.put("gourd", Material.GOURD);
		materials.put("dragonegg", Material.DRAGON_EGG);
		materials.put("cake", Material.CAKE);

		// For backward compatibility
		materials.put("plant", Material.PLANTS);
		materials.put("ocean_plants", Material.OCEAN_PLANT);
		materials.put("tall_plant", Material.TALL_PLANTS);

		materials.put("circuits", Material.MISCELLANEOUS);
		materials.put("craftedsnow", Material.SNOW_BLOCK);
		materials.put("packedice", Material.PACKED_ICE);
		materials.put("redstonelight", Material.REDSTONE_LIGHT);
		materials.put("vine", Material.TALL_PLANTS);
	}

	public static Material get(String materialName) {
		String key = materialName.toLowerCase();
		if (materials.containsKey(key)) {
			return materials.get(key);
		} else {
			LogHelper.warn("Unable to find block material: " + materialName + "(" + key + ")");
			return defaultVal;
		}
	}
}