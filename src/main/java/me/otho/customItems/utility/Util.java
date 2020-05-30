package me.otho.customItems.utility;

import javax.annotation.Nullable;

import net.minecraft.util.ResourceLocation;
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

	public static int range(int var, int min, int max) {
		if (var < min)
			var = min;

		if (var > max)
			var = max;

		return var;
	}
}
