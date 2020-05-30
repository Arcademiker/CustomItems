package me.otho.customItems.configuration.item;

import me.otho.customItems.configuration.common.JsRegistriableBase;
import me.otho.customItems.utility.Util;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class JsItemBase extends JsRegistriableBase {
	public Boolean glows = false;
	
	public abstract Item construct();
	
	public ResourceLocation getTextureResLoc() {
		return Util.resLoc(this.getDefaultTextureName());
	}
}
