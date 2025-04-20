package io.lemonjuice.tvlgensokyo.datagen.loots;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TGLootTableGenerator extends LootTableProvider {
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> LOOT_TABLES = ImmutableList.of(
            Pair.of(TGBlockLootTables::new, LootParameterSets.BLOCK)
    );

    public TGLootTableGenerator(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return LOOT_TABLES;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationTracker) {
        map.forEach((id, lootTable) -> {
            LootTableManager.validateLootTable(validationTracker, id, lootTable);
        });
    }

    @Override
    public String getName() {
        return "Travelogue Gensokyo LootTables";
    }
}
