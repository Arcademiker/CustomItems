package me.otho.customItems.mod.materials;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.material.Material;

public class CI_Material {
	private final static Map<String, Material> materials = new HashMap<>();
	static {
		materials.put("air", Material.AIR);
		materials.put("anvil", Material.ANVIL);
		materials.put("cactus", Material.CACTUS);
		materials.put("cake", Material.CAKE);
		materials.put("carpet", Material.CARPET);
		materials.put("clay", Material.CLAY);
		materials.put("coral", Material.CORAL);
		materials.put("dragonegg", Material.DRAGON_EGG);
		materials.put("fire", Material.FIRE);
		materials.put("glass", Material.GLASS);
		materials.put("gourd", Material.GOURD);
		
		materials.put("stone", Material.ROCK);
		materials.put("iron", Material.IRON);
		materials.put("stone", Material.ROCK);
		materials.put("rock", Material.ROCK);
		materials.put("wood", Material.WOOD);
		materials.put("web", Material.WEB);
		materials.put("water", Material.WATER);
	}
	
	
  public static Material getMaterial(String material) {
	  return materials.containsKey(material.toLowerCase()) ? materials.get(material.toLowerCase()) : Material.ROCK;

	  // TODO: Add all materials
	  //	  
//	  if (material.equals("circuits")) {
//      return Material.MISCELLANEOUS;	// TODO: Check this
//    } else if (material.equals("cloth")) {
//      return Material.cloth;
//    } else if (material.equals("craftedSnow")) {
//      return Material.SNOW_BLOCK;		// TODO: Check this
//    } else if (material.equals("grass")) {
//      return Material.grass;
//    } else if (material.equals("ground")) {
//      return Material.ground;
//    } else if (material.equals("ice")) {
//      return Material.ice;
//    } else if (material.equals("lava")) {
//      return Material.lava;
//    } else if (material.equals("leaves")) {
//      return Material.leaves;
//    } else if (material.equals("packedIce")) {
//      return Material.packedIce;
//    } else if (material.equals("piston")) {
//      return Material.piston;
//    } else if (material.equals("plants")) {
//      return Material.plants;
//    } else if (material.equals("portal")) {
//      return Material.portal;
//    } else if (material.equals("redstoneLight")) {
//      return Material.redstoneLight;
//    } else if (material.equals("sand")) {
//      return Material.sand;
//    } else if (material.equals("snow")) {
//      return Material.snow;
//    } else if (material.equals("sponge")) {
//      return Material.sponge;
//    } else if (material.equals("tnt")) {
//      return Material.tnt;
//    } else if (material.equals("vine")) {
//      return Material.vine;
//    } else if (material.equals("water")) {
//      return Material.water;
//    } else if (material.equals("web")) {
//      return Material.web;
//    } else if (material.equals("wood")) {
//      return Material.wood;
//    } else {
//      return Material.rock;
//    }
  }
}