package me.otho.customItems;

import org.apache.commons.lang3.tuple.Triple;

import me.otho.customItems.configuration.jsonReaders.blocks.Cfg_block.LootType;
import me.otho.customItems.registry.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.functions.ApplyBonus;

public class LootTableDataProvider extends LootTableProviderBase {
	public LootTableDataProvider(DataGenerator dataGeneratorIn, Object dummy) {
		super(dataGeneratorIn, "CustomItems Loot Table Provider");
	}

	@Override
	protected void addTables() {
		BlockRegistry.foreachBlock((block, data) -> {
			Triple<LootType, Item, Item> lootInfo = data.getDrops();
			LootType lootType = lootInfo.getLeft();
			Item normalDrop = lootInfo.getMiddle();
			Item silkTouchDrop = lootInfo.getRight();
			if (lootType == LootType.ALWAYS_BLOCK || lootType == LootType.ALWAYS_ITEM) {
				lootTables.put(block, alwaysDrop(normalDrop));
			} else if (lootType == LootType.GLASS) {
				lootTables.put(block, glass(silkTouchDrop));
			} else if (lootType == LootType.COAL_ORE) {				
				lootTables.put(block, ore(silkTouchDrop, normalDrop, data.minItemDrop, data.maxItemDrop,
						(builder)->builder
//							builder.acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, 0, 1))
										)
							);
			}
		});
	}
}
