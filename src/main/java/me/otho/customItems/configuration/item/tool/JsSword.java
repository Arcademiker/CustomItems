package me.otho.customItems.configuration.item.tool;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class JsSword extends JsToolBase {
	@Override
	public Item construct() {
		final boolean glows = this.glows;
		
		Item item = new SwordItem(this.itemTier(), attackDamage, attackSpeed, this.itemProp()) {
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