package me.otho.customItems.configuration.jsonReaders.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Cfg_item extends JSItemBase {
	public Item construct() {
		final boolean glows = this.glows;
		
		Item item = new Item(this.itemProp()) {
			@Override
			public boolean hasEffect(ItemStack stack) {
				if (glows)
					return true;
				return super.hasEffect(stack);
			}
		};
		
		item.setRegistryName(this.getRegistryName());

		return item;
	}
}