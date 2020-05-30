package me.otho.customItems.configuration.item.armor;

import java.util.function.Supplier;

import com.google.gson.JsonElement;

import me.otho.customItems.CustomItems;
import me.otho.customItems.configuration.item.JsItemBase;
import me.otho.customItems.utility.Util;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class JsArmorBase extends JsItemBase {
	protected int durability = 5;
	protected int reduction = 2;
	protected int enchantability = 15;
	protected float toughness = 0;
	protected String equipSound = null;
	protected JsonElement repairMaterial = null;
	
	protected abstract EquipmentSlotType slotType();

	protected IArmorMaterial armorMaterial() {		
		final String equipSound = this.equipSound;
		final JsonElement repairMaterial = this.repairMaterial;
		return new ArmorMaterial(textureName, durability, reduction, enchantability, toughness, 
				() -> {
					SoundEvent sound = null;
					if (equipSound != null)
						sound = Util.get(SoundEvent.class, equipSound);
					
					if (sound == null)
						sound = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
					
					return sound;
				},
				()->{
					if (repairMaterial == null)
						return Ingredient.fromItems(Items.AIR);
					else
						return Ingredient.deserialize(repairMaterial);
				}
		);
	}

	public static class ArmorMaterial implements IArmorMaterial {
		private final String name;
		private final int durability;
		private final int reduction;
		private final int enchantability;
		private final LazyValue<SoundEvent> soundEvent;
		private final LazyValue<Ingredient> repairMaterial;
		private final float toughness;

		private ArmorMaterial(String name, int durability, int reduction, int enchantability, float toughness,
				Supplier<SoundEvent> soundEvent, Supplier<Ingredient> repairMaterial) {
			this.name = name;
			this.durability = durability;
			this.reduction = reduction;
			this.enchantability = enchantability;
			this.toughness = toughness;
			this.soundEvent = new LazyValue<>(soundEvent);
			this.repairMaterial = new LazyValue<>(repairMaterial);
		}
		
		@Override
		public int getDurability(EquipmentSlotType slotIn) {
			return this.durability;
		}

		@Override
		public int getDamageReductionAmount(EquipmentSlotType slotIn) {
			return this.reduction;
		}

		public int getEnchantability() {
			return this.enchantability;
		}

		public SoundEvent getSoundEvent() {
			return this.soundEvent.getValue();
		}

		public Ingredient getRepairMaterial() {
			return this.repairMaterial.getValue();
		}

		@OnlyIn(Dist.CLIENT)
		public String getName() {
			return this.name;
		}

		public float getToughness() {
			return this.toughness;
		}
	}

	@Override
	public Item construct() {
		final boolean glows = this.glows;
		final String texture = this.textureName;

		Item item = new ArmorItem(armorMaterial(), slotType(), this.itemProp()) {
			@Override
			public boolean hasEffect(ItemStack stack) {
				if (glows)
					return true;
				return super.hasEffect(stack);
			}
			
			@Override
			public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
		        String domain = CustomItems.MOD_ID;
				String resLoc = String.format("%s:textures/models/armor/%s_layer_%d%s.png", 
						domain, 
						texture, 
						(slot==EquipmentSlotType.LEGS? 2 : 1), 
						type==null ? "" : String.format("_%s", type));
				return resLoc;
		    }
		};

		item.setRegistryName(this.getRegistryName());

		return item;
	}
}
