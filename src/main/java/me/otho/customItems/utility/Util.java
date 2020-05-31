package me.otho.customItems.utility;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import me.otho.customItems.CustomItems;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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
	
	public static ResourceLocation resLoc(String type, String textureStr) {
		int i = textureStr.indexOf(':');
		String domain = i>0 ? textureStr.substring(0, i) : CustomItems.MOD_ID;
		String path = textureStr.substring(i+1);
		
		i = textureStr.indexOf('/');
		if (i == -1)
			path = type + "/" + path;
		
		return new ResourceLocation(domain, path);
	}
	
	@Nonnull
	public static Item parseDropItem(String dropItemName) {
		if (dropItemName==null)
			return Items.AIR;

		int iSeperator = dropItemName.indexOf(':');
		String domain = iSeperator>=0 ? dropItemName.substring(0, iSeperator) : CustomItems.MOD_ID;
		String path = iSeperator>=0 ? dropItemName.substring(iSeperator+1) : dropItemName;

		Item item = Util.get(Item.class, domain, path);
		
		return item==null ? Items.AIR : item;
	}
}
