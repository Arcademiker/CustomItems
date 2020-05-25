package me.otho.customItems.registry;

import me.otho.customItems.configuration.jsonReaders.common.Cfg_creativeTab;
import me.otho.customItems.mod.creativeTab.CustomTab;
import me.otho.customItems.utility.LogHelper;

public class CommonRegistry {

	// Common
	public static void registerCreativeTabs(Cfg_creativeTab[] dataList) {
		if (dataList == null)
			return;

		LogHelper.info("Registering Creative tabs: ", 0);
		
		for (Cfg_creativeTab data: dataList) {
			LogHelper.info("Registering Creative Tab: " + data.tabLabel, 1);
			new CustomTab(data);
		}

		LogHelper.finishSection();
	}
}
