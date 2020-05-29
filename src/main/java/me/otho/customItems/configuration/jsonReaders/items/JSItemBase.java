package me.otho.customItems.configuration.jsonReaders.items;

import me.otho.customItems.CustomItems;
import me.otho.customItems.configuration.jsonReaders.common.Cfg_basicData;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class JSItemBase extends Cfg_basicData {
	public Boolean glows = false;
	
	public abstract Item construct();
	
	public ResourceLocation getTextureResLoc() {
		String textureStr = textureName;
		String path = "item/"+textureStr;
		return textureStr.contains(":") ? 
				new ResourceLocation(path) : 
				new ResourceLocation(CustomItems.MOD_ID, path);
	}
}
