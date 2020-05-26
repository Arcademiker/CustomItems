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
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemRegistry {
	private static Map<Item, ResourceLocation> items = new HashMap<>();
	
	public static void registerItems(IForgeRegistry<Item> registry) {
		registerItems(registry, JsonConfigurationHandler.allData.items);
		registerItems(registry, JsonConfigurationHandler.allData.foods);
		registerItems(registry, JsonConfigurationHandler.allData.axes);
		registerItems(registry, JsonConfigurationHandler.allData.hoes);
		registerItems(registry, JsonConfigurationHandler.allData.pickaxes);
		registerItems(registry, JsonConfigurationHandler.allData.shovels);
		registerItems(registry, JsonConfigurationHandler.allData.swords);
		registerItems(registry, JsonConfigurationHandler.allData.helmets);
		registerItems(registry, JsonConfigurationHandler.allData.chestplates);
		registerItems(registry, JsonConfigurationHandler.allData.leggings);
		registerItems(registry, JsonConfigurationHandler.allData.boots);
	}
	
	public static void registerItems(IForgeRegistry<Item> registry, JSItemBase[] dataList) {	
		if (dataList == null)
			return;
		
		for (JSItemBase data: dataList) {
			LogHelper.info("Instantiating Item: " + data.toString(), 1);
			Item item = data.construct();
			
			// Localization
			LocalizationGenerator.put(item, data.getFriendlyName());

			if (item == null) {
				LogHelper.error("Failed to register: Item " + data.getFriendlyName());
			} else {
				items.put(item, data.getTextureResLoc(item));
				registry.register(item);
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
	
	public static void foreachItem(BiConsumer<? super Item, ? super ResourceLocation> consumer) {
		items.forEach(consumer);
	}

//
//  public static boolean registerBoots(Cfg_boots data) {
//    LogHelper.info("Registering Boots: " + data.name, 1);
//
//    String registerName;
//    if(data.id != null) {
//    	registerName = Util.parseRegisterName(data.id);
//    } else {
//    	registerName = Util.parseRegisterName(data.name);
//    }
//
//    // Make Custom Armor
//    int reduction[] = { 0, 0, 0, 0 };
//    reduction[3] = data.reduction;
//
//    ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial(data.textureName, data.durability, reduction,
//        data.enchantability);
//    CustomArmor armor = new CustomArmor(material, 0, 3, data.textureName, data.durability);
//    // Register Armor
//
//    Registry.itemsList.add(armor);
//    Registry.itemsList.add(data.creativeTab);
//
//    armor.setTextureName(data.textureName);
//    GameRegistry.registerItem(armor, registerName);
//    armor.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + registerName);
//    LanguageRegistry.instance().addStringLocalization(armor.getUnlocalizedName() + ".name", "en_US",
//        data.name.substring(0, 1).toUpperCase() + data.name.substring(1));
//    return false;
//  }
//
//  public static boolean registerChestplate(Cfg_chestplate data) {
//    LogHelper.info("Registering Chestplate: " + data.name, 1);
//
//    String registerName;
//    if(data.id != null) {
//    	registerName = Util.parseRegisterName(data.id);
//    } else {
//    	registerName = Util.parseRegisterName(data.name);
//    }
//
//    // Make Custom Armor
//    int reduction[] = { 0, 0, 0, 0 };
//    reduction[1] = data.reduction;
//
//    ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial(data.textureName, data.durability, reduction,
//        data.enchantability);
//    CustomArmor armor = new CustomArmor(material, 0, 1, data.textureName, data.durability);
//    // Register Armor
//
//    Registry.itemsList.add(armor);
//    Registry.itemsList.add(data.creativeTab);
//
//    armor.setTextureName(data.textureName);
//    GameRegistry.registerItem(armor, registerName);
//    armor.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + registerName);
//    LanguageRegistry.instance().addStringLocalization(armor.getUnlocalizedName() + ".name", "en_US",
//        data.name.substring(0, 1).toUpperCase() + data.name.substring(1));
//
//    return false;
//  }
//
//  public static boolean registerHelmet(Cfg_helmet data) {
//    LogHelper.info("Registering Helmet: " + data.name, 1);
//
//    String registerName;
//    if(data.id != null) {
//    	registerName = Util.parseRegisterName(data.id);
//    } else {
//    	registerName = Util.parseRegisterName(data.name);
//    }
//
//    // Make Custom Armor
//    int reduction[] = { 0, 0, 0, 0 };
//    reduction[0] = data.reduction;
//
//    ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial(data.textureName, data.durability, reduction,
//        data.enchantability);
//    CustomArmor armor = new CustomArmor(material, 0, 0, data.textureName, data.durability);
//    // Register Armor
//
//    Registry.itemsList.add(armor);
//    Registry.itemsList.add(data.creativeTab);
//
//    armor.setTextureName(data.textureName);
//    GameRegistry.registerItem(armor, registerName);
//    armor.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + registerName);
//    LanguageRegistry.instance().addStringLocalization(armor.getUnlocalizedName() + ".name", "en_US",
//        data.name.substring(0, 1).toUpperCase() + data.name.substring(1));
//
//    return false;
//  }
//
//  public static boolean registerHoe(Cfg_hoe data) {
//    LogHelper.info("Registering Hoe: " + data.name, 1);
//
//    String registerName;
//    if(data.id != null) {
//    	registerName = Util.parseRegisterName(data.id);
//    } else {
//    	registerName = Util.parseRegisterName(data.name);
//    }
//
//    Item.ToolMaterial material = EnumHelper.addToolMaterial(data.textureName, data.harvestLevel, data.maxUses,
//        data.efficiencyOnProperMaterial, data.damageVsEntity, data.enchantability);
//
//    CustomHoe hoe = new CustomHoe(material);
//
//    Registry.itemsList.add(hoe);
//    Registry.itemsList.add(data.creativeTab);
//
//    hoe.setTextureName(data.textureName);
//    GameRegistry.registerItem(hoe, registerName);
//    hoe.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + registerName);
//    LanguageRegistry.instance().addStringLocalization(hoe.getUnlocalizedName() + ".name", "en_US",
//        data.name.substring(0, 1).toUpperCase() + data.name.substring(1));
//
//    return true;
//  }
//
//  public static boolean registerItem(Cfg_item data) {
//    LogHelper.info("Registering Item: " + data.name, 1);
//
//    String registerName;
//    if(data.id != null) {
//    	registerName = Util.parseRegisterName(data.id);
//    } else {
//    	registerName = Util.parseRegisterName(data.name);
//    }
//
//    CustomItem item = new CustomItem(data.maxStackSize);
//
//    item.setGlows(data.glows);
//
//    Registry.itemsList.add(item);
//    Registry.itemsList.add(data.creativeTab);
//
//    GameRegistry.registerItem(item, registerName);
//    item.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + registerName);
//    item.setTextureName(data.textureName);
//    LanguageRegistry.instance().addStringLocalization(item.getUnlocalizedName() + ".name", "en_US", data.name);
//
//    return false;
//  }
//
//  public static boolean registerLeggings(Cfg_leggings data) {
//    LogHelper.info("Registering Leggings: " + data.name, 1);
//
//    String registerName;
//    if(data.id != null) {
//    	registerName = Util.parseRegisterName(data.id);
//    } else {
//    	registerName = Util.parseRegisterName(data.name);
//    }
//
//    // Make Custom Armor
//    int reduction[] = { 0, 0, 0, 0 };
//    reduction[2] = data.reduction;
//
//    ItemArmor.ArmorMaterial material = EnumHelper.addArmorMaterial(data.textureName, data.durability, reduction,
//        data.enchantability);
//    CustomArmor armor = new CustomArmor(material, 0, 2, data.textureName, data.durability);
//    // Register Armor
//
//    Registry.itemsList.add(armor);
//    Registry.itemsList.add(data.creativeTab);
//
//    armor.setTextureName(data.textureName);
//    GameRegistry.registerItem(armor, registerName);
//    armor.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + registerName);
//    LanguageRegistry.instance().addStringLocalization(armor.getUnlocalizedName() + ".name", "en_US",
//        data.name.substring(0, 1).toUpperCase() + data.name.substring(1));
//
//    return true;
//  }
//
//  public static boolean registerPickaxe(Cfg_pickaxe data) {
//    LogHelper.info("Registering Pickaxe: " + data.name, 1);
//
//    String registerName;
//    if(data.id != null) {
//    	registerName = Util.parseRegisterName(data.id);
//    } else {
//    	registerName = Util.parseRegisterName(data.name);
//    }
//
//    Item.ToolMaterial material = EnumHelper.addToolMaterial(data.textureName, data.harvestLevel, data.maxUses,
//        data.efficiencyOnProperMaterial, data.damageVsEntity, data.enchantability);
//
//    CustomPickaxe pickaxe = new CustomPickaxe(material);
//
//    pickaxe.setTextureName(data.textureName);
//    Registry.itemsList.add(pickaxe);
//    Registry.itemsList.add(data.creativeTab);
//
//    GameRegistry.registerItem(pickaxe, registerName);
//    pickaxe.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + registerName);
//    LanguageRegistry.instance().addStringLocalization(pickaxe.getUnlocalizedName() + ".name", "en_US",
//        data.name.substring(0, 1).toUpperCase() + data.name.substring(1));
//
//    return true;
//  }
//
//  public static boolean registerShovel(Cfg_shovel data) {
//    LogHelper.info("Registering Shovel: " + data.name, 1);
//
//    String registerName;
//    if(data.id != null) {
//    	registerName = Util.parseRegisterName(data.id);
//    } else {
//    	registerName = Util.parseRegisterName(data.name);
//    }
//
//    Item.ToolMaterial material = EnumHelper.addToolMaterial(data.textureName, data.harvestLevel, data.maxUses,
//        data.efficiencyOnProperMaterial, data.damageVsEntity, data.enchantability);
//
//    CustomShovel shovel = new CustomShovel(material);
//
//    shovel.setTextureName(data.textureName);
//    Registry.itemsList.add(shovel);
//    Registry.itemsList.add(data.creativeTab);
//
//    GameRegistry.registerItem(shovel, registerName);
//    shovel.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + registerName);
//    LanguageRegistry.instance().addStringLocalization(shovel.getUnlocalizedName() + ".name", "en_US",
//        data.name.substring(0, 1).toUpperCase() + data.name.substring(1));
//
//    return true;
//  }
//
//  public static boolean registerSword(Cfg_sword data) {
//    LogHelper.info("Registering Sword: " + data.name, 1);
//
//    String registerName;
//    if(data.id != null) {
//    	registerName = Util.parseRegisterName(data.id);
//    } else {
//    	registerName = Util.parseRegisterName(data.name);
//    }
//
//    Item.ToolMaterial material = EnumHelper.addToolMaterial(data.textureName, data.harvestLevel, data.maxUses,
//        data.efficiencyOnProperMaterial, data.damageVsEntity, data.enchantability);
//
//    CustomSword sword = new CustomSword(material);
//
//    sword.setTextureName(data.textureName);
//    Registry.itemsList.add(sword);
//    Registry.itemsList.add(data.creativeTab);
//
//    GameRegistry.registerItem(sword, registerName);
//    sword.setUnlocalizedName(Registry.mod_id.toLowerCase() + ":" + registerName);
//    LanguageRegistry.instance().addStringLocalization(sword.getUnlocalizedName() + ".name", "en_US",
//        data.name.substring(0, 1).toUpperCase() + data.name.substring(1));
//
//    return true;
//  }
}
