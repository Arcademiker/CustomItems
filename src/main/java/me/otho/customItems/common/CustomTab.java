package me.otho.customItems.common;

import java.util.HashMap;

import java.util.Map;
import java.util.function.Consumer;

import me.otho.customItems.CustomItems;
import me.otho.customItems.client.data.LocalizationGenerator;
import me.otho.customItems.configuration.ForgeConfig;
import me.otho.customItems.configuration.common.IRegistrable;
import me.otho.customItems.configuration.common.JsItemGroup;
import me.otho.customItems.utility.Util;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class CustomTab extends ItemGroup {
	public ItemStack iconItem = null;
	public final String iconName;
	public final String labelName;	// [a-z_0-9]*
	
	// registryName - ItemGroup instance
	private final static Map<String, CustomTab> customTabs = new HashMap<>();
	
	public static void createDefaultTab() {
		if (ForgeConfig.defaultTab.get()) {
			new CustomTab(new ItemStack(Items.ITEM_FRAME), CustomItems.MOD_ID);
		}
	}

	/**
	 * @param iconItem The icon, must not be null.
	 * @param labelName [a-z_0-9]
	 */
	private CustomTab(ItemStack iconItem, String labelName) {
		super(labelName);
		this.labelName = labelName;
		this.iconName = null;
		customTabs.put(labelName, this);
		this.iconItem = iconItem;
		
		// Localization
		LocalizationGenerator.put(this, labelName, "CustomItems");
	}
	
	public CustomTab(JsItemGroup data) {
		super(data.getRegistryName());
		this.labelName = data.getRegistryName();
		this.iconName = data.iconItem;
		customTabs.put(this.labelName, this);
		
		// Localization
		LocalizationGenerator.put(this, labelName, data.getFriendlyName());
	}

	@Override
	public ItemStack createIcon() {
		if (this.iconItem == null) {
			String[] itemName = this.iconName.split(":");
			Item iconItem = Util.get(Item.class, itemName[0], itemName[1]);
			
			this.iconItem = new ItemStack(iconItem);
		}
		return this.iconItem;
	}

	public static ItemGroup getTabByName(String label) {
		if (customTabs.containsKey(label))
			return customTabs.get(label);
		
		// For backward compatibility
		for (String key: customTabs.keySet()) {
			if (IRegistrable.genRegistryName(label).equals(key))
				return customTabs.get(key);
		}
		return null;
	}
	
	public static void foreachTabs(Consumer<? super ItemGroup> action) {
		customTabs.values().forEach(action);;
	}
}
