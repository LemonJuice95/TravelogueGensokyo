package io.lemonjuice.tvlgensokyo.common.misc.tags;

import io.lemonjuice.tvlgensokyo.TravelogueGensokyo;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class TGItemTags {
    //tvlgensokyo tags
    public static final ITag.INamedTag<Item> MAPLE_LOGS = createTGTag("maple_log");
    public static final ITag.INamedTag<Item> SAKURA_LOGS = createTGTag("sakura_log");

    //forge tags
    public static final ITag.INamedTag<Item> CUCUMBERS = createForgeTag("crops/cucumber");
    public static final ITag.INamedTag<Item> CUCUMBER_SEEDS = createForgeTag("seeds/cucumber");

    //mc tags

    private static ITag.INamedTag<Item> createTGTag(String name){
        return ItemTags.createOptional(new ResourceLocation(TravelogueGensokyo.MODID, name));
    }

    private static ITag.INamedTag<Item> createForgeTag(String name) {
        return ItemTags.createOptional(new ResourceLocation("forge", name));
    }

    private static ITag.INamedTag<Item> createMCTag(String name) {
        return ItemTags.createOptional(new ResourceLocation(name));
    }
}
