package me.otho.customItems.configuration.item;

import me.otho.customItems.CustomItems;
import me.otho.customItems.configuration.common.JsRegistriableBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class JsItemBase extends JsRegistriableBase {
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
