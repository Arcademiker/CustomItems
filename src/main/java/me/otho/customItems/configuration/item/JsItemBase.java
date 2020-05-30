package me.otho.customItems.configuration.item;

import me.otho.customItems.configuration.common.IReloadable;
import me.otho.customItems.configuration.common.JsRegistriableBase;
import me.otho.customItems.utility.Util;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public abstract class JsItemBase extends JsRegistriableBase implements IReloadable<JsItemBase> {
	public Boolean glows = false;
	
	public abstract Item construct();
	
	public ResourceLocation getTextureResLoc() {
		return Util.resLoc("item", this.getDefaultTextureName());
	}
	
	@Override
	public void onJsonReload(JsItemBase newVal) {
		this.glows = newVal.glows;
		this.textureName = newVal.textureName;
	}
}
