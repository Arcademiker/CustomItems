package me.otho.customItems.configuration.item.armor;

import net.minecraft.inventory.EquipmentSlotType;

public class JsChestplate extends JsArmorBase {
	@Override
	protected EquipmentSlotType slotType() {
		return EquipmentSlotType.CHEST;
	}
}