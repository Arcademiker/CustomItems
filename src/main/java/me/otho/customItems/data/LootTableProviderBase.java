package me.otho.customItems.data;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.AlternativesLootEntry;
import net.minecraft.world.storage.loot.ConstantRange;
import net.minecraft.world.storage.loot.ItemLootEntry;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraft.world.storage.loot.StandaloneLootEntry;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class LootTableProviderBase implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    // Filled by subclasses
    protected final Map<Block, LootTable.Builder> lootTables = new HashMap<>();

    protected final DataGenerator generator;
    protected final String name;

    public LootTableProviderBase(DataGenerator dataGeneratorIn, String name) {
        this.generator = dataGeneratorIn;
        this.name = name;
    }

    // Subclasses can override this to fill the 'lootTables' map.
    protected abstract void addTables();

    @Override
    // Entry point
    public void act(DirectoryCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<Block, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(
            		getLootTableResLoc(entry.getKey()), 
            		entry.getValue().setParameterSet(LootParameterSets.BLOCK).build()
            		);
        }
        writeTables(cache, tables);
    }

    // Actually write out the tables in the output folder
    private void writeTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path);
            } catch (IOException e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }

    protected ResourceLocation getLootTableResLoc(IForgeRegistryEntry<?> entryIn) {
    	return new ResourceLocation(
    			entryIn.getRegistryName().getNamespace(), 
    			"blocks/" + entryIn.getRegistryName().getPath()
    			);
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
	/////////////////////////////////////////////
	/// Utils
	/////////////////////////////////////////////
    // Subclasses can call this if they want a standard loot table. Modify this for your own needs
    public static LootTable.Builder alwaysDrop(IItemProvider item) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(item))
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(builder);
    }
    
    public static final ILootCondition.IBuilder SILK_TOUCH = MatchTool.builder(
    				ItemPredicate.Builder.create().enchantment(
    						new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1))
    									));
    public static LootTable.Builder glass(IItemProvider item) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(ItemLootEntry.builder(item))
                .acceptCondition(SILK_TOUCH);
        return LootTable.builder().addLootPool(builder);
    }
    
    public static LootTable.Builder ore(IItemProvider block, IItemProvider item, Function<StandaloneLootEntry.Builder<?>, StandaloneLootEntry.Builder<?>> bonus) {
        LootPool.Builder builder = LootPool.builder()
                .rolls(ConstantRange.of(1))
                .addEntry(
                		AlternativesLootEntry.builder(
                				ItemLootEntry.builder(block).acceptCondition(SILK_TOUCH),
                				bonus.apply(ItemLootEntry.builder(item).acceptFunction(ExplosionDecay.builder()))
                				)
                		);
        return LootTable.builder().addLootPool(builder);
    }
}
