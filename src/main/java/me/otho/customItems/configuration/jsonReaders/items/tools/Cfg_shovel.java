package me.otho.customItems.configuration.jsonReaders.items.tools;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;

public class Cfg_shovel extends Cfg_basicTool {
	@Override
	public Item construct() {
		final boolean glows = this.glows;
		
		Item item = new ShovelItem(this.itemTier(), attackDamage, attackSpeed, this.itemProp()) {
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