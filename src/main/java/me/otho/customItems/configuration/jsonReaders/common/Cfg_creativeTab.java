package me.otho.customItems.configuration.jsonReaders.common;

public class Cfg_creativeTab implements IRegistrable {
  public String tabLabel;
  public String iconItem = "minecraft:item_frame";

  private String registryName = null;
  
@Override
public String getFriendlyName() {
	return tabLabel;
}

@Override
public String getRegistryNameRaw() {
	return registryName;
}

@Override
public void setRegistryName(String newRegName) {
	this.registryName = newRegName;
}
}
