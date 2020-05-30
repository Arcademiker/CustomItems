package me.otho.customItems;

import me.otho.customItems.common.LogHelper;

public class CommonProxy {
	public void createClientResPackDirs() {}
	
	public void runClientDataGenerators() {
		LogHelper.warn("Attemp to run client data generators on a dedicated server!");
	}
}
