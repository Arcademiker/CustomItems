package me.otho.customItems.configuration.jsonReaders.items.tools;

import java.util.function.Supplier;

import com.google.gson.JsonElement;

import me.otho.customItems.configuration.jsonReaders.items.JSItemBase;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

public class Cfg_basicTool extends JSItemBase {
	/**
	 * harvestLevel is the level of "hardness" that this tool can mine
	 */
	protected int harvestLevel = 0;
	/**
	 * maxUses is the amount of times that this tool can be used to mine or hit
	 * entities
	 */
	protected int maxUses = 59;
	/**
	 * efficiencyOnProperMaterial is how fast can this tool mine the right blocks
	 */
	protected float efficiencyOnProperMaterial = 2.0f;
	/**
	 * damageVsEntity is how much damage this does causes
	 */
	protected float damageVsEntity = 0.0f;
	/**
	 * enchantability is how easy can this tool be enchanted
	 */
	protected int enchantability = 15;

	protected int attackDamage = 9;
	protected float attackSpeed = -2.4F;
	private JsonElement repairMaterial = null;

	public static class ItemTier implements IItemTier {
		private final int harvestLevel;
		private final int maxUses;
		private final float efficiency;
		private final float attackDamage;
		private final int enchantability;
		private final LazyValue<Ingredient> repairMaterial;

		private ItemTier(int harvestLevelIn, int maxUsesIn, float efficiencyIn, float attackDamageIn,
				int enchantabilityIn, Supplier<Ingredient> repairMaterialIn) {
			this.harvestLevel = harvestLevelIn;
			this.maxUses = maxUsesIn;
			this.efficiency = efficiencyIn;
			this.attackDamage = attackDamageIn;
			this.enchantability = enchantabilityIn;
			this.repairMaterial = new LazyValue<>(repairMaterialIn);
		}

		public int getMaxUses() {
			return this.maxUses;
		}

		public float getEfficiency() {
			return this.efficiency;
		}

		public float getAttackDamage() {
			return this.attackDamage;
		}

		public int getHarvestLevel() {
			return this.harvestLevel;
		}

		public int getEnchantability() {
			return this.enchantability;
		}

		public Ingredient getRepairMaterial() {
			return this.repairMaterial.getValue();
		}

	}

	protected IItemTier itemTier() {
		return new ItemTier(this.harvestLevel, this.maxUses, this.efficiencyOnProperMaterial, this.damageVsEntity,
				this.enchantability, new Supplier<Ingredient>() {
					private Ingredient cache = null;

					@Override
					public Ingredient get() {
						if (cache == null) {
							if (repairMaterial == null)
								cache = Ingredient.fromItems(Items.AIR);
							else
								cache = Ingredient.deserialize(repairMaterial);
						}

						return cache;
					}

				});
	}

	@Override
	public Item construct() {
		return null;
	}
}
