package io.lemonjuice.tvlgensokyo.common.events;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = TravelogueGensokyo.MODID)
public class LootTableLoadHandler {
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        ResourceLocation name = event.getName();
        if(name.equals(LootTables.GAMEPLAY_FISHING_FISH)) {
            LootPool pool = event.getTable().getPool("main");
            LootEntry entry = getEntry("gameplay/fishing/hagfish", 30, -1);
            addEntry(pool, entry);
        }
    }

    private static LootEntry getEntry(String name, int weight, int quality) {
        return TableLootEntry.builder(new ResourceLocation(TravelogueGensokyo.MODID, name)).weight(weight).quality(quality).build();
    }

    private static void addEntry(LootPool pool, LootEntry entry) {
        if(pool != null) {
            try {
                ArrayList<LootEntry> lootEntries = (ArrayList<LootEntry>) ObfuscationReflectionHelper.findField(LootPool.class, "field_186453_a").get(pool);
                if (lootEntries.stream().anyMatch(e -> e == entry)) {
                    throw new RuntimeException("Attempted to add a duplicate entry to pool: " + entry);
                }
                lootEntries.add(entry);
            } catch (IllegalAccessException e) {
                TravelogueGensokyo.LOGGER.error("Error occurred when attempting to add a new entry, to the fishing loot table");
                e.printStackTrace();
            }
        }
    }

}