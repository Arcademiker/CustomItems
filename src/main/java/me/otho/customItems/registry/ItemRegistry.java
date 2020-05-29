package me.otho.customItems.registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import me.otho.customItems.LocalizationGenerator;
import me.otho.customItems.configuration.JsonConfigurationHandler;
import me.otho.customItems.configuration.jsonReaders.items.JSItemBase;
import me.otho.customItems.utility.LogHelper;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.TieredItem;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemRegistry {
	// Values only available during start-up
	private final static Map<Item, JSItemBase> items = new HashMap<>();
	
	public static void registerItems(IForgeRegistry<Item> registry) {
		initItems(registry, JsonConfigurationHandler.allData.items);
		initItems(registry, JsonConfigurationHandler.allData.foods);
		initItems(registry, JsonConfigurationHandler.allData.axes);
		initItems(registry, JsonConfigurationHandler.allData.hoes);
		initItems(registry, JsonConfigurationHandler.allData.pickaxes);
		initItems(registry, JsonConfigurationHandler.allData.shovels);
		initItems(registry, JsonConfigurationHandler.allData.swords);
		initItems(registry, JsonConfigurationHandler.allData.helmets);
		initItems(registry, JsonConfigurationHandler.allData.chestplates);
		initItems(registry, JsonConfigurationHandler.allData.leggings);
		initItems(registry, JsonConfigurationHandler.allData.boots);
		
		items.entrySet().stream().sorted(
				(entry1, entry2) -> 
				Integer.valueOf(entry1.getValue().registerOrder).compareTo(entry2.getValue().registerOrder)
			)
			.forEach((entry)->registry.register(entry.getKey()));
	}
	
	public static void initItems(IForgeRegistry<Item> registry, JSItemBase[] dataList) {	
		if (dataList == null)
			return;
		
		for (JSItemBase data: dataList) {
			LogHelper.info("Instantiating Item: " + data.toString(), 1);
			Item item = data.construct();
			
			// Localization
			LocalizationGenerator.put(item, data.getFriendlyName());

			if (item == null) {
				LogHelper.error("Failed to instante: Item " + data.getFriendlyName());
			} else {
				items.put(item, data);
			}
		}
	}
	
	public static void postRegistraion() {
		foreachItem((item) -> {
			if (item instanceof TieredItem) {
				((TieredItem) item).getTier().getRepairMaterial();
			} else if (item instanceof ArmorItem) {
				((ArmorItem) item).getArmorMaterial().getRepairMaterial();
				((ArmorItem) item).getArmorMaterial().getSoundEvent();
			}
		});
	}
	
	public static void foreachItem(Consumer<? super Item> consumer) {
		items.keySet().forEach(consumer);
	}
	
	public static void foreachItem(BiConsumer<? super Item, ? super JSItemBase> consumer) {
		items.forEach(consumer);
	}
}
