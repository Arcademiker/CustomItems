package me.otho.customItems.registry;

import me.otho.customItems.common.CustomTab;
import me.otho.customItems.common.LogHelper;
import me.otho.customItems.configuration.common.JsItemGroup;

public class CommonRegistry {
	// Common
	public static void registerCreativeTabs(JsItemGroup[] dataList) {
		if (dataList == null)
			return;

		LogHelper.info("Registering Creative tabs: ", 0);
		
		for (JsItemGroup data: dataList) {
			LogHelper.info("Registering Creative Tab: " + data.tabLabel, 1);
			new CustomTab(data);
		}

		LogHelper.finishSection();
	}
}
