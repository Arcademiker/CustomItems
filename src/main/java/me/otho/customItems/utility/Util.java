package me.otho.customItems.utility;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Util {
	@Nullable
	public static <T extends IForgeRegistryEntry<T>> T get(Class<T> cls, String res) {
		ResourceLocation resLoc = new ResourceLocation(res);
		IForgeRegistry<T> registry = GameRegistry.findRegistry(cls);
		return registry.containsKey(resLoc) ? registry.getValue(resLoc) : null;
	}
	
	@Nullable
	public static <T extends IForgeRegistryEntry<T>> T get(Class<T> cls, String modid, String name) {
		ResourceLocation resLoc = new ResourceLocation(modid, name);
		IForgeRegistry<T> registry = GameRegistry.findRegistry(cls);
		return registry.containsKey(resLoc) ? registry.getValue(resLoc) : null;
	}

  public static float range(float var, float min, float max) {
    if (var < min) {
      var = min;
    }
    if (var > max) {
      var = max;
    }
    return var;
  }

  public static int range(int var, int min, int max) {
    if (var < min) {
      var = min;
    }
    if (var > max) {
      var = max;
    }
    return var;
  }

  public static SoundType parseSoundType(String stepSound) {
    if (stepSound.equals("anvil")) {
      return SoundType.ANVIL;
    } else if (stepSound.equals("cloth")) {
      return SoundType.CLOTH;
    } else if (stepSound.equals("glass")) {
      return SoundType.GLASS;
    } else if (stepSound.equals("grass")) {
      return SoundType.WET_GRASS;// TODO: check this
    } else if (stepSound.equals("gravel")) {
      return SoundType.SAND;	// TODO: check this
    } else if (stepSound.equals("ladder")) {
      return SoundType.LADDER;
    } else if (stepSound.equals("metal")) {
      return SoundType.METAL;
    } else if (stepSound.equals("piston")) {
      return SoundType.METAL;	// TODO: check this
    } else if (stepSound.equals("sand")) {
      return SoundType.SAND;
    } else if (stepSound.equals("snow")) {
      return SoundType.SNOW;
    } else if (stepSound.equals("stone")) {
      return SoundType.STONE;
    } else if (stepSound.equals("wood")) {
      return SoundType.WOOD;
    } else {
      return SoundType.STONE;
    }
  }

  public static String validateToolClass(String toolClass) {
    if (toolClass == null)
      return null;
    return ToolType.get(toolClass)!=null ? toolClass : null;
  }

}
