package me.otho.customItems;

import java.util.function.Function;

import org.apache.commons.lang3.tuple.Triple;

import me.otho.customItems.configuration.jsonReaders.blocks.Cfg_block.LootType;
import me.otho.customItems.registry.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.StandaloneLootEntry;
import net.minecraft.world.storage.loot.functions.ApplyBonus;
import net.minecraft.world.storage.loot.functions.SetCount;

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
				Function<StandaloneLootEntry.Builder<?>, StandaloneLootEntry.Builder<?>> bonus = (builder)->builder;
				if (data.minItemDrop > 1 && data.maxItemDrop > 1) {
					bonus = bonus.andThen(
							(entry)->entry.acceptFunction(SetCount.builder(RandomValueRange.of(data.minItemDrop, data.maxItemDrop)))
						);
				}
				
				if (data.eachExtraItemDropChance > 0) {
					if (data.eachExtraItemDropChance > 99)
						data.eachExtraItemDropChance = 99;
					bonus = bonus.andThen(
							(entry)->entry.acceptFunction(ApplyBonus.binomialWithBonusCount(Enchantments.FORTUNE, data.eachExtraItemDropChance / 100.0F, 1))
						);					
				}
				
				lootTables.put(block, ore(silkTouchDrop, normalDrop, bonus));
			}
		});
	}
}
