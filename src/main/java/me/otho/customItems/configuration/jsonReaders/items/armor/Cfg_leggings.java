package me.otho.customItems.configuration.jsonReaders.items.armor;

import net.minecraft.inventory.EquipmentSlotType;

public class Cfg_leggings extends JsArmorBase {
	@Override
	protected EquipmentSlotType slotType() {
		return EquipmentSlotType.LEGS;
	}
}