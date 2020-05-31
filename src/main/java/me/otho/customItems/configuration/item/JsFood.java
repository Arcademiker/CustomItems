package me.otho.customItems.configuration.item;

import me.otho.customItems.configuration.common.JsPotionEffect;
import me.otho.customItems.utility.Util;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.UseAction;
import net.minecraft.util.LazyValue;
import net.minecraft.world.World;

public class JsFood extends JsItemBase {
	protected int healAmount = 1;
	protected float saturationModifier = 1;
	protected boolean alwaysEdible = false;
	protected boolean isWolfFood = false;	// TODO: isWolfFood should be renamed to isMeat
	protected boolean fastToEat = false;
	protected String useAction = "eat";
	protected String dropItemName = null;

	protected JsPotionEffect[] potionEffects;

	@Override
	public Item.Properties itemProp() {
		Item.Properties itemProp = super.itemProp();

		Food.Builder foodDefBuilder = (new Food.Builder())
				.hunger(healAmount)
				.saturation(saturationModifier)
				.setAlwaysEdible();

		if (this.alwaysEdible)
			foodDefBuilder.setAlwaysEdible();
		
		if (this.potionEffects != null) {
			for (JsPotionEffect effectData: this.potionEffects) {
				effectData.addEffectToFood(foodDefBuilder);
			}
		}
		
		if (this.isWolfFood)
			foodDefBuilder.meat();
		
		if (this.fastToEat)
			foodDefBuilder.fastToEat();

		itemProp.food(foodDefBuilder.build());

		return itemProp;
	}
	
	protected UseAction getUseAction() {
		for (UseAction action: UseAction.values()) {
			if (useAction.toUpperCase().equals(action.name()))
				return action;
		}
		return UseAction.EAT;
	}

	@Override
	public Item construct() {
		final boolean glows = this.glows;
		final UseAction useAction = this.getUseAction();
		final LazyValue<Item> itemReturnSupplier = new LazyValue<>(()->Util.parseDropItem(this.dropItemName));
		Item item = new Item(this.itemProp()) {
			@Override
			public boolean hasEffect(ItemStack stack) {
				if (glows)
					return true;
				return super.hasEffect(stack);
			}
			
			@Override
			public UseAction getUseAction(ItemStack stack) {
				return stack.getItem().isFood() ? useAction : UseAction.NONE;
			}
			
			@Override
			public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
				ItemStack itemstack = super.onItemUseFinish(stack, worldIn, entityLiving);
				
				Item itemReturn = itemReturnSupplier.getValue();
				if (itemReturn != Items.AIR && entityLiving instanceof PlayerEntity) {
					ItemStack itemStackReturn = new ItemStack(itemReturn);
					boolean added = ((PlayerEntity) entityLiving).inventory.addItemStackToInventory(itemStackReturn);
					if (!added && !worldIn.isRemote) {
						((PlayerEntity) entityLiving).dropItem(itemStackReturn, true, false);
					}
				}
				return itemstack;
			}
		};

		item.setRegistryName(this.getRegistryName());

		return item;
	}
}