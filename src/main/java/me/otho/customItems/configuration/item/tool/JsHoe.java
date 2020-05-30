package me.otho.customItems.configuration.item.tool;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.HoeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class JsHoe extends JsToolBase {
	@Override
	public Item construct() {
		final boolean glows = this.glows;

		Item item = new HoeItem(this.itemTier(), attackSpeed, this.itemProp()) {
			@Override
			public boolean hasEffect(ItemStack stack) {
				if (glows)
					return true;
				return super.hasEffect(stack);
			}

			@Override
			public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
				Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot);
				if (equipmentSlot == EquipmentSlotType.MAINHAND) {
					multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(),
							new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", attackDamage, AttributeModifier.Operation.ADDITION));
					multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(),
							new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", (double) attackSpeed, AttributeModifier.Operation.ADDITION));
				}

				return multimap;
			}
		};

		item.setRegistryName(this.getRegistryName());

		return item;
	}
}