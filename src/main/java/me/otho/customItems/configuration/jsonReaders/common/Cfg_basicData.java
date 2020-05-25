package me.otho.customItems.configuration.jsonReaders.common;

import me.otho.customItems.CustomItems;
import me.otho.customItems.mod.creativeTab.CustomTab;
import me.otho.customItems.utility.Util;
import net.minecraft.item.Item;

public class Cfg_basicData implements IRegistrable {
  /**
   * name is the default localized name for the item/block
   * If no id is passed name will also be used for identification
   */
  private String name;
  /**
   * textureName is the name of the image file of the item/block to be
   * registered
   */
  public String textureName;
  /**
   * creativeTab is the identifier of the creativeTab that the item/block will
   * be put on
   */
  public String creativeTab = CustomItems.MOD_ID;
  /**
   * registerOrder is a variable to order the items/blocks before being
   * registered. This should order items in creativetabs and NEI
   */
  public Integer registerOrder = 0;
  
  public int maxStackSize = 64;
  
	public Item.Properties itemProp() {
		Item.Properties itemProp = new Item.Properties();
		itemProp.maxStackSize(Util.range(this.maxStackSize, 1, 64));
		itemProp.group(CustomTab.getTabByName(this.creativeTab));
		return itemProp;
	}
  
	private String registryName = null;

	@Override
	public String getFriendlyName() {
		return this.name;
	}

	@Override
	public String getRegistryNameRaw() {
		return this.registryName;
	}

	@Override
	public void setRegistryName(String newRegName) {
		this.registryName = newRegName;
	}

	@Override
	public String toString() {
		return this.getFriendlyName() + "(" + this.getRegistryName() + ")";
	}
}
